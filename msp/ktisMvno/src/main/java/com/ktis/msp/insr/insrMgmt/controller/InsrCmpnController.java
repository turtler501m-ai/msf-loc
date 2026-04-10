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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.insr.insrMgmt.service.InsrCmpnService;
import com.ktis.msp.insr.insrMgmt.vo.InsrCmpnVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class InsrCmpnController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private InsrCmpnService insrCmpnService;
	
	@Autowired
	private FileDownService fileDownService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public InsrCmpnController() {
		setLogPrefix("[InsrCmpnController] ");
	}
	
	/**
	* @Description : 보험보상목록By보험일련번호 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCmpnListByInsrMngmNo.json")
	public String getInsrCmpnListByInsrMngmNo(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험보상목록By보험일련번호 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCmpnListByInsrMngmNo(insrCmpnVO);
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, resultList.size());
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9001002", "보험가입자목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 단말보상목록 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrCmpnView.do", method={POST, GET})
	public ModelAndView insrCmpnView(HttpServletRequest pRequest,
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
			

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1005");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 보험보상목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCmpnList.json")
	public String getInsrCmpnList(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험보상목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCmpnList(insrCmpnVO, pRequestParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "보험보상목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 보험보상목록 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrCmpnListByExcel.json")
	public String getInsrCmpnListByExcel(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
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
			
			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCmpnListByExcel(insrCmpnVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보험보상목록_";//파일명
			String strSheetname = "보험보상목록";//시트명
			
			String [] strHead = { "전화번호","고객명","계약번호","보험상품명","보상유형"
								,"단말모델ID","단말모델명","단말일련번호","보험관리번호","사고번호"
								,"사고등록일자","사고일자","사고유형","보상승인일자","보상한도금액"
								,"잔여보상금액","보상누적금액","잔여보상한도금액","영수증금액","실보상금액"
								,"은행명","계좌번호","예금주","보상시점출고가","고객부담금"
								,"초과금","유심비용","보상단말모델(대표)","보상단말모델(색상)","연락처"
								,"결제방법","은행","가상계좌","결제금액","결제상태"
								,"결제일시","예약번호","단말기일련번호","택배사","송장번호"
								,"검증결과","처리자","처리일시"
								};
			
			String [] strValue = { "subscriberNo","subLinkName","contractNum","insrProdNm","cmpnTypeNm"
								,"modelId","modelNm","intmSrlNo","insrMngmNo","acdntNo"
								,"acdntRegDt","acdntDt","acdntTp","cmpnCnfmDt","cmpnLmtAmt"
								,"rmnCmpnAmt","cmpnAcmlAmt","rmnCmpnLmtAmt","rcptAmt","realCmpnAmt"
								,"bankNm","acntNo","dpstNm","outUnitPric","custBrdnAmt"
								,"overAmt","usimAmt","cmpnModelNm","cmpnModelColor","cmpnCtn"
								,"payTypeNm","vacBankNm","vacId","payAmt","payYn"
								,"payDttm","resNo","reqPhoneSn","tbNm","dlvryNo"
								,"vrfyRsltNm","rvisnId","rvisnDttm"
								};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 8000, 8000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000
							};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 0, 1
						, 1, 1, 1, 1, 1
						, 0, 0, 0, 1, 1
						, 1, 1, 0, 0, 0
						, 0, 0, 0, 1, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0
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
	
	/**
	* @Description : 단말보험 전손 결제 관리 정보 변경
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/updIntmInsrCmpnDtl.json")
	public String updIntmInsrCmpnDtl(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(insrCmpnVO);
			
			String vacId = insrCmpnService.updIntmInsrCmpnDtl(insrCmpnVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("vacId", vacId);
			
		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP9005001", "보험보상목록")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	 * @Description : 보험보상목록(모빈스) 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrCmpnMbsView.do", method={POST, GET})
	public ModelAndView insrCmpnMbsView(HttpServletRequest pRequest,
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

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1006");
			
			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	* @Description : 보험보상목록(모빈스) 조회
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCmpnMbsList.json")
	public String getInsrCmpnMbsList(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("보험보상목록(모빈스) 조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCmpnMbsList(insrCmpnVO, pRequestParamMap);

			int totalCount = 0;

			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}

			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);

		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "보험보상목록(모빈스)")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}

	/**
	* @Description : 보험보상목록(모빈스) 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrCmpnMbsListByExcel.json")
	public String getInsrCmpnMbsListByExcel(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
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

			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCmpnMbsListByExcel(insrCmpnVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "보험보상목록(모빈스)_";//파일명
			String strSheetname = "보험보상목록(모빈스)";//시트명

			String [] strHead = { "전화번호","고객명","계약번호","보험상품명","보상유형"
								,"단말모델ID","단말모델명","단말일련번호","보험관리번호","사고번호"
								,"사고등록일자","사고일자","보상승인일자","보상한도금액","잔여보상금액"
								,"보상누적금액","잔여보상한도금액","영수증금액","실보상금액","고객부담금"
								,"초과금","유심비용","보상단말모델","단말일련번호","택배사"
								,"송장번호","결제여부","결제일시","처리자","처리일시"
								};

			String [] strValue = { "subscriberNo","subLinkName","contractNum","insrProdNm","cmpnTypeNm"
								,"modelId","modelNm","intmSrlNo","insrMngmNo","acdntNo"
								,"acdntRegDt","acdntDt","cmpnCnfmDt","cmpnLmtAmt","rmnCmpnAmt"
								,"cmpnAcmlAmt","rmnCmpnLmtAmt","rcptAmt","realCmpnAmt","custBrdnAmt"
								,"overAmt","usimAmt","cmpnModelNm","reqPhoneSn","tbNm"
								,"dlvryNo","payYn","payDttm","rvisnId","rvisnDttm"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							, 5000, 5000, 8000, 5000, 5000
							, 5000, 5000, 5000, 5000, 5000
							};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0, 0, 1, 1
						, 1, 1, 1, 1, 1
						, 1, 1, 0, 0, 0
						, 0, 0, 0, 0, 0
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
	
	/**
	 * @Description : 해지미처리목록 화면
	 * @Param :
	 * @Return : ModelAndView
	 */
	@RequestMapping(value = "/insr/insrMgmt/insrCanView.do", method={POST, GET})
	public ModelAndView insrCanView(HttpServletRequest pRequest,
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

			modelAndView.setViewName("/insr/insrMgmt/msp_insr_mgmt_1007");
			
			return modelAndView;

		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}
	
	/**
	* @Description : 해지미처리목록  조회
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/insr/insrMgmt/getInsrCanList.json")
	public String getInsrCanList(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {

		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("해지미처리목록  조회 START."));
		logger.info(generateLogMsg("================================================================="));

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);

			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCanList(insrCmpnVO, pRequestParamMap);

			int totalCount = 0;

			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}

			resultMap = makeResultMultiRowNotEgovMap(pRequestParamMap, resultList, totalCount);

		} catch (Exception e) {
			resultMap.clear();
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "", "해지미처리목록")) {
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	

	/**
	* @Description : 해지미처리목록 엑셀 다운로드
	* @Param  :
	* @Return : ModelAndView
	*/
	@RequestMapping("/insr/insrMgmt/getInsrCanListByExcel.json")
	public String getInsrCanListByExcel(@ModelAttribute("insrCmpnVO") InsrCmpnVO insrCmpnVO, HttpServletRequest request,
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

			List<InsrCmpnVO> resultList = insrCmpnService.getInsrCanListByExcel(insrCmpnVO, pRequestParamMap);

			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "해지미처리목록_";//파일명
			String strSheetname = "해지미처리목록";//시트명

			String [] strHead = { "전화번호","고객명","계약번호","계약상태","보상품코드"
								,"보험상품명","가입일자","해지요청일자","해지요청사유","해지요청결과"
								,"메모"
								};

			String [] strValue = { "subscriberNo","subLinkName","contractNum","subStatusNm","insrProdCd"
								,"insrProdNm","strtDttm","procDt","canRsltCd","rsltMsg"
								,"rmk"
								};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 4000, 5000, 5000, 5000
							, 9000, 8000, 8000, 5000, 10000
							, 20000
							};
			int[] intLen = {0, 0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0
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
