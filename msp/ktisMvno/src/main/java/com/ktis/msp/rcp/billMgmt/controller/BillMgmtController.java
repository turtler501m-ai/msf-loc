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

package com.ktis.msp.rcp.billMgmt.controller;

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
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.rcp.billMgmt.service.BillMgmtService;
import com.ktis.msp.rcp.billMgmt.vo.BillMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : BillMgmtController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.12.14   이상직     최초작성
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class BillMgmtController extends BaseController {
	
	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService menuAuthService;

	@Autowired
	private FileDownService fileDownService;
	
	@Autowired
	private BillMgmtService billMgmtService;
	
	@RequestMapping(value = "/rcp/billMgmt/getCustInvPymMgmt.do")
	public ModelAndView getCustInvPymMgmt(@ModelAttribute("searchVO") BillMgmtVO vo, 
										HttpServletRequest request,
										HttpServletResponse response,
										ModelMap model) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response)); 
			
			modelAndView.setViewName("/rcp/billMgmt/custInvPymMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}
	
	@RequestMapping("/rcp/billMgmt/getCustInvPymMgmtList.json")
	public String getCustInvPymMgmtList(HttpServletRequest request, 
										HttpServletResponse response, 
										@ModelAttribute("searchVO") BillMgmtVO vo, 
										@RequestParam Map<String, Object> pRequestParamMap,
										ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			List<?> list = billMgmtService.getCustInvPymMgmtList(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000050", "고객청구자료"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/rcp/billMgmt/getCustInvPymListExcel.json")
	public String getCustInvPymListExcel(HttpServletRequest request,
										HttpServletResponse response,
										ModelMap model,
										@ModelAttribute("searchVO") BillMgmtVO searchVO,
										@RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			List<?> list = billMgmtService.getCustInvPymMgmtListExcel(searchVO, pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			
			String strFilename = serverInfo  + searchVO.getBillYm().substring(0, 4) + "년" + searchVO.getBillYm().substring(4) + "월요금정산내역_";//파일명
			String strSheetname = "정산자료";//시트명
			
			//엑셀 첫줄
			String [] strHead = {"청구번호","서비스계약번호","서비스번호","고객명","실사용자"
								,"월정액","국내음성통화료","국제로밍이용료","데이터통화료","Mobile 국제통화료"
								,"kt국제통화료","국제통화료","국제SMS이용료","기타청구금액","할인전부가세","할인금액"
								,"할인금액부가세","단말할부금","당월요금계","총청구금액"
								,"공급가액","부가세","전월청구금액","전월수납금액","요금제"};
			String [] strValue = {"ban","svcCntrNo","subscriberNo","customerLinkName","subLinkName"
								,"mavoiAmnt","rcp8002Amnt","rcp8003Amnt","rcp8004Amnt","rcp8005Amnt"
								,"rcp8006Amnt","rcp8007Amnt","rcp8008Amnt","etcAmnt","vatAmnt","discountAmnt"
								,"discountVat","eqistAmnt","invAmnt","totInvAmnt"
								,"supplyAmnt","supplyVat","lastInvAmnt","lastPymAmnt","lstRateNm"};
			int[] intWidth = {5000,5000,5000,5000,5000,5000,5000,5000,5000,5000
							,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000,5000
							,5000,5000,5000,5000};
			int[] intLen = {0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0};
			
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
				
				pRequestParamMap.put("FILE_NM"   ,file.getName());              //파일명
				pRequestParamMap.put("FILE_ROUT" ,file.getPath());              //파일경로
				pRequestParamMap.put("DUTY_NM"   ,"RCP");                       //업무명
				pRequestParamMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				pRequestParamMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				pRequestParamMap.put("menuId"    ,request.getParameter("menuId")); //메뉴ID
				pRequestParamMap.put("DATA_CNT"  ,list.size());                            //자료건수
				
				fileDownService.insertCmnFileDnldMgmtMst(pRequestParamMap);
			}
			//=======파일다운로드사유 로그 END==========================================================
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
									messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), returnMsg, "MSP1000050", "고객청구자료"))
			{
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
	
}
