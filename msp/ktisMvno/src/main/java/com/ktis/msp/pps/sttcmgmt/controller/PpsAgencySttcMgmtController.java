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
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.sttcmgmt.service.PpsAgencySttcMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;
/**
 * @Class Name : PpsAgencySttcMgmtController.java
 * @Description : PpsAgencySttcMgmtController class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.08.13           최초생성
 *
 * @author 홍은표
 * @since 2015. 08.13
 * @version 1.0
 */

@Controller
public class PpsAgencySttcMgmtController extends BaseController {

	@Autowired
	private PpsAgencySttcMgmtService sttcService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private MaskingService  maskingService;
	
	/**
	 * 선불 대리점 >> 개통현황(통계) 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcOpenMgmtForm.do", method={POST, GET} )
	public ModelAndView selectAgencySttcOpenMgmtListForm( ModelMap model, 
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

			String lvlCd = loginInfo.getUserOrgnLvlCd();
			String typeCd = loginInfo.getUserOrgnTypeCd();
			if (typeCd.equals("20") && lvlCd.equals("10")) {
				pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
				pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
			}
		
			
			logger.info("===========================================");
			logger.info("PpsAgencySttcMgmtController.selectAgencySttcOpenMgmtListForm :   선불  대리점>> 개통현황(통계) 페이지 호출");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");

			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/sttcmgmt/agency_sttcmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 선불 대리점 개통현황(통계) 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcOpenMgmtList.json" )
	public String selectAgencySttcOpenMgmtListJson( ModelMap model, 
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
    	
    	String lvlCd = loginInfo.getUserOrgnLvlCd();
		String typeCd = loginInfo.getUserOrgnTypeCd();
		if (typeCd.equals("20") && lvlCd.equals("10")) {
			pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
			pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
		}
    	
		//pRequestParamMap.put("searchAgentId", "AG0002");
		
		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcOpenMgmtListJson : 선불  대리점 개통현황(통계) 목록조회");
		
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
		
    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = sttcService.getAgencySttcOpenMgmtList(pRequestParamMap);
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
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcOpenMgmtListExcel.json" )
	public String selectAgencySttcOpenMgmtListExcelJson( ModelMap model, 
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
    	
    	String lvlCd = loginInfo.getUserOrgnLvlCd();
		String typeCd = loginInfo.getUserOrgnTypeCd();
		if (typeCd.equals("20") && lvlCd.equals("10")) {
			pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
			pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
		}
		//pRequestParamMap.put("searchAgentId", "AG0002");
		
		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcOpenMgmtListExcelJson : 선불 대리점  개통현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{	
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
    		sttcService.getAgencySttcOpenMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	
	    	
	 		
    	return "jsonView";
	}
	
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcSubscribersMgmtForm.do", method={POST, GET} )
	public ModelAndView selectAgencySttcSubscribersMgmtListForm( ModelMap model, 
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

			String lvlCd = loginInfo.getUserOrgnLvlCd();
			String typeCd = loginInfo.getUserOrgnTypeCd();
			if (typeCd.equals("20") && lvlCd.equals("10")) {
				pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
				pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
			}
		
			
			logger.info("===========================================");
			logger.info("PpsAgencySttcMgmtController.selectAgencySttcSubscribersMgmtListForm :   선불  대리점>> 가입자이용현황(통계) 페이지 호출");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");

			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/sttcmgmt/agency_sttcmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	
	
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcSubscribersMgmtList.json" )
	public String selectAgencySttcSubscribersMgmtListJson( ModelMap model, 
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
    	
    	String lvlCd = loginInfo.getUserOrgnLvlCd();
		String typeCd = loginInfo.getUserOrgnTypeCd();
		if (typeCd.equals("20") && lvlCd.equals("10")) {
			pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
			pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
		}
    	
		//pRequestParamMap.put("searchAgentId", "AG0002");
		
		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcSubscribersMgmtListJson : 선불  대리점가입자이용현황(통계) 목록조회");
		
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
		
    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = sttcService.getAgencySttcSubscribersMgmtList(pRequestParamMap);
	    	 
	    	 HashMap maskFields = new HashMap();
		 	 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름

		 		
		 		
		 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 	 
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
	
	@RequestMapping(value = "/pps/agency/sttcmgmt/SttcSubscribersMgmtListExcel.json" )
	public String selectAgencySttcSubscribersMgmtListExcelJson( ModelMap model, 
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
    	
    	String lvlCd = loginInfo.getUserOrgnLvlCd();
		String typeCd = loginInfo.getUserOrgnTypeCd();
		if (typeCd.equals("20") && lvlCd.equals("10")) {
			pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
			pRequestParamMap.put("searchAgentId", loginInfo.getUserOrgnId());
		}
		//pRequestParamMap.put("searchAgentId", "AG0002");
		
		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcSubscribersMgmtListExcelJson : 선불 대리점  가입자이용현황(통계) 목록조회 엑셀출력");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
       	try{	
       		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
       		sttcService.getAgencySttcSubscribersMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
       	}catch(Exception e){
       		throw new MvnoErrorException(e);
       	}
	    	
	    	
	 		
    	return "jsonView";
	}
	
}
