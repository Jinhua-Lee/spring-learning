package cn.spring.learning.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 单连接数据库工具，供简单测试用
 *
 * @author Jinhua
 */
@Slf4j
public class SingleJdbcConnectionUtil {
    /**
     * 四大参数
     */
    private static final String DRIVER = PropertiesResolver.getValue("jdbc.driver");
    private static final String URL = PropertiesResolver.getValue("jdbc.url");
    private static final String USER = PropertiesResolver.getValue("jdbc.user");
    private static final String PASSWORD = PropertiesResolver.getValue("jdbc.password");

    private static final ThreadLocal<Connection> THREAD_LOCAL;

    static {
        THREAD_LOCAL = new ThreadLocal<>();
        try {
            Class.forName(DRIVER);
            log.info("驱动注册成功~");
        } catch (ClassNotFoundException e) {
            log.error("驱动注册失败！{}", (Object) e.getStackTrace());
        }
    }

    private static Connection getConnection() {
        Connection conn = THREAD_LOCAL.get();
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                THREAD_LOCAL.set(conn);
                log.info("连接数据库成功~");
            } catch (SQLException e) {
                log.error("连接数据库失败！{}", (Object) e.getStackTrace());
            }
        }
        return conn;
    }

    private static void closeConnection() {
        Connection conn = THREAD_LOCAL.get();
        try {
            if (conn != null) {
                if (!conn.isClosed()) {
                    conn.close();
                    THREAD_LOCAL.remove();
                }
            }
        } catch (SQLException e) {
            log.error("{}", (Object) e.getStackTrace());
        }
    }

    public static int executeStmtUpdate(String sql) {
        Statement pst = null;
        int row = 0;
        try {
            pst = getConnection().createStatement();
            // 执行SQL语句，返回受影响的行数
            return pst.executeUpdate(sql);
        } catch (Exception e) {
            log.error("{}", (Object) e.getStackTrace());
        } finally {
            try {
                if (pst != null && !pst.isClosed()) {
                    pst.close();
                }
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    public static int executePstUpdate(String sql, Object... parameters) {
        PreparedStatement pst = null;
        int row = 0;
        // 获取连接对象
        Connection conn = getConnection();
        try {
            pst = conn.prepareStatement(sql);
            if (parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    // 设置参数，下标从1开始
                    pst.setObject(i + 1, parameters[i]);
                }
            }
            // 执行SQL语句，返回受影响的行数
            row = pst.executeUpdate();
        } catch (Exception e) {
            log.error("{}", (Object) e.getStackTrace());
        } finally {
            try {
                if (pst != null && !pst.isClosed()) {
                    pst.close();
                }
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    public static List<Map<String, Object>> executeStmtQuery(String sql) {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return parseRes(rs);
        } catch (SQLException e) {
            log.error("{}", (Object) e.getStackTrace());
            return new ArrayList<>();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    stmt.close();
                    closeConnection();
                } catch (SQLException e) {
                    log.error("{}", (Object) e.getStackTrace());
                }
            }
        }
    }

    public static List<Map<String, Object>> executePstQuery(String sql, Object... parameters) {
        Connection conn = getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            // 判断是否需要设置参数
            if (parameters.length > 0) {
                for (int i = 0; i < parameters.length; i++) {
                    // 设置占位符对应的参数
                    pst.setObject(i + 1, parameters[i]);
                }
            }
            // 通过编译对象执行SQL指令
            rs = pst.executeQuery();
            return parseRes(rs);
        } catch (SQLException e) {
            log.error("{}", (Object) e.getStackTrace());
            return new ArrayList<>();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    pst.close();
                    closeConnection();
                } catch (SQLException e) {
                    log.error("{}", (Object) e.getStackTrace());
                }
            }
        }
    }

    private static List<Map<String, Object>> parseRes(ResultSet rs) throws SQLException {
        List<Map<String, Object>> table = new ArrayList<>();
        if (rs == null) {
            return new ArrayList<>();
        }
        // 获取结果集的元数据
        ResultSetMetaData rsd = rs.getMetaData();
        // 获取当前表的总列数
        int columnCount = rsd.getColumnCount();
        // 遍历结果集
        while (rs.next()) {
            // 创建存储当前行的集合对象
            Map<String, Object> row = new LinkedHashMap<>();
            // 遍历当前行每一列
            for (int i = 0; i < columnCount; i++) {
                // 获取列的编号获取列名
                String columnName = rsd.getColumnName(i + 1);
                // 通过列名获取当前遍历列的值
                Object columnValue = rs.getObject(columnName);
                // 列名和获取值作为 K 和 V 存入Map集合
                row.put(columnName, columnValue);
            }
            // 把每次遍历列的Map集合存储到List集合中
            table.add(row);
        }
        return table;
    }

    public static void main(String[] args) {
        String sql = "select * from account where id = 1";
        String pstSql = "select * from account where id = ?";
        printMap(SingleJdbcConnectionUtil.executeStmtQuery(sql));
        printMap(SingleJdbcConnectionUtil.executePstQuery(pstSql, 1));
    }

    private static void printMap(List<Map<String, Object>> table) {
        for (Map<String, Object> row : table) {
            ArrayList<String> rowNames = new ArrayList<>(row.keySet());
            for (String name : rowNames) {
                System.out.print(row.get(name) + "\t");
            }
            System.out.println();
        }
    }
}
