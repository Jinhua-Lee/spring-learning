package cn.spring.learning.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 10:13
 */
@EnableConfigurationProperties
@ServletComponentScan(basePackages = "cn.spring.learning.mvc")
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.config", "cn.spring.learning.mvc"
})
public class MvcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcDemoApplication.class, args);
    }
}
