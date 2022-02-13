package cn.spring.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring学习
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.redis"
})
@EnableScheduling
public class SpringLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearningApplication.class, args);
    }
}
