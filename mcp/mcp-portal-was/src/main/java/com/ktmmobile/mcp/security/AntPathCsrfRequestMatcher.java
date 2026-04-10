package com.ktmmobile.mcp.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AntPathCsrfRequestMatcher implements RequestMatcher {
    private final List<RequestMatcher> protectedMatchers = new ArrayList<>();
    private final List<RequestMatcher> excludeMatchers   = new ArrayList<>();

    public void setProtectedPatterns(List<String> patterns) {
        for (String p : patterns) protectedMatchers.add(new AntPathRequestMatcher(p));
    }
    public void setExcludePatterns(List<String> patterns) {
        for (String p : patterns) excludeMatchers.add(new AntPathRequestMatcher(p));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String m = request.getMethod();
        if ("GET".equals(m) || "HEAD".equals(m) || "TRACE".equals(m) || "OPTIONS".equals(m)) return false;
        for (RequestMatcher ex : excludeMatchers)   if (ex.matches(request)) return false;
        for (RequestMatcher pr : protectedMatchers) if (pr.matches(request)) return true;
        return false; // 지정한 보호 경로 외에는 검사 안 함
    }
}
