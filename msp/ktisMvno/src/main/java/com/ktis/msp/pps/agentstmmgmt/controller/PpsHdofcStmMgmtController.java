package com.ktis.msp.pps.agentstmmgmt.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.agentstmmgmt.service.PpsHdofcStmMgmtService;
import com.ktis.msp.pps.agentstmmgmt.vo.PpsAgentStmOpenVo;
import com.ktis.msp.pps.smsmgmt.service.FileReadService;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : PpsHdofcStmMgmtController.java
 * @Description : PpsHdofcStmMgmtController class
 * @Modification Information
 * @
 * @  수정일			수정자			수정내용
 * @ ---------		---------	-------------------------------
 * @ 2017.05.01		김웅			최초생성
 *
 * @author 김웅
 * @since 2017. 05.01
 * @version 1.0
 */

@Controller
public class PpsHdofcStmMgmtController  extends BaseController {
	
	@Autowired
	private PpsHdofcStmMgmtService stmService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MaskingService  maskingService;
	
	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsFileService ppsFileService;
	
	@Autowired
	private FileReadService fileReadService;
	
	/**
	 * 대리점 개통 기본 수수료
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmBasicForm.do", method={POST, GET} )
	public ModelAndView selectStmInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.AgentStmBasicForm:  대리점 개통 기본 수수료 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 대리점 개통 기본 수수료 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmBasicList.json" )
	public String selectAgentStmBasicListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmBasicListJson : 대리점 개통 기본 수수료 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmInfoMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("regAdminNm", "CUST_NAME");
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 대리점 개통 기본 수수료  엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmBasicListExcel.json" )
	public String selectAgentStmBasicListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmBasicListExcelJson : 대리점 개통 기본 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);

 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 대리점 Grade 수수료
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmGradeForm.do", method={POST, GET} )
	public ModelAndView selectStmGradeInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmGradeInfoMgmtListForm:  대리점 Grade 수수료 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 대리점 Grade 수수료 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmGradeList.json" )
	public String selectAgentStmGradeListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmGradeListJson : 대리점 Grade 수수료 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmGradeMgmtList(pRequestParamMap);
	    	 
	    	//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("remark", "CUST_NAME");
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);

    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 대리점 Grade 수수료  엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmGradeListExcel.json" )
	public String selectAgentStmGradeListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmGradeMgmtListExcelJson : 대리점 Grade 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmGradeMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 대리점 명의 변경 수수료
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmMbForm.do", method={POST, GET} )
	public ModelAndView selectStmMbInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmMbInfoMgmtListForm:  대리점 명의 변경  수수료 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 대리점 명변 수수료 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmMbList.json" )
	public String selectAgentStMbListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmMbListJson : 대리점 명변 수수료 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmMbMgmtList(pRequestParamMap);
	    	 
	    	//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("regAdmin", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
				
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 대리점 명변 수수료  엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmMbListExcel.json" )
	public String selectAgentStmMbListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmMbListExcelJson : 대리점 명변 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmMbMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 대리점 우량 고객 수수료
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmUlForm.do", method={POST, GET} )
	public ModelAndView selectStmUlInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmUlInfoMgmtListForm:  대리점 우량 고객  수수료 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 우량 고객 수수료 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmUlList.json" )
	public String selectAgentStUlListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmUlListJson : 우량 고객 수수료 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmUlMgmtList(pRequestParamMap);
	    	//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("regAdminNm", "CUST_NAME");
			
			maskingService.setMask(resultList, maskFields, pRequestParamMap);

	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 우량 고객 수수료  엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmUlListExcel.json" )
	public String selectAgentStmUlListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmUlListExcelJson : 대리점 우량고객 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmUlMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}

	/**
	 * 환수등록
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundExcelForm.do", method={POST, GET} )
	public ModelAndView selectStmRefundExcelInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmRefundExcelInfoMgmtListForm:  환수 등록 근거페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0060");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 환수 등록 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundExcelList.json" )
	public String selectAgentStRefundExcelListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmRefundExcelListJson : 환수 등록 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmRefundExcelMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 환수 등록 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundExcelListExcel.json" )
	public String selectAgentStmRefundExcelListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmRefundExcelListExcelJson : 환수 등록 엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmRefundExcelListMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 자동이체 수수료
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmCmsForm.do", method={POST, GET} )
	public ModelAndView selectStmCmsInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmCmsInfoMgmtListForm:  자동이체 수수료 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0070");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 자동 이체 수수료 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmCmsList.json" )
	public String selectAgentStCmsListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmCmsListJson : 자동 이체 수수료 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmCmsMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);

    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 자동 이체 수수료  엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmCmsListExcel.json" )
	public String selectAgentStmCmsListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmCmsListExcelJson : 자동 이체 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmCmsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);

 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 해지 환수 관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundForm.do", method={POST, GET} )
	public ModelAndView selectStmRefundInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmRefundInfoMgmtListForm:  해지 환수 관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0080");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 해지 환수 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundList.json" )
	public String selectAgentStRefundListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmRefundListJson : 해지 환수 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmRefundMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
			// Masking
			//----------------------------------------------------------------
    	 	HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("regAdminNm", "CUST_NAME");
		 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 해지 환수 조회 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRefundListExcel.json" )
	public String selectAgentStmRefundListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmRefundListExcelJson : 해지 환수 조회  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmRefundMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);

 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 미사용환수내역
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmNoUseForm.do", method={POST, GET} )
	public ModelAndView selectStmNoUseInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmNoUseInfoMgmtListForm:  미사용환수내역 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0090");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 미사용 환수 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmNoUseList.json" )
	public String selectAgentStNoUseListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmNoUseListJson : 미사용 환수 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmNoUseMgmtList(pRequestParamMap);
			//----------------------------------------------------------------
			// Masking
			//----------------------------------------------------------------
    	 	HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			maskFields.put("regAdminNm", "CUST_NAME");
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 미사용 환수 조회 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmNoUseListExcel.json" )
	public String selectAgentStmNoUseListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmNoUseListExcelJson : 미 사용 환수 조회  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmNoUseMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 비정상관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmReOpenForm.do", method={POST, GET} )
	public ModelAndView selectStmReOpenInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmReOpenInfoMgmtListForm:  비정상관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0100");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 비 정상 관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmReOpenList.json" )
	public String selectAgentStReOpenListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmReOpenListJson : 비 정상 관리 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmReOpenMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 비 정상 관리 조회 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmReOpenListExcel.json" )
	public String selectAgentStmReOpenListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmReOpenListExcelJson : 비 정상 관리  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmReOpenMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 재충전 수수료 관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRcgForm.do", method={POST, GET} )
	public ModelAndView selectStmRcgInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmRcgInfoMgmtListForm:  재충전 수수료 관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0130");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 재충전 수수료 관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRcgList.json" )
	public String selectAgentStRcgListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmRcgListJson : 재충전 수수료 관리 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmRcgMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 재충전 수수료 관리 조회 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmRcgListExcel.json" )
	public String selectAgentStmRcgListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmRcgListExcelJson : 재충전 수수료  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmRcgMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 조정 수수료 관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmModForm.do", method={POST, GET} )
	public ModelAndView selectStmModInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmModInfoMgmtListForm:  조정 수수료 관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0140");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 조정 수수료 관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmModList.json" )
	public String selectAgentStModListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmModListJson : 조정 수수료 관리 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmModMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 조정 관리 조회 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmModListExcel.json" )
	public String selectAgentStmModListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmModListExcelJson : 조정 관리  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmModMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 대리점별 정산내역
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmHistoryForm.do", method={POST, GET} )
	public ModelAndView selectStmHistoryInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmHistoryInfoMgmtListForm:  대리점별 정산내역 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0110");
			return modelAndView;
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 대리점별 정산내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmHistoryList.json" )
	public String selectAgentStmHistoryListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmHistoryListJson : 대리점별 정산내역 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmHistoryMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 대리점별 정산내역 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmHistoryListExcel.json" )
	public String selectAgentStmHistoryListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
     	logger.info("PpsHdofcStmMgmtController.selectAgentStmHistoryListExcelJson : 대리점별 정산내역  엑셀");
 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmHistoryMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 통합수수료정산
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmForm.do", method={POST, GET} )
	public ModelAndView selectAgentStmInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectAgentStmInfoMgmtListForm: 통합수수료정산  페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0120");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 통합수수료정산 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmList.json" )
	public String selectAgentStmListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmListJson : 통합수수료정산 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 통합수수료정산 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmListExcel.json" )
	public String selectAgentStmListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmListExcelJson : 통합수수료정산  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 환수 등록 Excel 업로드
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadRefundExcelFile.do")
	  public String uploadRefundExcelFile (
	  												ModelMap model, 
	  												HttpServletRequest pRequest, 
	  												HttpServletResponse pResponse, 
	  									    		@RequestParam Map<String, Object> pRequestParamMap
	  											)
	  {				
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    String sBaseDir = null;
	  	    sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
	  	    logger.info(sBaseDir);
	      	
	  	    try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		fileReadService.fileUpLoad(pRequest, "file", "PPS" );
	  	        resultMap.put("state", true);
	  			resultMap.put("name", "xxx".replace("'","\\'"));
	  			resultMap.put("size", "" + 111);
	      	
	    	} catch (Exception e) {
	 	 	   		
	    		resultMap.put("state", false);
	    		resultMap.put("name", "lll".replace("'","\\'"));
	    		resultMap.put("size",  111);
	    		model.addAttribute("result", resultMap);
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
					throw new MvnoErrorException(e);
				}
	    	}
			
	  	    model.addAttribute("result", resultMap);
		    
			return "jsonViewArray";

	  }
	
	/**
	 * 환수 등록 Excel 업로드 등록
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadRefundExcelProc.json")
	public String uploadRefundExcelProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sBaseDir = null;
		sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("fileName");
			String fileName = pRequestParamMap.get("fileName").toString();
			resultMap = stmService.getPpsRefundFileRead(pRequestParamMap, filePath, fileName);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";

	  }
	
	/**
	 * 각 수수료 상태 변경
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmStatusChgProc.json")
	public String ppsStmStatusChgProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmStatusChg(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 대리점별 정산내역 수정
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsAgentStmAgentModProc.json")
	public String ppsAgentStmAgentModProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsAgentStmAgentMod(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 조정수수료 Excel 업로드
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadAgentStmModExcelFile.do")
	  public String uploadAgentStmModExcelFile (
	  												ModelMap model, 
	  												HttpServletRequest pRequest, 
	  												HttpServletResponse pResponse, 
	  									    		@RequestParam Map<String, Object> pRequestParamMap
	  											)
	  {				
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    String sBaseDir = null;
	  	    sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
	  	    logger.info(sBaseDir);
	      	
	  	    try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		fileReadService.fileUpLoad(pRequest, "file", "PPS" );
	  	        resultMap.put("state", true);
	  			resultMap.put("name", "xxx".replace("'","\\'"));
	  			resultMap.put("size", "" + 111);
	      	
	    	} catch (Exception e) {
	 	 	  
	     		
	    		resultMap.put("state", false);
	    		resultMap.put("name", "lll".replace("'","\\'"));
	    		resultMap.put("size",  111);
	    		model.addAttribute("result", resultMap);
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
					throw new MvnoErrorException(e);
				}
	    	}
			
	  	    model.addAttribute("result", resultMap);
		    
			return "jsonViewArray";

	  }
	
	/**
	 * 조정수수료 Excel 업로드 등록
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadAgentStmModExcelProc.json")
	public String uploadAgentStmModExcelProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sBaseDir = null;
		sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("fileName");
			String fileName = pRequestParamMap.get("fileName").toString();
			resultMap = stmService.getPpsAgentStmModFileRead(pRequestParamMap, filePath, fileName);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";

	  }
	
	/**
	 * 조정 수수료 Excel Sample 다운로드
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/agentStmModExcelSample.json" )
	public String selectagentStmModExcelSampleJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToParameterMap(pRequestParamMap);
        
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
        	String sBaseDir = propertyService.getString("fileUploadBaseDirectory") + "/PPS/";
        	String strFileName = sBaseDir+"agentStmModExcelSample.xls";
        	
            file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String encodingFileName = "";
            
            int excelPathLen2 = sBaseDir.length();

            try {
              encodingFileName = URLEncoder.encode(encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
            out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			//logger.error(e.getMessage());
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			throw new MvnoErrorException(e);
 			
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	           // logger.error(e);
	        	  throw new MvnoErrorException(e);

	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}
	
	/**
	 * 환수등록 Excel Sample 다운로드
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/agentStmRefundExcelSample.json" )
	public String selectagentStmRefundExcelSampleJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToParameterMap(pRequestParamMap);
        
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
        	String sBaseDir = propertyService.getString("fileUploadBaseDirectory") + "/PPS/";
        	String strFileName = sBaseDir+"agentStmRefundExcelSample.xls";
        	
            file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String encodingFileName = "";
            
            int excelPathLen2 = sBaseDir.length();

            try {
              encodingFileName = URLEncoder.encode(encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
            out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			//logger.error(e.getMessage());
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	            //logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	 // logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}
	
	/**
	 * 정책관리 뷰
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSetupForm.do", method={POST, GET} )
	public ModelAndView selectStmSetupMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.AgentStmSetupForm:  정책관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 *  정책그룹 select 코드가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/PpsAgentStmGroupList.json" )
	public String selectPpsAgentStmGroupListJson( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		
		logger.info("PpsHdofcStmMgmtController.selectPpsAgentStmGroupListJson : 정책그룹 코드 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
			List<?> resultList = stmService.getPpsAgentStmGroupData(pRequestParamMap);
			if(resultList != null)
			{
				resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
				if(resultMap != null)
				{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	
				
				}
				else
				{
					resultMap = new HashMap<String, Object>();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					resultMap.put("msg", returnMsg);
				}
			}
			else
			{
				resultMap = new HashMap<String, Object>();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", returnMsg);
			}
	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	/**
	 * 정책 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSetupList.json" )
	public String selectAgentStmSetupListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmSetupListJson : 정책관리 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmSetupMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 정책 값 변경
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmSetupChgProc.json")
	public String ppsStmSetupChgProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmSetupChg(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 세부정책 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSetupDtlList.json" )
	public String selectAgentStmSetupDtlListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmSetupDtlListJson : 정책세부관리 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmSetupDtlMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 세부정책 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmCodeList.json" )
	public String selectAgentStmCodeListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmCodeListJson : 정책코드 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmCodeMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	
	//
	
	/**
	 * 중복 개통
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmOverlapForm.do", method={POST, GET} )
	public ModelAndView selectStmOverListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.AgentStmOverlapForm:  중복 개통");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0150");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	/**
	 * 중복 개통  조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmOverlapList.json" )
	public String selectAgentStmOverlapListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmOverlapListJson : 중복 개통 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmOverlapList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 중복 개통 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmOverlapListExcel.json" )
	public String selectAgentStmOverlapListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmOverlapListExcelJson : 중복 개통 엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmOverlapListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);
 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 현재 정책진행상태 체크.
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmCntList.json" )
	public String selectAgentStmCntListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmCodeListJson : 현재 정책진행상태 체크");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 List<?> resultList = stmService.getStmCntMgmtList(pRequestParamMap);
	    	 
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	    	/*
	 		HashMap<String,String> maskFields = new HashMap<String,String>();

	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		
	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 		*/
	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	 resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
         }
        	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 수동 기본수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmBasicProc.json")
	public String ppsStmBasicProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmBasic(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 grid수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmGradeProc.json")
	public String ppsStmGradeProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmGrade(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 명변수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmMbProc.json")
	public String ppsStmMbProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmMb(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동재충전수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmRcgProc.json")
	public String ppsStmRcgProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmRcg(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 비정상해지 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmRefundProc.json")
	public String ppsStmRefundProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmRefund(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 기준사용미달 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmNouseProc.json")
	public String ppsStmNouseProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmNouse(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 해지후재가입 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmReopenProc.json")
	public String ppsStmReopenProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmReopen(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 우량수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmUlProc.json")
	public String ppsStmUlProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmUl(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 자동이체수수료 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmCmsProc.json")
	public String ppsStmCmsProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmCms(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 대리점별 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmAgentProc.json")
	public String ppsStmAgentProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStmAgent(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 수동 통합 정산
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsStmProc.json")
	public String ppsStmProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsStm(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
	/**
	 * 대리점정산관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSelfForm.do", method={POST, GET} )
	public ModelAndView selectStmSelfInfoMgmtListForm( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		ModelAndView modelAndView = new ModelAndView();
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" PpsHdofcStmMgmtController.selectStmHistoryInfoMgmtListForm:  대리점별 정산관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/agentstmmgmt/hdofc_agentstmmgmt_0160");
			return modelAndView;
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 대리점정산관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSelfList.json" )
	public String selectAgentStmSelfListJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		logger.info("PpsHdofcStmMgmtController.selectAgentStmHistoryListJson : 대리점별 정산내역 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	List<?> resultList = stmService.getStmSelfMgmtList(pRequestParamMap);
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	    	
    	} catch (Exception e) {
       		resultMap.clear();
    		if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
    			//logger.error(e);
    			throw new MvnoErrorException(e);
    		}
        }
        
    	model.addAttribute("result", resultMap);
            //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * 대리점정산관리  엑셀다운
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/AgentStmSelfListExcel.json" )
	public String selectAgentStmSelfListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
	{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
 		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
     	loginInfo.putSessionToParameterMap(pRequestParamMap); 
	     	
 		logger.info("PpsHdofcStmMgmtController.selectAgentStmSelfListExcelJson : 대리점정산관리  엑셀");
	 		
 		try{
 			// 본사 권한
 			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
 				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
 			}
 			stmService.getStmSelfMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
 		}catch(Exception e){
 			//logger.error(e.getMessage());
 			throw new MvnoErrorException(e);

 		}
	 		
     	return "jsonView";
	}
	
	/**
	 * 대리점정산관리 Excel 업로드
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadAgentStmSelfExcelFile.do")
	  public String uploadAgentStmSelfExcelFile (
	  												ModelMap model, 
	  												HttpServletRequest pRequest, 
	  												HttpServletResponse pResponse, 
	  									    		@RequestParam Map<String, Object> pRequestParamMap
	  											)
	  {				
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    String sBaseDir = null;
	  	    sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
	  	    logger.info(sBaseDir);
	      	
	  	    try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		fileReadService.fileUpLoad(pRequest, "file", "PPS" );
	  	        resultMap.put("state", true);
	  			resultMap.put("name", "xxx".replace("'","\\'"));
	  			resultMap.put("size", "" + 111);
	      	
	    	} catch (Exception e) {
	 	 	  
	     		
	    		resultMap.put("state", false);
	    		resultMap.put("name", "lll".replace("'","\\'"));
	    		resultMap.put("size",  111);
	    		model.addAttribute("result", resultMap);
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
					throw new MvnoErrorException(e);
				}
	    	}
			
	  	    model.addAttribute("result", resultMap);
		    
			return "jsonViewArray";

	  }
	
	/**
	 * 대리점정산관리 Excel 업로드 등록
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/uploadAgentStmSelfExcelProc.json")
	public String uploadAgentStmSelfExcelProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sBaseDir = null;
		sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("file_upload1_r_0");
			String fileName = pRequestParamMap.get("file_upload1_r_0").toString();
			resultMap = stmService.getPpsAgentStmSelfFileRead(pRequestParamMap, filePath, fileName);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";

	  }
	
	/**
	 * 대리점정산관리 Excel Sample 다운로드
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/agentstmmgmt/agentStmSelfExcelSample.json" )
	public String selectagentStmSelfExcelSampleJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToParameterMap(pRequestParamMap);
        
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
        	String sBaseDir = propertyService.getString("fileUploadBaseDirectory") + "/PPS/";
        	String strFileName = sBaseDir+"agentStmSelfExcelSample.xls";
        	
            file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String encodingFileName = "";
            
            int excelPathLen2 = sBaseDir.length();

            try {
              encodingFileName = URLEncoder.encode(encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
            out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			//logger.error(e.getMessage());
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			throw new MvnoErrorException(e);
 			
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	           // logger.error(e);
	        	  throw new MvnoErrorException(e);

	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}
	
	/**
	 * 대리점정산관리 변경
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/hdofc/agentstmmgmt/ppsAgentStmSelfProc.json")
	public String ppsAgentStmSelfProc (
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
			  							)
	{				
			
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    
		try
		{  
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap = stmService.procPpsAgentStmSelfExcel(pRequestParamMap);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	      	
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);

		return "jsonView";

	  }
	
}