package com.ktis.msp.pps.agencycustmgmt.controller;

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
import com.ktis.msp.org.orgmgmt.vo.OrgMgmtPpsVo;
import com.ktis.msp.pps.agencycustmgmt.service.PpsAgencyCustMgmtService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCustMgmtService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerVo;

import egovframework.rte.fdl.property.EgovPropertyService;




/**
 * @Class Name : PpsAgencyCustMgmtController.java
 * @Description : PpsAgencyCustMgmt Controller Class
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
public class PpsAgencyCustMgmtController  extends BaseController {
	@Autowired
	private PpsAgencyCustMgmtService   ppsAgencyCustMgmtService;
	
	@Autowired
	private PpsHdofcCustMgmtService ppsHdofcCustMgmtService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MaskingService  maskingService;
	
//	private javax.servlet.http.HttpSession session;
	
//	@Autowired
//	private PpsHdofcCommonService ppsCommonService;
	
	
	/**
	 * 대리점관리 개통관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/OpenInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectOpenInfoMngListForm( ModelMap model, 
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
			pRequestParamMap.put("adminId", loginInfo.getUserId());

			String lvlCd = loginInfo.getUserOrgnLvlCd();
			String typeCd = loginInfo.getUserOrgnTypeCd();
			if (typeCd.equals("20") && lvlCd.equals("10")) {
				pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
			}

			logger.info("===========================================");
			logger.info("======  PpsAgencyCustMgmtController.OpenInfoMngForm -- 개통내역목록조회 페이지 호출  ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			
			//서식지 노출여부를 얻어온다
			List<?> resultList = ppsAgencyCustMgmtService.getAgencyOpenInfoAgentList(pRequestParamMap);
       	 	String agentDocFlag = "";
			
			if( resultList.size() == 0 ){
				modelAndView.getModelMap().addAttribute("agentDocFlag", "");
	        }else
	        {
	        	modelAndView.getModelMap().addAttribute("agentDocFlag", ((Map) resultList.get(0)));
	        }
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/custmgmt/agency_custmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 선불 대리점 개통내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/OpenInfoMgmtList.json" )
	public String selectOpenInfoMngListJson( ModelMap model, 
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
 	    
 	    String lvlCd   = loginInfo.getUserOrgnLvlCd();
 	    String typeCd      = loginInfo.getUserOrgnTypeCd();
 	    if(typeCd.equals("20") && lvlCd.equals("10"))
 	    {
 	    	pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
 	    }
 	    
 	   
		logger.info("PpsAgencyCustMgmtController.selectOpenInfoMngListJson 대리점 개통내역 페이지 목록");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
    		
	    	 List<?> resultList = ppsAgencyCustMgmtService.getAgencyOpenInfoMngList(pRequestParamMap);
	    	 
	    	//----------------------------------------------------------------
		 		// Masking
		 		//----------------------------------------------------------------
		 		HashMap<String, String> maskFields = new HashMap<String, String>();
		 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
		 		maskFields.put("userSsn", "SSN"); // 
		 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		
		 		
		 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
		 	 
		 	 
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
	 * 선불 대리점 개통태역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/OpenInfoMgmtListExcel.json" )
	public String selectOpenInfoMgmtListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		    
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		pRequestParamMap.put("adminId", loginInfo.getUserId());
	    
	    String lvlCd   = loginInfo.getUserOrgnLvlCd();
	    String typeCd      = loginInfo.getUserOrgnTypeCd();
	    if(typeCd.equals("20") && lvlCd.equals("10"))
	    {
	    	pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
	    }
	    
	    logger.info("psAgencyCustMgmtController.selectOpenInfoMgmtListExcelJson :선불 대리점 개통태역 목록조회 엑셀출력");
	    
	    try{
	    	// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		ppsAgencyCustMgmtService.getAgencyOpenInfoMngListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 선불 대리점 개통상세 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/OpenInfoMgmtDtlList.json" )
	public String selectOpenInfoMngDtlListJson( ModelMap model, 
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
 	    
 	    String lvlCd   = loginInfo.getUserOrgnLvlCd();
 	    String typeCd      = loginInfo.getUserOrgnTypeCd();
 	    if(typeCd.equals("20") && lvlCd.equals("10"))
 	    {
 	    	pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
 	    }
 	    
 	   
		logger.info("PpsAgencyCustMgmtController.selectOpenInfoMngDtlListJson 대리점 개통상세 페이지 목록");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
    		
	    	 List<?> resultList = ppsAgencyCustMgmtService.getAgencyOpenInfoMngDtlList(pRequestParamMap);
	    	 
	    	//----------------------------------------------------------------
		 		// Masking
		 		//----------------------------------------------------------------
		 		HashMap<String, String> maskFields = new HashMap<String, String>();
		 		//maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
		 		//maskFields.put("userSsn", "SSN"); // 
		 		//maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		
		 		
		 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
		 	 
		 	 
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
	 * 국가 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/PpsCustomerNationInfo.json" )
	public String selectAgencyPpsCustomerNationJson( ModelMap model,
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{


		//----------------------------------------------------------------
	  	// Login check
	  	//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		    pRequestParamMap.put("adminId", loginInfo.getUserId());
		    
		    String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		    }
			
			
			
			logger.info("PpsAgencyCustMgmtController.selectAgencyPpsCustomerDiaryTabListJson : 선불  상담내역tab목록조회 ");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
   	 
		try {
			List<?> resultList = ppsHdofcCustMgmtService.getPpsCustomerNationInfoData(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * sms언어리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/PpsCustomerLanguageInfo.json" )
	public String selectAgencyPpsCustomerLanguageJson( ModelMap model,
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{


		//----------------------------------------------------------------
	  	// Login check
	  	//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		    pRequestParamMap.put("adminId", loginInfo.getUserId());
		    
		    String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		    }
			
			
			
			logger.info("PpsAgencyCustMgmtController.selectAgencyPpsCustomerDiaryTabListJson : 선불  상담내역tab목록조회 ");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		
		 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
    	 
    	 try{
    		 List<?> resultList = ppsHdofcCustMgmtService.getPpsCustomerLanguageInfoData(pRequestParamMap);
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
	 * 선불대리점 고객 cms정보조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/PpsUserCmsInfo.json" )
	public String selectPpsAgencyUserCmsInfoJson( ModelMap model,
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap
		) 
	{


		//----------------------------------------------------------------
	  	// Login check
	  	//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		    pRequestParamMap.put("adminId", loginInfo.getUserId());
		    
		    String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("agentId", loginInfo.getUserOrgnId());
		    }
			
			
			
			logger.info("PpsAgencyCustMgmtController.selectPpsAgencyUserCmsInfoJson : 선불 대리점 고객 CMS정보조회 ");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		
		 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
    	 
    	 try{
    		if(!"20".equals(typeCd) || !"10".equals(lvlCd)){
     			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
     		}
    		 
    		 List<?> resultList = ppsAgencyCustMgmtService.getPpsAgencyUserCmsInfo(pRequestParamMap);
        	 
        	 HashMap maskFields = new HashMap();
 	 		//maskFields.put("cmsUserSsn", "SSN"); //    주민번호
 	 		 //maskFields.put("cmsAccount", "ACCOUNT"); // 계좌번호 
 	 		 maskFields.put("cmsUserName", "CUST_NAME"); // CMS예금주명 
 	 		
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
	 * 선불 대리점 - CMS설정요청
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/custmgmt/agencyPpsAgentCmsReqProc.json" )
	public String agencyPpsAgentCmsReqProc( ModelMap model, 
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
 	    
 	    String lvlCd   =  	 String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
 	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
 	    if(typeCd.equals("20") && lvlCd.equals("10"))
 	    {
 	    	String agentId =  pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
 	    	pRequestParamMap.put("agentId", agentId);
	    }
 	   else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
		
 	    logger.info("PpsAgencyCustMgmtController.selectPpsRealPayInfoList : 선불 대리점 CMS설정요청 ");
		
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
    		resultMap = ppsAgencyCustMgmtService.getAgencyPpsAgentCmsReq(pRequestParamMap);
    		 
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
