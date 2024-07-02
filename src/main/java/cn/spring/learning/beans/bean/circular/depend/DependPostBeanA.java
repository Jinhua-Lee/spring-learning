package cn.spring.learning.beans.bean.circular.depend;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * {@link DependsOn} 指定实例化顺序<p>
 * {@link Order} 注解暂时未生效
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/17 23:28
 */
@Getter
@Component
@Slf4j
@DependsOn(value = "dependPostBeanB")
public class DependPostBeanA {

    private DependPostBeanB dependPostBeanB;

    public DependPostBeanA() {
        log.info("[dependPostBeanA] construct...");
    }

    @Autowired
    public void setDependPostBeanB(DependPostBeanB dependPostBeanB) {
        this.dependPostBeanB = dependPostBeanB;
    }

    @PostConstruct
    @Order(value = 1)
    public void postConstruct() {
        log.info("[dependPostBeanA] post construct..");
    }
}