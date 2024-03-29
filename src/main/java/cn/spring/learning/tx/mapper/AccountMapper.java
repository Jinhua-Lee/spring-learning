package cn.spring.learning.tx.mapper;

import cn.spring.learning.tx.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
public interface AccountMapper extends BaseMapper<Account> {

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
     * 新增用户并初始化余额，或更新用户让其增加指定的余额
     *
     * @param account 账户
     * @return 添加结果
     */
    int insertAccountOrIncreaseBalance(Account account);

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
     * @param accountId 指定账户
     * @return 带余额的
     */
    List<Account> getBalanceById(Integer accountId);

    /**
     * 根据名字增加账户余额
     *
     * @param account 账户
     * @return 受影响的行数
     */
    int increaseBalanceByName(Account account);

    /**
     * 【用于测试一级缓存的相同SQL的不同方法】<p>
     * 查询指定ID账户的余额
     *
     * @param accountId 指定账户
     * @return 带余额的
     */
    List<Account> getBalanceById2(Integer accountId);

    /**
     * 更新指定ID账户的余额
     *
     * @param accountId 账户ID
     * @param balance   更新到的余额
     * @return 受影响的行数
     */
    int increaseBalance(Integer accountId, BigDecimal balance);

    /**
     * 查询所有账户<p>
     * 支持插件分页
     *
     * @return 所有账户
     */
    List<Account> getAllAccounts();

    /**
     * 根据ID和姓名片段来进行查询
     *
     * @param accountId 账户ID
     * @param namePart  姓名片段
     * @return 账户列表
     */
    List<Account> getAccountByIdAndNamePart(@Param(value = "id") Integer accountId,
                                            @Param(value = "name") String namePart);

    /**
     * 根据入参Map来查询对象
     *
     * @param argMap 入参Map
     * @return 查询出的账户
     */
    List<Account> getAccountByArgMap(@Param(value = "argMap") Map<String, Object> argMap);
}
