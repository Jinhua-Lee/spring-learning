package cn.demo.springlearning.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/1/24 14:08
 */
@Slf4j
@Component
public class MyEventPublisher implements ApplicationEventPublisherAware, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent() {
        log.info("开始发布自定义事件MyEvent");
        // 传入事件对象
        MyEvent myEvent = new MyEvent(applicationContext);
        // 发布事件
        applicationEventPublisher.publishEvent(myEvent);
        log.info("发布自定义事件MyEvent结束");
    }
}
