package cn.spring.learning.beans.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 【Bean定义注册】后置处理器
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/2 9:40
 */
@Slf4j
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        // 可以实现套娃注入Bean定义
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        // 扫描其他包
        String packagePath = "cn.spring.learning.common";
        int scanned = scanner.scan(packagePath);
        if (log.isInfoEnabled()) {
            log.info("totally scanned {} bean at class path {}", scanned, packagePath);
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
