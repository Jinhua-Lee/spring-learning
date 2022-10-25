package cn.spring.learning;

import cn.spring.learning.beans.aop.bean.MyFunction;
import cn.spring.learning.beans.aop.bean.MyFunctionImpl;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 17:35
 */
public class PlainTest {

    public static void main(String[] args) {
        System.out.println(MyFunction.class.isAssignableFrom(MyFunctionImpl.class));
    }
}
