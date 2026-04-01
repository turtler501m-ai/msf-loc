package com.ktmmobile.msf.formComm.controller;

import com.ktmmobile.msf.formComm.dto.SmartFormReqDto;
import com.ktmmobile.msf.formComm.service.SmartFormSvc;
import com.ktmmobile.msf.formSvcChg.service.SvcChgShareDataSvc;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 데이터쉐어링 신규 개통 Controller (업무공통).
 * ASIS AppformController 데이터쉐어링 관련 메서드 역할.
 * - isSimpleApplyObj      : 셀프개통 가능 시간 조회
 * - saveDataSharingSimple : DB저장 + 전체 OSST 자동 개통 플로우
 * - sendScan              : SCAN 서버 전송
 * - saveDataSharing       : 신청서 저장 (OSST 미포함)
 * - saveDataSharingStep1  : PC0 사전체크
 * - conPreCheck           : PC2 폴링 + Y39 CI 조회
 * - saveDataSharingStep2  : NU1 번호조회 + NU2 번호예약
 * - saveDataSharingStep3  : OP0 개통
 */
@RestController
@RequestMapping("/api/v1/service-change/data-sharing/apply")
public class SmartFormController {

    private final SmartFormSvc      smartFormSvc;
    private final SvcChgShareDataSvc dataSharingSvc;

    public SmartFormController(SmartFormSvc smartFormSvc,
                               SvcChgShareDataSvc dataSharingSvc) {
        this.smartFormSvc   = smartFormSvc;
        this.dataSharingSvc = dataSharingSvc;
    }

    /**
     * 셀프개통 가능 시간 조회.
     * ASIS AppformSvcImpl.isSimpleApplyObj() 와 동일.
     * GET /api/v1/service-change/data-sharing/apply/is-simple-apply-obj
     */
    @GetMapping("/is-simple-apply-obj")
    public Map<String, Object> isSimpleApplyObj() {
        return dataSharingSvc.isSimpleApplyObj();
    }

    /**
     * DB저장 + 전체 OSST 자동 개통 플로우.
     * ASIS AppformController.saveDataSharingSimple() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/simple
     */
    @PostMapping("/simple")
    public Map<String, Object> saveDataSharingSimple(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.saveDataSharingSimple(req);
    }

    /**
     * SCAN 서버 전송.
     * ASIS AppformController.sendScan() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/send-scan
     */
    @PostMapping("/send-scan")
    public Map<String, Object> sendScan(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.sendScan(req);
    }

    /**
     * 신청서 저장 (OSST 미포함).
     * ASIS AppformController.saveDataSharing() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/save
     */
    @PostMapping("/save")
    public Map<String, Object> saveDataSharing(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.saveDataSharing(req);
    }

    /**
     * PC0 사전체크 및 고객생성.
     * ASIS AppformController.saveDataSharingStep1() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/step1
     */
    @PostMapping("/step1")
    public Map<String, Object> saveDataSharingStep1(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.saveDataSharingStep1(req);
    }

    /**
     * PC2 완료 폴링 (ST1) + Y39 CI 조회.
     * ASIS AppformController.conPreCheck() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/con-pre-check
     */
    @PostMapping("/con-pre-check")
    public Map<String, Object> conPreCheck(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.conPreCheck(req);
    }

    /**
     * NU1 번호조회 + NU2 번호예약.
     * ASIS AppformController.saveDataSharingStep2() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/step2
     */
    @PostMapping("/step2")
    public Map<String, Object> saveDataSharingStep2(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.saveDataSharingStep2(req);
    }

    /**
     * OP0 개통 및 수납.
     * ASIS AppformController.saveDataSharingStep3() 와 동일.
     * POST /api/v1/service-change/data-sharing/apply/step3
     */
    @PostMapping("/step3")
    public Map<String, Object> saveDataSharingStep3(@RequestBody SmartFormReqDto req) {
        return smartFormSvc.saveDataSharingStep3(req);
    }
}
