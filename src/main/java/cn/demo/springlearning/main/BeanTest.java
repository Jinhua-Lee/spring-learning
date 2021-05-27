package cn.demo.springlearning.main;

import cn.demo.springlearning.bean.SingletonBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 非 Spring Boot环境
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/24 21:14
 */
public class BeanTest {

    private static final ApplicationContext CONTEXT;

    static {
        CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static void main(String[] args) {

    }

    /**
     * 修改XML中的scope，测试单例和原型
     */
    @Test
    public void testScope() {
        // 单例 => 返回同一个对象，true
        SingletonBean s1 = (SingletonBean) CONTEXT.getBean("sBean");
        SingletonBean s2 = (SingletonBean) CONTEXT.getBean("sBean");
        System.out.println(s1.equals(s2));

        // 原型 => 每次返回新对象，false
        SingletonBean s3 = (SingletonBean) CONTEXT.getBean("pBean");
        SingletonBean s4 = (SingletonBean) CONTEXT.getBean("pBean");
        System.out.println(s3.equals(s4));
    }

    /**
     * 测试Bean的静态工厂、实例工厂创建方式【是否能创建成功】
     */
    @Test
    public void testCreate() {
        // 1. 静态工厂
        SingletonBean staticBean = (SingletonBean) CONTEXT.getBean("beanStatic");
        System.out.println(staticBean);

        // 2. 实例工厂
        SingletonBean fBean = (SingletonBean) CONTEXT.getBean("fBean");
        System.out.println(fBean);
    }

    /**
     * 测试初始化及销毁方法
     */
    @Test
    public void testInitDestroy() {
        SingletonBean initDestroy = (SingletonBean) CONTEXT.getBean("initDestroy");
        System.out.println(initDestroy);
        // TODO 2021/5/27 待确定Bean的销毁时机，目前仅知道从以下两个时机会执行destroy方法
        ((AbstractApplicationContext)CONTEXT).close();
        ((AbstractApplicationContext)CONTEXT).registerShutdownHook();
    }
}
