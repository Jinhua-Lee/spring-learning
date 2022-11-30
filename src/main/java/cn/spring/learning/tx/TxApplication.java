package cn.spring.learning.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 【事务相关】的测试依赖启动类<p>&emsp;
 * exposeProxy 允许暴露代理对象到AopContext
 *
 * @author Jinhua
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf",
        "cn.spring.learning.tx"
})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class TxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxApplication.class, args);
    }
}
