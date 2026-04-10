package com.ktmmobile.mcp.app.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.app.mapper.AppMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.cmmn.util.ObjectUtils;
import com.ktmmobile.mcp.mypage.dto.JoinDto;
import com.ktmmobile.mcp.sms.dto.ApiMapDto;

@RestController
public class AppController {

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	AppMapper appMapper;

	/**
	 * 사용자 요금제 정보를 가지고 온다.
	 * @param contractNum
	 * @return
	 */
	@RequestMapping(value = "/app/usrRateInfo", method = {RequestMethod.POST,RequestMethod.GET})
	public Map<String, String> usrRateInfo(@RequestBody String contractNum) {

		Map<String, String> userRateInfo = null;

		try {
			userRateInfo = appMapper.selectUsrRateInfo(contractNum);
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return userRateInfo;
	}

	
	/**
	 * P_APP_SMS function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppSms", method = RequestMethod.POST)
	public void pAppSms(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("subscriberNo", apiMapDto.getSubscriberNo());
	        paramMap.put("userName", apiMapDto.getUserName());
	        paramMap.put("smsAuthCode", apiMapDto.getSmsAuthCode());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("appType", apiMapDto.getAppType());
	        paramMap.put("appVer", apiMapDto.getAppVer());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("smsSeq", apiMapDto.getSmsSeq());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppSms(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}


	
	/**
	 * P_APP_USER_INFO function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppUserInfo", method = RequestMethod.POST)
	public void pAppUserInfo(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("contractNum", apiMapDto.getContractNum());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("sessionId", apiMapDto.getSessionId());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppUserInfo(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}

	
	/**
	 * P_APP_CHG_VAC function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppChgVac", method = RequestMethod.POST)
	public void pAppChgVac(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("contractNum", apiMapDto.getContractNum());
	        paramMap.put("bankCd", apiMapDto.getBankCd());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("sessionId", apiMapDto.getSessionId());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppChgVac(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}

	
	/**
	 * P_APP_PP_REQ function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppPpReq", method = RequestMethod.POST)
	public void pAppPpReq(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("contractNum", apiMapDto.getContractNum());
	        paramMap.put("pinNo", apiMapDto.getPinNo());
	        paramMap.put("mngCd", apiMapDto.getMngCd());
	        paramMap.put("ccdNo", apiMapDto.getCcdNo());
	        paramMap.put("ccdExpire", apiMapDto.getCcdExpire());
	        paramMap.put("ccdPW", apiMapDto.getCcdPW());
	        paramMap.put("ccdBirth", apiMapDto.getCcdBirth());
	        paramMap.put("ccdRcgAmt", apiMapDto.getCcdRcgAmt());
	        paramMap.put("ccdPayAmt", apiMapDto.getCcdPayAmt());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("sessionId", apiMapDto.getSessionId());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppPpReq(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}

	
	/**
	 * P_APP_PP_RES function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppPpRes", method = RequestMethod.POST)
	public void pAppPpRes(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("contractNum", apiMapDto.getContractNum());
	        paramMap.put("rcgReq", apiMapDto.getRcgReq());
	        paramMap.put("rcgAmt", apiMapDto.getRcgAmt());
	        paramMap.put("payAmt", apiMapDto.getPayAmt());
	        paramMap.put("cardPayCode", apiMapDto.getCardPayCode());
	        paramMap.put("cardErrMsg", apiMapDto.getCardErrMsg());
	        paramMap.put("cardOrderNum", apiMapDto.getCardOrderNum());
	        paramMap.put("cardAmount", apiMapDto.getCardAmount());
	        paramMap.put("cardDouTrx", apiMapDto.getCardDouTrx());
	        paramMap.put("cardAuthNo", apiMapDto.getCardAuthNo());
	        paramMap.put("cardAuthDate", apiMapDto.getCardAuthDate());
	        paramMap.put("cardNointFlag", apiMapDto.getCardNointFlag());
	        paramMap.put("cardCpName", apiMapDto.getCardCpName());
	        paramMap.put("cardCpUrl", apiMapDto.getCardCpUrl());
	        paramMap.put("cardDouRsv1", apiMapDto.getCardDouRsv1());
	        paramMap.put("cardDouRsv2", apiMapDto.getCardDouRsv2());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("sessionId", apiMapDto.getSessionId());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppPpRes(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}

	
	/**
	 * P_APP_PP_RST function 호출
	 * @param apiMapDto
	 * @return
	 */
	@RequestMapping(value = "/appPps/pAppPpRst", method = RequestMethod.POST)
	public void pAppPpRst(@RequestBody ApiMapDto apiMapDto) {


		try {

			HashMap<String,Object> paramMap = new HashMap<String,Object>();

	        paramMap.put("opCode", apiMapDto.getOpCode());
	        paramMap.put("rcgReq", apiMapDto.getRcgReq());
	        paramMap.put("LANG", apiMapDto.getLANG());
	        paramMap.put("TMP", apiMapDto.getTMP());
	        paramMap.put("appType", apiMapDto.getAppType());
	        paramMap.put("appVer", apiMapDto.getAppVer());
	        paramMap.put("iUrl", apiMapDto.getiUrl());
	        paramMap.put("accessIp", apiMapDto.getAccessIp());
	        paramMap.put("sessionId", apiMapDto.getSessionId());
			paramMap.put("O_CODE","");
			paramMap.put("O_MSG","");
			paramMap.put("O_DATA","");

			appMapper.pAppPpRst(paramMap);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
	}

}
