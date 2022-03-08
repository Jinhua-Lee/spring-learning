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
    @DisplayName(value = "测试PreparedStatement")
    public void testPreparedStatement() {
        String preSql = "select * from account where id = ?";
        printMap(SingleJdbcConnectionUtil.executePstQuery(preSql));
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
