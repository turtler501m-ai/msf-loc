/*
 * @(#)LoginInfo.java    
 *
 * Copyright  ITEG, Inc. All Rights Reserved.
 *
 * ----------------------------------------------------------------------------
 *                        MODIFICATION              LOG
 *
 *     DATE               AUTHOR                   DESCRIPTION
 *    --------         --------------      ------------------------------------
 *         
 * ----------------------------------------------------------------------------
 */

package com.ktis.msp.base.login;
      
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ktis.msp.base.mvc.BaseVo;
 
public class LoginInfo implements Serializable
{
	private final Logger logger = LogManager.getLogger(getClass());
	
	public final javax.servlet.http.HttpServletRequest request;
    private final javax.servlet.http.HttpSession session;
    public final javax.servlet.http.HttpServletResponse response;

    private final String mLoginPage ;
    public final String mReqUri ;
    private final Map    mMap ;
    //private javax.servlet.jsp.JspWriter out  ;
//    private java.io.PrintWriter  mOut  ;
//    private String l_checkbox_enable_open_file_window = null;
    
        
    /**
    * 생성자
    * @param request jsp의 request
    * @return response jsp의 response
    * @see   #         
    */
    public LoginInfo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response){
        this.request      = request;
        this.response     = response;
        this.session      = request.getSession(false);
        this.mReqUri      = request.getRequestURI();
        this.mMap         = request.getParameterMap();  
        this.mLoginPage   = "/cmn/login/loginForm.do";
//        this.l_checkbox_enable_open_file_window  = StringUtils.defaultString(request.getParameter("ar_checkbox_enable_open_file_window"),"");
//        try{
        	//The PrintWriter uses the character encoding returned by getCharacterEncoding(). 
        	//If the response's character encoding has not been specified as described in 
        	//getCharacterEncoding (i.e., the method just returns the default value ISO-8859-1), 
        	//getWriter updates it to ISO-8859-1. 
        	//response.setCharacterEncoding("euc-kr"); 
        	//l_out      = response.getWriter();
//        	
//        }catch (Exception e){
//        	dummyCatch();
//        }
        logger.debug(this.mLoginPage);
        
	    try{
      	    if ( request.getRealPath(request.getRequestURI()).matches("/*_eng/*"))
                  this.session.setAttribute("LANGUAGE",             "ENG");
      	    else 
                  this.session.setAttribute("LANGUAGE",             "KOR");
        }catch ( Exception e){
        	dummyCatch();
        }
        

    }

     
