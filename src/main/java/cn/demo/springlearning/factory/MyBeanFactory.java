package cn.demo.springlearning.factory;

import cn.demo.springlearning.bean.SingletonBean;

/**
 * 简单工厂
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 21:25
 */
public class MyBeanFactory {

    public static SingletonBean getSingletonStatic() {
        return new SingletonBean();
    }

    public SingletonBean getSingleton() {
        return new SingletonBean();
    }
}
