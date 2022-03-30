package cn.spring.learning.mvc.config;

import cn.spring.learning.mvc.web.filter.EncodingFilter;
import cn.spring.learning.mvc.web.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 过滤器顺序测试：<p>&emsp;
 * 1. 原有WebFilter注解是按类名或filter名排序；<p>&emsp;
 * 2. 如果在SpringBoot下，必须去除原有注解，通过注册Bean来完成排序。<p>
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 10:33
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> sessionFilterBean() {
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
