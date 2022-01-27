package cn.spring.learning;

import cn.spring.learning.event.MyEventPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring学习
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning"
})
@EnableScheduling
public class SpringLearningApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringLearningApplication.class, args);
        MyEventPublisher eventPublisher = run.getBean(MyEventPublisher.class);
        eventPublisher.publishEvent();
    }
}
