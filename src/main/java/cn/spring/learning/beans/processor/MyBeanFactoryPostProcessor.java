package cn.spring.learning.beans.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Bean工厂后置处理器
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/7/12 19:05
 */
@Slf4j
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            log.info("[bean factory post processor] ...");
        }
    }
}
