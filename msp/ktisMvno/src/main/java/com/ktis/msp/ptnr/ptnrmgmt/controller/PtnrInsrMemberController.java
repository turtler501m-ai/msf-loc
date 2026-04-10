package com.ktis.msp.ptnr.ptnrmgmt.controller;

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
import com.ktis.msp.ptnr.ptnrmgmt.service.PtnrInsrMemberService;
import com.ktis.msp.ptnr.ptnrmgmt.vo.PtnrInsrMemberVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;
@Controller
public class PtnrInsrMemberController extends BaseController {
	
	@Resource(name="ptnrInsrMemberService")
	private PtnrInsrMemberService ptnrInsrMemberService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	/**
	 * @param
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/ptnr/ptnrMgmt/insrMember.do")
	public ModelAndView formPtnrPoint(@ModelAttribute("searchVO") RateMgmtVO searchVO, 
					HttpServletRequest request, 
					HttpServletResponse response,
					ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		logger.info("보험가입자정보 화면호출 [prnt_insr_info]");
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
			
			modelAndView.getModelMap().addAttribute("info",searchVO);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
	        
			//////
			modelAndView.setViewName("ptnr/insrinfo/prnt_insr_info");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	
	/**
	 * @Description : 동부 요금제 조회 selectbox set
	 * @Param  :
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2018. 6. 05.
	 */
	@RequestMapping("/ptnr/ptnrMgmt/selectDongbuRate.json")
	public String selectMnfctList(HttpServletRequest request, HttpServletResponse response,PtnrInsrMemberVO vo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("동부 요금제 조회 SELECT BOX SET START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			List<?> resultList = ptnrInsrMemberService.selectDongbuRate(vo);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("동부요금제 조회 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 보험가입자정보 리스트 조회
	 * @Param  : ptnrInsrMemberVO
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 05.
	 */
	@RequestMapping("/ptnr/ptnrMgmt/getInsrMemberList.json")
	public String getInsrMemberList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("ptnrInsrMemberVO") PtnrInsrMemberVO ptnrInsrMemberVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험가입자정보 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + ptnrInsrMemberVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(ptnrInsrMemberVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(ptnrInsrMemberVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrInsrMemberService.getInsrMemberList(ptnrInsrMemberVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("보험가입자정보  리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 보험가입자 상세정보 리스트 조회
	 * @Param  : ptnrInsrMemberVO
	 * @Return : ModelAndView
	 * @Author : 권오승
	 * @Create Date : 2018. 06. 05.
	 */
	@RequestMapping("/ptnr/ptnrMgmt/getInsrHistory.json")
	public String getInsrHistory(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("ptnrInsrMemberVO") PtnrInsrMemberVO ptnrInsrMemberVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험가입자 상세정보 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + ptnrInsrMemberVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(ptnrInsrMemberVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(ptnrInsrMemberVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = ptnrInsrMemberService.getInsrHistory(ptnrInsrMemberVO, pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("보험가입자 상세내역 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
}
