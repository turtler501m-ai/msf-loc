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
import com.ktmmobile.msf.domains.form.form.newchange.dto.MnpOsstRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.MnpOsstResponse;
import com.ktmmobile.msf.domains.form.form.newchange.service.NumberPortableService;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class NumberPortableController {

    private final NumberPortableService numberPortableService; //번호이동 사전동의 요청/결과조회/납부주장


    /**
     * 번호이동 사전동의 (NP1)
     */
    //@RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @PostMapping(value = "/portnumber/precheck/request")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpPreCheck(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpPreCheck(request));
    }

    /**
     * 번호이동 사전동의 결과조회 (NP3)
     */
    //@RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @PostMapping(value = "/portnumber/precheck/result")
    public CommonResponse<FormResponse<MnpOsstResponse>> requestNpAgree(@RequestBody @Valid MnpOsstRequest request) {
        return ResponseUtils.ok(numberPortableService.requestNpAgree(request));
    }
}
