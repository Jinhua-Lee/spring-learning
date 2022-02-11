package cn.spring.learning.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        serializer.setObjectMapper(objectMapper);
        return serializer;
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
