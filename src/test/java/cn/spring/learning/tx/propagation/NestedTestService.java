package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.BasePropagationTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 7. 测试Nested：<p>&emsp;
 * 1) 存在事务，则以嵌套事务方式执行；<p>&emsp;&emsp;
 *      - 当前PlatformTransactionManager下单数据库，采用savePoint保存点去实现伪嵌套事务。<p>&emsp;&emsp;
 *      - 分布式事务JTA情况下才会真正开启nested嵌套事务。<p>&emsp;
 * 2) 不存在事务，则按Required方式执行；<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:56
 */
@Service
@RequestMapping(value = "nested")
public class NestedTestService extends BasePropagationTestService {

    /**
     * 7.1 上层无事务，创建新事务
     */
    @GetMapping(value = "testNoTx_Nested_NestedEx")
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
    @GetMapping(value = "testTx_Nested_NestedEx")
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
    @GetMapping(value = "testTx_Nested_NestedExTry")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Nested_NestedExTry() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        System.out.println("comRes = " + comRes);
        try {
            boolean cusRes = propagationService.addCustomersException(buildCustomers());
            System.out.println("cusRes = " + cusRes);
        } catch (Exception ignored) {
            // 顾客事务发生异常回滚，虽然异常不暴露给上层，但仍然被spring事务管理器检测到了。
            // 上层事务执行成功，则商品插入成功，顾客插入失败！（测试成功！）
        }
    }

    /**
     * 7.4 上层有事务，外围事务方法异常
     */
    @GetMapping(value = "testTxEx_Nested_Nested")
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
