package com.yu.init.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class FilterHandle implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取访问 ip 地址
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        System.out.println("前端的访问路径 = " + requestURI);
        String visitIp = req.getRemoteAddr();
        // 每次拦截到请求输出访问 ip
        System.out.println("访问 IP = " + visitIp);
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
