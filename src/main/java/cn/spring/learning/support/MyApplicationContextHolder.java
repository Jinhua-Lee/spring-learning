package cn.spring.learning.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 带Spring上下文的抽象类，给子类使用
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:19
 */
@Component
public class MyApplicationContextHolder implements ApplicationContextAware {

    protected static ApplicationContext CONTEXT;
    protected ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        CONTEXT = applicationContext;
        this.context = applicationContext;
    }

    public static  <T> T getBean(Class<T> requiredType) {
        return CONTEXT.getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static  <T> T getBean(String beanName) {
        return (T) CONTEXT.getBean(beanName);
    }
}
