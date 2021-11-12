package cn.demo.springlearning.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * JDBC模板使用演示
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/1 22:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyJdbcTemplateTest extends MyApplicationContext {

    private JdbcTemplate template;

    public Integer getTotalCount() {
        String sql = "select count(*) from usr";
        return template.queryForObject(sql, int.class);
    }

    @Test
    public void testJdbcTemplate() {
        MyJdbcTemplateTest demo = (MyJdbcTemplateTest) context.getBean("myJdbcTemplateTest");
        Integer totalCount = demo.getTotalCount();
        System.out.println("totalCount = " + totalCount);
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
