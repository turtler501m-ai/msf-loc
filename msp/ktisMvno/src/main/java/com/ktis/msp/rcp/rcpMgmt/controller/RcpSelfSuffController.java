package com.ktis.msp.rcp.rcpMgmt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpSelfSuffService;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSuffVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RcpSelfSuffController extends BaseController  {

	@Autowired
	private OrgCommonService orgCommonService;

	/** rcpMgmtService */
	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private RcpSelfSuffService rcpSelfSuffService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private LoginService loginService;

	/** Constructor */
	public RcpSelfSuffController() {
		setLogPrefix("[RcpSelfSuffController] ");
	}

	
	/**
	 * 신청정보(자급제)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/rcpMgmtSelfSuff.do")
	public ModelAndView getRcpListSelfSuff( @ModelAttribute("searchVO") RcpSelfSuffVO searchVO,
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
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
			model.addAttribute("endDt",orgCommonService.getToDay());
			model.addAttribute("maskingYn", maskingYn);

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtSelfSuff");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 신청정보(자급제) 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtListSelfSuff.json")
	public String getRcpMgmtListSelfSuff(@ModelAttribute("searchVO") RcpSelfSuffVO searchVO,
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
			
			//2015-02-27 필수값 체크
			if(paramMap.isEmpty()){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			List<?> rcpList = rcpSelfSuffService.getRcpMgmtListSelfSuff(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSP1010033", "신청정보(자급제)"))
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
	 * 신청관리(자급제) 상세조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtListSelfSuffDtl.json")
	public String getRcpMgmtListSelfSuffDtl(@ModelAttribute("searchVO") RcpSelfSuffVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.info("paramMap.toString() : " + paramMap.toString());
		
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
			
			//2015-02-27 필수값 체크
			if(paramMap.isEmpty()){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			List<?> rcpList = rcpSelfSuffService.getRcpMgmtListSelfSuffDtl(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSP1010033", "신청정보(자급제)"))
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
	 * 신청관리(자급제) 상세정보 저장
	 */
	@RequestMapping("/rcp/rcpMgmt/updateSelfSuffDetail.json")
	public String updateSelfSuffDetail(HttpServletRequest pRequest,
				HttpServletResponse pResponse,
				RcpSelfSuffVO rcpSelfSuffVO,
				ModelMap model,
				@RequestParam Map<String, Object> pParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(자급제) 상세정보 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pParamMap);
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());
			
			// 본사 화면인 경우
			if(!"10".equals(pParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			// 마스킹 권한
			if(!"Y".equals(maskingYn)) {
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
//			System.out.println("## param : " + pParamMap);
			rcpSelfSuffService.updateSelfSuffDetail(pParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
	 * 신청관리(자급제) 상세정보 마스킹 저장
	 */
	@RequestMapping("/rcp/rcpMgmt/updateSelfSuffDetailMask.json")
	public String updateSelfSuffDetailMask(HttpServletRequest pRequest,
				HttpServletResponse pResponse,
				RcpSelfSuffVO rcpSelfSuffVO,
				ModelMap model,
				@RequestParam Map<String, Object> pParamMap)
	{
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(자급제) 상세정보 마스킹 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pParamMap);
			
			// 본사 화면인 경우
			if(!"10".equals(pParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
//			System.out.println("## param : " + pParamMap);
			rcpSelfSuffService.updateSelfSuffDetailMask(pParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		}catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
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
	 * 신청관리(자급제) 엑셀다운로드 
	 */
	@RequestMapping("/rcp/rcpMgmt/getSelfSuffListByExcel.json")
	public String getSelfSuffListByExcel(@ModelAttribute("searchVO") RcpSelfSuffVO searchVO, 
			HttpServletRequest request,	
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) 
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<?> resultList = rcpSelfSuffService.getSelfSuffListByExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "신청관리(자급제)_";//파일명
			String strSheetname = "신청관리(자급제)";//시트명
			
			String [] strHead = { 
					"폰진행상태", "유심진행상태", "신청서상태", "예약번호", "고객명", "생년월일", "휴대폰번호", "대리점", "요금제", "업무구분",
					"신청일자", "모집경로", "유입경로", "단말일련번호", "유심번호", "메모", "배송받으시는분", "연락처", "우편번호", "주소",
					"상세주소", "배송요청사항", "유심택배사", "유심송장번호", "단말택배사", "단말송장번호", 
					"수정자", "수정일자"};
			
			String [] strValue = { 
					"phoneStateName", "usimStateName", "pstateName", "resNo", "cstmrName2", "birthDt", "cstmrMobile", "agentName", "socName", "operName",
					"reqInDt", "onOffNm", "openMarketReferer", "reqPhoneSn", "reqUsimSn", "requestMemo", "dlvryName", "dlvryMobileNo", "dlvryPost", "dlvryAddr", 
					"dlvryAddrDtl", "dlvryMemo", "tbNm", "dlvryNo", "phoneTbNm", "phoneDlvryNo",
					"rvisnId", "rvisnDttm"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {
					4000, 4000, 4000, 4000, 4000, 4000, 4000, 6000, 12000, 4000,
					4000, 4000, 4000, 4000, 8000, 8000, 8000, 4000, 4000, 18000,
					6000, 10000, 4000, 4000, 4000, 4000, 4000, 6000};
			int[] intLen = {
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0};
			
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
				pRequestParamMap.put("DUTY_NM"	,"RCP");						//업무명
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
	* @Description : 신청관리(자급제) 송장번호등록 업로드 양식 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getSelfSuffDlvryNoExcelDown.json")
	public String getSelfSuffDlvryNoExcelDown(@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> pRequestParamMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<RcpSelfSuffVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "송장번호등록엑셀양식_";//파일명
			String strSheetname = "송장번호등록엑셀양식";//시트명
			
			String [] strHead = {"일련번호", "예약번호", "택배사", "송장번호"};
			String [] strValue = {"reqSrNo", "resNo", "tbNm" ,"dlvryNo"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000};
			int[] intLen = {};
			
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
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
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
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* @Description : 신청관리(자급제) 배송완료등록 업로드 양식 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getSelfSuffDlvryOkExcelDown.json")
	public String getSelfSuffDlvryOkExcelDown(@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> pRequestParamMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = new ArrayList<RcpSelfSuffVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "배송완료등록엑셀양식_";//파일명
			String strSheetname = "배송완료등록엑셀양식";//시트명
			
			String [] strHead = {"송장번호"};
			String [] strValue = {"dlvryNo"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000};
			int[] intLen = {};
			
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
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
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
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	/**
	 * @Description : 신청관리(자급제) 송장번호등록 엑셀업로드 목록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/rcpMgmt/getSelfSuffDlvryNoList.json")
	public String getSelfSuffDlvryNoList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			
			String[] arrCell = {"reqSrNo", "resNo", "tbNm", "dlvryNo"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSuffVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 신청관리(자급제) 배송완료 엑셀업로드 목록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/rcpMgmt/getSelfSuffDlvryOkList.json")
	public String getSelfSuffDlvryOkList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pRequestParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			
			String[] arrCell = {"dlvryNo"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.RcpSelfSuffVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	

	/**
	 * @Description : 신청관리(자급제) 송장번호 엑셀등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/rcpMgmt/regSelfSuffDlvryNoList.json")
	public String regSelfSuffDlvryNoList(@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(자급제) 송장번호 엑셀등록 START."));
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
			RcpSelfSuffVO vo = new ObjectMapper().readValue(data, RcpSelfSuffVO.class);

			rcpSelfSuffService.regSelfSuffDlvryNoList(vo, searchVO.getSessionUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP1010030", "신청관리(유심배송)")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	

	/**
	 * @Description : 신청관리(자급제) 배송완료 엑셀등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/rcpMgmt/regSelfSuffDlvryOkList.json")
	public String regSelfSuffDlvryOkList(@ModelAttribute("rcpSelfSuffVO") RcpSelfSuffVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("신청관리(자급제) 배송완료 엑셀등록 START."));
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
			RcpSelfSuffVO vo = new ObjectMapper().readValue(data, RcpSelfSuffVO.class);
			
			rcpSelfSuffService.regSelfSuffDlvryOkList(vo, searchVO.getSessionUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP1010030", "신청관리(유심배송)")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
}
