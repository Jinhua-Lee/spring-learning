package cn.spring.learning.beans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 【bean相关】的测试依赖启动类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/1/27 19:17
 */
@SpringBootApplication(scanBasePackages = {
        "cn.spring.learning.conf",
        // "cn.spring.learning.beans.bean.lifecycle",
        // "cn.spring.learning.beans.bean.inject",
        // "cn.spring.learning.beans.processor",
        // "cn.spring.learning.beans.bean.circular.depend",
        // "cn.spring.learning.beans.aop",
        // "cn.spring.learning.beans.bean.circular.plain",
        // "cn.spring.learning.beans.bean.hierarchical",
        // "cn.spring.learning.beans.bean.cond",
})
@EnableAspectJAutoProxy
public class BeansApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeansApplication.class, args);
    }
}
