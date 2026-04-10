/**
 * 
 */
package com.ktis.msp.cmn.mdlamtmgmt.controller;

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

import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.module.MvnoUtil;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.cmn.mdlamtmgmt.service.CmnMdlAmtMgmtService;
import com.ktis.msp.cmn.mdlamtmgmt.vo.CmnMdlAmtVO;

/**
 * @Class Name : CmnMdlAmtMgmtController
 * @Description : 모델 단가 관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.08.05 심정보 최초생성
 * @
 * @author : 심정보
 * @Create Date : 2015. 8. 4.
 */
@Controller
public class CmnMdlAmtMgmtController extends BaseController {

	@Autowired
	private CmnMdlAmtMgmtService cmnMdlAmtMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	public CmnMdlAmtMgmtController() {
		setLogPrefix("[CmnMdlAmtMgmtController] ");
	}
	
	/**
	 * @Description : 모델 단가 관리 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping(value = "/org/hndsetamtmgmt/hmdstAmtMgmt.do")
	public ModelAndView hndstAmtModelGrid(@ModelAttribute("cmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO,
									HttpServletRequest request, 
									HttpServletResponse response, 
									ModelMap model)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델 단가 관리 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("gSesSysDt", MvnoUtil.getDateStr("yyyyMMdd"));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			modelAndView.setViewName("cmn/mdlamtmgmt/msp_org_bs_1021_1");
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 모델단가 대표모델 리스트 조회
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectIntmMdlAmtList.json")
	public String selectIntmMdlAmtList(HttpServletRequest request, 
									HttpServletResponse response, 
									@ModelAttribute("cmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
									ModelMap model, 
									@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델단가 대표모델 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = cmnMdlAmtMgmtService.selectIntmMdlAmtList(cmnMdlAmtVO);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP2000031", "모델단가관리"))
			{
				throw new MvnoErrorException(e);
			} 
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("모델단가 대표모델 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	
	/**
	 * @Description : 입고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectMnfctAmtHisList.json")
	public String selectMnfctAmtHisList(HttpServletRequest request, 
									HttpServletResponse response, 
									CmnMdlAmtVO cmnMdlAmtVO, 
									ModelMap model, 
									@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 이력 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = cmnMdlAmtMgmtService.selectMnfctAmtHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 이력 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 입고 단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/insertMnfctAmtHisList.json")
	public String insertMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO, 
										ModelMap model,
										HttpServletRequest paramReq, 
										HttpServletResponse paramRes) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 등록 START."));
		logger.info(generateLogMsg("Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnMdlAmtVO.setSessionUserId(loginInfo.getUserId());
			cmnMdlAmtVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnMdlAmtVO.setRvisnId(loginInfo.getUserId());
			logger.info(generateLogMsg("CHECK Vo : [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
			
			int returnCnt = cmnMdlAmtMgmtService.insertMnfctAmtHisList(cmnMdlAmtVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", "");
			resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}   
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 입고 단가 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/updateMnfctAmtHisList.json")
	public String updateMnfctAmtHisList(CmnMdlAmtVO cmnMdlAmtVO, 
									ModelMap model, 
									HttpServletRequest paramReq, 
									HttpServletResponse paramRes) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("입고 단가 수정 START."));
		logger.info(generateLogMsg("Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnMdlAmtVO.setSessionUserId(loginInfo.getUserId());
			cmnMdlAmtVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnMdlAmtVO.setRvisnId(loginInfo.getUserId());
			
			logger.info(generateLogMsg("CHECK Vo : [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
			int returnCnt = cmnMdlAmtMgmtService.updateMnfctAmtHisList(cmnMdlAmtVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}   
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 출고 단가 이력 리스트 조회
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectHndstAmtHisList.json")
	public String selectHndstAmtHisList(HttpServletRequest request, 
										HttpServletResponse response, 
										CmnMdlAmtVO cmnMdlAmtVO, 
										ModelMap model, 
										@RequestParam Map<String, Object> pReqParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 이력 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = cmnMdlAmtMgmtService.selectHndstAmtHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 이력 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 출고 단가 등록
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/insertHndstAmtHisList.json")
	public String insertHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO, 
										ModelMap model, 
										HttpServletRequest paramReq, 
										HttpServletResponse paramRes) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 등록 START."));
		logger.info(generateLogMsg("Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnMdlAmtVO.setSessionUserId(loginInfo.getUserId());
			cmnMdlAmtVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnMdlAmtVO.setRvisnId(loginInfo.getUserId());
			
			logger.info(generateLogMsg("CHECK Vo : [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
			int returnCnt = cmnMdlAmtMgmtService.insertHndstAmtHisList(cmnMdlAmtVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}   
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 출고 단가 수정
	 * @Param  : CmnMdlAmtVO
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 10.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/updateHndstAmtHisList.json")
	public String updateHndstAmtHisList(CmnMdlAmtVO cmnMdlAmtVO, 
										ModelMap model, 
										HttpServletRequest paramReq, 
										HttpServletResponse paramRes) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("출고 단가 수정 START."));
		logger.info(generateLogMsg("Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			/* -- 로그인 조직정보 및 권한 체크 -- */
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			cmnMdlAmtVO.setSessionUserId(loginInfo.getUserId());
			cmnMdlAmtVO.setRegId(loginInfo.getUserId());	/** 사용자ID */
			cmnMdlAmtVO.setRvisnId(loginInfo.getUserId());
			
