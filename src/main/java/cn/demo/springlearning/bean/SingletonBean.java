package cn.demo.springlearning.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 单例Bean<p>&emsp;
 * 1) singleton（默认，初始化即创建）<p>&emsp;
 * 2) prototype（每次获取，根据原型创建）<p>&emsp;
 * 3) request（Web环境，bean生命周期与request一致）<p>&emsp;
 * 4) session（Web环境，bean生命周期与session一致）
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 22:26
 */
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@Scope(scopeName = "singleton")
@Slf4j
public class SingletonBean implements InitializingBean {

    private Integer id;
    private String name;

    @PostConstruct
    public void postConstruct() {
        log.info("post construct...");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("before destroy...");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("initializing bean...");
    }

    public void initMethod() {
        log.info("init method...");
    }

    public void destroyMethod() {
        log.info("destroy method...");
    }
}
