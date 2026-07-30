package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvgUnpauseService;

@Slf4j
@RestController
public class MsfSvcUnpauseController {

    @Autowired
    private MsfSvgUnpauseService unpauseService;

    /**
     * 분실복구/일시정지해제신청 가능 여부 확인
     * 일시정지해제가능여부조회(X28)
     * @param req
     * @return
     */
    @PostMapping("/api/msf/formServiceChange/unpause/check")
    public CommonResponse<FormResponse<UnpauseResponse>> unpauseCheck(@RequestBody UnpauseRequest req) {
        return ResponseUtils.ok(unpauseService.unpauseCheck(req));
    }

    /**
     * [TEST] 분실복구/일시정지신청 비밀번호 셋팅
     * 일시정지(X29)
     * @param req
     * @return
     */
    @PostMapping("/api/msf/formServiceChange/test/pause/process")
    public CommonResponse<FormResponse<UnpauseResponse>> testPauseProcess(@RequestBody UnpauseRequest req) {
        return ResponseUtils.ok(unpauseService.testPauseProcess(req));
    }

}
