package cn.spring.learning.tx.service.impl;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.ex.AccountException;
import cn.spring.learning.tx.mapper.AccountMapper;
import cn.spring.learning.tx.service.AccountService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户业务过程类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/11/30 19:39
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountMapper accountMapper;

    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void createAccount(Account account) {
        try {
            int insertNum = this.accountMapper.addAccount(account);
            if (insertNum != 1) {
                throw new AccountException("创建账户异常.");
            }
        } catch (Exception ex) {
            // 翻译为业务异常
            throw new AccountException("创建账户异常.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void updateAccount(Account account) {
        // 当前事务过程要被复用时候，需要将已有的REQUIRED传播行为改为NESTED
        try {
            int updateRowNum = this.accountMapper.increaseBalanceByName(account);
            if (updateRowNum != 1) {
                throw new AccountException("更新账户异常.");
            }
        } catch (Exception sex) {
            // 翻译为业务异常
            throw new AccountException("更新账户异常.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void upsertAccountByNameUnique(Account account) {
        // 调用当前类的方法，并使得事务不失效，则需要拿到当前对象的代理对象
        AccountService curProxy = (AccountService) AopContext.currentProxy();
        try {
            curProxy.createAccount(account);
        } catch (AccountException aex) {
            // 翻译为业务异常
            curProxy.updateAccount(account);
        }
    }

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }
}
