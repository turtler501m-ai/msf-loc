package com.ktis.msp.pps.imgchkmgmt.controller;

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
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.imgchkmgmt.service.PpsAgencyImgChkMgmtService;

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
public class PpsAgencyImgChkMgmtController  extends BaseController {
	@Autowired
	private PpsAgencyImgChkMgmtService   ppsAgencyImgChkMgmtService;
	
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
	 * 개통관리 검수관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/imgchkmgmt/CustImgChkMgmtForm.do", method={POST, GET} )
	public ModelAndView selectCustImgChkMngListForm( ModelMap model, 
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
			logger.info("======  PpsAgencyCustMgmtController.OpenInfoMngForm -- 검수관리목록조회 페이지 호출  ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/agency/imgchkmgmt/agency_imgchkmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 선불 대리점 검수관리 내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/imgchkmgmt/CustImgChkMgmtList.json" )
	public String selectCustImgChkMngListJson( ModelMap model, 
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
 	    
 	   
		logger.info("PpsAgencyCustMgmtController.selectOpenInfoMngListJson 대리점 검수관리내역 페이지 목록");
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
    		
	    	 List<?> resultList = ppsAgencyImgChkMgmtService.getAgencyCustImgChkMngList(pRequestParamMap);
	    	 
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
	 * 선불 대리점 검수관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/agency/imgchkmgmt/CustImgChkMgmtListExcel.json" )
	public String selectCustImgChkMngExcelJson(ModelMap model,
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
	    
	    logger.info("psAgencyCustMgmtController.selectOpenInfoMgmtListExcelJson :선불 대리점 검수관리 목록조회 엑셀출력");
	    
	    try{
	    	// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		ppsAgencyImgChkMgmtService.getAgencyCustImgChkMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 선불 대리점 - 검수관리변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/imgchkmgmt/PpsCustImgChkRegProc.json" )
	public String selectPpsCustImgChkRegProcJson( ModelMap model, 
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
		
		logger.info("PpsAgencyRcgMgmtController.selectAgencyDepositHstListJson : 검수관리 수정");
		
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
    		
    		 resultMap = ppsAgencyImgChkMgmtService.getPpsAgencyCustImgChkChgProc(pRequestParamMap);
    		 
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
