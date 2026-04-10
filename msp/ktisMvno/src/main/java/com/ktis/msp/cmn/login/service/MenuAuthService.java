package com.ktis.msp.cmn.login.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseService;
import com.ktis.msp.cmn.login.mapper.MenuAuthMapper;


import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: MenuAuthService.java
 * 3. Package	: com.ktis.msp.cmn.login.service
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:58:42
 * </PRE>
 */
@Service
public class MenuAuthService extends BaseService{
	@Autowired
	private MenuAuthMapper menuauthmapper;
	
	@Autowired
	private MenuAuthMapper menuAuthMapper;
	

//	public List<?> selectListXXXX(Map<String, Object> pReqParamMap)  {
//						
//		//----------------------------------------------------------------
//    	// 목록 db select
//    	//----------------------------------------------------------------
//		List<?>  resultList = menuAuthMapper.selectMenuAuthXXXX((pReqParamMap));
//		logger.debug(resultList);
//		
//		return resultList;
//		
//	}
	

//	public boolean selectMenuAuthForURLXXXXXXXXXXXXXXXXX(Map<String, Object> pReqParamMap)  
//	{
//		
//		//----------------------------------------------------------------
//    	// 목록 db select
//    	//----------------------------------------------------------------
//		List<?>  resultList = menuAuthMapper.buttonAuthForCRUD(pReqParamMap);
//		logger.debug(resultList);
//		
//    	if( resultList.size()  == 0 )
//    	{
//    		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    		logger.debug(">>>>>>>>>>>>>>>>  no auth");
//    		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    		return false;
//    	}
//    	
//		return true;
//		
//	}
	
