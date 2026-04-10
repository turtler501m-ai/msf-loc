package com.ktis.msp.org.grpcdmgmt.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.ktis.msp.org.grpcdmgmt.service.GrpCdMgmtService;
import com.ktis.msp.org.grpcdmgmt.vo.GrpCdMgmtVO;
import com.ktis.msp.org.grpcdmgmt.vo.GrpMgmtVO;

/**
 * @Class Name : GrpCdMgmtController
 * @Description : 공통코드관리 프로그램
 * @
 * @ 수정일       수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @2014.08.28  고은정 최초생성
 * @
 * @author : 고은정
 * @Create Date : 2014. 8. 28.
 */
@Controller
public class GrpCdMgmtController  extends BaseController {
	
	/** 공통코드관리 서비스 */
	@Autowired
	private GrpCdMgmtService grpCdMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService; 
	
	/** Constructor */
	public GrpCdMgmtController() {
		setLogPrefix("[GrpCdMgmtController] ");
	}
	
	/**
	 * @Description : 공통코드 관리 화면
	 * @Param  : GrpMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/grpMgmt.do")
	public ModelAndView grpMgmt(@ModelAttribute("searchVO") GrpMgmtVO grpMgmtVO, HttpServletRequest request, HttpServletResponse response, ModelMap model){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" grpMgmt 공통코드 관리화면"));
		logger.info(generateLogMsg("================================================================="));
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			modelAndView.setViewName("/org/grpcdmgmt/msp_org_bs_1038");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : 공통코드 리스트 조회
	 * @Param  : GrpMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/listGrpMgmt.json")
	public String listGrpMgmt(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") GrpMgmtVO grpMgmtVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" listGrpMgmt 공통코드 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = grpCdMgmtService.selectGrpMgmtList(grpMgmtVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result = " + resultMap);
		} catch (Exception e) {
			
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
					throw new MvnoErrorException(e);
			}
		}
		
		return "jsonView";
	}
	
	/**
	 * @Description : 공통코드 추가
	 * @Param  : GrpMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/insertGrpMgmt.json")
	public String insertGrpMgmt(GrpMgmtVO grpMgmtVO, HttpServletRequest request, HttpServletResponse response, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertGrpMgmt 공통코드 등록 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpMgmtVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			grpMgmtVO.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = grpCdMgmtService.insertGrpMgmt(grpMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	 * @Description : 공통코드 수정
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/updateGrpMgmt.json")
	public String updateGrpMgmt(GrpMgmtVO grpMgmtVO, HttpServletRequest request, HttpServletResponse response, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" updateGrpMgmt 공통코드 수정 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpMgmtVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			grpMgmtVO.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = grpCdMgmtService.updateGrpMgmt(grpMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	 * @Description : 공통코드 삭제
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/deleteGrpMgmt.json")
	public String deleteGrpMgmt(HttpServletRequest request, HttpServletResponse response, GrpMgmtVO grpMgmtVO, BindingResult bindingResult, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" deleteGrpMgmt 공통코드 삭제 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = grpCdMgmtService.deleteGrpMgmt(grpMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	 * @Description : 공통코드상세 리스트 조회
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/listGrpCdMgmt.json")
	public String listGrpCdMgmt(HttpServletRequest request, HttpServletResponse response, GrpCdMgmtVO grpCdMgmtVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" listGrpCdMgmt 공통코드상세 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpCdMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpCdMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = grpCdMgmtService.selectGrpCdMgmtList(grpCdMgmtVO);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			logger.debug("result:" + resultMap);
		} catch (Exception e) {
			
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
				throw new MvnoErrorException(e);
			}    
		
		}
			
		return "jsonView";
	}
	
	/**
	 * @Description : 공통코드 상세 추가
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/insertGrpCdMgmt.json")
	public String insertGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO, HttpServletRequest request, HttpServletResponse response, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertGrpCdMgmt 공통코드 상세 등록 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpCdMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpCdMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpCdMgmtVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			grpCdMgmtVO.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = grpCdMgmtService.insertGrpCdMgmt(grpCdMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	 * @Description : 공통코드 상세 수정
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/updateGrpCdMgmt.json")
	public String updateGrpCdMgmt(GrpCdMgmtVO grpCdMgmtVO, HttpServletRequest request, HttpServletResponse response, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertGrpCdMgmt 공통코드 상세 수정 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpCdMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpCdMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			grpCdMgmtVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			grpCdMgmtVO.setRvisnId(loginInfo.getUserId());
			
			int returnCnt = grpCdMgmtService.updateGrpCdMgmt(grpCdMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	 * @Description : 공통코드 상세 삭제
	 * @Param  : GrpCdMgmtVO
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 8. 28.
	 */
	@RequestMapping("/org/grpcdmgmt/deleteGrpCdMgmt.json")
	public String deleteGrpCdMgmt(HttpServletRequest request, HttpServletResponse response, GrpCdMgmtVO grpCdMgmtVO, BindingResult bindingResult, Model model){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" deleteGrpCdMgmt 공통코드 상세 삭제 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpCdMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpCdMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = grpCdMgmtService.deleteGrpCdMgmt(grpCdMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
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
	* @Description : 공통코드 LOV 파일쓰기 
	* @Param  : HttpServletRequest
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 9. 18.
	 */
	@RequestMapping("/org/grpcdmgmt/fileWriteLOV.json")
	public String fileWriteLOV(HttpServletRequest request, HttpServletResponse response, GrpCdMgmtVO grpCdMgmtVO, ModelMap model){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("공통코드 LOV 파일 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileWriter  out = null;
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpCdMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpCdMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<HashMap<String, String>> lovList = grpCdMgmtService.listGrpCdLOV();
			
			//webapp real path 얻어오기 (박상선과장님 문의)
			out = new FileWriter("C:/JIJFrameDev/workspace/ktisMvno/src/main/webapp/js/mvno_lov.js",false);
			
			out.write("var _LOV_ = { \n");
			
			for(int i = 0; i < lovList.size(); i++){
				HashMap<String, String> tempMap = lovList.get(i);
				
				for(String key : tempMap.keySet()){
				  logger.info("line : " + tempMap.get(key));
				  out.write(tempMap.get(key));
				  out.write("\n");
			  }
			}
			
			out.write("}; \n");
			out.write("if (! window.top._LOV_)  window.top._LOV_ = _LOV_;");
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
		  resultMap.clear();
		  resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
		  resultMap.put("msg", "");
		} finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
				    throw new MvnoErrorException(e);
				}	
			}
		
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	* @Description : 공통코드 그룹명 체크 
	* @Param  : GrpMgmtVO
	* @Return : String
	* @Author : 고은정
	* @Create Date : 2014. 9. 20.
	 */
	@RequestMapping("/org/grpcdmgmt/existKorNmGrpMgmt.json")
	public String existKorNmGrpMgmt(HttpServletRequest request, HttpServletResponse response, GrpMgmtVO grpMgmtVO, Model model ){
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" existKorNmGrpMgmt 공통코드 그룹명 체크 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = grpCdMgmtService.existKorNmGrpMgmt(grpMgmtVO);
			logger.debug("returnCnt" + returnCnt);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("data", returnCnt);
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
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
	 * @Description : 그룹ID 중복체크
	 * @Param  : 
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2014. 8. 29.
	 */
	@RequestMapping("/org/grpcdmgmt/isExistGrpCdMgmt.json")
	public String isExistGrpCdMgmt(HttpServletRequest request, HttpServletResponse response, GrpMgmtVO grpMgmtVO, ModelMap model) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("그룹ID 중복체크 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			//  Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(grpMgmtVO);
			
			// 본사 권한
			if(!"10".equals(grpMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = grpCdMgmtService.isExistGrpCdMgmt(grpMgmtVO);
			
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
	 * @Description : 공통 코드 리스트를 조회
	 * @Param  : 
	 * @Return : String
	 * @Author : 고은정
	 * @Create Date : 2014. 10. 7.
	 */
	@RequestMapping("/org/grpcdmgmt/listCmnCd.json")
	public String listCmnCd(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pReqParamMap) 
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("공통코드 리스트 START."));
		logger.info(generateLogMsg("================================================================="));
		Map<String, Object> resultMap = null;
		
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
			
			List<?> resultList = grpCdMgmtService.listCmnCd(request.getParameter("code"));
			
			resultMap =  makeResultMultiRow(pReqParamMap, resultList);
			
			model.addAttribute("result", resultMap);
			
			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}	
			//logger.info(generateLogMsg(String.format("공통코드 리스트 오류 CAUSE:%s MESSAGE:%s", e.getCause(), e.getMessage())));
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
}
