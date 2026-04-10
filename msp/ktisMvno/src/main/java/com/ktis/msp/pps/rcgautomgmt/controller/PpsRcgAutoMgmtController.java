package com.ktis.msp.pps.rcgautomgmt.controller;

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
import com.ktis.msp.pps.rcgautomgmt.service.PpsRcgAutoMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class Name : PpsRcgAutoMgmtController.java
 * @Description : PpsRcgAutoMgmt Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.03.16           최초생성
 *
 * @author 
 * @since 2016.03.16 
 * @version 1.0
 */

@Controller
public class PpsRcgAutoMgmtController extends BaseController {
	@Autowired
	private PpsRcgAutoMgmtService rcgAutoMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsHdofcCommonService ppsCommonService;
	
	@Autowired
	private MaskingService  maskingService;
	
//	@Autowired
//	private FileReadService fileReadService;


	

	/**
	 * 자동충전관리 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgAutoInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 자동충전관리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/rcgautomgmt/hdofc_rcgautomgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 자동충전관리 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoInfoMgmtList.json" )
	public String selectRcgAutoInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgAutoMgmtController.RcgAutoInfoMgmtListJson : 자동충전관리 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgAutoMgmtService.getRcgAutoInfoMgmtList(pRequestParamMap);
	    	 
	    	HashMap maskFields = new HashMap();
	 	 	maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	maskFields.put("subscriberNo", "MOBILE_PHO");
	 	 	maskFields.put("adminNm", "CUST_NAME");
	 	 	
	 	 		//maskFields.put("userSsn", "SSN"); // 
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
	 * 선불 본사 자동충전관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoInfoMgmtListExcel.json" )
	public String selectRcgAutoInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcRcgAutoMgmtController.selectRcgAutoMgmtListExcelJson: 선불 본사 자동충전관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	rcgAutoMgmtService.getRcgAutoInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 * 자동충전관리 등록
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/PpsRcgAutoReg.json" )
	public String goPpsRcgAutoReg( ModelMap model, 
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
		
	
		logger.info("PpsHdofcRcgAutoMgmtController.goPpsRcgAutoReg : 자동충전관리 등록 ");
	
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
	    	
	    	resultMap = rcgAutoMgmtService.ppsRcgAutoReg(pRequestParamMap);
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
	 * 자동충전변경이력 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoHistInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectRcgAutoHistInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 자동충전변경이력 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/rcgautomgmt/hdofc_rcgautomgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 자동충전변경이력 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoHistInfoMgmtList.json" )
	public String selectRcgAutoHistInfoMgmtListJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgAutoMgmtController.RcgAutoHistInfoMgmtListJson : 자동충전변경이력 목록조회");
		
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
	    	
	    	 List<?> resultList = rcgAutoMgmtService.getRcgAutoHistInfoMgmtList(pRequestParamMap);
	    	 
	    	HashMap maskFields = new HashMap();
	 	 	maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	maskFields.put("subscriberNo", "MOBILE_PHO");
	 	 	maskFields.put("hisAdminNm", "CUST_NAME");
	 	 	
	 	 		//maskFields.put("userSsn", "SSN"); // 
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
	 * 선불 본사 자동충전변경이력 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
		
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoHistInfoMgmtListExcel.json" )
	public String selectRcgAutoHistInfoMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap ) {
		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	
    	logger.info("PpsHdofcRcgAutoMgmtController.selectRcgAutoHistInfoMgmtListExcelJson: 선불 본사 자동충전변경이력 목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	rcgAutoMgmtService.getRcgAutoHistInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	return "jsonView";
	}
	
	/**
	 * 대리점명 조회해서 대리점 콤보박스 옵션 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json" )
	public String selectPpsRcgAutoAgentListJson( ModelMap model,
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
			pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		}
		
		logger.info("대리점명목록조회(자동충전관리 용)");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	//----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
	    	 List<?> resultList = rcgAutoMgmtService.getSelectPpsRcgAutoAgentList(pRequestParamMap);
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
	 *  요금제 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/ppsRcgAutoSocList.json" )
	public String selectPpsFeatureCodeListJson( ModelMap model,
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
		
		
		
		logger.info("PpsHdofcRcgAutoMgmtController.selectPpsRcgAutoSocListJson : 자동충전관리 요금제리스트 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
			List<?> resultList = rcgAutoMgmtService.getPpsRcgAutoSocListData(pRequestParamMap);
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
	 * RS요금제인지 체크
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/rcgautomgmt/rcgAutoPps35Chk.json" )
	public String selectRcgAutoPps35ChkJson( ModelMap model, 
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
    	
    	
    	
    	
		logger.info("PpsHdofcRcgAutoMgmtController.selectRcgAutoPps35ChkJson : 자동충전관리 RS여부 조회");
		
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
	    	
	    	 List<?> resultList = rcgAutoMgmtService.getRcgAutoPps35Chk(pRequestParamMap);
	    	 
	    	HashMap maskFields = new HashMap();
	 	 	maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 	maskFields.put("subscriberNo", "MOBILE_PHO");
	 	 		//maskFields.put("userSsn", "SSN"); // 
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
	
}
