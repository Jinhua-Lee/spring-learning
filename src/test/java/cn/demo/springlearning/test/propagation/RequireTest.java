package cn.demo.springlearning.test.propagation;

import cn.demo.springlearning.test.PropagationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 1. 测试Require：<p>&emsp;
 * 1) 调用方法无事务，创建一个新事务；<p>&emsp;
 * 2) 调用方法存在事务，加入该事务；<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:29
 */
public class RequireTest extends PropagationTest {

    /**
     * 1.1 调用方法没有事务，两个事务方法各自独立
     */
    @Test
    public void testNoTx_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 该测试方法未开启事务，两个方法各自独立，商品成功，顾客失败！(测试成功)
    }

    /**
     * 1.2.1 调用方法存在事务，外围方法中有异常<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 开启事务，外围方法抛异常，商品和顾客都插入失败！
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxOut_Required_Required() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 同属外围事务方法，外围方法抛异常，商品和顾客都插入失败！（测试成功）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 1.2.2 调用方法存在事务，其中一个子事务方法异常<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 开启事务，其中一个事务抛异常，则商品和顾客都插入失败！
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 开启事务，两个方法同属外围事务，商品和顾客均插入失败！(测试成功)
    }

    /**
     * 1.2.3 调用方法存在事务，就加入该事务<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 即使被catch不被外部感知，但发生回滚了。整个事务都回滚
     */
    @Test
    @Transactional
    public void testTxOut_Required_RequiredExTry() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        System.out.println("comRes = " + comRes);
        try {
            boolean cusRes = propagationService.addCustomersException(buildCustomers());
            System.out.println("cusRes = " + cusRes);
        } catch (Exception e) {
            // 即使被catch不被外部感知，但发生回滚了，会被事务管理器监测到。整个事务都回滚。（测试成功）
            e.printStackTrace();
        }
    }
}
