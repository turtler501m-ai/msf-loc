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

package com.ktis.msp.voc.reuserip.controller;

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
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.voc.reuserip.service.ReuseRipMgmtService;
import com.ktis.msp.voc.reuserip.vo.ReuseRipMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2023.02.06           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2023. 02.06
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class ReuseRipMgmtController extends BaseController {
	
	/** rcpReqService */
	@Autowired
	private ReuseRipMgmtService reuseRipMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private MenuAuthService menuAuthService;
	
	@Autowired
	private FileDownService  fileDownService;

	
	@RequestMapping(value = "/voc/reuserip/getReuseRipMgmt.do")
	public ModelAndView getReuseRipMgmt(@ModelAttribute("searchVO") ReuseRipMgmtVO vo, 
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
			
			
			modelAndView.getModelMap().addAttribute("info", vo);
			modelAndView.getModelMap().addAttribute("buttonAuth", menuAuthService.buttonAuthForCRUD(request, response));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(request, response));

			modelAndView.setViewName("/voc/reuserip/reuseRipMgmt");
			
			return modelAndView;
		} catch(Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}	
	
	@RequestMapping("/voc/reuserip/getContractInfo.json")
	public String getContractInfo(HttpServletRequest request
								, HttpServletResponse response
								, ModelMap model
								, @ModelAttribute("searchVO") ReuseRipMgmtVO vo
								, @RequestParam Map<String, Object> pRequestParamMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			List<?> list = reuseRipMgmtService.getContractInfo(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	@RequestMapping("/voc/reuserip/insertReuseRipMst.json")
	public String insertChgRcptMst(HttpServletRequest request
									, HttpServletResponse response
									, ModelMap model
									, @ModelAttribute("searchVO") ReuseRipMgmtVO vo
									, @RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			reuseRipMgmtService.insertReuseRipMst(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/voc/reuserip/selectReuseRipList.json")
	public String selectReuseUsimList(HttpServletRequest request, 
			HttpServletResponse response, 
			@ModelAttribute("searchVO") ReuseRipMgmtVO vo, 
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
		
			List<?> list = reuseRipMgmtService.selectReuseRipList(vo);
			
			resultMap = makeResultMultiRow(vo, list);
			
		} catch (Exception e) {
						
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", e.getMessage());
			resultMap.put("msg", "");
			throw new MvnoErrorException(e);
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/voc/reuserip/updateReuseRipMst.json")
	public String updateReuseRipMst(HttpServletRequest request
									, HttpServletResponse response
									, ModelMap model
									, @ModelAttribute("searchVO") ReuseRipMgmtVO vo
									, @RequestParam Map<String, Object> pRequestParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(vo);
			
			reuseRipMgmtService.updateReuseRipMst(vo);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			if (!getErrReturn(e, resultMap)) {
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	@RequestMapping(value="/voc/reuserip/selectReuseRipListExcel.json")
	public String getGroupByRateReListEx(@ModelAttribute("searchVO") ReuseRipMgmtVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		FileInputStream in = null;
		OutputStream out = null;
		File file = null;
		
		try {
			
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> resultList = reuseRipMgmtService.selectReuseRipListExcel(searchVO, paramMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "명의도용주장IP차단_";	//파일명
			String strSheetname = "명의도용주장IP차단";	//시트명

			String [] strHead = {"계약번호", "고객명", "휴대폰번호", "구매유형", "업무구분", "최초요금제코드", "최초요금제", "현재요금제코드", "현재요금제", "상태", //10
					             "해지사유", "모집경로", "대리점", "개통일자", "해지일자", "유심접점", "최초유심접점", "본인인증방식", "IP상태", "등록사유", //10
					             "등록일자", "등록자", "수정일자", "수정자"}; //4
			
			String [] strValue = {"contractNum", "subLinkName", "subscriberNo" ,"reqBuyTypeNm", "operTypeNm", "fstRateCd", "fstRateNm", "lstRateCd", "lstRateNm", "subStatusNm", //10 
								  "canRsn", "onOffTypeNm", "openAgntNm", "lstComActvDate", "canDate", "usimOrgNm", "fstUsimOrgNm", "authType", "ripStatusNm", "regRsnNm", //10 
								  "regDttm", "regstNm", "rvisnDttm", "rvisnNm"}; //4
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {6000, 6000, 6000, 5000, 5000, 6000, 10000, 6000, 10000, 4000, //10
							  6000, 6000, 8000, 5000, 5000, 8000, 8000, 6000, 5000, 6000, //10
							  6000, 6000, 6000, 6000 }; //4


			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, //10
					        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, //10
					        0, 0, 0, 0}; //4


			//파일명,시트명, 조회한 리스트Vo, 해드이름, 해드 사이즈, 값
			// rqstMgmtService 함수 호출
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
			
			    paramMap.put("FILE_NM"   ,file.getName());              //파일명
			    paramMap.put("FILE_ROUT" ,file.getPath());              //파일경로
			    paramMap.put("DUTY_NM"   ,"VOC");                       //업무명
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
			resultMap.put("msg", "");
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
