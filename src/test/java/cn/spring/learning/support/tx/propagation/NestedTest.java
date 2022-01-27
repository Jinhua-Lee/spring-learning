package cn.spring.learning.support.tx.propagation;

import cn.spring.learning.support.PropagationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 7. 测试Nested：<p>&emsp;
 * 1) 存在事务，则以嵌套事务方式执行；<p>&emsp;
 * 2) 不存在事务，则按Required方式执行；<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:56
 */
public class NestedTest extends PropagationTest {

    /**
     * 7.1 上层无事务，创建新事务
     */
    @Test
    public void testNoTx_Nested_NestedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 两个事务各自独立，顾客插入有异常抛出，则商品插入成功，顾客都插入失败！（测试成功）
    }

    /**
     * 7.2 上层有事务，保留事务依赖性
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Nested_NestedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 顾客事务发生异常回滚，上层事务也要回滚，则所有都插入失败！（测试成功）
    }

    /**
     * 7.3 上层有事务，对于异常的子事务捕获异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Nested_NestedExTry() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        System.out.println("comRes = " + comRes);
        try {
            boolean cusRes = propagationService.addCustomersException(buildCustomers());
            System.out.println("cusRes = " + cusRes);
        } catch (Exception ignored) {
            // FIXME: 2021/6/9 顾客事务发生异常回滚，但异常不暴露给上层，上层事务执行成功，则商品插入成功，顾客插入失败！（测试失败！）
        }
    }

    /**
     * 7.4 上层有事务，外围事务方法异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxEx_Nested_Nested() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 嵌套事务，父事务异常，子事务的也插入失败！（测试成功）
    }
}
