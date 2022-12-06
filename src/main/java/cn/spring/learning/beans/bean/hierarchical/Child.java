package cn.spring.learning.beans.bean.hierarchical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/6 15:51
 */
@Component
public class Child extends Parent {

    private DepB depB;

    @Override
    public void doRun() {
        depB.doSomething();
    }

    @Autowired
    public void setDepB(DepB depB) {
        this.depB = depB;
    }
}
