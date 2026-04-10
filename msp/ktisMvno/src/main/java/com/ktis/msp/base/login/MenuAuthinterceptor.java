package com.ktis.msp.base.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktis.msp.cmn.login.service.MenuAuthService;

//@Service 어노테이션을 반드시 설정하여야 함
//@Service
public class MenuAuthinterceptor  extends HandlerInterceptorAdapter 
{

	protected Logger logger = LogManager.getLogger(getClass());
	
    @Autowired  
    protected MessageSource messageSource; 
    
    @Autowired  
    protected MenuAuthService menuAuthService; 
    
    //--------------------------------------------------------
    // Weeb server의 filter에서 intercept하여 menu에 대한 권한 check
    //--------------------------------------------------------
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, 
							 Object handler) 
	{
		try {
			logger.debug(">>>>>>>>>>>MenuAuthService >>>>>>>>>>>");
			
		    //--------------------------------------------------------
		    // login 관련한 url은 권한 check에서 제외
		    //--------------------------------------------------------
			if ( request.getServletPath().contains("/cmn/login/"))
			{
				return true;
			}
			
			logger.debug(">>>" + request.getRequestURI() + "=="  + request.getServletPath() + "==>" + request.getRequestURI().replace ( request.getServletPath(), "" ) );
			
		    //--------------------------------------------------------
		    // URL에 대한 권한이 있는지 DB select하여 권한 있는지 여부를 return 받음
		    //--------------------------------------------------------
	//		String menuId = "";

			if ( ! menuAuthService.selectMenuAuthForURL(request, response ))
			{
			    //--------------------------------------------------------
			    // 확장자가
			    //--------------------------------------------------------
				String uri = request.getRequestURI().toLowerCase();
				if(uri.indexOf(".json") == -1)
				{
				    //--------------------------------------------------------
				    // 확장자가 json이 아닌 경우 권한 없음 페이지로 redirect 하기 위한 exception을 발생시킴
					// exception에 대한 redirect url은 context-.... 에 설정되어있음
				    //--------------------------------------------------------
					logger.debug(">>>>>>>>>>>>>>>>MenuAuthService -> IsNotAllowedException >>>>>>>>>");
//					throw new IsNotAllowedException(); 
					response.sendRedirect(  request.getRequestURI().replace ( request.getServletPath(), "" ) + "/cmn/login/noAuthRedirect2.do?noAuthUrl=" + request.getRequestURI());
				}else{
				    //--------------------------------------------------------
				    // 확장자가 json인경우 권한 없음을 json으로 reponse하는 페이지로 redirect
				    //--------------------------------------------------------
					logger.debug(">>>>>>>>>>>>>>>>MenuAuthService -> sendRedirect >>>>>>>>>" );
					response.sendRedirect(  request.getRequestURI().replace ( request.getServletPath(), "" ) + "/cmn/login/noAuthRedirect.json?noAuthUrl=" + request.getRequestURI());
				}
				return false;
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
    
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
	{
		try {
			super.postHandle(request, response, handler, modelAndView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
	{
		try {
			super.afterCompletion(request, response, handler, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			20200512 소스코드점검 수정
//	    	e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			logger.error("Connection Exception occurred");
		}
	}
}
