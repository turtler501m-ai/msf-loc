package com.ktmmobile.msf.domains.form.form.newchange.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MSimpleOsstXmlFs0VO;
import com.ktmmobile.msf.domains.form.form.common.service.AuthInfoService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.KnoteScanInfoResponse;

/**
 * Knote 신분증 인증 정보
 */
@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class KnoteScanInfoController {

    private final AuthInfoService authInfoService; //인증

    /**
     * KNOTE 신분증 목록조회 >> 서식지 목록조회 - FS0
     */
    @PostMapping("/knote/scaninfo/list")
    //public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList(@RequestBody @Valid KnoteScanInfoRequest request) {
    public CommonResponse<FormResponse<MSimpleOsstXmlFs0VO>> getIdList(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.getIdList(request));
    }

    /**
     * KNOTE 신분증 상태조회 >> 서식지 상태조회 - FS1
     */
    @PostMapping("/knote/scaninfo/check")
    public CommonResponse<FormResponse<KnoteScanInfoResponse>> checkIdStatus(@RequestBody @Valid KnoteScanInfoRequest request) {
        return ResponseUtils.ok(authInfoService.checkIdStatus(request));
    }
}
