package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.dto.MoscPymnReqDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.PaymentInfoVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.RealTimePayService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class PersonalIpayController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalIpayController.class);

	private static final String PAGE_TYPE = "IPAY";
	private static final String REDIRECT_URL = "/personal/auth.do";

	@Autowired
	private PersonalService personalService;

	@Autowired
	private RealTimePayService realTimePayService;

	@Autowired
	private FCommonSvc fCommonSvc;

	@Autowired
	private SmsSvc smsSvc;

	/**
	 * 개인화 url - 즉시납부(미납요금조회) 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/unpaidChargeList.do", "/m/personal/unpaidChargeList.do"})
	public String personalUnpaidChargeList(@ModelAttribute(value = "ncn") String ncn
																				,ModelMap model){

		String jspPageName = "/personal/portal/pUnpaidChargeList";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pUnpaidChargeList";
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

		MyPageSearchDto searchVO = new MyPageSearchDto();
		searchVO.setNcn(personalUser.getSvcCntrNo());
		searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
		searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

		String customerType = personalUser.getCustomerType();
		if("G".equals(customerType) || "B".equals(customerType)){
			customerType = "Y";
		}

		model.addAttribute("pageType", PAGE_TYPE);
		model.addAttribute("customerType", customerType);
		model.addAttribute("searchVO", searchVO);
		return jspPageName;
	}

	/**
	 * 개인화 url - 청구년월 당월요금 및 미납요금 조회
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/unpaidChargeListAjax.do", "/m/personal/unpaidChargeListAjax.do"})
	@ResponseBody
	public Map<String, Object> unpaidChargeListAjax(@RequestParam(value = "ncn") String ncn){

		Map<String, Object> rtnMap = new HashMap<>();

		String returnCode = "S";
		String message = "처리성공";

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		String customerType = personalUser.getCustomerType();
		if("G".equals(customerType) || "B".equals(customerType)){
			throw new McpCommonJsonException("0002", "개인고객만 납부 가능합니다.");
		}

		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		try{
			/* [MP] 당월요금+미납요금 조회 (X92) */
			PaymentInfoVO farPaymentInfoVO = realTimePayService.moscCurrMthNpayInfo(svcCntrNo, ctn, customerId);

			rtnMap.put("totNpayAmt", farPaymentInfoVO.getTotNpayAmt());          // 총미납요금
			rtnMap.put("currMthNpayAmt", farPaymentInfoVO.getCurrMthNpayAmt());  // 당월 미납요금
			rtnMap.put("payTgtAmt", farPaymentInfoVO.getPayTgtAmt());            // 납입대상요금

		}catch(McpCommonException e){
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

	/**
	 * 개인화 url - 즉시납부 신청 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/realTimePaymentView.do", "/m/personal/realTimePaymentView.do"})
	public String personalRealTimePaymentView(@ModelAttribute("moscPymnReqDto") MoscPymnReqDto moscPymnReqDto
																					 ,ModelMap model){

		String jspPageName = "/personal/portal/pRealTimePaymentView";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pRealTimePaymentView";
		}

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, moscPymnReqDto.getNcn());
		if(personalUser == null){

			String sessionUrl = SessionUtils.getPersonalUrl(PAGE_TYPE);
			if("".equals(sessionUrl)){
				throw new McpCommonException(F_BIND_EXCEPTION, REDIRECT_URL);
			}

			return "redirect:" + sessionUrl;
		}

		// 간편본인인증 세션 조회
		NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
		if(sessNiceRes == null){
			throw new McpCommonException(NICE_CERT_EXCEPTION_INSR);
		}

		// 즉시납부 수단 중 계좌이체 노출여부
		String isPayTransfer = NmcpServiceUtils.getCodeNm("Constant","isPayTransfer");
		if(StringUtils.isEmpty(isPayTransfer)){
			isPayTransfer = "N";
		}

		// 관리자 아이디만 계좌이체 노출
		if(!"Y".equals(isPayTransfer)){
			UserSessionDto userSession = SessionUtils.getUserCookieBean();
			String userId = (userSession == null) ? "" : userSession.getUserId();

			String isExceptionId = NmcpServiceUtils.getCodeNm(ID_GROUP_EXCEPTION, userId);
			if("Y".equals(isExceptionId)){
				isPayTransfer = "Y";
			}
		}

		model.addAttribute("payMentMoney", moscPymnReqDto.getPayMentMoney());
		model.addAttribute("ncn", personalUser.getSvcCntrNo());
		model.addAttribute("nowY", Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("isPayTransfer", isPayTransfer);
		return jspPageName;
	}

	/**
	 * 개인화 url - 즉시납부 신청 처리
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/insertRealTimePaymentAjax.do", "/m/personal/insertRealTimePaymentAjax.do"})
	@ResponseBody
	public Map<String, Object> insertRealTimePaymentAjax(@ModelAttribute("moscPymnReqDto") MoscPymnReqDto moscPymnReqDto
																											,HttpSession session){

		Map<String, Object> rtnMap = new HashMap<>();
		String linkUrl = "";

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, moscPymnReqDto.getNcn());
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		// 간편본인인증 세션 조회
		NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
		if(sessNiceRes == null){
			throw new McpCommonJsonException("0002", NICE_CERT_EXCEPTION_INSR);
		}

		moscPymnReqDto.setCtn(personalUser.getCntrMobileNo());
		moscPymnReqDto.setCustId(personalUser.getCustomerId());

		String paymentMoney = StringUtil.NVL(moscPymnReqDto.getPayMentMoney(), "");
		moscPymnReqDto.setPayMentMoney(paymentMoney.replaceAll(",", ""));

		try{

			/* [MP] 요금 즉시 납부 (X93) */
			linkUrl = realTimePayService.moscCurrMthNpayTreat(moscPymnReqDto);

			String blMethod = moscPymnReqDto.getBlMethod(); // 납부수단
			session.setAttribute("blMethod", blMethod);

			if("P".equals(blMethod)){ // P: 간편결제

				// 간편결제 오픈 여부
				String isSamplePayOpen = NmcpServiceUtils.getCodeNm("Constant","isSamplePayOpen");
				if(StringUtil.isEmpty(isSamplePayOpen)){
					isSamplePayOpen = "N";
				}

				// 관리자 아이디만 간편결제 오픈
				if(!"Y".equals(isSamplePayOpen)){
					UserSessionDto userSession = SessionUtils.getUserCookieBean();
					String userId = (userSession == null) ? "" : userSession.getUserId();

					String isExceptionId = NmcpServiceUtils.getCodeNm(ID_GROUP_EXCEPTION , userId);
					if("Y".equals(isExceptionId)){
						isSamplePayOpen = "Y";
					}
				}

				// 간편결제 URL SMS발송
				if("Y".equals(isSamplePayOpen)){
					MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(SAMPLE_PAY_TEMPLATE_ID);

					if(mspSmsTemplateMstDto != null){
						String rmnyChIdNm = NmcpServiceUtils.getCodeNm(RMNY_CHID_OBJ_LIST, moscPymnReqDto.getRmnyChId());

						if(!StringUtil.isEmpty(rmnyChIdNm)){
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
							mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{linkUrl}", linkUrl));
							smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), moscPymnReqDto.getCtn(), mspSmsTemplateMstDto.getText()
								,mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY
								,String.valueOf(SAMPLE_PAY_TEMPLATE_ID), "SYSTEM");
						}
					}
				}
			}

		}catch(SelfServiceException e){

			// mp 오류메세지 치환
			String resultCode = e.getResultCode();
			String resultMsg = e.getMessageNe();

			if(!StringUtil.isEmpty(resultCode)){
				NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("errorCodeMsg", resultCode);
				if(nmcpCdDtlDto != null && !StringUtil.isEmpty(nmcpCdDtlDto.getDtlCdDesc())){
					resultMsg = nmcpCdDtlDto.getDtlCdDesc();
				}
			}

			String errMsg = StringUtil.NVL(resultMsg, COMMON_EXCEPTION);
			throw new McpCommonJsonException("0003", errMsg);

		}catch(Exception e){
			throw new McpCommonJsonException("0004", COMMON_EXCEPTION);
		}

		rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		rtnMap.put("url", linkUrl);
		return rtnMap;
	}

	/**
	 * 개인화 url - 즉시납부 완료 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value= {"/personal/realTimePaymentCompleteView.do", "/m/personal/realTimePaymentCompleteView.do"})
	public String personalRealTimePaymentCompleteView(@ModelAttribute("moscPymnReqDto") MoscPymnReqDto moscPymnReqDto
																					         ,HttpSession session
																					         ,RedirectAttributes redirectAttributes
																					         ,ModelMap model){

		String jspPageName = "/personal/portal/pRealTimePaymentCompleteView";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pRealTimePaymentCompleteView";
		}

		String blMethod = (String) session.getAttribute("blMethod");

		if(StringUtil.isEmpty(blMethod)){
			redirectAttributes.addFlashAttribute("ncn", moscPymnReqDto.getNcn());
			return "redirect:/personal/unpaidChargeList.do";
		}

		session.removeAttribute("blMethod");
		model.addAttribute("ncn", moscPymnReqDto.getNcn());
		model.addAttribute("payMentMoney", moscPymnReqDto.getPayMentMoney());
		model.addAttribute("rmnyChId", moscPymnReqDto.getRmnyChId());
		return jspPageName;
	}

}
