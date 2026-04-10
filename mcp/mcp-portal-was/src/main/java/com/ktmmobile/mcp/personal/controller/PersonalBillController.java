package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.*;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.mypage.dto.BillWayChgDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MyinfoService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.PaywayService;
import com.ktmmobile.mcp.personal.dto.PersonalBillDto;
import com.ktmmobile.mcp.personal.service.PersonalService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@Controller
public class PersonalBillController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalBillController.class);

	private static final String PAGE_TYPE = "BILL";
	private static final String REDIRECT_URL = "/personal/auth.do";

	@Autowired
	private PersonalService personalService;

	@Autowired
	private MplatFormService mPlatFormService;

	@Autowired
	private MyinfoService myinfoService;

	@Autowired
	private PaywayService paywayService;

	@Autowired
	private MypageService mypageService;


	/**
	 * 개인화 url - 명세서 정보변경 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/billWayChgView.do", "/m/personal/billWayChgView.do"})
	public String personalBillWayChgView(@ModelAttribute(value = "ncn") String ncn
																			,ModelMap model){

		String jspPageName = "/personal/portal/pBillWayChgView";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pBillWayChgView";
		}

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){

			String sessionUrl = SessionUtils.getPersonalUrl(PAGE_TYPE);
			if("".equals(sessionUrl)){
				throw new McpCommonException(F_BIND_EXCEPTION, REDIRECT_URL);
			}

			return "redirect:" + sessionUrl;
		}

		MyPageSearchDto searchVO = new MyPageSearchDto();
		searchVO.setNcn(personalUser.getSvcCntrNo());
		searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
		searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

		model.addAttribute("searchVO", searchVO);
		return jspPageName;
	}

	/**
	 * 개인화 url - 명세서 정보 조회
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/myBillInfoViewAjax.do"})
	@ResponseBody
	public Map<String, Object> myBillInfoViewAjax(@RequestParam(value = "ncn") String ncn) {

		Map<String, Object> rtnMap = new HashMap<>();

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		/* [MP] 가입정보조회 (X01) */
		MpPerMyktfInfoVO perMyktfInfo = myinfoService.perMyktfInfo(svcCntrNo, ctn, customerId);
		String addr = (perMyktfInfo == null) ? "" : perMyktfInfo.getAddr();

		rtnMap.put("addr", addr); // 주소

		/* [MP] 납부방법조회 (X23) */
		MpFarChangewayInfoVO changeInfo = myinfoService.farChangewayInfo(svcCntrNo, ctn, customerId);
		String payMethod = (changeInfo == null) ? "" : changeInfo.getPayMethod();
		String blAddr = (changeInfo == null) ? "" : changeInfo.getBlAddr();
		String billCycleDueDay = (changeInfo == null) ? "" : changeInfo.getBillCycleDueDay();
		String blBankAcctNo = (changeInfo == null) ? "" : changeInfo.getBlBankAcctNo();
		String prevCardNo = (changeInfo == null) ? "" : changeInfo.getPrevCardNo();
		String prevExpirDt = (changeInfo == null) ? "" : changeInfo.getPrevExpirDt();

		rtnMap.put("payMethod", payMethod);              // 납부방법
		rtnMap.put("blAddr", blAddr);                    // 청구지 주소
		rtnMap.put("billCycleDueDay", billCycleDueDay);  // 납부일
		rtnMap.put("blBankAcctNo", blBankAcctNo);        // 계좌번호
		rtnMap.put("prevCardNo", prevCardNo);            // 카드번호
		rtnMap.put("prevExpirDt", prevExpirDt);          // 카드만료기간

		/* [MP] 요금 명세서 조회(X49) */
		MpMoscBilEmailInfoInVO moscBilEmailInfo = myinfoService.kosMoscBillInfo(svcCntrNo, ctn, customerId);
		String billTypeCd = (moscBilEmailInfo == null) ? "" : moscBilEmailInfo.getBillTypeCd();
		String email = (moscBilEmailInfo == null) ? "" : moscBilEmailInfo.getEmail();
		String maskedEmail = (moscBilEmailInfo == null) ? "" : moscBilEmailInfo.getMaskedEmail();
		String maskedCtn = (moscBilEmailInfo == null) ? "" : moscBilEmailInfo.getCtn();

		rtnMap.put("billTypeCd", billTypeCd);    // 명세서 발송유형
		rtnMap.put("email", email);              // 이메일주소(마스킹)
		rtnMap.put("maskedEmail", maskedEmail);  // 이메일주소(마스킹)
		rtnMap.put("maskedCtn", maskedCtn);      // 명세서 발송전화번호(마스킹)

		/* [MP] 우편 명세서 발행정보 조회 (X10) */
		MpBilPrintInfoVO bilPrintInfo = paywayService.bilPrintInfo(svcCntrNo, ctn, customerId);
		String zipCode = (bilPrintInfo == null) ? "" : bilPrintInfo.getZipCode();  // 우편번호
		String pAddr = (bilPrintInfo == null) ? "" : bilPrintInfo.getpAddr();      // 수신메인주소

		rtnMap.put("zipCode", zipCode); // 우편번호
		rtnMap.put("pAddr", pAddr);     // 수신메인주소
		rtnMap.put("sAddr", "");        // 수신상세주소 (표출하지 않음)

		rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		return rtnMap;
	}

	/**
	 * 개인화 url - 명세서 정보 변경
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/billChgAjax.do"})
	@ResponseBody
	public Map<String, Object> billChgAjax(@ModelAttribute PersonalBillDto pBillDto) {

		Map<String, Object> rtnMap = new HashMap<>();
		String returnCode = "S";
		String message = "처리성공";

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, pBillDto.getNcn());
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		boolean serviceFlag = false;  // 명세서 정보 변경 연동대상 여부
		String billAdInfo = "";       // 발송정보 (이메일인 경우 email주소, MMS인 경우 전화번호)
		String status = "";				    // 신청(1), 변경(9), 해지(0)
		String sendGubun = "";		    // 발송(1), 발송제외(2), 해지(3)
		String securMailYn = "";	    // 보안메일 여부
		String ecRcvAgreYn = "";      // 이벤트 수신 동의 여부
		String billTypeCd = ""; 	    // 이메일(1), MMS(2)

		try{

			if("MB".equals(pBillDto.getBillTypeCd())){
				// MMS 명세서
				billAdInfo = personalUser.getCntrMobileNo();
				status = "9";
				sendGubun = "1";
				securMailYn = "Y";
				ecRcvAgreYn = "N";
				billTypeCd = "2";
				serviceFlag = true;

			}else if("CB".equals(pBillDto.getBillTypeCd())){
				// 이메일 명세서
				billAdInfo = pBillDto.getBillAdInfo();
				status = "9";
				sendGubun = "1";
				securMailYn = "Y";
				ecRcvAgreYn = "N";
				billTypeCd = "1";
				serviceFlag = true;

			}else if("LX".equals(pBillDto.getBillTypeCd())){
				// 우편명세서

				/* [MP] 요금 명세서 조회(X49) */
				MpMoscBilEmailInfoInVO moscBilEmailInfo = mPlatFormService.kosMoscBillInfo(svcCntrNo, ctn, customerId);

				// 현재 명세서 유형이 우편이 아닌 경우 → 해지
				if(!pBillDto.getBillTypeCd().equals(moscBilEmailInfo.getBillTypeCd())){
					billAdInfo = "";
					status = "0";
					sendGubun = "3";
					securMailYn = "Y";
					ecRcvAgreYn = "Y";
					billTypeCd = "CB".equals(moscBilEmailInfo.getBillTypeCd()) ? "1" : "2";
					serviceFlag = true;
				}
			}else{
				throw new McpCommonJsonException("-1", INVALID_PARAMATER_EXCEPTION);
			}

			/* [MP] 명세서 정보 변경 (X50) */
			if(serviceFlag){
				mPlatFormService.kosMoscBillChg(svcCntrNo, ctn, customerId, billTypeCd, status, billAdInfo, securMailYn, ecRcvAgreYn, sendGubun);
			}

			/* [MP] 우편명세서 주소변경 (X02) */
			if("LX".equals(pBillDto.getBillTypeCd())){
				String bilCtnDisplay = "Y";
				mPlatFormService.perAddrChg(svcCntrNo, ctn, customerId, pBillDto.getZip(), pBillDto.getAddr1(), pBillDto.getAddr2(), bilCtnDisplay);
			}

		}catch(McpCommonJsonException e){
			returnCode = "E";
			message = getErrMsg(e.getErrorMsg());
		}catch(SelfServiceException e){
			returnCode = "E";
			message = getErrMsg(e.getMessageNe());
		}catch(Exception e){
			returnCode = "E";
			message = getErrMsg(e.getMessage());
		}

		rtnMap.put("RESULT_CODE", returnCode);
		rtnMap.put("RESULT_MSG", message);
		return rtnMap;
	}

	/**
	 * 개인화 url - 명세서 재발송 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/billWayReSend.do", "/m/personal/billWayReSend.do"})
	public String personalBillWayReSend(@ModelAttribute(value = "ncn") String ncn
																		 ,ModelMap model){

		String jspPageName = "/personal/portal/pBillWayReSend";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pBillWayReSend";
		}

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){

			String sessionUrl = SessionUtils.getPersonalUrl(PAGE_TYPE);
			if("".equals(sessionUrl)){
				throw new McpCommonException(F_BIND_EXCEPTION, REDIRECT_URL);
			}

			return "redirect:" + sessionUrl;
		}

		MyPageSearchDto searchVO = new MyPageSearchDto();
		searchVO.setNcn(personalUser.getSvcCntrNo());
		searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
		searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		List<MoscBillSendInfoOutVO.MoscBillSendInfoOutDTO> outSendInfoListlDto = new ArrayList<>();

		try{
			/* [MP] 사이버명세서 발송 이력 조회 (X51) */
			MoscBillSendInfoOutVO moscBillSendInfoOutVO = mPlatFormService.kosMoscBillSendInfo(svcCntrNo, ctn, customerId);

			if(moscBillSendInfoOutVO != null) {
				outSendInfoListlDto = moscBillSendInfoOutVO.getMoscBillSendInfoOutDTO();
				if(outSendInfoListlDto != null && outSendInfoListlDto.size() > 6){
					outSendInfoListlDto = outSendInfoListlDto.subList(0, 6);
				}
			}
		} catch (SelfServiceException e) {
			logger.info("[X51] personalBillWayReSend.SelfServiceException={}", e.getMessage());
		}  catch(Exception e) {
			logger.info("[X51] personalBillWayReSend.Exception={}", e.getMessage());
		}

		model.addAttribute("outSendInfoListlDto", outSendInfoListlDto);
		model.addAttribute("searchVO", searchVO);
		return jspPageName;
	}

	/**
	 * 개인화 url - 명세서 재발송 팝업
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/billWayReSendPop.do", "/m/personal/billWayReSendPop.do"})
	public String billWayReSendPop(@RequestParam(value = "ncn") String ncn
																,@RequestParam(value = "thisMonth") String thisMonth
																,ModelMap model){

		String jspPageName = "/personal/portal/pBillWayReSendPop";
		String errPageName = "/portal/errmsg/errorPop";

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pBillWayReSendPop";
			errPageName = "/mobile/errmsg/errorPop";
		}

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			model.addAttribute("ErrorTitle", "명세서 재발송");
			model.addAttribute("ErrorMsg", "개인화 URL 재발급/재인증 부탁드립니다.");
			return errPageName;
		}

		MyPageSearchDto searchVO = new MyPageSearchDto();
		searchVO.setNcn(personalUser.getSvcCntrNo());
		searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
		searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

		model.addAttribute("searchVO", searchVO);
		model.addAttribute("thisMonth", thisMonth);
		return jspPageName;
	}

	/**
	 * 개인화 url - 명세서 재발송 처리
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/billWayReSendAjax.do", "/m/personal/billWayReSendAjax.do"})
	@ResponseBody
	public Map<String, Object> billWayReSendAjax(@RequestParam(value = "ncn") String ncn
																						  ,@ModelAttribute BillWayChgDto billWayChgDto){

		Map<String, Object> rtnMap = new HashMap<>();

		String returnCode = "S";
		String message = "처리성공";

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		// 요청파라미터 확인
		String thisMonth = billWayChgDto.getThisMonth();
		String billTypeCd = billWayChgDto.getBillTypeCd();

		if(StringUtils.isEmpty(thisMonth) || StringUtils.isEmpty(billTypeCd)){
			throw new McpCommonJsonException("0002", INVALID_PARAMATER_EXCEPTION);
		}

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		String year = (thisMonth.length() == 6) ? thisMonth.substring(0, 4) : "";
		String month = (thisMonth.length() == 6) ? thisMonth.substring(4, 6) : "";
		String billAdInfo = ("1".equals(billTypeCd)) ? billWayChgDto.getEmail() : ctn;

		MoscBillReSendChgOutVO moscBillReSendChgOutVO = null;

		try{
			/* [MP] 명세서 재발송 (X53) */
			moscBillReSendChgOutVO = mPlatFormService.kosMoscBillReSendChg(svcCntrNo, ctn, customerId, year, month, billTypeCd, billAdInfo);

		}catch(SelfServiceException e){
			returnCode = "E";
			message = getErrMsg(e.getMessageNe());
		}catch(SocketTimeoutException e){
			returnCode = "E";
			message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
		}catch(Exception e){
			returnCode = "E";
			message = getErrMsg(e.getMessage());
		}finally{

			// 명세서 재발송 이력저장
			BillWayChgDto histInfo = mypageService.getMspData(personalUser.getContractNum());
			if(histInfo != null){

				String globalNo = (moscBillReSendChgOutVO == null) ? "" : moscBillReSendChgOutVO.getGlobalNo();
				String successYn = "S".equals(returnCode) ? "Y" : "N";
				String errMsg = "Y".equals(successYn) ? "" : message;

				histInfo.setGlobalNo(globalNo);
				histInfo.setUserId("ACEN");
				histInfo.setUserNm(personalUser.getUserName());

				histInfo.setBillTypeCd(billTypeCd);
				histInfo.setBillAdInfo(billAdInfo);
				histInfo.setSuccessYn(successYn);
				histInfo.setErrMsg(errMsg);
				mypageService.insertMcpBillwayResend(histInfo);
			}
		}

		rtnMap.put("RESULT_CODE", returnCode);
		rtnMap.put("RESULT_MSG", message);
		return rtnMap;
	}

	private String getErrMsg(String msg) {

		if(StringUtils.isEmpty(msg)){
			return COMMON_EXCEPTION;
		}

		String[] arg = msg.split(";;;");
		String errMsg = (arg.length > 1) ? arg[1] : arg[0];
		return (errMsg.length() > 300) ? COMMON_EXCEPTION : errMsg;
	}

}
