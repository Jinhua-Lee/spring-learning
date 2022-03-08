package cn.spring.learning.tx.mapper;

import cn.spring.learning.tx.entity.Account;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 事务模拟的数据访问类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 21:36
 */
@Mapper
// 此处需要注解扫描才开启CacheNamespace
//@CacheNamespace
public interface TxDemoMapper {

    /**
     * 测试Java8接口默认方法
     *
     * @return 账户
     */
    default Account getNonQueryAccount() {
        return Account.builder()
                .id(-1)
                .build();
    }

    /**
     * 添加用户
     *
     * @param account 账户
     * @return 添加结果
     */
    int upsertBalance(Account account);

    /**
     * 添加账户
     *
     * @param account 待添加账户
     * @return 受影响的行数
     */
    int addAccount(Account account);

    /**
     * 查询指定ID账户的余额
     *
     * @param id 指定账户
     * @return 带余额的
     */
    List<Account> getBalanceById(Integer id);

    /**
     * 【用于测试一级缓存的相同SQL的不同方法】<p>
     * 查询指定ID账户的余额
     *
     * @param id 指定账户
     * @return 带余额的
     */
    List<Account> getBalanceById2(Integer id);

    /**
     * 更新指定ID账户的余额
     *
     * @param accountId      账户ID
     * @param balance 更新到的余额
     * @return 受影响的行数
     */
    int updateBalance(Integer accountId, BigDecimal balance);

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
