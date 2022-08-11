package cn.spring.learning.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 【事件】的测试依赖启动类
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf", "cn.spring.learning.event"
})
public class EventApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EventApplication.class, args);
        MyEventPublisher bean = run.getBean(MyEventPublisher.class);
        bean.publishEvent();
    }
}
