package cn.spring.learning.beans.aop.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 11:03
 */
@Slf4j
@Component
public class OriginFunctionImpl implements OriginFunction {

    @Override
    public void doSomething() {
        log.info("实现类做的事情");
    }
}
