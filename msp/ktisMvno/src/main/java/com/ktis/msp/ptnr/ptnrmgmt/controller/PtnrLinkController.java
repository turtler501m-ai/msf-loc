package com.ktis.msp.ptnr.ptnrmgmt.controller;

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
import com.ktis.msp.ptnr.ptnrmgmt.service.PtnrLinkService;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrLinkInfoVO;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrRetryPointVO;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class PtnrLinkController extends BaseController {
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private PtnrLinkService ptnrLinkService;
	
	/**
	 * 연동내역VIEW
	 * @param pRequest
	 * @param pResponse
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/ptnr/ptnrmgmt/ptnrLinkInit.do")
	public ModelAndView ptnrPlcyInit( HttpServletRequest pRequest
									, HttpServletResponse pResponse
									, @RequestParam Map<String, Object> commandMap
									, ModelMap model ) throws EgovBizException {
		
		logger.info("**********************************************************");
		logger.info("* 연동내역 : /ptnr/ptnrmgmt/msp_ptnr_mgmt_1005        *");
		logger.info("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
	        
			modelAndView.setViewName("/ptnr/ptnrMgmt/msp_ptnr_mgmt_1005");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 연동내역
	 * @param pRequest
	 * @param pResponse
	 * @param PtnrLinkInfoVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/ptnr/ptnrmgmt/getPtnrLinkLstInfo.json")
	public String getPtnrLinkLstInfo(HttpServletRequest pRequest
									, HttpServletResponse pResponse
									, @ModelAttribute("searchVO") PtnrLinkInfoVO ptnrLinkInfoVO
									, ModelMap modelMap
									, @RequestParam Map<String, Object> paramMap) throws EgovBizException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(ptnrLinkInfoVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(ptnrLinkInfoVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<PtnrLinkInfoVO> resultList = ptnrLinkService.getPtnrLinkLstInfo(ptnrLinkInfoVO);
			
			
			resultMap =  makeResultMultiRowNotEgovMap(paramMap, resultList, resultList.size());
			
			modelMap.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			throw new EgovBizException("조회에 실패하였습니다.");
		}
		
		return "jsonView";
	}
	
	/**
	 * 연동파일 재전송
	 * @param pRequest
	 * @param pResponse
	 * @param PtnrRetryPointVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/ptnr/ptnrmgmt/uploadPointFile.json")
	public String uploadPointFile(HttpServletRequest pRequest
								, HttpServletResponse pResponse
								, @ModelAttribute PtnrRetryPointVO ptnrRetryPointVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(ptnrRetryPointVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(ptnrRetryPointVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			// 제휴사 정산 파일 저장
			ptnrLinkService.savePointFile(ptnrRetryPointVO);
			
			// 정산파일 업로드
			ptnrLinkService.uploadPointFile(ptnrRetryPointVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "FTP서버에 파일 업로드 요청을 하였습니다.");
		
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "재전송에 실패 했습니다.");
			throw new MvnoErrorException(e);
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	 * 연동파일 재처리
	 * @param pRequest
	 * @param pResponse
	 * @param PtnrRetryPointVO
	 * @param modelMap
	 * @param paramMap
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/ptnr/ptnrmgmt/downloadPointFile.json")
	public String downloadPointFile(HttpServletRequest pRequest
								, HttpServletResponse pResponse
								, @ModelAttribute PtnrRetryPointVO ptnrRetryPointVO
								, ModelMap modelMap
								, @RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(ptnrRetryPointVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//본사화면일경우
			if(!"10".equals(ptnrRetryPointVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			// 지급결과 파일 다운로드
			ptnrLinkService.downloadPointFile(ptnrRetryPointVO);
			
			// 지급결과 디비 업데이트
			ptnrLinkService.readFile(ptnrRetryPointVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "FTP서버에 파일 다운로드 요청을 하였습니다.");
		
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "재처리에 실패 했습니다.");
			throw new MvnoErrorException(e);
		}
		
		modelMap.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
