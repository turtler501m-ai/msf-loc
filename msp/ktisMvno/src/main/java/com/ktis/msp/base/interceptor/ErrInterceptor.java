package com.ktis.msp.base.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktis.msp.base.login.LoginInfo;

public class ErrInterceptor extends HandlerInterceptorAdapter {
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired  
    protected MessageSource messageSource;
	
//	@Autowired
//	private ErrService errService;
	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//		return true;
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		try {
//			String strParameter = "";
			//v2018.11 PMD 적용 소스 수정
			StringBuffer sb = new StringBuffer();
			
			Enumeration<?> eParam = request.getParameterNames();
			while (eParam.hasMoreElements()) {
				String pName = (String)eParam.nextElement();
				String pValue = request.getParameter(pName);
				
				if(sb.length() > 0) {
					//strParameter += ",";
				   sb.append(",");
				} else {
					//strParameter += "{";
				   sb.append("{");
				}
				//strParameter += "\"" + pName + "\"=\"" + pValue + "\"";
				sb.append("\"");
				sb.append(pName);
				sb.append("\"=\"");
				sb.append(pValue);
				sb.append("\"");
			}
			
			/*if(!"".equals(strParameter)) {
				strParameter += "}";
			}*/
			if(!"".equals(sb.toString())) {
			   sb.append("}");
			}
			
//			strParameter = sb.toString();
			
			if (modelAndView != null) {
				ModelMap mMap = new ModelMap();
				mMap = modelAndView.getModelMap();

				if(mMap != null) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap = (Map<String, Object>)mMap.get("result");
					
					if(resultMap != null && resultMap.get("code") != null && !"".equals(resultMap.get("code")) && !messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()).equals(resultMap.get("code"))) {
						LoginInfo loginInfo = new LoginInfo(request, response);
						Map<String, Object> lParamMap = new HashMap<String, Object>();
						loginInfo.putSessionToParameterMap(lParamMap);

						Map<String, Object> pReqParamMap = new HashMap<String, Object>();
						pReqParamMap.put("category", resultMap.get("category") != null ? resultMap.get("category") : "MSP");
						pReqParamMap.put("prgmId", resultMap.get("prgmId"));
						pReqParamMap.put("prgmNm", resultMap.get("prgmNm"));
						pReqParamMap.put("requestMapping", resultMap.get("requestMapping") != null ? resultMap.get("requestMapping") : request.getServletPath());
						pReqParamMap.put("statCd", "ERR");
						pReqParamMap.put("execParam", sb.toString());
						pReqParamMap.put("errCd", resultMap.get("code"));
						
						if (resultMap.get("stackTrace") != null) {
							String tmpErrMsg = (String) resultMap.get("stackTrace");
							if (tmpErrMsg.length() > 4000) {
								tmpErrMsg = tmpErrMsg.substring(0, 3990);
							}
							pReqParamMap.put("errMsg", tmpErrMsg);
							
						} else {
							pReqParamMap.put("errMsg", resultMap.get("stackTrace"));
						}
						
						pReqParamMap.put("remrk", "");
						pReqParamMap.put("regstId", lParamMap.get("SESSION_USER_ID"));
						pReqParamMap.put("rvisnId", lParamMap.get("SESSION_USER_ID"));
						
//						errService.insertLog(pReqParamMap);
					}
				}
			}
			
		} catch (Exception e) {
			//v2018.11 PMD 적용 소스 수정
//		   e.printStackTrace();
//			20200512 소스코드점검 수정
			//System.out.println("Connection Exception occurred");
			//20210706 소스코드점검수정
			logger.error("Connection Exception occurred");
		}
	}
	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//	}
}
