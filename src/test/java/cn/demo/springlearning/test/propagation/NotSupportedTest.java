package cn.demo.springlearning.test.propagation;

import cn.demo.springlearning.test.PropagationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * 5. 测试NotSupported：<p>&emsp;
 * 非事务方式执行操作。当前存在事务，就把当前事务挂起
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:51
 */
public class NotSupportedTest extends PropagationTest {

    /**
     * 非事务方式执行操作。当前存在事务，就把当前事务挂起
     */
    @Test
    @Transactional
    public void testNotSupported() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 无论当前方法是否开启事务，都以非事务方式执行，两批都插入成功！（测试通过）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }
}
