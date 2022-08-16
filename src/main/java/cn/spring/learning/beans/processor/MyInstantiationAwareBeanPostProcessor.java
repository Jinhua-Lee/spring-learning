package cn.spring.learning.beans.processor;

import cn.spring.learning.beans.bean.lifecycle.AbstractLifecycleBean;
import cn.spring.learning.beans.bean.lifecycle.MyLifecycleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 实例化感知，Bean后置处理器
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/16 10:44
 */
@Slf4j
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(@NonNull Class<?> beanClass,
                                                 @NonNull String beanName) throws BeansException {
        // 可以在此处替换新的实现，只要不为空，后续就不会执行bean工厂中的createBean的创建
        final String myLifecycleBean = "myLifecycleBean";

        // 参数的beanClass是实现类
        if (AbstractLifecycleBean.class.isAssignableFrom(beanClass)) {
            if (Objects.equals(beanName, myLifecycleBean)) {
                if (log.isInfoEnabled()) {
                    log.info("完成了 beanName = {} 的bean创建前的替换，对应postProcessor的After仍然会执行", myLifecycleBean);
                }
                return new MyLifecycleBean(2, "这是一个替换的bean");
            } else {
                if (log.isInfoEnabled()) {
                    log.info("beanName = {} 的bean，使用BeanDefinition方式创建", myLifecycleBean);
                }
            }
        }
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public boolean postProcessAfterInstantiation(@NonNull Object bean,
                                                 @NonNull String beanName) throws BeansException {
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public PropertyValues postProcessProperties(@NonNull PropertyValues pvs,
                                                @NonNull Object bean, @NonNull String beanName) throws BeansException {
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }
}
