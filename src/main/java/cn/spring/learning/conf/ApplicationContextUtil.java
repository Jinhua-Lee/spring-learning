package cn.spring.learning.conf;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jinhua-Lee
 */
@Slf4j
@Component
@SuppressWarnings(value = "unused")
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext STATIC_APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        STATIC_APPLICATION_CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> classT) {
        if (null == STATIC_APPLICATION_CONTEXT) {
            return null;
        }
        return STATIC_APPLICATION_CONTEXT.getBean(classT);
    }

    public static Object getBean(String beanName) {
        if (null == STATIC_APPLICATION_CONTEXT) {
            return null;
        }
        return STATIC_APPLICATION_CONTEXT.getBean(beanName);
    }

    /**
     * 获取接口的所有实现类
     */
    @SuppressWarnings(value = "unchecked")
    public static <T> List<T> getBeanList(Class<T> classT) {
        String[] beanNames = STATIC_APPLICATION_CONTEXT.getBeanNamesForType(classT);
        List<T> beanList = new ArrayList<>();
        Object bean;
        for (String beanName : beanNames) {
            bean = getBean(beanName);
            if (null != bean) {
                beanList.add((T) bean);
            }
        }
        return beanList;
    }

    public static void publishEvent(ApplicationEvent event) {
        STATIC_APPLICATION_CONTEXT.publishEvent(event);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return STATIC_APPLICATION_CONTEXT.getBean(beanName, clazz);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return STATIC_APPLICATION_CONTEXT.getBeansWithAnnotation(annotationType);
    }

    public static Class<?> getType(String beanName) {
        return STATIC_APPLICATION_CONTEXT.getType(beanName);
    }

    public static String getActiveProfile() {
        return STATIC_APPLICATION_CONTEXT.getEnvironment().getActiveProfiles()[0];
    }

    public static List<String> getBeanNames() {
        return Arrays.asList(STATIC_APPLICATION_CONTEXT.getBeanDefinitionNames());
    }

    public static <T> T getPropertyValue(String key, Class<T> clazz) {
        return STATIC_APPLICATION_CONTEXT.getEnvironment().getProperty(key, clazz);
    }

    public static void stopContext() {
        AbstractApplicationContext aac = (AbstractApplicationContext) STATIC_APPLICATION_CONTEXT;
        aac.close();
    }
}
