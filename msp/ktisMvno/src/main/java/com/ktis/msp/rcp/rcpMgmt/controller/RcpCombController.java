package com.ktis.msp.rcp.rcpMgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpCombMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCombVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.sale.rateMgmt.vo.RateMgmtVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RcpCombController extends BaseController  {
	
	@Autowired
	private RcpCombMgmtService rcpCombMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	/** Constructor */
	public RcpCombController() {
		setLogPrefix("[RcpCombController] ");
	}

	
	/**
	 * 신청관리(결합서비스)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpComb.do")
	public ModelAndView getRcpComb( @ModelAttribute("searchVO") RcpCombVO searchVO,
			ModelMap model,	
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
			)
	{
		ModelAndView modelAndView = new ModelAndView();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			if("0".equals(maskingYn)) {
				maskingYn = "N";
			}else if ("1".equals(maskingYn)){
				maskingYn = "Y";
			}
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");

			String agentVersion = (String) resultMap.get("AGENT_VERSION");
			String serverUrl = (String) resultMap.get("SERVER_URL");
			
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("agentVersion", agentVersion);
			modelAndView.getModelMap().addAttribute("serverUrl", serverUrl);
			modelAndView.getModelMap().addAttribute("scanDownloadUrl", scanDownloadUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			
			model.addAttribute("maskingYn", maskingYn);

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpCombMgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}	
	/**
	 * 신청정보(결합서비스) 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpCombMgmtList.json")
	public String getRcpCombMgmtList(@ModelAttribute("searchVO") RcpCombVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			//2022-11-22 필수값 체크
			if(paramMap.isEmpty()){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			List<?> rcpList = rcpCombMgmtService.getRcpCombMgmtList(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSP1010021", "신청관리(결합서비스)"))
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
	 * 신청정보(결합서비스) - 상세(팝업조회)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpCombMgmtInfo.json")
	public String getRcpCombMgmtInfo(@ModelAttribute("searchVO") RcpCombVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> rcpList = rcpCombMgmtService.getRcpCombMgmtInfo(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * 신청정보(결합서비스) - 계약번호별 계약 상세(팝업조회)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpCombCntrInfo.json")
	public String getRcpCombCntrInfo(@ModelAttribute("searchVO") RateMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> rcpList = rcpCombMgmtService.getRcpCombCntrInfo(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	

	/**
	 * 신청관리(결합서비스) - 결합상품 조회(팝업조회)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpCombParentRateCdInfo.json")
	public String getRcpCombParentRateCdInfo(@ModelAttribute("searchVO") RcpDetailVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> rcpList = rcpCombMgmtService.getRcpCombParentRateCdInfo(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 신청관리(결합서비스) - 부가서비스명 조회(팝업조회)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpCombRelationRateCdInfo.json")
	public String getRcpCombRelationRateCdInfo(@ModelAttribute("searchVO") RcpDetailVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> rcpList = rcpCombMgmtService.getRcpCombRelationRateCdInfo(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	

	/**
	 * 신청정보(결합서비스) - 팝업 저장
	 */
	@RequestMapping("/rcp/rcpMgmt/insertRcpCombMgmtInfo.json")
	public String insertRcpCombMgmtInfo(@ModelAttribute("searchVO") RcpCombVO searchVO,
				   ModelMap model, 
				   HttpServletRequest request, 
				   HttpServletResponse response, 
				   @RequestParam Map<String, Object> paramMap)
	{
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			searchVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
			
			rcpCombMgmtService.insertRcpCombMgmtInfo(searchVO);
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	

	/**
	 * 신청정보(결합서비스) - 팝업 수정
	 */
	@RequestMapping("/rcp/rcpMgmt/updateRcpCombMgmtInfo.json")
	public String updateRcpCombMgmtInfo(@ModelAttribute("searchVO") RcpCombVO searchVO,
				   ModelMap model, 
				   HttpServletRequest request, 
				   HttpServletResponse response, 
				   @RequestParam Map<String, Object> paramMap)
	{
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			searchVO.setRegstId(loginInfo.getUserId());
			
			//마스킹권한이 없는경우
			//String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			// 마스킹 0 : Y, 1: N
			//if("0".equals(maskingYn)){
				//throw new MvnoServiceException("마스킹 해제 권한이 없습니다.");
			//}
			
			rcpCombMgmtService.updateRcpCombMgmtInfo(searchVO);
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 결합서비스 승인여부 등록
	 * @Param  :
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2022. 11. 22.
	 */
	@RequestMapping("/rcp/rcpMgmt/updateRcpCombRslt.json")
	public String updateRcpCombRslt(@ModelAttribute("rcpCombVO") RcpCombVO rcpCombVO,
				   ModelMap model, 
				   HttpServletRequest request, 
				   HttpServletResponse response, 
				   @RequestParam Map<String, Object> paramMap)
	{
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpCombVO);
			
			// 본사 화면인 경우
			if(!"10".equals(rcpCombVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rcpCombVO.setRvisnId(loginInfo.getUserId());

			rcpCombMgmtService.updateRcpCombRslt(rcpCombVO);
			
	 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		}catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	/**
	 * @Description : 신청관리(결합서비스) 자료생성
	 * @Param  : RcpCombVO
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2022. 11. 22.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpCombMgmtListExcelDownload.json")
	public String getOpenMgmtListExcelDwonload(@ModelAttribute("searchVO") RcpCombVO searchVO, 
					Model model, 
					@RequestParam Map<String, Object> pRequestParamMap,
					HttpServletRequest request, 
					HttpServletResponse response)
	{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(결합서비스) 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [RcpCombVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","개통관리");
			String searchVal = "신청일자:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|검색구분:["+searchVO.getSearchCd()+","+searchVO.getSearchVal()+"]"+
					"|결합유형:"+searchVO.getCombTypeCd()+
					"|승인상태:"+searchVO.getRsltCd()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			BatchJobVO vo = new BatchJobVO();
						
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00191");
			vo.setSessionUserId(loginInfo.getUserId());		
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"strtDt\":" + "\"" + searchVO.getStrtDt() + "\""
						+ ",\"endDt\":" + "\"" + searchVO.getEndDt() + "\"" 
						+ ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\"" 
						+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\"" 
						+ ",\"combTypeCd\":" + "\"" + searchVO.getCombTypeCd() + "\"" 
						+ ",\"rsltCd\":" + "\"" + searchVO.getRsltCd() + "\"" 
						+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
						+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
						+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
						+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
						+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
						
					);
			
			btchMgmtService.insertBatchRequest(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		return "jsonView"; 
	}

	@RequestMapping("/rcp/rcpMgmt/canRequestCombineSolo.json")
	public String canRequestCombineSolo(@RequestParam String pRateCd, Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			resultMap.put("canRequestCombineSolo", rcpCombMgmtService.canRequestCombineSolo(pRateCd));
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
