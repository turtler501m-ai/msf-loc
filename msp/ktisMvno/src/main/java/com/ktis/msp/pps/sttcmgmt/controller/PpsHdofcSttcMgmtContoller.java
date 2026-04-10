package com.ktis.msp.pps.sttcmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.pps.sttcmgmt.service.PpsHdofcSttcMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * @Class Name : PpsHdofcSttcMgmtContoller.java
 * @Description : PpsHdofcSttcMgmtContoller class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.08           최초생성
 *
 * @author 장익준
 * @since 2014. 08.08
 * @version 1.0
 */

@Controller
public class PpsHdofcSttcMgmtContoller extends BaseController {
	
	@Autowired
	private PpsHdofcSttcMgmtService sttcService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	/**
	 * 선불 개통현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcOpenMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcOpenMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListForm : 선불 개통현황(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 개통현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcOpenMgmtList.json" )
	public String selectSttcOpenMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListJson : 선불 개통현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcOpenMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 개통현황 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcOpenMgmtListExcel.json" )
	public String selectSttcOpenMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcOpenMgmtListExcelJson : 선불 개통현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{	
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcOpenMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	
	 		
    	return "jsonView";
	}
	
	/**
	 * 선불 충전현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcRechargeMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcRechargeMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcRechargeMgmtListForm : 선불 충전현황(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 충전현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcRechargeMgmtList.json" )
	public String selectSttcRechargeMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcRechargeMgmtListJson : 선불 충전현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcRechargeMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 충전현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcRechargeMgmtListExcel.json" )
	public String selectSttcRechargeMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcRechargeMgmtListExcelJson : 선불 충전현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcRechargeMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	 
	    	 
		
    	return "jsonView";
	}
	
	/**
	 * 선불 예치금입금현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositInMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcDepositInMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositInMgmtListForm : 선불 예치금입금현황(통계) 페이지 호출");
		
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 예치금입금현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositInMgmtList.json" )
	public String selectSttcDepositInMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositInMgmtListJson : 선불 예치금입금현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcDepositInMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
  	 	} catch (Exception e) {
  	 		resultMap.clear();
//			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
//				//logger.error(e);
//			}
       }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 선불예치금 입금현황(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositInMgmtListExcel.json" )
	public String selectSttcDepositInMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositInMgmtListExcelJson : 선불 선불예치금 입금현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			sttcService.getSttcDepositInMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
	    	 
	    	 
	    	
		
    	return "jsonView";
	}
	
	
	
	
	
	
	/**
	 * 선불 예치금 출금현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositOutMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcDepositOutMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositOutMgmtListForm : 선불 예치금출금현황(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    			
		
	}
	
	/**
	 * 선불 예치금 출금현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositOutMgmtList.json" )
	public String selectSttcDepositOutMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositOutMgmtListForm : 선불 예치금출금현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcDepositOutMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 선불예치금 출금현황(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcDepositOutMgmtListExcel.json" )
	public String selectSttcDepositOutMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcDepositOutMgmtListExcelJson : 선불 선불예치금 출금현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			sttcService.getSttcDepositOutMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
	    	
	    	 
	    						
		
    	return "jsonView";
	}
	
	
	
	
	/**
	 * 선불 사용현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUseMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcUseMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListForm : 선불 사용현황(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 사용현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUseMgmtList.json" )
	public String selectSttcUseMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListJson : 선불 사용현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcUseMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 사용현황(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUseMgmtListExcel.json" )
	public String selectSttcUseMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListExcelJson : 선불 카드현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcUseMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	 
	    	
		
    	return "jsonView";
	}
	
	/**
	 * 선불 국제현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcIntrnMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcIntrnMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			logger.info("===========================================");
			logger.info("======  PpsHdofcSttcMgmtContoller.selectSttcIntrnMgmtListForm -- 선불 국제현황(통계) 페이지 호출  ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0060");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 국제현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcIntrnMgmtList.json" )
	public String selectSttcIntrnMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		logger.info("===========================================");
		logger.info("======  PpsHdofcSttcMgmtContoller.selectSttcIntrnMgmtListJson -- 선불 국제현황(통계) 목록조회  ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
    	logger.info("===========================================");
		
//    	//----------------------------------------------------------------
//    	// 1. getAuth 
//    	//----------------------------------------------------------------
//    	pRequestParamMap.put("ar_prgm_id","SAM_PGM");  
//    	pRequestParamMap.put("ar_duty_id","SAM_DUTY");  
//    	List<?> menuAuth =  menuAuthService.selectList(pRequestParamMap);
//
//    	//----------------------------------------------------------------
//    	// 2. check open auth  and redirect
//    	//----------------------------------------------------------------
//    	if( ! menuAuthService.chekcOpenAuth(menuAuth, "LIST", pRequest, pResponse))
//    		return "";
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
		
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	 List<?> resultList = sttcService.getSttcIntrnMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	
       
  	 	} catch (Exception e) {
  	 		resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				 throw new MvnoErrorException(e);
			}
       }
    	model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	
	/**
	 * 선불 국제 현황(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcIntrnMgmtListExcel.json" )
	public String selectSttcIntrnMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcIntrnMgmtListExcelJson : 선불 카드현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcIntrnMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	 
	    	 
	    	 
		
    	return "jsonView";
	}
	
	
	/**
	 * 선불 카드현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcCardMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcCardMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcCardMgmtListForm : 선불 카드현황(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0070");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 카드현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcCardMgmtList.json" )
	public String selectSttcCardMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcCardMgmtListJson : 선불 카드현황(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcCardMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 카드현황(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcCardMgmtListExcel.json" )
	public String selectSttcCardMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcCardMgmtListExcelJson : 선불 카드현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcCardMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	 
		
    	return "jsonView";
	}
	
	/**
	 * 선불 ARPU분석통계 페이지호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcArpuMgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcArpuMgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcArpuMgmtListForm : 선불 ARPU 분석통계(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0080");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 arpu 분석통계 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcArpuMgmtList.json" )
	public String selectSttcArpuMgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcArpuMgmtListJson : 선불 arpu 분석통계 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcArpuMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
  	 	} catch (Exception e) {
  	 		resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				 throw new MvnoErrorException(e);
			}
       }
    	model.addAttribute("result", resultMap);
        logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 arpu 분석통계 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcArpuMgmtListExcel.json" )
	public String selectSttcArpuMgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcArpuMgmtListExcelJson : 선불 arpu분석통계 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcArpuMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	 
		
    	return "jsonView";
	}
	
	/**
	 * 선불 사용현황2(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUse2MgmtForm.do", method={POST, GET} )
	public ModelAndView selectSttcUse2MgmtListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			
			logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListForm : 선불 사용현황2(통계) 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/sttcmgmt/hdofc_sttcmgmt_0090");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 사용현황2(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUse2MgmtList.json" )
	public String selectSttcUse2MgmtListJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListJson : 선불 사용현황2(통계) 목록조회");
		
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
	    	
	    	 List<?> resultList = sttcService.getSttcUse2MgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 선불 사용현황2(통계) 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/sttcmgmt/SttcUse2MgmtListExcel.json" )
	public String selectSttcUse2MgmtListExcelJson( ModelMap model, 
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
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
		
		logger.info("PpsHdofcSttcMgmtContoller.selectSttcUseMgmtListExcelJson : 선불 사용현황2(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		sttcService.getSttcUse2MgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	 
	    	
		
    	return "jsonView";
	}
	
}
