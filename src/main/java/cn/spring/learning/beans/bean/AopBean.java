package cn.spring.learning.beans.bean;

import org.springframework.stereotype.Component;

/**
 * 被代理的原始对象
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/29 20:46
 */
@Component
public class AopBean {

    public void method() {
        System.out.println("execute target method.");
    }
}
