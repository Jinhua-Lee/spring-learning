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

    private final TxDemoService txService = (TxDemoService) CONTEXT.getBean("txDemoService");

    @Test
    public void testTx() {
        Account account = Account.builder()
                .name("李金华")
                .age(24)
                .balance(BigDecimal.valueOf(2))
                .build();
        boolean success = this.txService.updateBalance(account);
        System.out.println("success = " + success);
    }

    @Test
    public void testTransfer() {
        Account from = new Account(7, null, null, null);
        Account to = new Account(1, null, null, null);
        BigDecimal amount = BigDecimal.valueOf(3000);
        this.txService.transfer(from, to, amount);
    }
}
