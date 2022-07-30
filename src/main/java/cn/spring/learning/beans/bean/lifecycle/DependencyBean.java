package cn.spring.learning.beans.bean.lifecycle;

import org.springframework.stereotype.Component;

/**
 * 被依赖的Bean
 * 由DependsOn注解引用
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/7/30 22:21
 */
@Component
public class DependencyBean extends AbstractLifeCycleBean {

    private static final String BEAN_NAME = "dependencyBean";

    @Override
    protected String getLifecycleBeanName() {
        return BEAN_NAME;
    }
}
