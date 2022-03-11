package cn.spring.learning.tx;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.mapper.TxDemoMapper;
import cn.spring.learning.tx.service.TxDemoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 事务测试类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:18
 */
@SpringBootTest(classes = TxApplication.class)
@ActiveProfiles(profiles = "company")
@RunWith(SpringRunner.class)
@Transactional
public class TxTest {

    private TxDemoService txDemoService;
    private TxDemoMapper txDemoMapper;

    @Test
    public void testDefaultMethod() {
        Account account = txDemoMapper.getNonQueryAccount();
        System.out.println("account = " + account);
    }

    @Test
    public void testTxUpsert() {
        Account account = Account.builder()
                .name("李金华")
                .age(24)
                .balance(BigDecimal.valueOf(2))
                .build();
        boolean success = this.txDemoService.updateBalance(account);
        System.out.println("success = " + success);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = this.txDemoMapper.getAllAccounts();
        accounts.forEach(System.out::println);
    }

    @Test
    public void  testGetById() {
        List<Account> accounts = this.txDemoMapper.getBalanceById(1);
        accounts.forEach(System.out::println);
    }

    @Test
    public void testPageQuery() {
        PageHelper.startPage(1, 1);
        List<Account> accountsByPage = txDemoMapper.getAccountsByPage();
        PageInfo<Account> pageInfo = new PageInfo<>(accountsByPage);

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
        this.txDemoService.transfer(from, to, amount);
    }

    @Autowired
    public void setTxDemoService(TxDemoService txDemoService) {
        this.txDemoService = txDemoService;
    }

    @Autowired
    public void setTxDemoMapper(TxDemoMapper txDemoMapper) {
        this.txDemoMapper = txDemoMapper;
    }
}
