package cn.spring.learning.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据源配置
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/8/23 22:34
 */
@Configuration
@MapperScan(
        basePackages = {"cn.spring.learning.tx.mapper"},
        sqlSessionFactoryRef = DataSourceConfig.DATASOURCE_SESSION_FACTORY
)
public class DataSourceConfig {

    private final static String DATASOURCE_NAME = "learning";
    private final static String DATASOURCE_PREFIX = "spring.datasource.druid." + DATASOURCE_NAME;
    final static String DATASOURCE_SESSION_FACTORY = DATASOURCE_NAME + "SqlSessionFactory";
    public final static String DATASOURCE_TRANSACTION_MANAGER = DATASOURCE_NAME + "TransactionManager";
    private final static String DATASOURCE_SESSION_TEMPLATE = DATASOURCE_NAME + "SqlSessionTemplate";

    @Bean(name = DATASOURCE_NAME)
    @ConfigurationProperties(prefix = DATASOURCE_PREFIX)
    @SneakyThrows
    @Primary
    public DataSource dateSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setFilters("wall,slf4j");
        dataSource.setFailFast(true);
        return dataSource;
    }

    @Bean(name = DATASOURCE_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATASOURCE_NAME) DataSource dataSource,
                                               PageInterceptor pageInterceptor) throws Exception {
        // mybatis-plus，BaseMapper完成基础的实现，需要替换为该会话工厂
        final MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/**.xml"));
        sessionFactoryBean.setPlugins(pageInterceptor);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 注意这里识别属性都是字符串。
        properties.put("offsetAsPageNum", "false");
        properties.put("rowBoundsWithCount", "false");
        properties.put("pageSizeZero", "false");
        properties.put("reasonable", "false");
        properties.put("supportMethodsArguments", "false");
        properties.put("returnPageInfo", "false");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean(name = DATASOURCE_TRANSACTION_MANAGER)
    public DataSourceTransactionManager transactionManager(@Qualifier(DATASOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = DATASOURCE_SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(DATASOURCE_SESSION_FACTORY)
                                                         SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
