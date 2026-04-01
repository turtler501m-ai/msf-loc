package com.ktmmobile.msf.formComm.service;

import com.ktmmobile.msf.formComm.dto.SmartFormReqDto;

import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 신청 서비스 인터페이스.
 * ASIS AppformController 데이터쉐어링 관련 메서드 이식.
 * 신청서 저장 + OSST 연동 (PC0/ST1/Y39/NU1/NU2/OP0).
 */
public interface SmartFormSvc {

    /**
     * 신청서 저장 + 전체 OSST 자동 개통 플로우.
     * ASIS AppformController.saveDataSharingSimple() 와 동일.
     * DB저장 → PC0 → ST1폴링(PC2) → Y39(CI) → NU1(번호조회) → NU2(번호예약) → OP0(개통).
     */
    Map<String, Object> saveDataSharingSimple(SmartFormReqDto req);

    /**
     * SCAN 서버 전송.
     * ASIS AppformController.sendScan() 와 동일.
     * requestKey 로 신청서 XML 생성 후 SCAN 서버 전송.
     */
    Map<String, Object> sendScan(SmartFormReqDto req);

    /**
     * 신청서 저장 (OSST 미포함).
     * ASIS AppformController.saveDataSharing() 와 동일.
     * DB저장 후 SMS/카카오 알림 발송.
     */
    Map<String, Object> saveDataSharing(SmartFormReqDto req);

    /**
     * [Step1] PC0 사전체크 및 고객생성 (OSST).
     * ASIS AppformController.saveDataSharingStep1() 와 동일.
     */
    Map<String, Object> saveDataSharingStep1(SmartFormReqDto req);

    /**
     * PC2 완료상태 폴링 (ST1) + Y39 CI 조회.
     * ASIS AppformController.conPreCheck() 와 동일.
     */
    Map<String, Object> conPreCheck(SmartFormReqDto req);

    /**
     * [Step2] NU1 번호조회 + NU2 번호예약.
     * ASIS AppformController.saveDataSharingStep2() 와 동일.
     */
    Map<String, Object> saveDataSharingStep2(SmartFormReqDto req);

    /**
     * [Step3] OP0 개통 및 수납.
     * ASIS AppformController.saveDataSharingStep3() 와 동일.
     */
    Map<String, Object> saveDataSharingStep3(SmartFormReqDto req);
}
