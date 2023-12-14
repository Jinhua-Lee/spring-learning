package cn.spring.learning.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jinhua-Lee
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf",
        "cn.spring.learning.env",
})
public class EnvPropApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnvPropApplication.class, args);
    }
}
