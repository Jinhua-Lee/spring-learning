package cn.spring.learning.mybatis;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * 一级缓存测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/3/1 1:21
 */
public class FirstCacheTest {

    private SqlSession session;

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(
                this.getClass().getResourceAsStream("/mybatis/SqlMapConfig.xml")
        );
        this.session = sqlSessionFactory.openSession();
    }

    @Test
    public void test1() {
        AccountMapper mapper = session.getMapper(AccountMapper.class);
        // 能命中缓存：
        //      1. 同一session，
        //      2. 同一sql，
        //      3. 同一查询条件（包括分页），
        //      4. 同一StatementId

        List<Account> firstList = mapper.getBalanceById(1);
        List<Account> secondList = mapper.getBalanceById(1);

        // 如果第一个元素相等，则说明命中了一级缓存
        Assert.assertNotNull(firstList);
        Assert.assertNotNull(secondList);
        Assert.assertSame(firstList.get(0), secondList.get(0));
    }

    @Test
    public void test2() {
        // 不能命中缓存：
        //      1. mapper中select标签添加一个参数 flushCache = true，每次清空缓存；
        //      2. 中间调用了一个flushCache = true的sql；
        //      3. 执行了update
        //      4. 缓存作用域Scope != Statement，配置文件统一更改
        AccountMapper mapper = session.getMapper(AccountMapper.class);

        List<Account> firstList = mapper.getBalanceById(1);

        // test1：中间调用了一个flushCache = true的sql；
//        mapper.getBalanceById2(1);
        // test2：执行了update
        mapper.updateBalance(7, BigDecimal.valueOf(6000));

        List<Account> secondList = mapper.getBalanceById(1);

        Assert.assertNotNull(firstList);
        Assert.assertNotNull(secondList);
        Assert.assertNotSame(firstList.get(0), secondList.get(0));
    }
}
