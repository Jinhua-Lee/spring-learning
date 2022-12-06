package cn.spring.learning.beans.bean.hierarchical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/6 15:52
 */
@Slf4j
@Component
public class DepB {

    public void doSomething() {
        log.info("DepB -> doSomething");
    }
}
