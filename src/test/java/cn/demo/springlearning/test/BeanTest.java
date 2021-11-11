package cn.demo.springlearning.test;

import cn.demo.springlearning.bean.*;
import cn.demo.springlearning.entity.User;
import cn.demo.springlearning.bean.circular.BeanA;
import cn.demo.springlearning.bean.circular.BeanB;
import cn.demo.springlearning.bean.circular.BeanC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * 非 Spring Boot环境
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/24 21:14
 */
public class BeanTest extends MyApplicationContext {

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
        PrototypeBean s3 = (PrototypeBean) CONTEXT.getBean("pBean");
        PrototypeBean s4 = (PrototypeBean) CONTEXT.getBean("pBean");
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

        // 3. FactoryBean方式
        String beanName = "factoryBeanSingleton";
        // 3.1 工厂Bean
        MyFactoryBean factoryBean = (MyFactoryBean) CONTEXT.getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
        System.out.println(factoryBean);
        // 3.2 工厂Bean返回的Bean
        SingletonBean factoryBeanSingleton = (SingletonBean) CONTEXT.getBean(beanName);
        System.out.println(factoryBeanSingleton);
    }

    /**
     * 测试构造器注入
     */
    @Test
    @SneakyThrows
    public void testCtorInject() {
        SingletonBean cBean = (SingletonBean) CONTEXT.getBean("cBean");
        System.out.println(toString(cBean));
    }

    /**
     * 测试p名称空间注入
     */
    @Test
    public void pNameInject() {
        SingletonBean pNameBean = (SingletonBean) CONTEXT.getBean("pNameBean");
        System.out.println(toString(pNameBean));
    }

    /**
     * 测试复杂类型注入
     */
    @Test
    public void testComplexInjection() {
        ComplexInjectionBean complexBean = (ComplexInjectionBean) CONTEXT.getBean("complexBean");
        Arrays.stream(complexBean.getIntArray()).forEach(System.out::println);
        complexBean.getIntegers().forEach(System.out::println);
        complexBean.getInt2Str().forEach((k, v) -> System.out.println(k + "-->" + v));
    }

    /**
     * 测试初始化及销毁方法
     */
    @Test
    public void testInitDestroy() {
        SingletonBean initDestroy = (SingletonBean) CONTEXT.getBean("initDestroy");
        System.out.println(initDestroy);
        // TODO 2021/5/27 待确定Bean的销毁时机，目前仅知道从以下两个时机会执行destroy方法
        ((AbstractApplicationContext) CONTEXT).close();
        ((AbstractApplicationContext) CONTEXT).registerShutdownHook();
    }

    /**
     * 测试AOP方法增强
     */
    @Test
    public void testAop() {
        AopBean aopBean = (AopBean) CONTEXT.getBean("aopBean");
        aopBean.method();
    }

    @Test
    public void testCircularBean() {
        BeanA a = (BeanA) CONTEXT.getBean("a");
        BeanB b = (BeanB) CONTEXT.getBean("b");
        BeanC c = (BeanC) CONTEXT.getBean("c");

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    /**
     * 测试JDBC
     */
    @Test
    public void testJdbc() {
        ComboPooledDataSource c3p0dataSource = new ComboPooledDataSource("c3p0-config.xml");
        JdbcTemplate template = new JdbcTemplate(c3p0dataSource);
        String sql = "select * from usr";
        List<User> users = template.query(sql,
                (rs, i) -> new User(rs.getInt("id"), rs.getString("name"),
                rs.getString("pwd"), rs.getString("sex"),
                rs.getString("home"), rs.getString("info")
        ));
        users.forEach(System.out::println);
    }

    @SneakyThrows
    private String toString(Object obj) {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
