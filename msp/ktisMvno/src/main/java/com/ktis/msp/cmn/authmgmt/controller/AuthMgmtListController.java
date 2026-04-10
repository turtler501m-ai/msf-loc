package com.ktis.msp.cmn.authmgmt.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.authmgmt.service.AuthMgmtService;

/**
 * @Class Name : AuthMgmtController.java
 * @Description : AuthMgmtController Class
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01     임지혜          최초생성
 * 
 *
 * @author 임지혜
 */

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: AuthMgmtListController.java
 * 3. Package	: com.ktis.msp.cmn.authmgmt.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 6:17:42
 * </PRE>
 */
@Controller
public class AuthMgmtListController extends BaseController {
	
	@Autowired
	private AuthMgmtService authMgmtService;
	
	/**
	 * 프로그램 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmList.json")
	public String  prgmList(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap) 
	{
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmList -- 프로그램 목록  ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.prgmList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
	

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * 프로그램 목록(추가 화면 용)
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmList2.json")
	public String  prgmList2(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmList -- 프로그램 목록  ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.prgmList2(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
	

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 프로그램  상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmDetail.json")
	public String  prgmDetail(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmDetail -- 프로그램 상세 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.prgmDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);

			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
//			sqlService.Close();
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 프로그램  insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmInsert.json")
	public String  prgmInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmInsert -- 프로그램 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//--------------------------------------
			// 동일한 메뉴에 이미 prgm_type='MAIN'인 건이 존재하면 error
			//--------------------------------------
			List<?> resultList =  authMgmtService.prgmInsertDupMainCheck(pRequestParamMap);
			if ( resultList.size() > 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.PRGM_DUP_MAIN", null, Locale.getDefault()) ); 			
			}else
			{
				authMgmtService.prgmInsert(pRequestParamMap);
				
			 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * 프로그램  insert2 (화면 분리)
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmInsert2.json")
	public String  prgmInsert2(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmInsert -- 프로그램 insert2 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//--------------------------------------
			// 동일한 메뉴에 이미 prgm_type='MAIN'인 건이 존재하면 error -> 필요없음.
			//--------------------------------------
				authMgmtService.prgmInsert2(pRequestParamMap);
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	

	/**
	 * 프로그램  update
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmUpdate.json")
	public String  prgmUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmUpdate -- 프로그램 Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//--------------------------------------
			// 동일한 메뉴에 이미 prgm_type='MAIN'인 건이 존재하면 error
			//--------------------------------------
			List<?> resultList =  authMgmtService.prgmUpdateDupMainCheck(pRequestParamMap);
			
			if ( resultList.size() > 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.PRGM_DUP_MAIN", null, Locale.getDefault()) ); 			
			}else
			{
				
				int updateCnt =  authMgmtService.prgmUpdate(pRequestParamMap);
				if ( updateCnt == 0 )
				{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
				}else
				{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					resultMap.put("msg", "");
				}
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 프로그램  delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmDelete.json")
	public String  prgmDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.prgmDelete -- 프로그램 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			
			int updateCnt =  authMgmtService.prgmDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	/**
	 * 메뉴  목록
	 */
	@RequestMapping(value = {"/cmn/authmgmt/menuList.json", "/cmn/authmgmt/menuList2.json"})
	public String  menuList(  
								ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap
							) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuList -- 메뉴 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.menuHghrList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}


	
	/**
	 * 메뉴 상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/menuDetail.json")
	public String  menuDetail(  
									ModelMap model, 
									HttpServletRequest pRequest, 
									HttpServletResponse pResponse, 
									@RequestParam Map<String, Object> pRequestParamMap
								) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuMgmtDetail -- 메뉴 상세 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.menuDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
			logger.debug(e);
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}


	/**
	 * 메뉴 insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/menuInsert.json")
	public String  menuInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuInsert -- 프로그램 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.menuInsert(pRequestParamMap);
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 메뉴 update
	 */
	@RequestMapping(value = "/cmn/authmgmt/menuUpdate.json")
	public String  menuUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuUpdate -- 프로그램 Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.menuUpdate(pRequestParamMap);
			
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 메뉴 delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/menuDelete.json")
	public String  menuDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuDelete -- 프로그램 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.menuDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	
	/**
	 * 권한 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/authList.json")
	public String  authList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authList -- 권한 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.authList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * 권한 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/authListPaging.json")
	public String  authListPaging(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authList -- 권한 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.authListPaging(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	
	/**
	 * 권한 상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/authDetail.json")
	public String  authDetail(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authDetail -- 권한 상세======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.authDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	


	/**
	 * 권한 insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/authInsert.json")
	public String  authInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authInsert -- 권한 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.authInsert(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한 update
	 */
	@RequestMapping(value = "/cmn/authmgmt/authUpdate.json")
	public String  authUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.menuUpdate -- 권한 Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.authUpdate(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한 delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/authDelete.json")
	public String  authDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authDelete -- 권한 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.authDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	
	/**
	 * 그룹 찾기
	 */
	@RequestMapping(value = "/cmn/authmgmt/groupSearchList.json")
	public String  groupSearchList(  
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.groupSearchList -- 그룹 찾기 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.groupSearchList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
			logger.debug(e);
		}finally{
			dummyFinally();
		}
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	/**
	 * 권한-메뉴 ASSIGN 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenuList.json")
	public String  authMenuList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuList -- 권한-메뉴 ASSIGN 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.authMenuList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한-메뉴 ASSIGN 상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenuDetail.json")
	public String  authMenuDetail(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuDetail -- 권한-메뉴 ASSIGN 상세 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.authMenuDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}			
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	


	/**
	 * 권한-메뉴 ASSIGN  insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenuInsert.json")
	public String  authMenuInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuInsert -- 권한-메뉴 ASSIGN  insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.authMenuInsert(pRequestParamMap);
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한-메뉴 ASSIGN  update
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenuUpdate.json")
	public String  authMenuUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												)
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuUpdate -- 권한-메뉴 ASSIGN  Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.authMenuUpdate(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한-메뉴 ASSIGN  delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenuDelete.json")
	public String  authMenuDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuDelete -- 권한-메뉴 ASSIGN  Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.authMenuDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	
	/**
	 * 사용자-메뉴 ASSIGN 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenuAsgnList.json")
	public String  usrMenuAsgnList(  
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.userSpclAuthList -- 사용자-메뉴 ASSIGN 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrMenuAsgnList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 권한-메뉴 ASSIGN 상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenuAsgnDetail.json")
	public String  usrMenuAsgnDetail(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.authMenuDetail -- 권한-메뉴 ASSIGN 상세 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrMenuAsgnDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	


	/**
	 * 사용자-메뉴 ASSIGN insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenuAsgnInsert.json")
	public String  usrMenuAsgnInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.userSpclAuthInsert -- 사용자-메뉴 ASSIGN  insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.usrMenuAsgnInsert(pRequestParamMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 사용자-메뉴 ASSIGN  update
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenuAsgnUpdate.json")
	public String  usrMenuAsgnUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.userSpclAuthUpdate -- 사용자-메뉴 ASSIGN  Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.usrMenuAsgnUpdate(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 사용자-메뉴 ASSIGN  delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenuAsgnDelete.json")
	public String  usrMenuAsgnDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.userSpclAuthDelete -- 사용자-메뉴 ASSIGN  Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.usrMenuAsgnDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	/**
	 * 그룹-권한 ASSIGN 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/grpAuthAsgnList.json")
	public String  grpAuthAsgnList(  
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.grpAuthAsgnList -- 그룹-권한 ASSIGN 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.grpAuthAsgnList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	



	/**
	 * 그룹-권한 ASSIGN insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/grpAuthAsgnInsert.json")
	public String  grpAuthAsgnInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.grpAuthAsgnInsert -- 그룹-권한 ASSIGN insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.grpAuthAsgnInsert(pRequestParamMap);
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	

	/**
	 * 그룹-권한 ASSIGN delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/grpAuthAsgnDelete.json")
	public String  grpAuthAsgnDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.grpAuthAsgnDelete -- 그룹-권한 ASSIGN Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.grpAuthAsgnDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	


	/**
	 * 사용자그룹 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpList.json")
	public String  usrGrpList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpList -- 사용자그룹 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrGrpList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 사용자그룹 상세
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpDetail.json")
	public String  usrGrpDetail(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpDetail -- 사용자그룹 상세 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrGrpDetail(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultSingleRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	


	/**
	 * 사용자그룹 insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpInsert.json")
	public String  usrGrpInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpInsert -- 사용자그룹 insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			authMgmtService.usrGrpInsert(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 사용자그룹 update
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpUpdate.json")
	public String  usrGrpUpdate(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpUpdate -- 사용자그룹 Update ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.usrGrpUpdate(pRequestParamMap);
			if ( updateCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	

	/**
	 * 사용자그룹 delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpDelete.json")
	public String  usrGrpDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpDelete -- 사용자그룹 Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.usrGrpDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	

	/**
	 * 사용자그룹-사용자 ASSIGN 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpAsgnList.json")
	public String  usrGrpAsgnList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpAsgnList -- 사용자그룹-사용자 ASSIGN 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrGrpAsgnList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	


	/**
	 * 사용자그룹-사용자 ASSIGN insert
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpAsgnInsert.json")
	public String  usrGrpAsgnInsert(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpAsgnInsert -- 사용자그룹-사용자 ASSIGN insert ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = authMgmtService.usrGrpAsgnInsert(pRequestParamMap);
			
			//사용자 그룹 이력을 남긴다.
			if(returnCnt > 0)
			{
				pRequestParamMap.put("operType", "I");
				
				authMgmtService.usrGrpAsgnHstInsert(pRequestParamMap);
				
			}
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	


	/**
	 * 사용자그룹-사용자 ASSIGN delete
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpAsgnDelete.json")
	public String  usrGrpAsgnDelete(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrGrpAsgnDelete -- 사용자그룹-사용자 ASSIGN Delete ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int updateCnt =  authMgmtService.usrGrpAsgnDelete(pRequestParamMap);
			if ( updateCnt == 0 )
			{
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				//사용자 그룹 이력을 남긴다.
				if(updateCnt > 0)
				{
					pRequestParamMap.put("operType", "D");
					
					authMgmtService.usrGrpAsgnHstInsert(pRequestParamMap);
				}
				
		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
				
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}	
	
	


	/**
	 * 사용자 권한 리포트 목록
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrAuthReportList.json")
	public String  usrAuthReportList(  
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
									) 
	{ 
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.usrAuthReportList -- 사용자 권한 리포트목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.usrAuthReportList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}



	@RequestMapping(value = "/cmn/authmgmt/menuHghrListXml.json")
	public void menuHghrListXml(
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info("===========================================");
		logger.info("======  AccessLogSrch.selecList -- 화면 접속 로그 화면 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PrintWriter printWriter = null;
		
		try{
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			String sql = " \n"  
						+ " 	        SELECT level,                                                                                                          \n"
						+ " 	        XMLElement(\"row\"                                                                                                     \n"
						+ " 	                   , XMLAttributes( a.menu_id as \"id\")                                                                       \n"
						+ " 	                   --, XMLElement(\"userdata\", XMLAttributes( a.menu_nm as \"name\") ,  a.menu_nm)                            \n"
						+ " 	                   , XMLElement(\"cell\",  a.menu_id)                                                                          \n"
						+ " 	                   , XMLElement(\"cell\",  a.menu_nm)                                                                          \n"
						+ " 	                   , XMLElement(\"cell\",  a.menu_lvl)                                                                         \n"
						+ " 	                   , XMLElement(\"cell\",  a.menu_dsc)                                                                         \n"
						+ " 	                   , XMLElement(\"cell\",  a.algn_seq)                                                                         \n"
						+ " 	                   , XMLElement(\"cell\",  a.usg_yn)                                                                           \n"
						+ " 	                   , XMLElement(\"cell\"                                                                                              \n"
						+ " 	                   ,XMLAttributes( '\\cmn\\btn_plus.gif' as \"image\") ,a.menu_nm                                     \n"
						+ " 	                    )                                                                                                                 \n"
						+ " 	                  )                                                                                                            \n"
						+ " 	      FROM                                                                                                                     \n"
						+ " 	      (                                                                                                                        \n"
						+ " 	          SELECT a.*                                                                                                           \n"
						+ " 	                  FROM                                                                                                         \n"
						+ " 	                      (                                                                                                        \n"
						+ " 	                       SELECT         ROWNUM ROW_INDEX                                                                         \n"
						+ " 	                                    , a.MENU_ID                                                                                \n"
						+ " 	                                    , a.MENU_LVL                                                                               \n"
						+ " 	                                    , a.MENU_NM                                                                                \n"
						+ " 	                                    , replace(RPAD(' ', 4*(LEVEL - 1)),' ','.') || MENU_NM MENU_NM_HGHR                        \n"
						+ " 	                                    , a.MENU_DSC                                                                               \n"
						+ " 	                                    , a.HGHR_MENU_ID                                                                           \n"
						+ " 	                                    , a.ALGN_SEQ                                                                               \n"
						+ " 	                                    , a.USG_YN                                                                                 \n"
						+ " 	                                    , a.LMT_ORGN_LVL_CD                                                                        \n"
						+ " 	                                    , a.REGST_ID                                                                               \n"
						+ " 	                                    , TO_CHAR(a.REG_DTTM, 'YYYY-MM-DD HH24:MI:SS') REG_DTTM                                    \n"
						+ " 	                                    , a.RVISN_ID                                                                               \n"
						+ " 	                                    , TO_CHAR(a.RVISN_DTTM, 'YYYY-MM-DD HH24:MI:SS') RVISN_DTTM                                \n"
						+ " 	                                    , a.DUTY_NM                                                                                \n"
						+ " 	                                    , a.DEL_YN                                                                                 \n"
						+ " 	                                    , a.PRGM_ID                                                                                \n"
						+ " 	                                    , a.DUTY_ID                                                                                \n"
						+ " 	                              FROM CMN_MENU_MST a                                                                              \n"
						+ " 	                              START WITH  '0' =  a.MENU_LVL                                                                    \n"
						+ " 	                              CONNECT BY a.HGHR_MENU_ID = PRIOR a.MENU_ID                                                      \n"
						+ " 	                              ORDER SIBLINGS BY   1 + ALGN_SEQ                                                                 \n"
						+ " 	                      ) a                                                                                                      \n"
						+ " 	                  WHERE 1 = 1                                                                                                  \n"
						+ " 	      )     a                                                                                                                  \n"
						+ " 	      START WITH  0 =  a.MENU_LVL                                                                                              \n"
						+ " 	      CONNECT BY a.HGHR_MENU_ID = PRIOR a.MENU_ID                                                                              \n"
						+ " 	      ORDER SIBLINGS BY   1 + ALGN_SEQ                                                                                         \n" ;
		
			// space 제거 - 안하면 문자열 제한 걸림
			sql = sql.replaceAll(" {2,}"," ");
			
			
			pRequestParamMap.put("querySql", sql);
			
			List<?> resultList =  authMgmtService.menuHghrListXml(pRequestParamMap);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Map lmap = (Map) resultList.get(0);
			
			String result = (String) lmap.get("xml");
			
			result = result.replaceAll("<.+xml.+version[^>]*>", "" );
			
			result = new StringBuffer().append("<rows>\n").append(result).append("\n</rows>").toString();
			
			pResponse.setContentType("application/xml; charset=UTF-8");
			
			logger.info(result);
			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", "menuTree.xml");
			pResponse.setHeader(headerKey, headerValue);
			
			printWriter = pResponse.getWriter();
			printWriter.write(result);
			printWriter.flush();
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
			printWriter.close();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
//		model.addAttribute("result", resultMap);
//	    return "jsonView";

	}
	
	
	
	/**
	 * 사용자 리스트 조회
	 */
	@RequestMapping(value = "/cmn/authmgmt/userInfoMgmtList.json")
	public String  userInfoMgmtList(  
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
												) 
	{
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.userInfoMgmtList -- 사용자 목록  ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  authMgmtService.userInfoMgmtList(pRequestParamMap);
			
			//----------------------------------------------------------------
			// return format으로 return object 생성
			//----------------------------------------------------------------
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

		}catch ( Exception e)
		{
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}	
		}finally{
			dummyFinally();
		}
	
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
	
}
