package cn.spring.learning.tx.mapper;

import cn.spring.learning.tx.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 事务模拟的数据访问类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 21:36
 */
@Mapper
public interface TxDemoMapper {

    /**
     * 添加用户
     *
     * @param account 账户
     * @return 添加结果
     */
    int upsertBalance(Account account);

    /**
     * 查询指定ID账户的余额
     *
     * @param from 指定账户
     * @return 带余额的
     */
    List<Account> getBalanceById(Account from);

    /**
     * 更新指定ID账户的余额
     *
     * @param from 更新余额
     * @return 受影响的行数
     */
    int updateBalance(Account from);

    /**
     * 查询所有账户
     *
     * @return 所有账户
     */
    List<Account> getAllAccounts();

    /**
     * 分页查询账户
     *
     * @return 分页查询账户
     */
    List<Account> getAccountsByPage();
}
