package com.ktis.msp.pps.hdofccustmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.masking.service.MaskingService;
import com.ktis.msp.pps.filemgmt.service.PpsFileService;
import com.ktis.msp.pps.filemgmt.vo.PpsCustomerImageVo;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCustMgmtService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerDiaryVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsCustomerVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsVacVo;

import egovframework.rte.fdl.property.EgovPropertyService;




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
public class PpsHdofcCustMgmtController  extends BaseController {
	
	@Autowired
	PpsHdofcCustMgmtService ppsHdofcCustMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private MaskingService  maskingService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private PpsFileService ppsFileService;
	
	@Autowired
	private LoginService loginService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
/*	
	@RequestMapping(value = "/pps/hdofc/custmgmt/{id}.do", method={POST, GET})
	public String ppsHdofcCustMng(@PathVariable("id") String id){
	
	logger.info(generateLogMsg("================================================================="));
	logger.info(generateLogMsg("test START."+id));
	logger.info(generateLogMsg("================================================================="));

	
	return "pps/hdofc/custmgmt/hdofc_custmgmt_"+id;
	}	
*/	
	
	/**
	 * 개통관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/OpenInfoMgmtForm.do" )
	public ModelAndView selectOpenInfoMngListForm(
			@ModelAttribute("searchVO") PpsCustomerVo searchVO, 
			ModelMap model, 
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

			logger.info("===========================================");
			logger.info("======  PpsHdofcCustMgmtController.OpenInfoMngForm -- 개통내역목록조회 페이지 호출  ======");
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
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	
	/**
	 * 개통정보 목록조회 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/OpenInfoMgmtList.json" )
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
    	
    	
    	logger.info("===========================================");
    	logger.info("======  PpsHdofcCustMngControllerselectOpenInfoMngListJson -- 개통내역 페이지 목록 ======");
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
//    	return "";
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = ppsHdofcCustMgmtService.getOpenInfoMngList(pRequestParamMap);
	    	 
	    		//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 		//maskFields.put("userSsn", "SSN"); // 
	 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	 	 		//
	 	 		logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	 	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	 	        resultMap.put("msg", "");
	 	       
	 	    	          
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
	 
    	 model.addAttribute("result", resultMap);
         logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
         
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 개통내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/OpenInfoMgmtListExcel.json" )
	public String selectOpenInfoMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :선불 본사 개통내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getOpenInfoMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	
	/**
	 * 상담내역목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CnslDtlsMgmtForm.do")
	public ModelAndView selectCnslDtlsMngListForm( 
			@ModelAttribute("searchVO") PpsCustomerDiaryVo searchVO, 
			ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.CnslDtlsMngForm : 상담내역목록조회 페이지 호출 ");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
	
	}
	
	
	
	/**
	 * 상담내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CnslDtlsMgmtList.json" )
	public String selectCnslDtlsMngListJson( ModelMap model, 
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
    	
    	
    	
    	logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngListJson 상담내역 페이지 목록 ");
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
			
	    	 List<?> resultList = ppsHdofcCustMgmtService.getCnslDtlsMngList(pRequestParamMap);
	    	 	//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("reqUserName", "CUST_NAME"); // 고객이름
	 	 		maskFields.put("regAdminNm", "CUST_NAME"); // 접수자
	 	 		maskFields.put("resAdminNm", "CUST_NAME"); // 처리자
	 	 		//maskFields.put("userSsn", "SSN"); // 
	 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	    
	    		 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		    	 
		    	 
		         
		         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	  			 resultMap.put("msg", "");
	    	
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 상담내역 목록조회 엑셀출력 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CnslDtlsMgmtListExcel.json" )
		public String selectCnslDtlsMngExcelJson(ModelMap model,
				HttpServletRequest pRequest, HttpServletResponse pResponse,
				@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson  :상담내역 목록조회 엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getCnslDtlsMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
 		
    	return "jsonView";
	}
	
	
	/**
	 * 문자발송내역목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtForm.do", method={POST, GET} )
	public ModelAndView selectCustSmsDtlsMngListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.CustSmsDtlsMngForm : 문자내역목록조회 페이지 호출 ");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	
	}
	
	/**
	 * 문자발송내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmt.json" )
	public String selectCustSmsDtlsMngListJson( ModelMap model, 
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

    	
    	
    	
    	logger.info("PpsHdofcCustMgmtController.selectCustSmsDtlsMngListJson:문자발송내역 페이지 목록");
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
			
	    	 List<?> resultList = ppsHdofcCustMgmtService.getCustSmsDtlsMngList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
  			 resultMap.put("msg", "");
    	
	    	 
	    	
	         
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	 model.addAttribute("result", resultMap);
         //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 문자발송내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtExcel.json" )
	public String selectCustSmsDtlsMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		
		
		logger.info("PpsHdofcCustMgmtController.selectgetTmntCustMngExcelJson:문자발송내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getCustSmsDtlsMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		
    	return "jsonView";
	}
	
	/**
	 * 문자발송내역목록조회 페이지 호출(과거)
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtFormOld.do", method={POST, GET} )
	public ModelAndView selectCustSmsDtlsMngListFormOld( ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.CustSmsDtlsMngFormOld : 문자내역목록조회 페이지 호출 ");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0030Old");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	
	}
	
	/**
	 * 문자발송내역 목록조회(과거)
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtOld.json" )
	public String selectCustSmsDtlsMngListJsonOld( ModelMap model, 
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

    	
    	
    	
    	logger.info("PpsHdofcCustMgmtController.selectCustSmsDtlsMngListJsonOld:문자발송내역 페이지 목록");
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
			
	    	 List<?> resultList = ppsHdofcCustMgmtService.getCustSmsDtlsMngListOld(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
  			 resultMap.put("msg", "");
    	
	    	 
	    	
	         
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	 model.addAttribute("result", resultMap);
         //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 문자발송내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/CustSmsDtlsMgmtExcelOld.json" )
	public String selectCustSmsDtlsMngExcelJsonOld(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		
		
		logger.info("PpsHdofcCustMgmtController.selectgetTmntCustMngExcelJsonOld:문자발송내역 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getCustSmsDtlsMngExcelOld(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		
    	return "jsonView";
	}
	
	/**
	 * 해지자관리 목록조회폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/TmntCustMgmtForm.do", method={POST, GET} )
	public ModelAndView selectTmntCustMngListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.TmntCustMngForm:해지자관리 목록조회 페이지 호출 ");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	
	}
	
	/**
	 * 해지자관리 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/TmntCustMgmt.json" )
	public String selectTmntCustMngListJson( ModelMap model, 
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
		
		logger.info("PpsHdofcCustMgmtController.selectTmntCustMngListJson:해지자관리 페이지 목록");
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
			
	    	 List<?> resultList = ppsHdofcCustMgmtService.getTmntCustMngList(pRequestParamMap);
	    	//----------------------------------------------------------------
		 	// Masking
		 	//----------------------------------------------------------------
		 		HashMap maskFields = new HashMap();
		 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
		 		
		 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 	 //logger.debug("masking >>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    		
	    		 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			   	
	    		
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	 model.addAttribute("result", resultMap);
	     //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 해지자 관리 목록조회 엑셀출력 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/TmntCustMgmtExcel.json" )
	public String selectgetTmntCustMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    loginInfo.putSessionToParameterMap(pRequestParamMap); 
		
		
		logger.info("PpsHdofcCustMgmtController.selectgetTmntCustMngExcelJson:선불 해지자 관리 목록조회 엑셀출력  엑셀출력 ");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getTmntCustMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}

 		
    	return "jsonView";
	}
	
	
	/**
	 * 선불 통화내역목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPrepaidCdrMngListForm( ModelMap model, 
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

			logger.info("PpsHdofcCustMgmtController.PrepaidCdrMngForm:선불 통화내역목록조회 페이지 호출");
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
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * 선불통화(CDR)관리 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrMgmt.json" )
	public String selectPrepaidCdrMngListJson( ModelMap model, 
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
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectPrepaidCdrMngListJson: 선불CDR관리 페이지 목록");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	

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
//    	return "";
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
    		// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
    	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPrepaidCdrMngList(pRequestParamMap);
	    	 
	    	 //----------------------------------------------------------------
		 	// Masking
		 	//----------------------------------------------------------------
		 		HashMap maskFields = new HashMap();
		 		
		 		maskFields.put("calledNum", "MOBILE_PHO"); // 전화번호 
		 		
		 		
		 	 maskingService.setOnlyMask(resultList, maskFields, pRequestParamMap);
		 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
		 	 
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 model.addAttribute("result", resultMap);
	         //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
	
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 CDR관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrMgmtExcel.json" )
	public String selectPrepaidCdrMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 

		logger.info("PpsHdofcCustMgmtController.selectPrepaidCdrMngExcelJson:선불 본사 CDR관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getPrepaidCdrMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
			
		
    	return "jsonView";
	}
	
	/**
	 * 선불 일사용내역목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrDailyMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPrepaidCdrDailyMngListForm( ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.PrepaidCdrDailyMngForm:선불 일사용내역목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0060");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	
	}
	
	
	
	/**
	 * 선불 일사용내역 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrDailyMgmt.json" )
	public String selectPrepaidCdrDailyMngListJson( ModelMap model, 
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
		
	    
	    logger.info("PpsHdofcCustMgmtController.selectPrepaidCdrDailyMngListJson :선불  일사용내역관리 페이지 목록");
	    // logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
    	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPrepaidCdrDailyMngList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 일사용 내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PrepaidCdrDailyMgmtExcel.json" )
	public String selectPrepaidCdrDailyMgmtExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {
		
		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap); 
		
		
		logger.info("PpsHdofcCustMgmtController.selectPrepaidCdrDailyMgmtExcelJson:선불 일사용 내역 목록조회 엑셀출력 엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getPrepaidCdrDailyMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
			
			
		
    	return "jsonView";
	}
	
	
	
	/**
	 * 고객관리 상세정보 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/selectHdofcCustMgmtInfo.json" )
	public String selectHdofcCustMngInfoJson( ModelMap model, 
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
		
		logger.info("PpsHdofcCustMgmtController.selectHdofcCustMngInfoJson : 고객관리 상세정보 조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
	    
    	
        //----------------------------------------------------------------
    	// 항목 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			// --------------------------------------
			// return JSON 변수 선언
			// --------------------------------------

			Map<String, Object> dataMap = new HashMap<String, Object>();

			// pRequestParamMap : contractNum
			List<?> resultList = ppsHdofcCustMgmtService.getPpsKtJuoFeatureInfoList(pRequestParamMap); // 부가서비스
			dataMap = ppsHdofcCustMgmtService.getHdofcCustMngInfo(pRequestParamMap); // 가입자상세

			//logger.debug(">>>>>>>>>>>>>> test:" + resultList);
			//logger.debug(">>>>>>>>>>>>>> test:" + dataMap);

			dataMap.put("row1", resultList);

			resultMap.put("data", dataMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	
	/**
	 * 가상계좌정보 Tab조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsVacDataTab.json" )
	public String selectgetPpsVacDataTabJson( ModelMap model,
	@ModelAttribute("searchVO")PpsCustomerVo ppsCustomerVo,	
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
	    
	    
	    
		
		logger.info("PpsHdofcCustMgmtController.selectgetPpsVacDataTabJson : 가상계좌정보 Tab조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
    	    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String returnMsg = null;
    	
    	try{
    		// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
    		PpsVacVo ppsVacVo = ppsHdofcCustMgmtService.getPpsVacData(pRequestParamMap);
    		
    		if(ppsVacVo != null)
    		{
    			resultMap =  makeResultSingleRow(ppsCustomerVo, ppsVacVo);
    			
    			if(resultMap != null)
        		{
        			 model.addAttribute("result", resultMap);
        	         logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
        	         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
          			 resultMap.put("msg", returnMsg);
        		}
        		else
        		{
        			ppsVacVo = new PpsVacVo();
        			resultMap = new HashMap<String, Object>();
        			model.addAttribute("result", resultMap);
        			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
         			resultMap.put("msg", returnMsg);
         			resultMap.put("data", ppsVacVo);
        		}
    			
    		}
    		else
    		{
    			ppsVacVo = new PpsVacVo();
    			resultMap = new HashMap<String, Object>();
    			model.addAttribute("result", resultMap);
    			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
     			resultMap.put("msg", returnMsg);
     			resultMap.put("data", ppsVacVo);
    			
    		}
         
    	 } catch (Exception e) {
    		 PpsVacVo ppsVacVo = new PpsVacVo();
 			resultMap = new HashMap<String, Object>();
 			model.addAttribute("result", resultMap);
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
  			resultMap.put("msg", returnMsg);
  			resultMap.put("data", ppsVacVo);
         }
	
    	return "jsonView";
	}

	/**
	 * 실시간 자동이체 tab목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRcgRealCmsTabList.json" )
	public String selectPpsRcgRealCmsTabListJson( ModelMap model, 
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
	    
	    
	    
	   
		logger.info("PpsHdofcCustMgmtController.selectPpsRcgRealCmsTabListJson:선불 실시간 자동이체 tab목록조회");
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
    					
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPpsRcgRealCmsTabList(pRequestParamMap);
	    	 
	    	//----------------------------------------------------------------
		 		// Masking
		 		//----------------------------------------------------------------
		 		HashMap maskFields = new HashMap();
		 		maskFields.put("bankPeopleId", "SSN"); //    주민번호
		 		maskFields.put("bankAccount", "ACCOUNT"); // 계좌번호 
		 		
		 		
		 	 maskingService.setMask(resultList, maskFields, pRequestParamMap);
		 	 logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	    	 
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 충전tab목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRcgTabList.json" )
	public String selectPpsRcgTabListJson( ModelMap model, 
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
	
	   
	    logger.info("PpsHdofcCustMgmtController.selectPpsRcgTabListJson :선불  충전tab목록조회");
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
    	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPpsRcgTabList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * * 통화내역tab목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCdrPpTabList.json" )
	public String selectPpsCdrPpTabListJson( ModelMap model, 
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
	
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCdrPpTabListJson :선불  * 통화내역tab목록조회 ");
		
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
   	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPpsCdrPpTabList(pRequestParamMap);
	    	//----------------------------------------------------------------
		 		// Masking
		 		//----------------------------------------------------------------
		 		HashMap maskFields = new HashMap();
		 		
		 		maskFields.put("calledNum", "MOBILE_PHO"); // 전화번호 
		 		
		 		
		 	 maskingService.setOnlyMask(resultList, maskFields, pRequestParamMap);
		 	 //logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
		 	 
		 	 
	    	 
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
        
	   	 } catch (Exception e) {
	   		resultMap.clear();
	        if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
	        {
	            //logger.error(e);
	        	throw new MvnoErrorException(e);
	        }  
	     }
	   	model.addAttribute("result", resultMap);
	    //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   	return "jsonView";
	}
	
	/**
	 * *일사용내역tab목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCdrPktTabList.json" )
	public String selectPpsCdrPktTabListJson( ModelMap model, 
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
	    
	    
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCdrPktTabListJson :선불  * 일사용내역tab목록조회 =");
		
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
	   	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPpsCdrPktTabList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
	   	 } catch (Exception e) {
	   		 resultMap.clear();
	         if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
	         {
	             //logger.error(e);
	        	 throw new MvnoErrorException(e);
	         }  
	     }
	    model.addAttribute("result", resultMap);
	    //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
	   
	   	return "jsonView";
	}
	
	/**
	 * 문자사용내역tab목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsSmsTabList.json" )
	public String selectPpsSmsTabListJson( ModelMap model, 
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
	
	    
		logger.info("PpsHdofcCustMgmtController.selecPpsSmsTabListJson : 선불   문자사용내역tab목록조회");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
	   	
   	
       //----------------------------------------------------------------
   	// 목록 db select
   	//----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		

		try {
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ppsHdofcCustMgmtService.getPpsSmsTabList(pRequestParamMap);
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
	 * 선불 상담내역tab목록조회 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/getPpsCustomerDiaryTabList.json" )
	public String selectPpsCustomerDiaryTabListJson( ModelMap model, 
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
	    
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerDiaryTabListJson : 선불  상담내역tab목록조회");
		
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		
	  	 	
      //----------------------------------------------------------------
	  	// 목록 db select
	  	//----------------------------------------------------------------
	  	Map<String, Object> resultMap = new HashMap<String, Object>();
  	
  	
		try {
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ppsHdofcCustMgmtService.getPpsCustomerDiaryTabList(pRequestParamMap);
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
	 * 상담내역 등록/변경
	 * @param model
	 * @param ppsCustomerVo
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerDiaryWriteChange.json" )
	public String updatePpsCustomerDiaryJson( ModelMap model,
	@ModelAttribute("searchVO")PpsCustomerVo ppsCustomerVo,	
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
	    pRequestParamMap.put("bonsaId", loginInfo.getUserId());
	    
	    
	    
		
		logger.info("PpsHdofcCustMgmtController.updatePpsCustomerDiaryJson: 상담내역 등록/변경");
		
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
			
			// PpsCustomerDiaryVo ppsCustomerDiaryVo =
			// ppsHdofcCustMgmtService.getPpsCustomerDiaryInfo(pRequestParamMap);
			resultMap = ppsHdofcCustMgmtService.getPpsCustomerDiaryWriteUpdate(pRequestParamMap);

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
	 * 선불 충전처리 
	 * @param model
	 * @param ppsCustomerVo
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRcgProcs.json" )
	public String  selectPpsRcgProcsJson( ModelMap model,
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
		pRequestParamMap.put("adminId", loginInfo.getUserId());


		
		logger.info("PpsHdofcCustMgmtController.selectPpsRcgProcsJson :선불 충전처리 ");
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 처리 
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	
	
	
		try {
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			resultMap = ppsHdofcCustMgmtService.procPpsRecharge(pRequestParamMap);

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
	 * 선불 pin정보 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsPinInfo.json" )
	public String selectPpsPinInfoDataJson( ModelMap model,
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

		
		logger.info("PpsHdofcCustMgmtController.selectPpsPinInfoDataJson : 선불 pin정보  상세 조회");
		
		logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap=ppsHdofcCustMgmtService.getPpsPinInfoData(pRequestParamMap);
			//logger.debug(resultMap.toString());
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
	 * 선불 가상계좌 처리 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsVacProcs.json" )
	public String  selectPpsVacProcsJson( ModelMap model,
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
		pRequestParamMap.put("adminId", loginInfo.getUserId());

		
		logger.info(" PpsHdofcCustMgmtController.selectPpsVacProcsJson : 선불 가상계좌처리");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 처리 
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	
		if("A".equals(pRequestParamMap.get("userType").toString()) && "A".equals(pRequestParamMap.get("opMode").toString()))
		{
			String lvlCd   = loginInfo.getUserOrgnLvlCd();
		    String typeCd      = loginInfo.getUserOrgnTypeCd();
		    if(typeCd.equals("20") && lvlCd.equals("10"))
		    {
		    	pRequestParamMap.put("userNumber", loginInfo.getUserOrgnId());
		    }
		}
		if(pRequestParamMap.get("orgOrgnId") != null && !StringUtils.isBlank(pRequestParamMap.get("orgOrgnId").toString()))
		{
			pRequestParamMap.put("userNumber", pRequestParamMap.get("orgOrgnId"));
		}
		
		try {
			// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			resultMap =  ppsHdofcCustMgmtService.procPpsVac(pRequestParamMap);
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
		
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap+"\n");
		
		return "jsonView";
	}
	
	/**
	 * 실시간 이체 설정
	 * @param model
	 * @param ppsCustomerVo
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerRealCmsSetting.json" )
	public String settingPpsCustomerRealCms( ModelMap model,
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
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsHdofcCustMgmtController.settingPpsCustomerRealCms -- =====");
		
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
			
			resultMap = ppsHdofcCustMgmtService.settingPpsCustomerRealCms(pRequestParamMap);
			
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
	 * 선불 문자전송처리 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsSmsProcs.json" )
	public String  selectPpsSmsProcJson( ModelMap model,
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

		
		logger.info("======  PpsHdofcCustMgmtController.selectPpsSmsProcJson -- 선불 문자전송처리 =====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 처리 
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	if("20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && "10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
	    		pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
	    	}
			
		    resultMap =  ppsHdofcCustMgmtService.procPpsSms(pRequestParamMap);
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
	 * 선불 고객상세화면 잔액갱신
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusDtlRemains.json" )
	public String ppsCusDtlRemains( ModelMap model, 
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
    	
    	
    	
		
		logger.info("PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson 선불 고객상세화면 잔액갱신");
		
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
    	
			String clientIp = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			if (clientIp==null || clientIp.length() == 0
					|| clientIp.equals("unknown")) {
				clientIp = pRequest.getHeader("REMOTE_ADDR");
			}
    	 
			if (clientIp == null || clientIp.length() == 0
					|| clientIp.equals("unknown")) {
				clientIp = pRequest.getRemoteAddr();
			}
    	
			pRequestParamMap.put("reqType", "BS_REMAINS_VIEW");
			pRequestParamMap.put("ip", clientIp);

			resultMap = ppsHdofcCustMgmtService.getPpsCusRemains(pRequestParamMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "");

		} catch (Exception e) {
			//logger.error(generateLogMsg(String.format("잔액갱신 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 선불 고객상세화면 잔액문자전송
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusRemainsSms.json" )
	public String ppsCusRemainsSms( ModelMap model, 
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
	
    	
    	
    	
    	
		logger.info("PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson :선불 잔액 문자발송");
		
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
    		
    		pRequestParamMap.put("event", "REMAINS");
    	
    		resultMap = ppsHdofcCustMgmtService.getPpsRemainsSms(pRequestParamMap);
    		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" 잔액문자전송 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 선불 고객상세화면 충전가능/불가능 변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusRcgFlagChg.json" )
	public String ppsCusRcgFlagChg( ModelMap model, 
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
		
	
		
		logger.info("PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson :선불 고객 Rcg Flag 변경");
		
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
    		
    		pRequestParamMap.put("adminId", loginInfo.getUserId());
	    	resultMap = ppsHdofcCustMgmtService.ppsCusRcgFlagChg(pRequestParamMap);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	        
        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" 충전Flag변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
         	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
         }
    	model.addAttribute("result", resultMap);
       // logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 선불 고객상세화면 문자전송여부 변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusSmsFlagChg.json" )
	public String ppsCusSmsFlagChg( ModelMap model, 
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
	
		
		logger.info("PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson:선불 고객 sms Flag 변경 ");
		
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
    	
	    	resultMap = ppsHdofcCustMgmtService.ppsCusSmsFlagChg(pRequestParamMap);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	        
        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" smsFlag변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 선불 고객상세화면 비자접수여부 변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusVizaFlagChg.json" )
	public String ppsCusVizaFlagChg( ModelMap model, 
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
	
		
		logger.info("PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson:선불 고객 viza Flag 변경 ");
		
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
	    	
	    	resultMap = ppsHdofcCustMgmtService.ppsCusVizaFlagChg(pRequestParamMap);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	        
        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" vizaFlag변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 선불 고객상세화면 문자전송번호 변경(동보전송)
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusSmsPhoneUp.json" )
	public String ppsCusSmsPhoneUp( ModelMap model, 
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
	
		
		logger.info(" PpsHdofcCustMngController.selectPrepaidCdrDailyMngListJson: 선불 고객 sms 동보전송 번호변경");
		
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
    		
	    	resultMap = ppsHdofcCustMgmtService.ppsCusSmsPhoneUp(pRequestParamMap);
	    	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	    	resultMap.put("msg", "");
	        
        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" sms동보전송 번호변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 고객관리 상세 국가 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerNationInfo.json" )
	public String selectPpsCustomerNationJson( ModelMap model,
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

		
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerNationJson : 고객관리 상세 국가 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	   	 
		try{
	   	 List<?> resultList = ppsHdofcCustMgmtService.getPpsCustomerNationInfoData(pRequestParamMap);
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
	 * 고객관리 상세 언어 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerLanguageInfo.json" )
	public String selectPpsCustomerLanguageJson( ModelMap model,
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

		
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerLanguageJson : 고객관리 상세 언어 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
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
				//logger.error(e);
				throw new MvnoErrorException(e);
			}
		}
		 model.addAttribute("result", resultMap);
		 //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		 return "jsonView";
	}
	
	/**
	 * 대리점select 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerOrgnInfo.json" )
	public String selectPpsCustomerOrgnListJson( ModelMap model,
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

		
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerOrgnListJson : 대리점select 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
    	 
    	 try{
    		 List<?> resultList = ppsHdofcCustMgmtService.getPpsCustomerOrgnInfoData(pRequestParamMap);
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
	 *  가상계좌 코드 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsVacCodeList.json" )
	public String selectPpsVacCodeListJson( ModelMap model,
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
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsVacCodeListJson : 고객관리 상세 가상계좌 리스트 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
	  	 List<?> resultList = ppsHdofcCustMgmtService.getPpsVacCodeListData(pRequestParamMap);
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
	 *  요금제 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsFeatureCodeList.json" )
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
		
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsFeatureCodeListJson : 고객관리 상세 요금제 리스트 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
			List<?> resultList = ppsHdofcCustMgmtService.getPpsFeatureCodeListData(pRequestParamMap);
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
	 *  부가서비스 정보 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsFeatureAddtionCodeList.json" )
	public String selectPpsFeatureAddtionCodeListJson( ModelMap model,
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
				
				
				
		
		logger.info("PpsHdofcCustMgmtController.selectPpsFeatureAddtionCodeListJson : 고객관리 상세 부가요금제 리스트");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
	  	 List<?> resultList = ppsHdofcCustMgmtService.getPpsFeatureAddtionCodeListData(pRequestParamMap);
	  	 
	  	 	if(resultList != null)
	  	 	{
	  	 		resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	  	 		
	  	 		if(resultMap != null)
	  	 		{
	  	 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	  	 			resultMap.put("msg", "");
	  	  	      	 
	
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
	 *  가입된 부가서비스 정보 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsMyFeatureAddtionCodeList.json" )
	public String selectPpsMyFeatureAddtionCodeListJson( ModelMap model,
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

		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsMyFeatureAddtionCodeListJson : 가입된 부가요금제 리스트=====");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
	
	

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
  	 
		try {
			List<?> resultList = ppsHdofcCustMgmtService.getPpsKtJuoFeatureInfoList(pRequestParamMap);

			if (resultList != null) {
				resultMap = makeResultMultiRow(pRequestParamMap, resultList);
				if (resultMap != null) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

					
				} else {
					resultMap = new HashMap<String, Object>();
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
					resultMap.put("msg", returnMsg);
				}
			} else {
				resultMap = new HashMap<String, Object>();
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
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
	 * 선불 고객상세화면 국적/SMS 언어변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusLangUpdatePop.json" )
	public String ppsCusLangUpdatePop( ModelMap model, 
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
    	
    	
		
		logger.info("PpsHdofcCustMngController.ppsCusLangUpdatePop: 선불 고객 국적/SMS 언어변경");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	   
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String returnMsg = null;
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		resultMap = ppsHdofcCustMgmtService.ppsCusLangUpdatePop(pRequestParamMap);
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
    	
	    	
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" 국적/SMS 변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 * 대리점 일괄변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsAllAgentChg.json" )
	public String selectPpsCusLangUpdatePop( ModelMap model, 
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
    	if(loginInfo != null)
    	{
    		pRequestParamMap.put("adminId", loginInfo.getUserId());
    	}
    	
	
		logger.info("PpsHdofcCustMngController.selectPpsCusLangUpdatePop : 대리점일괄변경 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String returnMsg = null;
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
    		
    		//ip가져와야함
    		if(pRequest != null && pRequest.getRemoteAddr() != null)
    		{
    			pRequestParamMap.put("ip", pRequest.getRemoteAddr());
    		}
    		else
    		{
    			pRequestParamMap.put("ip", "");
    		}
	    	resultMap = ppsHdofcCustMgmtService.getPpsAllAgentChg(pRequestParamMap);
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
        
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" 국적/SMS 변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
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
	 *  CMS 정보 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsUserCmsInfo.json" )
	public String selectPpsHdofcUserCmsInfo( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse,
	@ModelAttribute("ppsCustomerVo")PpsCustomerVo customerCmsVo,
	@RequestParam Map<String, Object> pRequestParamMap
	) 
{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsHdofcUserCmsInfo : 고객관리 상세 CMS 정보 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
	
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	  	 
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			PpsCustomerVo resultVo = ppsHdofcCustMgmtService.getPpsHdofcUserCmsInfo(pRequestParamMap);
			
			//----------------------------------------------------------------
	 		// Masking
	 		//----------------------------------------------------------------
	 		HashMap maskFields = new HashMap();
	 		//maskFields.put("cmsUserSsn", "SSN"); //    주민번호
	 		maskFields.put("cmsAccount", "ACCOUNT"); // 계좌번호 
	 		maskFields.put("cmsUserName", "CUST_NAME"); // CMS예금주명 
	 		
	 		
	 		maskingService.setMask(resultVo, maskFields, pRequestParamMap);
	 		
	 		//logger.debug(">>>>>>>>>>>>>> resultVo:" + resultVo.toString());
	 		
			
			resultMap = makeResultSingleRow(customerCmsVo,resultVo);
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
	 * 통화내역 TAB 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCdrPpTabListExcel.json" )
	public String selectPpsCdrPpTabListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap) 
	{
	
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		    	
		logger.info(" PpsHdofcCustMgmtController.selectPpsCdrPpTabListExcelJson : 선불   통화내역tab목록조회 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			ppsHdofcCustMgmtService.getPpsCdrPpTabListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	
	/**
	 * 일사용내역 탭 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCdrPktTabListExcel.json" )
	public String selectPpsCdrPktTabListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap) 
	{
	
		//----------------------------------------------------------------
	   	// Login check
	   	//----------------------------------------------------------------
	   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		logger.info("PpsHdofcCustMgmtController.selectPpsCdrPktTabListExcelJson: 선불  * 일사용내역tab목록조회 엑셀출력 ");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			ppsHdofcCustMgmtService.getPpsCdrPktTabListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
    	 
		return "jsonView";
	}
	
	/**
	 * 개통관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/HdofcCustMgmtInfoDetailForm.do", method={POST, GET} )
	public ModelAndView hdofcCustMgmtInfoDetailForm( ModelMap model, 
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse, 
	@RequestParam Map<String, Object> pRequestParamMap
	) 
	{
	
		ModelAndView modelAndView = new ModelAndView();

		try {
			
			logger.info("PpsHdofcCustMgmtController.hdofcCustMgmtInfoDetailForm : 고객 정보 상세");
			
			//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
			//printRequest(pRequest);
			
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			String scanSearchUrl =  propertiesService.getString("scan.search.url");			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			
			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("scanSearchUrl", scanSearchUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0070");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
	
	}
	
	
	/**
	 * 선불 상담내역tab상세 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/getPpsCustomerDiaryTabDetail.json" )
	public String selectPpsCustomerDiaryTabDetailJson( ModelMap model,
	@ModelAttribute("searchVO")PpsCustomerVo ppsCustomerVo,	
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

		
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerDiaryTabDetailJson :선불  상담내역tab상세");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	  	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String returnMsg = null;
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		PpsCustomerDiaryVo ppsCustomerDiaryVo = ppsHdofcCustMgmtService.getPpsCustomerDiaryTabDetail(pRequestParamMap);
    		
    		if(ppsCustomerDiaryVo != null)
    		{
    			resultMap =  makeResultSingleRow(ppsCustomerVo, ppsCustomerDiaryVo);
    			
    			if(resultMap != null)
        		{
        			
        	         //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
        	         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
          			 resultMap.put("msg", returnMsg);
        		}
        		else
        		{
        			ppsCustomerDiaryVo = new PpsCustomerDiaryVo();
        			resultMap = new HashMap<String, Object>();
        			
        			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
         			resultMap.put("msg", returnMsg);
         			resultMap.put("data", ppsCustomerDiaryVo);
        		}
    			
    		}
    		else
    		{
    			ppsCustomerDiaryVo = new PpsCustomerDiaryVo();
    			resultMap = new HashMap<String, Object>();
    			
    			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
     			resultMap.put("msg", returnMsg);
     			resultMap.put("data", ppsCustomerDiaryVo);
    			
    		}
         
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
	 * 개통리스트 대리점변경
	 * @param model
	 * @param ppsCustomerVo
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsAgentInfoChg.json" )
	public String selectPpsAgentInfoChg( ModelMap model,	
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
	    pRequestParamMap.put("ip", pRequest.getRemoteAddr());
	    
	    
	    
		
		logger.info("PpsHdofcCustMgmtController.selectPpsAgentInfoChg : 개통대리점 대리점변경");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	
	
	
		try {
			// 본사, 대리점 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			// PpsCustomerDiaryVo ppsCustomerDiaryVo =
			// ppsHdofcCustMgmtService.getPpsCustomerDiaryInfo(pRequestParamMap);
			resultMap = ppsHdofcCustMgmtService.getPpsAgentInfoChg(pRequestParamMap);

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
	 *  은행계좌 코드 가져오기 실시간이체용
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRcgCodeList.json" )
	public String selectPpsRcgCodeListJson( ModelMap model,
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
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsRcgCodeListJson : 은행계좌 리스트 실시간이체용");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
		  	 List<?> resultList = ppsHdofcCustMgmtService.getPpsRcgCodeListData(pRequestParamMap);
		  	 if(resultList != null)
		  	 {
		  		resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		  		if(resultMap != null)
		  		{
		  			 resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		  			 resultMap.put("msg","");
		  	     	 
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
	 * 선불고객 수동 등록
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerProc.json" )
	public String selectPpsCustomerProcJson( ModelMap model,	
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
		    //pRequestParamMap.put("ip", pRequest.getRemoteAddr());
		    
		    
		    
			
			logger.info(" PpsHdofcCustMgmtController.selectPpsCustomerProcJson : 선불 고객 수동 등록 ");
			
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
		    	
				resultMap = ppsHdofcCustMgmtService.getPpsCustomerProc(pRequestParamMap);
			
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
	 * 예치금 충전 설정
	 * @param model
	 * @param ppsCustomerVo
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCustomerDpRcgSetting.json" )
	public String settingPpsCustomerDpRcg( ModelMap model,
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
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsHdofcCustMgmtController.settingPpsCustomerDpRcg -- =====");
		
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
	    	
			resultMap = ppsHdofcCustMgmtService.settingPpsCustomerDpRcg(pRequestParamMap);
			
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
	 *  예치금 충전 정보 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsUserDpRcgInfo.json" )
	public String selectPpsHdofcUserDpRcgInfo( ModelMap model,
	HttpServletRequest pRequest, 
	HttpServletResponse pResponse,
	@ModelAttribute("ppsCustomerVo")PpsCustomerVo customerCmsVo,
	@RequestParam Map<String, Object> pRequestParamMap
	) 
{
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToParameterMap(pRequestParamMap);
		
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsHdofcUserDpRcgInfo : 고객관리 상세 예치금 충전 정보 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	  	 
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
			
			PpsCustomerVo resultVo = ppsHdofcCustMgmtService.getPpsHdofcUserDpRcgInfo(pRequestParamMap);
			
			resultMap = makeResultSingleRow(customerCmsVo,resultVo);
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
	 * 서식지 관리 폼 호출
	 * @param searchVO
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/imgFileInfoMgmtForm.do" )
	public ModelAndView selectImgFileInfoMgmtForm(
			@ModelAttribute("searchVO") PpsCustomerImageVo searchVO, 
			ModelMap model, 
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

			logger.info("===========================================");
			logger.info("======  PpsFileMgmtController.selectImgFileInfoMgmtForm -- 서식지목록조회 페이지 호출  ======");
			logger.info("===========================================");
			logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			printRequest(pRequest);
			logger.info("===========================================");

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			
			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("maskingYn", maskingYn);		
			model.addAttribute("scanSearchUrl", scanSearchUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);	
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0090");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 서식지 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/imgFileInfoMgmtList.json" )
	public String selectPpsCustomerImageInfoList( ModelMap model, 
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
    	
    	
    	
    	logger.info("PpsHdofcCustMgmtController.selectPpsCustomerImageInfoList선불 본사 서식지 페이지 목록 ");
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
    		
	    	 List<?> resultList = ppsFileService.getPpsCustomerImageInfoList(pRequestParamMap);
	    	 	//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("recIdNm", "CUST_NAME"); // 처리자
	 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	    
	    		 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		    	 
		    	 
		         
		         resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	  			 resultMap.put("msg", "");
	    	
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
		
	@RequestMapping(value = "/pps/hdofc/custmgmt/imgFileInfoMgmtListExcel.json" )
	public String selectPpsCustomerImageInfoListExcel(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectPpsCustomerImageInfoListExcel :선불 본사 서식지 페이지 목록 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsFileService.getPpsCustomerImageInfoListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		

		
    	return "jsonView";
	}
	
	/**
	 * 실시간이체 PayInfo
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRealPayInfoInfo.json" )
	public String selectPpsRealPayInfoList( ModelMap model,
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
		pRequestParamMap.put("adminId", loginInfo.getUserId());
		
		
		
		logger.info("  PpsHdofcCustMgmtController.settingPpsCustomerDpRcg -- =====");
		
		logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
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
	    	
			List<?> resultList = ppsHdofcCustMgmtService.getPpsRealPayInfoList(pRequestParamMap);
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			logger.info(">>>>pRequestParamMap.toString() : "+ resultMap.toString());
		}  catch (Exception e) {
			 resultMap.clear();
	         if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
	         {
	             //logger.error(e);
	        	 throw new MvnoErrorException(e);
	         }  
	     }
		model.addAttribute("result", resultMap);
		//logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		return "jsonView";
	}
	
	/**
	 * 서식지관리 목록조회 탭 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json" )
	public String selectPpsCustomerImageInfoTabListJson( ModelMap model, 
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
	
	   
	    logger.info("PpsHdofcCustMgmtController.selectPpsCustomerImageInfoTabListJson :선불  서식지관리 tab목록조회");
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
    	
    		 List<?> resultList = ppsFileService.getPpsCustomerImageInfoTabList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	    	 
	    	 
         
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	/**
	 * 실시간이체등록내역 목록조회 페이지 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRealPayinfoMgmtForm.do")
	public ModelAndView selecPpsRealPayinfoListForm( 
			@ModelAttribute("searchVO") PpsCustomerDiaryVo searchVO, 
			ModelMap model, 
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

			
			logger.info("PpsHdofcCustMgmtController.CnslDtlsMngForm : 실시간이체등록내역 페이지 호출 ");
			//logger.info(">>>>pRequestParamMap.toString() : "+ pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0080");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}
    	
	
	}
	
	/**
	 * 실시간이체등록내역 리스트
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRealPayinfoList.json" )
	public String selectPpsRealPayinfoMenuList( ModelMap model, 
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
    	
    	logger.info("PpsAgencySttcMgmtContoller.selectAgencySttcOpenMgmtListJson : 실시간이체등록내역 목록조회");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());

    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPpsRealPayinfoMenuList(pRequestParamMap);
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	 	 		//
	 	 		logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	 	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	 	        resultMap.put("msg", "");
	 	       
	 	    	          
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
	 
    	 model.addAttribute("result", resultMap);
         logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
         
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 개통내역 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsRealPayinfoListExcel.json" )
	public String getPpsRealPayinfoListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :실시간이체등록내역 엑셀출력");
		
		try{
			// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
		
			ppsHdofcCustMgmtService.getPpsCustCmsListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		

			
 	 		
    	 
		
    	return "jsonView";
	}
	
	
	/**
	 * 개통관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/Pps35InfoMgmtForm.do" )
	public ModelAndView selectPps35InfoMgmtForm(
			@ModelAttribute("searchVO") PpsCustomerVo searchVO, 
			ModelMap model, 
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

			logger.info("===========================================");
			logger.info("======  PpsHdofcCustMgmtController.OpenInfoMngForm -- pps35관리 목록조회 페이지 호출  ======");
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
			modelAndView.setViewName("pps/hdofc/custmgmt/hdofc_custmgmt_0100");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 *  PPS35 요금제 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsPPS35SocList.json" )
	public String selectPpsPPS35SocListJson( ModelMap model,
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
		
		
		
		logger.info("PpsHdofcCustMgmtController.selectPpsFeatureCodeListJson : PPS35관리 PPS35요금제 리스트 ");
		
		//logger.info(">>>>pRequestParamMap.toString() : "+ String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
		
		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String returnMsg = null;
	  	 
		try{
			List<?> resultList = ppsHdofcCustMgmtService.getPpsPPS35SocListData(pRequestParamMap);
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
	 * 개통정보 목록조회 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/Pps35InfoMgmtList.json" )
	public String selectPps35InfoMgmtListJson( ModelMap model, 
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
    	
    	
    	logger.info("===========================================");
    	logger.info("======  PpsHdofcCustMngControllerselectPps35InfoMgmtListJson -- PPS35관리 페이지 목록 ======");
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
//    	return "";
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = ppsHdofcCustMgmtService.getPps35InfoMgmtList(pRequestParamMap);
	    	 
	    		//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
	 	 		//maskFields.put("userSsn", "SSN"); // 
	 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
	 	 		
	    	 resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
	 	 		//
	 	 		logger.debug(">>>>>>>>>>>>>> resultList:" + resultList.toString());
	 	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
	 	        resultMap.put("msg", "");
	 	       
	 	    	          
    	 } catch (Exception e) {
    		 resultMap.clear();
             if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
             {
                 //logger.error(e);
            	 throw new MvnoErrorException(e);
             }  
         }
	 
    	 model.addAttribute("result", resultMap);
         logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
         
    	return "jsonView";
	}
	
	/**
	 * 선불 본사 PPS35 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/Pps35InfoMgmtListExcel.json" )
	public String selectPps35InfoMgmtListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectPps35InfoMgmtListExcelJson :선불 본사 PPS35관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsHdofcCustMgmtService.getPps35InfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	
	/**
	 * 선불 고객상세화면 체류기간변경
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/custmgmt/PpsCusStayExpirUpdatePop.json" )
	public String ppsCusStayExpirUpdatePop( ModelMap model, 
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
    	
    	
		
		logger.info("PpsHdofcCustMngController.PpsCusStayExpirUpdatePop: 선불 고객 체류기간변경");
		
		//logger.info(">>>>pRequestParamMap.toString() : " + String.valueOf(pRequestParamMap));
		//printRequest(pRequest);
	   
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	String returnMsg = null;
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
    		resultMap = ppsHdofcCustMgmtService.getppsCusStayExpirUpdatePop(pRequestParamMap);
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
    	
	    	
    	 } catch (Exception e) {
         	//logger.error(generateLogMsg(String.format(" 체류기간변경 오류  CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
         	resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
			//	logger.error(e);
				throw new MvnoErrorException(e);
			}
         }
    	model.addAttribute("result", resultMap);
        //logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
    	return "jsonView";
	}
	
	
		
		
}

