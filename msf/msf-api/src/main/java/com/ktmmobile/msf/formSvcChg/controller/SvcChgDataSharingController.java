package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.DataSharingReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgDataSharingSvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 데이터쉐어링 Controller.
 * ASIS AppformController.saveDataSharingSimple / MyShareDataController 역할.
 */
@RestController
@RequestMapping("/api/v1/service-change/data-sharing")
public class SvcChgDataSharingController {

    private final SvcChgDataSharingSvc dataSharingSvc;

    public SvcChgDataSharingController(SvcChgDataSharingSvc dataSharingSvc) {
        this.dataSharingSvc = dataSharingSvc;
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
}
