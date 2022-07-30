package cn.spring.learning.beans.bean.lifecycle;

import cn.spring.learning.beans.config.BeanLifeCycleConfig;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 【Bean生命周期】测试<p>
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/7/12 19:14
 * @see BeanLifeCycleConfig#myLifecycleBean() 通过@Bean注解于方法返回值，来调试，所以不通过@Component方法返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DependsOn(value = "dependencyBean")
public class MyLifecycleBean extends AbstractLifeCycleBean implements InitializingBean, DisposableBean {

    private Integer id;
    private String name;

    private static final String BEAN_NAME = "myLifecycleBean";

    @Override
    protected String getLifecycleBeanName() {
        return BEAN_NAME;
    }
}
