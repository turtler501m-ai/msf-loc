package com.ktis.msp.rcp.rcpMgmt.controller;
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
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.rcp.rcpMgmt.service.UsimDirDlvryMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDirDlvryMgmtVO;
import com.ktis.msp.rcp.rcpMgmt.vo.UsimDlvryMgmtVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : UsimDirDlvryMgmtController.java
 * @Description : UsimDirDlvryMgmtController Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2021.05.13           최초생성
 *
 * @author 
 * @since 2021.05.13
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class UsimDirDlvryMgmtController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;

	/** usimDlvryMgmtService */
	@Autowired
	private UsimDirDlvryMgmtService usimDirDlvryMgmtService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;

	/** Constructor */
	public UsimDirDlvryMgmtController() {
		setLogPrefix("[UsimDirDlvryMgmtController] ");
	}

	
	/**
	 * 신청관리(바로배송)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/usimDirDlvryView.do")
	public ModelAndView usimDirDlvryView( @ModelAttribute("searchVO") UsimDlvryMgmtVO searchVO,
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
			
			// 본사 화면인 경우
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));

			model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
			model.addAttribute("endDt",orgCommonService.getToDay());

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtUsimDirDlvry");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}
//	
	/**
	* @Description : 유심바로배송 목록 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getUsimDirDlvryList.json")
	public String getUsimDirDlvryList(@ModelAttribute("searchVO") UsimDirDlvryMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심바로배송 목록 조회 START."));
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
			
			List<?> resultList = usimDirDlvryMgmtService.getUsimDirDlvryList(pRequestParamMap);
			
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
	* @Description : 바로배송 목록 엑셀 다운로드
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping("/rcp/rcpMgmt/getUsimDirDlvryListByExcel.json")
	public String getUsimDlvryListByExcel(@ModelAttribute("searchVO") UsimDirDlvryMgmtVO searchVO, 
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
			
			List<?> resultList = usimDirDlvryMgmtService.getUsimDirDlvryListByExcel(pRequestParamMap);
			
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "유심바로배송목록_";//파일명
			String strSheetname = "유심바로배송목록";//시트명
			
			String [] strHead = {
					"주문번호", "kt주문번호", "업체명", "고객명", "메모", "배송우편번호", "배송주소", "연락처", "유심종류", "배송요청사항", // 10
					"신청일자", "완료일자", "진행상태", "신청상태", "결제상태", "신청경로", "예약번호", "재접수업체명", "재접수여부", "법정대리인",
					"수정자", "수정일자", "결제취소상태", "결제취소실패사유", "결제취소자", "결제취소일"};
			
			String [] strValue = {
					"selfDlvryIdx", "ktOrdId" ,"bizOrgNm", "cstmrName", "selfMemo", "dlvryPost", "dlvryPostAddr", "dlvryTel", "usimProdNm", "dlvryMemo", 
					"sysRdate", "cplDate", "dlvryStateNm", "selfStateNm", "payState","reqTypeNm", "resNo", "reBizOrgNm", "reAcceptYn" , "minorAgentName",
					"rvisnNm", "rvisnDttm", "cnclYnStat", "cnclFailRsn", "cnclNm", "cnclDt"};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {
					5000, 5000 ,4000, 4000, 20000, 5000, 20000, 4000, 4000, 10000, 
					6000, 6000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 4000,
					4000, 6000, 5000, 10000, 5000, 5000};
			int[] intLen = {
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
					0, 0, 0, 0, 0, 0};
			
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
	* @Description : 바로배송 정보 수정
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/setUsimDirDlvryInfo.json")
	public String setUsimDlvryInfo(@ModelAttribute("usimDirDlvryMgmtVO") UsimDirDlvryMgmtVO usimDirDlvryMgmtVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심배송 정보 수정 START."));
		logger.info(generateLogMsg("================================================================="));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(usimDirDlvryMgmtVO);
			
			// 본사 권한
			if(!"10".equals(usimDirDlvryMgmtVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			/** 20230518 PMD 조치 */
			if (usimDirDlvryMgmtVO.getSelfDlvryIdx() == null || "".equals(usimDirDlvryMgmtVO.getSelfDlvryIdx()) ) {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", "주문번호는 필수 항목입니다.");
			} else {
				usimDirDlvryMgmtService.setUsimDirDlvryInfo(usimDirDlvryMgmtVO);
	 			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			}
			
		} catch (Exception e) {
			resultMap.clear();
			if ( ! getErrReturn(e, (Map<String, Object>) resultMap))
			{
			    throw new MvnoErrorException(e);
			}
		}
		logger.debug("resultMap ==  " + resultMap);
		model.addAttribute("result", resultMap);
		return "jsonView";
		
	}
	
	
	/**
	 * 바로배송 주문취소 및 변경 mplatform 호출
	 */
	@RequestMapping("/rcp/rcpMgmt/dvryServiceCall.json")
	public String dvryServiceCall(@ModelAttribute("searchVO") UsimDirDlvryMgmtVO searchVO
									,HttpServletRequest request
									,HttpServletResponse response
									,@RequestParam Map<String, Object> paramMap
									,ModelMap model) 
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
			searchVO.setAppEventCd("D03");  //D03 배송취소
			searchVO.setJobGubun("D"); // D 취소
			searchVO.setDlvryAdrType("2"); // addr 타입
			searchVO.setMrktYn("Y"); // 개인정보이용동의
			searchVO.setDvryResYn("N");
			searchVO.setSelfStateCode("02");     //신청서 상태 => 접수취소
			
			HashMap<String,String> param = new HashMap<String,String>();
			
			//param
			param.put("appEventCd", searchVO.getAppEventCd()); // AppEventCd
			param.put("jobGubun", searchVO.getJobGubun()); // job_gubun
        	param.put("ktOrderId", searchVO.getKtOrdId());  // kt_ord_id 
        	
        	//2021년 6월 22일 접수취소 파라미터 삭제
        	/*param.put("orderRcvTlphNo", searchVO.getDlvryTel()); // 수령고객연락처
        	param.put("zipNo", searchVO.getDlvryPost());// 우편번호
        	param.put("addrTypeCd", searchVO.getDlvryAdrType()); // 주소유형
        	param.put("targetAddr1", searchVO.getDlvryAddr()); // 기본주소
        	param.put("targetAddr2", searchVO.getDlvryAddrDtl()); // 상세주소
        	param.put("custInfoAgreeYn", searchVO.getMrktYn()); // 개인정보제공동의여부
        	param.put("rsvOrderYn", searchVO.getDvryResYn()); // 배달예약여부
        	param.put("orderReqMsg", searchVO.getDvryReqMemo());  //배달요청메세지
        	param.put("acceptTime", searchVO.getAcceptTime());  //접수가능시간
        	param.put("rsvOrderDt", searchVO.getDvryHpTime());  //배달희망시간
        	param.put("usimAmt", searchVO.getUsimAmt());  //유심금액
*/			
			logger.info("dvryServiceCall START ================================");
			logger.info("이벤트코드=" + searchVO.getAppEventCd());
			logger.info("dvryServiceCall END   ================================");
			
			param.put("USER_ID" ,  loginInfo.getUserId());
			
			HashMap<String, Object> map = (HashMap<String, Object>)usimDirDlvryMgmtService.dvryServiceCall(param, searchVO , 3000);
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			
			 if("S".equals(map.get("code"))){
					resultMap.put("selfStateCode", searchVO.getSelfStateCode());
					resultMap.put("msg", "[" + map.get("code") + "]" +"정상적으로 처리 되었습니다.");
				}else{
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
					resultMap.put("msg", "[" + map.get("code") + "]" + map.get("msg"));
					
				}

			 
		}catch(Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg","MPLATFORM 연동 지연으로 인해 잠시 후 이용바랍니다.");
		}
		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

//	
	/**
	* @Description : 바로배송 상담이력 조회
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getUsimDirDlvryHst.json")
	public String getUsimDirDlvryHst(@ModelAttribute("searchVO") UsimDirDlvryMgmtVO searchVO,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> pRequestParamMap,
			ModelMap model)
	{		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심바로배송 상담이력 조회 START."));
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
			
			List<?> resultList = usimDirDlvryMgmtService.getUsimDirDlvryHst(pRequestParamMap);
			
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
	
}