			logger.info(generateLogMsg("CHECK Vo : [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
			int returnCnt = cmnMdlAmtMgmtService.updateHndstAmtHisList(cmnMdlAmtVO);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
			
			if ( ! getErrReturn(e, resultMap))
			{
				throw new MvnoErrorException(e);
			}   
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	// 대표모델 출고단가 찾기 팝업 -- 단종모델 제외
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 호출 -- 단종모델 제외 공통호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmMdlAmtForm.do", method={POST, GET})
	public String intmMdlAmtForm(CmnMdlAmtVO cmnMdlAmtVO, 
								ModelMap model, 
								HttpServletRequest request, 
								HttpServletResponse response) 
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 대표모델단가 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		return "/cmn/mdlamtmgmt/msp_org_pu_1021_1";

	}
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 호출 -- 단종모델 제외 Ajax 
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmMdlAmtMbForm.do")
	public ModelAndView intmMdlAmtMbForm( @ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
										ModelMap model, 
										HttpServletRequest request, 
										HttpServletResponse response, 
										@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		mv.setViewName("cmn/mdlamtmgmt/msp_org_pu_1021_1");
		
		return mv; 
	}
	
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 -- 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectRprsHndstAmtHisList.json")
	public String selectRprsHndstAmtHisList(HttpServletRequest request, 
											HttpServletResponse response, 
											CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			List<?> resultList = cmnMdlAmtMgmtService.selectRprsHndstAmtHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	// 대표모델 출고단가 찾기 팝업 -- 단종모델 포함
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 호출 -- 단종모델 포함 공통
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmMdlAmtAllForm.do", method={POST, GET})
	public String intmMdlAmtAllForm(HttpServletRequest request, 
									HttpServletResponse response, 
									CmnMdlAmtVO cmnMdlAmtVO, 
									ModelMap model, 
									@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 대표모델단가 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		return "/cmn/mdlamtmgmt/msp_org_pu_1020_2";

	}
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 호출 -- 단종모델 포함 Ajax
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmMdlAmtAllMbForm.do")
	public ModelAndView intmMdlAmtAllMbForm( @ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											HttpServletRequest request, 
											HttpServletResponse response, 
											@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		mv.setViewName("cmn/mdlamtmgmt/msp_org_pu_1020_2");
		return mv; 
	}
	 
	/**
	 * @Description : 대표모델 출고단가 찾기 팝업 리스트 조회 -- 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectRprsHndstAmtAllHisList.json")
	public String selectRprsHndstAmtAllHisList(HttpServletRequest request, 
											HttpServletResponse response, 
											CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = cmnMdlAmtMgmtService.selectRprsHndstAmtAllHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	// 대표모델의 중고여부 단말 찾기 팝업 -- 단종모델 제외
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 제외 공통
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmMdlOldYnForm.do", method={POST, GET})
	public String intmMdlOldYnForm(HttpServletRequest request, 
								HttpServletResponse response, 
								CmnMdlAmtVO cmnMdlAmtVO, 
								ModelMap model, 
								@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 대표모델단가 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		return "/cmn/mdlamtmgmt/msp_org_pu_1020_3";

	}
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 제외 Ajax
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmMdlOldYnMbForm.do")
	public ModelAndView intmMdlOldYnMBForm( @ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											HttpServletRequest request, 
											HttpServletResponse response, 
											@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		mv.setViewName("cmn/mdlamtmgmt/msp_org_pu_1020_3");
		return mv; 
	}
	 
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 제외
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectRprsHndstOldYnHisList.json")
	public String selectRprsHndstOldYnHisList(@ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											HttpServletRequest request, 
											HttpServletResponse response, 
											@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			// 본사 화면인 경우
			if(!"10".equals(cmnMdlAmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = cmnMdlAmtMgmtService.selectRprsHndstOldYnHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
	// 대표모델의 중고여부 단말 찾기 팝업 -- 단종모델 포함
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 포함 공통
	 * @Param  : void
	 * @Return : String
	 * @Author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
	@RequestMapping(value = "/cmn/intmmdl/intmMdlOldYnAllForm.do", method={POST, GET})
	public String intmMdlOldYnAllForm(@ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
									ModelMap model, 
									HttpServletRequest request, 
									HttpServletResponse response, 
									@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 대표모델단가 찾기 화면 호출 START."));
		logger.info(generateLogMsg("================================================================="));
		
		LoginInfo loginInfo = new LoginInfo(request, response);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		return "/cmn/mdlamtmgmt/msp_org_pu_1020_4";

	}
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 포함 Ajax
	 * @Param  : CmnIntmMdlVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 21.
	 */
	 @RequestMapping("/cmn/intmmdl/intmMdlOldYnMbAllForm.do")
	public ModelAndView intmMdlOldYnAllMBForm( @ModelAttribute("CmnMdlAmtVO") CmnMdlAmtVO cmnMdlAmtVO, 
											ModelMap model, 
											HttpServletRequest pRequest, 
											HttpServletResponse pResponse, 
											@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView mv = new ModelAndView();
		
		LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
		loginInfo.putSessionToVo(cmnMdlAmtVO);
		
		mv.setViewName("cmn/mdlamtmgmt/msp_org_pu_1020_4");
		return mv; 
	}
	 
	/**
	 * @Description : 대표모델의 중고여부 단말 찾기 팝업 리스트 조회 -- 단종모델 포함
	 * @Param  : CmnMdlAmtVO
	 * @Return : ModelAndView
	 * @Author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
	@RequestMapping("/cmn/hndsetamtmgmt/selectRprsHndstOldYnAllHisList.json")
	public String selectRprsHndstOldYnAllHisList(HttpServletRequest request, 
												HttpServletResponse response, 
												CmnMdlAmtVO cmnMdlAmtVO, 
												ModelMap model, 
												@RequestParam Map<String, Object> pReqParamMap)
	{
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 START."));
		logger.info(generateLogMsg("Return Vo [cmnMdlAmtVO] = " + cmnMdlAmtVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		try { 
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(cmnMdlAmtVO);
			
			List<?> resultList = cmnMdlAmtMgmtService.selectRprsHndstOldYnAllHisList(cmnMdlAmtVO);
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("대표모델 출고단가 찾기 팝업 리스트 조회 END."));
		logger.info(generateLogMsg("================================================================="));
		
		return "jsonView"; 
	}
	
}
