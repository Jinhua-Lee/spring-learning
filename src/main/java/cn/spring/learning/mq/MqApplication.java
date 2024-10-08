package cn.spring.learning.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/11 20:41
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf",
        // "cn.spring.learning.mq.rabbit",
        // "cn.spring.learning.mq.rocket",
        // "cn.spring.learning.mq.mqtt",
})
public class MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }
}
