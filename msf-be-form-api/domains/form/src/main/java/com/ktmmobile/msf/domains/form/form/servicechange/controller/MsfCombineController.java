package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import java.io.IOException;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfCombineSvcServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MsfCombineController {

    private final MsfCombineSvcServiceImpl msfCombineSvcService;


    /**
     * 아무나SOLO 결합 가능 체크
     *
     *
     * ASIS: /content/checkCombineSelfAjax.do
     * TOBE: POST /api/form/servicechange/combine-self/check
     *
     * @param req ncn(신청자 계약번호)/ctn/custId
     * @return CombineSelfResponse
     */
    @PostMapping("/api/form/servicechange/combine-self/check")
    public CommonResponse<FormResponse<CombineSelfResponse>> combineSelfCheck(@RequestBody @Valid CombineSelfRequest request) throws IOException {
        return ResponseUtils.ok(msfCombineSvcService.combineSelfCheck(request));
    }

    /**
     * 아무나SOLO 결합 처리
     *
     *
     * ASIS: /content/regCombineSelfAjax.do
     * TOBE: POST /api/form/servicechange/combine-self/process
     *
     * @param req ncn(신청자 계약번호)/ctn/custId
     * @return InsuranceProcessResponse
     */
    @PostMapping("/api/form/servicechange/combine-self/process")
    public CommonResponse<FormResponse<CombineSelfResponse>> combineSelfProcess(@RequestBody @Valid CombineSelfRequest request) throws IOException {
        return ResponseUtils.ok(msfCombineSvcService.combineSelfProcess(request));
    }

}
