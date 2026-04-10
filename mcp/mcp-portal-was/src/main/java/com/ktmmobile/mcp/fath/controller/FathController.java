package com.ktmmobile.mcp.fath.controller;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.fath.dto.FathDto;
import com.ktmmobile.mcp.fath.dto.FathFormInfo;
import com.ktmmobile.mcp.fath.service.FathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Controller
public class FathController {

	private static final Logger logger = LoggerFactory.getLogger(FathController.class);

	@Autowired
	private FathService fathService;
	
	/**
	 * 셀프안면인증 본인확인 페이지
	 * @Date : 2025.12.19
	 */
	@RequestMapping(value = { "/fath/fathAuth.do", "/m/fath/fathAuth.do" })
	public String fathAuthView(HttpServletRequest request
			,@RequestParam(value = "u", required = false, defaultValue = "") String uuid      
			,ModelMap model
			,RedirectAttributes redirectAttributes) {
		
		String viewType = "0000"; 	// viewType : 인증화면(0000)
		String jspPageName = "/portal/fath/fathAuth";
		String redirectUrl = "/fath/fathSelf.do";

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/mobile/fath/fathAuth";
			redirectUrl = "/m/fath/fathSelf.do";
		}
		
		// 필수값 검사
		if(StringUtil.isEmpty(uuid)){
			viewType = "0001";
			model.addAttribute("viewType", viewType);
			return jspPageName;
		}

		// 발급된 개인화 URL 조회
		FathDto fathDto = fathService.getFathSelfUrl(uuid);
		if(fathDto == null){
			viewType = "0001";
			model.addAttribute("viewType", viewType);
			return jspPageName;
		}
		
		// 조회한 URL의 유효기간 확인
		String expirDay = fathDto.getExpirDt();
		String now = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		if(expirDay.compareTo(now) < 0 ) {
			viewType = "0002";
			model.addAttribute("viewType", viewType);
			return jspPageName;
		}
		
		// 인증 세션 조회
		AuthSmsDto authSession = SessionUtils.getSmsSession("fath");
		
		// 리다이렉트
		if(authSession != null){
			redirectAttributes.addAttribute("resNo", fathDto.getResNo());
			redirectAttributes.addAttribute("operType", fathDto.getOperType());
			return "redirect:" + redirectUrl;
		}

		model.addAttribute("viewType", viewType);
		model.addAttribute("resNo", fathDto.getResNo());
		model.addAttribute("operType", fathDto.getOperType());
		model.addAttribute("menuType", "fath");
		return jspPageName;
	}

	/**
	 * 셀프안면인증 페이지
	 * @Date : 2025.12.19
	 */
	@RequestMapping(value = { "/fath/fathSelf.do", "/m/fath/fathSelf.do" })
	public String fathSelfView(HttpServletRequest request
			,@ModelAttribute FathDto fathDto
			,ModelMap model) {

		//안면인증 세션 초기화
		SessionUtils.initializeFathSession();

		String jspPageName = "/portal/fath/fathSelf";
		String redirectUrl = "/fath/fathAuth.do";
		
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/mobile/fath/fathSelf";
			redirectUrl = "/m/fath/fathAuth.do";
		}
	
		//sms인증세션 미 존재 시 메인으로 리다이렉트
		AuthSmsDto authSession = SessionUtils.getSmsSession("fath");
		if(authSession == null){
			throw new McpCommonException(F_BIND_EXCEPTION, redirectUrl);
		}
		//필수값 미 존재 시 
		String resNo = fathDto.getResNo();
		String operType = fathDto.getOperType();
		if(StringUtil.isEmpty(resNo) || StringUtil.isEmpty(operType)) {
			throw new McpCommonException(F_BIND_EXCEPTION, redirectUrl);
		}
		//파라미터로 넘어온 예약번호로 신청서정보 조회
		String mobileNo = "";
		String name = "";

		FathFormInfo fathFormInfo = fathService.getFathFormInfo(resNo, operType);
		if(fathFormInfo == null) {
			throw new McpCommonException(F_BIND_EXCEPTION, redirectUrl);
		}
		mobileNo = fathFormInfo.getFathTelNo();
		name = fathFormInfo.getCstmrName();

		//sms인증세션값과 신청서정보 비교
		if(!name.equals(authSession.getAuthNum()) || !mobileNo.equals(authSession.getPhoneNum())) {
			SessionUtils.setAuthSmsSetNullSession(authSession);
			throw new McpCommonException("신청정보와 SMS인증 정보가 다릅니다. <br>신청한 정보로 SMS인증 부탁드립니다.", redirectUrl);
		}
		
		model.addAttribute("resNo", resNo);
		model.addAttribute("operType", operType);
		
		return jspPageName;
	}
	
}
