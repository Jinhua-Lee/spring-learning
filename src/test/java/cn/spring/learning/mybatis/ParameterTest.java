package cn.spring.learning.mybatis;

import cn.spring.learning.tx.entity.Account;
import cn.spring.learning.tx.mapper.TxDemoMapper;
import cn.spring.learning.util.PropertiesResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/10 17:11
 */
@Slf4j
public class ParameterTest {

    private SqlSession sqlSession;
    private TxDemoMapper txDemoMapper;
    private ObjectMapper objectMapper;

    @BeforeEach
    @SneakyThrows
    public void init() {
        SqlSessionFactoryBuilder ssfBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssFactory = ssfBuilder.build(
                this.getClass().getResourceAsStream("/mybatis/SqlMapConfig.xml")
        );
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.sqlSession = ssFactory.openSession();
        this.txDemoMapper = this.sqlSession.getMapper(TxDemoMapper.class);
    }

    @AfterEach
    public void destroy() {
        this.sqlSession.close();
    }

    @Test
    @DisplayName(value = "测试接口方法单个参数")
    @SneakyThrows
    public void testSingleParam() {
        List<Account> balanceById = this.txDemoMapper.getBalanceById(1);
        for (Account account : balanceById) {
            log.debug("{}", objectMapper.writeValueAsString(account));
        }
    }

    @Test
    @DisplayName(value = "测试Map入参作为MetaObject")
    @SneakyThrows
    public void testMetaObject() {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", 1);
        argMap.put("name", "jh");
        List<Account> accounts = this.txDemoMapper.getAccountByArgMap(argMap);
        for (Account account : accounts) {
            log.debug("{}", objectMapper.writeValueAsString(account));
        }
    }

    @Test
    @DisplayName(value = "普通参数和占位参数的处理")
    @SneakyThrows
    public void testParamHandle() {
        List<Account> accounts = this.txDemoMapper.getAccountByIdAndNamePart(1, "jh");
        for (Account account : accounts) {
            log.debug("{}", objectMapper.writeValueAsString(account));
        }
    }
}
