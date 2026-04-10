package com.ktis.msp.cmn.prgmmgmt.controller;

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
import com.ktis.msp.cmn.prgmmgmt.service.PrgmMgmtService;
import com.ktis.msp.cmn.prgmmgmt.vo.PrgmMgmtVO;

@Controller
public class PrgmMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private PrgmMgmtService prgmMgmtService;
	
	/**
	 * 프로그램조회
	 * @return "/cmn/prgmmgmt/prgmMgmt.jsp"
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/prgmmgmt/getPrgmMgmtList.do")
	public ModelAndView getBtchPgmList(@ModelAttribute("searchVO") PrgmMgmtVO vo, 
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
			
			modelAndView.setViewName("cmn/prgmmgmt/prgmMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 배치실행이력조회
	 * @param vo
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping(value = "/cmn/prgmmgmt/getPrgmMgmtHst.json")
	public String getPrgmMgmtHst(@ModelAttribute PrgmMgmtVO vo,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> pReqParamMap,
								ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = prgmMgmtService.getPrgmMgmtHst(vo);
			
			resultMap =  makeResultMultiRow(vo, resultList);
		} catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSPPRGM001", "프로그램오류관리")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}
