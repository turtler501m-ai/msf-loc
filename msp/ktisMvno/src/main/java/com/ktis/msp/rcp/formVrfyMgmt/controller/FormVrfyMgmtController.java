package com.ktis.msp.rcp.formVrfyMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.formVrfyMgmt.service.FormVrfyMgmtService;
import com.ktis.msp.rcp.formVrfyMgmt.vo.FormVrfyMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class FormVrfyMgmtController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private FormVrfyMgmtService formVrfyMgmtService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public FormVrfyMgmtController() {
		setLogPrefix("[FormVrfyMgmtController] ");
	}
	
	/**
	 * @Description : 서식지검증관리 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/rcp/formVrfyMgmt/formVrfyMgmtView.do", method={POST, GET})
	public ModelAndView formVrfyMgmtView(HttpServletRequest pRequest,
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

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");
			String serverUrl = (String) resultMap.get("SERVER_URL");
			
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("agentVersion", agentVersion);
			modelAndView.getModelMap().addAttribute("serverUrl", serverUrl);
			modelAndView.getModelMap().addAttribute("scanDownloadUrl", scanDownloadUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			
			modelAndView.setViewName("/rcp/formVrfyMgmt/msp_form_vrfy_1001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 서식지검증관리 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/formVrfyMgmt/getFormVrfyList.json")
	public String getFormVrfyList(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("서식지검증관리 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			formVrfyMgmtVO.setVrfyUsrId(loginInfo.getUserId());
			
			List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyList(formVrfyMgmtVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 서식지검증관리 목록 조회 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/formVrfyMgmt/getFormVrfyListByExcel.json")
	public String getFormVrfyListByExcel(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			formVrfyMgmtVO.setVrfyUsrId(loginInfo.getUserId());
			
			List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyListByExcel(formVrfyMgmtVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "서식지검증목록_";//파일명
			String strSheetname = "서식지검증목록";//시트명
			
			String [] strHead = {"상품구분", "계약번호", "고객명", "생년월일", "나이(만)",
		             "휴대폰번호", "요금제", "할인유형", "판매정책명", "약정개월수",
		             "할부개월수", "단말모델", "단말일련번호", "상태", "해지사유",
		             "모집경로", "유입경로", "추천인구분", "추천인정보", "녹취여부",
		             "대리점", "판매점명", "판매자명", "기변유형", "기변일자",
		             "기변모델명", "기변대리점", "프로모션명", "개통유형", "할부원금", 
		             "1차검증결과", "1차비고", "1차검증자", "1차검증일자", "2차검증결과", 
		             "2차비고", "2차검증자", "2차검증일자", "광고수신동의여부", "선택형상해보험서식지등록여부", 
		             "선택형상해보험적격여부"};



			String [] strValue = {"prodTypeNm", "contractNum", "cstmrName", "birth", "age",
					              "subscriberNo", "fstRateNm", "sprtTpNm", "salePlcyNm", "enggMnthCnt",
					              "instMnthCnt", "fstModelNm", "fstModelSrlNum", "subStatusNm", "canRsn",
					              "onOffTypeNm", "openMarketReferer", "recommendFlagNm", "recommendInfo", "recYn",
					              "agentNm", "shopNm", "shopUsrNm", "dvcOperTypeNm", "dvcChgDt",
					              "dvcModelNm", "dvcAgntNm", "promotionNm", "operTypeNm", "modelInstallment",
					              "fstRsltNm", "fstRemark", "fstUsrNm", "fstVrfyDttm", "sndRsltNm", 
					              "sndRemark", "sndUsrNm", "sndVrfyDttm", "clausePriAdFlagNm", "insrFormFlagNm", 
					              "insrCfmtFlagNm"};
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 5000, 10000, 5000, 15000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 10000, 10000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000};
			
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 1
						, 1, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 1
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"	,file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"	,"INSR");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);							//자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
		
	/**
	 * @Description : 서식지검증관리(대리점) 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/rcp/formVrfyMgmt/formVrfyAgntView.do", method={POST, GET})
	public ModelAndView formVrfyAgntView(HttpServletRequest pRequest,
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

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			
			String scnUrl =  propertiesService.getString("mcp.url");
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			
			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");
			String serverUrl = (String) resultMap.get("SERVER_URL");
			
			modelAndView.getModelMap().addAttribute("scnUrl", scnUrl);
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);
			modelAndView.getModelMap().addAttribute("agentVersion", agentVersion);
			modelAndView.getModelMap().addAttribute("serverUrl", serverUrl);
			modelAndView.getModelMap().addAttribute("scanDownloadUrl", scanDownloadUrl);
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			
			modelAndView.setViewName("/rcp/formVrfyMgmt/msp_form_vrfy_1002");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 서식지검증관리(대리점) 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/formVrfyMgmt/getFormVrfyAgntList.json")
	public String getFormVrfyAgntList(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("서식지검증관리(대리점) 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyAgntList(formVrfyMgmtVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 서식지검증관리(대리점) 목록 조회 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/formVrfyMgmt/getFormVrfyAgntListByExcel.json")
	public String getFormVrfyAgntListByExcel(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyAgntListByExcel(formVrfyMgmtVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "서식지검증목록_";//파일명
			String strSheetname = "서식지검증목록";//시트명
			
			String [] strHead = {"상품구분", "계약번호", "검증결과", "고객명", "생년월일", 
								 "나이(만)", "휴대폰번호", "요금제", "할인유형", "판매정책명", 
								 "약정개월수", "할부개월수", "단말모델", "단말일련번호", "상태", 
								 "해지사유", "모집경로", "유입경로", "추천인구분", "추천인정보", 
								 "녹취여부", "대리점", "판매점명", "판매자명", "기변유형", 
								 "기변일자", "기변모델명", "기변대리점", "프로모션명", "개통유형", 
								 "할부원금"};
			
			
			
			String [] strValue = {"prodTypeNm", "contractNum", "vrfyStatNm", "cstmrName", "birth", 
								  "age", "subscriberNo", "fstRateNm", "sprtTpNm", "salePlcyNm", 
								  "enggMnthCnt", "instMnthCnt", "fstModelNm", "fstModelSrlNum", "subStatusNm", 
								  "canRsn", "onOffTypeNm", "openMarketReferer", "recommendFlagNm", "recommendInfo", 
								  "recYn", "agentNm", "shopNm", "shopUsrNm", "dvcOperTypeNm", 
								  "dvcChgDt", "dvcModelNm", "dvcAgntNm", "promotionNm", "operTypeNm", 
								  "modelInstallment" };
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 10000, 5000, 15000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 10000, 10000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 1, 1, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 1};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			
			file = new File(strFileName);
			
			response.setContentType("applicaton/download");
			response.setContentLength((int) file.length());
			
			in = new FileInputStream(file);
			
			out = response.getOutputStream();
			
			int temp = -1;
			while((temp = in.read()) != -1){
				out.write(temp);
			}
			
			//=======파일다운로드사유 로그 START==========================================================
			if(KtisUtil.isNotEmpty(request.getParameter("DWNLD_RSN"))){
				String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
				
				pRequestParamMap.put("FILE_NM"	,file.getName());				//파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());				//파일경로
				pRequestParamMap.put("DUTY_NM"	,"INSR");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", 0);							//자료건수
				pRequestParamMap.put("SESSION_USER_ID", loginInfo.getUserId());	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", returnMsg);
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
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 서식지 검증결과 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/formVrfyMgmt/regFormVrfyRslt.json")
	public String regFormVrfyRslt(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(formVrfyMgmtVO);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if(formVrfyMgmtService.getFormVrfyRstAsinYn(formVrfyMgmtVO).equals("Y")){
				formVrfyMgmtService.updFormVrfyInfo(formVrfyMgmtVO);
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg", "할당되지 않은 개통정보입니다. 할당 후 검증 가능합니다.");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 서식지 조치결과 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/formVrfyMgmt/regFormVrfyAgentRslt.json")
	public String regFormVrfyAgentRslt(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(formVrfyMgmtVO);
			
			formVrfyMgmtService.updFormVrfyAgentInfo(formVrfyMgmtVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 서식지검증할당관리 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/rcp/formVrfyMgmt/formVrfyAsinView.do", method={POST, GET})
	public ModelAndView formVrfyAsinView(HttpServletRequest pRequest,
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

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			modelAndView.setViewName("/rcp/formVrfyMgmt/msp_form_vrfy_1003");
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
		return modelAndView;
	}
	
	/**
	 * @Description : 서식지검증사용자 조회
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/rcp/formVrfyMgmt/getFormVrfyUsrList.json")
	public String  usrGrpAsgnList(
		ModelMap model,
		HttpServletRequest pRequest,
		HttpServletResponse pResponse,
		@RequestParam Map<String, Object> pRequestParamMap){
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
		
			List<?> resultList =  formVrfyMgmtService.getFormVrfyUsrList(pRequestParamMap);
			
			resultMap =  makeResultMultiRow(pRequestParamMap, resultList);
		}catch ( Exception e){
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}finally{
			dummyFinally();
		}
	
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 할당대상 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/formVrfyMgmt/getFormVrfyAsinList.json")
	public String getFormVrfyAsinList(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("할당대상 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			formVrfyMgmtVO.setVrfyUsrId(request.getParameter("vrfyUsrId"));
			List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyAsinList(formVrfyMgmtVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	* @Description : 서식지 검증 대상 할당 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/formVrfyMgmt/regFormVrfyAsin.json")
	public String regFormVrfyAsin(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(formVrfyMgmtVO);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
						
			if(formVrfyMgmtVO.getContractNum() != null && !formVrfyMgmtVO.getContractNum().equals("")){
				if (request.getParameter("asinYn") != null && (request.getParameter("asinYn").equals("1") || request.getParameter("asinYn").equals("true"))) {
					List<FormVrfyMgmtVO> resultList = formVrfyMgmtService.getFormVrfyRstList(formVrfyMgmtVO);
					if(resultList.size() > 0){
						formVrfyMgmtService.updFormVrfyAsinInfoY(formVrfyMgmtVO);
					}else{
						formVrfyMgmtService.regFormVrfyAsinInfo(formVrfyMgmtVO);
					}
				} else {
					formVrfyMgmtService.updFormVrfyAsinInfoN(formVrfyMgmtVO);
				}
				
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg", "계약번호가 입력되지 않았습니다.");
			}
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/rcp/formVrfyMgmt/getFormVrfyInfo.json")
	public String getFormVrfyInfo(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			formVrfyMgmtVO.setVrfyUsrId(loginInfo.getUserId());
			
			FormVrfyMgmtVO resultData = formVrfyMgmtService.getFormVrfyInfo(formVrfyMgmtVO, pRequestParamMap);
			
			resultMap.put("data", resultData);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
		}

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/rcp/formVrfyMgmt/getFormVrfyAgntInfo.json")
	public String getFormVrfyAgntInfo(@ModelAttribute("formVrfyMgmtVO") FormVrfyMgmtVO formVrfyMgmtVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			formVrfyMgmtVO.setVrfyUsrId(loginInfo.getUserId());
			
			FormVrfyMgmtVO resultData = formVrfyMgmtService.getFormVrfyAgntInfo(formVrfyMgmtVO, pRequestParamMap);
			
			resultMap.put("data", resultData);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) throw new MvnoErrorException(e);
		}

		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
}
