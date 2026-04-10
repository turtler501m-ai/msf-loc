package com.ktis.msp.pps.hdofccustmgmt.controller;


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

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;

/**
 * @Class Name : MnfctMgmtXXController.java
 * @Description : MnfctMgmtXX Controller Class
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
public class PpsHdofcCommonMgmtController  extends BaseController {
	
	
	@Autowired
	PpsHdofcCommonService ppsHdofcCommonService;
	
	
	
	
	
	
	/**
	 * 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCmsBankList.json" )
	public String getCmsBankList( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
		
		logger.info(" PpsHdofcCustMgmtController.selectPpsFeatureAddtionCodeListJson: CMS은행 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		//----------------------------------------------------------------
		// Login check
		//----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	  	 
		try{
	  	 List<?> resultList = ppsHdofcCommonService.getCmsBankList(pRequestParamMap);
	      	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	  	 
	      	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	
	      	
	
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
	 * 판매점 select리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/commonmgmt/ppsAgentSaleSelect.json" )
	public String ppsAgentSaleSelectJson( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
		
		logger.info("PpsHdofcCustMgmtController.selectPpsFeatureAddtionCodeListJson: 판매점 select리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
  	 
		try {
			List<?> resultList = ppsHdofcCommonService.ppsAgentSaleSelect(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

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
	 * 선불공통코드 select  리스트  
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/commonmgmt/ppsKtCommonCdList.json" )
	public String ppsKtCommonCdListSelectJson( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
		
		logger.info("PpsHdofcCustMgmtController.ppsKtCommonCdListSelectJson: 선불공통코드 select리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
  	 
		try {
			List<?> resultList = ppsHdofcCommonService.getppsKtCommonCdList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * 선불 콤보코드 selectbox 리스트 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/commonmgmt/PpsCodeComboList.json" )
	public String selectPpsCodeComboListJson( ModelMap model,
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCodeComboListJson: 선불공통코드 select리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
  	 
		try{
			List<?> resultList = ppsHdofcCommonService.getPpsCodeComboList(pRequestParamMap);
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
	 * 선불 설명 리스트 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/commonmgmt/PpsCodeDescInfo.json" )
	public String selectPpsCodeDescInfoJson( ModelMap model,
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCodeComboListJson: 선불설명내역");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
  	 
		try{
			List<?> resultList = ppsHdofcCommonService.getPpsCodeDescInfoList(pRequestParamMap);
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

}

