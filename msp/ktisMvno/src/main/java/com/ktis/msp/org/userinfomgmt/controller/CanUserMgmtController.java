package com.ktis.msp.org.userinfomgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import com.ktis.msp.org.userinfomgmt.service.CanUserMgmtService;
import com.ktis.msp.org.userinfomgmt.vo.CanUserReqVO;
import com.ktis.msp.org.userinfomgmt.vo.CanUserResVO;
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;



/**
 * @Class Name : CanUserMgmtController
 * @Description : CAN 사용자 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.06.08 TREXSHIN 최초생성
 * @
 * @author : TREXSHIN
 * @Create Date : 2017.06.08.
 */

@Controller
public class CanUserMgmtController extends BaseController {
	
	@Autowired
	private CanUserMgmtService canUserMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	public CanUserMgmtController() {
		setLogPrefix("[CanUserMgmtController] ");
	}
	
	/**
	 * @Description : CAN사용자관리 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : TREXSHIN
	 */
	@RequestMapping(value = "/org/userinfomgmt/canUserMgmt.do", method={POST, GET})
	public ModelAndView canUserMgmt(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CAN 사용자관리 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);
			
			// 본사 권한
			if(!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("org/userinfomgmt/msp_org_bs_1030");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * CAN 사용자관리 조회
	 * @param request
	 * @param response
	 * @param userInfoMgmtVo
	 * @param model
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping("/org/userinfomgmt/canUserMgmtList.json")
	public String canUserMgmtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("canUserReqVO") CanUserReqVO canUserReqVO, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("CAN 사용자 조회 START."));
		logger.info(generateLogMsg("Return Vo [userInfoMgmtVo] = " + canUserReqVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(canUserReqVO);
			
			// 본사 권한
			if(!"10".equals(canUserReqVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<CanUserResVO> resultList = canUserMgmtService.selectCanUserList(canUserReqVO);
			
			int cnt = 0;
			if(resultList != null && resultList.size() > 0) {
				cnt = Integer.parseInt(resultList.get(0).getTotalCount());
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, cnt);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("CAN 사용자 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}
			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/org/userinfomgmt/saveCanUser.json")
	public String saveCanUser(@ModelAttribute CanUserResVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			canUserMgmtService.saveCanUser(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
	
	@RequestMapping("/org/userinfomgmt/checkCanUser.json")
	public String checkCanUser(@ModelAttribute CanUserResVO vo,
								ModelMap model,
								HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			
			/* 로그인정보체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			// 본사 화면인 경우
			if(!"10".equals(vo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int checkCnt = canUserMgmtService.checkCanUser(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("checkCnt", checkCnt);
		}
		catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
		
	}
}
