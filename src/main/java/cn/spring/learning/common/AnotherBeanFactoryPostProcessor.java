package cn.spring.learning.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 另一个【Bean工厂后置处理器】
 * 用于测试套娃注入定义
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/2 9:55
 */
@Slf4j
@Component
public class AnotherBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("this is another BeanFactoryPostProcessor.");
    }
}
