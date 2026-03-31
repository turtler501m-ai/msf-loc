package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgDataSharingReqDto;

import java.util.Map;

/**
 * 데이터쉐어링 서비스.
 * ASIS MyShareDataSvcImpl 역할: X69 사전체크, X71 목록, X70 가입/해지.
 */
public interface SvcChgDataSharingSvc {

    /** X71 데이터쉐어링 결합 중인 대상 조회. */
    Map<String, Object> list(SvcChgDataSharingReqDto req);

    /** X69 데이터쉐어링 사전체크 (가입 가능 여부). */
    Map<String, Object> check(SvcChgDataSharingReqDto req);

    /** X70 데이터쉐어링 가입 (opmdWorkDivCd=A). */
    Map<String, Object> join(SvcChgDataSharingReqDto req);

    /** X70 데이터쉐어링 해지 (opmdWorkDivCd=C). */
    Map<String, Object> cancel(SvcChgDataSharingReqDto req);
}
