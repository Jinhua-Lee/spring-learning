package cn.spring.learning.tx;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.util.PropertiesResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * 测试执行器，不需要依赖Spring的容器
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/2/27 22:06
 */

@RunWith(SpringRunner.class)
public class ExecutorTest {

    private Configuration configuration;
    private JdbcTransaction jdbcTransaction;

    //    @Autowired
    private ObjectMapper objectMapper;

    @Before
    @SneakyThrows
    public void init() {
        SqlSessionFactoryBuilder ssfBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssFactory = ssfBuilder.build(
                this.getClass().getResourceAsStream("/mybatis/SqlMapConfig.xml")
        );
        configuration = ssFactory.getConfiguration();
        Connection connection = DriverManager.getConnection(
                PropertiesResolver.getValue("jdbc.url"),
                PropertiesResolver.getValue("jdbc.user"),
                PropertiesResolver.getValue("jdbc.password"));
        jdbcTransaction = new JdbcTransaction(connection);
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 简单执行器测试
     */
    @Test
    @SneakyThrows
    public void testBaseExecutor() {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement getBalanceById =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.TxDemoMapper.getBalanceById");
        List<Account> accounts = executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        // 第二次执行，观察预编译次数 -> 每次执行doQuery都会预编译
        executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        for (Account acc : accounts) {
            System.err.println(objectMapper.writeValueAsString(acc));
        }
    }

    @Test
    @SneakyThrows
    public void testReuseExecutor() {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement getBalanceById =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.TxDemoMapper.getBalanceById");
        List<Account> accounts = executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        // 第二次执行，观察预编译次数 -> 只会预编译一次
        executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        for (Account acc : accounts) {
            System.err.println(objectMapper.writeValueAsString(acc));
        }
    }

    @Test
    @SneakyThrows
    public void testBatchExecutor() {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement getBalanceById =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.TxDemoMapper.getBalanceById");

        Account account = Account.builder()
                .name("testBatch")
                .age(11)
                .balance(BigDecimal.ZERO)
                .build();

        List<Account> accounts = executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        // 第二次执行，观察预编译次数 -> 只会预编译一次
        executor.doQuery(getBalanceById, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, getBalanceById.getBoundSql(1)
        );
        for (Account acc : accounts) {
            System.err.println(objectMapper.writeValueAsString(acc));
        }
    }
}
