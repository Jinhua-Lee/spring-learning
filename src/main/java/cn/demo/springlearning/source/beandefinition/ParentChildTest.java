package cn.demo.springlearning.source.beandefinition;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Data
@AllArgsConstructor
class ChildService {
    private int id;
    private String name;
}

@Data
@AllArgsConstructor
class ParentService {
    private int id;
    private String name;
}

public class ParentChildTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        GenericBeanDefinition parentBeanDefinition = new GenericBeanDefinition();
        parentBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        parentBeanDefinition.setAttribute("name", "coderLee");
        parentBeanDefinition.setAbstract(true);
        parentBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(1);
        parentBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue("CoderLee");

        GenericBeanDefinition childBeanDefinition = new GenericBeanDefinition();
        childBeanDefinition.setParentName("parent");
        childBeanDefinition.setBeanClass(ChildService.class);

        context.registerBeanDefinition("parent", parentBeanDefinition);
        context.registerBeanDefinition("child", childBeanDefinition);

        context.refresh();

        BeanDefinition child = context.getBeanFactory().getMergedBeanDefinition("child");
        for (String s : child.attributeNames()) {
            System.out.println(s + ":" + child.getAttribute(s));
        }
        System.out.println("scope:" + child.getScope());

        System.out.println("-------------------");

        ChildService service = context.getBean(ChildService.class);
        System.out.println(service.getId());
        System.out.println(service.getName());
    }
}
