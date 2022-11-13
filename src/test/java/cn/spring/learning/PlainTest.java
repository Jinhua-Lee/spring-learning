package cn.spring.learning;

import cn.spring.learning.beans.aop.bean.OriginFunctionImpl;
import cn.spring.learning.beans.aop.bean.TargetFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 17:35
 */
public class PlainTest implements Serializable {

    public static void main(String[] args) {
        System.out.println(TargetFunction.class.isAssignableFrom(OriginFunctionImpl.class));
    }

    @Test
    @DisplayName(value = "通过class的getInterfaces方法，判断某个类型实现的接口")
    public void testInterfaces() {
        Class<?>[] interfaces = PlainTest.class.getInterfaces();
        // 预期：Serializable接口
        Arrays.stream(interfaces).forEach(System.out::println);
    }
}
