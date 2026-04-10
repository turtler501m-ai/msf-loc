package com.ktis.msp.rcp.rcpMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.rcp.rcpMgmt.service.FathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.ptnr.grpinsrmgmt.service.GrpInsrMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpSimpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstReqVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OsstResVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSimpVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpTlphNoVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RcpSimpMgmtController extends BaseController {
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private RcpSimpMgmtService rcpSimpMgmtService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GrpInsrMgmtService grpInsrService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
    @Autowired
    private FathService fathService;

	/**
	 * 개통간소화 신청 등록 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSimpMgmtView.do")
	public ModelAndView getRcpSimpMgmtView( @ModelAttribute("searchVO") RcpListVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");

			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 사전승낙제 대상 대리점
			String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
			if("10".equals(loginInfo.getUserOrgnTypeCd())){
				knoteYn = "Y";
			} else { // 본사 권한자가 아니면서 사전승낙제 대상 대리점이 아닌경우 접근 불가
				if (!"Y".equals(knoteYn)) throw new MvnoRunException(-1, "");
			}

			String authRealShopYn = "N";
			if (menuAuthService.chkUsrGrpAuth(loginInfo.getUserId(),"A_SHOPNM_R")) {
				authRealShopYn = "Y";
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
			
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("maskingYn", maskingYn);
			model.addAttribute("authRealShopYn", authRealShopYn);
			model.addAttribute("knoteYn", knoteYn);
			model.addAttribute("sessionUserOrgnId", searchVO.getSessionUserOrgnId());
            model.addAttribute("isEnabledFT1", fathService.isEnabledFT1());

			// 단체보험이벤트
			GrpInsrReqVO insrReqVO = new GrpInsrReqVO();
			insrReqVO.setReqInDay(searchVO.getReqInDay());
			model.addAttribute("grpInsrYn", grpInsrService.getGrpInsrYn(insrReqVO));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpSimpMgmt");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 개통간소화 부가서비스 선택 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSimpRateView.do", method={POST, GET})
	public String getRcpSimpRateView(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpSimpRate";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 개통간소화 부가서비스 목록
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSimpRateList.json")
	public String getRcpSimpRateList(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("rcpRateVO") RcpRateVO rcpRateVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = null;
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpRateVO);
			
			List<RcpRateVO> resultList = rcpSimpMgmtService.getRcpSimpRateList(rcpRateVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 부가서비스 배타관계
	 */
	@RequestMapping("/rcp/rcpMgmt/getExclusiveRateList.json")
	public String getExclusiveRateList(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("rcpRateVO") RcpRateVO rcpRateVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = null;
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpRateVO);
			
			List<RcpRateVO> resultList = rcpSimpMgmtService.getExclusiveRateList();
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 전화번호 조회 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSimpTlphNoView.do", method={POST, GET})
	public String getRcpSimpTlphNoView(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpSimpTlphNo";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 개통간소화 예약번호조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSimpRsvTlphNoByInfo.json")
	public String getRcpSimpRsvTlphNoByInfo(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("rcpTlphNoVO") RcpTlphNoVO rcpTlphNoVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpTlphNoVO);
			
			RcpTlphNoVO rsltVo = rcpSimpMgmtService.getRcpSimpRsvTlphNoByInfo(rcpTlphNoVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			resultMap.put("data", rsltVo);
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 납부주장 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSimpPayClaView.do", method={POST, GET})
	public String getRcpSimpPayClaView(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpSimpPayCla";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 개통간소화 번호이동 미납금액 조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSimpPayClaByInfo.json")
	public String getRcpSimpPayClaByInfo(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("osstReqVO") OsstReqVO osstReqVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(osstReqVO);
			
			OsstResVO rsltVo = rcpSimpMgmtService.getRcpSimpPayClaByInfo(osstReqVO);
			
			if(rsltVo != null){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
				
				resultMap.put("data", rsltVo);
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "미납금액이 없습니다.");
			}
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 번호이동 납부주장
	 */
	@RequestMapping("/rcp/rcpMgmt/updMcpMovePayClaByInfo.json")
	public String updMcpMovePayClaByInfo(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("searchVO") RcpSimpVO searchVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			rcpSimpMgmtService.updMcpMovePayClaByInfo(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 연동결과 조회화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSimpMsgView.do", method={POST, GET})
	public String getRcpSimpMsgView(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpSimpLog";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 연동메시지 조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getOsstRsltMsg.json")
	public String getOsstRsltMsg(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("osstReqVO") OsstReqVO osstReqVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(osstReqVO);
			
			OsstResVO rsltVo = rcpSimpMgmtService.getOsstRsltMsg(osstReqVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			resultMap.put("data", rsltVo);
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 SMS 발송화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSmsSendView.do", method={POST, GET})
	public String getRcpSmsSendView(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model,
			@RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpSmsSend";
		} catch (Exception e) {
			
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * SMS템플릿목록
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSmsTemplateList.json", method={POST, GET})
	public String getRcpSmsTemplateList(HttpServletRequest request, 
			HttpServletResponse response,
			@ModelAttribute("searchVO") RcpDetailVO searchVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> list = rcpSimpMgmtService.getRcpSmsTemplateList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, list, list.size());
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * SMS발송
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/setSmsSend.json", method={POST, GET})
	public String setSmsSend(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("searchVO") RcpSimpVO searchVO, 
			ModelMap model, 
			@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			rcpSimpMgmtService.setSmsSend(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg",  "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * 개통간소화 SMS발송이력
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSmsSendListView.do")
	public ModelAndView getRcpSmsSendListView( @ModelAttribute("searchVO") RcpSimpVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpSmsSendList");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 개통간소화 SMS발송이력
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpSmsSendListViewOld.do")
	public ModelAndView getRcpSmsSendListViewOld( @ModelAttribute("searchVO") RcpSimpVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpSmsSendListOld");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * SMS발송이력 조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSmsSendList.json")
	public String getRcpSmsSendList(HttpServletRequest request, 
					HttpServletResponse response, 
					@ModelAttribute("searchVO") RcpSimpVO searchVO, 
					ModelMap model, 
					@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = null;
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = rcpSimpMgmtService.getRcpSmsSendList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}
	
	/**
	 * SMS발송이력 조회 구
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpSmsSendListOld.json")
	public String getRcpSmsSendListOld(HttpServletRequest request, 
					HttpServletResponse response, 
					@ModelAttribute("searchVO") RcpSimpVO searchVO, 
					ModelMap model, 
					@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = null;
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> resultList = rcpSimpMgmtService.getRcpSmsSendListOld(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}
		
		return "jsonView";
	}

	/**
	 * 2024.02.15
	 * @Description : K-Note 팝업
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getKnotePop.do", method={POST, GET})
	public String getKnotePop(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);


			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);

			return "/rcp/rcpMgmt/rcpKnotePop";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
}
