package com.ktis.msp.rcp.rcpMgmt.mapper;

import com.ktis.msp.rcp.rcpMgmt.vo.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.Map;


@Mapper("FathMapper")
public interface FathMapper {
		
	/**
	 * 이미 발급된 URL 중 유효한 것이 있는지 조회
	 */
	FathVO selectFathSelfUrl(String resNo);

	/**
	 * 셀프안면인증 URL 발급
	 */
	void insertFathSelfUrl(FathVO fathVO);

	/**
	 * 안면인증 연락처 변경(명의변경)
	 */
	void updateNameChgFathMobile(FathVO fathVO);
	
	/**
	 * 안면인증 연락처 변경(명의변경 외)
	 */
	void updateFathMobile(FathVO fathVO);
	
	/**
	 * 안면인증 대상여부 업데이트
	 */
	void updateFathTrgYn(RcpDetailVO rcpDetailVO);

	/**
	 * 본인인증유형 업데이트
	 */
	void updateSelfCertType(RcpDetailVO rcpDetailVO);

	/**
	 * 본인인증유형 업데이트(법정대리인)
	 */
	void updateSelfCertTypeMinor(RcpDetailVO rcpDetailVO);

	/**
	 * 안면인증 트랜잭션 ID 업데이트
	 */
	void updateFathTransacId(RcpDetailVO rcpDetailVO);

	/**
	 * 안면인증 인증일자 업데이트
	 */
	void updateFathCmpltNtfyDt(RcpDetailVO rcpDetailVO);

	/**
	 * 안면인증 정보조회
	 */
	Map<String, String> selectFathInfo(String resNo);

    /**
     * 안면인증 정보조회
     */
    Map<String, String> selectFathInfoMcn(String mcnResNo);
	
	/**
	 * 안면인증 결과 PUSH 조회
	 */
	FathVO selectFathResltPush(String fathTransacId);

	
	/**
	 * 신청서 진행상태 변경
	 */
	void updateReqStateCode(RcpDetailVO rcpDetailVO);
	
	/**
	 * Acen 업무유형 조회
	 */
	String selectAcenEvntType(String resNo);

    /**
     * 명의변경 정보 업데이트
     */
    void updateNameChgInfo(RcpDetailVO rcpDetailVO);

    /**
     * 명의변경 신청서 정보 조회
     */
    Map<String, String> getNameChgInfo(String mcnResNo);

	/**
	 * 신분증 정보 변경(법정대리인)
	 */
	void updateSelfCertInfoMinor(RcpDetailVO rcpDetailVO);
	
	/**
	 * 신분증 정보 변경
	 */
	void updateSelfCertInfo(RcpDetailVO rcpDetailVO);
	/**
	 * 신분증 정보 변경(법정대리인)
	 */
	void updateNameChgSelfCertTypeMinor(RcpDetailVO rcpDetailVO);
	
	/**
	 * 신분증 정보 변경
	 */
	void updateNameChgSelfCertType(RcpDetailVO rcpDetailVO);
	
	/**
	 * (대리점)안면인증 가능 접점ID조회
	 */
	String getCpntId(String cntpntShopId);
	
	
	/**
	 * 명의변경 신분증 정보 변경(법정대리인)
	 */
	void updateNameChgSelfCertInfoMinor(RcpDetailVO rcpDetailVO);
	
	/**
	 * 명의변경 신분증 정보 변경
	 */
	void updateNameChgSelfCertInfo(RcpDetailVO rcpDetailVO);

	int updateNameChgInfoFathSkip(RcpDetailVO rcpDetailVO);

	boolean isEnabledFT1();
}
