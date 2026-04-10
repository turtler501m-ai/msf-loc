    package com.ktmmobile.mcp.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OriginRefererFilter implements Filter {
    private String allowDomain; // ex) ktmmobile.com

    @Override
    public void init(FilterConfig filterConfig) {
        String param = filterConfig.getInitParameter("allowOrigin");
        // "https://*.ktmmobile.com" 형태로 입력하면 ktmmobile.com만 추출
        if (param != null && param.contains("*")) {
            allowDomain = param.substring(param.indexOf("*.") + 2); // ktmmobile.com
        } else if (param != null) {
            allowDomain = param.replace("https://", "").replace("http://", "");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String origin = req.getHeader("Origin");
        String referer = req.getHeader("Referer");

        boolean valid = false;

        // 1️⃣ Origin 우선 체크
        if (origin != null && origin.matches("^https://([a-zA-Z0-9.-]*\\.)?" + allowDomain + "(:[0-9]+)?$")) {
            valid = true;
        }
        // 2️⃣ Referer 보조 체크
        else if (referer != null && referer.matches("^https://([a-zA-Z0-9.-]*\\.)?" + allowDomain + "(:[0-9]+)?($|/)")) {
            valid = true;
        }

        if (!valid) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Invalid request origin");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // no-op: nothing to clean up
    }
}
