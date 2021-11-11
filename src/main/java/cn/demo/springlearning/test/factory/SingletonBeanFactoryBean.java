package cn.demo.springlearning.test.factory;

import cn.demo.springlearning.bean.SingletonBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 工厂Bean，本身作为Bean，是产生Bean的Bean
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 21:25
 */
@Component
public class SingletonBeanFactoryBean implements FactoryBean<SingletonBean> {

    /**
     * 替代了实例工厂方法注入
     *
     * @return SingletonBean
     */
    @Override
    public SingletonBean getObject() {
        return new SingletonBean();
    }

    @Override
    public Class<SingletonBean> getObjectType() {
        return SingletonBean.class;
    }
}
