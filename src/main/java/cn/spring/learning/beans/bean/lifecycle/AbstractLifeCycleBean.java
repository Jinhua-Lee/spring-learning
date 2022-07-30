package cn.spring.learning.beans.bean.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生命周期测试Bean，通用依赖支持
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/7/30 22:27
 */
@Slf4j
public abstract class AbstractLifeCycleBean implements InitializingBean, DisposableBean {

    /**
     * 获取BeanName
     *
     * @return beanName
     */
    protected abstract String getLifecycleBeanName();

    @PostConstruct
    public void postConstruct() {
        log.info("[{}] post construct...", getLifecycleBeanName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("[{}] before destroy...", getLifecycleBeanName());
    }

    @Override
    public void afterPropertiesSet() {
        log.info("[{}] initialize bean...", getLifecycleBeanName());
    }

    @Override
    public void destroy() {
        log.info("[{}] dispose bean...", getLifecycleBeanName());
    }

    public void initMethod() {
        log.info("[{}] init method...", getLifecycleBeanName());
    }

    public void destroyMethod() {
        log.info("[{}] destroy method...", getLifecycleBeanName());
    }
}
