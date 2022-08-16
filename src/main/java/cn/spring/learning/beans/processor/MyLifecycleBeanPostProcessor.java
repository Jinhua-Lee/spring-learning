package cn.spring.learning.beans.processor;

import cn.spring.learning.beans.bean.lifecycle.AbstractLifecycleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 自定义【BeanPostProcessor】<p>&emsp;
 * <p>
 * 提供给{@link AbstractLifecycleBean} 子类使用
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/7/12 18:43
 */
@Slf4j
@Component
public class MyLifecycleBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof AbstractLifecycleBean) {
            log.info("[{}] before init...", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof AbstractLifecycleBean) {
            log.info("[{}] after init...", beanName);
        }
        return bean;
    }
}
