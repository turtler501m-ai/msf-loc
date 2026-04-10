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
import com.ktis.msp.insr.insrMgmt.service.InsrMemberService;
import com.ktis.msp.insr.insrMgmt.vo.InsrMemberVO;
import com.ktis.msp.insr.insrMgmt.vo.InsrRegVO;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 안심보험 가입자조회
 *
 */
@Controller
public class InsrMemberController extends BaseController {
	
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
	private InsrMemberService insrMemberService;
	
	public InsrMemberController() {
		setLogPrefix("[InsrMemberController] ");
	}
	
	/**
	 * @Description : 보험가입자목록 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrMemberView.do", method={POST, GET})
	public ModelAndView insrMemberView(HttpServletRequest pRequest,
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
			

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1002");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보험가입자목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrMemberList.json")
	public String getInsrMemberList(@ModelAttribute("insrMemberVO") InsrMemberVO insrMemberVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험가입자목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrMemberVO> resultList = insrMemberService.getInsrMemberList(insrMemberVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9002001", "보험가입자목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 보험가입자목록 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrMemberListByExcel.json")
	public String getInsrMemberListByExcel(@ModelAttribute("insrMemberVO") InsrMemberVO insrMemberVO, HttpServletRequest request,
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
			
			List<InsrMemberVO> resultList = insrMemberService.getInsrMemberListByExcel(insrMemberVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보험가입자목록_";//파일명
			String strSheetname = "보험가입자목록";//시트명
			
			String [] strHead = { "전화번호", "고객명", "계약번호", "개통대리점ID", "개통대리점" 
								, "보험상품코드", "보험상품명", "신청일자", "가입일자", "종료일자", "보험상태"
								, "가입경로", "구매유형", "단말모델ID", "단말모델명", "단말일련번호"
								, "보험일련번호", "보상한도금액", "잔여보상금액", "보험약정기간", "해지사유"};
			
			String [] strValue = { "subscriberNo", "subLinkName", "contractNum", "openAgntCd", "openAgntNm"
								, "insrProdCd", "insrProdNm", "reqinday", "strtDttm", "endDttm", "insrStatNm"
								, "chnNm", "reqBuyTypeNm", "modelId", "modelNm", "intmSrlNo"
								, "insrMngmNo", "cmpnLmtAmt", "rmnCmpnAmt", "insrEnggCnt", "canRsltNm"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 1, 1, 0, 0};
			
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
	* @Description : 고객정보 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCntrInfo.json")
	public String getInsrCntrInfo(@ModelAttribute("insrRegVO") InsrRegVO insrRegVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험가입자목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			InsrRegVO rsltVo = insrMemberService.getInsrCntrInfo(insrRegVO, pRequestParamMap);
			
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
	* @Description : 단말보험등록(수기등록)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/regMspIntmInsrOrder.json")
	public String regMspIntmInsrOrder(@ModelAttribute("insrRegVO") InsrRegVO insrRegVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrRegVO);
			
			insrMemberService.regMspIntmInsrOrder(insrRegVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9002001", "단말보험등록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
}
