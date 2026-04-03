//////////package com.ktmmobile.mcp.cert.service;
//////////
//////////import com.ktmmobile.mcp.cert.dto.CertDto;
//////////
//////////import javax.servlet.http.HttpServletRequest;
//////////import java.util.Map;
//////////
//////////public interface CertService {
//////////	
//////////	//세션의 crtSeq로 스탭개수 조회
//////////	public int getStepCnt();
//////////	
//////////	//인증정보 비교해서 map으로 리턴
//////////	public Map<String, Object> getCertInfo(CertDto certDto);
//////////	
//////////	//인증 개선 대상 url인지 검사
//////////	public Map<String, String> isAuthStepApplyUrl(HttpServletRequest request);
//////////	
//////////	//eSim/eSimWatch에서 새로고침해서 appForm.do로 돌아갔을 때 일정 스텝 이상 삭제
//////////	public int delStepInfo(int step);
//////////
//////////	//한 스텝의 referer update
//////////	public int updateCrtReferer();
//////////
//////////	//메인스텝
//////////	public int getModuTypeStepCnt(String moduType, String ncType);
//////////
//////////	/**
//////////	 * cert 비교 공통 함수
//////////	 * @param compType 비교유형
//////////	 * @param keyArr   필수항목명
//////////	 * @param valueArr 필수항목값
//////////	 * @return Map<String,String>
//////////	 */
//////////	Map<String,String> vdlCertInfo(String compType, String[] keyArr, String[] valueArr);
//////////
//////////	/**
//////////	 * cert 대상 sms 인증 메뉴
//////////	 * @param menuType sms메뉴타입
//////////	 * @param smsAuthType sms url유형
//////////	 * @return Map<String,String>
//////////	 */
//////////	Map<String, String> smsAuthCertMenuType(String menuType, String smsAuthType);
//////////
//////////	// ncType조회 (0: 청소년, "": 내국인 또는 외국인)
//////////	String getNcTypeForCrt(String userSsn, String contractNum);
//////////
//////////	// crtSeq에 해당하는 referer 조회
//////////	String getCertReferer(long crtSeq);
//////////}
