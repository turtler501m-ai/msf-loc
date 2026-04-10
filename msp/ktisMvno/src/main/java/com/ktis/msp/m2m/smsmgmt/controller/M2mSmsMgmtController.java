package com.ktis.msp.m2m.smsmgmt.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.fileup2.service.FileUp2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;

import com.ktis.msp.m2m.smsmgmt.service.M2mSmsMgmtService;

import com.ktis.msp.m2m.smsmgmt.vo.SmsSendReqVO;
import com.ktis.msp.m2m.smsmgmt.vo.SmsSendResVO;
import com.ktis.msp.m2m.smsmgmt.vo.SmsTemplateReqVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class M2mSmsMgmtController extends BaseController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private M2mSmsMgmtService m2mSmsMgmtService;
	
	@Autowired
	private MenuAuthService menuAuthService;
		

	/**
	 * M2M SMS 이력 조회 화면
	 * @return "/m2m/smsmgmt/mspM2mSmsMng.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/m2m/smsmgmt/mspM2mSmsMng.do")
	public ModelAndView mspM2mSmsMng(@ModelAttribute("searchVO") SmsTemplateReqVO vo, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",vo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("m2m/smsmgmt/mspM2mSmsMng");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * SMS발송조회
	 * @param vo
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m2m/smsmgmt/getSmsSendList.json")
	public String getSmsSendList(@ModelAttribute SmsSendReqVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsSendResVO> resultList = m2mSmsMgmtService.getSmsSendList(vo);
			
			int cnt = 0;
			if(resultList != null && resultList.size() > 0) {
				cnt = Integer.parseInt(resultList.get(0).getTotalCount());
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, cnt);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}

	@RequestMapping("/m2m/smsmgmt/setSmsDelete.json")
	public String setSmsDelete(@RequestBody String pJsonString,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap,
								ModelMap model) {
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.debug("pJsonString=" + pJsonString);
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", SmsSendResVO.class);
			logger.debug("size()=" + list.size());
			
			m2mSmsMgmtService.setSmsDelete(list, loginInfo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	@RequestMapping("/m2m/smsmgmt/setSmsRetry.json")
	public String setSmsRetry(@RequestBody String pJsonString,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap,
								ModelMap model) {
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		logger.debug("pJsonString=" + pJsonString);
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = getVoFromMultiJson(pJsonString, "ALL", SmsSendResVO.class);
			logger.debug("size()=" + list.size());
			
			m2mSmsMgmtService.setSmsRetry(list, loginInfo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	@RequestMapping("/m2m/smsmgmt/getTemplateCombo.json")
	public String getTemplateCombo(HttpServletRequest paramReq,
							HttpServletResponse paramRes,
							@ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo,
							ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnCdMgmtVo);
			
			List<?> resultList = m2mSmsMgmtService.getTemplateCombo(cmnCdMgmtVo);
			
			resultMap =  makeResultMultiRow(cmnCdMgmtVo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	@RequestMapping("/m2m/smsmgmt/getSearchCodeCombo.json")
	public String getSearchCodeCombo(HttpServletRequest paramReq,
							HttpServletResponse paramRes,
							@ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo,
							ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnCdMgmtVo);
			
			List<?> resultList = m2mSmsMgmtService.getSearchCodeCombo(cmnCdMgmtVo);
			
			resultMap =  makeResultMultiRow(cmnCdMgmtVo, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "", ""))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * M2M SMS 이력 조회 화면(과거)
	 * @return "/m2m/smsmgmt/mspM2mSmsMng_old.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/m2m/smsmgmt/mspM2mSmsMngOld.do")
	public ModelAndView mspM2mSmsMngOld(@ModelAttribute("searchVO") SmsTemplateReqVO vo, 
									HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("info",vo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("m2m/smsmgmt/mspM2mSmsMng_old");
			
			return modelAndView;
			
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * SMS발송조회(과거)
	 * @param vo
	 * @param request
	 * @param response
	 * @param pReqParamMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/m2m/smsmgmt/getSmsSendListOld.json")
	public String getSmsSendListOld(@ModelAttribute SmsSendReqVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<SmsSendResVO> resultList = m2mSmsMgmtService.getSmsSendListOld(vo);
			
			int cnt = 0;
			if(resultList != null && resultList.size() > 0) {
				cnt = Integer.parseInt(resultList.get(0).getTotalCount());
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, cnt);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
		
}
