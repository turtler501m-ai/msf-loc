package com.ktis.msp.cmn.filedownsrch.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedownsrch.service.FileDownSrchService;
import com.ktis.msp.cmn.login.service.MenuAuthService;


/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: FileDownSrchController.java
 * 3. Package	: com.ktis.msp.cmn.filedownsrch.controller
 * 4. Commnet	: 
 * 5. 작성자	: Administrator
 * 6. 작성일	: 2014. 9. 25. 오후 3:54:41
 * </PRE>
 */
@Controller
public class FileDownSrchController  extends BaseController { 

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownSrchService fileDownSrchService;
	
	/**
	 * <PRE>
	 * 1. MethodName: form
	 * 2. ClassName	: FileDownSrchController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:43
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/filedownsrch/form.do")
	public ModelAndView form(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		logger.info("===========================================");
		logger.info("======  FileDownSrch.form -- 파일 다운로드 관리 화면 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("cmn/filedownsrch/msp_org_bs_1041");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName: selecListAjax
	 * 2. ClassName	: FileDownSrchController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:54:45
	 * </PRE>
	 * 		@return String
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/cmn/filedownsrch/selecList.json")
	public String selecListAjax(ModelMap model, 
							HttpServletRequest pRequest, 
							HttpServletResponse pResponse, 
							@RequestParam Map<String, Object> pRequestParamMap) 
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.info("===========================================");
		logger.info("======  FileDownSrch.selecList -- 파일 다운로드 관리 화면 목록 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
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
			
			List<?> resultList =  fileDownSrchService.selectList(pRequestParamMap);
			
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
	
	
}