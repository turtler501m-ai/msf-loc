package com.ktis.msp.pps.cardmgmt.controller;





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
import com.ktis.msp.pps.cardmgmt.service.PpsHdofcCardMgmtService;
import com.ktis.msp.pps.cardmgmt.vo.PpsPinSummaryOpenVo;
import com.ktis.msp.pps.hdofccustmgmt.service.PpsHdofcCommonService;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsAgentVo;
import com.ktis.msp.pps.hdofccustmgmt.vo.PpsPinInfoVo;

import egovframework.rte.fdl.property.EgovPropertyService;





/**
 * @Class Name : PpsHdofcCardMgmtController.java
 * @Description : PpsHdofcCardMgmt Controller Class
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
public class PpsHdofcCardMgmtController extends BaseController {
	@Autowired
	private PpsHdofcCardMgmtService cardMgmtService;


	@Autowired
	private MenuAuthService  menuAuthService;


	
	@Autowired
	protected EgovPropertyService propertyService;
	    

	@Autowired
	private PpsHdofcCommonService ppsCommonService;


	

	/**
	 * 핀정보조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinInfoMgmtListForm(ModelMap model,
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

			
			logger.info(" 핀정보조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			

			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0010");
			
			return modelAndView;
			
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 핀정보조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoMgmtList.json" )
	public String selectPinInfoMgmtListJson( ModelMap model,
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
    	
    	
		
		logger.info("핀정보목록조회");
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
			
			List<?> resultList = cardMgmtService.getPinInfoMgmtList(pRequestParamMap);
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
	 * 핀 정보목록 조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoMgmtListExcel.json" )
	public String selectPinInfoMgmtListExcelJson( ModelMap model, 
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
			
			logger.info("핀 정보목록 조회  엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	  cardMgmtService.getPinInfoMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}


	/**
	 * 핀생성목록조회폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinCreateMgmtListForm( ModelMap model,
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

			
			logger.info("핀생성목록조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0020");
			return modelAndView;
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 핀생성목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateMgmtList.json" )
	public String selectPinCreateMgmtListJson( ModelMap model,
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
    	
    	
    	
		
		logger.info("핀생성목록조회");
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
    		
	    	List<?> resultList = cardMgmtService.getPinCreateMgmtList(pRequestParamMap);
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	    	

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
	 * 핀 생성목록 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateMgmtListExcel.json" )
	public String selectPinCreateMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	    	// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
			
			logger.info("핀 생성목록 엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	
       
	    	cardMgmtService.getPinCreateMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
		
		
    	
	}


	/**
	 * 핀개통목록 조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinOpenMgmtListForm( ModelMap model,
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

			
			logger.info("핀개통목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0040");
			return modelAndView;
		} catch (Exception e) {
			
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 핀 개통 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenMgmtList.json" )
	public String selectPinOpenMgmtListJson( ModelMap model,
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

		
		logger.info("핀개통목록조회");
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
    		
	    	 List<?> resultList = cardMgmtService.getPinOpenMgmtList(pRequestParamMap);
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
	 * 핀개통목록 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenMgmtListExcel.json" )
	public String selectPinOpenMgmtListExcelJson( ModelMap model, 
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
	    	
	    	// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	
			logger.info("핀 개통목록 엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	   	  cardMgmtService.getPinOpenMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
	    		
	 	return "jsonView";
	}

	/**
	 * 핀출고 정보 목록조회 폼 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinReleaseMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinReleaseMgmtForm( ModelMap model,
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

			
			logger.info("핀출고목록조회 페이지 호출");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0030");
			return modelAndView;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 핀출고목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinReleaseMgmtList.json" )
	public String selectPinReleaseMgmtListJson( ModelMap model,
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
    	

		
		logger.info("핀출고목록조회");
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
	    	
	    	 List<?> resultList = cardMgmtService.getPinReleaseMgmtList(pRequestParamMap);
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
	 *  핀출고목록조회 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinReleaseMgmtListExcel.json" )
	public String selectPinReleaseMgmtListExcelJson( ModelMap model, 
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
		
		
		logger.info("핀 개통목록 엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
    	
	    	 cardMgmtService.getPinReleaseMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		//logger.error(e.getMessage());
    		throw new MvnoErrorException(e);
    	}
	    	 
	    	 	
	 		return "jsonView";
	}

	/**
	 * 선불 카드관리 PIN 정보 상세조회
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsPinInfo.json" )
	public String selectPpsPinInfoDataJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsPinInfoVo")PpsPinInfoVo searchPinInfoVo,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
		) 
{

		//----------------------------------------------------------------
    	// Login check
    	//----------------------------------------------------------------
    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
    	loginInfo.putSessionToParameterMap(pRequestParamMap);
    	

		logger.info("선불 카드관리  pin정보  상세 조회");
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
	    	
			PpsPinInfoVo resultVo= cardMgmtService.getPpsPinInfoData(pRequestParamMap);

			resultMap = makeResultSingleRow(searchPinInfoVo,resultVo);

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
	 *  선불카드 관리 핀 정보 등록
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsPinInfoCreate.json" )
	public String selectPpsPinInfoCreateJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsPinInfoVo")PpsPinInfoVo searchPinInfoVo,
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
    	
		
		logger.info("선불 카드관리  pin정보 등록처리");
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
	    	
			resultMap= cardMgmtService.getPpsPinInfoCreate(pRequestParamMap);


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
	 * 핀 출고 처리
	 * @param model
	 * @param pRequest
	 * @param searchPinInfoVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsPinOutProc.json" )
	public String selectPpsPinOutProcJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsPinSummaryOutVo")PpsPinSummaryOpenVo searchPpsPinSummaryOutVo,
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
    	

		logger.info("선불 카드관리  pin 출고처리");
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
	    	
			resultMap= cardMgmtService.getPpsPinOutProc(pRequestParamMap);

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
	 * 생성,출고,개통시 핀정보 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoListExcel.json" )
	public String selectPinInfoListExcelJson( ModelMap model,
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
    	
    	
		
		logger.info("핀정보목록엑셀출력");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	//----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	try{
    		// 본사 권한
	    	if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
	    		throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
	    	}
	    	 cardMgmtService.getPinInfListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    	}catch(Exception e){
    		//logger.error(e.getMessage());
    		throw new MvnoErrorException(e);
    	}
	    	 

	 		return "jsonView";
	}

	/**
	 * 선불 카드 PIN 개통처리
	 * @param model
	 * @param pRequest
	 * @param searchPpsPinSummaryOpenVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsPinOpenProc.json" )
	public String selectPpsPinOpenProcJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsPinSummaryOpenVo")PpsPinSummaryOpenVo searchPpsPinSummaryOpenVo,
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
    	
		
		logger.info("선불 카드관리  pin 개통처리");
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
	    	
			resultMap= cardMgmtService.getPpsPinOpenProc(pRequestParamMap);


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
	 * 대리점명 조회해서 대리점 콤보박스 옵션 가져오기
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json" )
	public String selectPpsAgentInfoDataListJson( ModelMap model,
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
		
		logger.info("대리점명목록조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	//----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
	    	 List<?> resultList = ppsCommonService.getSelectPpsAgentInfoDataList(pRequestParamMap);
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
	 * 관리코드 헤더 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json" )
	public String selectPpsMngCodeHeaderListJson( ModelMap model,
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
    	
		logger.info("관리코드 헤더 목록조회");
		//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		//printRequest(pRequest);
    	
        //----------------------------------------------------------------
    	// 목록 db select
    	//----------------------------------------------------------------
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	
    	try{
	    	 List<?> resultList = cardMgmtService.getMngCodeHeaderList(pRequestParamMap);
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
 * 핀 개통정보 상세 조회
 * @param model
 * @param pRequest
 * @param searchPinSummaryOpenVo
 * @param pResponse
 * @param pRequestParamMap
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsPinOpenSummaryInfo.json" )
	public String selectPpsPinOpenSummaryInfoJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsPinSummaryOpenVo")PpsPinSummaryOpenVo searchPinSummaryOpenVo,
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
    	
    	
		
		logger.info("선불 카드관리  pin개통정보  상세 조회");
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
	    	
			PpsPinSummaryOpenVo resultVo = cardMgmtService.getPpsPinOpenSummaryInfo(pRequestParamMap);

			resultMap = makeResultSingleRow(searchPinSummaryOpenVo,resultVo);

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
	 * 대리점 정보 조회 
	 * @param model
	 * @param pRequest
	 * @param searchAgentVo
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PpsAgentInfo.json" )
	public String selectPpsAgentInfoJson( ModelMap model,
			HttpServletRequest pRequest,
			@ModelAttribute("ppsAgentVo")PpsAgentVo searchAgentVo,
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
    	
		
		logger.info("선불 카드관리  pin개통정보  상세 조회");
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
			
			PpsAgentVo resultVo = cardMgmtService.getAgentInfo(pRequestParamMap);
			
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
	
	/**
	 * 핀생성통계 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateStatsMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinCreateStatsMgmtListForm( ModelMap model,
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

			
			logger.info("핀생성통계조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0050");
			return modelAndView;
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 핀생성통계 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateStatsMgmtList.json" )
	public String selectPinCreateStatsMgmtListJson( ModelMap model,
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
    	
    	
    	
		
		logger.info("핀생성통계");
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
    		
	    	List<?> resultList = cardMgmtService.getPinCreateStatsMgmtList(pRequestParamMap);
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	    	

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
	 * 핀 생성통계 목록 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinCreateStatsMgmtListExcel.json" )
	public String selectPinCreateStatsMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	    	// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
			
			logger.info("핀 생성통계 엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	
       
	    	cardMgmtService.getPinCreateStatsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
		
		
    	
	}
	
	/**
	 * 핀개통통계 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenStatsMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinOpenStatsMgmtListForm( ModelMap model,
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

			
			logger.info("핀개통통계조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0060");
			return modelAndView;
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 핀생성통계 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenStatsMgmtList.json" )
	public String selectPinOpenStatsMgmtListJson( ModelMap model,
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
    	
    	
    	
		
		logger.info("핀개통통계");
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
    		
	    	List<?> resultList = cardMgmtService.getPinOpenStatsMgmtList(pRequestParamMap);
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	    	

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
	 * 핀 개통통계 목록 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinOpenStatsMgmtListExcel.json" )
	public String selectPinOpenStatsMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	    	// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
			
			logger.info("핀 개통통계 엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	
       
	    	cardMgmtService.getPinOpenStatsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
		
		
    	
	}
	
	/**
	 * 핀현황통계 호출
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoStatsMgmtForm.do", method={POST, GET} )
	public ModelAndView selectPinInfoStatsMgmtListForm( ModelMap model,
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

			
			logger.info("핀현황통계조회 페이지 호출");
			
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
			
			// //----------------------------------------------------------------
			// // 1. getAuth
			// //----------------------------------------------------------------
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			
			//메뉴명 가져오기
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.setViewName("pps/hdofc/cardmgmt/hdofc_cardmgmt_0070");
			return modelAndView;
			
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	 * 핀현황통계 목록조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoStatsMgmtList.json" )
	public String selectPinInfoStatsMgmtListJson( ModelMap model,
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
    	
    	
    	
		
		logger.info("핀현황통계");
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
    		
	    	List<?> resultList = cardMgmtService.getPinInfoStatsMgmtList(pRequestParamMap);
	    	resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

	    	

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
	 * 핀 현황통계 목록 엑셀출력
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/hdofc/cardmgmt/PinInfoStatsMgmtListExcel.json" )
	public String selectPinInfoStatsMgmtListExcelJson( ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		try {
			//----------------------------------------------------------------
	    	// Login check
	    	//----------------------------------------------------------------
	    	LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
	    	loginInfo.putSessionToParameterMap(pRequestParamMap);
	    	
	    	// 본사 권한
    		if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
			
			logger.info("핀 현황통계 엑셀출력");
			//logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
			//printRequest(pRequest);
	    	
	    	
       
	    	cardMgmtService.getPinInfoStatsMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
		}catch(Exception e){
			//logger.error(e.getMessage());
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
		
		
    	
	}

}
