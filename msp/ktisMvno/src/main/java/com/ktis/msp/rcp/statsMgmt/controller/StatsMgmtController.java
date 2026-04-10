package com.ktis.msp.rcp.statsMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktis.msp.rcp.rcpMgmt.service.FathService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ktis.msp.base.KtisUtil;
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
import com.ktis.msp.org.userinfomgmt.vo.UserInfoMgmtVo;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.statsMgmt.service.StatsMgmtService;
import com.ktis.msp.rcp.statsMgmt.vo.StatsMgmtVo;
import com.ktis.msp.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class StatsMgmtController extends BaseController {

	protected Logger logger = LogManager.getLogger(getClass());

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private StatsMgmtService statsMgmtService;

	@Autowired
	private FileDownService fileDownService;

	@Autowired
	private OrgCommonService orgCommonService;

	@Autowired
	private BtchMgmtService btchMgmtService;

	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
    @Autowired
    private FathService fathService;

	/** Constructor */
	public StatsMgmtController() {
		setLogPrefix("[StatsMgmtController] ");
	}

	/**
	 * 개통현황 통계 페이지 이동
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsPage.do", method = {POST, GET})
	public String getOpenStatList(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 개통현황 통계 조회
	 */
	@RequestMapping(value = "/stats/statsMgmt/getOpenStatList.json", method = {POST, GET})
	public String getOpenStatListJson(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
									  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getOpenStatList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000103",
					"개통현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 개통현황 엑셀
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsCustListExcel.json")
	@ResponseBody
	public String getOpenStatListEx(ModelMap model, HttpServletRequest request, HttpServletResponse response,
									@RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("개통현황 엑셀 저장 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getOpenStatListEx(pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo + "개통현황_";// 파일명
			String strSheetname = "개통현황";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"일자", "대리점명", "신규개통", "번호이동", "서식지오류", "개통건수(소계)", "신규개통정지", "번호이동정지", "정지건수(소계)",
					"신규개통취소", "번호이동취소", "취소건수(소계)", "신규해지", "번호이동해지", "해지건수(소계)", "누적건수"};

			String[] strValue = {"statDt", "orgnNm", "nacOpenCnt", "mnpOpenCnt", "errOpenCnt", "openCnt", "nacStopCnt",
					"mnpStopCnt", "stopCnt", "nacCanCnt", "mnpCanCnt", "canCnt", "nacCloseCnt", "mnpCloseCnt",
					"closeCnt", "acmlCnt"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000};

			int[] intLen = {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 해지현황 초기 화면 호출
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsCanPage.do", method = {POST, GET})
	public String getCanStatList(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsCanMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 해지현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCanStatList.json", method = {POST, GET})
	public String getCanStatListJson(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
									 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getCanStatList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000106",
					"해지현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 실시간 개통현황 초기 화면 호출
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsRealTimeMgmt.do", method = {POST, GET})
	public String statsRealTime(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			Map<String, Object> resultMap = new HashMap<String, Object>();

			pRequestParamMap.put("grpId", "DEV0000");
			pRequestParamMap.put("cdVal", "TIMER");

			resultMap = statsMgmtService.getTimerVal(pRequestParamMap);

			pRequest.setAttribute("timer", resultMap.get("val"));

			return "/rcp/statsMgmt/statsRealTimeMgmt";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 실시간 개통현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsRealTime.json", method = {POST, GET})
	public String getStatsRealTimeJson(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
									   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsRealTimeJson(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000108",
					"실시간 개통현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 실시간 요금제별현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getRateStats.json", method = {POST, GET})
	public String getRateStatsJson(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getRateStatsJson(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000108",
					"실시간 개통현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 실시간 대리점별현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 01. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getAgntStats.json", method = {POST, GET})
	public String getAgntStatsJson(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getAgntStatsJson(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000108",
					"실시간 개통현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 재약정현황 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsSimPage.do", method = {POST, GET})
	public String statsSimPage(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model,
							   @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("startDate", orgCommonService.getWantDay(-7));
			model.addAttribute("endDate", orgCommonService.getToDay());
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsSimMgmt";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 재약정현황 조회
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsSimList.json")
	public String getStatsSimList(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest request,
								  HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("재약정현황 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getStatsSimList(statsMgmtVo, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "재약정현황");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 재약정현황 엑셀 다운로드
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsSimListExcel.json")
	public String getStatsSimListExcel(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo,
									   HttpServletRequest request, HttpServletResponse response, ModelMap model,
									   @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("재약정현황 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [StatsMgmtVO] = " + statsMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// ----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID", request.getParameter("menuId"));
			excelMap.put("USR_ID", loginInfo.getUserId());
			excelMap.put("USR_NM", loginInfo.getUserName());
			excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM", "MSP");
			excelMap.put("MENU_NM", "재약정현황");
			String searchVal = "|조회기간:" + statsMgmtVo.getSrchStrtDt() + "~" + statsMgmtVo.getSrchEndDt() + "|상태:"
					+ statsMgmtVo.getSubStatus() + "|검색구분:" + statsMgmtVo.getSearchGbn() + "|검색값:"
					+ statsMgmtVo.getSearchName() + "|구매유형:" + statsMgmtVo.getOnOffType() + "|재약정경로:"
					+ statsMgmtVo.getOrderType();
			if (searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL", searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00171");
			vo.setSessionUserId(loginInfo.getUserId());
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);

			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();

			vo.setExecParam("{\"srchStrtDt\":" + "\"" + statsMgmtVo.getSrchStrtDt() + "\"" + ",\"srchEndDt\":" + "\""
					+ statsMgmtVo.getSrchEndDt() + "\"" + ",\"subStatus\":" + "\"" + statsMgmtVo.getSubStatus() + "\""
					+ ",\"searchGbn\":" + "\"" + statsMgmtVo.getSearchGbn() + "\"" + ",\"searchName\":" + "\""
					+ statsMgmtVo.getSearchName() + "\"" + ",\"onOffType\":" + "\"" + statsMgmtVo.getOnOffType() + "\""
					+ ",\"orderType\":" + "\"" + statsMgmtVo.getOrderType() + "\"" + ",\"userId\":" + "\""
					+ loginInfo.getUserId() + "\"" + ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\""
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" + ",\"menuId\":" + "\"" + request.getParameter("menuId")
					+ "\"" + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");

			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 재약정현황 배송정보 수정
	 * @Param : statsMgmtVo
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2019. 10. 10.
	 */
	@RequestMapping("/stats/statsMgmt/updateDlvryInfo.json")
	public String updateDlvryInfo(StatsMgmtVo statsMgmtVo, HttpServletRequest request, HttpServletResponse response,
								  ModelMap model, @RequestParam Map<String, Object> pReqParamMap) {
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("재약정현황 배송정보 수정 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(statsMgmtVo);

			// 본사 권한
			if (!"10".equals(statsMgmtVo.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// ----------------------------------------------------------------
			// 받으시는분, 메모, e-mail, 상세주소 특수문자(<, >) 체크
			// ----------------------------------------------------------------
			String strMnfctNm = String.valueOf(statsMgmtVo.getDlvryName()); // 받으시는분
			String strMemo = String.valueOf(statsMgmtVo.getAgrmMemo()); // 메모
			String strDtlAddr = String.valueOf(statsMgmtVo.getDlvryAddrDtl()); // 상세주소
			if (StringUtil.chkString(strMnfctNm) || StringUtil.chkString(strMemo) || StringUtil.chkString(strDtlAddr)) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.CHK_STRING", null, Locale.getDefault()));
			}

			int returnCnt = statsMgmtService.updateDlvryInfo(statsMgmtVo);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "");

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch (Exception e) {
			resultMap.clear(); // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}

		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 특정폰사용고객현황
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsModelOpen.do", method = {POST, GET})
	public String statsModelOpen(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model,
								 @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("openEndDate", orgCommonService.getWantDay(-1));
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsModelOpen";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 특정폰사용고객현황 조회
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getModelOpenList.json")
	public String getModelOpenList(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest request,
								   HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("특정폰사용고객현황 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<StatsMgmtVo> resultList = statsMgmtService.getModelOpenList(statsMgmtVo, pRequestParamMap);
			int totalCount = 0;
			if (resultList != null && resultList.size() > 0) {
				totalCount = resultList.get(0).getTOTAL_COUNT();
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			} else {
				resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			}

		} catch (Exception e) {
			resultMap.clear();
			getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "특정폰사용고객현황");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 재약정현황 엑셀 다운로드
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getModelOpenListExcel.json")
	public String getModelOpenListExcel(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo,
										HttpServletRequest request, HttpServletResponse response, ModelMap model,
										@RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("특정폰사용고객현황 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [StatsMgmtVO] = " + statsMgmtVo.toString()));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// ----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID", request.getParameter("menuId"));
			excelMap.put("USR_ID", loginInfo.getUserId());
			excelMap.put("USR_NM", loginInfo.getUserName());
			excelMap.put("ORGN_ID", loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM", "MSP");
			excelMap.put("MENU_NM", "특정폰사용고객현황");
			String searchVal = "|조회기간:" + statsMgmtVo.getOpenEndDate() + "|상태:" + statsMgmtVo.getSubStatus() + "|모델명:"
					+ statsMgmtVo.getModelName();
			if (searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL", searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00178");
			vo.setSessionUserId(loginInfo.getUserId());
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);

			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();

			vo.setExecParam("{\"openEndDate\":" + "\"" + statsMgmtVo.getOpenEndDate() + "\"" + ",\"subStatus\":" + "\""
					+ statsMgmtVo.getSubStatus() + "\"" + ",\"modelName\":" + "\"" + statsMgmtVo.getModelName() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" + ",\"dwnldRsn\":" + "\""
					+ request.getParameter("DWNLD_RSN") + "\"" + ",\"ipAddr\":" + "\"" + ipAddr + "\"" + ",\"menuId\":"
					+ "\"" + request.getParameter("menuId") + "\"" + ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}");

			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		}

		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : OSST 현황 초기 화면 호출
	 * @Param : void
	 * @Return : String
	 * @Author :
	 * @Create Date : 2021. 02. 02.
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsOsstMgmt.do", method = {POST, GET})
	public String statsOsstMgmt(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsOsstMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : OSST 처리현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author :
	 * @Create Date : 2021. 02. 02.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsOsstMgmtList.json", method = {POST, GET})
	public String getStatsOsstMgmtList(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
									   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsOsstMgmtList(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000111",
					"OSST처리현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : OSST 일일현황 조회
	 * @Param : void
	 * @Return : String
	 * @Author :
	 * @Create Date : 2021. 02. 02.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsOsstMgmtDaily.json", method = {POST, GET})
	public String getStatsOsstMgmtDaily(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
										HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsOsstMgmtDaily(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000111",
					"OSST일일현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : OSST 일일현황 상세 조회
	 * @Param : void
	 * @Return : String
	 * @Author :
	 * @Create Date : 2021. 02. 02.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsOsstMgmtDetail.json", method = {POST, GET})
	public String getStatsOsstMgmtDetail(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
										 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		// ----------------------------------------------------------------
		// 목록 db select
		// ----------------------------------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsOsstMgmtDetail(pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000111",
					"OSST일일현황상세")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 개통현황 엑셀
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @param pRequestParamMap
	 * @return
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsOsstMgmtDetailExcel.json")
	@ResponseBody
	public String getStatsOsstMgmtDetailExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response,
											  @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsOsstMgmtDetailExcel(pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo + "OSST일일현황상세_";// 파일명
			String strSheetname = "일일현황상세";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"MVNO오더번호", "OSST오더번호", "서비스코드", "오류코드", "오류내용", "NSTEPGLOBALID", "처리일시"};

			String[] strValue = {"mvnoOrdNo", "osstOrdNo", "prgrStatCd", "rsltCd", "rsltMsg", "nstepGlobalId",
					"rsltDt"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 20000, 10000, 5000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 명세서 재발행
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsBillingStat.do", method = {POST, GET})
	public String getStatsBillingStat(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
									  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			// model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsBillingStat";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 명세서 재발행 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 05. 19.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsBillingStatList.json", method = {POST, GET})
	public String getStatsBillingStatList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getStatsBillingStatList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping(value = "/stats/statsMgmt/getStatsBillingStatListExcel.json")
	@ResponseBody
	public String getStatsBillingStatListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
											   HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsBillingStatListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "명세서 재발행_";// 파일명
			String strSheetname = "명세서 재발행";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "고객명", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "유심접점", "신청일시", "재발행유형",
					"성공여부"}; // 11

			String[] strValue = {"contractNum", "cstmrNm", "gender", "age", "lstComActvDate", "openAgntNm",
					"onOffType", "fstUsimOrgNm", "rdt", "billAdInfo", "successYn"}; // 11

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 10000, 3000, 3000, 5000, 6000, 5000, 7000, 10000, 13000, 5000}; // 11

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 11

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 유심셀프변경
	 * @Param : searchVO
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsUsimChgInfo.do", method = {POST, GET})
	public String statsUsimChgInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			// model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsUsimChgInfo";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 유심변경 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 06. 16.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getUsimChgList.json", method = {POST, GET})
	public String getUsimChgList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getUsimChgList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 유심변경 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 06. 16.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getUsimChgListExcel.json")
	@ResponseBody
	public String getUsimChgListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
									  HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getUsimChgListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "유심셀프변경_";// 파일명
			String strSheetname = "유심셀프변경";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"MVNO오더ID", "계약번호", "고객명", "전화번호", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "신청일시", "처리상태",
					"유심일련번호", "사전체크결과", "사전체크 불가내용", "사전체크 안내내용", "결과코드", "결과메시지"}; // 16

			String[] strValue = {"mvnoOrdNo", "svcContId", "subLinkName", "ctn", "gender", "age", "lstComActvDate", "openAgntNm", "onOffType", "rdt", "prgrStatCd",
					"iccId", "trgtAtribSbst", "trgtFaluMsg", "trgtInsurMsg", "rsltCd", "rsltMsg"}; // 16

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000, 8000, 8000, 8000}; // 16

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0}; // 16

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 통화내역열람
	 * @Param : searchVO
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCallInfo.do", method = {POST, GET})
	public ModelAndView getCallInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request,
									HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			// model.addAttribute("maskingYn", maskingYn);

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
//			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));



			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);

			modelAndView.setViewName("/rcp/statsMgmt/statsCallListInfo");
			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 통화내역열람 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCallList.json", method = {POST, GET})
	public String getCallList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
							  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getCallList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 통화내역열람 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCallListExcel.json")
	@ResponseBody
	public String getCallListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
								   HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(statsMgmtVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getCallListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "통화열람내역_";// 파일명
			String strSheetname = "통화열람내역";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "고객명", "전화번호", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "신청일자", "요청시작일자",
					"요청종료일자", "수신방법", "열람항목", "음성", "문자", "데이터", "음성+문자", "음성+문자+데이터", "신청사유", "연락가능연락처",
					"세부입력", "결과", "메모", "수정자", "수정일시"}; // 25

			String[] strValue = {"svcContId", "subLinkName", "ctn", "gender", "age", "lstComActvDate", "openAgntNm", "onOffTypeNm", "sysRdate", "startDate",
					"endDate", "recvType", "callType", "typeVoice", "typeText", "typeData", "typeVoiceText", "typeAll", "reqRsn", "callNum",
					"recvText", "procCdNm", "clMemo", "updtId", "sysUdate"}; // 25

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000, 8000, 8000}; // 25

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0}; // 16

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}



	/**
	 * @Description : 통화내역열람 상태 처리 및 서식지 합본
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 15.
	 */
	@RequestMapping("/stats/statsMgmt/updateCall.json")
	public String updateCall(StatsMgmtVo statsMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse,
							 @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(statsMgmtVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if (!"10".equals(statsMgmtVo.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int resultCnt = statsMgmtService.updateCall(statsMgmtVo, loginInfo.getUserId());

			if (resultCnt == 0) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMap.put("msg", "변경대상이 없습니다.");
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				resultMap.put("msg", "");
			}

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 명의변경신청
	 * @Param : searchVO
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgInfo.do", method = {POST, GET})
	public ModelAndView getNameChgInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request,
									   HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
//			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));



			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("isEnabledFT1", fathService.isEnabledFT1());

			modelAndView.setViewName("/rcp/statsMgmt/statsReqNameChgInfo");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 명의변경신청 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgList.json", method = {POST, GET})
	public String getNameChgList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getNameChgList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 명의변경신청 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2025. 12. 31.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgDtl.json", method = {POST, GET})
	public String getNameChgDtl(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getNameChgDtl(pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명의변경신청 조회")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 명의변경 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 13.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgListExcel.json")
	@ResponseBody
	public String getNameChgListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
									  HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getNameListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "명의변경내역_";// 파일명
			String strSheetname = "명의변경내역";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "명의변경예약번호", "양도인고객명", "양도인전화번호", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "유심접점", "신청일자",
					"양도인_고객정보삭제동의", "양수인고객명", "양수인전화번호", "이메일", "고객 혜택 제공을 위한 개인정보 수집 및 관련 동의", "개인정보 처리 위탁 및 혜택 제공 동의", "혜택 제공을 위한 제 3자 제공 동의 : M모바일", "혜택 제공을 위한 제 3자 제공 동의 : KT", "제 3자 제공관련 광고 수신 동의", "고유식별정보 수집이용제공동의",
					"개인,신용정보 수집동의", "개인정보 제3자 제공동의", "제휴서비스를 위한동의", "정보변경여부", "요금제", "명세서유형", "요금납부방법", "은행", "계좌번호", "카드사",
					"카드번호", "유효년월", "우편번호", "주소", "상세주소", "신분증 유형", "발급/만료일자", "발급번호", "결과", "진행상태", "메모",
					"수정자", "수정일시", "제휴처"}; // 43

			String[] strValue = {"svcContId", "mcnResNo", "subLinkName", "ctn", "gender", "age", "lstComActvDate", "openAgntNm", "usimOrgNm", "onOffTypeNm", "sysRdate",
					"clauseCntrDelFlag", "cstmrName", "ctnGet", "cstmrMail", "personalInfoCollectAgree", "clausePriAdFlag", "othersTrnsAgree", "othersTrnsKtAgree","othersAdReceiveAgree", "clauseEssCollectFlag",
					"clausePriCollectFlag", "clausePriOfferFlag", "clauseJehuFlag","reqInfoChgYn", "soc", "cstmrBillSendCode", "reqPayType", "reqBank", "reqAccountNumber", "reqCardCompany",
					"reqCardNo", "reqCardYy", "cstmrPost", "cstmrAddr", "cstmrAddrDtl", "selfCertType", "selfIssuExprDt", "selfIssuNum", "procCdNm", "mcnStateCode", "clMemo",
					"updtId", "sysUdate", "jehuProdType"}; // 43

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000, 8000, 14000, 15000, 13000, 12000, 9000, 9000,
					8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000,
					8000, 8000, 8000}; // 43

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0}; //43

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}



	/**
	 * @Description : 명의변경 상태 처리 및 서식지 합본
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2022. 07. 15.
	 */
	@RequestMapping("/stats/statsMgmt/updateName.json")
	public String updateName(StatsMgmtVo statsMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse,
							 @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(statsMgmtVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if (!"10".equals(statsMgmtVo.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int resultCnt = statsMgmtService.updateName(statsMgmtVo, loginInfo.getUserId());

			if (resultCnt == 0) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMap.put("msg", "변경대상이 없습니다.");
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				resultMap.put("msg", "");
			}

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	/**
	 * @Description : 명의변경현황
	 * @Param : searchVO
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgState.do", method = {POST, GET})
	public ModelAndView getNameChgState(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request,
									   HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
//			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));



			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);

			modelAndView.setViewName("/rcp/statsMgmt/statsReqNameChgState");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 명의변경현황 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2025. 10. 21.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgStateList.json", method = {POST, GET})
	public String getNameChgStateList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getNameChgStateList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명의변경현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 명의변경현황 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 이승국
	 * @Create Date : 2025. 10. 21.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getNameChgStateListExcel.json")
	@ResponseBody
	public String getNameChgStateListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
									  HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getNameStateListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "명의변경현황_";// 파일명
			String strSheetname = "명의변경현황";// 시트명

			// 엑셀 첫줄
			String[] strHead = {
									"계약번호",			"서비스계약번호",			"상태",				"CTN",						"고객명",
									"나이",				"개통일자",					"대리점",				"최초명의변경일자",	"최종명의변경일자",
									"대리인(신청서)",	"대리인연락처(신청서)",	"대리인(현시점)",	"대리인연락처(현시점)"
									}; // 14

			String[] strValue = {
									"contractNum",			"svcCntrNo",				"subStatus",			"ctn",					"subLinkName",
									"age",						"lstComActvDate",		"openAgntCd",		"fstChangeDate",	"lstChangeDate",
									"minorAgentName",	"minorAgentTel",		"lglAgntNm",		"lglAgntNo"
									}; // 14

			// 엑셀 컬럼 사이즈
			int[] intWidth = {
									8000, 10000, 6000, 8000, 8000, 
									5000, 8000, 25000, 10000, 10000,
									10000, 12000, 10000, 12000
								 }; // 14

			int[] intLen = {
									0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
									0, 0, 0, 0
							  }; //14

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 가입신청서 출력요청 화면
	 * @Param : searchVO
	 * @Return : ModelAndView
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsReqJoinFormInfo.do", method = {POST, GET})
	public ModelAndView statsReqJoinFormInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			//login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
//			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);

			modelAndView.setViewName("/rcp/statsMgmt/statsReqJoinFormInfo");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 가입신청서 출력요청 리스트 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/getReqJoinFormList.json", method = {POST, GET})
	public String getReqJoinFormList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getReqJoinFormList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "가입신청서출력요청리스트조회")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 가입신청서 출력요청 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/getReqJoinFormListExcel.json")
	@ResponseBody
	public String getReqJoinFormListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getReqJoinFormListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "가입신청서출력요청_";// 파일명
			String strSheetname = "가입신청서출력요청";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "고객명", "전화번호", "성별", "나이(만)", "개통일", "개통 대리점",
					"모집유형", "유심접점", "신청일자", "수신방법", "결과", "메모", "수정자", "수정일시"};

			String[] strValue = {"svcContId", "subLinkName", "ctn", "gender", "age", "lstComActvDate", "openAgntNm",
					"onOffTypeNm", "usimOrgNm", "sysRdate", "recvType", "procCdNm", "clMemo", "updtId", "sysUdate"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 가입신청서 출력요청 상태 수정
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping("/stats/statsMgmt/updateReqJoinForm.json")
	public String updateReqJoinForm(StatsMgmtVo statsMgmtVo, @RequestBody String data, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(statsMgmtVo);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			if (!"10".equals(statsMgmtVo.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			int resultCnt = statsMgmtService.updateReqJoinForm(statsMgmtVo);

			if (resultCnt == 0) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
				resultMap.put("msg", "변경대상이 없습니다.");
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
				resultMap.put("msg", "");
			}

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	 * @Description : 유심PUK번호 열람신청 화면
	 * @Param : searchVO
	 * @Return : ModelAndView
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsReqUsimPukInfo.do", method = {POST, GET})
	public ModelAndView statsReqUsimPukInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			//login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
//			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);

			modelAndView.setViewName("/rcp/statsMgmt/statsReqUsimPukInfo");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 유심PUK번호 열람신청 리스트 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/getReqUsimPukList.json", method = {POST, GET})
	public String getReqUsimPukList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getReqUsimPukList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "유심PUK번호열람신청리스트조회")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 유심PUK번호 열람신청 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping(value = "/stats/statsMgmt/getReqUsimPukListExcel.json")
	@ResponseBody
	public String getReqUsimPukListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getReqUsimPukListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "유심PUK번호열람신청_";// 파일명
			String strSheetname = "유심PUK번호열람신청";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "고객명", "전화번호", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "유심접점", "신청일시"};

			String[] strValue = {"svcContId", "subLinkName", "ctn", "gender", "age", "lstComActvDate", "openAgntNm", "onOffTypeNm", "usimOrgNm", "sysRdate"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : OMD단말 등록 현황
	 * @Param : void
	 * @Return : String
	 * @Author : 권오승
	 * @Create Date : 2021. 11. 16.
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsOmdRegInfo.do", method = {POST, GET})
	public ModelAndView statsOmdRegInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model, UserInfoMgmtVo userInfoMgmtVo) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("OMD단말 등록 현황 START"));
		logger.info(generateLogMsg("================================================================="));

		ModelAndView modelAndView = new ModelAndView();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(userInfoMgmtVo);

			// 본사 권한
			if (!"10".equals(userInfoMgmtVo.getSessionUserOrgnTypeCd())) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));


			modelAndView.setViewName("rcp/statsMgmt/statsOmdRegInfo");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : OMD단말 등록 현황 리스트 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 권오승
	 * @CreateDate : 2021. 11. 16.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getOmdRegList.json", method = {POST, GET})
	public String getOmdRegList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getOmdRegList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "OMD단말등록현황리스트조회")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : OMD단말 등록 현황 리스트 조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 권오승
	 * @CreateDate : 2021. 11. 16.
	 */

	@RequestMapping(value = "/stats/statsMgmt/getOmdRegListExcel.json")
	@ResponseBody
	public String getOmdRegListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getOmdRegListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "OMD단말등록현황_";// 파일명
			String strSheetname = "OMD단말등록현황";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"신청일자", "모델명", "이벤트종류", "일련번호", "결과코드", "결과메시지", "결과", "IMEI1", "IMEI2", "EID"};

			String[] strValue = {"sysRdate", "reqModelName", "evntCd", "reqPhoneSn", "rsltCd", "rsltMsg", "rsltNm", "imei1", "imei2", "eid"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 10000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 가입번호조회
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2022. 10. 07.
	 */
	@RequestMapping(value = "/stats/statsMgmt/ownPhoNumReq.do", method = {POST, GET})
	public String ownPhoNumReq(HttpServletRequest pRequest,
							   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			// model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsOwnPhoNum";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 가입번호조회 요청 리스트
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2022. 10. 07.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getOwnPhoNumReqList.json", method = {POST, GET})
	public String getOwnPhoNumReqList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getOwnPhoNumReqList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 가입번호조회 요청 리스트 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박준성
	 * @Create Date : 2022. 10. 07.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getOwnPhoNumReqListExcel.json")
	@ResponseBody
	public String getOwnPhoNumReqListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request,
										   HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getOwnPhoNumReqListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "가입번호조회_";// 파일명
			String strSheetname = "가입번호조회";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"조회일", "고객명", "생년월일", "성별", "본인인증유형", "회선수"}; // 6

			String[] strValue = {"sysRdate", "cstmrName", "birth", "gender", "onlineAuthTypeNm", "lineCnt"}; // 6

			// 엑셀 컬럼 사이즈
			int[] intWidth = {8000, 8000, 8000, 8000, 8000, 8000}; // 6

			int[] intLen = {0, 0, 0, 0, 0, 0}; // 6

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 안심보험 신청현황 화면
	 * @Param : searchVO
	 * @Return : ModelAndView
	 * @Author : wooki
	 * @CreateDate : 2022.10.25
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsReqInsrInfo.do", method = {POST, GET})
	public ModelAndView statsReqInsrInfo(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			//login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			ModelAndView modelAndView = new ModelAndView();

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			String scnUrl = propertiesService.getString("mcp.url");
			String scanSearchUrl = propertiesService.getString("scan.search.url");
			String faxSearchUrl = propertiesService.getString("fax.search.url");

			String scanDownloadUrl = propertiesService.getString("scan.download.url");
//			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i = 0; i < macInfoList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String) map.get("cdVal"), map.get("cdDsc"));
			}

			String agentVersion = (String) resultMap.get("AGENT_VERSION");    // 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");        // 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("faxSearchUrl", faxSearchUrl);
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);

			modelAndView.setViewName("/rcp/statsMgmt/statsReqInsrInfo");

			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description : 통화품질불량 접수
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 10. 20.
	 */

	@RequestMapping(value = "/stats/statsMgmt/callQualityAs.do", method = {POST, GET})
	public String callQualityAs(HttpServletRequest pRequest,
								HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsCallQualityAs";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 통화품질불량 접수 조회
	 * @Param : void
	 * @Return : String
	 * @Author : 박효진
	 * @Create Date : 2022. 10. 24.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCallQualityAsList.json", method = {POST, GET})
	public String getCallQualityAsList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getCallQualityAsList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "명세서 재발행")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description : 통화품질불량 접수 엑셀
	 * @Param : searchVO
	 * @Return : String
	 * @Author : 박효진
	 * @CreateDate : 2022. 10. 24.
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCallQualityAsListExcel.json")
	@ResponseBody
	public String getCallQualityAsListExcel(StatsMgmtVo statsMgmtVo, ModelMap model,
											HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getCallQualityAsListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "통화품질불량접수_";// 파일명
			String strSheetname = "통화품질불량접수";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"계약번호", "고객명", "전화번호", "신청일시", "개통일", "개통 대리점", "신청인",
					"연락가능한 연락처", "증상", "증상 발생기간", "발생현상", "지역", "상담내용", "결과"}; // 14

			String[] strValue = {"contractNum", "cstmrName", "mobileNo", "regDt", "lstComActvDate", "openAgntNm", "regNm",
					"regMobileNo", "errorCd", "errorDate", "errorTypeCd", "errorLocTypeNm", "counselDetail", "counselRsltCdNm"};  // 14

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 8000, 8000, 8000, 8000, 8000, 8000, 15000, 8000, 5000, 5000, 10000, 3000}; // 14

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 14

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : 통화품질불량 접수 수정
	 * @Param : searchVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2022.08.02
	 */
	@RequestMapping("/stats/statsMgmt/updateCallQualityAs.json")
	public String updateCallQualityAs(StatsMgmtVo statsMgmtVo, @RequestBody String data,
									  HttpServletRequest pRequest, HttpServletResponse pResponse,
									  @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {


		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(statsMgmtVo);

			statsMgmtService.updateCallQualityAs(statsMgmtVo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap)) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description 예약상담 신청현황 페이지 진입
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.14
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsCsResInfo.do", method = {POST, GET})
	public String statsCsResInfo(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("maxPerCnt", statsMgmtService.getMaxPerCnt()); // 예약상담 허용인원 등록 최대 수
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsCsResInfo";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 예약상담 리스트 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.18
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCsResInfoList.json")
	public String getCsResInfoList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 예약상담 리스트 조회
			List<?> resultList = statsMgmtService.getCsResInfoList(searchVO);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010046", "예약상담 신청현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 예약상담 리스트 상세 조회
	 * @Param : searchVo
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.19
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCsResDtlInfoList.json")
	public String getCsResDtlInfoList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 예약상담 리스트 상세 조회
			List<?> resultList = statsMgmtService.getCsResDtlInfoList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010046", "예약상담 신청현황 상세")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 시간대별 예약상담 허용인원 조회
	 * @Param : searchVo
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.21
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCsResPerCntByDt.json")
	public String getCsResPerCntByDt(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 예약일시가 빈값이면 오늘 날짜+1로 세팅
			if (KtisUtil.isEmpty(searchVO.getCsResDt())) {
				searchVO.setCsResDt(orgCommonService.getWantDay(1));
			}

			List<?> resultList = statsMgmtService.getCsResPerCntByDt(searchVO);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010046", "예약상담 등록/수정")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		model.addAttribute("csResDt", searchVO.getCsResDt());
		return "jsonView";
	}

	/**
	 * @Description 시간대별 예약상담 허용인원 등록/수정
	 * @Param : searchVo
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.21
	 */
	@RequestMapping(value = "/stats/statsMgmt/mergeCsResPerCntByDt.json")
	public String mergeCsResPerCntByDt(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}


			// 등록, 수정에 필요한 파라미터 세팅
			searchVO.setUserId(loginInfo.getUserId());

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			cal.add(Calendar.MONTH, 2);
			String afterTwoMonth = sdf.format(cal.getTime());

			// 등록, 수정
			statsMgmtService.mergeCsResPerCntByDt(searchVO, orgCommonService.getToDay(), orgCommonService.getCertainMonthMaxDay(afterTwoMonth));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010046", "예약상담 등록/수정")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 예약상담 신청현황 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.04.25
	 */
	@RequestMapping(value = "/stats/statsMgmt/getCsResListExcel.json")
	@ResponseBody
	public String getCsResListExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
									HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 엑셀 데이터 조회
			List<?> resultList = statsMgmtService.getCsResListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "예약상담내역_";    // 파일명
			String strSheetname = "예약상담내역";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"접수일시", "예약일시", "접수 시간대", "허용 인원", "상태값", "고객명", "계약번호", "예약 고객", "예약 가능고객", "종결 고객",
					"미처리 고객", "처리결과", "VOC(대)", "VOC(중)", "VOC(소)", "VOC(상세)", "AP 사용자명", "AP계정", "처리일자"}; // 19

			String[] strValue = {"regstDtFormat", "csResDtFormat", "csResTmNm", "perCnt", "resStat", "cstmrNm", "contractNum", "resCnt", "remainCnt", "comCnt",
					"uncomCnt", "csStat", "vocFir", "vocSec", "vocThi", "vocDtl", "apNm", "apId", "endDttm"}; // 19

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 5000, 6000, 8000, 5000, 5000, 6000}; // 19

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 19

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		// return json
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 평생할인 프로모션 가입 현황 진입 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.09.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/prmtAutoAdd.do", method = {POST, GET})
	public String prmtAutoAdd(HttpServletRequest pRequest,
							  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("평생할인 프로모션 현황 진입 페이지 START"));
		logger.info(generateLogMsg("================================================================="));

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsPrmtAutoAdd";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 평생할인 프로모션 가입이력 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.09.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getPrmtAutoAddList.json")
	public String getPrmtAutoAddList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 대리점인 경우, 다른 대리점으로 조회했을시 리턴
			if("20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				if(searchVO == null
						|| !loginInfo.getUserOrgnId().equals(searchVO.getCntpntShopId())
						|| !loginInfo.getUserOrgnNm().equals(searchVO.getCntpntShopNm())){
					throw new MvnoRunException(-1, "대리점은 본인 대리점만 조회 가능 합니다.");
				}
			}

			// 프로모션 부가서비스 가입 이력 조회
			List<EgovMap> resultList = statsMgmtService.getPrmtAutoAddList(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010047", "평생할인 자동가입 현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}


	/**
	 * @Description 평생할인 프로모션 처리여부 수정
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	@RequestMapping(value = "/stats/statsMgmt/updatePrmtAutoAddProc.json")
	public String updatePrmtAutoAddProc(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
										HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 처리상태(수동) UPDATE
			searchVO.setUserId(loginInfo.getUserId());
			statsMgmtService.updatePrmtAutoAddProc(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010047", "평생할인 자동가입 현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 프로모션 가입이력 상세 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	@RequestMapping(value = "/stats/statsMgmt/getPrmtAutoAddDetail.json")
	public String getPrmtAutoAddDetail(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 프로모션 부가서비스 가입이력 상세조회
			List<EgovMap> resultList = statsMgmtService.getPrmtAutoAddDetail(searchVO, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010047", "평생할인 자동가입 현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 평생할인 프로모션 가입이력 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.04
	 */
	@RequestMapping(value = "/stats/statsMgmt/getPrmtAutoAddListExcel.json")
	@ResponseBody
	public String getPrmtAutoAddListExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//대리점인 경우, 다른 대리점으로 조회했을시 리턴
			if("20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				if(searchVO == null
						|| !loginInfo.getUserOrgnId().equals(searchVO.getCntpntShopId())
						|| !loginInfo.getUserOrgnNm().equals(searchVO.getCntpntShopNm())){
					throw new MvnoRunException(-1, "대리점은 본인 대리점만 조회 가능 합니다.");
				}
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getPrmtAutoAddListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인자동가입현황_";    // 파일명
			String strSheetname = "평생할인 자동가입 현황";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"프로모션 가입 요청일", "업무구분", "프로모션명", "최초 성공여부", "재처리 성공여부", "계약번호", "고객명", "처리여부", "처리일시", "처리자ID", "처리자 이름", "처리메모"}; //12
			String[] strValue = {"effectiveDate", "cdDsc", "prmtNm", "succYn", "fnlSuccYn","contractNum", "custNm", "procYn", "procDate", "procId", "procNm", "procMemo"}; // 12

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 3000, 9000, 5000, 5000, 5000, 8000, 3000, 6000, 5000, 5000, 8000}; // 12

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 12

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 프로모션 부가서비스 가입 재처리
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.05
	 */
	@RequestMapping(value = "/stats/statsMgmt/retryPrmtAutoAdd.json")
	public String reprocessPrmtAutoAdd(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 필수값 세팅
			searchVO.setSessionUserOrgnTypeCd(loginInfo.getUserOrgnTypeCd());
			searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
			searchVO.setUserId(loginInfo.getUserId());
			searchVO.setAccessIp(statsMgmtService.getClientIp());
			searchVO.setSrchStrtDt(orgCommonService.getToDay());

			// 1. 재처리 금지 검사
			statsMgmtService.reprocessPrmtAutoChk(searchVO);

			// 2. 부가서비스 가입 연동
			resultMap = statsMgmtService.reprocessPrmtAutoAdd(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		}catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010047", "평생할인 자동가입 현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 평생할인 프로모션 적용 대상 조회 진입 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsDisPrmtTrgMgmt.do", method = {POST, GET})
	public String statsDisPrmtTrgMgmt(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsDisPrmtTrgMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 평생할인 프로모션 적용 대상 조회
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtTrgMgmtList.json")
	public String getDisPrmtTrgMgmtList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//평생할인 프로모션 적용 대상 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtTrgMgmtList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010048", "평생할인 프로모션 적용 대상")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * @Description 평생할인 프로모션 적용 대상 회원 조회 엑셀 다운로드
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtTrgMgmtListExcel.json")
	@ResponseBody
	public String getDisPrmtTrgMgmtListExcel(@ModelAttribute("searchVO") StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
											 HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-7));
			}
			if(KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
				searchVO.setSrchStrtDt(orgCommonService.getToDay());
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtTrgMgmtListExcel(searchVO,pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인프로모션적용대상_";    // 파일명
			String strSheetname = "평생할인 프로모션 적용 대상";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"마스터시퀀스","상세시퀀스","등록일자","처리여부","처리자ID", "처리일시", "계약번호","업무구분","처리번호","전문발생일시","이벤트적용일시","등록자ID", "등록일시"}; //13
			String[] strValue = {"trgMstSeq","trgDtlSeq","regstDt","procYn","procId","procDttm","contractNum","evntCd","evntTrtmNo","evntTrtmDt","effectiveDate","regstId","regstDttm"}; // 13

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000}; // 13

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 13

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		// return json
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 업무구분별 평생할인 정책 적용 대상 상세 이력
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtTrgMgmtDtl.json")
	public String getDisPrmtTrgMgmtDtl(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 평생할인 정책 적용 대상 상세 이력 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtTrgMgmtDtl(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010048", "평생할인 프로모션 적용 대상")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * @Description 업무구분별 평생할인 정책 적용 대상 상세 이력 팝업 전체보기
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtTrgMgmtDtlPop.json")
	public String getDisPrmtTrgMgmtDtlPop(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 업무구분별 평생할인 정책 적용 대상 상세 이력 팝업 전체보기
			List<EgovMap> resultList = statsMgmtService.getDisPrmtTrgMgmtDtlPop(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010048", "평생할인 프로모션 적용 대상")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}


	/**
	 * @Description 업무구분별 평생할인 정책 적용 대상 상세 이력 엑셀 다운로드
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @Author : hsy
	 * @CreateDate : 2023.10.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtTrgMgmtDtlPopExcel.json")
	@ResponseBody
	public String getDisPrmtTrgMgmtDtlPopExcel(@ModelAttribute("searchVO") StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
											   HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			//본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-7));
			}
			if(KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
				searchVO.setSrchStrtDt(orgCommonService.getToDay());
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtTrgMgmtDtlPopExcel(searchVO,pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인프로모션적용대상상세이력_";    // 파일명
			String strSheetname = "평생할인 프로모션 적용 대상 상세이력";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"상세시퀀스","등록일자","처리여부", "처리자ID","처리일시","계약번호","업무구분","처리번호","전문발생일시","이벤트적용일시","등록자ID","등록일시",}; //12
			String[] strValue = {"trgDtlSeq","regstDt","procYn","procId","procDttm","contractNum","evntCd","evntTrtmNo","evntTrtmDt","effectiveDate","regstId","regstDttm"}; // 12

			// 엑셀 컬럼 사이즈
			int[] intWidth = {6000, 5000, 5000, 3000, 6000, 5000, 5000, 5000, 6000, 6000, 3000, 6000}; // 12

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 12

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		// return json
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * @Description 평생할인 프로모션 지연 관리 진입 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.01.31
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsDisPrmtDelay.do", method = {POST, GET})
	public String statsDisPrmtDelay(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-1));
			model.addAttribute("srchToday", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsDisPrmtDelay";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}


	/**
	 * @Description 평생할인 프로모션 지연 관리 조회
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.02.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtDelayMst.json")
	public String getDisPrmtDelayMst(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 대리점인 경우, 다른 대리점으로 조회했을시 리턴
			if("20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				if(searchVO == null
						|| !loginInfo.getUserOrgnId().equals(searchVO.getCntpntShopId())
						|| !loginInfo.getUserOrgnNm().equals(searchVO.getCntpntShopNm())){
					throw new MvnoRunException(-1, "대리점은 본인 대리점만 조회 가능 합니다.");
				}
			}

			/* 날짜 조회 검사 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date today = formatter.parse(orgCommonService.getToDay());
			Date srchDt = formatter.parse(searchVO.getSrchStrtDt());

			if(!srchDt.before(today)){
				throw new MvnoRunException(-1, "오늘보다 이전인 경우만 조회 가능합니다.");
			}

			//평생할인 프로모션 지연 관리 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtDelayMst(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010049", "평생할인 자동가입 지연 현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";

	}

	/**
	 * @Description 평생할인 프로모션 가입이력 상세 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.02.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtDelayDtl.json")
	public String getDisPrmtDelayDtl(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 프로모션 부가서비스 가입이력 상세조회
			searchVO.setLateChk("Y");
			List<EgovMap> resultList = statsMgmtService.getPrmtAutoAddDetail(searchVO, pRequestParamMap);

			//가입 이력 중, 최종 부가서비스 내역만 조회
			List<EgovMap> maxDtList = statsMgmtService.getPrmtDelayMaxDt(resultList);

			resultMap = makeResultMultiRow(pRequestParamMap, maxDtList);
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010049", "평생할인 자동가입 지연 현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 자동가입 지연 현황 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.02.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtDelayMstExcel.json")
	@ResponseBody
	public String getDisPrmtDelayMstExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-1));
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtDelayMstExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인자동가입지연현황_";    // 파일명
			String strSheetname = "평생할인 자동가입 지연 현황";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"프로모션 적용 일시", "프로모션명", "고객명", "계약번호", "휴대폰 번호", "업무구분", "수동 처리 여부", "수동 처리 일시", "프로모션 적용 대리점", "프로모션 적용 대리점 코드"}; //10
			String[] strValue = {"effectiveDate", "prmtNm", "custNm", "contractNum", "subscriberNo","cdDsc", "procYn", "procDate", "prmtAgnt", "prmtAgntCd"}; //10

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 10000, 8000, 5000, 6000, 3000, 6000, 7000, 8000, 8000}; //10

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //10

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : KT인터넷 개통현황
	 * @Param : searchVO
	 * @Return : ModelAndView
	 * @Author : wooki
	 * @CreateDate : 2024.06.04
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsInetInfo.do", method = {POST, GET})
	public String statsInetInfo(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model,
			   @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("startDate", orgCommonService.getWantDay(-7));
			model.addAttribute("endDate", orgCommonService.getToDay());
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			model.addAttribute("maskingYn", maskingYn);

			return "/rcp/statsMgmt/statsInetMgmt";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * @Description : KT인터넷 개통현황 조회
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsInetList.json")
	public String getStatsInetList(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest request,
								  HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("KT인터넷 개통현황 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getStatsInetList(statsMgmtVo, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "KT인터넷 개통현황");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : KT인터넷 개통현황 변경이력 조회
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsInetHistList.json")
	public String getStatsInetHistList(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("KT인터넷 개통현황 변경이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = statsMgmtService.getStatsInetHistList(statsMgmtVo, pRequestParamMap);
			
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);
			
		} catch (Exception e) {
			resultMap.clear();
			getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "", "KT인터넷 개통현황 변경이력");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description : KT인터넷 개통현황 엑셀 다운로드
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/getStatsInetListExcel.json")
	@ResponseBody
	public String getStatsInetListExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-1));
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getStatsInetListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "KT인터넷개통현황_";    // 파일명
			String strSheetname = "KT인터넷 개통현황";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"고객명", "생년월일", "성별", "연락처", "서비스계약ID", "고객ID", "상품코드", "상품명", "상태", "접수일", "개통일자(설치완료)", "해지일자", "모집경로", "인터넷ID","결합여부"}; //15
			String[] strValue = {"custNm","birthDt","gender","ctn","svcCntrIdMsk","custId","prodCd","prodNm","status","regDt","openDt","canDt","regType","internetId","combYn"}; //15

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 10000, 3000, 10000, 10000, 10000, 6000, 6000, 6000, 8000, 8000, 8000, 8000, 10000, 3000}; //15

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //15

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description M쇼핑할인 회원정보 진입 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.05.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsMcashJoinCustMgmt.do", method = {POST, GET})
	public String getMcashMemInfo(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M쇼핑할인 회원정보 진입 페이지 START"));
		logger.info(generateLogMsg("================================================================="));

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsMcashJoinCustMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description M쇼핑할인 회원정보 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.05.27
	 */
	@RequestMapping(value = "/stats/statsMgmt/getMcashJoinCustMgmtList.do")
	public String getMcashList(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// M쇼핑할인 회원정보 조회
			List<EgovMap> resultList = statsMgmtService.getMcashJoinCustMgmtList(statsMgmtVo, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010050", "M쇼핑할인 회원정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description M캐시 회원 수납 이력 팝업
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.05.28
	 */
	@RequestMapping(value = "/stats/statsMgmt/getMcashListDtlPop.do")
	public String getMcashListDtlPop(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// M캐시 회원 수납 이력 상세 조회 팝업
			List<EgovMap> resultList = statsMgmtService.getMcashListDtlPop(statsMgmtVo, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010050", "M캐시 회원정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description M캐시 회원 수납 이력 합계 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.05.28
	 */
	@RequestMapping(value = "/stats/statsMgmt/getMcashDisTotal.do")
	public String getMcashDisTotal(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// M캐시 회원 수납 이력 합계 조회
			List<?> resultList = statsMgmtService.getMcashDisTotal(statsMgmtVo);
			resultMap = makeResultSingleRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010050", "M캐시 회원정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description M쇼핑할인 회원정보 상세이력 조회
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.08.07
	 */
	@RequestMapping(value = "/stats/statsMgmt/getMcashJoinCustMgmtHist.do")
	public String getMcashJoinCustMgmtHist(@ModelAttribute("statsMgmtVo") StatsMgmtVo statsMgmtVo, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("M쇼핑할인 회원정보 이력 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// M캐시 회원정보 조회
			List<EgovMap> resultList = statsMgmtService.getMcashJoinCustMgmtHist(statsMgmtVo, pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010050", "M쇼핑할인 회원정보")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description M쇼핑할인 회원정보 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2024.05.29
	 */
	@RequestMapping(value = "/stats/statsMgmt/getMcashJoinCustMgmtListExcel.json")
	@ResponseBody
	public String getMcashListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest pRequest,
									HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(statsMgmtVo.getSrchStrtDt())){
				statsMgmtVo.setSrchStrtDt(orgCommonService.getWantDay(-1));
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getMcashJoinCustMgmtListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "M쇼핑할인회원정보_";    // 파일명
			String strSheetname = "M쇼핑할인 회원정보";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"가입신청일자", "포털ID", "고객명", "생년월일", "계약번호",
									"서비스계약번호", "휴대폰번호", "회원상태", "M쇼핑할인 가입 최초 요금제","최초요금제 코드", 
									"현재요금제", "현재요금제 코드", "성별", "업무구분", "상태", 
									"대리점", "유심접점", "개통일자", "해지사유", "해지일자", 
									"이동전 통신사", "해지후이동사업자정보","광고/정보전송동의"};	// 23
			String[] strValue = {"strtDttm", "maskUserId", "custNm", "userSsn", "contractNum",
					"svcCntrNo","mobileNo", "status", "fstRateNm", "fstRateCd", "lstRateNm", "lstRateCd",
					"gender", "operTypeNm", "subStatusNm", "openAgntNm", "usimOrgNm",
					"openDt", "canRsn", "tmntDt", "moveCompanyNm", "cmpnNm", "agrYn"}; // 23

			// 엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000,
					5000, 5000, 5000, 10000, 10000,
					10000, 10000, 5000, 5000, 5000,
					10000, 10000, 5000, 5000, 5000,
					10000, 10000, 7000}; // 23

			int[] intLen = {0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0}; // 23

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 서식지 연동 현황 진입 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.02.04
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsAppFormSyncMgmt.do", method = {POST, GET})
	public String statsAppFormSyncMgmt(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-1));
			model.addAttribute("srchToday", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsAppFormSyncMgmt";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 서식지 연동 현황 조회
	 */
	@RequestMapping(value = "/stats/statsMgmt/getAppFormSyncList.json", method = {POST, GET})
	public String getAppFormSyncList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getAppFormSyncList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1010053", "서식지 연동 처리 실패 현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * 서식지 연동 현황 상세 조회
	 */
	@RequestMapping(value = "/stats/statsMgmt/getAppFormSyncDetailList.json", method = {POST, GET})
	public String getAppFormSyncDetailList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getAppFormSyncDetailList(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1010053", "서식지 연동 처리 실패 현황")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}


	/**
	 * 서식지 연동 현황 엑셀 다운로드
	 */
	@RequestMapping(value = "/stats/statsMgmt/getAppFormSyncListExcel.json")
	@ResponseBody
	public String getAppFormSyncListExcel(StatsMgmtVo statsMgmtVo, ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<?> resultList = statsMgmtService.getAppFormSyncListExcel(statsMgmtVo, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "K-NOTE서식지연동현황_";// 파일명
			String strSheetname = "K-NOTE서식지연동현황";// 시트명

			// 엑셀 첫줄
			String[] strHead = {"개통일자","계약번호","서식지 아이디","성공 이력", "최근 이력"};

			String[] strValue = {"lstComActvDateVal","contractNum","itemId","succYn", "succRecentYn"};

			// 엑셀 컬럼 사이즈
			int[] intWidth = {4000, 4000, 10000, 4000, 4000};

			int[] intLen = {0, 0, 0, 0, 0};

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}
			file = new File(strFileName);

			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());

			in = new FileInputStream(file);

			out = response.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// =======파일다운로드사유 로그
			// START==========================================================
			if (KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))) {
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = request.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "CUST"); // 업무명
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// =======파일다운로드사유 로그
			// END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}
		file.delete();
		// ----------------------------------------------------------------
		// return json
		// ----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 프로모션 자동 가입 검증 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.04.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsDisPrmtChk.do", method = {POST, GET})
	public String statsDisPrmtChk(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-7));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsDisPrmtChk";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 평생할인 프로모션 자동 가입 검증 목록 조회
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.04.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtChkList.json")
	public String getDisPrmtChkList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			/* 날짜 조회 검사 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date today = formatter.parse(orgCommonService.getToDay());
			Date srchDt = formatter.parse(searchVO.getSrchStrtDt());

			if(!srchDt.before(today)){
				throw new MvnoRunException(-1, "오늘보다 이전인 경우만 조회 가능합니다.");
			}

			//평생할인 프로모션 지연 관리 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtChkList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010054", "평생할인 자동가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 프로모션 검증 처리여부 수정
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.04.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/updatePrmtChkProc.json")
	public String updatePrmtChkProc(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
										HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 처리상태(수동) UPDATE
			searchVO.setUserId(loginInfo.getUserId());
			statsMgmtService.updatePrmtChkProc(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010054", "평생할인 자동가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 자동가입 검증 목록 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.04.01
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisPrmtChkListExcel.json")
	@ResponseBody
	public String getDisPrmtChkListExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
										  HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			//날짜 세팅
			if(KtisUtil.isEmpty(searchVO.getSrchStrtDt())){
				searchVO.setSrchStrtDt(orgCommonService.getWantDay(-7));
			}
			if(KtisUtil.isEmpty(searchVO.getSrchEndDt())) {
				searchVO.setSrchStrtDt(orgCommonService.getToDay());
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getDisPrmtChkListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인자동가입검증_";    // 파일명
			String strSheetname = "평생할인 자동가입 검증";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"시퀀스", "프로모션 적용일시", "검증유형", "계약번호", "프로모션ID",
								"프로모션명", "업무구분", "미처리코드", "미처리사유", "처리여부",
								"처리메모", "처리자", "처리일시"}; //13
			String[] strValue = {"regSeq", "effectiveDate", "chkType", "contractNum", "prmtId",
								 "prmtNm", "evntCd", "rsltCd", "rsltDesc", "procYn",
								 "procMemo", "procId", "procDate"}; //13

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 10000, 7000, 7000, 7000,
							  15000, 7000, 7000, 15000, 7000,
							  15000, 7000, 10000}; //13

			int[] intLen = {0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 0, 0}; //13

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", 0); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 부가서비스 조회
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.04.01
	 */
	@RequestMapping("/stats/statsMgmt/getDisPrmtAddList.json")
	public String getApplFormInfo(@ModelAttribute("searchVO") StatsMgmtVo searchVO,
								  Model model,
								  @RequestParam Map<String, Object> paramMap,
								  HttpServletRequest request,
								  HttpServletResponse response)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);

			// 본사 권한
			if (!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "success");

			resultMap.put("data", statsMgmtService.getDisPrmtAddList(searchVO, paramMap));

		} catch (Exception e) {

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}


	/**
	 * @Description 평생할인 프로모션 가입 검증 페이지
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsDisAddChk.do", method = {POST, GET})
	public String statsDisAddChk(HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// Login check
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getToDay());
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsDisAddChk";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * @Description 평생할인 프로모션 가입 검증 리스트 조회
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisAddChkList.json")
	public String getDisAddChkList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = statsMgmtService.getDisAddChkList(searchVO,pRequestParamMap);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010057", "평생할인 부가서비스 가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 프로모션 가입 검증 목록 엑셀 다운로드
	 * @Param : searchVO
	 * @Param : model
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisAddChkListExcel.json")
	@ResponseBody
	public String getDisAddChkListExcel(StatsMgmtVo searchVO, ModelMap model, HttpServletRequest pRequest,
										 HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		try {

			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 엑셀 데이터 조회
			List<EgovMap> resultList = statsMgmtService.getDisAddChkListExcel(searchVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "평생할인가입검증_";    // 파일명
			String strSheetname = "평생할인 가입 검증";                // 시트명

			// 엑셀 첫줄
			String[] strHead = {"검증일자", "계약번호", "청구번호", "개통일자", "업무구분", "구매유형",
					"적용일시", "대리점", "접점", "최종요금제코드", "최종요금제명",
					"요금제 월정액", "M전산 정책 요금제 할인금액", "KT시스템 요금제 할인 금액", "검증결과", "프로모션ID",
					"프로모션명", "M전산 정책 부가서비스", "M전산 정책 부가서비스명", "KT시스템 부가서비스", "KT시스템 부가서비스명",
					"재처리여부", "재처리결과", "재처리자", "재처리일시", "완료여부",
					"완료메모", "완료자", "완료일시"}; //29
			String[] strValue = {"chkDt", "contractNum", "ban", "openDt", "evntCd", "reqBuyTypeNm",
					"effectiveDate", "agentNm", "shopNm", "lstRateCd", "lstRateNm",
					"lstRateAmt", "disAmt", "ktDisAmt", "chkRslt", "prmtId",
					"prmtNm", "prmtSocs", "prmtSocsNm", "ktSocs", "ktSocsNm",
					"rtyYn", "rtyRslt", "rtyId", "rtyDate", "compYn",
					"compMemo", "compId", "compDate"}; //29

			// 엑셀 컬럼 사이즈
			int[] intWidth = {7000, 7000, 7000, 7000, 7000, 8000,
					15000, 15000, 15000, 7000, 20000,
					7000, 9000, 9000, 7000, 7000,
					20000, 20000, 20000, 20000, 20000,
					7000, 20000, 7000, 15000, 7000,
					20000, 7000, 15000}; //29

			int[] intLen = {0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0}; //29

			String strFileName = "";

			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth,
						strValue, pRequest, pResponse, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			file = new File(strFileName);

			pResponse.setContentType("applicaton/download");
			pResponse.setContentLength((int) file.length());

			in = new FileInputStream(file);
			out = pResponse.getOutputStream();

			int temp = -1;
			while ((temp = in.read()) != -1) {
				out.write(temp);
			}

			// 파일다운로드사유 로그 STRAT
			if (KtisUtil.isNotEmpty(pRequest.getParameter("DWNLD_RSN"))) {
				String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getHeader("REMOTE_ADDR");

				if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
					ipAddr = pRequest.getRemoteAddr();

				pRequestParamMap.put("FILE_NM", file.getName()); // 파일명
				pRequestParamMap.put("FILE_ROUT", file.getPath()); // 파일경로
				pRequestParamMap.put("DUTY_NM", "RCP"); // 업무명 (고객)
				pRequestParamMap.put("IP_INFO", ipAddr); // IP정보
				pRequestParamMap.put("FILE_SIZE", (int) file.length()); // 파일크기
				pRequestParamMap.put("menuId", pRequest.getParameter("menuId")); // 메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size()); // 자료건수

				// CMN_FILE_DNLD_MGMT_MST 테이블 로그 확인
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			// 파일다운로드사유 로그 END

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()));
			resultMap.put("msg", returnMsg);
			throw new MvnoErrorException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
		}

		file.delete();

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 가입중인 평생할인 부가서비스 가져오기
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/getDisAddList.json")
	public String getDisAddList(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 권한
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			List<EgovMap> resultList = statsMgmtService.getDisAddList(searchVO);
			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010054", "평생할인 자동가입 검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 부가서비스 검증 처리상태변경
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/updateDisAddChkComp.json")
	public String updateDisAddChkComp(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 처리상태(수동) UPDATE
			searchVO.setUserId(loginInfo.getUserId());
			statsMgmtService.updateDisAddChkComp(searchVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010057", "평생할인 부가서비스 가입검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * @Description 평생할인 부가서비스 재처리
	 * @Param : searchVO
	 * @Param : pRequest
	 * @Param : pResponse
	 * @Param : model
	 * @Param : pRequestParamMap
	 * @Return : String
	 * @CreateDate : 2025.07.08
	 */
	@RequestMapping(value = "/stats/statsMgmt/disAddRty.json")
	public String disAddRty(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
									  HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사, 대리점 권한
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) && (!"20".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD")) || !"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_LVL_CD"))) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 평생할인 부가서비스 재처리
			searchVO.setUserId(loginInfo.getUserId());
			resultMap = statsMgmtService.disAddRty(searchVO);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010057", "평생할인 부가서비스 가입검증")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * @Description : 유심변경현황
	 * @Param : searchVO
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/stats/statsMgmt/statsUsimChgHst.do", method = {POST, GET})
	public String statsUsimChgHst(@ModelAttribute("searchVO") RcpListVO searchVO, HttpServletRequest pRequest,
								   HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			model.addAttribute("srchStrtDt", orgCommonService.getWantDay(-1));
			model.addAttribute("srchEndDt", orgCommonService.getToDay());
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			model.addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			return "/rcp/statsMgmt/statsUsimChgHst";

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * @Description : 유심변경현황 조회
	 * @Param : searchVO
	 * @Return : String
	 */
	@RequestMapping(value = "/stats/statsMgmt/getUsimChgHst.json", method = {POST, GET})
	public String getUsimChgHst(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest,
								 HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<?> resultList = statsMgmtService.getUsimChgHst(searchVO, pRequestParamMap);

			resultMap = makeResultMultiRow(pRequestParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", "유심변경현황조회")) {
				throw new MvnoErrorException(e);
			}
		}
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	
	/**
	 * @Description : 유심변경현황 자료생성
	 * @Param : searchVO
	 * @Return : String
	 */
	@RequestMapping(value = "/stats/statsMgmt/getUsimChgHstExcel.json")
	public String getUsimChgHstExcel(@ModelAttribute("searchVO") StatsMgmtVo searchVO, HttpServletRequest pRequest, HttpServletResponse pResponse, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심변경현황 자료생성 START"));
		logger.info(generateLogMsg("Return Vo [StatsMgmtVo] = " + searchVO.toString()));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			// 본사 화면인 경우
			if (!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))) {
				throw new MvnoServiceException(
						messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}

			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",pRequest.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","유심변경현황");
			String searchVal = "|조회기간:" + searchVO.getSrchStrtDt() + "~" + searchVO.getSrchEndDt()
					+ "|검색구분:" + searchVO.getSearchGbn()
					+ "|검색값:" + searchVO.getSearchName();
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();

			BatchJobVO vo = new BatchJobVO();

			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00265");
			vo.setSessionUserId(loginInfo.getUserId());
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID

			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId()))
				vo.setExclDnldYn(vo.getExclDnldYn()+1);

			String ipAddr = pRequest.getHeader("HTTP_X_FORWARDED_FOR");
			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getHeader("REMOTE_ADDR");

			if (ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = pRequest.getRemoteAddr();

			vo.setExecParam("{\"srchStrtDt\":" + "\"" + searchVO.getSrchStrtDt() + "\""
					+ ",\"srchEndDt\":" + "\"" + searchVO.getSrchEndDt() + "\""
					+ ",\"searchGbn\":" + "\"" + searchVO.getSearchGbn() + "\""
					+ ",\"searchName\":" + "\"" + searchVO.getSearchName() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\""
					+ ",\"dwnldRsn\":" + "\"" + pRequest.getParameter("DWNLD_RSN") + "\""
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\""
					+ ",\"menuId\":" + "\"" + pRequest.getParameter("menuId") + "\""
					+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}"
			);
			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
			resultMap.put("msg", "다운로드성공");


		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", e.getMessage(), "MSP1010059", "유심변경현황")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

    @RequestMapping("/stats/statsMgmt/copyMcnRequest.json")
    public String copyMcnRequest(StatsMgmtVo statsMgmtVo, HttpServletRequest pRequest, HttpServletResponse pResponse,
                                 @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            //----------------------------------------------------------------
            // Login check
            //----------------------------------------------------------------
            LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
            loginInfo.putSessionToVo(statsMgmtVo);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            if (!"10".equals(statsMgmtVo.getSessionUserOrgnTypeCd())) {
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            statsMgmtVo.setSessionUserId(loginInfo.getUserId());
            int resultCnt = statsMgmtService.copyNameChg(statsMgmtVo);

            if (resultCnt == 0) {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
                resultMap.put("msg", "변경대상이 없습니다.");
            } else {
                resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()));
                resultMap.put("msg", "");
            }

        } catch (Exception e) {
            resultMap.clear();
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()));
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        //----------------------------------------------------------------
        // return json
        //----------------------------------------------------------------
        model.addAttribute("result", resultMap);

        return "jsonView";
    }
}