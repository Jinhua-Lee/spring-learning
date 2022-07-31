package cn.spring.learning.mybatis;

import cn.spring.learning.util.SingleJdbcConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 原生的JDBC测试
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/8 10:41
 */
@Slf4j
public class OriJdbcTest {

    @Test
    @DisplayName(value = "测试Statement")
    public void testStatement() {
        String sql = "select * from account where id = 1";
        printMap(SingleJdbcConnectionUtil.executeStmtQuery(sql));
    }

    @Test
    @DisplayName(value = "测试PreparedStatement查询语句")
    public void testPreparedStatement() {
        String preSql = "select * from account where id = ?";
        printMap(SingleJdbcConnectionUtil.executePstQuery(preSql, 1));
    }

    @Test
    @DisplayName(value = "测试Pst插入语句")
    public void testPreparedStatementUpsert() {
        String preSql = "        insert into account(name, age, balance)\n" +
                "        values (?, ?, ?)\n" +
                "        on duplicate key update balance = balance + ?;";
        int rowCount = SingleJdbcConnectionUtil.executePstUpdate(preSql, "李金华", 24, 2, 2);
        System.out.println("rowCount = " + rowCount);
    }

    private void printMap(List<Map<String, Object>> table) {
        for (Map<String, Object> row : table) {
            ArrayList<String> rowNames = new ArrayList<>(row.keySet());
            for (String name : rowNames) {
                log.info(row.get(name) + "\t");
            }
            log.info(System.lineSeparator());
        }
    }
}
