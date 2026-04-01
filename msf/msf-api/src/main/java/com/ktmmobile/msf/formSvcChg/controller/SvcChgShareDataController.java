package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgShareDataReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgShareDataSvc;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 데이터쉐어링 Controller.
 * ASIS MyShareDataController 역할 — X69/X70/X71 + 화면 데이터 조합.
 * - dataSharingStep2   : Step2 화면 데이터 (X71 목록 + 개통가능시간)
 * - isSimpleApplyObj   : 셀프개통 가능 시간 조회
 * - dorReqSharingView  : 개통요청 뷰 데이터 조회
 * - doinsertOpenRequestAjax : X69 사전체크 + X70 가입 처리
 */
@RestController
@RequestMapping("/api/v1/service-change/data-sharing")
public class SvcChgShareDataController {

    private final SvcChgShareDataSvc dataSharingSvc;

    public SvcChgShareDataController(SvcChgShareDataSvc dataSharingSvc) {
        this.dataSharingSvc = dataSharingSvc;
    }

    /**
     * Step2 화면 데이터 조합 (X71 결합목록 + 개통가능시간).
     * ASIS MyShareDataController.dataSharingStep2() 와 동일.
     * POST /api/v1/service-change/data-sharing/step2-info
     */
    @PostMapping("/step2-info")
    public Map<String, Object> dataSharingStep2(@RequestBody SvcChgShareDataReqDto req) {
        return dataSharingSvc.dataSharingStep2(req);
    }

    /**
     * 셀프개통 가능 시간 조회 (NAC: 08:00~21:50).
     * ASIS AppformSvcImpl.isSimpleApplyObj() 와 동일.
     * GET /api/v1/service-change/data-sharing/is-simple-apply-obj
     */
    @GetMapping("/is-simple-apply-obj")
    public Map<String, Object> isSimpleApplyObj() {
        return dataSharingSvc.isSimpleApplyObj();
    }

    /**
     * 개통요청 뷰 데이터 조회.
     * ASIS MyShareDataController.dorReqSharingView() 와 동일.
     * POST /api/v1/service-change/data-sharing/req-sharing-view
     */
    @PostMapping("/req-sharing-view")
    public Map<String, Object> dorReqSharingView(
            @RequestParam(value = "contractNum") String contractNum) {
        return dataSharingSvc.dorReqSharingView(contractNum);
    }

    /**
     * 데이터쉐어링 개통 신청 (X69 사전체크 + X70 가입).
     * ASIS MyShareDataController.doinsertOpenRequestAjax() 와 동일.
     * POST /api/v1/service-change/data-sharing/insert-open-request
     */
    @PostMapping("/insert-open-request")
    public Map<String, Object> doinsertOpenRequestAjax(@RequestBody SvcChgShareDataReqDto req) {
        return dataSharingSvc.doinsertOpenRequestAjax(req);
    }
}
