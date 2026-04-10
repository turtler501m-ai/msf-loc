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
import com.ktis.msp.rcp.rcpMgmt.service.AppFormMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.AppFormMgmtVO;

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
public class AppFormMgmtController extends BaseController {

//	@Autowired
//	private OrgCommonService orgCommonService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private AppFormMgmtService appFormService;
	
	@Autowired
	private LoginService loginService;
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	/** Constructor */
	public AppFormMgmtController() {
		setLogPrefix("[AppFormMgmtController]");
	}

	/**
	 * 서식지함
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getAppFormMgmt.do")
	public ModelAndView getAppFormMgmt( @ModelAttribute("searchVO") AppFormMgmtVO searchVO,
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
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			
			String scanSearchUrl =  propertiesService.getString("scan.search.url");
			
			modelAndView.getModelMap().addAttribute("scanSearchUrl", scanSearchUrl);

			String scanDownloadUrl =  propertiesService.getString("scan.download.url");
			String maskingYn = loginService.getUsrMskYn(loginInfo.getUserId());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<?> macInfoList = loginService.selectMacChkInfo();
			for (int i=0;i<macInfoList.size();i++){
				Map<String, Object> map = (Map<String, Object>) macInfoList.get(i);
				resultMap.put((String)map.get("cdVal"), map.get("cdDsc"));
			}
			
			String agentVersion = (String) resultMap.get("AGENT_VERSION");	// 스캔버전 (현재 1.1)
			String serverUrl = (String) resultMap.get("SERVER_URL");		// 서버환경 (로컬 : L, 개발 : D, 운영 : R)

			logger.info("agentVersion : " + agentVersion);
			logger.info("serverUrl : " + serverUrl);
			logger.info("maskingYn : " + maskingYn);
			logger.info("scanSearchUrl : " + scanSearchUrl);
			logger.info("scanDownloadUrl : " + scanDownloadUrl);
			
			model.addAttribute("agentVersion", agentVersion);
			model.addAttribute("serverUrl", serverUrl);
			model.addAttribute("scanDownloadUrl", scanDownloadUrl);
			model.addAttribute("maskingYn", maskingYn);
			
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/appFormMgmt");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	/**
	 * 서식지함 목록조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getAppFormMgmtList.json")
	public String getAppFormMgmtList(@ModelAttribute("searchVO") AppFormMgmtVO searchVO,
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
			
			// 대리점인 경우 대리점ID 강제 세팅
			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				searchVO.setAgntCd(loginInfo.getUserOrgnId());
			}
			
			List<?> list = appFormService.getAppFormMgmtList(searchVO, paramMap);
			
			resultMap =  makeResultMultiRow(searchVO, list);
		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1010100", "서식지함"))
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
	 * 서식지함 엑셀다운로드
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getAppFormMgmtListExcel.json")
	public String getAppFormMgmtListExcel(@ModelAttribute("searchVO") AppFormMgmtVO searchVO,
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
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			//----------------------------------------------------------------
			// 엑셀다운로드 HISTORY 저장
			Map<String, Object> excelMap = new HashMap<String, Object>();
			int exclDnldId = fileDownService.getSqCmnExclDnldHst();
			excelMap.put("EXCL_DNLD_ID", exclDnldId);
			excelMap.put("MENU_ID",request.getParameter("menuId"));
			excelMap.put("USR_ID",loginInfo.getUserId());
			excelMap.put("USR_NM",loginInfo.getUserName());
			excelMap.put("ORGN_ID",loginInfo.getUserOrgnId());
			excelMap.put("DUTY_NM","RCP");
			excelMap.put("MENU_NM","서식지함");
			String searchVal = "신청일자:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|대리점:"+searchVO.getAgntCd()+
					"|고객명:"+searchVO.getCustNm()+
					"|판매점:"+searchVO.getShopCd()+
					"|진행상태:"+searchVO.getProcStatCd()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			
			// 대리점인 경우 대리점ID 강제 세팅
			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				searchVO.setAgntCd(loginInfo.getUserOrgnId());
			}
			
			List<?> list = appFormService.getAppFormMgmtListExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			
			String strFilename = serverInfo  + "서식지함_";//파일명
			String strSheetname = "서식지함";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"등록일시","대리점ID","대리점","판매점ID", "판매점","고객명","업무구분","진행상태","파일갯수","등록자ID","등록자","처리자ID","처리자","처리일시"};//14
			String [] strValue = {"regDttm","agencyId","agntNm","companyId","shopNm","custNm","workNm","procStatNm","scanFileCnt","rgstPrsnId","rgstPrsnNm","procPrsnId","procPrsnNm","procDttm"};
			int[] intWidth = {8000, 5000, 8000, 5000, 8000, 8000, 5000, 5000, 3000, 5000, 5000, 5000, 5000, 8000}; //14
			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
			
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
				paramMap.put("DATA_CNT"  ,list.size());                 //자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				paramMap.put("EXCL_DNLD_ID", exclDnldId);
				
				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
		} catch (Exception e) {
						
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					returnMsg, "MSP1010100", "서식지함"))
			{
            //v2018.11 PMD 적용 소스 수정
				throw new MvnoErrorException(e);
			} 			
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
	 * 서식지함변경
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/updateAppFormData.json")
	public String updateAppFormData(@ModelAttribute("searchVO") AppFormMgmtVO searchVO,
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
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getAgencyId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			appFormService.updateAppFormData(searchVO);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch ( Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					"서식지 수정 실패 " + e.getMessage(), "MSP1010100", "서식지함"))
			{
            //v2018.11 PMD 적용 소스 수정
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
	 * 서식지상태체크
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getAppFormStatCd.json")
	public String getAppFormStatCd(@ModelAttribute("searchVO") AppFormMgmtVO searchVO,
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
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getAgencyId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = appFormService.getAppFormStatCd(searchVO);
			
			resultMap =  makeResultMultiRow(searchVO, list);
		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1010100", "서식지함"))
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
	
}
