package cn.spring.learning.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/1/24 14:07
 */
public class MyEvent extends ApplicationContextEvent {

    public MyEvent(ApplicationContext source) {
        super(source);
    }
}
