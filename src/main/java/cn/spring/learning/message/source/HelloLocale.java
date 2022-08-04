package cn.spring.learning.message.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化消息测试
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/4 15:54
 */
@Slf4j
@Component
public class HelloLocale implements ApplicationContextAware {

    private ApplicationContext appCtx;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

    public void doSomething() {
        String appName = appCtx.getMessage("appName", null, Locale.US);
        log.info("appName = {}", appName);
    }
}
