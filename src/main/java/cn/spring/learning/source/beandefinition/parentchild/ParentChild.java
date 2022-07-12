package cn.spring.learning.source.beandefinition.parentchild;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * bean定义的父子实现
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/8/24 13:55
 */
public class ParentChild {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 1. 抽象父类
        GenericBeanDefinition parentBeanDefinition = new GenericBeanDefinition();
        parentBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        parentBeanDefinition.setAttribute("name", "coderLee");
        parentBeanDefinition.setAbstract(true);
        // 设置构造器参数：顺序不影响，会自动识别类型
        parentBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(1);
        parentBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue("CoderLee");

        // 2. 设置子类
        GenericBeanDefinition childBeanDefinition = new GenericBeanDefinition();
        childBeanDefinition.setParentName("parent");
        childBeanDefinition.setBeanClass(ChildService.class);

        context.registerBeanDefinition("parent", parentBeanDefinition);
        context.registerBeanDefinition("child", childBeanDefinition);

        context.refresh();

        // bean定义的合并
        BeanDefinition child = context.getBeanFactory().getMergedBeanDefinition("child");
        for (String attrName : child.attributeNames()) {
            System.out.println(attrName + ": " + child.getAttribute(attrName));
        }
        System.out.println("scope:" + child.getScope());

        System.out.println("-------------------");

        ChildService service = context.getBean(ChildService.class);
        System.out.println(service.getId());
        System.out.println(service.getName());
    }

    @Data
    @AllArgsConstructor
    private static class ChildService {
        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class ParentService {
        private int id;
        private String name;
    }
}
