package com.ktis.msp.insr.insrMgmt.controller;

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
import com.ktis.msp.insr.insrMgmt.service.InsrMgmtService;
import com.ktis.msp.insr.insrMgmt.vo.InsrMgmtVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class InsrMgmtController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private InsrMgmtService insrMgmtService;
	
	public InsrMgmtController() {
		setLogPrefix("[InsrMgmtController] ");
	}
	
	/**
	 * @Description : 단말안심보험관리 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrMgmtView.do", method={POST, GET})
	public ModelAndView insrMgmtView(HttpServletRequest pRequest,
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

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 단말보험 상품 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getIntmInsrProdList.json")
	public String getIntmInsrProdList(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말보험 상품 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrMgmtVO> resultList = insrMgmtService.getIntmInsrProdList(insrMgmtVO);
			
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
	* @Description : 단말보험 상품 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/regIntmInsrMst.json")
	public String regIntmInsrMst(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			
			insrMgmtService.regIntmInsrMst(insrMgmtVO);
			
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
	@RequestMapping("/insr/insrMgmt/updIntmInsrMst.json")
	public String updIntmInsrMst(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			
			insrMgmtService.updIntmInsrMst(insrMgmtVO);
			
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
	* @Description : 단말보험 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getIntmInsrMstList.json")
	public String getIntmInsrMstList(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말보험 상품 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrMgmtVO> resultList = insrMgmtService.getIntmInsrMstList(insrMgmtVO);
			
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
	@RequestMapping(value="/insr/insrMgmt/getPrdtListByPrdtNm.json")
	public String getPrdtListByPrdtNm(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말검색 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrMgmtVO> resultList = insrMgmtService.getPrdtListByPrdtNm(insrMgmtVO);
			
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
	@RequestMapping("/insr/insrMgmt/regIntmInsrRel.json")
	public String regIntmInsrRel(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, @RequestBody String data,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			@SuppressWarnings("unchecked")
			List<InsrMgmtVO> aryList = getVoFromMultiJson(data, "ALL", InsrMgmtVO.class);
			
			insrMgmtService.regIntmInsrRel(aryList, loginInfo.getUserId());
			
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
	@RequestMapping(value="/insr/insrMgmt/getIntmInsrRelList.json")
	public String getIntmInsrRelList(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("단말목록조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrMgmtVO> resultList = insrMgmtService.getIntmInsrRelList(insrMgmtVO);
			
			int totalCount = 0;
			
			if(resultList.size() > 0 && "Y".equals(insrMgmtVO.getPagingYn())){
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
	@RequestMapping("/insr/insrMgmt/updIntmInsrRel.json")
	public String updIntmInsrRel(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, @RequestBody String data,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			@SuppressWarnings("unchecked")
			List<InsrMgmtVO> aryList = getVoFromMultiJson(data, "ALL", InsrMgmtVO.class);
			
			insrMgmtService.updIntmInsrRel(aryList, loginInfo.getUserId());
			
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
	* @Description : 보험목록조회(combobox용)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCombo.json")
	public String getInsrCombo(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험목록조회(combobox용) START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<EgovMap> resultList = insrMgmtService.getInsrCombo(insrMgmtVO);
			
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
	* @Description : 변경신청관리 보험목록 조회(combobox용)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getChgInsrCombo.json")
	public String getChgInsrCombo(@ModelAttribute("insrMgmtVO") InsrMgmtVO insrMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험목록조회(combobox용) START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrMgmtVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<EgovMap> resultList = insrMgmtService.getChgInsrCombo(insrMgmtVO);
			
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