	/**
	 * <PRE>
	 * 1. MethodName: selectMenuAuthForURL
	 * 2. ClassName	: MenuAuthService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:47
	 * </PRE>
	 * 		@return boolean
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@return
	 * 		@throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean selectMenuAuthForURL(HttpServletRequest pRequest, HttpServletResponse pResponse) throws Throwable 
			
	{
		
		//----------------------------------------------------------------
		// 로그인이 안되어있을 경우 어차피 로그인 화면으로 redirection될것이기에
		// 권한 체크는 pass 한다
		// spring filter에서 우선 순위를 정할 수 없으므로 권한 체크가 로그인 체크보다
		// 먼저 수행될 경우 문제가 발생하므로  이렇게 함
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		if ( ! loginInfo.isLogin())
			return true;
		
		//----------------------------------------------------------------
		// 권한관련 select를 하기 위해 사용자 id를 loginInfo에서 가져와서
		// parameter에 담음
		//----------------------------------------------------------------
		Map<String, Object> lParamMap = new HashMap<String, Object>();
		loginInfo.putSessionToParameterMap(lParamMap);
		lParamMap.put("pgmUrl", pRequest.getServletPath() );
		
		List<?>  resultList = menuAuthMapper.buttonAuthForCRUD(lParamMap);
		logger.debug(resultList);
		
		//----------------------------------------------------------------
		// 해당 url -> cmn_prgm_mst -> cmn_menu_mst -> cmn_auth_menu_asgn_mst 
		// -> cmn_auth_mst -> cmn_grp_auth_asgn_mst -> cmn_usr_grp_mst
		// -> cmn_usr_mst -> session의 user id를 찾아서 row가 없으면 
		// 권한이 설정되지 않은 것임
		// -> return false
		//----------------------------------------------------------------
		if( resultList.size()  == 0 )
		{
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>>>>>>>>>>>>  no auth => 등록된 url 없음");
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return false;
		}
		
		//----------------------------------------------------------------
		// 
		//----------------------------------------------------------------
		EgovMap lRow = (EgovMap) resultList.get(0);
		
		
		if( resultList.size()  == 1 )
		{
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>>>>>>>>>>>>  no auth  => 해당 url에 권한 없음");
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return false;
		}
		
		//----------------------------------------------------------------
		// 사용자의 권한이 없을 경우 row는 return되나 usr_id field가 null이므로 
		// 이럴때 권한 없음 처리
		//----------------------------------------------------------------
		lRow = (EgovMap) resultList.get(1);
		if( (String) lRow.get("allow") ==  null || "".equals((String) lRow.get("allow")) || "N".equals((String) lRow.get("allow")) )
		{
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug(">>>>>>>>>>>>>>>>  no auth  => 해당 url에 권한 있으나 CRUD에 권한 없음");
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return false;
		}
		
		//----------------------------------------------------------------
		// main 화면일때만 접속 로그 남김
		//----------------------------------------------------------------
		if(  "MAIN".equals((String) lRow.get("prgmType")))
		{
			lParamMap.put("MENU_ID", lRow.get("menuId"));
			
			String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
			
			lParamMap.put("IP_INFO"   ,ipAddr);
			
			menuAuthMapper.insertLog(lParamMap);
		}
		
		
		return true;
		
	}
	

	/**
	 * <PRE>
	 * 1. MethodName: buttonAuthForCRUD
	 * 2. ClassName	: MenuAuthService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:58:53
	 * </PRE>
	 * 		@return HashMap
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * 		@throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public HashMap buttonAuthForCRUD(HttpServletRequest pRequest, HttpServletResponse pResponse) throws JsonGenerationException, JsonMappingException, IOException 
			
	{
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		HashMap<String,Object> lRtnHashMap = new HashMap<String,Object>();
		HashMap<String,String> lCrudHashMap = new HashMap<String,String>();
		
		List<String> lUsrGrpAuthList = new ArrayList<String>();
		lRtnHashMap.put("extra", lUsrGrpAuthList);

		//		HashMap<String,String> l_usrGrpAuthHashMap = new HashMap<String,String>();
//	    l_rtnHashMap.put("extra", l_usrGrpAuthHashMap);
		lRtnHashMap.put("crud", lCrudHashMap);
		
		//----------------------------------------------------------------
		// 권한관련 select를 하기 위해 사용자 id를 loginInfo에서 가져와서
		// parameter에 담음
		//----------------------------------------------------------------
		Map<String, Object> lParamMap = new HashMap<String, Object>();
		loginInfo.putSessionToParameterMap(lParamMap);
		lParamMap.put("pgmUrl", pRequest.getServletPath() );
		List<?>  resultList = menuAuthMapper.buttonAuthForCRUD(lParamMap);
	
		//----------------------------------------------------------------
		// 해당 url -> cmn_prgm_mst -> cmn_menu_mst -> cmn_auth_menu_asgn_mst 
		// -> cmn_auth_mst -> cmn_grp_auth_asgn_mst -> cmn_usr_grp_mst
		// -> cmn_usr_mst -> session의 user id를 찾아서 row가 없으면 
		// 권한이 설정되지 않은 것임
		// -> return false
		//----------------------------------------------------------------
		if( resultList.size()  == 0 )
		{
			return lRtnHashMap;
		}
		
		//----------------------------------------------------------------
		// 세션에 현재의 메뉴 id 를 담아두고 개별 화면에서 현재 자신의 메뉴를
		// 아는데 사용함
		//----------------------------------------------------------------
		EgovMap lRow = (EgovMap) resultList.get(0);
		
		lRtnHashMap.put("menuId", (String) lRow.get("menuId"));
		
		if( resultList.size()  == 1 )
		{
			// 권한 매핑 안하는 경우도 접속로그 생성
			lRow = (EgovMap) resultList.get(0);
			
			if(  "MAIN".equals((String) lRow.get("prgmType")))
			{
				lParamMap.put("MENU_ID", lRow.get("menuId"));
				
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();
				
				lParamMap.put("IP_INFO"   ,ipAddr);
				
				menuAuthMapper.insertLog(lParamMap);
			}
			
			return lRtnHashMap;
		}
		
		//----------------------------------------------------------------
		// 사용자의 권한이 없을 경우 row는 return되나 usr_id field가 null이므로 
		// 이럴때 권한 없음 처리
		//----------------------------------------------------------------
		lRow = (EgovMap) resultList.get(1);

		lCrudHashMap.put("creatAbableYn" , (String) lRow.get("creatAbableYn"));
		lCrudHashMap.put("delAuth"       , (String) lRow.get("delAuth"));
		lCrudHashMap.put("rvisnAuth"     , (String) lRow.get("rvisnAuth"));
		lCrudHashMap.put("srchAuth"      , (String) lRow.get("srchAuth"));
		lCrudHashMap.put("exelAbableYn"  , (String) lRow.get("exelAbableYn"));
		lCrudHashMap.put("prntAbableYn"  , (String) lRow.get("prntAbableYn"));

//    	l_crudHashMap.put("creatAbableYn" , ((String) lRow.get("creatAbableYn")).equals("Y") ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("delAuth"       , ((String) lRow.get("delAuth")).equals("Y")       ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("rvisnAuth"     , ((String) lRow.get("rvisnAuth")).equals("Y")     ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("srchAuth"      , ((String) lRow.get("srchAuth")).equals("Y")      ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("exelAbableYn"  , ((String) lRow.get("exelAbableYn")).equals("Y")  ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("prntAbableYn"  , ((String) lRow.get("prntAbableYn")).equals("Y")  ? " , disabled:false  " : "" );
		
//    	l_crudHashMap.put("creatAbableYn_disabled_tag" , ((String) lRow.get("creatAbableYn")).equals("Y") ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("delAuth_disabled_tag"       , ((String) lRow.get("delAuth")).equals("Y")       ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("rvisnAuth_disabled_tag"     , ((String) lRow.get("rvisnAuth")).equals("Y")     ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("srchAuth_disabled_tag"      , ((String) lRow.get("srchAuth")).equals("Y")      ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("exelAbableYn_disabled_tag"  , ((String) lRow.get("exelAbableYn")).equals("Y")  ? " , disabled:false  " : "" );
//    	l_crudHashMap.put("prntAbableYn_disabled_tag"  , ((String) lRow.get("prntAbableYn")).equals("Y")  ? " , disabled:false  " : "" );

		
		//----------------------------------------------------------------
		// 사용자의 권한 목록을 가져옴
		//----------------------------------------------------------------
//    	List<?>  usrGrpAuthList = menuAuthMapper.usrGrpAuthListForButton(l_paramMap);
//    	l_usrGrpAuthHashMap.clear();
//    	for( int inx = 0 ; inx < usrGrpAuthList.size(); inx++)
//    	{
//    		l_row.clear();
//    		l_row = (EgovMap) usrGrpAuthList.get(inx);
//        	
//    		l_usrGrpAuthHashMap.put((String) l_row.get("authId"), "Y");
////    		l_usrGrpAuthHashMap.put((String) l_row.get("authId") ,   " , disabled:false  " );
//    		
//    		l_usrGrpAuthHashMap.put((String) l_row.get("authId") + "_disabled_tag",   " , disabled:false  " );
//    	}
		
		if(  "MAIN".equals((String) lRow.get("prgmType")))
		{
			logger.debug("버튼 권한 조회시 화면접속로그 생성하도록 수정... 20141120");
			
			lParamMap.put("MENU_ID", lRow.get("menuId"));
			
			String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();
			
			lParamMap.put("IP_INFO"   ,ipAddr);
			
			menuAuthMapper.insertLog(lParamMap);
		}
		
		//----------------------------------------------------------------
		// 사용자의 권한 목록을 가져옴
		//----------------------------------------------------------------
		List<?>  usrGrpAuthList = menuAuthMapper.usrGrpAuthListForButton(lParamMap);
		lUsrGrpAuthList.clear();
		for( int inx = 0 ; inx < usrGrpAuthList.size(); inx++)
		{
			lRow.clear();
			lRow = (EgovMap) usrGrpAuthList.get(inx);
			
			lUsrGrpAuthList.add((String)lRow.get("authId"));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(lRtnHashMap);
		lRtnHashMap.put("jsonString", jsonString);
		
		return lRtnHashMap;
		
	}
	
	public boolean chkUsrGrpAuthListForButton(String strSessionUserId, String strAuthId, String strChkMsg) throws MvnoRunException {
		try {
			Map<String, Object> lParamMap = new HashMap<String, Object>();
			lParamMap.put("SESSION_USER_ID", strSessionUserId);
			lParamMap.put("AUTH_ID", strAuthId);
			int iAuthCnt = menuauthmapper.chkUsrGrpAuthListForButton(lParamMap);
			if(iAuthCnt == 0) {
				throw new MvnoRunException(-1, strChkMsg);
			}
		} catch(MvnoRunException mre) {
			throw mre;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		return true;
	}
	
	public boolean chkUsrGrpAuth(String userId, String authId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("SESSION_USER_ID", userId);
		paramMap.put("AUTH_ID", authId);
				
		return menuauthmapper.chkUsrGrpAuthListForButton(paramMap) > 0;
	}

//	public boolean chekcOpenAuthXXXXX(List<?> pList, String pMenuId,  HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException 
//			
//	{
//		
//		if ( pList.size() <= 0 )
//		{
//			return false;
//		}
//
//		logger.debug("====================  menu authority =====================" );
//		for( int inx = 0 ; inx <pList.size() ; inx++)
//		{
//			EgovMap lRtn =(EgovMap) pList.get(inx);
//			logger.debug("====" + lRtn.get("menuId") +   "\t\t==>" +  lRtn.get("hasAuth")  );
//			if ( lRtn.get("menuId").equals(pMenuId))
//			{
//				return true;
//			}
//		}
//		logger.debug("====================  menu authority =====================" );
//		
//		pResponse.sendRedirect(  pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/noAuthRedirect.json");
//		
//		return false;
//		
//	}
	

//	public boolean chekcOpenAuthV2XXXXXXXXXXXX(List<?> pList, String pMenuId, String pFunctionId, HttpServletRequest pRequest, HttpServletResponse pResponse, Map<String, Object> pRequestParamMap) throws IOException 
//
//	{
//		
//		// CREAT_ABABLE_YN DEL_AUTH RVISN_AUTH SRCH_AUTH EXEL_ABABLE_YN PRNT_ABABLE_YN
//		
//		if ( pList.size() <= 0 )
//		{
//			return false;
//		}
//
//		logger.debug("====================  menu authority =====================" );
//		for( int inx = 0 ; inx <pList.size() ; inx++)
//		{
//			EgovMap lRow =(EgovMap) pList.get(inx);
//			logger.debug("====menu authority menu authority : " + lRow);
//			logger.debug("    ====p_menu_id : " + pMenuId + "== p_function_id : " + pFunctionId);
//			if (  lRow.get("menuId").equals(pMenuId) && lRow.get(pFunctionId).equals("Y"))
//			{
//				logger.debug("l_row.get(p_function_id):" + lRow.get(pFunctionId));
//				
//				pRequestParamMap.put("MENU_ID", pMenuId);
//				menuAuthMapper.insertLog(pRequestParamMap);
//				return true;
//			}
//		}
//		logger.debug("====================  menu authority =====================" );
//		
//		pResponse.sendRedirect(  pRequest.getRequestURI().replace ( pRequest.getServletPath(), "" ) + "/cmn/login/noAuthRedirect.json");
//		
//		return false;
//		
//	}
	


//	public boolean chekcOpenAuthV2XXXXXXXXXXX(List<?> pList, 
//			String pMenuId, 
//			String pFunctionId, 
//			HttpServletRequest pRequest, 
//			HttpServletResponse pResponse) 
//
//	{
//		return false;
//	}
	
//	public static String AUTH_CREAT_ABABLE_YN = "creatAbableYn";
//	public static String AUTH_DEL_AUTH        = "delAuth";
//	public static String AUTH_RVISN_AUTH      = "rvisnAuth";
//	public static String AUTH_SRCH_AUTH       = "srchAuth";
//	public static String AUTH_EXEL_ABABLE_YN  = "exelAbableYn";
//	public static String AUTH_PRNT_ABABLE_YN  = "prntAbableYn";
	
	public HashMap<String, Object> buttonAuthChk(Map<String, Object> param){
		
		return menuAuthMapper.buttonAuthChk(param);
	}
	/**
	 * <PRE>
	 * 1. MethodName: breadCrumb
	 * 2. ClassName	: MenuAuthService
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2019. 8. 01.
	 * </PRE>
	 * 		@return HashMap
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * 		@throws Exception
	 *  화면의 경로와 title을 조회
	 */
	@Transactional(rollbackFor=Exception.class)
	public HashMap breadCrumb(HttpServletRequest pRequest, HttpServletResponse pResponse) throws JsonGenerationException, JsonMappingException, IOException 
			
