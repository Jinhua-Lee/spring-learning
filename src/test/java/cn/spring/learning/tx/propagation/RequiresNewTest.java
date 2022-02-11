package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.PropagationTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 4. 测试RequiresNew：<p>&emsp;
 * 一律创建新事务。与父事务无关。
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:49
 */
public class RequiresNewTest extends PropagationTest {

    /**
     * 4.1 上层无事务，创建独立事务，事务间不受影响
     */
    @Test
    public void testNoTx_RequiresNew_RequiresNewEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层无事务，两个事务独立，则商品添加成功，顾客添加失败！（测试成功）
    }

    /**
     * 4.2 上层有事务，也创建独立事务。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_RequiresNew_RequiresNewEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层有事务，两个事务独立，则商品添加成功，顾客添加失败！（测试成功）
    }

    /**
     * 4.3 上层有事务，外围异常抛出
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxTry_RequiresNew_RequiresNew() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 外围方法异常，但内层方法不属于外围方法的事务，所以商品和顾客仍然插入成功！（测试成功）
    }
}
