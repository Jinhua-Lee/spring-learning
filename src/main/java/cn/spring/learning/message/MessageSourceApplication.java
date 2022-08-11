package cn.spring.learning.message;

import cn.spring.learning.message.source.HelloLocale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 【国际化消息源】启动器
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/4 15:46
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf",
        "cn.spring.learning.message.source"
})
public class MessageSourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MessageSourceApplication.class, args);
        HelloLocale helloLocale = context.getBean(HelloLocale.class);
        helloLocale.doSomething();
    }
}
