package com.ktis.msp.insr.insrMgmt.controller;

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
import com.ktis.msp.insr.insrMgmt.service.InsrOrderService;
import com.ktis.msp.insr.insrMgmt.vo.InsrOrderVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class InsrOrderController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private InsrOrderService insrOrderService;
	
	public InsrOrderController() {
		setLogPrefix("[InsrOrderController] ");
	}
	
	/**
	 * @Description : 보험신청대상목록(모빈스) 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrOrderView.do", method={POST, GET})
	public ModelAndView insrOrderView(HttpServletRequest pRequest,
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
			
			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1003");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보험신청대상목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrOrderList.json")
	public String getInsrOrderList(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험신청대상목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrOrderVO> resultList = insrOrderService.getInsrOrderList(insrOrderVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "보험신청대상목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 보험신청대상목록(모빈스) 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrOrderListByExcel.json")
	public String getInsrOrderListByExcel(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
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
			
			List<InsrOrderVO> resultList = insrOrderService.getInsrOrderListByExcel(insrOrderVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보험신청목록_";//파일명
			String strSheetname = "보험신청목록";//시트명
			String strFileName = "";
			
			//wooki(2022.10.26)
			//고객 > 현황자료 > 안심보험가입신청현황일 경우
			if("MSP1010045".equals(request.getParameter("menuId"))){
				strFilename = serverInfo  + "안심보험가입신청현황_";//파일명
				strSheetname = "안심보험가입신청현황";//시트명
				
				String [] strHeadE = {"계약번호", "고객명", "전화번호", "성별", "나이(만)", "개통일", "개통 대리점", "모집유형", "유심접점", "신청일시" ,"가입일시", "가입경로", "보험상품", "처리상태", "별도연락처"};

				String [] strValueE = {"contractNum", "subLinkName", "subscriberNo", "genderType", "custAge", "lstComActvDate", "openAgntNm", "onOffTypeNm", "usimOrgNm", "reqinday", "strtDttm", "chnNm", "insrProdNm", "ifTrgtNm", "smsPhone"};
				
				//엑셀 컬럼 사이즈
				int[] intWidthE = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 9000, 5000, 5000};
				
				int[] intLenE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				
				//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHeadE, intWidthE, strValueE, request, response, intLenE);

			//휴대폰안심보험 > 보험신청목록일 경우
			}else {				
				String [] strHead = { "전화번호", "고객명", "계약번호", "개통대리점", "보험상품명"
						,"신청일자" , "가입일자", "종료일자", "처리상태", "가입경로", "구매유형", "업무구분", "상품구분"
						, "단말모델명", "단말일련번호", "이미지등록여부", "서식지변경여부", "검증결과"
						, "1차검증처리자", "1차검증일자", "2차검증처리자", "2차검증일자", "메모"};

				String [] strValue = { "subscriberNo", "subLinkName", "contractNum", "openAgntNm", "insrProdNm"
									,"reqinday" , "strtDttm", "endDttm", "ifTrgtNm", "chnNm", "reqBuyTypeNm", "operTypeNm", "prodTypeNm"
									, "modelNm", "intmSrlNo", "imgChkYn", "scanMdyYn", "vrfyRsltNm"
									, "fstVrfyId", "fstVrfyDttm", "sndVrfyId", "sndVrfyDttm", "rmk"};
				
				//엑셀 컬럼 사이즈
				int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 8000};
				
				int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};		
				
				//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, resultList.iterator(), strHead, intWidth, strValue, request, response, intLen);
			}	
									
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
	* @Description : 유심단독개통 고객 단말 정보 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/regIntmInfoByUsimCust.json")
	public String regIntmInfoByUsimCust(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrOrderVO);
			
			insrOrderService.regIntmInfoByUsimCust(insrOrderVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001003", "보험신청대상목록(모빈스)")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 서식지 검증 등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/regVrfyRslt.json")
	public String regVrfyRslt(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrOrderVO);
			
			insrOrderService.regVrfyRslt(insrOrderVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001003", "보험신청대상목록(모빈스)")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보험신청대상목록(대리점) 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrAgntView.do", method={POST, GET})
	public ModelAndView insrAgntView(HttpServletRequest pRequest,
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
			

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1004");
			
			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보험신청대상목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrOrderAgntList.json")
	public String getInsrOrderAgntList(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험신청대상목록(대리점) 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrOrderVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(insrOrderVO.getSessionUserOrgnTypeCd()) && !insrOrderVO.getSessionUserOrgnId().equals(insrOrderVO.getAgntId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<InsrOrderVO> resultList = insrOrderService.getInsrOrderList(insrOrderVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "보험신청대상목록(대리점)")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보험가입자목록(KOS) 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrReadyView.do", method={POST, GET})
	public ModelAndView insrReadyView(HttpServletRequest pRequest,
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
			
			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1008");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보험가입자목록(KOS) 조회
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrReadyList.json")
	public String getInsrReadyList(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험가입자목록(KOS)목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<InsrOrderVO> resultList = insrOrderService.getInsrReadyList(insrOrderVO, pRequestParamMap);

			int totalCount = 0;

			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}

			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);

		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "보험가입자목록(KOS)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	

	/**
	* @Description : 보험가입자목록(KOS) 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrReadyListByExcel.json")
	public String getInsrReadyListByExcel(@ModelAttribute("insrOrderVO") InsrOrderVO insrOrderVO, HttpServletRequest request,
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

			List<InsrOrderVO> resultList = insrOrderService.getInsrReadyListByExcel(insrOrderVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보험가입자목록KOS_";//파일명
			String strSheetname = "보험가입자목록KOS";//시트명
			
			String [] strHead = { "전화번호","고객명","계약번호","계약상태","개통대리점"
								,"보험상품명","신청일자","가입일자","종료일자"
								};
			
			String [] strValue = { "subscriberNo","subLinkName","contractNum","subStatusNm","openAgntNm"
								,"insrProdNm","reqinday","strtDttm","endDttm"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 8000, 5000, 3000, 6000
							, 10000, 5000, 5000, 5000
							};
			int[] intLen = {0, 0, 0, 0, 0
							, 0, 0, 0, 0
							};

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
	
}
