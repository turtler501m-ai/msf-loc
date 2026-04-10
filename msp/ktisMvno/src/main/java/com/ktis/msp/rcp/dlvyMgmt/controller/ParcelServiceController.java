package com.ktis.msp.rcp.dlvyMgmt.controller;

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
import com.ktis.msp.cmn.fileup2.service.FileUp2Service;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.dlvyMgmt.service.ParcelService;
import com.ktis.msp.rcp.dlvyMgmt.vo.ParcelServiceVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ParcelServiceController extends BaseController {
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private OrgCommonService orgCommonService;
	
	@Autowired
	private ParcelService parcelService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private FileUp2Service fileUp2Service;
	
	/**
	 * 택배발송현황초기화
	 * @param commandMap
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value = "/rcp/dlvyMgmt/parcelServiceInfoInit.do")
	public ModelAndView parcelServiceInfoInit(HttpServletRequest pRequest,
								HttpServletResponse pResponse,
								@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
								ModelMap model){
		
		logger.info("**********************************************************");
		logger.info("* 택배발송현황조회 : msp_rcp_stat_1006 ");
		logger.info("**********************************************************");
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(parcelServiceVO);
			
			//본사화면일경우
			if(!"10".equals(pRequest.getSession().getAttribute("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			model.addAttribute("startDate",orgCommonService.getWantDay(-30));
			model.addAttribute("endDate",orgCommonService.getToDay());
			
			modelAndView.setViewName("/rcp/dlvyMgmt/msp_rcp_stat_1006");
			
			return modelAndView;
			
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 택배발송현황조회
	 * @param paramReq
	 * @param paramRes
	 * @param ParcelServiceVO
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/getParcelServiceLstInfo.json")
	public String getParcelServiceLstInfo(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ParcelServiceVO> resultList = parcelService.getParcelServiceLstInfo(parcelServiceVO, pReqParamMap);
			
			int totalCount = 0;
			
			if(resultList.size() > 0){
				totalCount = resultList.get(0).getTOTAL_COUNT();
			}
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, totalCount);
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * 택배발송현황엑셀다운
	 * @param paramReq
	 * @param paramRes
	 * @param ParcelServiceVO
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/getParcelServiceLstInfoByExcel.json")
	public String getParcelServiceLstInfoByExcel(@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> paramMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ParcelServiceVO> resultList = parcelService.getParcelServiceLstInfoByExcel(parcelServiceVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "택배발송현황_";//파일명
			String strSheetname = "택배발송현황";//시트명
			
			String [] strHead = {"신청일자", "택배사", "송장번호", "예약번호", "수취인", "전화번호", "주소", "상세주소", "우편번호", "메모"};
			String [] strValue = {"sysRdate", "tbCd", "dlvrNo", "resNo", "dlvryName", "mobileNo", "dlvryAddr", "dlvryAddrDtl", "dlvryPost", "dlvryMemo"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000, 5000, 15000, 15000, 5000, 10000};
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
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
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"RCP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
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
	 * 송장번호업로드양식다운
	 * @param paramReq
	 * @param paramRes
	 * @param ParcelServiceVO
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/getParNumTempExcelDown.json")
	public String getParNumTempExcelDown(@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> paramMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			
			// 본사 화면인 경우
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ParcelServiceVO> resultList = new ArrayList<ParcelServiceVO>();
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "송장등록엑셀양식_";//파일명
			String strSheetname = "송장등록엑셀양식";//시트명
			
			//엑셀 제목
			ArrayList<String[]> aryHead = new ArrayList<String[]>();
			
			String [] strtitle = {"송장등록엑셀양식", "", ""};
			
			aryHead.add(strtitle);
			
			String [] strHead = {"택배사", "송장번호", "예약번호"};
			
			aryHead.add(strHead);
			
			//셀병합
			ArrayList<int[]> aryMerged = new ArrayList<int[]>();
			
			int[] nMerged1 = {0,0,0,2};
			
			aryMerged.add(nMerged1);
			
			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelCellMergedDownProc(strFilename, strSheetname, resultList.iterator(), aryHead, aryMerged, intWidth, strValue, request, response, intLen);
			
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
	 * 배송완료엑셀양식다운
	 * @param paramReq
	 * @param paramRes
	 * @param ParcelServiceVO
	 * @param model
	 * @return
	 * @throws EgovBizException
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/getParComTempExcelDown.json")
	public String getParComTempExcelDown(@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> paramMap,
												ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			
			// 본사 화면인 경우
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<ParcelServiceVO> resultList = new ArrayList<ParcelServiceVO>();
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "배송완료처리양식_";//파일명
			String strSheetname = "배송완료처리양식";//시트명
			
			//엑셀 제목
			ArrayList<String[]> aryHead = new ArrayList<String[]>();
			
			String [] strtitle = {"배송완료처리양식", "", ""};
			
			aryHead.add(strtitle);
			
			String [] strHead = {"택배사", "송장번호", "배송진행상태"};
			
			aryHead.add(strHead);
			
			//셀병합
			ArrayList<int[]> aryMerged = new ArrayList<int[]>();
			
			int[] nMerged1 = {0,0,0,2};
			
			aryMerged.add(nMerged1);
			
			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000};
			int[] intLen = {};
			
			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			String strFileName = fileDownService.excelCellMergedDownProc(strFilename, strSheetname, resultList.iterator(), aryHead, aryMerged, intWidth, strValue, request, response, intLen);
			
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
	 * 엑셀업로드
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/regParExcelUp.do")
	public String regParExcelUp(
								ModelMap model, HttpServletRequest pRequest, HttpServletResponse pResponse, 
								@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//--------------------------------------
			// return JSON 변수 선언
			//--------------------------------------
			
			String uploadFileName = fileUp2Service.fileUpLoad(pRequest, "file", "CMN" );
			
			resultMap.put("state", true);
			resultMap.put("name", uploadFileName);
			resultMap.put("size", "");
			
		} catch (Exception e) {
			
			resultMap.put("state", false);
			resultMap.put("name", "");
			resultMap.put("size", "");
			model.addAttribute("result", resultMap);
			
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonViewArray";
	}
	
	/**
	 * 송장번호 엑셀업로드 목록
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/regParNumExcelUpList.json")
	public String regParNumExcelUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + parcelServiceVO.getFileName();
			
			String[] arrCell = {"tbCd", "dlvrNo", "resNo"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.dlvyMgmt.vo.ParcelServiceVO", sOpenFileName, arrCell, 2);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * 배송완료 엑셀업로드 목록
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping(value="/rcp/dlvyMgmt/regParComExcelUpList.json")
	public String regParComExcelUpList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + parcelServiceVO.getFileName();
			
			String[] arrCell = {"tbCd", "dlvrNo", "requestStateCode"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.dlvyMgmt.vo.ParcelServiceVO", sOpenFileName, arrCell, 2);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	/**
	 * 송장번호등록
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping("/rcp/dlvyMgmt/regParcelServiceInfo.json")
	public String regParcelServiceInfo(@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO, 
			@RequestBody String data,
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ParcelServiceVO vo = new ObjectMapper().readValue(data, ParcelServiceVO.class);
			
			int resultCnt = parcelService.regParcelServiceInfo(vo, loginInfo.getUserId());
			
			if ( resultCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "변경대상이 없습니다.");
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch (Exception e) {
			resultMap.clear();
						
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
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
	 * 배송완료등록
	 * 
	 * @return String
	 * @author 
	 * @version 
	 * @created 
	 * @updated
	 */
	@RequestMapping("/rcp/dlvyMgmt/regParcelCompleServiceInfo.json")
	public String regParcelCompleServiceInfo(@ModelAttribute("parcelServiceVO") ParcelServiceVO parcelServiceVO, 
			@RequestBody String data,
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(parcelServiceVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			if(!"10".equals(parcelServiceVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			ParcelServiceVO vo = new ObjectMapper().readValue(data, ParcelServiceVO.class);
			
			int resultCnt = parcelService.regParcelCompleServiceInfo(vo, loginInfo.getUserId());
			
			if ( resultCnt == 0 ){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "변경대상이 없습니다.");
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch (Exception e) {
			resultMap.clear();
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.SQL_ERROR", null, Locale.getDefault()));
			resultMap.put("msg" , "");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView"; 
	}
}
