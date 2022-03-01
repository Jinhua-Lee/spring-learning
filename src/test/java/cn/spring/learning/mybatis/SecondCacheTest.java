package cn.spring.learning.mybatis;

import cn.spring.learning.tx.entity.Account;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * 二级缓存的测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/3/2 2:31
 */
public class SecondCacheTest {

    private Configuration configuration;

    @Before
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(
                this.getClass().getResourceAsStream("/mybatis/SqlMapConfig.xml")
        );
        this.configuration = sqlSessionFactory.getConfiguration();
    }

    @Test
    public void cacheTest1() {
        Cache cache = configuration.getCache("cn.spring.learning.tx.mapper.TxDemoMapper");
        Account account = Account.builder()
                .name("testBatch")
                .age(11)
                .balance(BigDecimal.ZERO)
                .build();
        cache.putObject("key1", account);
        cache.getObject("key1");
    }

}
