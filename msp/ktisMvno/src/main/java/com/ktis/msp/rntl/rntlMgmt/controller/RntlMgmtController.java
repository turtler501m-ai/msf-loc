package com.ktis.msp.rntl.rntlMgmt.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rntl.rntlMgmt.service.RntlMgmtService;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByMnthCalcListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchSaleListVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtByPurchStandVO;
import com.ktis.msp.rntl.rntlMgmt.vo.RntlMgmtBySaleOpenMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class RntlMgmtController extends BaseController {
	@Autowired
	private MenuAuthService  menuAuthService;
	
	@Autowired
	private RntlMgmtService rntlMgmtService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	@Autowired
	private FileDownService  fileDownService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchStandView.do")
	public ModelAndView getPurchStandView(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("rntl/rntlMgmt/msp_rntl_mgmt_8001");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchStandList.json")
	public String getPurchStandList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByPurchStandVO") RntlMgmtByPurchStandVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매입기준관리 목록조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByPurchStandVO> resultList = rntlMgmtService.getPurchStandList(searchVO);
			
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
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchStandDtlList.json")
	public String getPurchStandDtlList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByPurchStandVO") RntlMgmtByPurchStandVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매입기준관리 목록 상세 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByPurchStandVO> resultList = rntlMgmtService.getPurchStandDtlList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchStandListExcel.json")
	@ResponseBody
	public String getPurchStandListExcel(@ModelAttribute RntlMgmtByPurchStandVO vo,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> pReqParamMap,
											ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "매입기준관리_";	/** 파일명 */
			String strSheetname = "매입기준관리";				/** 시트명 */
			
			/* 매입기준관리 조회 */
			List<RntlMgmtByPurchStandVO> resultList = rntlMgmtService.getPurchStandListExcel(vo);
			
			//엑셀 첫줄
			String[] strHead = {"대리점", "적용월", "단말모델명", "물류처리비", "매입가", 
								"월별렌탈비", "검수비", "최저매입보장가", "등록자", "등록일시", "수정자", "수정일시"};
			String[] strValue = {"agncyNm", "strtYm", "prdtCode", "deplrictCost", "buyAmnt", 
								 "rentalCost", "virfyCost", "grntAmnt", "regId", "regDttm", "rvisnId", "rvisnDttm"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000, 5000, 5000};
							  
			int[] intLen = {0, 0, 0, 1, 1, 
							1, 1, 1, 0, 0, 0, 0};
			
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
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RNTL");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				
				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
				
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/rntl/rntlMgmt/savPurchStandByInfo.json")
	public String savPurchStandByInfo(@ModelAttribute("searchVO") RntlMgmtByPurchStandVO rntlMgmtByPurchStandVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rntlMgmtByPurchStandVO);
			
			// 본사 화면인 경우
			if(!"10".equals(rntlMgmtByPurchStandVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int nRslt = rntlMgmtService.savPurchStandByInfo(rntlMgmtByPurchStandVO);
			
			if(nRslt > 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "저장에 실패 하였습니다.");
			}
		}
		catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getSaleOpenMgmtView.do")
	public ModelAndView getSaleOpenMgmtView(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo", rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("rntl/rntlMgmt/msp_rntl_mgmt_8002");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getSaleOpenList.json")
	public String getSaleOpenList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtBySaleOpenMgmtVO") RntlMgmtBySaleOpenMgmtVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매각개통내역 목록조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId()) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtBySaleOpenMgmtVO> resultList = rntlMgmtService.getSaleOpenList(searchVO, pReqParamMap);
			
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
	
	@RequestMapping(value = "/rntl/rntlMgmt/getSaleOpenListExcel.json")
	@ResponseBody
	public String getSaleOpenListExcel(@ModelAttribute RntlMgmtBySaleOpenMgmtVO vo,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> pReqParamMap,
											ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "매각개통내역_";	/** 파일명 */
			String strSheetname = "매각개통내역";				/** 시트명 */
			
			/* 매각개통내역 조회 */
			List<RntlMgmtBySaleOpenMgmtVO> resultList = rntlMgmtService.getSaleOpenListExcel(vo, pReqParamMap);
			
			//엑셀 첫줄
			String[] strHead = {"대리점", "예약번호", "계약번호", "고객명", "단말모델코드",
								"단말모델명", "단말모델일련번호", "개통일자", "반납일자", "계약상태",
								"렌탈상태","매입단가","렌탈비용","정산일수(누적)", "정산금액(누적)",
								"정산일수(당월)", "정산금액(당월)", "잔존가액"};
			String[] strValue = {"agncyNm", "resNo", "contractNum", "custNm", "intmMdlCd", 
								 "intmMdlNm", "intmMdlSrl", "openDt", "rtrnDt","subStatus",
								 "rntlStat", "buyAmnt", "rentalCost","prvdTotDay","prvdTotAmnt",
								 "prvdMonDay", "prvdMonAmnt", "remainAmnt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000};
							  
			int[] intLen = {0, 0, 0, 0, 0,
							0, 0, 0, 0, 0,
							0, 1, 1, 1, 1,
							1, 1, 1};
			
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
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RNTL");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				
				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
				
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping("/rntl/rntlMgmt/saveReturnMgmtByInfo.json")
	public String saveReturnMgmtByInfo(@ModelAttribute("searchVO") RntlMgmtBySaleOpenMgmtVO rtlMgmtBySaleOpenMgmtVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rtlMgmtBySaleOpenMgmtVO);
			
			if(!"10".equals(rtlMgmtBySaleOpenMgmtVO.getSessionUserOrgnTypeCd()) && !rtlMgmtBySaleOpenMgmtVO.getSessionUserOrgnId().equals(rtlMgmtBySaleOpenMgmtVO.getOrgnId()) ){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int nRslt = rntlMgmtService.saveReturnMgmtByInfo(rtlMgmtBySaleOpenMgmtVO);
			
			if(nRslt > 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "저장에 실패 하였습니다.");
			}
		}
		catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchSaleListView.do")
	public ModelAndView getPurchSaleListView(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("rntl/rntlMgmt/msp_rntl_mgmt_8003");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchList.json")
	public String getPurchList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByPurchSaleListVO") RntlMgmtByPurchSaleListVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매입내역 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByPurchSaleListVO> resultList = rntlMgmtService.getPurchList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getSaleList.json")
	public String getSaleList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByPurchSaleListVO") RntlMgmtByPurchSaleListVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("매각내역 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByPurchSaleListVO> resultList = rntlMgmtService.getSaleList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getPurchSaleListExcel.json")
	@ResponseBody
	public String getPurchSaleListExcel(@ModelAttribute RntlMgmtByPurchSaleListVO vo,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> pReqParamMap,
											ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "매입매각통계내역_";	/** 파일명 */
			
			ArrayList<HashMap<String, Object>> excelInfo = new ArrayList<HashMap<String, Object>>();
			
			String strSheetname = "매입통계내역";				/** 시트명 */
			
			/* 매입통계내역 조회 */
			List<RntlMgmtByPurchSaleListVO> resultList = rntlMgmtService.getPurchList(vo);
			
			//엑셀 첫줄
			String[] strHead = {"정산월", "대리점", "단말모델코드", "단말모델명",
								"매입단가", "매입수량", "매입합계", "물류비용", "지급합계"};
			String[] strValue = {"prvdYm", "agncyNm", "intmMdlCd", "intmMdlNm",
								 "buyAmnt", "buyCnt", "buySumAmnt", "deplrictCost", "buyPayAmnt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 
							  5000, 5000, 5000, 5000, 5000};
							  
			int[] intLen = {0, 0, 0, 0,
							1, 1, 1, 1, 1};
			
			HashMap<String, Object> excelMap = new HashMap<String, Object>();
			
			excelMap.put("sheetName", strSheetname);
			excelMap.put("voLists"	, resultList.iterator());
			excelMap.put("strHead"	, strHead);
			excelMap.put("strValue"	, strValue);
			excelMap.put("intWidth"	, intWidth);
			excelMap.put("intLen"	, intLen);
			
			excelInfo.add(excelMap);
			
			String strSheetname2 = "매각통계내역";				/** 시트명 */
			
			/* 매각통계내역 조회 */
			List<RntlMgmtByPurchSaleListVO> resultList2 = rntlMgmtService.getSaleList(vo);
			
			//엑셀 첫줄
			String[] strHead2 = {"정산월", "대리점", "단말모델코드", "단말모델명","매입단가",
								 "매각수량", "매각금액", "검수비용", "실매각금액", "정산금액"};
			String[] strValue2 = {"prvdYm", "agncyNm", "intmMdlCd", "intmMdlNm", "buyAmnt",
								 "saleCnt", "saleAmnt", "virfyCost", "salePayAmnt", "prvdAmnt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth2 = {5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000};
							  
			int[] intLen2 = {0, 0, 0, 0, 1,
							1, 1, 1, 1, 1};
			
			excelMap = new HashMap<String, Object>();
			
			excelMap.put("sheetName", strSheetname2);
			excelMap.put("voLists"	, resultList2.iterator());
			excelMap.put("strHead"	, strHead2);
			excelMap.put("strValue"	, strValue2);
			excelMap.put("intWidth"	, intWidth2);
			excelMap.put("intLen"	, intLen2);
			
			excelInfo.add(excelMap);
			
			String strFileName = fileDownService.excelDownProcAry(strFilename, excelInfo, request, response);
			
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
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RNTL");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				
				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
				
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	
	@RequestMapping(value = "/rntl/rntlMgmt/getMnthCalcListView.do")
	public ModelAndView getMnthCalcListView(
			ModelMap model, 
			HttpServletRequest pRequest, 
			HttpServletResponse pResponse, 
			@RequestParam Map<String, Object> pRequestParamMap) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("rntl/rntlMgmt/msp_rntl_mgmt_8004");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getMnthCalcList.json")
	public String getMnthCalcList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByMnthCalcListVO") RntlMgmtByMnthCalcListVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("월정산내역 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByMnthCalcListVO> resultList = rntlMgmtService.getMnthCalcList(searchVO);
			
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
	
	@RequestMapping(value = "/rntl/rntlMgmt/getMnthCalcListExcel.json")
	@ResponseBody
	public String getMnthCalcListExcel(@ModelAttribute RntlMgmtByMnthCalcListVO vo,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> pReqParamMap,
											ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			/* 로그인조직정보 및 권한체크 */
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo + "월정산내역_";	/** 파일명 */
			String strSheetname = "월정산내역";				/** 시트명 */
			
			/* 월정산내역 조회 */
			List<RntlMgmtByMnthCalcListVO> resultList = rntlMgmtService.getMnthCalcListExcel(vo);
			
			//엑셀 첫줄
			String[] strHead = {"정산월", "대리점", "계약번호", "개통일자", "반납일자",
								"단말모델코드", "단말모델명", "렌탈상태", "매입단가", "렌탈비용",
								"정산일수(누적)", "정산금액(누적)", "정산일수(당월)", "정산금액(당월)", "환수일수",
								 "환수금액", "검수비용", "매각금액", "잔존가액", "매입보장가"};
			String[] strValue = {"prvdYm" ,"agncyNm" ,"contractNum" ,"openDt" ,"rtrnDt"
								,"intmMdlCd" ,"intmMdlNm" ,"rntlStat" ,"buyAmnt" ,"rentalCost"
								,"prvdTotDay" ,"prvdTotAmnt" ,"prvdMonDay" ,"prvdMonAmnt" ,"backUsgDay"
								,"backAmnt" ,"virfyCost" ,"saleAmnt" ,"remainAmnt" ,"grntAmnt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000,
							  5000, 5000, 5000, 5000, 5000};
							  
			int[] intLen = {0, 0, 0, 0, 0,
							0, 0, 0, 1, 1,
							1, 1, 1, 1, 1,
							1, 1, 1, 1, 1};
			
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
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getHeader("REMOTE_ADDR");
				}
				
				if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown")) {
					ipAddr = request.getRemoteAddr();
				}
				
				pReqParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pReqParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pReqParamMap.put("DUTY_NM"   ,"RNTL");                      //업무명
				pReqParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pReqParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pReqParamMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				pReqParamMap.put("DATA_CNT", resultList.size());            //자료건수
				
				fileDownService.insertCmnFileDnldMgmtMst(pReqParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
				
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
			
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			
			if(out != null) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw new MvnoErrorException(e);
				}
			}
			file.delete();
		}
		
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	@RequestMapping(value = "/rntl/rntlMgmt/getMnthCalcDtlList.json")
	public String getMnthCalcDtlList(HttpServletRequest request,
								HttpServletResponse response,
								@ModelAttribute("RntlMgmtByMnthCalcListVO") RntlMgmtByMnthCalcListVO searchVO,
								ModelMap model,
								@RequestParam Map<String, Object> pReqParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("월정산상세내역 조회"));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<RntlMgmtByMnthCalcListVO> resultList = rntlMgmtService.getMnthCalcDtlList(searchVO);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, resultList, resultList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}
}
