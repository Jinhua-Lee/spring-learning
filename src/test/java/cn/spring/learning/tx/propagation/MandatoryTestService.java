package cn.spring.learning.tx.propagation;

import cn.spring.learning.tx.BasePropagationTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 3. 测试Mandatory：<p>&emsp;
 * 1) 调用方法存在事务，就加入该事务；<p>&emsp;
 * 2) 调用方法不存在事务，就抛出异常，不执行。<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:46
 */
@Service
@RequestMapping(value = "/mandatory")
public class MandatoryTestService extends BasePropagationTestService {

    /**
     * 3.1 调用方法不存在事务，就抛出异常，不执行
     */
    @GetMapping(value = "/testNoTx_Mandatory_Mandatory")
    public void testNoTx_Mandatory_Mandatory() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 调用方法未开启事务，不执行，两批数据都插入失败！（测试通过）
        // 直接抛出异常 org.springframework.transaction.IllegalTransactionStateException:
        // No existing transaction found for transaction marked with propagation 'mandatory'
    }

    /**
     * 3.2 调用方法存在事务，就加入该事务；<p>&emsp;
     */
    @GetMapping(value = "testTx_Mandatory_MandatoryEx")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Mandatory_MandatoryEx() {
        // 该测试方法开启事务，加入事务
        // 第二个事务方法中有异常，但所有事务都加入外围事务，外围事务回滚，所有数据插入失败！(测试成功)
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
    }
}
