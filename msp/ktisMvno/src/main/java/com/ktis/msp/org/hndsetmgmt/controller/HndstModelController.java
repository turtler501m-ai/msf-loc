package com.ktis.msp.org.hndsetmgmt.controller;

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
import com.ktis.msp.org.hndsetmgmt.service.HndstModelService;
import com.ktis.msp.org.hndsetmgmt.vo.HndstModelVo;



/**
 * @Class Name : HndstModelController
 * @Description : 제품 관리
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@Controller
public class HndstModelController extends BaseController {

	@Autowired
	private HndstModelService hndstModelService;
	
	@Autowired
	private MenuAuthService  menuAuthService;   
	
	public HndstModelController() {
		setLogPrefix("[HndstModelController] ");
	}	

	/**
	 * @Description : 제품 초기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
//	@RequestMapping(value = "/org/hndsetmgmt/hmdstModel.do", method={POST, GET})
	public ModelAndView hndstModelGrid(@ModelAttribute("hndstModelVo") HndstModelVo hndstModelVo, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 초기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			
			modelAndView.setViewName("org/hndsetmgmt/msp_org_bs_1020");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * @Description : 제품 찾기 화면 호출
	 * @Param  : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping(value = "/org/hndsetmgmt/searchHmdstModel.do", method={POST, GET})
	public String hndstModelGridInsert(){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 찾기 화면 START."));
		logger.info(generateLogMsg("================================================================="));
		
		return "org/hndsetmgmt/msp_org_pu_1020";
	}
	
	/**
	 * @Description : 제품 리스트 조회
	 * @Param  : 
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
	@RequestMapping("/org/hndsetmgmt/hndstModelList.json")
	public String selectHndstModelList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("hndstModelVo") HndstModelVo hndstModelVo, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 조회 START."));
		logger.info(generateLogMsg("Return Vo [hndstModelVo] = " + hndstModelVo.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try { 
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = hndstModelService.selectHndstModelList(hndstModelVo);
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				//logger.info(generateLogMsg(String.format("제품 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			    throw new MvnoErrorException(e);
			}    
		}
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 제품 등록
	 * @Param  : orgMgmtVO.class
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 13.
	 */
	@RequestMapping("/org/hndsetmgmt/insertHndstModel.json")
	public String insertHndstModel(HndstModelVo hndstModelVo, HttpServletRequest request, HttpServletResponse response ,ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			hndstModelVo.setRegId(loginInfo.getUserId());    /** 사용자ID */
			hndstModelVo.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = hndstModelService.insertHndstModel(hndstModelVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("등록 건수 = " + returnCnt));
			
		} catch (Exception e) {
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
	 * @Description : 제품 정보 수정
	 * @Param  : HndstModelVo
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/hndsetmgmt/updateHndstModel.json")
	public String updateHndstModel(HndstModelVo hndstModelVo, HttpServletRequest request, HttpServletResponse response, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			hndstModelVo.setRegId(loginInfo.getUserId());    /** 사용자ID */
			hndstModelVo.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = hndstModelService.updateHndstModel(hndstModelVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));
			
		} catch (Exception e) {
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
	 * @Description : 제품명 중복체크
	 * @Param  : HndstModelVo
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/hndsetmgmt/isExistHndstModel.json")
	public String isExistHndstModel(HndstModelVo hndstModelVo, HttpServletRequest request, HttpServletResponse response, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품명 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = hndstModelService.isExistHndstModel(hndstModelVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);
			
			logger.info(generateLogMsg("중복 건수 = " + returnCnt));
			
		} catch (Exception e) {
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
	 * @Description : KT 제품명 중복체크
	 * @Param  : HndstModelVo
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/hndsetmgmt/isExistHndstModel2.json")
	public String isExistHndstModel2(HndstModelVo hndstModelVo, HttpServletRequest request, HttpServletResponse response, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품명 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(hndstModelVo);
			
			// 본사 권한
			if(!"10".equals(hndstModelVo.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = hndstModelService.isExistHndstModel2(hndstModelVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("resultCnt", returnCnt);

			logger.info(generateLogMsg("중복 건수 = " + returnCnt));
			
		} catch (Exception e) {
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
	
	@RequestMapping("/org/hndsetmgmt/listPrdtColorCd.json")
	public String listPrdtColorCd(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("제품색상 코드 리스트 START."));
		logger.info(generateLogMsg("================================================================="));
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			// 본사 권한
			if(!"10".equals(pReqParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = hndstModelService.listPrdtColorCd(request.getParameter("mnfctCd"));
			
			Map<String, Object> resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			//logger.info(generateLogMsg(String.format("제품색상 코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
		    throw new MvnoErrorException(e);
			
		}
		
		return "jsonView";
	}
}
