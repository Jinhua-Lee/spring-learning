package cn.demo.springlearning.test;

import cn.demo.springlearning.entity.Account;
import cn.demo.springlearning.mapper.TxDemoMapper;
import cn.demo.springlearning.service.TxDemoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * 事务测试类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:18
 */
public class TxTest extends MyApplicationContext {

    private final TxDemoService txService = (TxDemoService) CONTEXT.getBean("txDemoService");

    private final TxDemoMapper txDemoMapper = (TxDemoMapper) CONTEXT.getBean("txDemoMapper");

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
    public void testGetAllAccounts() {
        List<Account> accounts = this.txDemoMapper.getAllAccounts();
        accounts.forEach(System.out::println);
    }

    @Test
    public void testPageQuery() {
        PageHelper.startPage(1, 1);
        List<Account> accountsByPage = txDemoMapper.getAccountsByPage();
        PageInfo<Account> pageInfo =  new PageInfo<>(accountsByPage);

        List<Account> list = pageInfo.getList();
        list.forEach(System.out::println);

        System.out.println("总页数: " + pageInfo.getPages());
        System.out.println("总记录数: " + pageInfo.getTotal());
        System.out.println("当前页数: " + pageInfo.getPageNum());
        System.out.println("当前页面记录数量: " + pageInfo.getSize());
    }

    @Test
    public void testTransfer() {
        Account from = new Account(7, null, null, null);
        Account to = new Account(1, null, null, null);
        BigDecimal amount = BigDecimal.valueOf(3000);
        this.txService.transfer(from, to, amount);
    }
}
