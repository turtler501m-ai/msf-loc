package com.ktis.msp.cmn.codefind.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.codefind.service.CodeFindService;

import com.ktis.msp.base.exception.MvnoErrorException;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: CodeFindController.java
 * 3. Package	: com.ktis.msp.cmn.codefind.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:51:53
 * </PRE>
 */
@Controller
public class CodeFindController  extends BaseController { 

	@Autowired
	private CodeFindService codeFindService;
	
	/**
	 * <PRE>
	 * 1. MethodName: cmnMenuMst
	 * 2. ClassName	: CodeFindController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:52:00
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/codefind/cmnMenuMst.json")
	public String cmnMenuMst( ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap) 
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{		

			logger.info("===========================================");
			logger.info("======  CodeFind.cmnMenuMst -- 콤보박스 메뉴 목록 ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			List<?> resultList =  codeFindService.cmnMenuMst(pRequestParamMap);
			
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
//			sqlService.Close();
			dummyFinally();
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		return "jsonView";

	}
	
}


