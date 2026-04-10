/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.rcp.rcpMgmt.controller;


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
import com.ktis.msp.rcp.rcpMgmt.service.DvcChgMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.DvcChgMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class DvcChgMgmtController extends BaseController {
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private DvcChgMgmtService dvcChgService;
	
	@Autowired
	private RcpMgmtService rcpMgmtService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	/** Constructor */
	public DvcChgMgmtController() {
		setLogPrefix("[DvcChgMgmtController]");
	}

	/**
	 * 기변대상조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgTrgtMgmt.do")
	public ModelAndView getDvcChgTrgtMgmt( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											ModelMap model,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/msp_rcp_bs_0203");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 기변대상 목록조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgTrgtList.json")
	public String getDvcChgTrgtList(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
									HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> paramMap,
									ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 대리점인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				searchVO.setOpenAgntCd(loginInfo.getUserOrgnId());
			}
			
			List<?> list = dvcChgService.getDvcChgTrgtList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1003010", "기기변경대상"))
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
	 * 기변대상 엑셀다운로드
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgTrgtListExcel.json")
	public String getDvcChgTrgtListExcel(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap,
											ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 대리점인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				searchVO.setOpenAgntCd(loginInfo.getUserOrgnId());
			}
			
			List<?> list = dvcChgService.getDvcChgTrgtListExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			
			String strFilename = serverInfo  + "기변대상_";//파일명
			String strSheetname = "기변대상";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"추출월","계약번호","CTN","개통일자","계약상태","TM상태","상담원","고객명","개통대리점","단말모델","할부원금","할부개월수","잔여할부개월수","약정개월수","잔여약정개월수","보증보험관리번호","미납여부","마케팅동의여부"};//17
			String [] strValue = {"crtYm","contractNum","subscriberNoMask","openDt","subStatusNm","tmStatNm","vocPrsnNm","custNmMask","openAgntNm","prdtCode","instAmnt","instMnthCnt","remainCnt","agrmMnthCnt","remainAgrmCnt","grntInsrMngmNo","unpdYn","mktAgrmYn"};//17
			int[] intWidth = {3000,5000,5000,3000,3000,3000,5000,5000,8000,5000,5000,5000,5000,5000,5000,8000,3000,5000}; //16
			int[] intLen = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0};
			
			String strFileName = "";
			
			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
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
				
				paramMap.put("FILE_NM"   ,file.getName());              //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				paramMap.put("DUTY_NM"   ,"RCP");                       //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId"    ,request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT"  ,list.size());                            //자료건수
				
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
	 * 기변대상 상담원할당(균등할당)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/setDvcChgTrgtAsgnList.json")
	public String setDvcChgTrgtAsgnList(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap,
											ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 대리점인 경우
			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
				searchVO.setOpenAgntCd(loginInfo.getUserOrgnId());
			}
			
			// 상담원유무 확인
			List<?> plist = dvcChgService.getAsgnTrgtPrsnList();
			
			if(plist.size() == 0){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "할당대상 상담원이 존재하지 않습니다.");
			}else{
				dvcChgService.setDvcChgTrgtAsgnList(searchVO);
				
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}
			
		} catch ( Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "상담원할당 실패");
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	/**
	 * 기변대상 상담원할당(수동할당)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/setDvcChgTrgtAsgnList2.json")
	public String setDvcChgTrgtAsgnList2(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
										@RequestBody String data,
										ModelMap model,
										HttpServletRequest request, 
										HttpServletResponse response,
										@RequestParam Map<String, Object> requestParamMap)
	{
			
		logger.debug("pJsonString=" + data);
			
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, String> resultMap = new HashMap<String, String>();
			
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			DvcChgMgmtVO vo = new ObjectMapper().readValue(data, DvcChgMgmtVO.class);
			
			dvcChgService.setDvcChgTrgtAsgnList2(vo);
			
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
	 * 기변상담관리
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgCnslMgmt.do")
	public ModelAndView getDvcChgCnslMgmt( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
												ModelMap model,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam Map<String, Object> paramMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo", loginInfo);
			modelAndView.getModelMap().addAttribute("authCheck", dvcChgService.getAuthCheck(searchVO));	// 권한자 체크
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/msp_rcp_bs_0204");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 기변상담관리
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgCnslList.json")
	public String getDvcChgCnslList( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											ModelMap model,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = dvcChgService.getDvcChgCnslList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1003020", "기변상담관리"))
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
	 * TM결과등록
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/updateDvcChgTmRslt.json")
	public String updateDvcChgTmRslt( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
										ModelMap model,
										HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam Map<String, Object> paramMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			dvcChgService.updateDvcChgTmRslt(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "상담결과 수정 실패");
			throw new MvnoErrorException(e);
		}
		
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		
		return "jsonView";
	}
	
	/**
	* 기기변경목록
	*/
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgList.do")
	public ModelAndView getDvcChgList( @ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											ModelMap model,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			modelAndView.getModelMap().addAttribute("loginInfo" , loginInfo);
			modelAndView.getModelMap().addAttribute("orgnInfo"  , rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));
			
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/msp_rcp_bs_0206");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	* 기기변경목록 조회
	*/
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgListAjax.json")
	public String getDvcChgListAjax(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
									HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam Map<String, Object> paramMap,
									ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = dvcChgService.getDvcChgList(searchVO, paramMap);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1003040", "기기변경목록"))
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
	* 기기변경목록 엑셀다운로드
	* url 변경
	*/
	@RequestMapping(value = "/rcp/rcpMgmt/getDvcChgListExcel.json")
	public String getDvcChgListExcel(@ModelAttribute("searchVO") DvcChgMgmtVO searchVO,
											HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam Map<String, Object> paramMap,
											ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> list = dvcChgService.getDvcChgListExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			
			String strFilename  = serverInfo  + "기기변경목록_"; //파일명
			String strSheetname = "기기변경목록";                //시트명
			
			 			
			//2020.12.04 엑셀시트에 할인유형 열 추가
			//[SRM20112729346] M전산 기기변경목록 항목(할인유형) 추가 요청
			//엑셀 첫줄
			String [] strHead = {"계약번호","계약상태","휴대폰번호","고객명","생년월일","업무구분","구매유형","모집경로","할인유형","신청일시",//10
								 "기변일자","최초개통일자","해지일자","해지사유","기변요금제","현재요금제","최초요금제","약정개월수","할부개월수","기변개통단말",//10
								 "기변단말일련번호","현재단말명","현재단말일련번호","개통대리점","개통대리점코드","기변대리점","기변대리점코드","할부원금","기변단말출고가","보증보험관리번호",//10
								 "보험가입상태","공시지원금"};	//1
			String [] strValue = {"contractNum","subStatusNm","subscriberNoMask","cstmrName","birthDt","operTypeNm","reqBuyTypeNm","onOffTypeNm","sprtTpNm","reqInDayEx",
								  "dvcChgDtEx","lstComActvDate","dvcChgTmntDtEx","dvcChgTmntRsnNm","dvcRateNm","rateNm","fstRateNm","instMnthCnt","enggMnthCnt","dvcModelNm",
								  "dvcIntmSrlNo","modelNm","intmSrlNo","openAgntNm","openAgntCd","agntNm","agntCd","instOrginAmnt","modelPrice","grntInsrMngmNo",
								  "grntInsrStatNm","kdSupotDscnAmnt"};
			int[] intWidth = {3000,3000,5000,5000,3000,3000,3000,3000,3000,5000,
							  3000,5000,3000,5000,6000,6000,6000,5000,5000,5000,
							  5000,5000,5000,10000,6000,10000,6000,5000,5000,5000,
							  5000,5000};
			int[] intLen = {0,0,0,0,0,0,0,0,0,0,
							0,0,0,0,0,0,0,1,1,0,
							0,0,0,0,0,0,0,1,1,0,
							0,1};
			
			String strFileName = "";
			
			try {
				strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
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
				
				paramMap.put("FILE_NM"   ,file.getName());                 //파일명
				paramMap.put("FILE_ROUT" ,file.getPath());                 //파일경로
				paramMap.put("DUTY_NM"   ,"RCP");                          //업무명
				paramMap.put("IP_INFO"   ,ipAddr);                         //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());            //파일크기
				paramMap.put("menuId"    ,request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT"  ,list.size());                    //자료건수
				
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
	
}