//     /**
//      * Login화면의 url을 구
//      * @param void
//      * @return void
//      * @see   #        
//      */
//      public String getLoginPage() {
//     	 return mLoginPage;
//      }
      
      
    /**
     * <PRE>
     * 1. MethodName: isLogin
     * 2. ClassName	: LoginInfo
     * 3. Commnet	: 단순 session 체크
     *                login check하여 login되어있지않으면 login page로 redirect
     * 4. 작성자	: Administrator
     * 5. 작성일	: 2014. 10. 6. 오전 10:29:42
     * </PRE>
     * 		@return boolean
     * 		@return
     */
    public boolean isLogin() 
    {
        String usrId;
        try {
        	usrId = (String) session.getAttribute("CONNECTION_USER_IDENTITY_ABCDEFGHIJKLMN");
//        	System.out.println("sess_l_user_id>>:"+usrId);
        }catch ( Exception e) {
        	usrId = "";
            //throw e;
        }
        //l_out.println(l_user_id + "=");


        if ( usrId == null || usrId.equals("")) {
//                javax.servlet.RequestDispatcher rd = session.getServletContext().getRequestDispatcher(getLoginPage() + "?reqUri="+ l_req_uri);
//                rd.forward(request, response);
            return false;
        }

        return true;
    }
    
    /**
    * session 정보  return
    * @param void
    * @return String
    * @see   #         
    */
    public String getAttribute(String pAttributeName) {
        String rtnValue;
        if ( session == null)
            return "";
        try{
        	rtnValue = (String) session.getAttribute(pAttributeName);
            if ( rtnValue == null)
                return "";
        }catch( Exception e){
            return "";
        }
            
        return rtnValue;
    }

    /**
    * 사용자 ID return
    * @param void
    * @return String
    * @see   #      
    */
    public String getUserId() {
        return getAttribute("CONNECTION_USER_IDENTITY_ABCDEFGHIJKLMN");
    }
       
    /**
    * 사용자 NAME return
    * @param void
    * @return String
    * @see   #      
    */
    public String getUserName() {
        return getAttribute("SESSION_USER_NAME");
    }

    /**
    * MENU_ID return
    * @param void
    * @return String
    * @see   #      
    */
    public String getMenuId() {
        return getAttribute("SESSION_MENU_ID");
    }

    /**
    * TYPE_DTL_CD1 return
    * @param void
    * @return String
    * @see   #      
    */
    public String getTypeDtlCd1() {
        return getAttribute("SESSION_TYPE_DTL_CD1");
    }
    
    
    /**
    * 
    * @param void
    * @return String
    * @see   #      
    */
    public String getUserMskAuthYn() {
        return getAttribute("SESSION_USER_MASK_AUTH_YN");
    }

	
    public String getUserOrgnId() {
        return getAttribute("SESSION_USER_ORGN_ID");
    }
    
    public String getUserOrgnTypeCd() {
        return getAttribute("SESSION_USER_ORGN_TYPE_CD");
    }

	
    public String getUserOrgnNm() {
        return getAttribute("SESSION_USER_ORGN_NM");
    }
    
	
    public String getUserOrgnLvlCd() {
        return getAttribute("SESSION_USER_ORGN_LVL_CD");
    }
    
	
    public String getUserLogisCnterYn() {
        return getAttribute("SESSION_USER_LOGIS_CNTER_YN");
    }
    
    public String getUserMngrRule() {
        return getAttribute("SESSION_USER_MNGR_RULE");
    }
    
    /**
     * 사용자 Locale return
     * @param void
     * @return String
     * @see   #      
     */
     public Locale getLocale() {
    	 Locale rtnVvalue;
    	 
         if ( session == null)
             return Locale.getDefault();
         try{
        	 rtnVvalue = (Locale) session.getAttribute("LOCALE");
             if ( rtnVvalue == null)
                 return Locale.getDefault();
         }catch( Exception e){
             return Locale.getDefault();
         }
             
         return rtnVvalue;
     }

     /**
      * 사용자 Locale set
      * @param void
      * @return String
      * @see   #      
      */
      public void setLocale(Locale pLocale) {
//     	 Locale rtnValue;
     	 
          if ( session == null)
              return ;
          try{
        	  session.setAttribute("LOCALE",  pLocale);
          }catch( Exception e){
        	  session.setAttribute("LOCALE",  Locale.getDefault());
          }

      }
      
    
    public String getlnParameters(){
    	String rtnString ="";
    	
	   	StringBuffer lStrBuffer = new StringBuffer(rtnString);
	   	lStrBuffer.append("//--------------------------------------------------");
	   	lStrBuffer.append("// Message from printlnParameters  ");
	   	lStrBuffer.append("// redirection hidden parameer setting failed ");
	   	lStrBuffer.append("//--------------------------------------------------");
	   	rtnString = lStrBuffer.toString();
   	 
        try { 
            if (mMap != null && mMap.size() > 0) {
                String key = null;
                String[] values = null;
                String lValue = "";
                Iterator it = mMap.keySet().iterator();
                while (it.hasNext()) {
                    key = (String) it.next();
                    values = (String[]) mMap.get(key);
                    if (values != null){
                        for (int inx = 0; inx < values.length; inx++) {
                        	lValue = (values[inx] == null) ? "" : values[inx];
                            if ( ! key.equals("usrId") &&   ! key.equals("pass") )
                                //this.l_out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + l_value + "\" > " );
//                            	rtnString = rtnString + "<input type=\"hidden\" name=\"" + key + "\" value=\"" + lValue + "\" > " ;
                            	lStrBuffer.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + lValue + "\" > ");
                        }
                    }
                }
                rtnString = lStrBuffer.toString();
            }
         }catch(Exception e){

        	 lStrBuffer.append("//--------------------------------------------------");
        	 lStrBuffer.append("// Message from printlnParameters  ");
        	 lStrBuffer.append("// redirection hidden parameer setting failed ");
        	 lStrBuffer.append("//--------------------------------------------------");
        	 rtnString = lStrBuffer.toString();
        	 
         }
         return rtnString;
    }
    
    public void putSessionToParameterMap( Map pMap ){
    	pMap.put("SESSION_USER_ID", getUserId());
    	pMap.put("SESSION_USER_NAME", getUserName());
    	pMap.put("SESSION_USER_MASKING", getUserMskAuthYn());
    	pMap.put("SESSION_USER_ORGN_ID", getUserOrgnId());
    	pMap.put("SESSION_USER_ORGN_NM", getUserOrgnNm());
    	pMap.put("SESSION_USER_ORGN_TYPE_CD", getUserOrgnTypeCd());

    	pMap.put("SESSION_USER_ORGN_LVL_CD", getUserOrgnLvlCd());
    	pMap.put("SESSION_USER_LOGIS_CNTER_YN", getUserLogisCnterYn());

    	//선,후불 구분 코드 SU 이면 선불
    	pMap.put("SESSION_TYPE_DTL_CD1", getTypeDtlCd1());
    	
    	pMap.put("SESSION_MENU_ID", getMenuId());
//    	p_map.put("SESSION_USER_AUTH", getUserAuth());
    	
    	// [SRM18072573065] ARS 관련 API 연동
    	pMap.put("SESSION_ENC_USER_ID", getEncUserId());

    	logger.debug("======== SESSION ================" );
    	logger.debug(pMap );
    	logger.debug("=================================" );
    }
    
    public void putSessionToVo( BaseVo pBaseVo ){
    	pBaseVo.setSessionUserId(getUserId());
    	pBaseVo.setSessionUserName(getUserName());
    	pBaseVo.setSessionUserMskAuthYn(getUserMskAuthYn());
    	pBaseVo.setSessionUserOrgnId(getUserOrgnId());
    	pBaseVo.setSessionUserOrgnNm(getUserOrgnNm());
    	pBaseVo.setSessionUserOrgnTypeCd(getUserOrgnTypeCd());

    	pBaseVo.setSessionUserOrgnLvlCd(getUserOrgnLvlCd());
    	pBaseVo.setSessionUserLogisCnterYn(getUserLogisCnterYn());

    	pBaseVo.setSessionTypeDtlCd1(getTypeDtlCd1());

    	pBaseVo.setSessionMenuId(getMenuId());
//    	p_map.put("SESSION_USER_AUTH", getUserAuth());
    	
    	// [SRM18072573065] ARS 관련 API 연동
    	pBaseVo.setSessionEncUserId(getEncUserId());

    	logger.debug("======== SESSION ================" );
    	logger.debug(pBaseVo );
    	logger.debug("=================================" );
    	
    }
    

	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 		@return void
	 */
	public String  dummyCatch()
	{
		return "";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: dummyFinally
	 * 2. ClassName	: BaseController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 26. 오전 11:39:24
	 * </PRE>
	 * 		@return void
	 */
	public String dummyFinally()
	{
		return "";

	}
	
	
	public String getOrgChgYn() {
        return getAttribute("SESSION_ORG_CHG_YN");
    }
	
	// 2017-12-26, 세션 타임아웃 추가
	public String getTimeout() {
		return getAttribute("SESSION_TIME_OUT");
	}
	
	// [SRM18072573065] ARS 관련 API 연동
	public String getEncUserId() {
		return getAttribute("SESSION_ENC_USER_ID");
	}
} 
