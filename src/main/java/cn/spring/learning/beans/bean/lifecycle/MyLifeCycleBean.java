package cn.spring.learning.beans.bean.lifecycle;

import cn.spring.learning.beans.config.BeanLifeCycleConfig;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 【Bean生命周期】测试<p>
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/7/12 19:14
 * @see BeanLifeCycleConfig#myLifecycleBean() 通过@Bean注解于方法返回值，来调试，所以不通过@Component方法返回
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLifeCycleBean implements InitializingBean {

    private Integer id;
    private String name;

    private static final String BEAN_NAME = "myLifecycleBean";

    @PostConstruct
    public void postConstruct() {
        log.info("[{}] post construct...", BEAN_NAME);
    }

    @PreDestroy
    public void preDestroy() {
        log.info("[{}] before destroy...", BEAN_NAME);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("[{}] initializing bean...", BEAN_NAME);
    }

    public void initMethod() {
        log.info("[{}] init method...", BEAN_NAME);
    }

    public void destroyMethod() {
        log.info("[{}] destroy method...", BEAN_NAME);
    }
}
