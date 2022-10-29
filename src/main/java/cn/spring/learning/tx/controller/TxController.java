package cn.spring.learning.tx.controller;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.service.AccountTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 19:33
 */
@RestController
public class TxController {

    private AccountTransferService accountTransferService;

    @GetMapping(value = "/transfer")
    public boolean transfer() {
        Account from = new Account(2, null, null, null);
        Account to = new Account(1, null, null, null);
        BigDecimal amount = BigDecimal.valueOf(3000);
        accountTransferService.transfer(from, to, amount);
        return true;
    }

    @Autowired
    public void setAccountTransferService(AccountTransferService accountTransferService) {
        this.accountTransferService = accountTransferService;
    }
}
