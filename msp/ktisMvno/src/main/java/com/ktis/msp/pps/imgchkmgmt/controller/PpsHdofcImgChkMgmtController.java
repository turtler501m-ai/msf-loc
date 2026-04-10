package com.ktis.msp.pps.imgchkmgmt.controller;

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
import com.ktis.msp.pps.cardmgmt.vo.PpsPinSummaryOpenVo;
import com.ktis.msp.pps.filemgmt.service.PpsFileService;
import com.ktis.msp.pps.filemgmt.vo.PpsCustomerImageVo;
import com.ktis.msp.pps.imgchkmgmt.service.PpsHdofcImgChkMgmtService;





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
public class PpsHdofcImgChkMgmtController  extends BaseController {

	@Autowired
	private PpsHdofcImgChkMgmtService  ppsImgChkService;
	
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
	
	/**
	 * 검수관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkMgmtForm.do" )
	public ModelAndView selectCustImgChkMngListForm(
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
			logger.info("======  PpsImgChkMgmtController.custImgChkMgmtForm -- 검수내역목록조회 페이지 호출  ======");
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
			
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("scanSearchUrl", scanSearchUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			
			modelAndView.setViewName("pps/hdofc/imgchkmgmt/hdofc_imgchkmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 검수관리 목록조회 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json" )
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
    	
    	
    	logger.info("===========================================");
    	logger.info("======  PpsImgChkMngControllerselectCustImgChkMngListJson -- 건수관리 페이지 목록 ======");
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
    		
	    	 List<?> resultList = ppsImgChkService.getCustImgChkMngList(pRequestParamMap);
	    	 
	    		//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
				maskFields.put("userSsn","SSN");
		 		maskFields.put("chkAdminNm", "CUST_NAME");
		 		maskFields.put("regAdminNm", "CUST_NAME");	
				
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
	 * 검수관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkMgmtListExcel.json" )
	public String selectCustImgChkMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :선불 본사 검수관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsImgChkService.getCustImgChkMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 검수관리 검수등록
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsCustImgChkRegProc.json" )
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
    	
		
		logger.info("검수관리 검수등록");
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
	    	
			resultMap= ppsImgChkService.getPpsCustImgChkRegProc(pRequestParamMap);


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
	 * 검수관리 검수수정
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsCustImgChkChgProc.json" )
	public String selectPpsCustImgChkChgProcJson( ModelMap model,
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
    	
		
		logger.info("검수관리 검수수정");
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
	    	
			resultMap= ppsImgChkService.getPpsCustImgChkChgProc(pRequestParamMap);


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
	 * 검수이력관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkGrpMgmtForm.do" )
	public ModelAndView selectCustImgChkGrpMngListForm(
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
			logger.info("======  PpsImgChkMgmtController.custImgChkGrpMgmtForm -- 검수이력관리목록조회 페이지 호출  ======");
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
			modelAndView.setViewName("pps/hdofc/imgchkmgmt/hdofc_imgchkmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 검수이력관리 목록조회 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkGrpMgmtList.json" )
	public String selectCustImgChkGrpMngListJson( ModelMap model, 
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
    	logger.info("======  PpsImgChkMngControllerselectCustImgChkGrpMngListJson -- 검수이력관리 페이지 목록 ======");
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
    		
	    	 List<?> resultList = ppsImgChkService.getCustImgChkGrpMngList(pRequestParamMap);
	    	 
	    		//----------------------------------------------------------------
	 	 		// Masking
	 	 		//----------------------------------------------------------------
	 	 		HashMap maskFields = new HashMap();
	 	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
		 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
				maskFields.put("userSsn","SSN");
				maskFields.put("regAdminNm", "CUST_NAME");
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
	 * 검수이력관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkGrpMgmtListExcel.json" )
	public String selectCustImgChkGrpMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :선불 본사 검수이력관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsImgChkService.getCustImgChkGrpMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 재검수관리 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkReMgmtForm.do" )
	public ModelAndView selectCustImgChkReMngListForm(
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
			logger.info("======  PpsImgChkMgmtController.custImgChkReMgmtForm -- 재검수관리목록조회 페이지 호출  ======");
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
			modelAndView.setViewName("pps/hdofc/imgchkmgmt/hdofc_imgchkmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 재검수관리 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/CustImgChkReMgmtListExcel.json" )
	public String selectCustImgChkReMngExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :선불 본사 재검수관리 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsImgChkService.getCustImgChkReMngExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 자동이체등록 폼 호출 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqMgmtForm.do" )
	public ModelAndView selectPpsAgentCmsReqMgmtForm(
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
			logger.info("======  PpsImgChkMgmtController.selectPpsAgentCmsReqMgmtForm -- 자동이체등록조회 페이지 호출  ======");
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
			
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("scanSearchUrl", scanSearchUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			
			modelAndView.setViewName("pps/hdofc/imgchkmgmt/hdofc_imgchkmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 자동이체등록 목록조회 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqMgmtList.json" )
	public String selectPpsAgentCmsReqMngListJson( ModelMap model, 
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
    	logger.info("======  PpsImgChkMngController.selectPpsAgentCmsReqMngListJson -- 자동이체등록 페이지 목록 ======");
    	logger.info("===========================================");
    	logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
    	printRequest(pRequest);
        logger.info("===========================================");

        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = ppsImgChkService.getPpsAgentCmsReqMngList(pRequestParamMap);
	    	 
    		 //----------------------------------------------------------------
 	 		 // Masking
 	 		 //----------------------------------------------------------------
 	 		 //HashMap maskFields = new HashMap();
 	 		 //maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
	 		 //maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			 //maskFields.put("userSsn","SSN");
 	 		 //maskingService.setMask(resultList, maskFields, pRequestParamMap);
 	 		
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
	 * 자동이체등록 목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqMgmtListExcel.json" )
	public String selectPpsAgentCmsReqMngListExcelJson(ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap)
			 {

		
		// ----------------------------------------------------------------
		// Login check
		// ----------------------------------------------------------------
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap); 
    	
    	
    	
		logger.info("PpsHdofcCustMgmtController.selectCnslDtlsMngExcelJson :선불 본사 자동이체등록 목록조회 엑셀출력");
		
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			ppsImgChkService.getPpsAgentCmsReqMngListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 자동이체등록 세부목록 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqRow.json" )
	public String selectPpsAgentCmsReqRowJson( ModelMap model, 
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
    	logger.info("======  PpsImgChkMngController.selectPpsAgentCmsReqRowJson -- 자동이체등록 세부목록 페이지 목록 ======");
    	logger.info("===========================================");
    	logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
    	printRequest(pRequest);
        logger.info("===========================================");

        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = ppsImgChkService.getPpsAgentCmsReqRow(pRequestParamMap);
	    	 
    		 //----------------------------------------------------------------
 	 		 // Masking
 	 		 //----------------------------------------------------------------
 	 		 //HashMap maskFields = new HashMap();
 	 		 //maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
	 		 //maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			 //maskFields.put("userSsn","SSN");
 	 		 //maskingService.setMask(resultList, maskFields, pRequestParamMap);
 	 		
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
	 * 자동이체등록 예금주명,주민번호 Json
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqCustInfo.json" )
	public String selectPpsAgentCmsReqCustInfoJson( ModelMap model, 
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
    	logger.info("======  PpsImgChkMngController.selectPpsAgentCmsReqRowJson -- 자동이체등록 세부목록 페이지 목록 ======");
    	logger.info("===========================================");
    	logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
    	printRequest(pRequest);
        logger.info("===========================================");

        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
	    	 List<?> resultList = ppsImgChkService.getPpsAgentCmsReqCustInfo(pRequestParamMap);
	    	 
    		 //----------------------------------------------------------------
 	 		 // Masking
 	 		 //----------------------------------------------------------------
 	 		 HashMap maskFields = new HashMap();
 	 		 //maskFields.put("subscriberNo", "MOBILE_PHO"); // 전화번호 
	 		 maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
			 //maskFields.put("userSsn","SSN");
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
	 * 자동이체등록 
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqProc.json" )
	public String selectPpsAgentCmsReqProcJson( ModelMap model,
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
    	
		
		logger.info("자동이체등록");
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
	    	
			resultMap= ppsImgChkService.getPpsAgentCmsReqProc(pRequestParamMap);


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

