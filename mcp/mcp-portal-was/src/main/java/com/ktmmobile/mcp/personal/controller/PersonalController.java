package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.NiceLogSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.personal.dto.PersonalDto;
import com.ktmmobile.mcp.personal.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class PersonalController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

	private static final Map<String, String> PERSONAL_REDIRECT_URL = Map.of("SOC", "/personal/farPricePlanView.do"
																																				 ,"ADD", "/personal/regServiceView.do"
																																				 ,"CPAY", "/personal/chargeView05.do"
																																				 ,"BILL", "/personal/billWayChgView.do"
																																				 ,"UAMT", "/personal/callView01.do"
																																				 ,"MPAY", "/personal/mPayView.do"
																																				 ,"IPAY", "/personal/unpaidChargeList.do");

	@Value("${SERVER_NAME}")
	private String serverName;

	@Autowired
	private PersonalService personalService;

	@Autowired
	private NiceLogSvc nicelog;

	/**
	 * 개인화 url 인증 메인 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = { "/personal/auth.do", "/m/personal/auth.do" })
	public String personalAuthView(HttpServletRequest request
																,@ModelAttribute PersonalDto personalDto
																,ModelMap model
																,RedirectAttributes redirectAttributes) {

		String viewType = "0000"; 	// viewType : 인증화면(0000), 인증불가화면(0001)
		String jspPageName = "/personal/portal/pAuthView";

		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pAuthView";
		}

		// 필수값 검사
		if(StringUtil.isEmpty(personalDto.getPageType()) || StringUtil.isEmpty(personalDto.getLanding()) || StringUtil.isEmpty(personalDto.getSeq())){
			viewType = "0001";
			model.addAttribute("viewType", viewType);
			return jspPageName;
		}

		// 발급된 개인화 URL 조회
		PersonalDto sPersonalDto = personalService.getPersonalUrlInfo(personalDto);

		if(sPersonalDto == null){
			viewType = "0001";
			model.addAttribute("viewType", viewType);
			return jspPageName;
		}

		// 인증 세션 조회
		AuthSmsDto authSmsDto = new AuthSmsDto();
		authSmsDto.setMenu(sPersonalDto.getPageType());
		AuthSmsDto authSession = SessionUtils.getPersonalAuthSmsBean(authSmsDto);

		// 발급된 개인화 URL 소유자와 SMS인증 대상 일치여부
		String authPhoneNum = (authSession == null) ? "" : authSession.getPhoneNum();
		boolean phoneNumChk = sPersonalDto.getCntrMobileNo().equals(authPhoneNum);

		// 리다이렉트
		if(authSession != null && authSession.isResult() && phoneNumChk){
			redirectAttributes.addFlashAttribute("ncn", sPersonalDto.getSvcCntrNo());
			return "redirect:" + PERSONAL_REDIRECT_URL.get(sPersonalDto.getPageType());
		}

		// 개인화 URL 세션 저장
		String personalUrl = request.getRequestURL().append("?").append(request.getQueryString()).toString();
		SessionUtils.setPersonalUrl(sPersonalDto.getPageType(), personalUrl);

		model.addAttribute("viewType", viewType);
		model.addAttribute("seq", sPersonalDto.getSeq());
		model.addAttribute("pageType", sPersonalDto.getPageType());
		model.addAttribute("landing", sPersonalDto.getLanding());
		model.addAttribute("maskedPhoneNum", StringMakerUtil.getPhoneNum(sPersonalDto.getCntrMobileNo()));
		return jspPageName;
	}

	/**
	 * 개인화 url SMS 인증번호 요청
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/sendSmsAjax.do", "/m/personal/sendSmsAjax.do"})
	@ResponseBody
	public Map<String, Object> sendSmsAjax(@ModelAttribute PersonalDto personalDto
																				,@RequestParam(value = "timeYn", required = false) String timeYn){

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 필수값 체크
		if(StringUtil.isEmpty(personalDto.getPageType()) || StringUtil.isEmpty(personalDto.getLanding()) || StringUtil.isEmpty(personalDto.getSeq())){
			rtnMap.put("RESULT_CODE", "00001");
			rtnMap.put("message", "비정상적인 접근입니다.");
			return rtnMap;
		}

		// 개인화 URL 정보 확인
		PersonalDto sPersonalDto = personalService.getPersonalUrlInfo(personalDto);
		if(sPersonalDto == null){
			rtnMap.put("RESULT_CODE", "00002");
			rtnMap.put("message", "개인화 URL 재발급 부탁드립니다.");
			return rtnMap;
		}

		if("Y".equals(timeYn)){

			// 시간연장
			AuthSmsDto authSmsDto = new AuthSmsDto();
			authSmsDto.setMenu(sPersonalDto.getPageType());
			AuthSmsDto authSession = SessionUtils.getPersonalAuthSmsBean(authSmsDto);

			String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
			authSmsDto.setStartDate(today);
			authSmsDto.setPhoneNum(sPersonalDto.getCntrMobileNo());
			authSmsDto.setMenu(sPersonalDto.getPageType());

			if(authSession != null) {
				authSmsDto.setAuthNum(authSession.getAuthNum());
			} else {
				authSmsDto.setAuthNum("");
			}

			SessionUtils.setPersonalAuthSmsSession(authSmsDto);
			rtnMap.put("RESULT_CODE", "00005");

		}else{

			// SMS 인증번호 발급
			AuthSmsDto authSmsDto = new AuthSmsDto();
			authSmsDto.setPhoneNum(sPersonalDto.getCntrMobileNo());
			authSmsDto.setMenu(sPersonalDto.getPageType());
			authSmsDto.setReserved02(sPersonalDto.getPageType());
			authSmsDto.setReserved03("nonMember");

			// resultCd : 발송성공[Y], 발송실패[N], 추가발송불가[D]
			String resultCd = personalService.sendPersonalSms(authSmsDto);

			if("D".equals(resultCd)) {
				rtnMap.put("RESULT_CODE", "00003");
				rtnMap.put("message", "문자 발송이 이미 완료 되었습니다.<br>수신된 문자를 확인 부탁 드립니다.");
				return rtnMap;
			}

			if("N".equals(resultCd)){
				rtnMap.put("RESULT_CODE", "00004");
				rtnMap.put("message", "인증 번호 발송에 실패했습니다.");
				return rtnMap;
			}

			rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

			if("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)){
				rtnMap.put("AUTH_NUM", authSmsDto.getAuthNum());
			}
		}

		return rtnMap;
	}

	/**
	 * 개인화 url SMS 인증번호 확인
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/checkSmsAuthAjax.do", "/m/personal/checkSmsAuthAjax.do"})
	@ResponseBody
	public Map<String, Object> checkSmsAuthAjax(@RequestParam(value = "certifySms") String certifySms
																						 ,@RequestParam(value = "pageType") String pageType){

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 필수값 체크
		if(StringUtil.isEmpty(certifySms) || StringUtil.isEmpty(pageType)){
			rtnMap.put("RESULT_CODE", "00001");
			rtnMap.put("message", "비정상적인 접근입니다.");
			return rtnMap;
		}

		// SMS 인증번호 확인
		AuthSmsDto authSmsDto = new AuthSmsDto();
		authSmsDto.setAuthNum(certifySms);
		authSmsDto.setMenu(pageType);

		SessionUtils.checkPersonalAuthSmsSession(authSmsDto);

		if(authSmsDto.isResult()){
			rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		}else{
			rtnMap.put("RESULT_CODE", "00002");
			rtnMap.put("message", authSmsDto.getMessage());
		}

		return rtnMap;
	}


	/**
	 * 개인화 url SMS 인증 완료
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/completeSmsAuth.do", "/m/personal/completeSmsAuth.do"})
	public String completeSmsAuth(@ModelAttribute PersonalDto personalDto
															 ,RedirectAttributes redirectAttributes){


		// 필수값 검사
		if(StringUtil.isEmpty(personalDto.getPageType()) || StringUtil.isEmpty(personalDto.getLanding()) || StringUtil.isEmpty(personalDto.getSeq())){
			throw new McpCommonException(F_BIND_EXCEPTION);
		}

		// SMS 인증 세션 조회
		AuthSmsDto authSmsDto = new AuthSmsDto();
		authSmsDto.setMenu(personalDto.getPageType());
		AuthSmsDto authSession = SessionUtils.getPersonalAuthSmsBean(authSmsDto);

		if(authSession == null || !authSession.isResult()){
			throw new McpCommonException(F_BIND_EXCEPTION);
		}

		// 발급된 개인화 URL 조회
		PersonalDto sPersonalDto = personalService.getPersonalUrlInfo(personalDto);
		if(sPersonalDto == null){
			throw new McpCommonException("개인화 URL 재발급 부탁드립니다.");
		}

		// 발급된 개인화 URL 소유자와 SMS인증 대상 일치여부
		boolean phoneNumChk = sPersonalDto.getCntrMobileNo().equals(authSession.getPhoneNum());
		if(!phoneNumChk){
			throw new McpCommonException(F_BIND_EXCEPTION);
		}

		// 리다이렉트
		redirectAttributes.addFlashAttribute("ncn", sPersonalDto.getSvcCntrNo());
		return "redirect:" + PERSONAL_REDIRECT_URL.get(sPersonalDto.getPageType());
	}

	/**
	 * 개인화 url 간편본인인증 검증
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = "/personal/certAuthAjax.do")
	@ResponseBody
	public Map<String, Object> personalCertAuthAjax(@ModelAttribute NiceLogDto niceLogDto
																								 ,@RequestParam(value = "ncn") String ncn
																								 ,@RequestParam(value = "pageType") String pageType){

		HashMap<String, Object> rtnMap = new HashMap<>();

		// 필수값 체크
		if(StringUtil.isEmpty(ncn)
			|| StringUtil.isEmpty(pageType)
			|| StringUtil.isEmpty(niceLogDto.getReqSeq())
			|| StringUtil.isEmpty(niceLogDto.getResSeq())
			|| StringUtil.isEmpty(niceLogDto.getParamR3())){

			throw new McpCommonJsonException("AUTH01", F_BIND_EXCEPTION);
		}

		// 본인인증 정보 조회
		NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
		if(niceLogRtn == null || StringUtil.isEmpty(niceLogRtn.getBirthDateDec()) || StringUtil.isEmpty(niceLogRtn.getName())){
			throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
		}

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(pageType, ncn);
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		// 이름과 생년월일 일치여부
		String niceName = niceLogRtn.getName().toUpperCase().replaceAll(" ", "");
		String niceBitrh = niceLogRtn.getBirthDateDec();  // yyyymmdd 또는 yymmdd

		String personalName = personalUser.getUserName().toUpperCase().replaceAll(" ", "");
		String personalBirth = personalUser.getUnUserSSn().substring(0, 6); // yymmdd

		if(!niceName.equals(personalName) || niceBitrh.indexOf(personalBirth) < 0){
			throw new McpCommonJsonException("AUTH03", NICE_CERT_EXCEPTION);
		}

		// 본인인증 세션 저장
		NiceResDto niceResDtoSess = new NiceResDto();
		niceResDtoSess.setReqSeq(niceLogRtn.getReqSeq());
		niceResDtoSess.setResSeq(niceLogRtn.getResSeq());
		niceResDtoSess.setAuthType(niceLogRtn.getAuthType());
		niceResDtoSess.setName(niceLogRtn.getName());
		niceResDtoSess.setBirthDate(niceLogRtn.getBirthDateDec());
		niceResDtoSess.setDupInfo(niceLogRtn.getDupInfo());
		niceResDtoSess.setConnInfo(niceLogRtn.getConnInfo());
		niceResDtoSess.setParam_r3(niceLogDto.getParamR3());
		niceResDtoSess.setGender(niceLogRtn.getGender());
		niceResDtoSess.setNationalInfo(niceLogRtn.getNationalInfo());
		niceResDtoSess.setsMobileNo(niceLogRtn.getsMobileNo());
		niceResDtoSess.setsMobileCo(niceLogRtn.getsMobileCo());

		if(CUST_AUTH.equals(niceLogDto.getParamR3())){
			SessionUtils.saveNiceRes(niceResDtoSess);
		}

		rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
		return rtnMap;
	}

}
