package cn.spring.learning.redis;

import cn.spring.learning.event.EventApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/11 14:57
 */
@EnableCaching
@ServletComponentScan
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.config", "cn.spring.learning.redis"
})
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
    }
}
