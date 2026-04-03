package com.ktmmobile.msf.system.faceauth.service;

import com.ktmmobile.msf.form.newchange.dto.AppformReqDto;
import com.ktmmobile.msf.form.newchange.dto.OsstFathReqDto;
import com.ktmmobile.msf.system.faceauth.dto.FathDto;
import com.ktmmobile.msf.system.faceauth.dto.FathFormInfo;
import com.ktmmobile.msf.system.faceauth.dto.FathSessionDto;

import java.util.HashMap;

public interface FathService {
	/*안면인증 사용여부 조회*/
	String getFathUseYn();

	/*셀프안면인증 URL 정보 조회*/
	FathDto getFathSelfUrl(String uuid);
	/*안면인증 결과 PUSH 조회*/
	FathDto selectFathResltPush(String fathTransacId);
	
	FathFormInfo getFathFormInfo(String resNo, String operType);

	void updateFathTransacIdByResNo(AppformReqDto appformReqDto);

	void updateFathCompleteInfo(AppformReqDto appformReqDto);

	void sendMsgFathUrl(String urlAdr, String smsRcvTelNo);

	String findAndSaveSessionCpntId(String cntpntShopId);
	
	String generateTempResNo();
	void updateFathMcpRequestOsst(String resNo);
	FathSessionDto validateFathSession();

	HashMap<String, Object> fathResltRtn(String resltCd, OsstFathReqDto osstFathReqDto);
	HashMap<String, Object> fathSuccRtn(FathDto fathDto, OsstFathReqDto osstFathReqDto);
	HashMap<String, Object> fathFailRtn(OsstFathReqDto osstFathReqDto);

	void updateNameChgFathSkip(String resNo);
}
