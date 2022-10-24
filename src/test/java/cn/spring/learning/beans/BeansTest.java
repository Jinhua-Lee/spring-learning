package cn.spring.learning.beans;

import cn.spring.learning.beans.aop.bean.AopBean;
import cn.spring.learning.beans.bean.factorybean.MyFactoryBean;
import cn.spring.learning.beans.bean.circular.plain.BeanA;
import cn.spring.learning.beans.bean.circular.plain.BeanB;
import cn.spring.learning.beans.bean.inject.ComplexInjectionBean;
import cn.spring.learning.beans.bean.scope.PrototypeBean;
import cn.spring.learning.beans.bean.scope.SingletonBean;
import cn.spring.learning.support.MyApplicationContextHolder;
import cn.spring.learning.tx.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

/**
 * 非 Spring Boot环境
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/24 21:14
 */
@SpringBootTest(classes = BeansApplication.class)
@ActiveProfiles(profiles = "company")
@Slf4j
public class BeansTest extends MyApplicationContextHolder {

    /**
     * 修改XML中的scope，测试单例和原型
     */
    @Test
    public void testScope() {
        // 单例 => 返回同一个对象，true
        SingletonBean s1 = (SingletonBean) context.getBean("singletonBean");
        SingletonBean s2 = (SingletonBean) context.getBean("singletonBean");
        System.out.println(s1.equals(s2));

        // 原型 => 每次返回新对象，false
        PrototypeBean s3 = (PrototypeBean) context.getBean("prototypeBean");
        PrototypeBean s4 = (PrototypeBean) context.getBean("prototypeBean");
        System.out.println(s3.equals(s4));
    }

    /**
     * 测试Bean的静态工厂、实例工厂创建方式【是否能创建成功】
     */
    @Test
    public void testCreate() {
        // 1. 静态工厂
        SingletonBean staticBean = (SingletonBean) context.getBean("beanStatic");
        System.out.println(staticBean);

        // 2. 实例工厂
        SingletonBean fBean = (SingletonBean) context.getBean("fBean");
        System.out.println(fBean);

        // 3. FactoryBean方式
        String beanName = "factoryBeanSingleton";
        // 3.1 工厂Bean
        MyFactoryBean factoryBean = (MyFactoryBean) context.getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
        System.out.println(factoryBean);
        // 3.2 工厂Bean返回的Bean
        SingletonBean factoryBeanSingleton = (SingletonBean) context.getBean(beanName);
        System.out.println(factoryBeanSingleton);
    }

    /**
     * 测试构造器注入
     */
    @Test
    @SneakyThrows
    public void testCtorInject() {
        SingletonBean cBean = (SingletonBean) context.getBean("cBean");
        System.out.println(toString(cBean));
    }

    /**
     * 测试p名称空间注入
     */
    @Test
    public void pNameInject() {
        SingletonBean pNameBean = (SingletonBean) context.getBean("pNameBean");
        System.out.println(toString(pNameBean));
    }

    /**
     * 测试复杂类型注入
     */
    @Test
    public void testComplexInjection() {
        ComplexInjectionBean complexBean = (ComplexInjectionBean) context.getBean("complexInjectionBean");
        log.info("===== int array yml start =====");
        Arrays.stream(complexBean.getIntArrayYml()).forEach(System.out::println);
        log.info("===== int array yml end =====");

        log.info("===== int array props start =====");
        Arrays.stream(complexBean.getIntArrayProps()).forEach(System.out::println);
        log.info("===== int array props end =====");

        log.info("===== integer list csv start =====");
        complexBean.getIntegerListCsv().forEach(System.out::println);
        log.info("===== integer list csv end =====");

        log.info("===== int array by value start =====");
        Arrays.stream(complexBean.getIntArrByValue()).forEach(System.out::println);
        log.info("===== int array by value start =====");

        log.info("===== str array yml start =====");
        Arrays.stream(complexBean.getStrArrayYml()).forEach(System.out::println);
        log.info("===== str array yml end =====");

        log.info("===== map yml start =====");
        complexBean.getInt2StrYml().forEach((k, v) -> System.out.println(k + "-->" + v));
        log.info("===== map yml end =====");
    }

    /**
     * 测试初始化及销毁方法
     */
    @Test
    public void testInitDestroy() {
        SingletonBean initDestroy = (SingletonBean) context.getBean("initDestroy");
        System.out.println(initDestroy);
        // TODO 2021/5/27 待确定Bean的销毁时机，目前仅知道从以下两个时机会执行destroy方法
        ((AbstractApplicationContext) context).close();
        ((AbstractApplicationContext) context).registerShutdownHook();
    }

    /**
     * 测试AOP方法增强
     */
    @Test
    public void testAop() {
        AopBean aopBean = (AopBean) context.getBean("aopBean");
        aopBean.method();
    }

    @Test
    public void testCircularDependency() {
        BeanA beanA = (BeanA) context.getBean("beanA");
        BeanB beanB = (BeanB) context.getBean("beanB");

        System.out.println(beanA);
        System.out.println(beanB);
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
