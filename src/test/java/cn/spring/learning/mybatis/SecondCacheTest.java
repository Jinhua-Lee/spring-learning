package cn.spring.learning.mybatis;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.mapper.TxDemoMapper;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.mapping.CacheBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

/**
 * 二级缓存的测试
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/3/2 2:31
 */
public class SecondCacheTest {

    private Configuration configuration;
    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void init() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        this.sqlSessionFactory = factoryBuilder.build(
                this.getClass().getResourceAsStream("/mybatis/SqlMapConfig.xml")
        );
        this.configuration = this.sqlSessionFactory.getConfiguration();
    }

    @Test
    @DisplayName(value = "查看二级缓存对象的结构")
    public void testCacheStructure() {
        Cache cache = configuration.getCache("cn.spring.learning.tx.mapper.TxDemoMapper");
        // 缓存类必须实现Serializable接口
        Account account = Account.builder()
                .name("testBatch")
                .age(11)
                .balance(BigDecimal.ZERO)
                .build();
        cache.putObject("key1", account);
        cache.getObject("key1");
    }

    @Test
    @DisplayName(value = "测试二级缓存如何实现会话共享")
    public void testCacheProc() {
        // 开启两个会话：defaultSqlSessionFactory中是new出来的sqlSession
        SqlSession sessionA = sqlSessionFactory.openSession();
        SqlSession sessionB = sqlSessionFactory.openSession();

        TxDemoMapper mapperA = sessionA.getMapper(TxDemoMapper.class);
        // 调试它是否将查询结果放入二级缓存
        mapperA.getBalanceById(1);
        // 会话必须提交
        sessionA.commit();

        TxDemoMapper mapperB = sessionB.getMapper(TxDemoMapper.class);
        // 调试它是否从二级缓存中来（会的）
        mapperB.getBalanceById(1);
        sessionB.commit();
    }

    @Test
    @DisplayName(value = "测试是否会替换已有的缓存")
    public void testCacheReplace() {
        // 开启四个会话，分别查id = {1, 7, 1, 7}的账户，测试第二个查询是否会替换第一个查询的缓存
        SqlSession sessionA = sqlSessionFactory.openSession();
        SqlSession sessionB = sqlSessionFactory.openSession();
        SqlSession sessionC = sqlSessionFactory.openSession();
        SqlSession sessionD = sqlSessionFactory.openSession();

        TxDemoMapper mapperA = sessionA.getMapper(TxDemoMapper.class);
        mapperA.getBalanceById(1);
        // 会话必须提交
        sessionA.commit();

        TxDemoMapper mapperB = sessionB.getMapper(TxDemoMapper.class);
        // 不会覆盖sessionA放入的缓存,Cache接口其实维护的是一系列缓存，具体的实现类维护CacheKey -> Cache的Map
        mapperB.getBalanceById(7);
        sessionB.commit();

        TxDemoMapper mapperC = sessionC.getMapper(TxDemoMapper.class);
        // 命中sessionA的缓存
        mapperC.getBalanceById(1);
        sessionC.commit();

        TxDemoMapper mapperD = sessionD.getMapper(TxDemoMapper.class);
        // 命中sessionB的缓存
        mapperD.getBalanceById(7);
        sessionD.commit();
    }

}
