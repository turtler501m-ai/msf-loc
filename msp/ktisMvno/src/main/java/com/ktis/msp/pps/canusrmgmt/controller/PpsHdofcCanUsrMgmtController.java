package com.ktis.msp.pps.canusrmgmt.controller;

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
import com.ktis.msp.pps.canusrmgmt.service.PpsHdofcCanUsrMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : PpsHdofcCanUsrMgmtController.java
 * @Description : PpsHdofcCanUsrMgmt Controller Class
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
public class PpsHdofcCanUsrMgmtController extends BaseController {
	@Autowired
	private PpsHdofcCanUsrMgmtService canUsrMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	protected EgovPropertyService propertyService;
	    

//	@Autowired
//	private PpsHdofcCommonService ppsCommonService;
	
	@Autowired
	private MaskingService  maskingService;

	/**
	 * 해지대상자조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPpsCanUsrMgmtForm(ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			ModelAndView modelAndView = new ModelAndView();
			
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" 해지대상자조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/canusrmgmt/hdofc_canusrmgmt_0010");
			
			return modelAndView;
			
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}

	}
	
	
	/**
	 * 해지대상자조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrMgmtList.json" )
	public String selectPpsCanUsrMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("해지대상자조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			
			// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = canUsrMgmtService.getCanUsrMgmtList(pRequestParamMap);
			
			HashMap maskFields = new HashMap();
 	 		maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
 	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 핸드폰번호
 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
 	 		
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
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
	 * 해지대상자조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrMgmtExcel.json" )
	public String selectPpsCanUsrMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		try{
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
			
	    	// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info("해지대상자조회  엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
			canUsrMgmtService.getCanUsrMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * 직권해지처리 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrQueueMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPpsCanUsrQueueMgmtListForm(ModelMap model,
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

			
			logger.info(" 직권해지처리 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/canusrmgmt/hdofc_canusrmgmt_0020");
			
			
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}
		
		return modelAndView;
	}
	
	/**
	 * 직권해지처리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrQueueMgmtList.json" )
	public String selectPpsCanUsrQueueMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("직권해지처리");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			
			// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = canUsrMgmtService.getCanUsrQueueMgmtList(pRequestParamMap);
			
			HashMap maskFields = new HashMap();
			maskFields.put("subLinkName", "CUST_NAME"); // 고객이름
 	 		maskFields.put("subscriberNo", "MOBILE_PHO"); // 핸드폰번호
			maskFields.put("reqNm", "CUST_NAME"); // 요청자
 	 		maskFields.put("confirmNm", "CUST_NAME"); // 처리자
 	 		maskingService.setMask(resultList, maskFields, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
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
	 * 직권해지처리 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/PpsCanUsrQueueExcel.json" )
	public String selectPpsCanUsrQueueExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		try{
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
			
	    	// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info("직권해지처리  엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
			canUsrMgmtService.getCanUsrQueueMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * 해지결과 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrMgmtStatsForm.do", method={POST, GET} )
	public ModelAndView selectCanUsrMgmtStatsForm(ModelMap model,
												HttpServletRequest pRequest,
												HttpServletResponse pResponse,
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			ModelAndView modelAndView = new ModelAndView();
			
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			
			logger.info(" 해지결과 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/canusrmgmt/hdofc_canusrmgmt_0030");
			
			return modelAndView;
			
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 해지결과 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/PpsCanUsrStatsExcel.json" )
	public String selectPpsCanUsrStatsExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		try{
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
			
	    	// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			logger.info("해지결과  엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
			canUsrMgmtService.getCanUsrStatsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * 해지결과
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/CanUsrStatsMgmtList.json" )
	public String selectPpsCanUsrStatsMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("해지결과");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
		try {
			
			// 권한체크
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = canUsrMgmtService.getCanUsrStatsMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
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
	 *  해지대상자조회, 해지내역삭제, 해지처리
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json" )
	public String selectPpsCanUsrQueueProcJson( ModelMap model,
			HttpServletRequest pRequest,
			/*@ModelAttribute("ppsPinInfoVo")PpsPinInfoVo searchPinInfoVo,*/
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
    	
		
		logger.info("해지대상자조회, 해지내역삭제, 해지처리");
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
	    	
			resultMap= canUsrMgmtService.getPpsCanUsrQueue(pRequestParamMap);

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
