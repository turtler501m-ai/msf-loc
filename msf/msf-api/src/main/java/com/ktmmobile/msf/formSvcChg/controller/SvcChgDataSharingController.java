package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.DataSharingApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.DataSharingReqDto;
import com.ktmmobile.msf.formSvcChg.service.DataSharingApplySvc;
import com.ktmmobile.msf.formSvcChg.service.SvcChgDataSharingSvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 데이터쉐어링 Controller.
 * ASIS AppformController.saveDataSharingSimple / MyShareDataController 역할.
 * - A. 기존 회선 쉐어링: list/check/join/cancel (X69/X70/X71)
 * - B. 신규 개통: apply/step1/step2/step3/step4 (PC0/ST1/NU1/NU2/OP0)
 */
@RestController
@RequestMapping("/api/v1/service-change/data-sharing")
public class SvcChgDataSharingController {

    private final SvcChgDataSharingSvc dataSharingSvc;
    private final DataSharingApplySvc  applyDataSharingSvc;

    public SvcChgDataSharingController(SvcChgDataSharingSvc dataSharingSvc,
                                       DataSharingApplySvc applyDataSharingSvc) {
        this.dataSharingSvc      = dataSharingSvc;
        this.applyDataSharingSvc = applyDataSharingSvc;
    }

    /**
     * X71 데이터쉐어링 결합 중인 대상 조회.
     * POST /api/v1/service-change/data-sharing/list
     */
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody DataSharingReqDto req) {
        return dataSharingSvc.list(req);
    }

    /**
     * X69 데이터쉐어링 사전체크 (가입 가능 여부).
     * POST /api/v1/service-change/data-sharing/check
     */
    @PostMapping("/check")
    public Map<String, Object> check(@RequestBody DataSharingReqDto req) {
        return dataSharingSvc.check(req);
    }

    /**
     * X70 데이터쉐어링 가입 (opmdWorkDivCd=A).
     * POST /api/v1/service-change/data-sharing/join
     */
    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody DataSharingReqDto req) {
        return dataSharingSvc.join(req);
    }

    /**
     * X70 데이터쉐어링 해지 (opmdWorkDivCd=C).
     * POST /api/v1/service-change/data-sharing/cancel
     */
    @PostMapping("/cancel")
    public Map<String, Object> cancel(@RequestBody DataSharingReqDto req) {
        return dataSharingSvc.cancel(req);
    }

    // ─── B. 신규 개통 단계별 API ──────────────────────────────────────────

    /**
     * [Step0] 신청서 저장.
     * ASIS: saveDataSharingSimpleAjax / saveDataSharingAjax → fnSetDataOfdataSharing → saveAppform
     * POST /api/v1/service-change/data-sharing/apply
     */
    @PostMapping("/apply")
    public Map<String, Object> apply(@RequestBody DataSharingApplyReqDto req) {
        return applyDataSharingSvc.apply(req);
    }

    /**
     * [Step1] PC0 사전체크 및 고객생성 (OSST).
     * ASIS: saveDataSharingStep1Ajax
     * POST /api/v1/service-change/data-sharing/apply/step1
     */
    @PostMapping("/apply/step1")
    public Map<String, Object> step1(@RequestBody DataSharingApplyReqDto req) {
        return applyDataSharingSvc.step1(req);
    }

    /**
     * [Step2] PC2 폴링 + Y39 CI 조회.
     * ASIS: conPreCheckAjax (ST1 폴링 + Y39)
     * POST /api/v1/service-change/data-sharing/apply/step2
     */
    @PostMapping("/apply/step2")
    public Map<String, Object> step2(@RequestBody DataSharingApplyReqDto req) {
        return applyDataSharingSvc.step2(req);
    }

    /**
     * [Step3] NU1 번호조회 + NU2 번호예약.
     * ASIS: saveDataSharingStep2Ajax
     * POST /api/v1/service-change/data-sharing/apply/step3
     */
    @PostMapping("/apply/step3")
    public Map<String, Object> step3(@RequestBody DataSharingApplyReqDto req) {
        return applyDataSharingSvc.step3(req);
    }

    /**
     * [Step4] OP0 개통 및 수납.
     * ASIS: saveDataSharingStep3Ajax
     * POST /api/v1/service-change/data-sharing/apply/step4
     */
    @PostMapping("/apply/step4")
    public Map<String, Object> step4(@RequestBody DataSharingApplyReqDto req) {
        return applyDataSharingSvc.step4(req);
    }
}
