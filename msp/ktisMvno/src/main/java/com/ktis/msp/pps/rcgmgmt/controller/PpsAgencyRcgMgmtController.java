package com.ktis.msp.pps.rcgmgmt.controller;

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
import com.ktis.msp.pps.cardmgmt.service.PpsHdofcCardMgmtService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.orgmgmt.service.PpsHdofcOrgMgmtService;
import com.ktis.msp.pps.rcgmgmt.service.PpsAgencyRcgMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;



/**
 * @Class Name : PpsAgencyRcgMgmtController.java
 * @Description : PpsAgencyRcgMgmt Controller class
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
public class PpsAgencyRcgMgmtController  extends BaseController {
	
	@Autowired
	private PpsAgencyRcgMgmtService agencyRcgService;
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MaskingService  maskingService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
//	@Autowired
//	private PpsHdofcCommonService  hdofcCommonService;
	
	@Autowired
	private PpsHdofcCardMgmtService cardMgmtService;
	
	@Autowired
	private PpsHdofcOrgMgmtService orgService;
	
		
	
	
	/**
	 * 대리점 충전내역 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/RcgInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectAgencyRcgInfoMgmtListForm( ModelMap model, 
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
			String lvlCd = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
			String typeCd = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
			if (typeCd.equals("20") && lvlCd.equals("10")) {
				String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
				pRequestParamMap.put("searchAgentId", agentId);
				pRequestParamMap.put("agentId", agentId);
			} else {
				String agentId = loginInfo.getUserOrgnId();
				pRequestParamMap.put("searchAgentId", agentId);
				pRequestParamMap.put("agentId", agentId);
			}
			
			logger.info("PpsAgencyRcgMgmtController.selectAgencyRcgInfoMgmtListForm : 충전내역 목록조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/rcgmgmt/agency_rcgmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 선불 대리점 - 충전내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/RcgInfoMgmtList.json" )
	public String selectAgencyRcgInfoMgmtListJson( ModelMap model, 
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
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
		
		
		logger.info("PpsAgencyRcgMgmtController.selectAgencyRcgInfoMgmtListJson : 대리점충전내역 목록조회");
		
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
		
    	 List<?> resultList = agencyRcgService.getAgencyRcgInfoMgmtList(pRequestParamMap);
    	 
    	//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap<String,String> maskFields = new HashMap<String,String>();
	 		//maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 		//maskFields.put("userSsn", "SSN"); // 
	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호
	 		maskFields.put("adminId", "SYSTEM_ID");
	 		
	 		
	 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
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
	 * 대리점 충전내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/RcgInfoMgmtListExcel.json" )
	public String selectRcgInfoMgmtListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		    
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		pRequestParamMap.put("adminId", loginInfo.getUserId());
	    String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
  	    String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
  	    if(typeCd.equals("20") && lvlCd.equals("10"))
  	    {
  	    	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
  	    	//pRequestParamMap.put("searchAgentId", agentId);
  	       	pRequestParamMap.put("agentId", agentId);
	    }
  	    else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
		}
	    
	    logger.info("psAgencyCustMgmtController.selectRcgInfoMgmtListExcelJson :선불 대리점 충전내역 목록조회 엑셀출력");
	    
	    try{
	    	// 대리점 권한
			if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
    		agencyRcgService.getAgencyRcgInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	 /**
	 * 대리점 충전 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/RcgAgencyRechargeMgmtForm.do", method={POST, GET} )
	public ModelAndView goAgencyRcgMgmtRechargeForm( ModelMap model, 
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
			// LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			// loginInfo.putSessionToVo(demoVo);
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info("PpsAgencyRcgMgmtController.goAgencyRcgMgmtRechargeForm : 충전 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/rcgmgmt/agency_rcgmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
		
	}	

	/**
	 * 선불 대리점 - 가상계좌 은행.번호. 예치금 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/PpsRcgAgentInfo.json" )
	public String selectAgencyRcgAgentInfoMgmtJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											)
	{
		
		//2014.09.22 테이블 미 정립.. 추후 수정해야함.
		// Kim Woong
		
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
 	    
 	    
		logger.info("PpsAgencyRcgMgmtController.selectAgencyRcgAgentInfoMgmtJson : 대리점 가상계좌/번호 조회");
		
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
		
    	 List<?> resultList = agencyRcgService.getAgencyRcgAgentInfoMgmt(pRequestParamMap);
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
	 * 선불 대리점 - 예치금내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/RcgDepositHstList.json" )
	public String selectAgencyDepositHstListJson( ModelMap model, 
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
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
	    }
 	   else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
		}
    	
    	
		logger.info("PpsAgencyRcgMgmtController.selectAgencyDepositHstListJson : 대리점충전내역 목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
			List<?> resultList = orgService.getAgentDepositMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

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
	 * 선불 대리점 - 충전
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/agencySearchCtn.json" )
	public String agencySearchCtn( ModelMap model, 
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
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
	    }
 	    else
 	    {
 	    	String agentId =  loginInfo.getUserOrgnId();
 	    	pRequestParamMap.put("agentId", agentId);
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
 	    }
		
		
		
		logger.info("PpsAgencyRcgMgmtController.selectAgencyDepositHstListJson : 충전");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
    	
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
			pRequestParamMap.put("ip", pRequest.getRemoteAddr());
			// admin_id 넣어야함
			// String admin_id = (String)
			// session.getAttribute("CONNECTION_USER_IDENTITY_ABCDEFGHIJKLMN");
			// 대리점아이디 넣어야함
			resultMap = agencyRcgService.agencySearchCtn(pRequestParamMap);

			PpsAgentVo resultVo = cardMgmtService.getAgentInfo(pRequestParamMap);
			if (resultVo == null) {
				resultVo = new PpsAgentVo();
			}

			resultMap.put("deposit", resultVo.getDeposit());

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
	 * 선불 대리점 - 고객전화번호 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/agencyRecharge.json" )
	public String agencyRecharge( ModelMap model, 
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
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
	    }
 	   else
		{
			String agentId = loginInfo.getUserOrgnId();
			pRequestParamMap.put("agentId", agentId);
 	   	  	pRequestParamMap.put("orgnId", agentId);
	    	pRequestParamMap.put("searchAgentId", agentId);
		}
		
		
		
		logger.info("PpsAgencyRcgMgmtController.selectAgencyDepositHstListJson : 고객전화번호조회");
		
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
    		
    		 pRequestParamMap.put("ip", pRequest.getRemoteAddr());
    		
    		 resultMap = agencyRcgService.agencyRecharge(pRequestParamMap);
    		 
    		 PpsAgentVo resultVo = cardMgmtService.getAgentInfo(pRequestParamMap);
	 		 if(resultVo== null){
	 			resultVo = new PpsAgentVo();
	 		}
	 		 
	 		resultMap.put("deposit", resultVo.getDeposit());
 			
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
	 * 대리점 상세 조회
	 * @param model
	 * @param pRequest
	 * @param searchAgentVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/rcgmgmt/PpsAgentInfo.json" )
	public String selectPpsAgentInfoJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsAgentVo")PpsAgentVo searchAgentVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		)
{

		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		printRequest(pRequest);
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		  	   
		String lvlCd   	   = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"));
		String typeCd      = String.valueOf(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"));
		if(typeCd.equals("20") && lvlCd.equals("10"))
		{
		   	String agentId = pRequestParamMap.get("SESSION_USER_ORGN_ID").toString();
		   	pRequestParamMap.put("searchAgentId", agentId);
		 }
		else
		{
			String agentId = loginInfo.getUserOrgnId();
 	    	pRequestParamMap.put("searchAgentId", agentId);
		}
		  	    
		
		logger.info("PpsAgencyRcgMgmtController.selectPpsPinInfoDataJson : 대리점  상세 조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
			PpsAgentVo resultVo = cardMgmtService.getAgentInfo(pRequestParamMap);
			if(resultVo== null){
				resultVo = new PpsAgentVo();
			}
			
			resultMap = makeResultSingleRow(searchAgentVo,resultVo);

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
