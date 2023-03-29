package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.BasePropagationTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 4. 测试RequiresNew：<p>&emsp;
 * 一律创建新事务。与外层事务无关。
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:49
 */
@Service
@RequestMapping(value = "requiresNew")
public class RequiresNewTestService extends BasePropagationTestService {

    /**
     * 4.1 上层无事务，创建独立事务，事务间不受影响
     */
    @GetMapping(value = "testNoTx_RequiresNew_RequiresNewEx")
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
    @GetMapping(value = "testTx_RequiresNew_RequiresNewEx")
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
    @GetMapping(value = "testTxEx_RequiresNew_RequiresNew")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxEx_RequiresNew_RequiresNew() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 外围方法异常，但内层方法不属于外围方法的事务，所以商品和顾客仍然插入成功！（测试成功）
    }
}
