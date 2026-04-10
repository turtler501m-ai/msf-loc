package com.ktis.msp.sale.ktPlcyMgmt.controller;

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

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.sale.ktPlcyMgmt.service.KtPlcyMgmtService;
import com.ktis.msp.sale.ktPlcyMgmt.vo.KtPlcyMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class KtPlcyMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private KtPlcyMgmtService ktPlcyMgmtService;
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/ktPlcyMgmtView.do")
	public ModelAndView ktPlcyMgmtView(ModelMap model,
									HttpServletRequest pRequest,
									HttpServletResponse pResponse,
									@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
									@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
						
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("sale/ktPlcyMgmt/msp_sale_mgmt_4001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getKtPlcyList.json")
	public String getKtPlcyList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getKtPlcyList(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getKtPlcyDtlList.json")
	public String getKtPlcyDtlList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getKtPlcyDtlList(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getMspKtPlcyDiscountByGrid.json")
	public String getMspKtPlcyDiscountByGrid(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getMspKtPlcyDiscountByGrid(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getMspKtPlcyOperByGrid.json")
	public String getMspKtPlcyOperByGrid(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getMspKtPlcyOperByGrid(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getMspKtPlcyInstGrid.json")
	public String getMspKtPlcyInstGrid(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getMspKtPlcyInstGrid(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getMspKtPlcyEnggByGrid.json")
	public String getMspKtPlcyEnggByGrid(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<KtPlcyMgmtVO> resultList = ktPlcyMgmtService.getMspKtPlcyEnggByGrid(searchVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping("/sale/ktPlcyMgmt/getMspKtPlcyMstBySlsNo.json")
	public String getMspKtPlcyMstBySlsNo(@ModelAttribute("searchVO") KtPlcyMgmtVO searchVO, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int rntlCnt = ktPlcyMgmtService.getMspKtPlcyMstBySlsNo(searchVO);
			
			if ( rntlCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "" );
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "기존에 등록된 판매번호가 존재합니다.");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	
	@RequestMapping("/sale/ktPlcyMgmt/savMspKtPlcyMst.json")
	public String savMspKtPlcyMst(@ModelAttribute("searchVO") KtPlcyMgmtVO searchVO, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			searchVO.setSessionUserId(loginInfo.getUserId());
			
			int rntlCnt = ktPlcyMgmtService.savMspKtPlcyMst(searchVO);
			
			if ( rntlCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
			resultMap.put("msg", "");
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/sale/ktPlcyMgmt/getKtPlcyListCombo.json")
	public String getKtPlcyListCombo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("KtPlcyMgmtVO") KtPlcyMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			List<EgovMap> resultList = ktPlcyMgmtService.getKtPlcyListCombo(searchVO);
			
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
