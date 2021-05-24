package cn.demo.springlearning.main;

import cn.demo.springlearning.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 非 Spring Boot环境
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/24 21:14
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }
}
