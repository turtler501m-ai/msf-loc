package com.ktis.msp.cmn.authmgmt.controller;

import java.io.PrintWriter;
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

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.authmgmt.service.AuthMgmtService;
import com.ktis.msp.cmn.authmgmt.vo.AuthMgmtVO;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;

/**
 * @Class Name : AuthMgmtController.java
 * @Description : AuthMgmtController Class
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.01     임지혜          최초생성
 * 
 *
 * @author 임지혜
 */

@Controller
public class AuthMgmtController extends BaseController {

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private AuthMgmtService authMgmtService;

	@Autowired
	private OrgCommonService orgCommonService;
	
	/**
	 * 로그인 화면
	 */
	/**
	 * 사용자 그룹 관리 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrp.do")
	public ModelAndView usrGrp (
			 
								ModelMap model, 
								HttpServletRequest pRequest, 
								HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> pRequestParamMap
								) 
	{
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 사용자 그룹 관리 화면");
		logger.info("===========================================");
		
		try {
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1030_usrGrp");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	
	
	/**
	 * 권한별 그룹 관리 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/grpAuth.do")
	public ModelAndView getAuthGrpList(
			 
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
										) 
	{
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 권한별 그룹 관리 화면");
		logger.info("===========================================");
		
		try {
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1034");
		
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * 프로그램 - 메뉴 연결 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgm.do")
	public ModelAndView getSttcCrdtLoanInfoList(
			 
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
													) 
	{
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 프로그램 목록 조회 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1032_prgm");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	
	/**
	 * 메뉴 등록 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/menu.do")
	public ModelAndView getMenuMgmtList (
			 
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 메뉴 관리 목록 조회 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
	
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1033_menu");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}

	
	/**
	 * 권한별 그룹 관리 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/authGrp.do")
	public ModelAndView getAuthMgmtList(
			 
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
										) 
	{
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 메뉴 관리 목록 조회 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1034_authGrp");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}

	/**
	 * 그룹 찾기
	 */
	@RequestMapping(value = "/cmn/authmgmt/popGrpList.do")
	public ModelAndView getGroupSearchList(
			 
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
											) 
	{
		logger.info("===========================================");
		logger.info("======  AuthMgmtListController.getAuthMgmtList -- 권한별 그룹 관리 화면 ======");
		logger.info("===========================================");
		logger.info(">>>>pRequestParamMap.toString() : " + pRequestParamMap.toString());
		printRequest(pRequest);
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
	
			ModelAndView mv = new ModelAndView();
			mv.addObject("pRequestParamMap",pRequestParamMap);
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
				
			mv.setViewName("/cmn/authmgmt/msp_org_pu_1035_popGrpList");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * 권한별 메뉴 등록
	 */
	@RequestMapping(value = "/cmn/authmgmt/authMenu.do")
	public ModelAndView getAuthMenuList(
			 
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
											) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 권한별 메뉴 등록 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
	
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1036_authMenu");
	
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	
	/**
	 * 사용자특별권한관리
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrMenu.do")
	public ModelAndView getUserSpclAuthList (
			 
												ModelMap model, 
												HttpServletRequest pRequest, 
												HttpServletResponse pResponse, 
												@RequestParam Map<String, Object> pRequestParamMap
												) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 특정 사용자 메뉴 권한 관리 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1037_usrMenu");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	
	
	/**
	 * 그룹리스트 xml 로 조회
	 * @param searchVO
	 * @param model
	 * @return jsonData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cmn/authmgmt/getGrpListXml.do")
	public void getGrpListXml(HttpServletRequest request, 
								 HttpServletResponse response,
								 @RequestParam Map<String, Object> paramMap,
								 ModelMap model) 
	{
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> grpIdList =  authMgmtService.grpIdList();
			
			logger.debug(">>>> AuthMgmtController.getGrpListXml");
			logger.debug(">>>> grpList");
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<data>\n");
			
			for(int i=0; i<grpIdList.size(); i++) {
				
				HashMap<String, String> map = (HashMap<String, String>) grpIdList.get(i);
				
				if(map.get("GRP_NM") == null || "".equals(map.get("GRP_NM"))) {
					sb.append("<item value=\"\" label=\"" + map.get("GRP_NM") + "\" />\n");
				} else {
					sb.append("<item value=\""+ map.get("GRP_ID")+"\" label=\"" + map.get("GRP_NM") + "\" />\n");
				}
				// <item value="6" label="amoeba"/>
				
			}
			
			sb.append("</data>");
			
			response.setContentType("application/xml; charset=UTF-8");
			
			/** 20230518 PMD 조치 */
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write(sb.toString());				
			}catch(Exception e) {
				throw new MvnoErrorException(e);				
			}finally{
				if(out != null){
					try {
						out.close();
					} catch (Exception e) {
						throw new MvnoErrorException(e);
					}				
				}
			}
			
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}

	

	/**
	 * 우편번호 조회 팝업
	 */
	@RequestMapping(value = "/cmn/authmgmt/zipcode.do")
	public ModelAndView searchZipcode (
			 
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 특정 사용자 메뉴 권한 관리 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
	
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			
			mv.setViewName("/cmn/authmgmt/msp_org_pu_1039_zipcode");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	

	/**
	 * 사용자 권한 REPORT
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrAuthReportForm.do")
	public ModelAndView usrAuthReport(
			 
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 사용자 권한 report 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_usrAuthReport");
			
			return mv; 
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	
	/**
	 * 프로그램 등록 화면(추가)
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmReg.do")
	public ModelAndView getPrgmRegList(
			 
													ModelMap model, 
													HttpServletRequest pRequest, 
													HttpServletResponse pResponse, 
													@RequestParam Map<String, Object> pRequestParamMap
													) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 프로그램 등록 화면(추가)");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			mv.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/msp_org_bs_1038_prgmReg");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	
	/**
	 * 프로그램 리스트 조회 팝업
	 */
	@RequestMapping(value = "/cmn/authmgmt/prgmPop.do")
	public ModelAndView prgmPop (
			 
										ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap
										) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller.prgmPop  : prgmPop 화면");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
	
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			
			mv.setViewName("/cmn/authmgmt/msp_org_pu_1038_prgmPop");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	
	/**
	 * 메뉴 등록 화면
	 */
	@RequestMapping(value = "/cmn/authmgmt/mnp.do")
	public ModelAndView mnp (
			 
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap
											) 
	{ 
		logger.info("===========================================");
		logger.debug("authmgmt.controller  : 메뉴 관리 목록 조회 화면 mnp.do");
		logger.info("===========================================");
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
	
			ModelAndView mv = new ModelAndView();
			mv.getModelMap().addAttribute("loginInfo",loginInfo);
			mv.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			mv.setViewName("/cmn/authmgmt/mnp_tmp");
			
			return mv;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}        
	}
	

	/**
	 * TREEGRID
	 */
	@RequestMapping(value = "/cmn/authmgmt/treeMenuList.json")
	public String treeMenuList(AuthMgmtVO authMgmtVO, 
						ModelMap model, 
						HttpServletRequest request, 
						HttpServletResponse response,
						@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("메뉴 트리 리스트 조회 START."+authMgmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = authMgmtService.treeMenuList(authMgmtVO);

			resultMap =  makeResultTreeRow(pRequestParamMap, resultList, authMgmtVO.getId());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			} 
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/cmn/authmgmt/treeMenuAllList.json")
	public String treeMenuAllList(AuthMgmtVO authMgmtVO, 
						ModelMap model, 
						HttpServletRequest request, 
						HttpServletResponse response,
						@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("메뉴 전체 트리 리스트 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = authMgmtService.treeMenuAllList();
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			} 
		}
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 사용자 그룹 이력 로그
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2015. 2. 09.
	 */
	@RequestMapping(value = "/cmn/authmgmt/usrGrpHst.do")
	public ModelAndView usrGrpHst (ModelMap model, 
						HttpServletRequest pRequest, 
						HttpServletResponse pResponse, 
						@RequestParam Map<String, Object> pRequestParamMap){
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			modelAndView.setViewName("/cmn/authmgmt/msp_org_bs_1030_usrGrpHst");
			model.addAttribute("startDate",orgCommonService.getWantDayTime(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
	}
	
	/**
	 * @Description : 사용자 그룹 이력 로그 리스트 조회
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2015. 2. 09.
	 */	
	@RequestMapping(value = "/cmn/authmgmt/usrGrpHstList.json")
	public String usrGrpHstList(AuthMgmtVO authMgmtVO, 
						ModelMap model, 
						HttpServletRequest request, 
						HttpServletResponse response,
						@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info("=================================================================");
		logger.info("사용자 그룹 이력 로그 리스트 조회 START.");
		logger.info("=================================================================");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try { 
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = authMgmtService.usrGrpHstList(pRequestParamMap);

			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			} 
		}
		return "jsonView"; 
	}	
}
