package cn.demo.springlearning.test;

import cn.demo.springlearning.bean.AopBean;
import cn.demo.springlearning.bean.ComplexInjectionBean;
import cn.demo.springlearning.bean.SingletonBean;
import cn.demo.springlearning.tx.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

    /**
     * 测试JDBC
     */
    @Test
    public void testJdbc() {
        ComboPooledDataSource c3p0dataSource = new ComboPooledDataSource("c3p0-config.xml");
        JdbcTemplate jTemplate = new JdbcTemplate(c3p0dataSource);
        String sql = "select * from usr";
        List<User> users = jTemplate.query(sql,
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
