package cn.spring.learning.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 【bean相关】的测试依赖启动类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/1/27 19:17
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.config",
        "cn.spring.learning.beans.config",
        "cn.spring.learning.beans.bean.circular",
        "cn.spring.learning.beans.processor"
})
@Slf4j
public class BeansApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeansApplication.class, args);
    }
}
