package cn.spring.learning.beans.config;

import cn.spring.learning.beans.bean.lifecycle.MyLifeCycleBean;
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

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyLifeCycleBean myLifecycleBean() {
        return new MyLifeCycleBean(1, "my lifecycle bean");
    }
}
