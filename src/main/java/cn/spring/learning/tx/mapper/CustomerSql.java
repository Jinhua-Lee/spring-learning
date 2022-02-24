package cn.spring.learning.tx.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 客户查询SQL类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/23 21:18
 */
public class CustomerSql {

    public String getCustomersByNameAndAge(@Param("name") String namePart, @Param("age") Integer age) {
        return new SQL() {
            {
                SELECT("*");
                FROM("customer");
                if (namePart != null) {
                    WHERE("name like '%${name}%'");
                }
                if (age != null) {
                    AND().WHERE("age = #{age}");
                }
            }
        }.toString();
    }
}
