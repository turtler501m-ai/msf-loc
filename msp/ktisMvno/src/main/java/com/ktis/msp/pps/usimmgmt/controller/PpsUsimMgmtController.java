package com.ktis.msp.pps.usimmgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.smsmgmt.service.FileReadService;
import com.ktis.msp.pps.usimmgmt.service.PpsUsimMgmtService;
import com.ktis.msp.pps.usimmgmt.vo.PpsUsimVo;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class Name : PpsUsimMgmtController.java
 * @Description : PpsUsimMgmt Controller Class
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
public class PpsUsimMgmtController extends BaseController {
	@Autowired
	private PpsUsimMgmtService usimMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	protected EgovPropertyService propertyService;
	
	@Autowired
	private FileReadService fileReadService;

	@Autowired
	private PpsHdofcCommonService ppsCommonService;


	

	/**
	 * 유심정보조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectUsimInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 유심정보조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/usim/mgmt/usim_mgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 유심정보조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimInfoMgmtList.json" )
	public String selectUsimInfoMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("유심정보목록조회");
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
			
			List<?> resultList = usimMgmtService.getUsimInfoMgmtList(pRequestParamMap);
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
	 * 유심 정보목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimInfoMgmtListExcel.json" )
	public String selectUsimInfoMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("유심 정보목록 조회  엑셀출력");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			usimMgmtService.getUsimInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
	    	 	
	 	return "jsonView";
	}
	
	/**
	 *  유심관리 정보 상세조회
	 * @param model
	 * @param pRequest
	 * @param searchUsimInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/PpsUsimInfo.json" )
	public String selectPpsUsimInfoDataJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsUsimInfoVo")PpsUsimVo searchUsimInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
	   	// Login check
	   	//----------------------------------------------------------------
	   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	   	loginInfo.putSessionToParameterMap(pRequestParamMap);
   	

		logger.info("유심관리  유심정보  상세 조회");
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
			
			PpsUsimVo resultVo= usimMgmtService.getPpsUsimInfoData(pRequestParamMap);

			resultMap = makeResultSingleRow(searchUsimInfoVo,resultVo);

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
	 * 유심입고조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimCreateMgmtForm.do", method={POST, GET} )
	public ModelAndView selectUsimCreateMgmtListForm(ModelMap model,
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

			
			logger.info(" 유심입고조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/usim/mgmt/usim_mgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 유심입고조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimCreateMgmtList.json" )
	public String selectUsimCreateMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("유심입고목록조회");
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
			
			List<?> resultList = usimMgmtService.getUsimCreateMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

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


	/**
	 * 유심 입고목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimCreateMgmtListExcel.json" )
	public String selectUsimCreateMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("유심 입고목록 조회  엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		usimMgmtService.getUsimCreateMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	 	
	 	return "jsonView";
	}
	
	/**
	 *  유심관리 입고 상세조회
	 * @param model
	 * @param pRequest
	 * @param searchUsimInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/PpsUsimCreate.json" )
	public String selectPpsUsimCreateDataJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsUsimInfoVo")PpsUsimVo searchUsimInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
   	// Login check
   	//----------------------------------------------------------------
   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
   	loginInfo.putSessionToParameterMap(pRequestParamMap);
   	

		logger.info("유심관리  유심입고  상세 조회");
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
			
			PpsUsimVo resultVo= usimMgmtService.getPpsUsimCreateData(pRequestParamMap);

			resultMap = makeResultSingleRow(searchUsimInfoVo,resultVo);

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
	 * 유심출고조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimOutMgmtForm.do", method={POST, GET} )
	public ModelAndView selectUsimOutMgmtListForm(ModelMap model,
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

			
			logger.info(" 유심출고조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/usim/mgmt/usim_mgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 유심출고조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimOutMgmtList.json" )
	public String selectUsimOutMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("유심출고목록조회");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			List<?> resultList = usimMgmtService.getUsimOutMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

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


	/**
	 * 유심 출고목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimOutMgmtListExcel.json" )
	public String selectUsimOutMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("유심 출고목록 조회  엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		usimMgmtService.getUsimOutMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	 	
	 	return "jsonView";
	}
	
	/**
	 *  유심관리 출고 상세조회
	 * @param model
	 * @param pRequest
	 * @param searchUsimInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/PpsUsimOut.json" )
	public String selectPpsUsimOutDataJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsUsimInfoVo")PpsUsimVo searchUsimInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
   	// Login check
   	//----------------------------------------------------------------
   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
   	loginInfo.putSessionToParameterMap(pRequestParamMap);
   	

		logger.info("유심관리  유심출고  상세 조회");
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
			
		//	PpsUsimVo resultVo= usimMgmtService.getPpsUsimOutData(pRequestParamMap);

		//	resultMap = makeResultSingleRow(searchUsimInfoVo,resultVo);

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
	 * 유심반품조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimReturnMgmtForm.do", method={POST, GET} )
	public ModelAndView selectUsimReturnMgmtListForm(ModelMap model,
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

			
			logger.info(" 유심반품조회 페이지 호출");
			
			logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/usim/mgmt/usim_mgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 유심반품조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimReturnMgmtList.json" )
	public String selectUsimReturnMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("유심반품목록조회");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			List<?> resultList = usimMgmtService.getUsimBackMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

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


	/**
	 * 유심 반품목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimReturnMgmtListExcel.json" )
	public String selectUsimReturnMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("유심 반품목록 조회  엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
		try{
			// 본사 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			usimMgmtService.getUsimBackMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			throw new MvnoErrorException(e);
		}
	    	 	
	 	return "jsonView";
	}
	
	
	/**
	 * 유심 모델 콤보박스 옵션 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/ppsUsimModelDataList.json" )
	public String selectPpsUsimModeDataListJson( ModelMap model,
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

		
		logger.info("상태목록조회");
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
	    	 List<?> resultList = usimMgmtService.getSelectPpsUsimModeDataList(pRequestParamMap);
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
	 * 유심입고 proc
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/usim/mgmt/ppsUsimCreateProc.json" )
	public String  ppsUsimCreateProcJson( ModelMap model,
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
			
			logger.info("PpsUsimMgmtController.ppsUsimCreateProcJson : 유심 입고 proc");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			   resultMap =  usimMgmtService.ppsUsimCreateProc(pRequestParamMap);
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
	 * 유심출고 proc
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/usim/mgmt/ppsUsimOutProc.json" )
	public String  ppsUsimOutProcJson( ModelMap model,
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
			
			logger.info("PpsUsimMgmtController.ppsUsimOutProc : 유심 출고 proc");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			   resultMap =  usimMgmtService.ppsUsimOutProc(pRequestParamMap);
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
	 * 유심반품 proc
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/usim/mgmt/ppsUsimBackProc.json" )
	public String  ppsUsimBackProcJson( ModelMap model,
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
			
			logger.info("PpsUsimMgmtController.ppsUsimBackProc : 유심 반품 proc");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
		    	
			   resultMap =  usimMgmtService.ppsUsimBackProc(pRequestParamMap);
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
	 * 유심모델관리 조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimModelInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectUsimModelInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 유심정보조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/usim/mgmt/usim_mgmt_0050");
			return modelAndView;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 유심모델관리 정보조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimModelInfoMgmtList.json" )
	public String selectUsimModelInfoMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("유심모델관리목록조회");
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
			
			List<?> resultList = usimMgmtService.getUsimModelInfoMgmtList(pRequestParamMap);
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
	 * 유심모델관리 목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/UsimModelInfoMgmtListExcel.json" )
	public String selectUsimModelInfoMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("유심모델관리목록 조회  엑셀출력");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	try{
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		usimMgmtService.getUsimModelInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		throw new MvnoErrorException(e);
    	}
	    	 	
	 	return "jsonView";
	}
	
	/**
	 *  유심모델관리 정보 상세조회
	 * @param model
	 * @param pRequest
	 * @param searchUsimInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/PpsUsimModelInfo.json" )
	public String selectPpsUsimModelInfoDataJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsUsimInfoVo")PpsUsimVo searchUsimInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
   	// Login check
   	//----------------------------------------------------------------
   	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
   	loginInfo.putSessionToParameterMap(pRequestParamMap);
   	

		logger.info("유심모델관리  유심정보  상세 조회");
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
			
			PpsUsimVo resultVo= usimMgmtService.getPpsUsimInfoData(pRequestParamMap);

			resultMap = makeResultSingleRow(searchUsimInfoVo,resultVo);

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
	 * 유심모델등록/삭제
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * 
	 */
	@RequestMapping(value = "/pps/usim/mgmt/PpsUsimModelInput.json" )
	public String ppsUsimModelInputProc( ModelMap model,
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
			
			logger.info("PpsUsimMgmtController.ppsUsimModelInputProc : 유심모델등록/삭제");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			   resultMap =  usimMgmtService.ppsUsimModelInputProc(pRequestParamMap);
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
	 * 유심관리 proc 호출용
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/getPpsUsimInfoForProcData.json" )
	public String getPpsUsimInfoForProcData( ModelMap model,
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
    	
    	
		
		logger.info("유심관리 proc 호출용");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
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
			
			List<?> resultList = usimMgmtService.getPpsUsimInfoForProcData(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

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
	
	
	
	/**
	 *  문자관리 Excel파일 업로드
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/usim/mgmt/uploadUsimExcelFile.do")
	  public String uploadUsimExcelFile (
	  												ModelMap model, 
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
	    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    String sBaseDir = null;
	  	  sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	  	  logger.info(sBaseDir);
	      	try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		fileReadService.fileUpLoad(pRequest, "file", "PPS" );
	  	        resultMap.put("state", true);
	  			resultMap.put("name", "xxx".replace("'","\\'"));
	  			resultMap.put("size", "" + 111);
	      	
	    	} catch (Exception e) {
	 	 	   		
	    		resultMap.put("state", false);
	    		resultMap.put("name", "lll".replace("'","\\'"));
	    		resultMap.put("size",  111);
	    		model.addAttribute("result", resultMap);
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					 throw new MvnoErrorException(e);
				}
	    	}
			model.addAttribute("result", resultMap);
		    return "jsonViewArray";

	  }
	
	
	/**
	 *  문자관리 Excel파일 읽기
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pps/usim/mgmt/readUsimExcelFile.do")
	  public String readUsimExcelFile (
	  												ModelMap model, 
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
    	
	  	    Map<String, Object> resultMap = new HashMap<String, Object>();
	  	    List<Map<String, Object>> tmpList = new ArrayList<Map<String,Object>>();
	  	    String resultIccIds  = "";
	  	    String sBaseDir = null;
	  	    sBaseDir = propertyService.getString("fileUploadBaseDirectory");
	      	try
	      	{  
	      		// 본사 권한
		    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
		    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
		    	}
		    	
	      		String filePath = sBaseDir+"/PPS/" +pRequestParamMap.get("fileName");
	      		resultIccIds = usimMgmtService.getPpsUsimExcelFileRead(filePath);
	      		resultMap = makeResultMultiRow(pRequestParamMap, tmpList);
	      	
	    	} catch (Exception e) {
	    		resultMap.clear();
				if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
					//logger.error(e);
					 throw new MvnoErrorException(e);
				}
	    	}
	      	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
      		resultMap.put("msg", "");
			resultMap.put("resultIccIds", resultIccIds );
			model.addAttribute("result", resultMap);
			return "jsonView";

	  }
	
	/**
	 * 유심 Excel파일 읽은 S/N 등록
	 * 1. MethodName: insertUpdateMulti
	 * 2. ClassName	: DemoController
	 * 3. Commnet	: 
	 * 4. 작성자	: Administrator
	 * 5. 작성일	: 2014. 9. 25. 오후 3:52:42
	 * </PRE>
	 * 		@return String
	 * 		@param p_jsonString
	 * 		@param model
	 * 		@param pRequest
	 * 		@param pResponse
	 * 		@param pRequestParamMap
	 * 		@return
	 * 		@throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/usimExcelCreateProc.json", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
    public String usimExcelCreateProc(@RequestBody String sJson,
											ModelMap model,
											HttpServletRequest pRequest,
											HttpServletResponse pResponse,
								    		@RequestParam Map<String, Object> pRequestParamMap) {
    	
    	logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	pRequestParamMap.put("adminId", loginInfo.getUserId());
    	
    	try {
		    
    		// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		
		    resultMap = usimMgmtService.ppsUsimCreateProc(pRequestParamMap);
	   
		 	resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
    	} catch (Exception e) {
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			 throw new MvnoErrorException(e);
			
    	}
        
        model.addAttribute("result", resultMap);
		
		return "jsonView";    
    }
	
	/**
	 * 유심 업로드 Excel Sample 다운로드
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/usim/mgmt/usimCreateExcelSample.json" )
	public String usimCreateExcelSample( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("Return Vo [pReqParamMap] = " + pRequestParamMap.toString()));
        logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToParameterMap(pRequestParamMap);
        
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;
        
        try {
        	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
        	String sBaseDir = propertyService.getString("fileUploadBaseDirectory") + "/PPS/";
        	String strFileName = sBaseDir+"PpsUsimSample.xls";
        	
            file = new File(strFileName);
            
            pResponse.setContentType("applicaton/download");
            pResponse.setContentLength((int) file.length());
            
            String encodingFileName = "";
            
            int excelPathLen2 = sBaseDir.length();

            try {
              encodingFileName = URLEncoder.encode(encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            pResponse.setHeader("Cache-Control", "");
            pResponse.setHeader("Pragma", "");

            pResponse.setContentType("Content-type:application/octet-stream;");
            pResponse.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            pResponse.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);
            
            out = pResponse.getOutputStream();
            
            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }
            
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");
 			
 		} catch (Exception e) {
 			//logger.error(e.getMessage());
 			
 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
 			resultMap.put("msg", returnMsg);
 			 throw new MvnoErrorException(e);
	    } finally {
	        if (in != null) {
	          try {
	        	  in.close();
	          } catch (Exception e) {
	        	  throw new MvnoErrorException(e);
	          }
	        }
	        if (out != null) {
	          try {
	        	  out.close();
	          } catch (Exception e) {
	        	  //logger.error(e);
	        	  throw new MvnoErrorException(e);
	          }
	        }
	    }
 		//----------------------------------------------------------------
 		// return json 
 		//----------------------------------------------------------------	
 		model.addAttribute("result", resultMap);
 		return "jsonView";
	}

}
