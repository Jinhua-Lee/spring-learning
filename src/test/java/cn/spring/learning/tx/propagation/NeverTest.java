package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.PropagationTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 6. 测试Never：<p>&emsp;
 * 1) 以非事务方式执行；<p>&emsp;
 * 2)若当前存在事务，则抛出异常；<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:53
 */
public class NeverTest extends PropagationTest {

    /**
     * 6.1 以非事务方式执行
     */
    @Test
    public void testNoTx_Never_Never() {
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
     * 6.2 上层存在事务，则直接抛出异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Never_Never() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层存在事务，直接抛异常，方法不执行，商品和顾客都插入失败！（测试成功）
    }
}
