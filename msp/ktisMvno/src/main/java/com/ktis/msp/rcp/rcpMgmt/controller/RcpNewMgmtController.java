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

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.rcp.rcpMgmt.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.service.GrpInsrMgmtService;
import com.ktis.msp.ptnr.grpinsrmgmt.vo.GrpInsrReqVO;
import com.ktis.msp.rcp.rcpMgmt.vo.AppFormMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.DvcChgMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.OpenInfoVO;
import com.ktis.msp.rcp.rcpMgmt.vo.PointTxnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpBnftVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNatnVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpNewMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrdtListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpPrmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RntlPrdtInfoVO;
import com.ktis.msp.rcp.rcpMgmt.vo.ScanVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class RcpNewMgmtController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;

	/** rcpMgmtService */
	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private RcpNewMgmtService rcpNewMgmtService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private AppFormMgmtService appFormService;

	@Autowired
	private DvcChgMgmtService dvcChgService;

	@Autowired
	private FileDownService  fileDownService;

	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ScanService scanService;

	@Autowired
	private GrpInsrMgmtService grpInsrService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * pointRecoveryService
	 * 2022.02.17 포인트 원복 로직으로 추가
	 */
	@Autowired
	private PointRecoveryService pointRecoveryService;
    @Autowired
    private FathService fathService;

	/**
	 * 신청관리 New
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpNewList.do")
	public ModelAndView getRcpNewList( @ModelAttribute("searchVO") RcpListVO searchVO,
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

			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
			if("10".equals(loginInfo.getUserOrgnTypeCd())){
				knoteYn = "Y";
			}

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.getModelMap().addAttribute("knoteYn", knoteYn);

			model.addAttribute("startDate",orgCommonService.getWantDay(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpNewList");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}


	/**
	 * 신청등록
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpNewMgmt.do")
	public ModelAndView getRcpNewMgmt( @ModelAttribute("searchVO") RcpListVO searchVO,
									   ModelMap model,
									   HttpServletRequest pRequest,
									   HttpServletResponse pResponse,
									   @RequestParam Map<String, Object> pRequestParamMap){

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

			if("MSP1010100".equals(pRequestParamMap.get("trgtMenuId"))){	//서식지함
				logger.debug("pRequestParamMap=" + pRequestParamMap.get("scanId"));
				logger.debug("pRequestParamMap=" + pRequestParamMap.get("shopCd"));
				logger.debug("pRequestParamMap=" + pRequestParamMap.get("procStatCd"));
				logger.debug("searchVO=" + searchVO);

				// 서식지상태변경
				AppFormMgmtVO vo = new AppFormMgmtVO();
				vo.setScanId((String) pRequestParamMap.get("scanId"));
				vo.setProcStatCd("10"); // 신청등록
				vo.setSessionUserId(searchVO.getSessionUserId());
				appFormService.updateAppFormData(vo);
			}

			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");

			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
			if("10".equals(loginInfo.getUserOrgnTypeCd())){
				knoteYn = "Y";
			}

			String authRealShopYn = "N";
			if (menuAuthService.chkUsrGrpAuth(loginInfo.getUserId(),"A_SHOPNM_R")) {
				authRealShopYn = "Y";
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("authRealShopYn : " + authRealShopYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);

			model.addAttribute("startDate",orgCommonService.getWantDay(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());

			/* 기기변경 가능 권한 체크 */
			/*RcpNewMgmtVO rcpNewMgmtVO = new RcpNewMgmtVO();
			rcpNewMgmtVO.setMenuId("MSP1003030");		//기기변경 신청 MenuID
			rcpNewMgmtVO.setUserId(loginInfo.getUserId());
			String rsltDvcChgYN = rcpNewMgmtService.getMenuAuthYN(rcpNewMgmtVO);
			model.addAttribute("dvcChgAuthYn", rsltDvcChgYN);*/
			// [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
			// 기기변경 신청 메뉴권한 -> 공통코드()로 변경
			model.addAttribute("dvcChgAuthYn", rcpNewMgmtService.getDvcChgAuthYn(searchVO.getSessionUserOrgnTypeCd()));

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

			model.addAttribute("hubOrderYn", rcpNewMgmtService.getHubOrderYn());

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpNewMgmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 단말재고확인
	 */
	@RequestMapping("/rcp/rcpMgmt/checkNewPhoneSN.json")
	public String checkNewPhoneSN(HttpServletRequest request, HttpServletResponse response,
								  RcpDetailVO rcpListVO,
								  ModelMap model,
								  @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpListVO);

			if( rcpListVO.getReqPhoneSn() == null || "".equals(rcpListVO.getReqPhoneSn())){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpListVO.getSessionUserOrgnTypeCd()) && !rcpListVO.getSessionUserOrgnId().equals(rcpListVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<RcpPrdtListVO> rsltList = rcpNewMgmtService.checkNewPhoneSN(rcpListVO);

			Map<String, Object> rsltMap =  makeResultMultiRowNotEgovMap(pReqParamMap, rsltList, rsltList.size());

			resultMap.put("result", rsltMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 재고확인 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpPrdt.do", method={POST, GET})
	public ModelAndView getRcpPrdt( @ModelAttribute("searchVO") RcpDetailVO searchVO,
									ModelMap model,
									HttpServletRequest pRequest,
									HttpServletResponse pResponse,
									@RequestParam Map<String, Object> pRequestParamMap){
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpPrdt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 재고확인 팝업 조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getPrdtStatList.json")
	public String getPrdtStatList(HttpServletRequest request, HttpServletResponse response,
								  RcpPrdtListVO rcpPrdtListVO,
								  ModelMap model,
								  @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpPrdtListVO);

			if( rcpPrdtListVO.getPrdtSrlNum() == null || "".equals(rcpPrdtListVO.getPrdtSrlNum()) ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			List<RcpPrdtListVO> rsltList = rcpNewMgmtService.getPrdtStatList(rcpPrdtListVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, rsltList, rsltList.size());

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 요금제그룹 조회(제휴요금제)
	 */
	@RequestMapping("/rcp/rcpMgmt/getRateGrpByInfo.json")
	public String getRateGrpByInfo(HttpServletRequest request, HttpServletResponse response,
								   RcpNewMgmtVO rcpNewMgmtVO,
								   ModelMap model,
								   @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			if( rcpNewMgmtVO.getRateCd() == null || "".equals(rcpNewMgmtVO.getRateCd()) ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			String strRslt = rcpNewMgmtService.getRateGrpByInfo(rcpNewMgmtVO);

			Map<String, Object> dataMap = new HashMap<String, Object>();

			dataMap.put("rateGrpYn", strRslt);

			resultMap.put("data", dataMap);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "성공");

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 신청서 복사
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/copyRcpMgmt.json")
	public String copyRcpMgmt(HttpServletRequest pRequest
			, HttpServletResponse pResponse
			, RcpDetailVO rcpDetailVO
			, ModelMap model
			, @RequestParam Map<String, Object> pReqParamMap
			, SessionStatus status)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);
			rcpDetailVO.setRid(loginInfo.getUserId());
			rcpDetailVO.setRip(pRequest.getRemoteAddr());
			rcpDetailVO.setShopUsmId(loginInfo.getUserId());

			if( rcpDetailVO.getTrgtRequestKey() == null || "".equals(rcpDetailVO.getTrgtRequestKey()) ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}

			// 기존신청정보 ACEN 연동대상 확인(ACEN 연동이 최종완료되지 않은 경우 신청서 복사 불가)
			String acenStatus= rcpNewMgmtService.getAcenReqStatus(rcpDetailVO.getTrgtRequestKey());
			if(!KtisUtil.isEmpty(acenStatus) && !"Y".equals(acenStatus)){
				throw new MvnoRunException(-1, "A'CEN 해피콜 진행중인 신청서로 해당 요청 처리가 불가합니다.");
			}

			// 신청정보 복사
			int returnCnt = rcpNewMgmtService.copyRcpMgmt(rcpDetailVO);

			if ( returnCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );

			}else{

				// 기존 조건이 A'Cen 대상인 경우 A'Cen 전송 대상으로 추가
				if("Y".equals(acenStatus)){
					int acenRslt = rcpNewMgmtService.insertAcenReqStatus(rcpDetailVO.getRequestKey());
					resultMap.put("isAcenTrg", (acenRslt > 0) ? "Y" : "N");
				}

				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");

				resultMap.put("requestKey", rcpDetailVO.getRequestKey());
				logger.info(generateLogMsg("rcpDetailVO.getRequestKey() " + rcpDetailVO.getRequestKey()));
			}

		} catch ( Exception e) {

			resultMap.clear();


			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					e.getMessage(), "MSP1000014", "신청등록"))
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
	 * 개통관리 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getOpenList.json")
	public String getOpenList(@ModelAttribute("searchVO") RcpListVO searchVO,
							  HttpServletRequest request,
							  HttpServletResponse response,
							  @RequestParam Map<String, Object> paramMap,
							  ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(paramMap.isEmpty()){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg","필수정보가 없습니다.");
			model.addAttribute("result", resultMap);
			return "jsonView";
		}

		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			List<OpenInfoVO> rcpList = rcpNewMgmtService.getOpenList(searchVO, paramMap);

			resultMap = makeResultMultiRowNotEgovMap(paramMap, rcpList, rcpList.size());

		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap))
				throw new MvnoErrorException(e);
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 신청관리 목록 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpNewMgmtList.json")
	public String getRcpNewMgmtList(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){
					searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
				} else if(typeCd == 30){
					searchVO.setpAgentCode(loginInfo.getUserOrgnId());
				}
			}

			List<EgovMap> rcpList = rcpNewMgmtService.getRcpNewMgmtList(searchVO, paramMap);

			resultMap =  makeResultMultiRow(paramMap, rcpList);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000015", "신청관리"))
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
	 * @Description : 신청관리 엑셀 자료생성
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2017. 04. 28.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpNewMgmtListExcelDownload.json")
	public String getOpenMgmtListExcelDwonload(@ModelAttribute("searchVO") RcpListVO searchVO,
											   Model model,
											   @RequestParam Map<String, Object> pRequestParamMap,
											   HttpServletRequest request,
											   HttpServletResponse response)
	{


		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [RcpListVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		//2015-02-27 필수값 체크
		if(
				(searchVO.getSearchStartDt()==null     ||  "".equals(searchVO.getSearchStartDt())) &&
						(searchVO.getSearchEndDt()==null       ||  "".equals(searchVO.getSearchEndDt())) &&
						(searchVO.getpServiceType()==null      ||  "".equals(searchVO.getpServiceType())) &&
						(searchVO.getpOperType()==null         ||  "".equals(searchVO.getpOperType())) &&
						(searchVO.getCntpntShopId()==null      ||  "".equals(searchVO.getCntpntShopId())) &&
						(searchVO.getpAgentCode()==null        ||  "".equals(searchVO.getpAgentCode())) &&
						(searchVO.getpRequestStateCode()==null ||  "".equals(searchVO.getpRequestStateCode()))&&
						(searchVO.getpSearchName()==null       ||  "".equals(searchVO.getpSearchName()) )&&
						(searchVO.getpPstate()==null           ||  "".equals(searchVO.getpPstate()) )&&
						(searchVO.getReqBuyType()==null        ||  "".equals(searchVO.getReqBuyType()))
		){
			return "<script>alert('There is no required input Parameter');history.back(-1);</script>";
		}

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
			excelMap.put("MENU_NM","신청관리");
			String searchVal = "신청일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|서비스구분:"+searchVO.getpServiceType()+
					"|업무구분:"+searchVO.getpOperType()+
					"|대리점:"+searchVO.getpAgentCode()+
					"|진행상태:"+searchVO.getpRequestStateCode()+
					"|신청취소:"+searchVO.getpPstate()+
					"|검색구분:["+searchVO.getpSearchGbn()+","+searchVO.getpSearchName()+"]"+
					"|구매유형:"+searchVO.getReqBuyType()+
					"|할인유형:"+searchVO.getSprtTp()+
					"|모집경로:"+searchVO.getOnOffType()+
					"|상품구분:"+searchVO.getProdType() +
					"|eSIM여부" + searchVO.getEsimYn() +
					"|A'CEN여부" + searchVO.getAcenYn() +
					"|셀프개통제외" + searchVO.getSelfYn() +
					"|생년월일" + searchVO.getpBirthDayVal()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();


			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00097");
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

			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
					+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\""
					+ ",\"pServiceType\":" + "\"" + searchVO.getpServiceType() + "\""
					+ ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\""
					+ ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\""
					+ ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\""
					+ ",\"pRequestStateCode\":" + "\"" + searchVO.getpRequestStateCode() + "\""
					+ ",\"pPstate\":" + "\"" + searchVO.getpPstate() + "\""
					+ ",\"pSearchGbn\":" + "\"" + searchVO.getpSearchGbn() + "\""
					+ ",\"pSearchName\":" + "\"" + searchVO.getpSearchName() + "\""
					+ ",\"reqBuyType\":" + "\"" + searchVO.getReqBuyType() + "\""
					+ ",\"sprtTp\":" + "\"" + searchVO.getSprtTp() + "\""
					+ ",\"prodType\":" + "\"" + searchVO.getProdType() + "\""
					+ ",\"onOffType\":" + "\"" + searchVO.getOnOffType() + "\""
					+ ",\"esimYn\":" + "\"" + searchVO.getEsimYn() + "\""
					+ ",\"acenYn\":" + "\"" + searchVO.getAcenYn() + "\""
					+ ",\"selfYn\":" + "\"" + searchVO.getSelfYn() + "\""
					+ ",\"pBirthDayVal\":" + "\"" + searchVO.getpBirthDayVal() + "\""
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

	@RequestMapping("/rcp/rcpMgmt/updateRcpNewDetail.json")
	public String updateRcpNewDetail(HttpServletRequest pRequest
			, HttpServletResponse pResponse
			, RcpDetailVO rcpDetailVO
			, ModelMap model
			, @RequestParam Map<String, Object> pReqParamMap
			, SessionStatus status)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);
			rcpDetailVO.setRid(loginInfo.getUserId());
			rcpDetailVO.setRip(pRequest.getRemoteAddr());
			rcpDetailVO.setShopUsmId(loginInfo.getUserId());

			//---------------------------------------------------------------
			// 신청등록, 신청관리(링커스), 신청관리(렌탈) 중복 수정 방지.
			//---------------------------------------------------------------
//			RcpMgmtVO rcpMgmt = new RcpMgmtVO();
//			rcpMgmt.setRequestKey(rcpDetailVO.getRequestKey());
//			rcpMgmt.setSysRdate(rcpDetailVO.getSysRdate());
//			if("".equals(rcpMgmtService.chkMcpRequest(rcpMgmt))) {
//				throw new Exception("문구 수정 해야함.");
//			}

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 90일 이내 동일명의 개통 10회선 초과 확인
			if(("NA".equals(rcpDetailVO.getCstmrType()) || "NM".equals(rcpDetailVO.getCstmrType()) || "FN".equals(rcpDetailVO.getCstmrType()))
					&& "NAC3".equals(rcpDetailVO.getOperType()) && "UU".equals(rcpDetailVO.getReqBuyType())) {

				if(rcpNewMgmtService.chkCstmrCnt(rcpDetailVO)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "90일 이내 동일명의 개통취소 10회 이상으로 신청 제한대상 입니다.");

					model.addAttribute("result", resultMap);

					return "jsonView";
				}
			}

			// ACEN 연동대상 확인 (ACEN 연동이 최종완료되지 않은 경우 예약번호 삭제 불가)
			if("30".equals(rcpDetailVO.getPstate())){
				String acenStatus= rcpNewMgmtService.getAcenReqStatus(rcpDetailVO.getRequestKey());
				if(!KtisUtil.isEmpty(acenStatus) && !"Y".equals(acenStatus)){
					throw new MvnoRunException(-1, "A'CEN 해피콜 진행중인 신청서로 해당 요청 처리가 불가합니다.");
				}
			}

			/**
			 * 2022.02.17 포인트 원복 로직 1.신청서상태 - 상태가 [고객취소, 관리자삭제, 예약번호삭제, 관리자삭제(유심번호미입력)]로 저장할
			 * 경우 포인트 원복 처리를 한다. 2.진행상태 - 상태가 [개통처리오류]로 저장할 경우 포인트 원복 처리를 한다.
			 *
			 * 1번상태에서는 2번상태와 무관하게 원복, 2번상태에서는 [개통처리오류]일경우 1번상태와 무관하게 원복
			 */
			//rcpDetailVO.setPstate("10"); //TEST용
			if ("10".equals(rcpDetailVO.getPstate()) || "20".equals(rcpDetailVO.getPstate())
					|| "30".equals(rcpDetailVO.getPstate()) || "40".equals(rcpDetailVO.getPstate())
					|| "31".equals(rcpDetailVO.getRequestStateCode())) {

				// 1.고객정보조회 select
				PointTxnVO selctPointTxnVO = pointRecoveryService.selectCustPointTxn(rcpDetailVO.getRequestKey());

				if(selctPointTxnVO != null && selctPointTxnVO.getPointTxnRsnCd() != null && !"".equals(selctPointTxnVO.getPointTxnRsnCd())) {
					//포인트처리사유코드[BE0010] 을 포인트적립사유코드[BE0009]로 변경
					//2022.03.18 유심개통[U21]을 제외한 코드는 포인트적립사유코드[BE0009]로 변경
					if(!"U21".equals(selctPointTxnVO.getPointTxnRsnCd()) ){

						selctPointTxnVO.setPointTxnRsnCd(selctPointTxnVO.getPointTxnRsnCd().concat("1").replace('U', 'S'));
					}

					// ID,IP 셋팅
					selctPointTxnVO.setCretIp(pRequest.getRemoteAddr());
					selctPointTxnVO.setCretId(loginInfo.getUserId());

					// 2.고객포인트지급기준기본 update
					pointRecoveryService.setTransactionPoint(selctPointTxnVO);
				}
			}
			int returnCnt = rcpNewMgmtService.updateRcpNewDetail(rcpDetailVO);

			if ( returnCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");

				/* v2017.02 신청관리 간소화 RequestKey Return 값으로 추가 */
				resultMap.put("requestKey", rcpDetailVO.getRequestKey());
				logger.info(generateLogMsg("rcpDetailVO.getRequestKey() " + rcpDetailVO.getRequestKey()));

				/**
				 * SRM18020186086 서식지자동생성
				 * 신청서 상태 정상, 개통유형 신규/번호이동, 공통코드(RCP3001) 등록된 대리점, scanId 가 없으면 서식지 생성
				 */
				if(rcpNewMgmtService.getAppFormMakeCheck(rcpDetailVO) > 0
						&& ("NAC3".equals(rcpDetailVO.getOperType()) || "MNP3".equals(rcpDetailVO.getOperType()))
						&& (rcpDetailVO.getScanId() == null || "".equals(rcpDetailVO.getScanId()))
						&& "00".equals(rcpDetailVO.getPstate())){

					ScanVO scanVo = new ScanVO();
					scanVo.setRequestKey(rcpDetailVO.getRequestKey());
					scanVo.setUserId(rcpDetailVO.getSessionUserId());
					scanVo.setScanPath(propertiesService.getString("scan.form.path"));
					scanVo.setScanUrl(propertiesService.getString("scan.form.url"));

					logger.debug("scanVO=" + scanVo);

					scanService.prodSendScan(scanVo);
				}
			}

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch ( Exception e) {

			resultMap.clear();


			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					e.getMessage(), "MSP1000014", "신청등록"))
			{
				//v2018.11 PMD 적용 소스 수정
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
	 * 신청정보등록
	 */
	@RequestMapping("/rcp/rcpMgmt/insertRcpNewRequest.json")
	public String insertRcpNewRequest(@ModelAttribute("searchVO") RcpDetailVO vo,
									  ModelMap model,
									  HttpServletRequest pRequest,
									  HttpServletResponse pResponse,
									  @RequestParam Map<String, Object> pParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(vo);

			// 정보세팅
			vo.setRid(loginInfo.getUserId());
			vo.setRip(pRequest.getRemoteAddr());
			vo.setShopUsmId(loginInfo.getUserId());

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(vo.getSessionUserOrgnTypeCd()) && !vo.getSessionUserOrgnId().equals(vo.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 기기변경인 경우 유심비 면제가 아닌 경우 유심비 조회
			if("HCN3".equals(vo.getOperType()) || "HDN3".equals(vo.getOperType())){
				// 가입비 처리
				vo.setJoinPayMthdCd("1");
				vo.setJoinPriceType("P");
				vo.setJoinPrice("0");

				if("1".equals(vo.getUsimPayMthdCd())){
					vo.setUsimPrice("0");
					vo.setUsimPriceType("N");
				}else{
					if("2".equals(vo.getUsimPayMthdCd())){
						vo.setUsimPriceType("R");
					}else{
						vo.setUsimPriceType("B");
					}
					vo.setUsimPrice(dvcChgService.getUsimPrice(vo));
				}

				//v2017.03 신청관리 간소화 기기변경 등록 시 고객식별번호 조회 하여 설정
				if( (vo.getCstmrNativeRrn1() == null || "".equals(vo.getCstmrNativeRrn1())) ||
						(vo.getCstmrNativeRrn2() == null || "".equals(vo.getCstmrNativeRrn2())) ){

					RcpNewMgmtVO rcpNewMgmtVO = rcpNewMgmtService.getUserInfo(vo);

					String strUserSsn = rcpNewMgmtVO.getUserSsn();

					if(strUserSsn == null || "".equals(strUserSsn)){
						resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
						resultMap.put("msg", "고객 식별 번호가 존재 하지 않습니다.");
						model.addAttribute("result", resultMap);
						return "jsonView";
					}

					vo.setCstmrNativeRrn1(strUserSsn.substring(0,6));
					vo.setCstmrNativeRrn2(strUserSsn.substring(6,strUserSsn.length()));
				}
			}else{
				vo.setUsimPriceType("B");
				vo.setJoinPriceType("I");
			}

			// 예약번호 리턴
			resultMap = null;

			resultMap = rcpNewMgmtService.insertRcpNewRequest(vo);

			if(resultMap == null || !resultMap.containsKey("resNo") || !resultMap.containsKey("requestKey")){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
		} catch ( Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(),
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()),
					e.getMessage(), "MSP1000014", "신청등록"))
			{
				//v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			}
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		logger.debug("resultMap=" + resultMap);

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/getRntlPrdtInfo.json")
	public String getRntlPrdtInfo(HttpServletRequest request, HttpServletResponse response,
								  RntlPrdtInfoVO rtlPrdtInfoVO,
								  ModelMap model,
								  @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rtlPrdtInfoVO);

			List<RntlPrdtInfoVO> rsltList = rcpNewMgmtService.getRntlPrdtInfo(rtlPrdtInfoVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, rsltList, rsltList.size());

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * 프로모션 정책 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpPrmt.do", method={POST, GET})
	public ModelAndView getRcpPrmt( @ModelAttribute("searchVO") RcpPrmtVO rcpPrmtVO,
									ModelMap model,
									HttpServletRequest pRequest,
									HttpServletResponse pResponse,
									@RequestParam Map<String, Object> pRequestParamMap){
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(rcpPrmtVO);

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpPrmt");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	@RequestMapping("/rcp/rcpMgmt/getRcpPrmtPrdcList.json")
	public String getRcpPrmtPrdcList(HttpServletRequest request, HttpServletResponse response,
									 RcpPrmtVO rcpPrmtVO,
									 ModelMap model,
									 @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpPrmtVO);

			List<RcpPrmtVO> rsltList = rcpNewMgmtService.getRcpPrmtPrdcList(rcpPrmtVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, rsltList, rsltList.size());

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap)){
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/getRcpPrmtList.json")
	public String getRcpPrmtList(HttpServletRequest request, HttpServletResponse response,
								 RcpPrmtVO rcpPrmtVO,
								 ModelMap model,
								 @RequestParam Map<String, Object> pReqParamMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpPrmtVO);

			List<RcpPrmtVO> rsltList = rcpNewMgmtService.getRcpPrmtList(rcpPrmtVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, rsltList, rsltList.size());

		} catch (Exception e) {
			resultMap.clear();

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping(value = "/rcp/rcpNewMgmt/getRcpBnft.do", method={POST, GET})
	public String getRcpBnft(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap){

		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			return "/rcp/rcpMgmt/rcpBnft";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	@RequestMapping(value = "/rcp/rcpNewMgmt/getRcpBnftList.json")
	public String getRcpBnftList(HttpServletRequest request,
								 HttpServletResponse response,
								 @ModelAttribute("RcpBnftVO") RcpBnftVO rcpBnftVO,
								 ModelMap model,
								 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			List<RcpBnftVO> resultList = rcpNewMgmtService.getRcpBnftList(rcpBnftVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		return "jsonView";
	}

	/**
	 * 기변가능여부확인
	 * 2018-01-12 DvcChgMgmtController 에서 이관
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgPsblCheck.json")
	public String getDvcChgPsblCheck( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
									  ModelMap model,
									  HttpServletRequest request,
									  HttpServletResponse response,
									  @RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			//심플할인약정고객인지 체크
			//20200122고객요청으로 심플할인 체크 삭제
			/*dvcChgService.getDvcChgSimCheck(searchVO);*/

			List<?> list = dvcChgService.getDvcChgPsblCheck(searchVO);

			resultMap = makeResultMultiRow(searchVO, list);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch(Exception e) {
			//resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", e.getMessage());
		}

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * N-STEP 전송시 서식지 유무 체크하여 생성 처리
	 * 2018-01-12 DvcChgMgmtController 에서 이관
	 */
	@RequestMapping("/rcp/rcpMgmt/saveRcpRequest.json")
	public String saveRcpRequest(HttpServletRequest req
			, HttpServletResponse res
			, RcpDetailVO vo
			, ModelMap model
			, @RequestParam Map<String, Object> paramMap
			, SessionStatus status)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(req, res);
			loginInfo.putSessionToVo(vo);
			vo.setRid(loginInfo.getUserId());
			vo.setRip(req.getRemoteAddr());
			vo.setShopUsmId(loginInfo.getUserId());

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(vo.getSessionUserOrgnTypeCd()) && !vo.getSessionUserOrgnId().equals(vo.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 고객센터 TM 을 통한 기기변경 신청인 경우 서식지를 자동 생성
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("resNo", (String) paramMap.get("resNo"));

			String scanId = rcpMgmtService.getMcpRequestScanId(map);
			logger.debug("scanId=" + scanId);
			if("10".equals(vo.getSessionUserOrgnTypeCd()) && scanId == null){
				logger.debug("서식지 생성===========================================");

				ScanVO scanVo = new ScanVO();
				scanVo.setRequestKey(vo.getRequestKey());
				scanVo.setUserId(vo.getSessionUserId());
				scanVo.setScanPath(propertiesService.getString("scan.form.path"));
				scanVo.setScanUrl(propertiesService.getString("scan.form.url"));

				logger.debug("scanVO=" + scanVo);

				scanService.prodSendScan(scanVo);
			}
			map.clear();

//			int returnCnt = rcpMgmtService.updateRcpDetail(vo);
			int returnCnt = rcpNewMgmtService.updateRcpNewDetail(vo);
			if ( returnCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch ( Exception e) {
			resultMap.clear();


			if (!getErrReturn(e, (Map<String, Object>) resultMap, req.getServletPath(), "", "", "MSP1000014", "신청등록"))
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
	 * SMS 인증문자 발송
	 * 2018-01-12 DvcChgMgmtController 에서 이관
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/sendSmsAuth.json")
	public String sendSmsAuth(@ModelAttribute("searchVO") DvcChgMgmtVO vo,
							  HttpServletRequest request,
							  HttpServletResponse response,
							  @RequestParam Map<String, Object> paramMap,
							  ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(paramMap);

			resultMap.put("otpNo", dvcChgService.sendSmsAuth(vo));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	@RequestMapping(value = "/rcp/rcpNewMgmt/getRcpNatn.do", method={POST, GET})
	public String getRcpNatn(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap){

		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			return "/rcp/rcpMgmt/rcpNatn";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	@RequestMapping(value = "/rcp/rcpNewMgmt/getRcpNatnList.json")
	public String getRcpNatnList(HttpServletRequest request,
								 HttpServletResponse response,
								 @ModelAttribute("RcpNatnVO") RcpNatnVO rcpNatnVO,
								 ModelMap model,
								 @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			List<RcpNatnVO> resultList = rcpNewMgmtService.getRcpNatnList(rcpNatnVO);

			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		return "jsonView";
	}

	@RequestMapping(value = "/rcp/rcpNewMgmt/getSaleinfo.json")
	public String getSaleinfo(HttpServletRequest request,
							  HttpServletResponse response,
							  @ModelAttribute("RcpDetailVO") RcpDetailVO rcpDetailVO,
							  ModelMap model,
							  @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);

			List<?> list = rcpNewMgmtService.getSaleinfo(rcpDetailVO);

			resultMap =  makeResultMultiRow(pReqParamMap, list);

			model.addAttribute("result", resultMap);

		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/checkKnoteShop.json")
	public String checkKnoteShop(HttpServletRequest pRequest
			, HttpServletResponse pResponse
			, RcpDetailVO rcpDetailVO
			, ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int returnCnt = rcpMgmtService.getKnoteShopYn(rcpDetailVO);

			if(returnCnt > 0) {
				//throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));

				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "K-Note 서식지로만 개통 가능한 판매점입니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
		}catch(Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/checkAuth.json")
	public String checkAuth(HttpServletRequest pRequest
			, HttpServletResponse pResponse
			, RcpDetailVO rcpDetailVO
			, ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			HashMap<String, String> rtnMap = rcpMgmtService.checkAuth(rcpDetailVO);
			String isAuth = rtnMap.get("IS_NOT_AUTH_AGENT");
			String selfCi = rtnMap.get("SELF_CSTMR_CI");

			if("N".equals(isAuth)) {
				if(StringUtils.isBlank(selfCi)) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "본인인증값(CI)이 없습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}
		}catch(Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/getNstepSendUrl.json")
	public String getNstepSendUrl(HttpServletRequest pRequest
			, HttpServletResponse pResponse
			, RcpDetailVO rcpDetailVO
			, ModelMap model
			, @RequestParam Map<String, Object> pReqParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			boolean isSendFS5 = rcpMgmtService.checkSendFS5();

			if(isSendFS5) {
				resultMap.put("sendUrl", "/rcp/rcpMgmt/osstServiceCall.json" );
			} else {
				resultMap.put("sendUrl", "/rcp/rcpMgmt/updateRcpSend.json" );
			}
		}catch(Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		resultMap.put("msg", "");
		model.addAttribute("result", resultMap);

		return "jsonView";
	}



	/**
	 * @Description ACEN 번호이동 위약금 입력대상 확인
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : rcpDetailVO
	 * @Param : model
	 * @Author : hsy
	 * @CreateDate : 2024.06.20
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getAcenPenaltyStatus.json")
	public String getCsResPerCntByDt(HttpServletRequest pRequest
									,HttpServletResponse pResponse
									,RcpDetailVO rcpDetailVO
									,ModelMap model)
	{

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);

			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			String status= "N";

			if(!KtisUtil.isEmpty(rcpDetailVO.getRequestKey())){
				status= rcpNewMgmtService.getAcenPenaltyStatus(rcpDetailVO.getRequestKey());
			}

			resultMap.put("status", status);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1000014", "신청등록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	
	/**
	 * 신청관리 (일시납)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpListPayFull.do")
	public ModelAndView getRcpListPayFull( @ModelAttribute("searchVO") RcpListVO searchVO,
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

			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
			if("10".equals(loginInfo.getUserOrgnTypeCd())){
				knoteYn = "Y";
			}

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.getModelMap().addAttribute("knoteYn", knoteYn);

			model.addAttribute("startDate",orgCommonService.getWantDay(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtPayFull");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 신청관리(일시납) 목록 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtPayFullList.json")
	public String getRcpMgmtPayFullList(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			List<EgovMap> rcpList = rcpNewMgmtService.getRcpMgmtPayFullList(searchVO, paramMap);

			resultMap =  makeResultMultiRow(paramMap, rcpList);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1010089", "신청관리(일시납)"))
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
	 * @Description : 신청관리(일시납) 엑셀 자료생성
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2024. 07. 24.
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpMgmtPayFullListExcelDownload.json")
	public String getRcpMgmtPayFullListExcelDownload(@ModelAttribute("searchVO") RcpListVO searchVO,
											   Model model,
											   @RequestParam Map<String, Object> pRequestParamMap,
											   HttpServletRequest request,
											   HttpServletResponse response)
	{


		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(일시납) 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [RcpListVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		//필수값 체크
		if(
				(searchVO.getSearchStartDt()==null     ||  "".equals(searchVO.getSearchStartDt())) &&
						(searchVO.getSearchEndDt()==null       ||  "".equals(searchVO.getSearchEndDt())) &&
						(searchVO.getpRequestStateCode()==null ||  "".equals(searchVO.getpRequestStateCode()))&&
						(searchVO.getPayRstCd()==null      ||  "".equals(searchVO.getPayRstCd())) &&
						(searchVO.getSearchVal()==null       ||  "".equals(searchVO.getSearchVal()))&&
						(searchVO.getpPstate()==null           ||  "".equals(searchVO.getpPstate()))&&
						(searchVO.getOperType()==null         ||  "".equals(searchVO.getOperType()))
		){
			return "<script>alert('There is no required input Parameter');history.back(-1);</script>";
		}

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
			excelMap.put("MENU_NM","신청관리(일시납)");
			String searchVal = "신청일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|진행상태:"+searchVO.getpRequestStateCode()+
					"|결제상태:"+searchVO.getPayRstCd()+
					"|검색구분:["+searchVO.getpSearchGbn()+","+searchVO.getSearchVal()+"]"+
					"|신청취소:"+searchVO.getpPstate()+
					"|업무구분:"+searchVO.getpOperType()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();


			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00226");
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

			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
					+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\""
					+ ",\"pRequestStateCode\":" + "\"" + searchVO.getpRequestStateCode() + "\""
					+ ",\"payRstCd\":" + "\"" + searchVO.getPayRstCd() + "\""
					+ ",\"pSearchGbn\":" + "\"" + searchVO.getpSearchGbn() + "\""
					+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\""
					+ ",\"pPstate\":" + "\"" + searchVO.getpPstate() + "\""
					+ ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\""
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
	
	/**
	 * @Description : 신청관리(일시납) 결제상태 변경
	 * @Param  : RcpListVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2024. 10. 15.
	 */
	@RequestMapping("/rcp/rcpMgmt/rcpMgmtPayFullUpdate.json")
	public String getRcpMgmtPayFullUpdate(@ModelAttribute("searchVO") RcpListVO searchVO,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap,
											ModelMap model) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(일시납) 결제상태 변경 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			if (searchVO.getRequestKey() == null || "".equals(searchVO.getRequestKey()) ) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "주문번호는 필수 항목입니다.");
			} else {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				searchVO.setIpAddr(ipAddr);
				rcpNewMgmtService.rcpMgmtPayFullUpdate(searchVO);
	 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			}
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		logger.debug("resultMap ==  " + resultMap);
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}

	/** 요금제 제휴처 조회 */
	@RequestMapping("/rcp/rcpMgmt/getJehuProdType.json")
	public String getJehuProdType(HttpServletRequest request
															 ,HttpServletResponse response
															 ,RcpListVO searchVO
															 ,ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			if(KtisUtil.isEmpty(searchVO.getSocCode())){
				throw new MvnoServiceException("필수정보가 없습니다.");
			}

			String jehuProdType = rcpNewMgmtService.getJehuProdType(searchVO.getSocCode());

			resultMap.put("jehuProdType", jehuProdType);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록")){
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/** 제휴처 및 제휴사 약관 validation check */
	@RequestMapping("/rcp/rcpMgmt/termsValidationCheck.json")
	public String termsValidationCheck(HttpServletRequest request
																		,HttpServletResponse response
																		,@ModelAttribute RcpListVO searchVO
																		,@RequestParam Map<String, String> pRequestParamMap
																		,ModelMap model){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);

			// 제휴처 체크
			Map<String, String> jehuChkMap = rcpNewMgmtService.checkJehuValidation(pRequestParamMap);
			resultMap.put("jehuChkMap", jehuChkMap);

			// 제휴사 체크
			Map<String, String> partnerChkMap = rcpNewMgmtService.checkPartnerValidation(pRequestParamMap);
			resultMap.put("partnerChkMap", partnerChkMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		}catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록")){
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 신청관리(New) 화면 로딩
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/rcpRequestList.do")
	public ModelAndView rcpRequestList( @ModelAttribute("searchVO") RcpListVO searchVO,
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

			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			String knoteYn = rcpMgmtService.getKnoteYn(loginInfo.getUserOrgnId());
			if("10".equals(loginInfo.getUserOrgnTypeCd())){
				knoteYn = "Y";
			}

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.getModelMap().addAttribute("knoteYn", knoteYn);

			model.addAttribute("startDate",orgCommonService.getWantDay(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			modelAndView.setViewName("/rcp/rcpMgmt/rcpRequest");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 신청관리 목록 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpRequestList.json")
	public String getRcpRequestList(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);

			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){
					searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
				} else if(typeCd == 30){
					searchVO.setpAgentCode(loginInfo.getUserOrgnId());
				}
			}

			List<EgovMap> rcpList = rcpNewMgmtService.getRcpRequestList(searchVO, paramMap);

			resultMap =  makeResultMultiRow(paramMap, rcpList);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000018", "신청관리(New)"))
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
	 * 신청관리(New) 엑셀 자료생성
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpRequestListExcelDownload.json")
	public String getRcpRequestListExcelDownload(@ModelAttribute("searchVO") RcpListVO searchVO,
											   Model model,
											   @RequestParam Map<String, Object> pRequestParamMap,
											   HttpServletRequest request,
											   HttpServletResponse response)
	{


		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(New) 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [RcpListVO] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(
				(searchVO.getSearchStartDt()==null     ||  "".equals(searchVO.getSearchStartDt())) &&
						(searchVO.getSearchEndDt()==null       ||  "".equals(searchVO.getSearchEndDt())) &&
						(searchVO.getpServiceType()==null      ||  "".equals(searchVO.getpServiceType())) &&
						(searchVO.getpOperType()==null         ||  "".equals(searchVO.getpOperType())) &&
						(searchVO.getCntpntShopId()==null      ||  "".equals(searchVO.getCntpntShopId())) &&
						(searchVO.getpAgentCode()==null        ||  "".equals(searchVO.getpAgentCode())) &&
						(searchVO.getpRequestStateCode()==null ||  "".equals(searchVO.getpRequestStateCode()))&&
						(searchVO.getpSearchName()==null       ||  "".equals(searchVO.getpSearchName()) )&&
						(searchVO.getpPstate()==null           ||  "".equals(searchVO.getpPstate()) )&&
						(searchVO.getReqBuyType()==null        ||  "".equals(searchVO.getReqBuyType()))
		){
			return "<script>alert('There is no required input Parameter');history.back(-1);</script>";
		}

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
			excelMap.put("MENU_NM","신청관리(New)");
			String searchVal = "신청일자:"+searchVO.getSearchStartDt()+"~"+searchVO.getSearchEndDt()+
					"|서비스구분:"+searchVO.getpServiceType()+
					"|업무구분:"+searchVO.getpOperType()+
					"|대리점:"+searchVO.getpAgentCode()+
					"|진행상태:"+searchVO.getpRequestStateCode()+
					"|신청취소:"+searchVO.getpPstate()+
					"|검색구분:["+searchVO.getpSearchGbn()+","+searchVO.getpSearchName()+"]"+
					"|구매유형:"+searchVO.getReqBuyType()+
					"|할인유형:"+searchVO.getSprtTp()+
					"|모집경로:"+searchVO.getOnOffType()+
					"|상품구분:"+searchVO.getProdType() +
					"|eSIM여부" + searchVO.getEsimYn() +
					"|A'CEN여부" + searchVO.getAcenYn() +
					"|셀프개통제외" + searchVO.getSelfYn() +
					"|생년월일" + searchVO.getpBirthDayVal()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00244");
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

			vo.setExecParam("{\"searchStartDt\":" + "\"" + searchVO.getSearchStartDt() + "\""
					+ ",\"searchEndDt\":" + "\"" + searchVO.getSearchEndDt() + "\""
					+ ",\"pServiceType\":" + "\"" + searchVO.getpServiceType() + "\""
					+ ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\""
					+ ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\""
					+ ",\"pRequestStateCode\":" + "\"" + searchVO.getpRequestStateCode() + "\""
					+ ",\"pPstate\":" + "\"" + searchVO.getpPstate() + "\""
					+ ",\"pSearchGbn\":" + "\"" + searchVO.getpSearchGbn() + "\""
					+ ",\"pSearchName\":" + "\"" + searchVO.getpSearchName() + "\""
					+ ",\"reqBuyType\":" + "\"" + searchVO.getReqBuyType() + "\""
					+ ",\"sprtTp\":" + "\"" + searchVO.getSprtTp() + "\""
					+ ",\"prodType\":" + "\"" + searchVO.getProdType() + "\""
					+ ",\"onOffType\":" + "\"" + searchVO.getOnOffType() + "\""
					+ ",\"esimYn\":" + "\"" + searchVO.getEsimYn() + "\""
					+ ",\"acenYn\":" + "\"" + searchVO.getAcenYn() + "\""
					+ ",\"selfYn\":" + "\"" + searchVO.getSelfYn() + "\""
					+ ",\"pBirthDayVal\":" + "\"" + searchVO.getpBirthDayVal() + "\""
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
	
	
	/**
	 * 신청관리 스캔솔루션 TEST PAGE
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpNewListScanT.do")
	public ModelAndView getRcpNewListT( @ModelAttribute("searchVO") RcpListVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			String scanSearchUrlT =  propertiesService.getString("scan.search.url.t");
			String faxSearchUrl =  propertiesService.getString("fax.search.url");

			String scanDownloadUrl =  propertiesService.getString("scan.download.url.t");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrlT", scanSearchUrlT);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);

			modelAndView.getModelMap().addAttribute("agentVersion", agentVersion);
			modelAndView.getModelMap().addAttribute("serverUrl", serverUrl);
			modelAndView.getModelMap().addAttribute("scanDownloadUrl", scanDownloadUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.getModelMap().addAttribute("sessionUserOrgnId", searchVO.getSessionUserOrgnId());

			model.addAttribute("startDate",orgCommonService.getWantDay(-7));
			model.addAttribute("endDate",orgCommonService.getToDay());

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpNewListScanT");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
}
