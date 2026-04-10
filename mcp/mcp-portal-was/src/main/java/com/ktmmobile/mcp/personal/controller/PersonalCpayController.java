package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MpVirtualAccountNoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.PaywayService;
import com.ktmmobile.mcp.payinfo.dto.PaywayDto;
import com.ktmmobile.mcp.personal.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class PersonalCpayController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalCpayController.class);

	private static final String PAGE_TYPE = "CPAY";
	private static final String REDIRECT_URL = "/personal/auth.do";

	@Autowired
	private PersonalService personalService;

	@Autowired
	private PaywayService paywayService;


	/**
	 * 개인화 url - 납부방법 변경 소개 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/chargeView05.do", "/m/personal/chargeView05.do"})
	public String personalChargeView05(@ModelAttribute(value = "ncn") String ncn
																		,ModelMap model){

		String jspPageName = "/personal/portal/pPaywayInfo";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pPaywayInfo";
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

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		MyPageSearchDto searchVO = new MyPageSearchDto();
		searchVO.setNcn(svcCntrNo);
		searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
		searchVO.setCtn(StringMakerUtil.getPhoneNum(ctn));

		Map<String,Object> map = new HashMap<>();

		try {
			/* [MP] 현재 납부 방법 및 명세서 정보 조회 (X01, X23, X49) */
			map = paywayService.getPayData(svcCntrNo, ctn, customerId);
		}catch(SelfServiceException e) {
			logger.error("personalChargeView05.SelfServiceException={}", e.getMessage());
		}catch(Exception e) {
			logger.error("personalChargeView05.Exception={}", e.getMessage());
		}

		model.addAttribute("reqType",         map.get("reqType"));          // 명세서유형
		model.addAttribute("reqTypeNm",       map.get("reqTypeNm"));        // 명세서추가정보 (제목)
		model.addAttribute("blaAddr",         map.get("blaAddr"));          // 명세서추가정보
		model.addAttribute("payMethod",       map.get("payMethod"));        // 납부방법
		model.addAttribute("blBankAcctNo",    map.get("blBankAcctNo"));     // 계좌번호
		model.addAttribute("billTypeCd",      map.get("billTypeCd"));       // 명세서유형코드
		model.addAttribute("billCycleDueDay", map.get("billCycleDueDay"));  // 납부일
		model.addAttribute("prevCardNo",      map.get("prevCardNo"));       // 카드번호
		model.addAttribute("prevExpirDt",     map.get("prevExpirDt"));      // 카드만료기간
		model.addAttribute("payTmsCd",        map.get("payTmsCd"));         // 납부회차
		model.addAttribute("searchVO", searchVO);
		return jspPageName;
	}


	/**
	 * 개인화 url - 납부방법 변경 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/paywayView.do", "/m/personal/paywayView.do"})
	public String personalPaywayView(@ModelAttribute(value = "ncn") String ncn
																	 ,ModelMap model){

		String jspPageName = "/personal/portal/pPaywayView";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pPaywayView";
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

		// 간편본인인증 세션 초기화
		SessionUtils.saveNiceRes(null);

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		/* [MP] 납부방법조회 (X23) */
		MpFarChangewayInfoVO mFarchangewayinfovo = paywayService.farChangewayInfo(svcCntrNo, ctn, customerId);
		String payMethod = (mFarchangewayinfovo == null) ? "" : mFarchangewayinfovo.getPayMethod();
		String payBizrCd = (mFarchangewayinfovo == null) ? "" : mFarchangewayinfovo.getPayBizrCd();

		/* [MP] 우편 명세서 발행정보 조회 (X10) */
		MpBilPrintInfoVO mBilprintinfovo = paywayService.bilPrintInfo(svcCntrNo, ctn, customerId);
		String zipCode = (mBilprintinfovo == null) ? "" : mBilprintinfovo.getZipCode();
		String pAddr = (mBilprintinfovo == null) ? "" : mBilprintinfovo.getpAddr();
		String sAddr = (mBilprintinfovo == null) ? "" : mBilprintinfovo.getsAddr();

		model.addAttribute("nowY", Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("payMethod", StringUtil.NVL(payMethod, ""));
		model.addAttribute("payBizrCd", StringUtil.NVL(payBizrCd, ""));
		model.addAttribute("zipCode", zipCode);
		model.addAttribute("pAddr", pAddr);
		model.addAttribute("sAddr", sAddr);
		model.addAttribute("ncn", svcCntrNo);
		model.addAttribute("pageType", PAGE_TYPE);
		return jspPageName;
	}

	/**
	 * 개인화 url - 납부방법 변경
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/farChgWayChgAjax.do", "/m/personal/farChgWayChgAjax.do"})
	@ResponseBody
	public Map<String, Object> farChgWayChgAjax(@ModelAttribute PaywayDto paywayDto){

		Map<String, Object> rtnMap = new HashMap<>();

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, paywayDto.getNcn());
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		// 간편본인인증 세션 조회
		NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
		if(niceResDto == null){
			throw new McpCommonJsonException("0002", NICE_CERT_EXCEPTION_INSR);
		}

		String myslAthnTypeCd = "C".equals(niceResDto.getAuthType()) ? "03" : "01";
		String birthdate = niceResDto.getBirthDate();
		if(birthdate != null &&  7 < birthdate.length()) {
			birthdate = birthdate.substring(2, 8);
		}

		paywayDto.setCtn(personalUser.getCntrMobileNo());
		paywayDto.setCustId(personalUser.getCustomerId());
		paywayDto.setCstmrName(niceResDto.getName());
		paywayDto.setCstmrNativeRrn01(birthdate);
		paywayDto.setReqSeq(niceResDto.getReqSeq());
		paywayDto.setResSeq(niceResDto.getResSeq());
		paywayDto.setUserId("ACEN");
		paywayDto.setMyslAthnTypeCd(myslAthnTypeCd);

		/* [MP] 납부방법변경 (X25) */
		Map<String, Object> map = paywayService.farChgWayChg(paywayDto);
		if(!"00".equals(map.get("returnCode"))){
			String errMsg = StringUtil.NVL((String) map.get("message"), COMMON_EXCEPTION);
			throw new McpCommonJsonException("0003", errMsg);
		}

		rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		rtnMap.put("RESULT_MSG", "처리성공");
		return rtnMap;
	}

	/**
	 * 개인화 url - 납부방법 변경 SMS 전송 (카카오, 페이코)
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/sendKakaSmsAjax.do", "/m/personal/sendKakaSmsAjax.do"})
	@ResponseBody
	public Map<String, Object> sendKakaSmsAjax(@ModelAttribute PaywayDto paywayDto){

		Map<String, Object> rtnMap = new HashMap<>();
		String returnCode = null;
		String message = null;

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, paywayDto.getNcn());
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		paywayDto.setCtn(personalUser.getCntrMobileNo());
		paywayDto.setCustId(personalUser.getCustomerId());

		try{
			/* [MP] 간편결제 가입 SMS 전송(X82) */
			Map<String, Object> map = paywayService.smsFarChgWayChg(paywayDto);
			returnCode = (String) map.get("RESULT_CODE");
			message = StringUtil.NVL((String) map.get("RESULT_MSG"), COMMON_EXCEPTION);
		}catch(SelfServiceException e){
			returnCode = "E";
			message = COMMON_EXCEPTION;
		}catch(Exception e){
			returnCode = "E";
			message = COMMON_EXCEPTION;
		}

		rtnMap.put("RESULT_CODE", returnCode);
		rtnMap.put("RESULT_MSG", message);
		return rtnMap;
	}

	@RequestMapping(value = {"/personal/virtualAccountListPop.do", "/m/personal/virtualAccountListPop.do"})
	public String virtualAccountListPop(HttpServletRequest request
			, @RequestParam(value = "ncn") String ncn
			, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO
	) {
		String url = "/portal/mypage/myinfo/virtualAccountListPop";
		String errorUrl = "/portal/errmsg/errorPop";
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())){
			url = "/mobile/mypage/myinfo/virtualAccountListPop";
			errorUrl = "/mobile/errmsg/errorPop";
		}

		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			model.addAttribute("ErrorTitle", "가상계좌 조회");
			model.addAttribute("ErrorMsg", "개인화 URL 재발급/재인증 부탁드립니다.");
			return errorUrl;
		}

		List<MpVirtualAccountNoDto> virtualAccountList = new ArrayList<>();
		boolean isSuccess = true;
		try {
			virtualAccountList = paywayService.getVirtualAccountNoList(personalUser.getSvcCntrNo(), personalUser.getCntrMobileNo(), personalUser.getCustomerId());
		} catch (SocketTimeoutException | SelfServiceException e) {
			isSuccess = false;
		}

		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("userName", StringMakerUtil.getName(personalUser.getUserName()));
		model.addAttribute("virtualAccountList", virtualAccountList);

		return url;
	}
}
