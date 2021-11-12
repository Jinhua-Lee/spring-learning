package cn.demo.springlearning.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 带Spring上下文的抽象类，给子类使用
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:19
 */
public class MyApplicationContext implements ApplicationContextAware {

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
