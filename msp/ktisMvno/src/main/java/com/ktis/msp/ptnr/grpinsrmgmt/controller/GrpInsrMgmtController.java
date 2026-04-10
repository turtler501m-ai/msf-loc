package com.ktis.msp.ptnr.grpinsrmgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
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
import com.ktis.msp.ptnr.grpinsrmgmt.service.GrpInsrMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrResVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class GrpInsrMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private GrpInsrMgmtService  grpInsrService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@RequestMapping(value = "/ptnr/grpinsrmgmt/GrpInsrMgmtView.do")
	public ModelAndView GrpInsrMgmtView(HttpServletRequest pRequest, 
										HttpServletResponse pResponse,
										@ModelAttribute("searchVO") GrpInsrReqVO searchVO,
										@RequestParam Map<String, Object> pRequestParamMap,
										ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 단체보험관리 Start *");
		logger.debug("*********************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
						
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("ptnr/grpinsrmgmt/msp_ptnr_mgmt_2001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 단체보험목록조회
	 */
	@RequestMapping(value="/ptnr/grpinsrmgmt/getGrpInsrMgmtList.json")
	public String getGrpInsrMgmtList(HttpServletRequest pRequest, 
									HttpServletResponse pResponse,
									@ModelAttribute("searchVO") GrpInsrReqVO searchVO,
									@RequestParam Map<String, Object> pRequestParamMap,
									ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 단체보험관리 목록조회 *");
		logger.debug("*********************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = grpInsrService.getGrpInsrMgmtList(searchVO);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "단체보험관리"))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 보험목록조회
	 */
	@RequestMapping(value="/ptnr/grpinsrmgmt/getGrpInsrCdList.json")
	public String getGrpInsrCdList(HttpServletRequest pRequest, 
									HttpServletResponse pResponse,
									@ModelAttribute("searchVO") GrpInsrReqVO searchVO,
									@RequestParam Map<String, Object> pRequestParamMap,
									ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 보험목록조회 *");
		logger.debug("*********************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = grpInsrService.getGrpInsrCdList(searchVO);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "단체보험관리"))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 단체보험등록
	 */
	@RequestMapping(value="/ptnr/grpinsrmgmt/insertGrpInsrMst.json")
	public String insertGrpInsrMst(HttpServletRequest pRequest, 
									HttpServletResponse pResponse,
									@ModelAttribute("searchVO") GrpInsrResVO searchVO,
									ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 단체보험 등록 *");
		logger.debug("* searchVO = " + searchVO.toString());
		logger.debug("*********************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpInsrService.insertGrpInsrMst(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", e.getMessage());
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 단체보험수정
	 */
	@RequestMapping(value="/ptnr/grpinsrmgmt/updateGrpInsrMst.json")
	public String updateGrpInsrMst(HttpServletRequest pRequest, 
									HttpServletResponse pResponse,
									@ModelAttribute("searchVO") GrpInsrResVO searchVO,
									ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 단체보험 등록 *");
		logger.debug("* searchVO = " + searchVO.toString());
		logger.debug("*********************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpInsrService.updateGrpInsrMst(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", e.getMessage());
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 신청등록 단체보험 콤보조회
	 */
	@RequestMapping(value="/ptnr/grpinsrmgmt/getGrpInsrCombo.json")
	public String getGrpInsrCombo(HttpServletRequest pRequest, 
									HttpServletResponse pResponse,
									@ModelAttribute("searchVO") GrpInsrReqVO searchVO,
									@RequestParam Map<String, Object> pRequestParamMap,
									ModelMap model)
	{
		logger.debug("*********************************");
		logger.debug("* 보험목록조회 *");
		logger.debug("*********************************");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = grpInsrService.getGrpInsrCombo(searchVO);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "단체보험관리"))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
}
