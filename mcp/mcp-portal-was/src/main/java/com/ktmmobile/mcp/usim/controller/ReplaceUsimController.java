package com.ktmmobile.mcp.usim.controller;

import com.ktmmobile.mcp.appform.dto.OsstUc0ReqDto;
import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.MSimpleOsstXmlUc0VO;
import com.ktmmobile.mcp.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mstory.dto.MstoryDto;
import com.ktmmobile.mcp.mstory.service.MstorySvc;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimDto;
import com.ktmmobile.mcp.usim.dto.ReplaceUsimSubDto;
import com.ktmmobile.mcp.usim.service.ReplaceUsimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.constants.Constants.EVENT_CODE_USIM_SELF_CHG;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class ReplaceUsimController {

	private static Logger logger = LoggerFactory.getLogger(ReplaceUsimController.class);

	@Autowired
	private ReplaceUsimService replaceUsimService;

	@Autowired
	private CertService certService;

	@Autowired
	private MypageService mypageService;

	@Autowired
	private AppformSvc appformSvc;

	@Autowired
	private MstorySvc mstorySvc;

	/** 교체 유심 신청 페이지 */
	@RequestMapping(value = {"/apply/replaceUsimView.do", "/m/apply/replaceUsimView.do"})
	public String replaceUsimView(ModelMap model){

		String isFreeUsimIng = NmcpServiceUtils.getCodeNm("Constant","isFreeUsimIng");
		if(!"Y".equals(isFreeUsimIng)){
			throw new McpCommonException("현재 다이렉트몰을 통한 교체 유심 신청이 불가합니다. 고객센터(114/무료)로 문의부탁드립니다.");
		}

		String jspPageName = "/portal/usim/replaceUsimView";
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/mobile/usim/replaceUsimView";
		}

		UserSessionDto userSession = SessionUtils.getUserCookieBean();
		String userName = userSession == null ? null : userSession.getName();
		model.addAttribute("userName", userName);

		SessionUtils.saveCurrentMenuUrlDto(null); // 메뉴세션 초기화
		SessionUtils.saveNiceRes(null); // 인증세션 초기화

		// 유심 교체 가이드 동영상
		MstoryDto mstoryDto = new MstoryDto();
		mstoryDto.setSnsCtgCd("SNS003");
		mstoryDto.setSnsCntpntCd("REUSIM");
		List<MstoryDto> list = mstorySvc.getSnsList(mstoryDto);
		model.addAttribute("guideVideo", list.isEmpty() ? null : list.get(0));

		return jspPageName;
	}

	/** 교체 유심 신청 AJAX */
	@RequestMapping("/apply/saveReplaceUsimRequest.do")
	@ResponseBody
	public Map<String, String> saveReplaceUsimRequest(ReplaceUsimDto replaceUsimDto){

		String isFreeUsimIng = NmcpServiceUtils.getCodeNm("Constant","isFreeUsimIng");
		if(!"Y".equals(isFreeUsimIng)){
			throw new McpCommonJsonException("F_OPERATION", "현재 다이렉트몰을 통한 교체 유심 신청이 불가합니다. 고객센터(114/무료)로 문의부탁드립니다.");
		}

		HashMap<String, String> rtnMap = new HashMap<>();

		// 약관 동의여부 확인
		if(!"Y".equals(replaceUsimDto.getAgrmYn())){
			throw new McpCommonJsonException("F_TERM", "필수약관에 동의해야 합니다.");
		}

		// 인증세션 확인
		NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
		if (sessNiceRes == null) {
			throw new McpCommonJsonException("F_AUTH", NICE_CERT_EXCEPTION_INSR);
		}

		// STEP 검증
		int dbStep = certService.getStepCnt();
		if(dbStep < 3){
			throw new McpCommonJsonException("F_STEP", STEP_CNT_EXCEPTION);
		}

		String[] certKey = {"urlType", "stepEndYn", "name", "ncType"};
		String[] certValue = {"saveReplaceUsimForm", "Y", replaceUsimDto.getCstmrName(), "0"};
		Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
		if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
			throw new McpCommonJsonException("F_STEP", vldReslt.get("RESULT_DESC"));
		}

		// 미성년자 (만 19세 미만) 제한
		int age = NmcpServiceUtils.getBirthDateToAge(sessNiceRes.getBirthDate(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		if (age < 19) {
			throw new McpCommonJsonException("F_AGE", "교체 유심 신청은 만 19세 이상 고객만 가능합니다.");
		}

		// M모바일 고객 여부 확인
		replaceUsimDto.setConnInfo(sessNiceRes.getConnInfo());
		String customerId = replaceUsimService.selectCustomerIdByConnInfo(replaceUsimDto);
		if(customerId == null){
			throw new McpCommonJsonException("F_MEMBER", "가입 정보 확인이 불가합니다.");
		}

		// T01 유심무상교체 접수 가능 여부조회 결과 확인
		if(replaceUsimDto.getOsstNo() < 1){
			throw new McpCommonJsonException("F_PRECHECK", "유심 교체 대상 여부를 확인해주세요.");
		}

		replaceUsimDto.setCustomerId(customerId);
		List<RetvUsimChgAcceptPsblVO> retvUsimChgAcceptPsblVOs = replaceUsimService.selectReplaceUsimReqInfo(replaceUsimDto);
		if(retvUsimChgAcceptPsblVOs == null || retvUsimChgAcceptPsblVOs.isEmpty()){
			throw new McpCommonJsonException("F_PRECHECK", "유심 교체 대상 여부를 확인해주세요.");
		}

		replaceUsimDto.setReqQnty(retvUsimChgAcceptPsblVOs.size());
		replaceUsimDto.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
		replaceUsimService.saveReplaceUsimRequest(replaceUsimDto, retvUsimChgAcceptPsblVOs);

		rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
		rtnMap.put("RESULT_MSG", "처리 성공");
		return rtnMap;
	}

	/** 교체 유심 셀프 변경 페이지 */
	@RequestMapping("/m/apply/replaceUsimSelf.do")
	public String replaceUsimSelf(){

		// 인증세션 초기화
		SessionUtils.saveNiceRes(null);
		return "/mobile/usim/replaceUsimSelf";
	}

	/** 교체 유심 셀프 변경 AJAX */
	@RequestMapping("/apply/replaceUsimSelfAjax.do")
	@ResponseBody
	public Map<String, String> replaceUsimSelfAjax(ReplaceUsimDto replaceUsimDto){

		HashMap<String, String> rtnMap = new HashMap<>();

		// 인증세션 확인
		NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
		if (sessNiceRes == null) {
			throw new McpCommonJsonException("F_AUTH", NICE_CERT_EXCEPTION_INSR);
		}

		// STEP 검증
		int dbStep = certService.getStepCnt();
		if(dbStep < 3){
			throw new McpCommonJsonException("F_STEP", STEP_CNT_EXCEPTION);
		}

		String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "reqUsimSn"};
		String[] certValue = {"replaceUsimSelf", "Y", replaceUsimDto.getCstmrName(), replaceUsimDto.getCstmrTel(), replaceUsimDto.getIccId()};
		Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);

		if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
			throw new McpCommonJsonException("F_STEP", vldReslt.get("RESULT_DESC"));
		}

		// UC0 정보 조회
		McpUserCntrMngDto contractInfo = replaceUsimService.selectContractInfoForReplaceUsim(replaceUsimDto);
		if(contractInfo == null){
			throw new McpCommonJsonException("F_MEMBER", "고객정보가 존재하지 않습니다.");
		}

		OsstUc0ReqDto osstUc0ReqDto = new OsstUc0ReqDto();
		osstUc0ReqDto.setIccId(replaceUsimDto.getIccId());
		osstUc0ReqDto.setDivCd("NONMEMBER");
		osstUc0ReqDto.setSvcContId(contractInfo.getSvcCntrNo());
		osstUc0ReqDto.setCntpntCd(contractInfo.getOpenAgntCd());
		osstUc0ReqDto.setCustNo(contractInfo.getCustomerId());
		osstUc0ReqDto.setTlphNo(contractInfo.getCntrMobileNo());

		// UC0 유심변경
		MSimpleOsstXmlUc0VO simpleOsstXmlVO = null;

		try {
			simpleOsstXmlVO = appformSvc.sendOsstUc0Service(osstUc0ReqDto, EVENT_CODE_USIM_SELF_CHG);
			if ("S".equals(simpleOsstXmlVO.getRsltCd())) {
				rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
				rtnMap.put("MVNO_ORD_NO", simpleOsstXmlVO.getMvnoOrdNo());
			} else {
				rtnMap.put("RESULT_CODE", "F_MPLATFORM_01");
				rtnMap.put("RESULT_MSG", "유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
			}
		} catch (McpMplatFormException e) {
			rtnMap.put("RESULT_CODE", "F_MPLATFORM_02");
			rtnMap.put("RESULT_MSG", "유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
			rtnMap.put("RESULT_MSG_DTL", "response massage is null.");
		} catch (SocketTimeoutException e) {
			rtnMap.put("RESULT_CODE", "F_MPLATFORM_03");
			rtnMap.put("RESULT_MSG", "유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
			rtnMap.put("RESULT_MSG_DTL", "SocketTimeout");
		} catch (SelfServiceException e) {
			rtnMap.put("RESULT_CODE", "F_MPLATFORM_04");
			rtnMap.put("RESULT_MSG_DTL", e.getMessageNe());

			if("MCG_BIZ_E0422".equals(e.getResultCode())){
				// 일별 호출 제한
				rtnMap.put("RESULT_MSG", "현재 요청이 너무 많아 유심 셀프변경 신청이 제한되었습니다. 익일 다시 이용해주세요.");
			}else if("MCG_BIZ_E0421".equals(e.getResultCode())){
				// 분당 호출 제한
				rtnMap.put("RESULT_MSG", "현재 요청이 너무 많아 잠시 제한되었습니다. 잠시 후 다시 시도해주세요.");
			}else{
				rtnMap.put("RESULT_MSG", "유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
			}
		}

		return rtnMap;
	}

	/** 유심 교체 신청가능 정보 확인 */
	@RequestMapping("/apply/checkReplaceUsimRequestUser.do")
	@ResponseBody
	public Map<String, Object> checkReplaceUsimRequestUser(){

		Map<String, Object> rtnMap = new HashMap<>();

		// 인증세션 확인
		NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
		if (sessNiceRes == null) {
			throw new McpCommonJsonException("F_AUTH", NICE_CERT_EXCEPTION_INSR);
		}

		// 미성년자 (만 19세 미만) 제한
		int age = NmcpServiceUtils.getBirthDateToAge(sessNiceRes.getBirthDate(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
		if (age < 19) {
			throw new McpCommonJsonException("F_AGE", "교체 유심 신청은 만 19세 이상 고객만 가능합니다.");
		}

		// M모바일 고객 여부 확인
		ReplaceUsimDto replaceUsimDto = new ReplaceUsimDto();
		replaceUsimDto.setCstmrName(sessNiceRes.getName());
		replaceUsimDto.setConnInfo(sessNiceRes.getConnInfo());

		String customerId = replaceUsimService.selectCustomerIdByConnInfo(replaceUsimDto);
		if(customerId == null){
			throw new McpCommonJsonException("F_MEMBER", "가입 정보 확인이 불가합니다.");
		}

		// 유심 신청 가능여부 조회
		List<ReplaceUsimSubDto> replaceUsimSubDtos = replaceUsimService.selectReplaceUsimSubInfo(customerId);
		if(replaceUsimSubDtos == null || replaceUsimSubDtos.isEmpty()){
			throw new McpCommonJsonException("F_MEMBER", "가입 정보 확인이 불가합니다.");
		}

		rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
		rtnMap.put("RESULT_MSG", "처리 성공");
		rtnMap.put("replaceUsimSubDtos", replaceUsimSubDtos);
		rtnMap.put("osstNo", replaceUsimSubDtos.get(0).getOsstNo() + "");
		return rtnMap;
	}

}
