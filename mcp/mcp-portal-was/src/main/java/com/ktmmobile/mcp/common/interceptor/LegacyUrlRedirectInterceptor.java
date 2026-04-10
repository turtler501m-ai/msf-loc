package com.ktmmobile.mcp.common.interceptor;

import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LegacyUrlRedirectInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LegacyUrlRedirectInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (isAjax) {
            return true;
        }

        int existDo = request.getRequestURI().lastIndexOf(".do");
        if (existDo < 0) {
            return true;
        }

        String uri = request.getRequestURI();
        List<NmcpCdDtlDto> legacyUrlList = NmcpServiceUtils.getCodeList("LegacyUrlRedirectList");
        for (NmcpCdDtlDto legacyUrl : legacyUrlList) {
            if (uri.equals(legacyUrl.getExpnsnStrVal1())) {
                response.sendRedirect(legacyUrl.getExpnsnStrVal2());
                return false;
            }
        }

        return true;
    }
}