	{
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		HashMap<String,String> lCrudHashMap = new HashMap<String,String>();
		//----------------------------------------------------------------
		// 권한관련 select를 하기 위해 사용자 id를 loginInfo에서 가져와서
		// parameter에 담음
		//----------------------------------------------------------------
		Map<String, Object> lParamMap = new HashMap<String, Object>();
		loginInfo.putSessionToParameterMap(lParamMap);
		lParamMap.put("pgmUrl", pRequest.getServletPath() );
		
		//breadCrumb 경로 가져오기
		List<?> resultList = menuAuthMapper.breadCrumb(lParamMap);
		
		String title = null;
		String breadCrumb = null;
		StringBuffer sb = null;
		for( int inx = 0 ; inx < resultList.size(); inx++)
		{
			EgovMap lRow = (EgovMap) resultList.get(inx);
			if(inx  == 0){
				breadCrumb = (String)lRow.get("menuNm") + " > ";
			}else if (inx > 0 && inx != resultList.size() -1){
				sb = new StringBuffer(breadCrumb);
				sb.append((String)lRow.get("menuNm"));
				sb.append(" > ");
				breadCrumb = sb.toString();
				//breadCrumb += (String)lRow.get("menuNm") + " > ";
				
			}
			
			if(inx == resultList.size() -1){
				sb = new StringBuffer(breadCrumb);
				sb.append((String)lRow.get("menuNm"));
				breadCrumb = sb.toString();
				//breadCrumb += (String)lRow.get("menuNm");
				title = (String)lRow.get("menuNm");
			}
		}
		
		lCrudHashMap.put("breadCrumb"  , breadCrumb);
		lCrudHashMap.put("title"  , title);
		logger.info("TITLE >>> " + lCrudHashMap.get("breadCrumb"));
		logger.info("메뉴경로 >>> " + lCrudHashMap.get("title"));
		
		return lCrudHashMap;
		
	}

}
