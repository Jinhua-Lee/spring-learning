package cn.spring.learning.beans.bean.cond;

import cn.spring.learning.beans.bean.lifecycle.MyLifecycleBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2023/1/3 14:51
 */
@Slf4j
@Component
@ConditionalOnMissingBean(value = MyLifecycleBean.class)
public class ConditionalBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("conditionalBean was initialized by Spring.");
    }
}
