package cn.demo.springlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring学习
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.demo.springlearning"
})
@EnableScheduling
public class SpringLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearningApplication.class, args);
    }
}
