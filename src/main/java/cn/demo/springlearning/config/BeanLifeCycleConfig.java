package cn.demo.springlearning.config;

import cn.demo.springlearning.bean.SingletonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean的生命周期测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2021/11/12 0:03
 */
@Configuration
public class BeanLifeCycleConfig {

    @Bean(name = "singletonBean2", initMethod = "initMethod", destroyMethod = "destroyMethod")
    public SingletonBean singletonBean() {
        return new SingletonBean(1, "a singleton bean");
    }
}
