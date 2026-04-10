package com.ktmmobile.mcp.common.interceptor;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.WorkNotiDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;

public class FrontLoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FrontLoginInterceptor.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {

//            if(!"LOCAL".equals(serverName)) {
//            	Cookie[] cookies = request.getCookies();
//            	for (Cookie cookie : cookies) {
//            		if ("JSESSIONID".equals(cookie.getName())) {
//            			response.setHeader(org.springframework.http.HttpHeaders.SET_COOKIE, "JSESSIONID="+cookie.getValue()+";path=/;SameSite=None;Secure;HttpOnly;");
//            		}
//            	}
//            }

        	//URL 로그인필수여부
        	List<WorkNotiDto> workNotiList =  NmcpServiceUtils.getWorkNotiList("mustLogin");

        	boolean mustLogin = false;
        	if (workNotiList != null  && workNotiList.size() > 0) {
            	for (WorkNotiDto workNotiDto : workNotiList) {
            		if (request.getRequestURI().equals(StringUtil.NVL(workNotiDto.getUrl(), ""))) {
            			if("Y".equals(workNotiDto.getLoginMustYn())) {
            				 mustLogin = true;
            			}
            			break;
            		}
            	}
        	}

            if(!SessionUtils.hasLoginUserSessionBean() && mustLogin ){

                UserSessionDto usd = SessionUtils.getUserCookieBean();
                if(usd == null) { // cookie 로도 session 을 생성하지 못했으면 로그인 페이지로 분기한다.

                    String url = request.getServletPath();
                    String query = request.getQueryString();
                    // 패키지 경로 알아내기 : org.springframework.web.util.UrlPathHelper&nbsp;
                    UrlPathHelper urlPathHelper = new UrlPathHelper();
                    String originalURL = URLEncoder.encode(urlPathHelper.getOriginatingRequestUri(request),"UTF-8");

                    if(url == null || "".equals(url)  ){
                        response.sendRedirect("/loginForm.do?uri="+originalURL);
                    }else{	//url 이있으면 모바일, pc 분기함
                        if("/m/".equals(url.substring(0, 3))) {
                            response.sendRedirect("/m/loginForm.do?uri="+originalURL);
                        }else{
                            response.sendRedirect("/loginForm.do?uri="+originalURL);
                        }
                    }
                    return false;
                }
            }else{

            	if(SessionUtils.hasLoginUserSessionBean()) {

                    //ajax 호출여부 확인
                    boolean ajax="XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
                    if(!ajax){

                    	UserSessionDto userDto = SessionUtils.getUserCookieBean();

                        // 메뉴 권한 체크
                        String reqUri = request.getRequestURI();

                    	boolean retVal  =  NmcpServiceUtils.getMenuAuthList(reqUri, userDto.getUserDivision());

                		if(!retVal) {
                			if("Y".equals(NmcpServiceUtils.isMobile())){
                				response.sendRedirect("/m/mypage/multiPhoneLine.do");
                			} else {
                    			response.sendRedirect("/mypage/multiPhoneLine.do");
                			}
                			return false;
                		}

                    }
            	} else {
                    logger.debug(">>>>>>>>>>>>>>>>> front session 없음");
            	}
            }
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        return true;
    }

}
