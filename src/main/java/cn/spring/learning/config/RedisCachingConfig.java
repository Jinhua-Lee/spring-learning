package cn.spring.learning.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis缓存配置类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/11 14:59
 */
@Configuration
public class RedisCachingConfig extends CachingConfigurerSupport {

    public static final String REDIS_KEY_DATABASE = "mall";

    /**
     * 配置Redis模板
     *
     * @param redisConnectionFactory Redis的连接工厂
     * @return Redis模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> valueSerializer = redisSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置Redis的序列化器
     *
     * @return Redis序列化器
     */
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.registerModule(configJavaTimeModule());
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    /**
     * 配置时间模块
     *
     * @return 时间模块
     */
    private JavaTimeModule configJavaTimeModule() {
        JavaTimeModule timeModule = new JavaTimeModule();
        // 设置date, time, dateTime的序列化
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // 设置date, time, dateTime的反序列化
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return timeModule;
    }

    /**
     * Redis的缓存管理器
     *
     * @param rConnectionFactory redis的连接工厂
     * @return Redis的缓存管理器
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory rConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(rConnectionFactory);
        // 设置Redis缓存的有效期为1天
        RedisCacheConfiguration rCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())
                ).entryTtl(Duration.ofDays(1L));
        return new RedisCacheManager(redisCacheWriter, rCacheConfiguration);
    }
}
