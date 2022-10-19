package cn.spring.learning.tx;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.mapper.AccountMapper;
import cn.spring.learning.tx.service.AccountTransferService;
import cn.spring.learning.tx.service.impl.AccountTransferServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(profiles = "home")
@Transactional
public class TxTest {

    private AccountTransferService accountTransferService;
    private AccountMapper accountMapper;

    @Test
    @DisplayName(value = "Java8接口默认方法")
    public void testDefaultMethod() {
        Account account = accountMapper.getNonQueryAccount();
        System.out.println("account = " + account);
    }

    @Test
    @DisplayName(value = "插入或更新")
    public void testTxUpsert() {
        Account account = Account.builder()
                .id(7)
                .name("李金华")
                .age(24)
                .balance(BigDecimal.valueOf(2))
                .build();
        this.accountTransferService.updateBalance(account);
        System.out.println("account.getId() = " + account.getId());
    }

    @Test
    @DisplayName(value = "获取所有账户")
    public void testGetAllAccounts() {
        List<Account> accounts = this.accountMapper.getAllAccounts();
        accounts.forEach(System.out::println);
    }

    @Test
    @DisplayName(value = "测试根据mybatis-plus的条件构造进行查询")
    public void testGetAccountByIdAndName() {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>(
                Account.builder().build()
        );
        List<Account> accounts = accountMapper.selectList(accountQueryWrapper);
        accounts.forEach(System.out::println);
    }

    @Test
    @DisplayName(value = "根据账户ID获取")
    public void testGetById() {
        List<Account> accounts = this.accountMapper.getBalanceById(1);
        accounts.forEach(System.out::println);
    }

    @Test
    public void testAddAccReturnsId() {
        Account account = Account.builder()
                .name("测试selectKey返回自增主键ID")
                .age(11)
                .balance(BigDecimal.ZERO)
                .build();
        int insert = this.accountMapper.insert(account);
        System.out.println("insert = " + insert);
        System.out.println("account.getId() = " + account.getId());
    }

    @Test
    public void testPageQuery() {
        PageHelper.startPage(1, 1);
        List<Account> accountsByPage = accountMapper.getAllAccounts();
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
        Account from = new Account(2, null, null, null);
        Account to = new Account(1, null, null, null);
        BigDecimal amount = BigDecimal.valueOf(3000);
        this.accountTransferService.transfer(from, to, amount);
    }

    @Autowired
    public void setTxDemoService(AccountTransferServiceImpl accountTransferServiceImpl) {
        this.accountTransferService = accountTransferServiceImpl;
    }

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }
}
