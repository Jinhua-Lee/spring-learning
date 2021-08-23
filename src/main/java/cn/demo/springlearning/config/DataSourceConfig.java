package cn.demo.springlearning.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * 数据源配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/8/23 22:34
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dateSource() {
        DruidDataSource dataSource = new DruidDataSource();
        ResourceBundle bundle = ResourceBundle.getBundle("mysql");
        dataSource.setDriverClassName(bundle.getString("jdbc.driver"));
        dataSource.setUrl(bundle.getString("jdbc.url"));
        dataSource.setUsername(bundle.getString("jdbc.user"));
        dataSource.setPassword(bundle.getString("jdbc.password"));
        return dataSource;
    }
}
