package cn.spring.learning.beans.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 测试FactoryBean接口
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/8/23 23:30
 */
@Component
public class MyFactoryBean implements FactoryBean<SingletonBean> {
    @Override
    public SingletonBean getObject() {
        return new SingletonBean(21, "factoryBeanSingleton");
    }

    @Override
    public Class<?> getObjectType() {
        return SingletonBean.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
