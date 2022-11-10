package cn.spring.learning;

import cn.spring.learning.beans.aop.bean.OriginFunctionImpl;
import cn.spring.learning.beans.aop.bean.TargetFunction;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 17:35
 */
public class PlainTest {

    public static void main(String[] args) {
        System.out.println(TargetFunction.class.isAssignableFrom(OriginFunctionImpl.class));
    }
}
