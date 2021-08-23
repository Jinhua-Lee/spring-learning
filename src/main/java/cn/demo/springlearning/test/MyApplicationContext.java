package cn.demo.springlearning.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 带Spring上下文的抽象类，给子类使用
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:19
 */
public abstract class MyApplicationContext {

    protected static final ApplicationContext CONTEXT;

    static {
        CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
//        CONTEXT = new AnnotationConfigApplicationContext("cn.demo");
    }
}
