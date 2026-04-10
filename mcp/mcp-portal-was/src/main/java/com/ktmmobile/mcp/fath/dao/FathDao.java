package com.ktmmobile.mcp.fath.dao;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.fath.dto.FathDto;
import com.ktmmobile.mcp.fath.dto.FathFormInfo;
import com.ktmmobile.mcp.mypage.dto.MyNameChgReqDto;


public interface FathDao {
	
	/*셀프안면인증 URL 정보 조회*/
	FathDto getFathSelfUrl(String uuid);
	
	/*안면인증 결과 PUSH 조회*/
	FathDto selectFathResltPush(String fathTransacId);
	
	/*안면인증 트랜잭션 ID 업데이트*/
	void updateFathTransacId(AppformReqDto AppformReqDto);
	/*안면인증 인증일자 업데이트*/
	void updateFathCmpltNtfyDt(AppformReqDto AppformReqDto);

	/*셀프안면인증 신청서정보조회*/
	FathFormInfo getFathAppForm(String ResNo);

	/*명의변경 신청서정보조회*/
	FathFormInfo getNameChgInfo(String ResNo);

	/*고객신분증정보 변경*/
	void updateSelfCertInfo(AppformReqDto AppformReqDto);
	/*고객신분증정보 변경(법정대리인)*/
	void updateSelfCertInfoMinor(AppformReqDto AppformReqDto);

	void updateNameChgFathTransacId(AppformReqDto appformReqDto);

	void updateNameChgComplete(AppformReqDto appformReqDto);

	String getCpntId(String cntpntShopId);

	/*(명의변경)고객신분증정보 변경*/
	void updateNameChgSelfCertInfo(MyNameChgReqDto myNameChgReqDto);
	/*(명의변경)고객신분증정보 변경(법정대리인)*/
	void updateNameChgSelfCertInfoMinor(MyNameChgReqDto myNameChgReqDto);

	/*임시예약번호 추출*/
	public String generateTempResNo();

	/*예약번호 업데이트*/
	public void updateFathMcpRequestOsst(FathDto fathDto);

	int updateNameChgFathSkip(String resNo);
}
