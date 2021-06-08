package cn.demo.springlearning.test.propagation;

import cn.demo.springlearning.test.PropagationTest;
import org.junit.Test;
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
     * 以非事务方式执行；
     */
    @Test
    @Transactional
    public void testNever() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，以非事务方式执行，两批数据都插入成功！（测试通过）
        // TODO: 2021/6/8 2. 该测试方法开启事务，不执行，抛出异常！（测试失败）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }
}
