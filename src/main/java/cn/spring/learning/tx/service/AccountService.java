package cn.spring.learning.tx.service;

import cn.spring.learning.tx.entity.Account;

/**
 * 账户服务
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/11/30 19:37
 */
public interface AccountService {

    /**
     * 创建账户
     *
     * @param account 待创建的账户
     */
    void createAccount(Account account);

    /**
     * 更新指定账户的信息
     *
     * @param account 账户信息
     */
    void updateAccount(Account account);

    /**
     * 创建账户或更新账户信息，根据名字判断是否有冲突
     *
     * @param account 账户信息
     */
    void upsertAccountByNameUnique(Account account);
}
