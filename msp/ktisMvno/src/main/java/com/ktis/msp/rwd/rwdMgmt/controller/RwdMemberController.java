package com.ktis.msp.rwd.rwdMgmt.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.cmn.fileup.service.FileUpload2Service;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.vo.FileInfoVO;
import com.ktis.msp.rcp.courtMgmt.vo.RegstCrCstmrVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rwd.rwdMgmt.service.RwdMemberService;
import com.ktis.msp.rwd.rwdMgmt.vo.RwdMemberVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : RwdMemberController
 * @Description : 보상서비스 신청관리
 * @
 * @ 수정일		수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2023.02.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2023.02.22
 */
@Controller
public class RwdMemberController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private FileUpload2Service  fileUp2Service;
	
	@Autowired
	private RwdMemberService rwdMemberService;
	
	public RwdMemberController() {
		setLogPrefix("[RwdMemberController] ");
	}
	
	
	/**
	 * @Description : 보상서비스 신청목록 초기 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value = "/rwd/rwd/rwdMemberView.do", method={POST, GET})
	public ModelAndView rwdMemberView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
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
			

			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1002");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	/**
	 * @Description : 보상서비스 가입 목록 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value = "/rwd/rwd/rwdMemberListView.do", method={POST, GET})
	public ModelAndView rwdMemberListView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
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
			

			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1003");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 보상서비스 도래 대상 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value = "/rwd/rwd/rwdMemComListView.do", method={POST, GET})
	public ModelAndView rwdMemComListView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
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
			

			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1004");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 보상서비스 권리 실행 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */	
	@RequestMapping(value = "/rwd/rwd/rwdMemCmpnListView.do", method={POST, GET})
	public ModelAndView rwdMemCmpnListView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1005");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 보상서비스 보상지급현황 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value = "/rwd/rwd/rwdMemPayListView.do", method={POST, GET})
	public ModelAndView rwdMemPayListView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1006");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 보상서비스 청구/수납 화면
	 * @Param  : pRequestParamMap
	 * @Return : ModelAndView
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value = "/rwd/rwd/rwdBillListView.do", method={POST, GET})
	public ModelAndView rwdBillListView(HttpServletRequest pRequest,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);

	        modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			
			modelAndView.getModelMap().addAttribute("maskingYn", maskingYn);
			modelAndView.setViewName("/rwd/rwdMgmt/msp_rwd_mgmt_1007");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * @Description : 보상서비스 접수 조회
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value="/rwd/rwdMgmt/getMemberJoinList.json")
	public String getMemberJoinList(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보상서비스 접수 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMemberVO> resultList = rwdMemberService.getMemberJoinList(rwdMemberVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100102", "보상서비스 접수관리")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상서비스 접수목록 엑셀 다운로드
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping("/rwd/rwdMgmt/getRwdMemberJoinListByExcel.json")
	public String getRwdMemberJoinListByExcel(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
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
			
			List<RwdMemberVO> resultList = rwdMemberService.getRwdMemberJoinListByExcel(rwdMemberVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보상서비스_접수목록_";//파일명
			String strSheetname = "보상서비스 접수목록";//시트명
			
			String [] strHead = { "신청일자", "전화번호", "고객명", "계약번호", "개통대리점ID"
								, "개통대리점", "보상서비스코드", "보상서비스명", "연동상태", "가입경로"
								, "구매유형", "구매가격", "단말모델ID", "단말모델명", "IMEI"
								, "IMEI2", "검증결과", "검증처리자", "검증일자", "메모"};
			
			String [] strValue = { "regstDt", "subscriberNo", "subLinkName", "contractNum", "openAgntCd"
								, "openAgntNm", "rwdProdCd", "rwdProdNm", "ifTrgtNm", "chnNm"
								, "reqBuyTypeNm", "buyPric", "modelId", "modelNm", "imei"
								, "imeiTwo", "vrfyRsltNm", "vrfyId", "vrfyDttm", "rmk"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 6500, 5000, 9000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 30000};
			int[] intLen = {0, 0, 0, 0, 0
						  , 0, 0, 0, 0, 0
						  , 0, 1, 0, 0, 0
						  , 0, 0, 0, 0, 0};
			
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
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
		if (file != null) file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 고객정보 조회
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value="/rwd/rwdMgmt/getRwdCntrInfo.json")
	public String getRwdCntrInfo(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("고객정보 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			RwdMemberVO rsltVo = rwdMemberService.getRwdCntrInfo(rwdMemberVO, pRequestParamMap);
			
			resultMap = makeResultSingleRow(rsltVo, rsltVo);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9002001", "고객정보조회")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상서비스 가입신청 등록
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping("/rwd/rwdMgmt/regMspRwdOrder.json")
	public String regMspIntmRwdOrder(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMemberVO);
			
			rwdMemberService.regMspRwdOrder(rwdMemberVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100102", "보상서비스 신청")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상서비스 가입신청 취소
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
    @RequestMapping(value="/rwd/rwdMgmt/cancelRwdOrder.json")
    public String cancelRwdOrder(HttpServletRequest request
            , HttpServletResponse response
            , @ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO
            , ModelMap modelMap
            , @RequestParam Map<String, Object> paramMap) {
    	
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMemberVO);

			rwdMemberVO.setUpdType("CAN");
			rwdMemberVO.setIfTrgtCd("C");
			
			rwdMemberService.updateRwdOrder(rwdMemberVO);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100102", "보상서비스 신청 취소")) {
				throw new MvnoErrorException(e);
			}
		}

        modelMap.addAttribute("result", resultMap);

        return "jsonView";
    }
	
	/**
	 * @Description : 보상서비스 가입신청 수정
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping("/rwd/rwdMgmt/updateRwdOrder.json")
	public String updateRwdOrder(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rwdMemberVO);
			
			rwdMemberVO.setUpdType("UPD");
			rwdMemberService.updateRwdOrder(rwdMemberVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100102", "보상서비스 신청")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상서비스 가입자 조회
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping(value="/rwd/rwdMgmt/getMemberList.json")
	public String getMemberList(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보상서비스 가입자 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMemberVO> resultList = rwdMemberService.getMemberList(rwdMemberVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100103", "보상서비스 가입자목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상서비스 가입자목록 엑셀 다운로드
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping("/rwd/rwdMgmt/getRwdMemberListByExcel.json")
	public String getRwdMemberListByExcel(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request,
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
			
			List<RwdMemberVO> resultList = rwdMemberService.getRwdMemberListByExcel(rwdMemberVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보상서비스_가입자목록_";//파일명
			String strSheetname = "보상서비스 가입자목록";//시트명
			
			String [] strHead = { "전화번호", "고객명", "계약번호", "개통대리점ID", "개통대리점" 
								, "보상서비스코드", "보상서비스명", "가입일자", "종료일자", "가입상태"
								, "가입경로", "구매유형", "구매가격", "단말모델ID", "단말모델명"
								, "IMEI", "IMEI2", "단말일련번호", "약정기간", "해지사유"};
			
			String [] strValue = { "subscriberNo", "subLinkName", "contractNum", "openAgntCd", "openAgntNm"
								, "rwdProdCd", "rwdProdNm", "strtDttm", "endDttm", "rwdStatCd"
								, "chnNm", "reqBuyTypeNm", "buyPric", "modelId", "modelNm"
								, "imei", "imeiTwo", "intmSrlNo", "rwdPrd", "canRsltNm"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 7000
							, 5000, 12000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0
						  , 0, 0, 0, 0, 0
						  , 0, 0, 1, 0, 0
						  , 0, 0, 0, 0, 0};
			
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
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
		if (file != null) file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

    /**
	 * @Description : 보상도래대상조회
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2023.05.11
	 */
	@RequestMapping(value="/rwd/rwdMgmt/getCmpnMemberList.json")
	public String getCmpnMemberList(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보상도래대상조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMemberVO> resultList = rwdMemberService.getCmpnMemberList(rwdMemberVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9100104", "보상도래대상조회")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보상도래대상조회 엑셀 다운로드
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2023.05.12
	 */
	@RequestMapping("/rwd/rwdMgmt/getCmpnMemberListByExcel.json")
	public String getCmpnMemberListByExcel(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<RwdMemberVO> resultList = rwdMemberService.getCmpnMemberListByExcel(rwdMemberVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보상도래대상조회_";//파일명
			String strSheetname = "보상도래대상조회";//시트명
			
			String [] strHead = { "전화번호", "고객명", "계약번호", "개통대리점", "보상서비스명"
								, "도래기간", "가입일자", "종료일자", "가입경로", "구매유형"
								, "구매가격", "단말모델ID", "단말모델명", "IMEI", "IMEI2"
								, "단말일련번호", "약정기간"};
			
			String [] strValue = { "subscriberNo", "subLinkName", "contractNum", "openAgntNm", "rwdProdNm"
								, "leftPrd", "strtDttm", "endDttm", "chnNm", "reqBuyTypeNm"
								, "buyPric", "modelId", "modelNm", "imei", "imeiTwo"
								, "intmSrlNo", "rwdPrd"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 9000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 1, 0, 0, 0, 0
						, 0, 0};
			
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
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
		if (file != null) file.delete();
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}

	/**
	 * 
	 * @Description : 보상서비스 권리실행 접수현황 조회
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	    
    @RequestMapping(value="/rwd/rwdMgmt/getRwdMemCmpnList.json")
    public String getRwdMemCmpnList(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 권리실행 접수현황 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = rwdMemberService.getRwdMemCmpnList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

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
	 * 
	 * @Description : 보상서비스 권리실행 접수현황 엑셀 다운로드
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	  
    @RequestMapping("/rwd/rwdMgmt/getRwdMemCmpnListByExcel.json")
    public String getRwdMemCmpnListByExcel(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam Map<String, Object> pRequestParamMap,
                                             ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 권리실행 접수현황 엑셀 다운 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {
            /* 로그인조직정보 및 권한체크 */
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<?> resultList = rwdMemberService.getRwdMemCmpnListByExcel(pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "보상서비스_권리실행_접수현황_";//파일명
            String strSheetname = "보상서비스 권리실행 접수현황";//시트명

            String [] strHead = { "접수일자", "전화번호", "고객명", "계약번호", "보상서비스명"
            					, "처리상태", "단말기구입가", "적용비율", "미래가격", "차감금액"
            					, "보장금액", "차감사유", "판정등급", "보상금지급예정일자", "등록일시"
            					, "수정일시"};

            String [] strValue = { "cmpnDt", "subscriberNo", "custNm", "contractNum", "rwdProdNm"
            					 , "cmpnStatCd", "buyPric", "rwdRt", "ftrPric", "ostPric"
            					 , "asrPric", "ostResn", "cnfmLv", "payPlanDttm", "regstDttm"
            					 , "rvisnDttm"};

            //엑셀 컬럼 사이즈
            int[] intWidth = { 4500, 4500, 3000, 4500, 8000
            				 , 3000, 4500, 3000, 4500, 4500
            				 , 4500, 8000, 3000, 7000, 5500
            				 , 5500};
            int[] intLen = { 0, 0, 0, 0, 0
                    	   , 0, 1, 1, 1, 1
                    	   , 1, 0, 0, 0, 0
                    	   , 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
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
	 * 
	 * @Description : 보상서비스 보상지급현황 조회
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	  
    @RequestMapping(value="/rwd/rwdMgmt/getRwdMemPayList.json")
    public String getRwdMemPayList(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 보상지급현황 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = rwdMemberService.getRwdMemPayList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

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
	 * 
	 * @Description : 보상서비스 보상지급현황 엑셀 다운로드
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	  
    @RequestMapping("/rwd/rwdMgmt/getRwdMemPayListByExcel.json")
    public String getRwdMemPayListByExcel(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam Map<String, Object> pRequestParamMap,
                                             ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 보상지급현황 엑셀 다운 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {
            /* 로그인조직정보 및 권한체크 */
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<?> resultList = rwdMemberService.getRwdMemPayListByExcel(pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "보상서비스_보상지급현황_";//파일명
            String strSheetname = "보상서비스 보상지급현황";//시트명

            String [] strHead = { "지급일자", "전화번호", "고객명", "계약번호" ,"보상서비스명"
            					, "보장금액", "실보상금액"};

            String [] strValue = { "payDttm", "subscriberNo", "custNm", "contractNum", "rwdProdNm"
            					 , "asrPric", "realCmpnAmt"};

            //엑셀 컬럼 사이즈
            int[] intWidth = { 4500, 4500, 3000, 4500, 8000,
            				   4500, 4500};
            
            int[] intLen = { 0, 0, 0, 0, 0,
                    		 1, 1};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
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
	 * 
	 * @Description : 보상서비스 청구수납 목록 조회
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	
    @RequestMapping(value="/rwd/rwdMgmt/getRwdBillPayList.json")
    public String getRwdBillPayList(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam Map<String, Object> pRequestParamMap,
                                      ModelMap model)
    {

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 청구수납 목록 조회 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);
            loginInfo.putSessionToVo(searchVO);

            // 본사 권한
            if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
                throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
            }

            List<?> resultList = rwdMemberService.getRwdBillPayList(pRequestParamMap);

            resultMap =  makeResultMultiRow(pRequestParamMap, resultList);

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
	 * 
	 * @Description : 보상서비스 청구수납 목록 엑셀 다운로드
	 * @Param  : pRequestParamMap
	 * @Return : resultList
	 * @Author : 김동혁
	 * @Create Date : 2023. 5. 12.
	 */	
    @RequestMapping("/rwd/rwdMgmt/getRwdBillPayListByExcel.json")
    public String getRwdBillPayListByExcel(@ModelAttribute("searchVO") RwdMemberVO searchVO,
                                             HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam Map<String, Object> pRequestParamMap,
                                             ModelMap model)
    {
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("보상서비스 청구수납 목록 엑셀 다운 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String returnMsg = null;
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;

        try {
            /* 로그인조직정보 및 권한체크 */
            LoginInfo loginInfo = new LoginInfo(request, response);
            loginInfo.putSessionToParameterMap(pRequestParamMap);

            List<?> resultList = rwdMemberService.getRwdBillPayListByExcel(pRequestParamMap);

            String serverInfo = propertiesService.getString("excelPath");
            String strFilename = serverInfo  + "보상서비스_청구수납목록_";//파일명
            String strSheetname = "보상서비스 청구수납목록";//시트명

            String [] strHead = { "청구월", "전화번호", "고객명", "계약번호", "가입경로"
            					, "보상서비스명", "구분", "청구/수납일자", "금액", "납부방법"};

            String [] strValue = { "billYm", "subscriberNo", "custNm", "contractNum", "chnNm"
            					 , "rwdProdNm", "pymnCd", "blpymDate", "pymnAmnt", "pymnMthdNm"};

            //엑셀 컬럼 사이즈
            int[] intWidth = { 3000, 5500, 4500, 4500, 8000
            				 , 12000, 4500, 5000, 4500, 4500};
            
            int[] intLen = { 0, 0, 0, 0, 0
            			   , 0, 0, 0, 1, 0};

			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList, strHead, intWidth, strValue, request, response, intLen);
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
				pRequestParamMap.put("DUTY_NM"	,"RWD");						//업무명
				pRequestParamMap.put("IP_INFO"	,ipAddr);						//IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());			//파일크기
				pRequestParamMap.put("menuId", request.getParameter("menuId"));	//메뉴ID
				pRequestParamMap.put("DATA_CNT", resultList.size());			//자료건수
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
	 * @Description : 보상서비스 파일 처리
	 * @Param  : RwdMemberVO
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@RequestMapping("/rwd/rwdMgmt/rwdFile.json")
	public String rwdFile(@ModelAttribute("rwdMemberVO") RwdMemberVO rwdMemberVO, ModelMap model) {
		
        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("첨부 파일 명 찾기 START."+ rwdMemberVO.toString()));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
        	List<?> resultList = rwdMemberService.getFile1(rwdMemberVO);

        	for (int i = 0; i < resultList.size(); i++) {
        		EgovMap tempMap = (EgovMap)resultList.get(i);

        		if(i==0)
        		{
        			resultMap.put("fileNm", tempMap.get("fileNm"));
        			resultMap.put("fileId", tempMap.get("fileId"));
        			resultMap.put("ext", tempMap.get("fileExt"));
        			resultMap.put("rwdSeq", tempMap.get("rwdSeq"));
        		}
			}
        	resultMap.put("fileTotalCnt", String.valueOf(resultList.size()));
            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
            resultMap.put("msg", "");

            logger.debug("result = " + resultMap);
        } catch (Exception e) {

            resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
            resultMap.put("msg", "");
            throw new MvnoErrorException(e);
        }

        model.addAttribute("result", resultMap);

        return "jsonView";
	}
	
    /**
     * @Description : 파일 다운로드 기능
     * @Param  : Model model
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2023. 2. 22.
     */
    @RequestMapping("/rwd/rwdMgmt/downFile.json")
    public String downFile( Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일다운로드 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();

        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = rwdMemberService.getFileNm(pReqParamMap.get("rwdSeq").toString());

            file = new File(strFileName);

            response.setContentType("applicaton/download");
            response.setContentLength((int) file.length());

            String encodingFileName = "";

    		int excelPathLen2 = Integer.parseInt(propertiesService.getString("rwdPathLen"));

            try {
              encodingFileName = URLEncoder.encode(strFileName.substring(excelPathLen2), "UTF-8").replace("+", "%20");
              logger.info("encodingFileName : " + encodingFileName);
            } catch (UnsupportedEncodingException uee) {
              encodingFileName = strFileName;
            }

            response.setHeader("Cache-Control", "");
            response.setHeader("Pragma", "");

            response.setContentType("Content-type:application/octet-stream;");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            in = new FileInputStream(file);

            out = response.getOutputStream();

            int temp = -1;
            while((temp = in.read()) != -1){
            	out.write(temp);
            }

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "다운로드성공");

 		} catch (Exception e) {

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }

    /**
     * @Description : 파일 삭제 기능
     * @Param  : Model
     * @Return : String
     * @Author : 장익준
     * @Create Date : 2023. 2. 22.
     */
    @RequestMapping("/rwd/rwdMgmt/deleteFile.json")
    public String deleteFile(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pReqParamMap){

        logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg("파일 삭제 START."));
        logger.info(generateLogMsg("================================================================="));

        Map<String, Object> resultMap = new HashMap<String, Object>();
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToParameterMap(pReqParamMap);

        File file = null;
        String returnMsg = null;

        try {

        	String strFileName = rwdMemberService.getFileNm(pReqParamMap.get("rwdSeq").toString());

            file = new File(strFileName);

            file.delete();

            int delcnt = rwdMemberService.deleteFile(pReqParamMap.get("rwdSeq").toString());
            logger.info(generateLogMsg("삭제건수 = " + delcnt));

            resultMap.put("deleteCnt", String.valueOf(delcnt));

 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
 			resultMap.put("msg", "파일삭제성공");

 		} catch (Exception e) {
 			resultMap.put("fileTotCnt", "0");
 			resultMap.put("deleteCnt", "0");
 			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					returnMsg, "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}  			
			throw new MvnoErrorException(e);
	    }
 		//----------------------------------------------------------------
 		// return json
 		//----------------------------------------------------------------
 		model.addAttribute("result", resultMap);
 		return "jsonView";
    }
    
	
	/**
	 * @Description :  파일업로드
	 * @Param  : model
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2023. 2. 22.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping("/rwd/rwdMgmt/fileUpLoad.do")
    public String fileUpUsingService(ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, @RequestParam Map<String, Object> pRequestParamMap) throws MvnoRunException{

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("파일 업로드 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInfoVO fileInfoVO = new FileInfoVO();

		HttpSession session = pRequest.getSession();
		
		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
        loginInfo.putSessionToVo(fileInfoVO);

        fileInfoVO.setRegstId(loginInfo.getUserId());    /** 사용자ID */
        fileInfoVO.setRvisnId(loginInfo.getUserId());

		String strUploadFileNm = "";      // 업로드한 파일 풀 경로
		String strSaveFileNm = "";        // 저장될 파일명(중복이 발생하면 (2)를 붙이는 작업까지 완료) 
		String strExt = "";               // 확장자
		String strSaveFullPath = "";      // 저장될 파일 경로
		String strSaveDir = propertiesService.getString("rwdPath");
		Integer filesize = 0;
		
		try{
			ServletFileUpload lServletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = lServletFileUpload.parseRequest(pRequest);

	  	    //--------------------------------
	        // upload base directory를 구함
	  	    // 존재하지 않으면 exception 발생
	        //--------------------------------
			if ( !new File(strSaveDir ).exists())
			{
				throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.NO_FILE_UPLOAD_BASE_DIR",  new Object[] { strSaveDir }  , Locale.getDefault()));
			}

			for (FileItem item : items) {

				//--------------------------------
		        // 파일 타입이 아니면 skip
		        //--------------------------------
				if (item.isFormField())
					continue;

				//--------------------------------
		        // 사전정의된 파일 사이즈 limit 이상이거나 사이즈가 0 이면 skip
		        //--------------------------------
				if ( item.getSize() == 0 ||  item.getSize()  >  Integer.parseInt(propertiesService.getString("fileUploadSizeLimit")) )
					continue;

				try
				{
					//----------------------------------------------------------------
			    	// 파일 이름관련 변수 선언 및 초기화
			    	//----------------------------------------------------------------
					strUploadFileNm = "";
					strSaveFileNm = "";
					strExt = "";
					strSaveFullPath = "";
					filesize = 0;
					
					//--------------------------------
			        // 모듈별 directory가 존재하지 않으면 생성
			        //--------------------------------
					File lDir = new File(strSaveDir );
					if ( !lDir.exists()) {
						lDir.mkdirs();
					}
					fileInfoVO.setAttRout(strSaveDir);

					//----------------------------------------------------------------
			    	// upload 이름 구함
			    	//----------------------------------------------------------------
					String fieldname = item.getFieldName();
					if ( fieldname.equals("file")) {
						//----------------------------------------------------------------
				    	// 파일 이름에 붙일 4자리 시퀀스 조회
				    	//----------------------------------------------------------------
						String fileNmSeq = rwdMemberService.getFileNmSeq();

						//----------------------------------------------------------------
				    	// 파일 이름 구함
				    	//----------------------------------------------------------------
						strUploadFileNm = FilenameUtils.getName(item.getName());
						
						int pos = strUploadFileNm.lastIndexOf(".");
						strExt = strUploadFileNm.substring( pos + 1); //확장자
						strSaveFileNm = fileNmSeq + "." + strExt; //파일명구하기 ex)202303140001.jpg
						strSaveFileNm = fileUp2Service.getAlternativeFileName(strSaveDir, strSaveFileNm);
						strSaveFullPath = strSaveDir + strSaveFileNm;

						logger.info(generateLogMsg("strUploadFileNm : " + strUploadFileNm));
				    	logger.info(generateLogMsg("strSaveFileNm : " + strSaveFileNm));
				    	logger.info(generateLogMsg("strExt : " + strExt));
				    	logger.info(generateLogMsg("strSaveFullPath : " + strSaveFullPath));

				    	session.setAttribute("realFilePath", strSaveDir);  //파일경로
				    	session.setAttribute("realFileNm", strSaveFileNm); //파일명
				    	session.setAttribute("ext", strExt);               //파일확장자
						//----------------------------------------------------------------
				    	// 파일 write
				    	//----------------------------------------------------------------
				    	InputStream filecontent = item.getInputStream();
						File f=new File(strSaveFullPath);

						OutputStream fout=new FileOutputStream(f);
						byte buf[]=new byte[1024];
						int len;
						try {
							while((len=filecontent.read(buf))>0) {
								fout.write(buf,0,len);
								filesize+=len;
							}
						} catch (Exception e1) {
							throw new MvnoErrorException(e1);
						} finally {
					        if (fout != null) {
					        	try {
					        		fout.close();
					        	} catch (Exception e) {
					        		throw new MvnoErrorException(e);
					        	}
					        }
							filecontent.close();
						}
					}

				}catch (Exception e) {
					throw new MvnoErrorException(e);
				}

		 		resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
    	} catch (Exception e) {
    		resultMap.put("state", false);
    		resultMap.put("name", strUploadFileNm.replace("'","\\'"));
    		resultMap.put("size",  111);

    		model.addAttribute("result", resultMap);
    		
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "", ""))
			{
            //v2018.11 PMD 적용 소스 수정
            throw new MvnoErrorException(e);
			}    		
    	    return "jsonViewArray";
    	}

		//파일 테이블에 데이터 저장하고.
		fileInfoVO.setFileId(generationUUID().replaceAll("-",""));
		fileInfoVO.setFileNm(strSaveFileNm);
		fileInfoVO.setFileSeq("9999999999");//초기값 조직ID
		fileInfoVO.setAttSctn("CRD"); //신용등급
				
		session.setAttribute("fileId", fileInfoVO.getFileId()); //파일ID
		
		resultMap.put("state", true);
		resultMap.put("name", fileInfoVO.getFileId());
		resultMap.put("size", "" + 111);

		model.addAttribute("result", resultMap);
	    return "jsonViewArray";
    }
	/**
	 * @Description : UUID 생성
	 * @Param  :
	 * @Return : String
	 * @Author : FileMgmtController 에서 가져 옴.
	 * @Create Date : 2016. 4. 06.
	 */
	public static String generationUUID(){
		  return UUID.randomUUID().toString();
	}
	
	/**
	 * 
	 * @Description : 선택한 고객 정보를 대상정보로 등록
	 * @Param  : 
	 * @Return : String
	 * @Author : 장익준
	 * @Create Date : 2016. 7. 11.
	 */
	@RequestMapping("/rwd/rwdMgmt/insertImageFile.json")
	public String insertImageFile(HttpServletRequest request, HttpServletResponse response, RwdMemberVO rwdMemberVO, Model model, Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" insertImageFile 등록 "));
		logger.info(generateLogMsg("================================================================="));
		
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		
    	String realFilePath = "";
    	String realFileNm = "";
    	String ext = "";
    	String fileId = "";
    	String rwdSeq = "";
    	
    	if (session.getAttribute("realFilePath") != null) {
    		realFilePath = (String) session.getAttribute("realFilePath"); //파일경로
    		rwdMemberVO.setRealFilePath(realFilePath);
    	}
    	
    	if (session.getAttribute("realFileNm") != null) {
    		realFileNm = (String) session.getAttribute("realFileNm"); //파일명
    		rwdMemberVO.setRealFileNm(realFileNm);
    	}
    	
    	if (session.getAttribute("ext") != null) {
    		ext = (String) session.getAttribute("ext"); //파일확장자
    		if(ext != null) {
    			ext = ext.toUpperCase();
    		}
    		rwdMemberVO.setExt(ext);
    	}
    	
    	if (session.getAttribute("fileId") != null) {
    		fileId = (String) session.getAttribute("fileId"); //파일ID
    		rwdMemberVO.setFileId(fileId);
    	}
    	
    	if (session.getAttribute("rwdSeq") != null) {
    		rwdSeq = (String) session.getAttribute("rwdSeq"); //키값
    		rwdMemberVO.setRwdSeq(rwdSeq);
    	}
    	
		logger.info("############################## ");
		logger.info("파일경로 === " + realFilePath);
		logger.info("파일명 === " + realFileNm);
		logger.info("확장자 === " + ext);
		logger.info("파일ID === " + fileId);
		logger.info("rwdSeq === " + rwdSeq);
		
		if ( realFileNm != null)
		logger.info("realFileNm.length() ===" + realFileNm.length());
		
		//----------------------------------------------------------------
        //  Login check
        //----------------------------------------------------------------
        LoginInfo loginInfo = new LoginInfo(request, response);
        loginInfo.putSessionToVo(rwdMemberVO);
        
		try {
			if (realFileNm != null && realFileNm.length() > 0){
				
				String[] realFileNmTemp = realFileNm.split("\\.");
				if(realFileNmTemp.length > 0) {
					rwdMemberVO.setRealFileNm(realFileNmTemp[0]);
				}
				rwdMemberVO.setRealFilePath(realFilePath + realFileNm);
				
				int returnCnt = rwdMemberService.insertImageFile(rwdMemberVO);
				logger.debug("returnCnt==" + returnCnt);
				rwdMemberVO.setRealFileNm(realFileNm);
				rwdMemberVO.setRealFilePath(realFilePath);
			}
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
		    resultMap.clear();  // 필요시 삭제하고 프로그램별로 상황에 맞게 처리
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1020200", "변경동의서관리"))
			{
                throw new MvnoErrorException(e);
			}            
		} finally {
			//session 정보 초기화
			session.setAttribute("realFilePath", ""); //파일경로
	    	session.setAttribute("realFileNm", ""); //파일명
	    	session.setAttribute("ext", ""); //파일확장자
	    	session.setAttribute("fileId", ""); //파일아이디
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------	
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}
