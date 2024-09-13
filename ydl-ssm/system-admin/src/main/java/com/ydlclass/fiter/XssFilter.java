package com.ydlclass.fiter;

import com.ydlclass.core.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 这个过滤器的左右，仅仅是讲原始的request进行一个包装，通过子类去处理恶意标签
        filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest),servletResponse);
    }
}
