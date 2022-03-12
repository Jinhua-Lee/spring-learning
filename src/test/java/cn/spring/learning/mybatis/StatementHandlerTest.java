package cn.spring.learning.mybatis;

import cn.spring.learning.util.PropertiesResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 语句处理器测试，通过执行器进入调试
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/8 10:07
 */
public class StatementHandlerTest {

    private Configuration configuration;
    private JdbcTransaction jdbcTransaction;
    private SimpleExecutor simpleExecutor;
    private MappedStatement ms;

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
        simpleExecutor = new SimpleExecutor(configuration, jdbcTransaction);
        ms = configuration.getMappedStatement("cn.spring.learning.tx.mapper.TxDemoMapper.getBalanceById");
    }


    @Test
    @DisplayName(value = "调试语句处理器的过程")
    @SneakyThrows
    public void test() {
        simpleExecutor.doQuery(ms, 1, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(1));
    }
}
