package cn.spring.learning.tx.service.impl;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/4 15:41
 */
@SpringBootTest
@ActiveProfiles(value = "home")
public class AccountServiceImplTest {

    private AccountService accountService;

    @Test
    @DisplayName(value = "测试调用当前类的代理方法")
    public void testUpsertAccountByNameUnique() {
        Account account = buildAopCurProxyAccount();
        this.accountService.upsertAccountByNameUnique(account);
    }

    private Account buildAopCurProxyAccount() {
        return Account.builder()
                .name("ljh")
                .age(26)
                .balance(BigDecimal.valueOf(4L))
                .build();
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}