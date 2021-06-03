package cn.demo.springlearning.test.tx;

import cn.demo.springlearning.test.entity.Account;
import org.apache.ibatis.annotations.Mapper;

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
}
