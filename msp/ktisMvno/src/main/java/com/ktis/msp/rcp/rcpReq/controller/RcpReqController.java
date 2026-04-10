package com.ktis.msp.rcp.rcpReq.controller;
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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.rcpReq.service.RcpReqService;
import com.ktis.msp.rcp.rcpReq.vo.RcpReqDefaultVO;

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
//@SessionAttributes("rcpReqVO")
public class RcpReqController extends BaseController {

	/** rcpReqService */
	@Autowired
	private RcpReqService rcpReqService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@RequestMapping(value = "/rcp/rcpReq/getRcpReq.do")
	public ModelAndView getRcpReqList(@ModelAttribute("searchVO") RcpReqDefaultVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			
			// 본사 화면인 경우
//			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",
					menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.setViewName("rcp/rcpReq/rcpReq");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}	
	

    @RequestMapping("/rcp/rcpReq/rcpReqInfoAjax.json")
    public String getRcpReqInfo(HttpServletRequest request, HttpServletResponse response, 
    											@RequestParam String srlNum, 
    											@RequestParam Map<String, Object> paramMap,
    											ModelMap model) {
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
//			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
			
			List<?> resultList = rcpReqService.getRcpReqInfo(srlNum);
	        
	        resultMap =  makeResultMultiRow(paramMap, resultList);
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);

		return "jsonView";
    }
	

}
