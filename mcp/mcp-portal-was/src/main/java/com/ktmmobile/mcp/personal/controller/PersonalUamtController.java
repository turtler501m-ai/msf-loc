package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelTotalUseTimeDto;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.FarPricePlanResDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.CallRegService;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.personal.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Controller
public class PersonalUamtController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalUamtController.class);

	private static final String PAGE_TYPE = "UAMT";
	private static final String REDIRECT_URL = "/personal/auth.do";

	@Autowired
	private PersonalService personalService;

	@Autowired
	private MypageService mypageService;

	@Autowired
	private CallRegService callRegService;

	@Autowired
	private FarPricePlanService farPricePlanService;


	/**
	 * 개인화 url - 이용량 조회 페이지
	 * @Date : 2025.05.26
	 */
	@RequestMapping(value = {"/personal/callView01.do", "/m/personal/callView01.do"})
	public String personalCallView01(@ModelAttribute(value = "ncn") String ncn
																	,@RequestParam(value = "useMonth", required = false) String useMonth
																	,ModelMap model){

		String jspPageName = "/personal/portal/pCallView01";
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			jspPageName = "/personal/mobile/pCallView01";
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

		// 요금제 조회
		McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(personalUser.getContractNum());
		if(mcpFarPriceDto == null){
			throw new McpCommonException(COMMON_EXCEPTION);
		}

		// 요금제 상세정보 조회
		FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
		if(farPricePlanResDto != null) {
			mcpFarPriceDto.setRateAdsvcLteDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(),"-"));
			mcpFarPriceDto.setRateAdsvcCallDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(),"-"));
			mcpFarPriceDto.setRateAdsvcSmsDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(),"-"));
		}

		/* [MP] 총 통화시간 조회 (X12) */
		String svcCntrNo = personalUser.getSvcCntrNo();
		String ctn = personalUser.getCntrMobileNo();
		String customerId = personalUser.getCustomerId();

		MpTelTotalUseTimeDto vo = callRegService.telTotalUseTimeDto(svcCntrNo, ctn, customerId, useMonth);
		vo.setStrSvcNameSms(mcpFarPriceDto.getPrvRateGrpNm());

		// 조회 날짜 설정
		String openDate = personalUser.getLstComActvDate().substring(0,6);
		List<String> todayList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String toDay = formatter.format(currentTime);
		String lastDay = "";

		try{

			lastDay = DateTimeUtil.lastDayOfMonth(toDay);

			// 당월 시작일 ~ 현재일
			String startDate = DateTimeUtil.getFormatString(lastDay.substring(0,4) + lastDay.substring(4,6) + "01", "yyyyMMdd", "yyyy.MM.dd");
			String endDate = DateTimeUtil.getFormatString(DateTimeUtil.getShortDateString(), "yyyyMMdd", "yyyy.MM.dd");
			todayList.add(startDate + "~" + endDate);

			// 한달전 시작일 ~ 마지막일
			String prvDate1= DateTimeUtil.addMonths(lastDay, -1);
			if(openDate.compareTo(prvDate1.substring(0,6)) <= 0){
				String prvStartDate1 = DateTimeUtil.getFormatString(prvDate1.substring(0,4) + prvDate1.substring(4,6) + "01", "yyyyMMdd", "yyyy.MM.dd");
				String prvEndDate1 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate1), "yyyyMMdd", "yyyy.MM.dd");
				todayList.add(prvStartDate1 + "~" + prvEndDate1);
			}

			// 두달전 시작일 ~ 마지막일
			String prvDate2= DateTimeUtil.addMonths(lastDay, -2);
			if(openDate.compareTo(prvDate2.substring(0,6)) <= 0){
				String prvStartDate2 = DateTimeUtil.getFormatString(prvDate2.substring(0,4) + prvDate2.substring(4,6) + "01", "yyyyMMdd", "yyyy.MM.dd");
				String prvEndDate2 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate2), "yyyyMMdd", "yyyy.MM.dd");
				todayList.add(prvStartDate2 + "~" + prvEndDate2);
			}

			// 세달전 시작일 ~ 마지막일
			String prvDate3= DateTimeUtil.addMonths(lastDay, -3);
			if(openDate.compareTo(prvDate3.substring(0,6)) <= 0){
				String prvStartDate3 = DateTimeUtil.getFormatString(prvDate3.substring(0,4) + prvDate3.substring(4,6) + "01", "yyyyMMdd", "yyyy.MM.dd");
				String prvEndDate3 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate3), "yyyyMMdd", "yyyy.MM.dd");
				todayList.add(prvStartDate3 + "~" + prvEndDate3);
			}

		}catch(ParseException e){
			throw new McpCommonException(COMMON_EXCEPTION);
		}

		model.addAttribute("todayList", todayList);
		model.addAttribute("vo", vo);
		model.addAttribute("data", vo.getItemTelVOListData());
		model.addAttribute("voice", vo.getItemTelVOListVoice());
		model.addAttribute("sms", vo.getItemTelVOListSms());
		model.addAttribute("roaming", vo.getItemTelVOListRoaming());
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("useTimeSvcLimit", vo.isUseTimeSvcLimit());
		model.addAttribute("mcpFarPriceDto", mcpFarPriceDto);
		model.addAttribute("useMonth", useMonth);
		return jspPageName;
	}

}
