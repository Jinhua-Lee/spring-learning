package cn.spring.learning.tx.service;

import cn.spring.learning.tx.entity.Account;

import java.math.BigDecimal;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/15 18:12
 */
public interface AccountTransferService {

    /**
     * 更新账户的余额
     *
     * @param account 账户及应该更新到的余额
     */
    void increaseBalance(Account account);

    /**
     * 转账
     *
     * @param from   转出的账户
     * @param to     转入的账户
     * @param amount 转账的金额
     */
    void transfer(Account from, Account to, BigDecimal amount);
}
