package cn.spring.learning.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 【事务相关】的测试依赖启动类
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf", "cn.spring.learning.tx"
})
@EnableScheduling
public class TxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxApplication.class, args);
    }
}
