package cn.spring.learning.beans.bean.factorybean;

import cn.spring.learning.beans.bean.scope.SingletonBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/9/12 11:59
 */
@Component
public class MySmartFactoryBean implements SmartFactoryBean<SingletonBean> {
    @Override
    public SingletonBean getObject() {
        return new SingletonBean(22, "smartFactoryBeanSingleton");
    }

    @Override
    public Class<?> getObjectType() {
        return SingletonBean.class;
    }

    @Override
    public boolean isEagerInit() {
        return true;
    }
}
