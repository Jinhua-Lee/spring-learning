package cn.spring.learning.beans.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 自定义【BeanPostProcessor】
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/7/12 18:43
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final String certainName = "myLifecycleBean";
        if (Objects.equals(beanName, certainName)) {
            log.info("[{}] before init...", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final String certainName = "myLifecycleBean";
        if (Objects.equals(beanName, certainName)) {
            log.info("[{}] after init...", beanName);
        }
        return bean;
    }
}
