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


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoErrorException;
import com.ktis.msp.base.exception.MvnoRunException;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.base.login.LoginInfo;
import com.ktis.msp.base.mvc.BaseController;
import com.ktis.msp.cmn.btchmgmt.serivce.BtchMgmtService;
import com.ktis.msp.cmn.btchmgmt.vo.BatchJobVO;
import com.ktis.msp.cmn.filedown.service.FileDownService;
import com.ktis.msp.cmn.login.service.LoginService;
import com.ktis.msp.cmn.login.service.MenuAuthService;
import com.ktis.msp.org.common.service.OrgCommonService;
import com.ktis.msp.org.userinfomgmt.service.UserInfoMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.NstepCallService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpMgmtService;
import com.ktis.msp.rcp.rcpMgmt.service.RcpNewMgmtService;
import com.ktis.msp.rcp.rcpMgmt.vo.NstepQueryVo;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpCommonVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpDetailVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO;
import com.ktis.msp.rcp.rcpMgmt.vo.RcpRateVO;
import com.ktis.msp.util.ExcelFilesUploadUtil;

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
public class RcpMgmtController extends BaseController {

	@Autowired
	private OrgCommonService orgCommonService;

	/** rcpMgmtService */
	@Autowired
	private RcpMgmtService rcpMgmtService;

	@Autowired
	private NstepCallService nStepCallService;

	/** propertiesService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired
	private MenuAuthService  menuAuthService;

	@Autowired
	private FileDownService  fileDownService;
	
	@Autowired
	private BtchMgmtService btchMgmtService;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private UserInfoMgmtService userInfoMgmtService;
	
	@Autowired
	private RcpNewMgmtService rcpNewMgmtService;
	
	/** Constructor */
	public RcpMgmtController() {
		setLogPrefix("[RcpMgmtController] ");
	}

	/**
	 * 신청관리 목록조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/rcpListAjax.json")
	public String getRcpList(@ModelAttribute("searchVO") RcpListVO searchVO,
						HttpServletRequest request,
						HttpServletResponse response,
						@RequestParam Map<String, Object> paramMap,
						ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//2015-02-27 필수값 체크
		if(paramMap.isEmpty()){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg","필수정보가 없습니다.");
			model.addAttribute("result", resultMap);
			return "jsonView";
		}

		try{
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(paramMap);
			
			int typeCd = Integer.parseInt(loginInfo.getUserOrgnTypeCd());
			if(typeCd >= 20){
				if(typeCd == 20){
					searchVO.setCntpntShopId(loginInfo.getUserOrgnId());
				} else if(typeCd == 30){
					searchVO.setpAgentCode(loginInfo.getUserOrgnId());
				}
			}
	
			List<EgovMap> rcpList = rcpMgmtService.getRcpList(searchVO, paramMap);
			
			//20250715 데이터 쉐어링 공통코드 조회
			List<EgovMap> sharingList = rcpNewMgmtService.getSharingList("CMN0285");
			String socCode = (String) rcpList.get(0).get("socCode");
			String sharingYn = "N";
			for(int i =0; i < sharingList.size(); i++){
				if(socCode.equals(sharingList.get(i).get("cdVal"))){
					sharingYn = "Y";
				}
			}
	
			resultMap.put("sharingYn", sharingYn);
			resultMap.put("data", rcpList.get(0));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		}catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
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
	
	@RequestMapping("/rcp/rcpMgmt/getRcpCode.do")
	//public @ResponseBody String getRcpCodeXml(HttpServletRequest request,
	public void getRcpCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, Object> paramMap,
			ModelMap model) {

		StringBuffer sb = new StringBuffer();
		/** 20230518 PMD 조치 */
		PrintWriter out =null; 
		
		/* 매출항목관리 목록*/
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			List<?> getRcpCodeList = rcpMgmtService.getRcpCodeList();
			//

			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<data>\n");

			for(int i=0; i<getRcpCodeList.size(); i++) {

				HashMap<String, String> map = (HashMap<String, String>) getRcpCodeList.get(i);

				if(map.get("ITEM_CD") == null || "".equals(map.get("ITEM_NM"))) {
					sb.append("<item value=\"\" label=\"" + map.get("ITEM_NM") + "\" />\n");
				} else {
					sb.append("<item value=\""+ map.get("ITEM_CD")+"\" label=\"" + map.get("ITEM_NM") + "\" />\n");
				}
				// <item value="6" label="amoeba"/>
			}

			sb.append("</data>");

			response.setContentType("application/xml; charset=UTF-8");

