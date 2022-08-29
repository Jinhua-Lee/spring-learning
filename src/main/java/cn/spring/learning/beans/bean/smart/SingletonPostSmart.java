package cn.spring.learning.beans.bean.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

/**
 * 单例存放到容器后，执行smart初始化做的事
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/8/27 13:42
 */
@Slf4j
@Component
public class SingletonPostSmart implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        log.info("这是singleton放入容器后，执行的smart回调做的事.");
    }
}
