package cn.demo.springlearning.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * JDBC模板使用演示
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/1 22:36
 */
@Component
public class MyJdbcTemplateTest extends MyApplicationContext {

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Integer getTotalCount() {
        String sql = "select count(*) from usr";
        return template.queryForObject(sql, int.class);
    }

    @Test
    public void testJdbcTemplate() {
        MyJdbcTemplateTest demo = (MyJdbcTemplateTest) CONTEXT.getBean("myJdbcTemplateTest");
        Integer totalCount = demo.getTotalCount();
        System.out.println("totalCount = " + totalCount);
    }
}
