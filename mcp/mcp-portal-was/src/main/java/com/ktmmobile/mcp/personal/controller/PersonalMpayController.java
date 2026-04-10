package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MoscWireUseTimeInfoRes;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.MPayViewDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MPayViewService;
import com.ktmmobile.mcp.personal.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Controller
public class PersonalMpayController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalMpayController.class);

	private static final String PAGE_TYPE = "MPAY";
	private static final String REDIRECT_URL = "/personal/auth.do";

	@Autowired
	private PersonalService personalService;

	@Autowired
	private MPayViewService mPayViewService;

	@Autowired
	private FCommonSvc fCommonSvc;

	@Autowired
	private FarPricePlanService farPricePlanService;


	/**
	 * 개인화 url - 소액결제 조회 및 한도변경 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/mPayView.do", "/m/personal/mPayView.do"})
	public String personalMPayView(@ModelAttribute(value = "ncn") String ncn
																,ModelMap model){

		String jspPageName = "/personal/portal/pMpayView";
		if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pMpayView";
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

		// 개통일로부터 날짜필터 조회
		String openingDate = personalUser.getLstComActvDate().substring(0,6);
		List<Map<String,String>> dateList= mPayViewService.getDateListFromOpening(openingDate);

		// 소액결제 상태 공통코드 조회
		NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
		nmcpMainCdDtlDto.setCdGroupId(Constants.MPAY_STATUS_CD);
		List<NmcpCdDtlDto> mPayStatusCd = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

		model.addAttribute("dateList", dateList);
		model.addAttribute("mPayStatusCd", mPayStatusCd);
		model.addAttribute("searchVO", searchVO);
		return jspPageName;
	}

	/**
	 * 개인화 url - 소액결제 내역 조회
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/selectMPayListAjax.do"})
	@ResponseBody
	public Map<String, Object> selectMPayListAjax(@ModelAttribute("mPayViewDto") MPayViewDto mPayViewDto) {

		Map<String, Object> rtnMap = new HashMap<>();

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, mPayViewDto.getSvcCntrNo());
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		// 소액결제 내역 조회
		List<MPayViewDto> mPayList = mPayViewService.selectMPayList(mPayViewDto);
		String isEmpty= (mPayList == null || mPayList.isEmpty()) ? "Y" : "N";

		// 소액결제 한도 조회
		String tmonLmtAmt = mPayViewService.getTmonLmtAmt(mPayViewDto);

		rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		rtnMap.put("isEmpty", isEmpty);
		rtnMap.put("mPayList", mPayList);
		rtnMap.put("tmonLmtAmt", StringUtil.NVL(tmonLmtAmt, "0"));
		return rtnMap;
	}

	/**
	 * 개인화 url - 소액결제 한도변경 가능여부 조회
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/possibleChangeMpayLimitAjax.do"})
	@ResponseBody
	public Map<String, Object> possibleChangeMpayLimitAjax(@RequestParam(value = "ncn") String ncn){

		Map<String, Object> rtnMap = new HashMap<>();

		// 개인화 URL SMS인증 완료 사용자 계약정보 조회
		McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
		if(personalUser == null){
			throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
		}

		/* [MP] 회선 사용기간조회 (X83) */
		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		try{
			MoscWireUseTimeInfoRes moscWireUseTimeInfoRes = farPricePlanService.moscWireUseTimeInfo(svcCntrNo, ctn, customerId);
			String realUseDayNum = StringUtil.NVL(moscWireUseTimeInfoRes.getRealUseDayNum(), "0");  // 실사용기간
			int realUseDayInt = Integer.parseInt(realUseDayNum, 10);

			// 실사용일 61일 이상 고객만 가능
			if(realUseDayInt < 61){
				rtnMap.put("resultCd", "F");
				rtnMap.put("resultMsg", "개통일로부터 실사용일 60일 이하 고객님께서는 고객센터(114/무료)를 통해 한도 변경을 신청 바랍니다.");
			}else{
				rtnMap.put("resultCd", "S");
				rtnMap.put("resultMsg", "한도 변경 가능합니다.");
			}

		}catch(McpCommonJsonException | McpMplatFormException | SelfServiceException e){
			rtnMap.put("resultCd", "F");
			rtnMap.put("resultMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
		}

		rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
		return rtnMap;
	}

}
