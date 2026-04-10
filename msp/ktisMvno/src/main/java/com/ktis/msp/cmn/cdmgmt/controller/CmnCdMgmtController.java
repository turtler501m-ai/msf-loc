package com.ktis.msp.cmn.cdmgmt.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.cdmgmt.service.CmnCdMgmtService;
import com.ktis.msp.cmn.cdmgmt.vo.CmnCdMgmtVo;

import com.ktis.msp.base.exception.MvnoErrorException;

/**
 * @Class Name  : CmnCdMgmtController.java
 * @Description : CmnCdMgmtController.Class
 * @Modification Information
 * @
 * @  수정일	  수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.09.10  IB		  최초생성
 * @
 * @author IB
 * @since 2014.09.10
 * @version 1.0
 */
@Controller
public class CmnCdMgmtController extends BaseController {
	
	@Autowired
	private CmnCdMgmtService cmnCdMgmtService;
	
	
	/**
	 * 공통코드Combo
	 * 
	 * @return String
	 * @author IB
	 * @version 1.0
	 * @created 2014.09.20
	 * @updated
	 */
	@RequestMapping("/cmn/cmnCdMgmtService/getCommCombo.json")
	public String getCommCombo(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<?> resultList = cmnCdMgmtService.getCommCombo(cmnCdMgmtVo);
			
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
	 * MCP 공통코드Combo
	 * 
	 * @return String
	 * @author 
	 * @version 1.0
	 * @created 2018.03.29
	 * @updated
	 */
	@RequestMapping("/cmn/cmnCdMgmtService/getMcpCommCombo.json")
	public String getMcpCommCombo(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("cmnCdMgmtVo") CmnCdMgmtVo cmnCdMgmtVo, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<?> resultList = cmnCdMgmtService.getMcpCommCombo(cmnCdMgmtVo);
			
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
}