package com.ktis.msp.rwd.rwdMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rwd.rwdMgmt.service.RwdMgmtService;
import com.ktis.msp.rwd.rwdMgmt.vo.RwdMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : RwdMgmtController
 * @Description : 보상서비스 기준정보 관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.02.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2023.02.22
 */
@Controller
public class RwdMgmtController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private RwdMgmtService rwdMgmtService;
	
	public RwdMgmtController() {
		setLogPrefix("[RwdMgmtController] ");
	}
	
	/**
	 * @Description : 보상서비스 관리화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/rwd/rwdMgmt/rwdMgmtView.do", method={POST, GET})
	public ModelAndView rwdMgmtView(HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap){
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보상서비스 상품 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getRwdProdList.json")
	public String getRwdProdList(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말보험 상품 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMgmtVO> resultList = rwdMgmtService.getRwdProdList(rwdMgmtVO);
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 보상서비스 상품 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rwd/rwdMgmt/regRwdMst.json")
	public String regIntmRwdMst(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			
			rwdMgmtService.regRwdMst(rwdMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 단말보험 상품 수정
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rwd/rwdMgmt/updRwdMst.json")
	public String updIntmRwdMst(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			
			rwdMgmtService.updRwdMst(rwdMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 보상서비스 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getRwdMstList.json")
	public String getIntmRwdMstList(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말보험 상품 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMgmtVO> resultList = rwdMgmtService.getRwdMstList(rwdMgmtVO);
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 단말검색
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getPrdtListByPrdtNm.json")
	public String getRwdPrdtListByPrdtNm(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말검색 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMgmtVO> resultList = rwdMgmtService.getPrdtListByPrdtNm(rwdMgmtVO);
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, resultList.size());
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 단말등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rwd/rwdMgmt/regRwdRel.json")
	public String regIntmRwdRel(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, @RequestBody String data,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			@SuppressWarnings("unchecked")
			List<RwdMgmtVO> aryList = getVoFromMultiJson(data, "ALL", RwdMgmtVO.class);
			
			rwdMgmtService.regRwdRel(aryList, loginInfo.getUserId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	* @Description : 단말목록조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getRwdRelList.json")
	public String getIntmRwdRelList(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말목록조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMgmtVO> resultList = rwdMgmtService.getRwdRelList(rwdMgmtVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0 && "Y".equals(rwdMgmtVO.getPagingYn())){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}else {
				totalCount = resultList.size();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 단말수정
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rwd/rwdMgmt/updRwdRel.json")
	public String updIntmRwdRel(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, @RequestBody String data,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			@SuppressWarnings("unchecked")
			List<RwdMgmtVO> aryList = getVoFromMultiJson(data, "ALL", RwdMgmtVO.class);
			
			rwdMgmtService.updRwdRel(aryList, loginInfo.getUserId());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001001", "기준정보")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
	
	/**
	* @Description : 보상서비스 목록조회(combobox용)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getRwdCombo.json")
	public String getRwdCombo(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험목록조회(combobox용) START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<EgovMap> resultList = rwdMgmtService.getRwdCombo(rwdMgmtVO);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	* @Description : 변경신청관리 보상서비스 조회(combobox용)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rwd/rwdMgmt/getChgRwdCombo.json")
	public String getChgRwdCombo(@ModelAttribute("rwdMgmtVO") RwdMgmtVO rwdMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험목록조회(combobox용) START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<EgovMap> resultList = rwdMgmtService.getChgRwdCombo(rwdMgmtVO);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}
