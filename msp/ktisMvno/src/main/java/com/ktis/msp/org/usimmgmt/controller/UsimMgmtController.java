package com.ktis.msp.org.usimmgmt.controller;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.usimmgmt.service.UsimMgmtService;
import com.ktis.msp.org.usimmgmt.vo.UsimMgmtVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class UsimMgmtController extends BaseController {
	
	protected Logger logger = LogManager.getLogger(getClass());
		
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private UsimMgmtService usimMgmtService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	public UsimMgmtController() {
		setLogPrefix("[UsimMgmtController] ");
	}
	
	/**
	 * @Description : 유심관리 화면
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/org/usimMgmt/usimMgmtView.do")
	public ModelAndView usimMgmtView(HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			ModelMap model,
			@RequestParam Map<String, Object> pRequestParamMap){
		
		try {
			ModelAndView modelAndView = new ModelAndView();
			
			// ----------------------------------------------------------------
			// Login check
			// ----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			// 본사 화면인 경우
			if(!"10".equals(pRequestParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.setViewName("/org/usimMgmt/msp_org_usim_1001");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* @Description : 유심관리 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/getUsimMgmtList.json")
	public String getUsimMgmtList(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심관리 목록 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimMgmtService.getUsimMgmtList(usimMgmtVO);
						
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
	* @Description : 유심개통현황 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/getActiveUsimList.json")
	public String getActiveUsimList(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			/** 20230518 PMD 조치 */
			if ("".equals(usimMgmtVO.getSearchFromDt()) || usimMgmtVO.getSearchFromDt() == null || "".equals(usimMgmtVO.getSearchToDt()) || usimMgmtVO.getSearchToDt() == null) {				
				throw new MvnoServiceException("검색 일자 정보가 존재하지 않습니다.");
			}
			
			if (!KtisUtil.isCompareDate(usimMgmtVO.getSearchFromDt(), usimMgmtVO.getSearchToDt(), KtisUtil.DATEPATTERN.YearToDayWithNoBar)) {
				throw new MvnoServiceException("종료일자가 시작일자보다 큽니다.");
			}
						
			if (KtisUtil.getCompareDateGap(usimMgmtVO.getSearchFromDt(), usimMgmtVO.getSearchToDt(), KtisUtil.DATEPATTERN.YearToDayWithNoBar) >= 14) {
				throw new MvnoServiceException("최대 14일까지 조회 가능 합니다.");
			}
				
			List<?> resultList = usimMgmtService.getActiveUsimList(usimMgmtVO, pRequestParamMap);
			
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
	* @Description : 제휴유심개통현황 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/org/usimMgmt/getActiveUsimListByExcel.json")
	public String getActiveUsimListByExcel(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
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
			
			List<?> resultList = usimMgmtService.getActiveUsimListByExcel(usimMgmtVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "제휴유심개통현황_";//파일명
			String strSheetname = "제휴유심개통현황";//시트명
			
			String [] strHead = { "개통일자", "계약번호", "최초요금제", "상태", "조직ID", "조직명", "대리점", "신청일자", "해지일자", "해지사유", "유심일련번호"};
			
			String [] strValue = { "lstComActvDate", "contractNum", "fstRateNm", "status", "orgnId", "orgnNm", "agentNm", "reqInDay", "tmntDt", "canRsn", "usimNo"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 10000, 5000, 5000, 5000, 8000, 5000, 5000, 5000, 6000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
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
	* @Description : 유심관리 변경 내역 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/getUsimHistList.json")
	public String getUsimHistList(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심관리 변경 내역 조회 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = usimMgmtService.getUsimHistList(usimMgmtVO);
						
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
	* @Description : 유심관리등록
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/regUsimMgmt.json")
	public String regUsimMgmt(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심관리 등록 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if(usimMgmtService.getUsimDupChk(usimMgmtVO.getUsimNo())) {
				usimMgmtService.insertUsimMgmt(usimMgmtVO);
	 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "이미 등록된 유심번호 입니다.");
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
	* @Description : 유심관리수정
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/setUsimMgmt.json")
	public String setUsimMgmt(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심관리 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimMgmtService.updateUsimMgmt(usimMgmtVO);
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
	* @Description : 유심관리삭제
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/org/usimMgmt/delUsimMgmt.json")
	public String delUsimMgmt(@ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심관리 삭제 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			usimMgmtService.deleteUsimMgmt(usimMgmtVO);
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
	
//	/**
//	* @Description : 유심등록확정처리
//	* @Param  : 
//	* @Return : ModelAndView
//	*/
//	@RequestMapping(value="/org/usimMgmt/confirmUsimMgmt.json")
//	public String confirmUsimMgmt(@RequestBody String data, ModelMap model, HttpServletRequest request,
//			HttpServletResponse response, @RequestParam Map<String, Object> pRequestParamMap, @ModelAttribute("usimMgmtVO") UsimMgmtVO usimMgmtVO ) {
//		
//		
//		logger.info(generateLogMsg("================================================================="));
//		logger.info(generateLogMsg("유심등록확정처리 START."));
//		logger.info(generateLogMsg("================================================================="));
//		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		ObjectMapper om = new ObjectMapper();
//		om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		
//		try {
//			LoginInfo loginInfo = new LoginInfo(request, response);
//			loginInfo.putSessionToVo(usimMgmtVO);
//			loginInfo.putSessionToParameterMap(pRequestParamMap);
//						
//			// 본사 권한
//			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
//						
//			UsimMgmtVO usimMgmtListVOs = om.readValue(data, UsimMgmtVO.class);
//			
//			List<String> arrSeq = new ArrayList<String>();
//			
//			for( UsimMgmtVO regVO : usimMgmtListVOs.getItems() ) {
//				arrSeq.add(regVO.getSeq());
//			}
//			
//			UsimMgmtVO pVO = new UsimMgmtVO();
//			pVO.setArrSeq(arrSeq);
//			pVO.setVrfyId(loginInfo.getUserId());
//			
//			usimMgmtService.updateUsimVrfyYn(pVO);
//			
// 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
// 			resultMap.put("msg", "");
//			
//		} catch (Exception e) {
//			resultMap.clear();
//			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
//			{
//			    throw new MvnoErrorException(e);
//			}
//		}
//		
//		model.addAttribute("result", resultMap);
//		
//		return "jsonView";
//		
//	}
	
	/**
	 * @Description : 유심번호엑셀업로드 목록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/org/usimMgmt/regUsimUpList.json")
	public String regUsimUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("usimMgmtVO") UsimMgmtVO searchVO,
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
			
			// 2021.01.21 [SRM21011583814] M전산 제휴유심관리 內 단말기코드 항목 추가요청 모델코드 추가
			String[] arrCell = {"orgnId", "usimNo", "deliveryDttm", "orderNum","usimPrdtCode"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.org.usimmgmt.vo.UsimMgmtVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pRequestParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * @Description : 유심번호엑셀등록
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/org/usimMgmt/regUsimMgmtList.json")
	public String regUsimMgmtList(@ModelAttribute("searchVO") UsimMgmtVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심번호엑셀등록 START."));
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
			UsimMgmtVO vo = new ObjectMapper().readValue(data, UsimMgmtVO.class);
			
			usimMgmtService.regUsimMgmtList(vo, loginInfo.getUserId());

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			if(!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", e.getMessage(), "MSP2003001", "제휴유심관리")) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 유심엑셀업로드 양식다운
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/org/usimMgmt/getUsimMgmtTempExcelDown")
	public String getUsimMgmtTempExcelDown(@ModelAttribute("usimMgmtVO") UsimMgmtVO searchVO,
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
			
			List<?> resultList = new ArrayList<UsimMgmtVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "제휴유심등록엑셀양식_";//파일명
			String strSheetname = "제휴유심등록엑셀양식";//시트명
			
			// 2021.01.21 [SRM21011583814] M전산 제휴유심관리 內 단말기코드 항목 추가요청 모델코드 추가
			String [] strHead = {"조직ID", "유심일련번호", "배송일자", "주문번호","모델코드"};
			String [] strValue = {"orgnId", "usimNo", "deliveryDttm", "orderNum","usimPrdtCode"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 6000, 5000, 6000,6000};
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
}