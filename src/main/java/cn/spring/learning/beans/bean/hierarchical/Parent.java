package cn.spring.learning.beans.bean.hierarchical;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模拟抽象类和实现类的Bean依赖
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/6 15:48
 */
@Component
public abstract class Parent implements InitializingBean {

    private DepA depA;

    @Override
    public void afterPropertiesSet() {
        if (depA.flag) {
            doRun();
        }
    }

    /**
     * 真正执行的内容
     */
    public abstract void doRun();

    @Autowired
    public void setDep(DepA depA) {
        this.depA = depA;
    }
}
