package cn.demo.springlearning.test;

import cn.demo.springlearning.test.entity.Account;
import cn.demo.springlearning.test.tx.TxDemoService;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * 事务测试类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:18
 */
public class TxTest extends MyApplicationContext {

    @Test
    public void testTx() {
        TxDemoService txService = (TxDemoService) CONTEXT.getBean("txDemoService");
        Account account = Account.builder()
                .name("李金华")
                .age(24)
                .balance(BigDecimal.valueOf(2))
                .build();
        boolean success = txService.updateBalance(account);
        System.out.println("success = " + success);
    }
}
