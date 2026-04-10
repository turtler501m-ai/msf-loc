package com.ktis.msp.rcp.custTrnsMgmt.controller;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.ktis.msp.rcp.custTrnsMgmt.service.CustTrnsMgmtService;
import com.ktis.msp.rcp.custTrnsMgmt.vo.CustTrnsVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

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
public class CustTrnsMgmtController extends BaseController {

	/** custMgmtService */
	@Autowired
	private CustTrnsMgmtService custTrnsMgmtService;
	
	@Autowired
    private FileDownService  fileDownService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@RequestMapping(value = "/rcp/custTrnsMgmt/getCustTrnsList.do", method={POST, GET})
	public ModelAndView getCustTrnsList(@ModelAttribute("searchVO") CustTrnsVO searchVO
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> paramMap
			, ModelMap model) {
		
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
			model.addAttribute("loginInfo",loginInfo);
			model.addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.setViewName("/rcp/custTrnsMgmt/custTrnsMgmt");
			
			return modelAndView;
		}catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	@RequestMapping(value = "/rcp/custTrnsMgmt/getCustTrnsListAjax.json")
	public String getCustTrnsListAjax(@ModelAttribute("searchVO") CustTrnsVO searchVO
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> paramMap
			, ModelMap model) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			//----------------------------------------------------------------
			// 목록 db select
			//----------------------------------------------------------------
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = custTrnsMgmtService.getCustTrnsList(searchVO);
			
			resultMap = makeResultMultiRow(paramMap, resultList);

		} catch (Exception e) {
			resultMap.put("code",	messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg",	"");
			throw new MvnoErrorException(e);
		}

		//----------------------------------------------------------------
		// return json 
		//----------------------------------------------------------------    
		model.addAttribute("result", resultMap);
		
		return "jsonView";

	}		

	@RequestMapping(value = "/rcp/custTrnsMgmt/setCustTrnsAgrmAjax.json")
	public String setCustTrnsAgrm(@ModelAttribute("searchVO") CustTrnsVO searchVO
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> paramMap
			, ModelMap model){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
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
			
			custTrnsMgmtService.setCustTrnsAgrm(searchVO);

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
	
	@RequestMapping("/rcp/custTrnsMgmt/getCustTrnsListEx.json")
	public String getCustTrnsListEx(@ModelAttribute("searchVO") CustTrnsVO searchVO
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> paramMap
			, Model model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

//		String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
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
			
			List<EgovMap> list = custTrnsMgmtService.getCustTrnsListEx(searchVO);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "사업이관동의_";	//파일명
			String strSheetname = "사업이관동의";	//시트명

			String [] strHead = {"계약번호", "고객명", "서비스유형", "휴대폰번호", "계약상태", "구매유형", "동의여부", "등록일시", "상담원", "미동의처리방법", "메모"};

			String [] strValue = {"contractNum", "custNm", "serviceTypeNm", "svcTelNo", "subStatusNm", "buyTypeNm", "trnsAgrmNm", "trnsAgrmDttm", "cnslNm", "procMthdNm", "trnsMemo"};

			//엑셀 컬럼 사이즈
			int[] intWidth = {5000, 10000, 5000, 8000, 5000, 5000, 5000, 10000, 5000, 5000, 20000};


			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
			String strFileName = fileDownService.excelDownProc(strFilename, strSheetname, list, strHead, intWidth, strValue, request, response, intLen);

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
			    paramMap.put("DUTY_NM"   ,"ORG");                       //업무명
			    paramMap.put("IP_INFO"   ,ipAddr);                      //IP정보
				paramMap.put("FILE_SIZE" ,(int) file.length());         //파일크기
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("DATA_CNT", 0);                            //자료건수
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); 	//사용자ID
			    
			    fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}finally {
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