			out = response.getWriter();
			out.write(sb.toString());
		} catch (Exception e) {
			throw new MvnoErrorException(e);
		}finally{
			out.close();
		}
	}

	@RequestMapping("/rcp/rcpMgmt/getRcpCommon.json")
	public String getRcpCommon(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("searchVO") RcpCommonVO searchVO, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(searchVO);
			
			if (searchVO.getGrpId() == null || searchVO.getGrpId().isEmpty()) {
//              throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				resultMap.clear();
				resultMap.put("code", "SESSION_FINISH" );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				model.addAttribute("result", resultMap);

				return "jsonView";
			}

			List<?> resultList = rcpMgmtService.getRcpCommon(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			} 
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	
	//제품가져오기
	@RequestMapping("/rcp/rcpMgmt/getRcpSimPrdtInfo.json")
	public String getRcpSimPrdtInfo(HttpServletRequest paramReq, HttpServletResponse paramRes, @ModelAttribute("searchVO") RcpCommonVO searchVO, ModelMap model) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(paramReq, paramRes);
			loginInfo.putSessionToVo(searchVO);
			
			if (searchVO.getGrpId() == null || searchVO.getGrpId().isEmpty()) {
//              throw new MvnoRunException(-1, messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				resultMap.clear();
				resultMap.put("code", "SESSION_FINISH" );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.GENERAL_ERROR_MSG", null, Locale.getDefault()));
				model.addAttribute("result", resultMap);

				return "jsonView";
			}

			List<?> resultList = rcpMgmtService.getRcpSimPrdtInfo(searchVO);

			resultMap =  makeResultMultiRow(searchVO, resultList);
		}
		catch(Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, paramReq.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			} 
		}

		model.addAttribute("result", resultMap);

		return "jsonView";
	}
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpRate.do", method={POST, GET})
	public String getRcpRate(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap){
		
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			
			return "/rcp/rcpMgmt/rcpRate";
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
		
	}

	@RequestMapping("/rcp/rcpMgmt/getRcpRateList.json")
	public String getRcpRateList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("rcpRateVO") RcpRateVO rcpRateVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){
		
		Map<String, Object> resultMap = null;
		try {
			// 로그인체크
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(rcpRateVO);
			
			List<?> resultList = rcpMgmtService.getRcpRateList(rcpRateVO);

			resultMap =  makeResultMultiRow(pReqParamMap, resultList);

			model.addAttribute("result", resultMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			
			//v2018.11 PMD 적용 소스 수정
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		return "jsonView";
	}

	@RequestMapping("/rcp/rcpMgmt/updateRcpDetail.json")
	public String updatetRcpDetail(HttpServletRequest pRequest
									, HttpServletResponse pResponse
									, RcpDetailVO rcpDetailVO
									, ModelMap model
									, @RequestParam Map<String, Object> pReqParamMap
									, SessionStatus status)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);
			rcpDetailVO.setRid(loginInfo.getUserId());
			rcpDetailVO.setRip(pRequest.getRemoteAddr());
			rcpDetailVO.setShopUsmId(loginInfo.getUserId());
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			int returnCnt = rcpMgmtService.updateRcpDetail(rcpDetailVO);
			if ( returnCnt == 0 )
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
				resultMap.put("msg", messageSource.getMessage("ktis.msp.rtn_code.NO_EXIST", null, Locale.getDefault()) );
			}else
			{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
				resultMap.put("msg", "");
			}

			logger.info(generateLogMsg("수정 건수 = " + returnCnt));

		} catch ( Exception e) {
			/*e.printStackTrace();
			if ( ! getErrReturn(e, resultMap)) {
				throw new MvnoRunException(-1, "가입신청 등록/수정 실패");
			}*/

			resultMap.clear();


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
	 * N-STEP 전송
	 */
	@RequestMapping("/rcp/rcpMgmt/updateRcpSend.json")
	public String updateRcpSend(HttpServletRequest pRequest, HttpServletResponse pResponse, RcpDetailVO rcpDetailVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap, SessionStatus status)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, String> resultNstepMap = new HashMap<String, String>();

		String requestKey = "";
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToVo(rcpDetailVO);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			if(!StringUtils.isEmpty(rcpDetailVO.getRequestKey())){
				requestKey = rcpDetailVO.getRequestKey();
				//2015-02-27 필수값 체크
				if(requestKey==null || "".equals(requestKey)){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","필수정보가 없습니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
				boolean isScan = nStepCallService.isScanFile(requestKey);
				if(!isScan){
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
					resultMap.put("msg","스캔 서식지 정보가 없습니다. 서식지 스캔후 NSTEP 전송이 가능 합니다.");
					model.addAttribute("result", resultMap);
					return "jsonView";
				}
			}
			else{
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","request_key 정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}


			/**개발기*/
			//String infNstepUrl = "http://112.175.98.116:8180/nStep/serviceCall.do";
			/**운영기*/
			//String infNstepUrl = "http://128.134.37.215:8180/nStep/serviceCall.do";
			String infNstepUrl = propertiesService.getString("nStep.prx.url");
			NstepQueryVo nStepVo = new NstepQueryVo();

			nStepVo = nStepCallService.getNstepCallData(requestKey);
			
			logger.debug("**************************************************");
			logger.debug("nStepVo=" + nStepVo);
			logger.debug("**************************************************");

			try {
				nStepVo = nStepCallService.decryptDBMS(nStepVo);
			} catch (Exception e) {
				throw new MvnoErrorException(e);
			}

			resultNstepMap =  nStepCallService.infNstepCall(infNstepUrl, nStepVo);

			if (resultNstepMap.get("code").equals("00")) {
				int returnCnt = rcpMgmtService.updateRcpSend(rcpDetailVO);
				if (returnCnt>0) {
					resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
					resultMap.put("msg","N-STEP 전송이 완료되었습니다.");
				}
			} else {
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg",resultNstepMap.get("msg"));
			}
		} catch (Exception e) {
			
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()), 
					e.getMessage(), "MSP1000014", "신청등록"))
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
	 * 판매정책 팝업화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpPlc.do", method={POST, GET})
	public ModelAndView getRcpPlcForm( @ModelAttribute("searchVO") RcpListVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
			)
	{
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(pRequestParamMap.get("sOrgnId"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String sOrgnNm = URLDecoder.decode(pRequest.getParameter("sOrgnNm") ,"UTF-8");

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			model.addAttribute("sOrgnNm", sOrgnNm);

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpPlc");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}

	/**
	 * 판매정책조회
	 */
	@RequestMapping("/rcp/rcpMgmt/getRcpPlc.json")
	public String getRcpPlc(HttpServletRequest pRequest, HttpServletResponse pResponse, 
			@ModelAttribute("rcpListVO") RcpListVO rcpListVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<?> resultList = new ArrayList<RcpListVO>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpListVO);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpListVO.getSessionUserOrgnTypeCd()) && !rcpListVO.getSessionUserOrgnId().equals(rcpListVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultList = rcpMgmtService.getRcpPlc(rcpListVO);

			resultMap =  makeResultMultiRow(pReqParamMap, resultList);

		} catch (Exception e) {
			resultMap.clear();
			resultList.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, pRequest.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}

		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	@RequestMapping("/rpc/rcpMgmt/comboList.json")
	public String selectMnfctList(HttpServletRequest pRequest, HttpServletResponse pResponse, RcpListVO rcpListVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//2015-02-27 필수값 체크
		if(pReqParamMap.isEmpty()){
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg","필수정보가 없습니다.");
			model.addAttribute("result", resultMap);
			return "jsonView";
		}
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpListVO);
			
			List<?> resultList = rcpMgmtService.comboList(rcpListVO);

			resultMap = makeResultMultiRow(pReqParamMap, resultList);

			logger.debug(">>>>>>>>>>>>>> result:" + resultMap);
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
	 * 단말재고확인
	 */
	@RequestMapping("/rcp/rcpMgmt/checkPhoneSN.json")
	public String checkPhoneSN(HttpServletRequest request, HttpServletResponse response,
			RcpDetailVO rcpListVO, ModelMap model, @RequestParam Map<String, Object> pReqParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(rcpListVO);
			
			//2015-02-27 필수값 체크
			if(     rcpListVO.getReqPhoneSn() == null || "".equals(rcpListVO.getReqPhoneSn()) ||    // 단말기일련번호
					rcpListVO.getPrdtId() == null || "".equals(rcpListVO.getPrdtId()) ||            // 단말모델코드
					rcpListVO.getCntpntShopId() == null || "".equals(rcpListVO.getCntpntShopId())   // 조직ID
					){
				resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
				resultMap.put("msg","필수정보가 없습니다.");
				model.addAttribute("result", resultMap);
				return "jsonView";
			}
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpListVO.getSessionUserOrgnTypeCd()) && !rcpListVO.getSessionUserOrgnId().equals(rcpListVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			resultMap.put("result", rcpMgmtService.checkPhoneSN(rcpListVO));
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
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
	 * 신청관리(링커스)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpListLinkus.do")
	public ModelAndView getRcpListLinkus( @ModelAttribute("searchVO") RcpListVO searchVO,
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
			modelAndView.getModelMap().addAttribute("maskingYn", loginService.getUsrMskYn(loginInfo.getUserId()));

			model.addAttribute("strtDt",orgCommonService.getWantDay(-7));
			model.addAttribute("endDt",orgCommonService.getToDay());

			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtLinkus");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 신청관리(링커스) 조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtListLinkus.json")
	public String getRcpMgmtListLinkus(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			
			List<?> rcpList = rcpMgmtService.getRcpMgmtListLinkus(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSPLNKS010", "신청정보(링커스)"))
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
	 * 신청관리(링커스) 상세조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtListLinkusDtl.json")
	public String getRcpMgmtListLinkusDtl(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			
			List<?> rcpList = rcpMgmtService.getRcpMgmtListLinkusDtl(paramMap);

			resultMap = makeResultMultiRow(searchVO, rcpList);

		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSPLNKS010", "신청정보(링커스)"))
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
	 * 신청관리(링커스) 엑셀다운로드 
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/rcpMgmtListLinkusExcel.json")
	public String getRcpMgmtListLinkusExcel(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			excelMap.put("MENU_NM","신청관리(링커스)");
			String searchVal = "신청일자:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|대리점:"+searchVO.getAgntCd()+
					"|서비스구분:"+searchVO.getServiceType()+
					"|검색구분:["+searchVO.getSearchCd()+","+searchVO.getSearchVal()+"]"+
					"|업무구분:"+searchVO.getpOperType()+
					"|진행상태:"+searchVO.getRequestStateCode()+
					"|구매유형:"+searchVO.getReqBuyType()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			// 본사 화면인 경우
			if(!"10".equals(paramMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			BatchJobVO vo = new BatchJobVO();
			
			vo.setExecTypeCd("REQ");
			vo.setBatchJobId("BATCH00181");
			vo.setSessionUserId(loginInfo.getUserId());	
			vo.setExclDnldId(exclDnldId); // 엑셀다운로드ID
			//admin 권한  DEV 권한 없으면 exclDnldYn +1 처리
			if(!userInfoMgmtService.isAdminGroupUser(loginInfo.getUserId()) && !userInfoMgmtService.isDevGroupUser(loginInfo.getUserId())) 
				vo.setExclDnldYn(vo.getExclDnldYn()+1);
			
			String ipAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
			
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getHeader("REMOTE_ADDR");
		   
			if(ipAddr == null || ipAddr.length() == 0 || ipAddr.equalsIgnoreCase("unknown"))
				ipAddr = request.getRemoteAddr();
			
			vo.setExecParam("{\"strtDt\":" + "\"" + searchVO.getStrtDt() + "\""
					+ ",\"endDt\":" + "\"" + searchVO.getEndDt() + "\"" 
					+ ",\"agntCd\":" + "\"" + searchVO.getAgntCd() + "\"" 
					+ ",\"serviceType\":" + "\"" + searchVO.getServiceType() + "\"" 
					+ ",\"searchCd\":" + "\"" + searchVO.getSearchCd() + "\"" 
					+ ",\"searchVal\":" + "\"" + searchVO.getSearchVal() + "\"" 
					+ ",\"pOperType\":" + "\"" + searchVO.getpOperType() + "\"" 
					+ ",\"pAgentCode\":" + "\"" + searchVO.getpAgentCode() + "\"" 
					+ ",\"requestStateCode\":" + "\"" + searchVO.getRequestStateCode() + "\"" 
					+ ",\"reqBuyType\":" + "\"" + searchVO.getReqBuyType() + "\""
					+ ",\"usimKindsCd\":" + "\"" + searchVO.getUsimKindsCd() + "\""
					+ ",\"userId\":" + "\"" + loginInfo.getUserId() + "\"" 
					+ ",\"dwnldRsn\":" + "\"" + request.getParameter("DWNLD_RSN") + "\"" 
					+ ",\"ipAddr\":" + "\"" + ipAddr + "\"" 
					+ ",\"menuId\":" + "\"" + request.getParameter("menuId") + "\"" 
					+ ",\"exclDnldId\":" + "\"" + exclDnldId + "\"}" 
					
				);
			
			btchMgmtService.insertBatchRequest(vo);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");

		} catch (Exception e) {
			resultMap.clear();
			   if (!getErrReturn(e, resultMap))
			    throw new MvnoErrorException(e);
		} 

		//----------------------------------------------------------------
		// return json
		//----------------------------------------------------------------
		model.addAttribute("result", resultMap);
		return "jsonView";
	}

	/**
	 * 신청관리(링커스) 배송정보 저장
	 */
	@RequestMapping("/rcp/rcpMgmt/updateRcpDetailLinkus.json")
	public String updateRcpDetailLinkus(HttpServletRequest pRequest,
				HttpServletResponse pResponse,
				RcpDetailVO rcpDetailVO,
				ModelMap model,
				@RequestParam Map<String, Object> pParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//---------------------------------------------------------------
			// 신청등록, 신청관리(링커스), 신청관리(렌탈) 중복 수정 방지.
			//---------------------------------------------------------------
//			RcpMgmtVO rcpMgmt = new RcpMgmtVO();
//			rcpMgmt.setRequestKey(String.valueOf(pParamMap.get("requestKey")));
//			rcpMgmt.setSysRdate(String.valueOf(pParamMap.get("sysRdate")));
//			if("".equals(rcpMgmtService.chkMcpRequest(rcpMgmt))) {
//				throw new Exception("문구 수정 해야함.");
//			}
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pParamMap);
			pParamMap.put("maskingYn", String.valueOf(loginService.getUsrMskYn(loginInfo.getUserId())));
			
			
			// 본사 화면인 경우
			if(!"10".equals(pParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rcpMgmtService.updateRcpDetailLinkus(pParamMap);

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
	 * 스캔ID 생성
	 */
	@RequestMapping("/rcp/rcpMgmt/getMcpRequestScanId.json")
	public String getMcpRequestScanId( HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam HashMap<String, String> reqParam) {

		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(reqParam);
			
			String scanId = rcpMgmtService.getMcpRequestScanId(reqParam);
			
			resultMap.put("scanId", scanId);
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");

		} catch (Exception e) {
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					"", "MSP1000014", "신청등록"))
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
	 * 신청관리(렌탈)
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtRental.do")
	public ModelAndView getRcpMgmtRental( @ModelAttribute("searchVO") RcpListVO searchVO,
					ModelMap model,
					HttpServletRequest pRequest,
					HttpServletResponse pResponse,
					@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			
			// 본사 화면인 경우
//			if(!"10".equals(loginInfo.getUserOrgnTypeCd())){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
			
			modelAndView.getModelMap().addAttribute("orgnInfo",rcpMgmtService.orgnInf(loginInfo.getUserOrgnId()));
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));

			modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpMgmtRental");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}

	}

	/**
	 * 신청관리(렌탈) 목록조회
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtRentalList.json")
	public String getRcpMgmtRentalList(@ModelAttribute("searchVO") RcpListVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		//--------------------------------------
		// return JSON 변수 선언
		//--------------------------------------
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = rcpMgmtService.getRcpMgmtRentalList(paramMap);

			resultMap = makeResultMultiRow(searchVO, list);
		} catch (Exception e) {
			

			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), 
					messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()), 
					e.getMessage(), "MSP1010020", "신청관리(렌탈)"))
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
	 * 신청관리(렌탈) 엑셀다운로드
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getRcpMgmtRentalListExcel.json")
	public String rcpMgmtRentalListExcel(@ModelAttribute("searchVO") RcpListVO searchVO,
					HttpServletRequest request,
					HttpServletResponse response,
					@RequestParam Map<String, Object> paramMap,
					ModelMap model)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//String returnMsg = null;
		FileInputStream in = null;
		OutputStream out = null;
		File file = null;

		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(paramMap);
			loginInfo.putSessionToVo(searchVO);
			
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
			excelMap.put("MENU_NM","신청관리(렌탈)");
			String searchVal = "신청일자:"+searchVO.getStrtDt()+"~"+searchVO.getEndDt()+
					"|대리점:"+searchVO.getOrgnId()+
					"|검색구분:["+searchVO.getSearchCd()+","+searchVO.getSearchVal()+"]"+
					"|진행상태:"+searchVO.getRequestStateCode()
					;
			if(searchVal.length() > 500) {
				searchVal = searchVal.substring(0, 500);
			}
			excelMap.put("SEARCH_VAL",searchVal);
			fileDownService.insertCmnExclDnldHst(excelMap);
			excelMap.clear();
			
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd()) && !searchVO.getSessionUserOrgnId().equals(searchVO.getOrgnId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			List<?> list = rcpMgmtService.getRcpMgmtRentalListExcel(paramMap);

			String serverInfo = propertiesService.getString("excelPath");

			String strFilename = serverInfo  + "신청관리_";//파일명
			String strSheetname = "신청관리";//시트명

			//엑셀 첫줄
			String [] strHead = {"대리점","예약번호","고객명","생년월일","제품명","제품ID","단말일련번호","유심일련번호","업무구분","휴대폰번호",
								 "요금제","배송지우편번호","배송지주소","배송휴대폰번호","유선연락처","신청일자","진행상태","수정자","수정일자"};	//19

			String [] strValue = {"agentName","resNo","cstmrNameMask","birthDt","modelName","prdtId","reqPhoneSnMask","reqUsimSnMask","operName","cstmrMobile",
								  "socName","dlvryPost","fullDlvryAddr","dlvryMobile","cstmrTel","reqInDt","requestStateName","rvisnNm","rvisnDttm"};	//19

			//엑셀 컬럼 사이즈
			int[] intWidth = {10000, 5000, 10000, 5000, 10000, 5000, 5000, 10000, 5000, 5000,
							  5000, 5000, 10000, 5000, 5000, 5000, 5000, 5000, 5000};

			int[] intLen = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
							0, 0, 0, 0, 0, 0, 0, 0, 0};

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
				paramMap.put("menuId", request.getParameter("menuId")); //메뉴ID
				paramMap.put("DATA_CNT", list.size());                  //자료건수
				paramMap.put("DWNLD_RSN", request.getParameter("DWNLD_RSN")); //사유
				paramMap.put("SESSION_USER_ID", loginInfo.getUserId()); //사용자ID
				paramMap.put("EXCL_DNLD_ID", exclDnldId);

				fileDownService.insertCmnFileDnldMgmtMst(paramMap);
			}
			//=======파일다운로드사유 로그 END==========================================================

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "다운로드성공");
		} catch (Exception e) {
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NG", null, Locale.getDefault()) );
			resultMap.put("msg", "");
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
	 * MCP_REQUEST 수정 여부 판단.
	 */
//	@RequestMapping("/rcp/rcpMgmt/chkMcpRequest.json")
//	public String chkMcpRequest( HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam HashMap<String, String> reqParam) {
//
//		//--------------------------------------
//		// return JSON 변수 선언
//		//--------------------------------------
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		try {
//			//----------------------------------------------------------------
//			// Login check
//			//----------------------------------------------------------------
//			LoginInfo loginInfo = new LoginInfo(request, response);
//			loginInfo.putSessionToParameterMap(reqParam);
//			
//			RcpMgmtVO rcpMgmt = new RcpMgmtVO();
//			rcpMgmt.setRequestKey(String.valueOf(reqParam.get("requestKey")));
//			
//			String strSysRdate = rcpMgmtService.chkMcpRequest(rcpMgmt);
//			
//			resultMap.put("sysRdate", strSysRdate);
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
//			resultMap.put("msg", "");
//
//		} catch (Exception e) {
//			logger.error(e);
//			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
//			resultMap.put("msg", "");
//		}
//
//		//----------------------------------------------------------------
//		// return json
//		//----------------------------------------------------------------
//		model.addAttribute("result", resultMap);
//
//		return "jsonView";
//	}
	
	/**
	 * 신청관리(렌탈) 배송정보 저장
	 */
	@RequestMapping("/rcp/rcpMgmt/updateRcpDetailRental.json")
	public String updateRcpDetailRental(HttpServletRequest pRequest,
				HttpServletResponse pResponse,
				RcpDetailVO rcpDetailVO,
				ModelMap model,
				@RequestParam Map<String, Object> pParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			//---------------------------------------------------------------
			// 신청등록, 신청관리(링커스), 신청관리(렌탈) 중복 수정 방지.
			//---------------------------------------------------------------
//			RcpMgmtVO rcpMgmt = new RcpMgmtVO();
//			rcpMgmt.setRequestKey(String.valueOf(pParamMap.get("requestKey")));
//			rcpMgmt.setSysRdate(String.valueOf(pParamMap.get("sysRdate")));
//			if("".equals(rcpMgmtService.chkMcpRequest(rcpMgmt))) {
//				throw new Exception("문구 수정 해야함.");
//			}
			
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pParamMap);
			loginInfo.putSessionToVo(rcpDetailVO);
			
//			// 본사 화면인 경우
//			if(!"10".equals(pParamMap.get("SESSION_USER_ORGN_TYPE_CD"))){
//				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
//			}
			
			// 본사 권한자가 아닌 경우 조직ID 조건 체크 ( 조직ID 가 다른 경우 오류 처리 )
			if(!"10".equals(rcpDetailVO.getSessionUserOrgnTypeCd()) && !rcpDetailVO.getSessionUserOrgnId().equals(rcpDetailVO.getCntpntShopId())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			rcpMgmtService.updateRcpDetailLinkus(pParamMap);
			
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
	
	// 개통이력조회팝업
	@RequestMapping(value = "/rcp/rcpMgmt/getCheckCstmr.do")
	public ModelAndView getCheckCstmr( ModelMap model, 
										HttpServletRequest pRequest, 
										HttpServletResponse pResponse, 
										@RequestParam Map<String, Object> pRequestParamMap)
	{
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);
			loginInfo.putSessionToParameterMap(pRequestParamMap);
			
			modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
			modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
			//----------------------------------------------------------------
			// jsp 지정
			//----------------------------------------------------------------
			modelAndView.setViewName("/rcp/rcpMgmt/rcpCstmr");
			
			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 개통이력확인
	 */
	@RequestMapping("/rcp/rcpMgmt/getCheckCstmr.json")
	public String getCheckCstmr(HttpServletRequest request
							, HttpServletResponse response
							, RcpDetailVO searchVO
							, ModelMap model
							, @RequestParam Map<String, Object> pReqParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			resultMap.put("result", rcpMgmtService.getCheckCstmr(searchVO));
			
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
		} catch (Exception e) {
			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.NOK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			}
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 개통이력확인
	 */
	@RequestMapping("/rcp/rcpMgmt/getCheckCstmrList.json")
	public String getCheckCstmrList(HttpServletRequest request
							, HttpServletResponse response
							, RcpDetailVO searchVO
							, ModelMap model
							, @RequestParam Map<String, Object> pReqParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			//----------------------------------------------------------------
			// Login check
			//----------------------------------------------------------------
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			loginInfo.putSessionToVo(searchVO);
			
			List<?> list = rcpMgmtService.getCheckCstmrList(searchVO);
			
			resultMap = makeResultMultiRow(searchVO, list);
			
		} catch (Exception e) {
			resultMap.clear();
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1000014", "신청등록"))
			{
				throw new MvnoErrorException(e);
			} 			
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
	
	/**
	 * 사전인증조회 화면
	 */
	@RequestMapping(value = "/rcp/rcpMgmt/getPreAuthList.do")
	public ModelAndView getPreAuthList( @ModelAttribute("searchVO") RcpListVO searchVO,
			ModelMap model,
			HttpServletRequest pRequest,
			HttpServletResponse pResponse,
			@RequestParam Map<String, Object> pRequestParamMap
			)
	{
		logger.info(generateLogMsg("================================================================="));
        logger.info(generateLogMsg(" 사전 인증 조회 화면 START."));
        logger.info(generateLogMsg("================================================================="));
        
        ModelAndView modelAndView = new ModelAndView();
        
        LoginInfo loginInfo = new LoginInfo(pRequest, pResponse);

        try {
    		modelAndView.getModelMap().addAttribute("loginInfo",loginInfo);
            modelAndView.getModelMap().addAttribute("buttonAuth",menuAuthService.buttonAuthForCRUD(pRequest, pResponse));
            modelAndView.getModelMap().addAttribute("breadCrumb", menuAuthService.breadCrumb(pRequest, pResponse));
        
			modelAndView.setViewName("/rcp/rcpMgmt/rcpPreAuthList");

			return modelAndView;
		} catch (Exception e) {
			throw new MvnoRunException(-1, "");
		}
	}
	
	/**
	 * 사전인증조회
	 */
	@RequestMapping("/rcp/rcpMgmt/selectPreAuthList.json")
	public String selectPreAuthList(HttpServletRequest request, HttpServletResponse response, RcpDetailVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 사전 인증 조회 START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	
	        searchVO.setSessionUserId(loginInfo.getUserId());
	        
			@SuppressWarnings("unchecked")
			List<EgovMap> resultList = ((List<EgovMap>) rcpMgmtService.selectPreAuthList(searchVO, pRequestParamMap));
			resultMap =  makeResultMultiRow(searchVO, resultList);
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
		    resultMap.clear();
		    
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1050001", "사전인증정보"))
			{
				throw new MvnoErrorException(e);
			}
		}

		return "jsonView";
	}
	

	/**
	 * 사전 인증 조회 화면 신청서 메모 수정
	 */
	@RequestMapping("/rcp/rcpMgmt/updatePreAuthMemo.json")
	public String updatePreAuthMemo(HttpServletRequest request, HttpServletResponse response, RcpDetailVO searchVO, ModelMap model,@RequestParam Map<String, Object> pRequestParamMap){
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg(" 사전 인증 조회 화면 신청서 메모 수정  START"));
		logger.info(generateLogMsg("================================================================="));
		
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
	        LoginInfo loginInfo = new LoginInfo(request, response);
	        loginInfo.putSessionToParameterMap(pRequestParamMap);
	
	        searchVO.setSessionUserId(loginInfo.getUserId());
	        
			rcpMgmtService.updatePreAuthMemo(searchVO, pRequestParamMap);

			resultMap.put("code", messageSource.getMessage("ktis.msp.rtn_code.OK", null, Locale.getDefault()) );
			resultMap.put("msg", "");
			
			model.addAttribute("result", resultMap);
		} catch (Exception e) {
		    resultMap.clear();
		    
			if (!getErrReturn(e, (Map<String, Object>) resultMap, request.getServletPath(), "", "", "MSP1050001", "사전인증정보"))
			{
				throw new MvnoErrorException(e);
			}
		}

		return "jsonView";
	}	
	/**
	 * 2020.12.10
	 * 유심일련번호 엑셀업로드 목록(링커스)
	 */
	@RequestMapping("/rcp/rcpMgmt/regUsimSnUpListLinkus.json")
	public String regUsimSnUpListLinkus(HttpServletRequest request
							, HttpServletResponse response
							, @ModelAttribute("searchVO") RcpListVO searchVO
							, ModelMap model
							, @RequestParam Map<String, Object> pReqParamMap)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			LoginInfo loginInfo = new LoginInfo(request, response);
			loginInfo.putSessionToVo(searchVO);
			loginInfo.putSessionToParameterMap(pReqParamMap);
			
			if(!"10".equals(searchVO.getSessionUserOrgnTypeCd())){
				throw new MvnoServiceException(messageSource.getMessage("ktis.msp.rtn_code.NO_AUTHORITY", null, Locale.getDefault()));
			}
			
			String baseDir = propertiesService.getString("fileUploadBaseDirectory");
			
			String sOpenFileName = baseDir + "/CMN/" + searchVO.getFileName();
			
			//2020.01.07 requestStateCode 진행상태코드 추가
			String[] arrCell = {"resNo" , "reqUsimSn" ,"requestStateCode"};
			
			List<Object> uploadList = ExcelFilesUploadUtil.getRegDataFromExcel("com.ktis.msp.rcp.rcpMgmt.vo.RcpListVO", sOpenFileName, arrCell);
			
			resultMap =  makeResultMultiRowNotEgovMap(pReqParamMap, uploadList, uploadList.size());
			
			model.addAttribute("result", resultMap);
			
		} catch (Exception e) {
			resultMap.clear();
			throw new MvnoErrorException(e);
		}
		
		return "jsonView"; 
	}	
	/**
	* 2020.12.10 
	* @Description : 유심일련번호등록 업로드 양식 다운로드(링커스)
	* @Param  : 
	* @Return : ModelAndView
	*/
	@RequestMapping(value="/rcp/rcpMgmt/getUsimSnTempExcelDownLinkus.json")
	public String getUsimSnTempExcelDownLinkus(@ModelAttribute("searchVO") RcpListVO searchVO,
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
			
			List<?> resultList = new ArrayList<RcpListVO>();
		
			String serverInfo = propertiesService.getString("excelPath");
			String strFilename = serverInfo  + "유심일련번호등록엑셀양식_";//파일명
			String strSheetname = "유심일련번호등록엑셀양식";//시트명
			
			String [] strHead = {"예약번호", "유심번호", "진행상태"};
			String [] strValue = {"resNo", "reqUsimSn", "requestStateCode"};
//			String [] strValue = {};
			
			//엑셀 컬럼 사이즈
			int[] intWidth = {5000,5000,5000};
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
	 * 2020.12.10
	 * @Description : 유심일련번호 엑셀등록(링커스)
	 * @Param  : 
	 * @Return : ModelAndView
	 */
	@RequestMapping(value="/rcp/rcpMgmt/regUsimSnListLinkus.json")
	public String regUsimSnListLinkus(@ModelAttribute("searchVO") RcpListVO searchVO, 
					@RequestBody String data,
					HttpServletRequest request, 
					HttpServletResponse response, 
					ModelMap model, 
					@RequestParam Map<String, Object> pRequestParamMap) {
		
		
		logger.info(generateLogMsg("================================================================="));
		logger.info(generateLogMsg("유심일련번호 엑셀등록(Linkus) START."));
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
			RcpListVO vo = new ObjectMapper().readValue(data, RcpListVO.class);
			
			rcpMgmtService.regUsimSnListLinkus(vo, loginInfo.getUserId() );//추가작업(

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
