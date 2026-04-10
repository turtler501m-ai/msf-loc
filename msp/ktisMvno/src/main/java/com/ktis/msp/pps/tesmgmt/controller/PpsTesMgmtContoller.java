package com.ktis.msp.pps.tesmgmt.controller;

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
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.pps.tesmgmt.service.PpsTesMgmtService;

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
public class PpsTesMgmtContoller extends BaseController {
	
	@Autowired
	private PpsTesMgmtService tesService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	/**
	 *  청소년요금제 충전/조회
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/tes/tesmgmt/RcgTesProc.json" )
	public String selectPpsRcgTesProcJson( ModelMap model,
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
    	pRequestParamMap.put("iIp", pRequest.getRemoteAddr());
    	
		
		logger.info("청소년요금제 충전/조회");
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
			// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			resultMap= tesService.getPpsRcgTesProc(pRequestParamMap);


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
	 * 청소년요금제 충전/조회내역 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/tes/tesmgmt/TesMgmtList.json" )
	public String selectTesMgmtListJson( ModelMap model, 
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
    	
		logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcOpenMgmtListJson : 청소년요금제 충전/조회내역 목록조회");
		
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
	    	
	    	 List<?> resultList = tesService.getTesMgmtList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
       
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
	 * 문자관리 문자전송내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 *
		
	@RequestMapping(value = "/pps/sms/smsmgmt/SmsDumpMgmtListExcel.json" )
	public String selectSmsDumpMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcOrgMgmtController.selectRcgRealCmsMgmtListExcelJson: 문자관리 문자전송내역 엑셀출력");
		
		
    	smsDumpService.getSmsDumpMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	*/
}
