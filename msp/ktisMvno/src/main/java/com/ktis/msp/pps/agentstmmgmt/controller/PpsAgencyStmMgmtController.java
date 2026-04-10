package com.ktis.msp.pps.agentstmmgmt.controller;

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
import com.ktis.msp.pps.agentstmmgmt.service.PpsAgencyStmMgmtService;
import com.ktis.msp.pps.agentstmmgmt.vo.PpsAgentStmOpenVo;

import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Class Name : PpsAgencyStmMgmtController.java
 * @Description : PpsAgencyStmMgmtController class
 * @Modification Information
 * @
 * @  수정일			수정자			수정내용
 * @ ---------		---------	-------------------------------
 * @ 2017.05.01		김웅			최초생성
 *
 * @author 김웅
 * @since 2017. 05.01
 * @version 1.0
 */

@Controller
public class PpsAgencyStmMgmtController extends BaseController {
	
	@Autowired
	private PpsAgencyStmMgmtService stmService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
//	@Autowired
//	private MaskingService  maskingService;
	
	@Autowired
	protected EgovPropertyService propertyService;

//	@Autowired
//	private PpsFileService ppsFileService;
	
	/**
	 * 대리점 정산내역
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/agentstmmgmt/agentAgentStmHistoryForm.do", method={POST, GET} )
	public ModelAndView selectAgentStmHistoryInfoMgmtListForm( ModelMap model, 
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
			logger.info("======  PpsAgencyStmMgmtController.selectAgentStmHistoryInfoMgmtListForm -- 대리점 정산내역  ======");
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
			modelAndView.setViewName("pps/agency/agentstmmgmt/agency_agentstmmgmt_0010");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 대리점 정산내역 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/agentstmmgmt/agentAgentStmHistoryList.json" )
	public String selectAgentStmHistoryListJson( @ModelAttribute("searchVO") PpsAgentStmOpenVo searchVO, 
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
 	    pRequestParamMap.put("adminId", loginInfo.getUserId());
 	    
 	    String lvlCd   = loginInfo.getUserOrgnLvlCd();
 	    String typeCd      = loginInfo.getUserOrgnTypeCd();
 	    if(typeCd.equals("20") && lvlCd.equals("10"))
 	    {
 	    	pRequestParamMap.put("loginAgentId", loginInfo.getUserOrgnId());
 	    }
 	    
 	   
		logger.info("PpsAgencyStmMgmtController.selectAgentStmHistoryListJson 대리점 정산내역 조회");
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
    		
    		 List<?> resultList = stmService.getAgentStmHistoryMgmtList(pRequestParamMap);
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
	 * 대리점 정산내역 엑셀
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/agentstmmgmt/agentAgentStmHistoryListExcel.json" )
	public String selectAgentStmHistoryListExcelJson( ModelMap model,
			HttpServletRequest pRequest, HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap )
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
	    
	    logger.info("PpsAgencyStmMgmtController.selectAgentStmHistoryListExcelJson :대리점 정산내역 엑셀");
	    
	    try{
	    	// 대리점 권한
    		if(!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))){
    			throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
    		}
    		stmService.getAgentStmHistoryMgmtListExcel(pResponse, pRequest, pRequestParamMap, propertyService.getString("excelPath"));
    		
		}catch(Exception e){
			//logger.error(e.getMessage());
			 throw new MvnoErrorException(e);
		}
		
    	return "jsonView";
	}
	
	/**
	 * 대리점정산관리
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/agentstmmgmt/agentAgentStmSelfForm.do", method={POST, GET} )
	public ModelAndView selectAgentStmSelfInfoMgmtListForm( ModelMap model, 
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
			logger.info("======  PpsAgencyStmMgmtController.selectAgentStmSelfMgmtListForm -- 대리점정산관리  ======");
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
			modelAndView.setViewName("pps/agency/agentstmmgmt/agency_agentstmmgmt_0020");
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 대리점정산관리 조회
	 * @param model
	 * @param pRequest
	 * @param pResponse
	 * @param pRequestParamMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pps/agency/agentstmmgmt/agentAgentStmSelfList.json" )
	public String selectAgentStmSelfListJson( ModelMap model, 
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
 	    
 	   
		logger.info("PpsAgencyStmMgmtController.selectAgentStmHistoryListJson 대리점정산관리 조회");
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
    		
    		 List<?> resultList = stmService.getAgentStmSelfMgmtList(pRequestParamMap);
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
