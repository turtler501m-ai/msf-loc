/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ktis.msp.rcp.chgRcptMgmt.controller;

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
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.ptnr.grpinsrmgmt.service.GrpInsrMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.rcp.chgRcptMgmt.service.ChgRcptMgmtService;
import com.ktis.msp.rcp.chgRcptMgmt.vo.ChgRcptMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class ChgRcptMgmtController extends BaseController {
	
	/** rcpReqService */
	@Autowired
	private ChgRcptMgmtService chgRcptService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GrpInsrMgmtService grpInsrService;
	
	@RequestMapping(value = "/rcp/rcpMgmt/getChgRcpt.do")
	public ModelAndView getChgRcpt(@ModelAttribute("searchVO") ChgRcptMgmtVO vo, 
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			
			modelAndView.getModelMap().addAttribute("info", vo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);

			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("maskingYn", maskingYn);
			
			GrpInsrReqVO insrReqVO = new GrpInsrReqVO();
			model.addAttribute("grpInsrYn", grpInsrService.getGrpInsrYn(insrReqVO));
			
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			modelAndView.setViewName("/rcp/rcpMgmt/chgRcptMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}	
	
	
	@RequestMapping("/rcp/rcpMgmt/getChgRcptList.json")
	public String getChgRcptList(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("searchVO") ChgRcptMgmtVO vo, 
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 대리점인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				vo.setProcAgntCd(loginInfo.getUserOrgnId());
			}
			
			List<?> list = chgRcptService.getChgRcptList(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", e.getMessage());
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/rcp/rcpMgmt/getContractInfo.json")
	public String getContractInfo(HttpServletRequest request
								, HttpServletResponse response
								, ModelMap model
								, @ModelAttribute("searchVO") ChgRcptMgmtVO vo
								, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			List<?> list = chgRcptService.getContractInfo(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/rcp/rcpMgmt/insertChgRcptMst.json")
	public String insertChgRcptMst(HttpServletRequest request
									, HttpServletResponse response
									, ModelMap model
									, @ModelAttribute("searchVO") ChgRcptMgmtVO vo
									, @RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			chgRcptService.insertChgRcptMst(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 단말보험 가입가능 기간
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/rcpMgmt/getInsrPsblYn.json")
	public String getInsrPsblYn(HttpServletRequest request
								, HttpServletResponse response
								, ModelMap model
								, @ModelAttribute("searchVO") ChgRcptMgmtVO vo
								, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			List<?> list = chgRcptService.getInsrPsblYn(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
}
