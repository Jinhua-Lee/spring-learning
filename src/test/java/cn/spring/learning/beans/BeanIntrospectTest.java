package cn.spring.learning.beans;

import cn.spring.learning.tx.entity.Account;
import lombok.SneakyThrows;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;

/**
 * JavaBean的内省机制测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/1/4 22:13
 */
public class BeanIntrospectTest {

    @Test
    @SneakyThrows
    public void testBeanIntrospect() {
        BeanInfo beanInfo = Introspector.getBeanInfo(Account.class, Object.class);

        System.out.println();
    }
}
