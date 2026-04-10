package com.ktis.msp.bnd.bondmgmt.controller;

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

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.bnd.bondmgmt.service.BondMgmtService;
import com.ktis.msp.bnd.bondmgmt.vo.BondMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;


@Controller
public class BondMgmtController extends BaseController {
	
	@Resource(name="bondMgmtService")
	private BondMgmtService bondMgmtService;

	@Autowired
	protected EgovPropertyService propertyService;

	public BondMgmtController() {
		setLogPrefix("[BondMgmtController] ");
	}		

	
	/**
	 * 판매회차Combo
	 * 
	 * @return String
	 * @author 
	 * @version 1.0
	 * @created 2015.08.21
	 * @updated
	 */
	@RequestMapping("/bnd/bondmgmt/getSaleNumCombo.json")
	public String getSaleNumCombo(@ModelAttribute("searchVO") BondMgmtVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap) {
			
				
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
						
			List<?> resultList = bondMgmtService.selectBondSaleInfoNum(searchVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
				   throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	

	/**
	 * @Description : 수납내역서 수납월 정보 가져오기
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 김연우
	 * @Create Date : 2015. 10. 1.
	 */
	@RequestMapping("/bnd/bondsalemgmt/getBillYm.json")
	public String selectBillYm(@ModelAttribute("searchVO") BondMgmtVO searchVO, 
			HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.debug(generateLogMsg("================================================================="));
		logger.debug(generateLogMsg("수납내역서 수납월 정보 가져오기 START."));
		logger.debug(generateLogMsg("Return Vo [BondMgmtVO] = " + searchVO.toString()));
		logger.debug(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try { 
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = bondMgmtService.selectBillYm(searchVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
				   throw new MvnoErrorException(e);
		}
		model.addAttribute("result", resultMap);
		
		
		return "jsonView"; 
	}
}
