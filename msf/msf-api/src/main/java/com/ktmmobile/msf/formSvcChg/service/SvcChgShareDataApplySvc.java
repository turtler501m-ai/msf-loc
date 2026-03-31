package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgShareDataApplyReqDto;

import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 서비스.
 * ASIS AppformController.saveDataSharingSimple(4001-03) / saveDataSharingStep1~3(4001-08~11)
 * 역할을 단계별 REST API 로 분리.
 */
public interface SvcChgShareDataApplySvc {

    /**
     * [Step0] 신청서 저장.
     * ASIS: saveDataSharingSimple / fnSetDataOfdataSharing
     * - MSF_REQUEST INSERT + MSF_REQUEST_OSST INSERT
     * - 응답: { success, requestKey, resNo }
     */
    Map<String, Object> apply(SvcChgShareDataApplyReqDto req);

    /**
     * [Step1] PC0 사전체크 및 고객생성 (OSST).
     * ASIS: saveDataSharingStep1
     * - OSST PC0 호출
     * - MSF_REQUEST_OSST UPDATE (prgrStatCd=PC0, osstOrdNo)
     * - 응답: { success, resNo, requestKey }
     */
    Map<String, Object> step1(SvcChgShareDataApplyReqDto req);

    /**
     * [Step2] PC2 폴링 + Y39 CI 조회.
     * ASIS: conPreCheck
     * - ST1 상태조회 (최대 2회, 5초 대기)
     * - PC2 완료 확인 후 Y39 CI 조회
     * - MSF_REQUEST_OSST UPDATE (prgrStatCd=PC2)
     * - 응답: { success, prgrStatCd, rsltCd, ipinCi }
     */
    Map<String, Object> step2(SvcChgShareDataApplyReqDto req);

    /**
     * [Step3] NU1 번호조회 + NU2 번호예약.
     * ASIS: saveDataSharingStep2
     * - NU1 희망번호 조회 (끝번호 → 중간번호 → Random 순서로 fallback)
     * - NU2 번호 예약
     * - MSF_REQUEST_OSST UPDATE (tlphNo, prgrStatCd=NU2)
     * - 응답: { success, tlphNo }
     */
    Map<String, Object> step3(SvcChgShareDataApplyReqDto req);

    /**
     * [Step4] OP0 개통 및 수납.
     * ASIS: saveDataSharingStep3
     * - OP0 개통 호출
     * - MSF_REQUEST UPDATE (openNo=tlphNo, proCd=완료)
     * - MSF_REQUEST_OSST UPDATE (prgrStatCd=OP0)
     * - 응답: { success }
     */
    Map<String, Object> step4(SvcChgShareDataApplyReqDto req);
}
