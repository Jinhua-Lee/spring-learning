package cn.spring.learning.scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jinhua
 * @version 1.0
 * @date 19/04/2022 17:43
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.config", "cn.spring.learning.scheduled"
})
public class ScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledApplication.class, args);
    }
}
