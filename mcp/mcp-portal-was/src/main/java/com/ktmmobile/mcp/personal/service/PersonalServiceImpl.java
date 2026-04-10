package com.ktmmobile.mcp.personal.service;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpErropPageException;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.personal.dao.PersonalDao;
import com.ktmmobile.mcp.personal.dto.PersonalDto;
import com.ktmmobile.mcp.point.dao.MstoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
public class PersonalServiceImpl implements PersonalService {

	private static Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Value("${SERVER_NAME}")
	private String serverName;

	@Autowired
	private SmsSvc smsSvc;

	@Autowired
	private PersonalDao personalDao;


	/** 발급된 개인화 URL 정보 조회 */
	@Override
	public PersonalDto getPersonalUrlInfo(PersonalDto personalDto) {

		// 발급된 개인화 URL 조회
		PersonalDto urlInfo = personalDao.getPersonalUrl(personalDto);
		if(urlInfo == null){
			return null;
		}

		// 계약 현행화 정보 조회
		McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
		userCntrMngDto.setSvcCntrNo(urlInfo.getSvcCntrNo());

		RestTemplate restTemplate = new RestTemplate();
		McpUserCntrMngDto userDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

		if(userDto == null || !userDto.getContractNum().equals(urlInfo.getContractNum())){
			return null;
		}

		urlInfo.setCntrMobileNo(userDto.getCntrMobileNo());
		return urlInfo;
	}

	/** 개인화 url SMS 인증번호 요청 */
	@Override
	public String sendPersonalSms(AuthSmsDto authSmsDto) {

		// resultCd : 발송성공[Y], 발송실패[N], 추가발송불가[D]
		String resultCd = "Y";

		RestTemplate restTemplate = new RestTemplate();

		// SMS 전송 제한여부 확인
		List<NmcpCdDtlDto> nmcpCdDtlDtos  = NmcpServiceUtils.getCodeList("SMSRESTRICT");
		int adMin = Integer.parseInt(nmcpCdDtlDtos.stream().filter(dtlCd -> dtlCd.getDtlCd().equals("smsMin")).findFirst().get().getExpnsnStrVal1());
		int adCount = Integer.parseInt(nmcpCdDtlDtos.stream().filter(dtlCd -> dtlCd.getDtlCd().equals("smsCnt")).findFirst().get().getExpnsnStrVal1());

		AuthSmsDto searchDto = new AuthSmsDto();
		searchDto.setPhoneNum(authSmsDto.getPhoneNum());
		searchDto.setLimitMin(String.valueOf(adMin));

		if("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)){
			searchDto.setIsReal("N");
		}else{
			searchDto.setIsReal("Y");
		}

		int stackCount = restTemplate.postForObject(apiInterfaceServer + "/sms/qStackNewCnt", searchDto, Integer.class);
		if(adCount <= stackCount) {
			return "D"; // 추가 문자발송 불가
		}

		// SMS 인증번호 발송
		String authSmsNo = "";

		try {
			Map<String, Object> sendMap = smsSvc.sendSmsForAuth(authSmsDto.getPhoneNum(), false, null, authSmsDto.getReserved02(), authSmsDto.getReserved03());
			authSmsNo = (String) sendMap.get("sNumber");
		} catch(Exception e) {
			return "N"; // 문자발송 실패
		}

		String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		authSmsDto.setStartDate(today);
		authSmsDto.setAuthNum(authSmsNo);
		SessionUtils.setPersonalAuthSmsSession(authSmsDto);

		return resultCd;
	}

	/** 개인화 URL SMS인증 완료 사용자 계약정보 조회 */
	@Override
	public McpUserCntrMngDto getPersonalUser(String pageType, String ncn) {

		if(StringUtil.isEmpty(pageType) || StringUtil.isEmpty(ncn)){
			return null;
		}

		// SMS 인증 세션 조회
		AuthSmsDto authSmsDto = new AuthSmsDto();
		authSmsDto.setMenu(pageType);
		AuthSmsDto authSession = SessionUtils.getPersonalAuthSmsBean(authSmsDto);

		if(authSession == null || !authSession.isResult()){
			return null;
		}

		// 계약 현행화 정보 조회
		McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
		userCntrMngDto.setSvcCntrNo(ncn);

		RestTemplate restTemplate = new RestTemplate();
		McpUserCntrMngDto userDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);

		if(userDto == null || !userDto.getCntrMobileNo().equals(authSession.getPhoneNum())){
			return null;
		}

		return userDto;
	}

}
