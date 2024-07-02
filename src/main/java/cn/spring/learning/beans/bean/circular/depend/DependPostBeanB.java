package cn.spring.learning.beans.bean.circular.depend;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/11 14:08
 */
@Getter
@Component
@Slf4j
public class DependPostBeanB {

    private DependPostBeanA dependPostBeanA;

    public DependPostBeanB() {
        log.info("[dependPostBeanB] construct...");
    }

    @Autowired
    public void setDependPostBeanA(DependPostBeanA dependPostBeanA) {
        this.dependPostBeanA = dependPostBeanA;
    }

    @PostConstruct
    @Order(value = 0)
    public void postConstruct() {
        log.info("[dependPostBeanB] post construct...");
    }
}