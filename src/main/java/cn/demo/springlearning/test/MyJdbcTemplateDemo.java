package cn.demo.springlearning.test;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
public class MyJdbcTemplateDemo {

    private JdbcTemplate template;

    private static final ApplicationContext CONTEXT;

    static {
        CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Integer getTotalCount() {
        String sql = "select count(*) from usr";
        return template.queryForObject(sql, int.class);
    }

    public static void main(String[] args) {
        MyJdbcTemplateDemo demo = (MyJdbcTemplateDemo) CONTEXT.getBean("myJdbcTemplateDemo");
        Integer totalCount = demo.getTotalCount();
        System.out.println("totalCount = " + totalCount);
    }
}
