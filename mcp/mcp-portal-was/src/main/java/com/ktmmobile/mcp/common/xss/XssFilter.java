package com.ktmmobile.mcp.common.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * @Class Name : XssFilter
 * @Description : servlet filter 클래스
 *
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
public class XssFilter implements Filter {

    @Deprecated
	private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);

    public FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
    }



}