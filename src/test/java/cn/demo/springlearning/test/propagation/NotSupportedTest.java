package cn.demo.springlearning.test.propagation;

import cn.demo.springlearning.test.PropagationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
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
     * 5.1 不存在，则以非事务方式执行操作
     */
    @Test
    public void testNoTx_NotSupported_NotSupportedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 非事务方式，商品插入成功，顾客插入失败！（测试成功）
    }

    /**
     * 5.2 当前存在事务，就把当前事务挂起（影响上层事务）
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_NotSupported_NotSupportedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 非事务方式，挂起上层事务，商品和顾客都插入成功！（测试成功）
    }
}
