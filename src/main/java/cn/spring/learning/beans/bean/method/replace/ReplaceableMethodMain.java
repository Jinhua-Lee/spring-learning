package cn.spring.learning.beans.bean.method.replace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 可替换的对象
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/7/2 18:37
 */
public class ReplaceableMethodMain {

    interface ReplaceableMethod {
        /**
         * 可替换的方法
         *
         * @return something
         */
        String method();
    }

    @Slf4j
    @Component
    public static class ReplaceMethodBean implements ReplaceableMethod {

        @Override
        public String method() {
            log.info("[origin method] I am ReplaceMethodBean#method.");
            return "ReplaceMethodBean#method: origin method.";
        }
    }

    /**
     * 替换原有方法实现，未找到对应的注解
     */
    @Slf4j
    public static class MyMethodReplacer implements MethodReplacer {

        @Override
        @NonNull
        public Object reimplement(@NonNull Object obj, @NonNull Method method, @NonNull Object[] args) {
            log.info("[replaced method] I am reimplement method.");
            return "this is replaced method";
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("replace-method.xml");
        ReplaceMethodBean beanA = context.getBean(ReplaceMethodBean.class);
        // 输出replacer中的内容
        System.out.println(beanA.method());
    }
}





