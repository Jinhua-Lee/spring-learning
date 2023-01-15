package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.BasePropagationTest;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 2. 测试Supports：<p>&emsp;
 * 1) 调用方法存在事务，就加入该事务；<p>&emsp;
 * 2) 调用方法不存在事务，就以非事务执行；<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:44
 */
@Service
public class SupportsTest extends BasePropagationTest {

    /**
     * 2.1 调用方法未开启事务，以非事务方式执行
     */
    @Test
    public void testNoTx_Supports_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 该测试方法未开启事务，非事务执行，商品和顾客都插入成功！（测试成功）
    }

    /**
     * 2.2 调用方法开启Required事务，两个子事务(supports)方法加入该事务
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Supports_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 开启Required事务，第二个子事务方法抛异常，整个事务回滚，商品和顾客都插入失败！（测试成功）
    }
}
