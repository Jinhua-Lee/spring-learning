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
        "cn.spring.learning.mq"
})
public class RocketMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketMqApplication.class, args);
    }
}
