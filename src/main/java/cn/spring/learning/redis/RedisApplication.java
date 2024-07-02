package cn.spring.learning.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/11 14:57
 */
@EnableCaching
@ServletComponentScan
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf", "cn.spring.learning.redis"
})
public class RedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedisApplication.class, args);
        for (String beanName : run.getBeanFactory().getBeanNamesForType(RedisConnectionFactory.class)) {
            System.out.println("beanName = " + beanName);
        }
    }
}
