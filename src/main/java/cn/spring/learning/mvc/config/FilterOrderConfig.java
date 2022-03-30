package cn.spring.learning.mvc.config;

import cn.spring.learning.mvc.web.filter.EncodingFilter;
import cn.spring.learning.mvc.web.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 10:33
 */
@Configuration
public class FilterOrderConfig {

    @Bean
    public FilterRegistrationBean<Filter> sessionFilter() {
        FilterRegistrationBean<Filter> sessionFilterBean = new FilterRegistrationBean<>();
        sessionFilterBean.setFilter(new SessionFilter());
        sessionFilterBean.setOrder(1);
        sessionFilterBean.addUrlPatterns("/*");
        return sessionFilterBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> encodingFilterBean() {
        FilterRegistrationBean<Filter> encodingFilterBean = new FilterRegistrationBean<>();
        encodingFilterBean.setFilter(new EncodingFilter());
        encodingFilterBean.setOrder(2);
        encodingFilterBean.addUrlPatterns("/*");
        return encodingFilterBean;
    }
}
