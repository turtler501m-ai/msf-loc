package com.ktis.msp.base.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//@Service 어노테이션을 반드시 설정하여야 함
//@Service
/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: LoginInterceptor.java
 * 3. Package	: com.ktis.msp.base.login
 * 4. Commnet	: 로그인 되어있는지 check하여 없으면 redirect
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 10. 6. 오전 10:25:36
 * </PRE>
 */
public class LoginInterceptor  extends HandlerInterceptorAdapter 
{

	protected Logger logger = LogManager.getLogger(getClass());
	
    @Autowired  
    protected MessageSource messageSource; 
    
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, 
							 Object handler) throws IOException 
	{
		logger.debug(">>>>>>>>>>>LoginInterceptor >>>>>>>>>>>");
		if ( request.getServletPath().contains("/cmn/login/"))
		{
			logger.debug(">>>>>>>>>>> contains /cmn/login/ ");
			return true;
		}
		logger.debug(">>>>>>>>>>>>>>>> CHECK URI >>>>>>>>>" + request.getRequestURI());
    	//----------------------------------------------------------------
    	// Login 되어있는지 check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(request, response);
    	if(!loginInfo.isLogin())
		{
    		logger.debug(">>>>>>>>>>>>>>>> not login >>>>>>>>>" + request.getRequestURI() + "=="  + request.getServletPath() );

        	//----------------------------------------------------------------
        	// Login 되어있지 않으면 로그인화면으로 redirect 또는 로그인결과 json 으로 redirect
        	//----------------------------------------------------------------
			String uri = request.getRequestURI().toLowerCase();
			if(uri.indexOf(".json") == -1)
			{
				logger.debug(">>>>>>>>>>>>>>>>LoginInterceptor -> IsSessionCheckException >>>>>>>>>");
//				throw new IsSessionCheckException();//세션체크
				response.sendRedirect((request.getRequestURI().replace ( request.getServletPath(), "" ) + "/cmn/login/loginForm.do?reqUri=" + request.getServletPath()).replaceAll("\r", "").replaceAll("\n", ""));
			}else{
				logger.debug(">>>>>>>>>>>>>>>>LoginInterceptor -> IsSessionCheckAjaxException >>>>>>>>>" + request.getRequestURI() + "=="  + request.getServletPath() + "==>" + request.getRequestURI().replace ( request.getServletPath(), "" ) );
				response.sendRedirect(  (request.getRequestURI().replace ( request.getServletPath(), "" ) + "/cmn/login/sessionNullForAjaxReturn.json").replaceAll("\r", "").replaceAll("\n", ""));
			}
			return false;

//			ModelAndView mv = new ModelAndView("http://localhost:8080/ktisMvno/cmn/login/sessionNullForAjaxReturn.json"); 
//			ModelAndViewDefiningException mvde = new ModelAndViewDefiningException(mv); 
//			throw mvde; 

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
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210706 소스코드점검 수정
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
//			e.printStackTrace();
			//System.out.println("Connection Exception occurred");
			//20210706 소스코드점검 수정
			logger.error("Connection Exception occurred");
		}
	}
}
