package cn.spring.learning.mvc.web.filter;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 9:34
 */
@Slf4j
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        log.info("===== session filter start =====");
        log.info("remote address = {}", request.getRemoteAddr());
        log.info("remote host = {}", request.getRemoteHost());
        log.info("remote port = {}", request.getRemotePort());
        log.info("===== session filter end =====");
        chain.doFilter(req, resp);
    }
}
