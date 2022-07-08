package com.pdx.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-08 12:04
 * @Description
 */
@Component
@Slf4j
public class LogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //打印请求信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("---------LogFilter is Start---------");
        log.info("请求地址：{}{}",request.getRequestURL().toString(),request.getMethod());
        log.info("远程地址：{}",request.getRemoteAddr());

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("-----------------LogFilter is End 耗时：{} ms-----------------",System.currentTimeMillis() - startTime);
    }

    @Override
    public void destroy() {

    }
}
