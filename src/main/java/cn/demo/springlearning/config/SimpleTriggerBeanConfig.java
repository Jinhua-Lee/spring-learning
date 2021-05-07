package cn.demo.springlearning.config;

import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 简单触发器配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/7 9:43
 */
@Component
public class SimpleTriggerBeanConfig {

    @Bean
    public SimpleTrigger config() {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(5_000L);
        factoryBean.setRepeatCount(30);
        return factoryBean.getObject();
    }
}
