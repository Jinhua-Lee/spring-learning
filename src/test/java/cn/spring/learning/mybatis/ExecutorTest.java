package cn.spring.learning.mybatis;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
public class ExecutorTest {

    private Configuration configuration;
    private JdbcTransaction jdbcTransaction;

    //    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
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

    @Test
    @DisplayName(value = "简单执行器测试")
    @SneakyThrows
    public void testSimpleExecutor() {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement getBalanceById =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.AccountMapper.getBalanceById");
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
    @DisplayName(value = "可重用执行器测试")
    @SneakyThrows
    public void testReuseExecutor() {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement getBalanceById =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.AccountMapper.getBalanceById");
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
    @DisplayName(value = "批量执行器测试")
    @SneakyThrows
    public void testBatchExecutor() {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement addAccount =
                configuration.getMappedStatement("cn.spring.learning.tx.mapper.AccountMapper.addAccount");

        Account account = Account.builder()
                .name("testBatch")
                .age(11)
                .balance(BigDecimal.ZERO)
                .build();

        executor.doUpdate(addAccount, account);
        executor.doUpdate(addAccount, account);
        executor.doUpdate(addAccount, account);
        // 必须执行刷新，才会将所有的语句提交到数据库。
//        executor.doFlushStatements(false);
    }
}
