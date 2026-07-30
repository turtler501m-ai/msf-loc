package com.ktmmobile.msf.domains.form.form.servicechange.controller;


import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfInsuranceSvcServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MsfInsuranceController {

    private final MsfInsuranceSvcServiceImpl msfInsuranceSvcService;

    /**
     * 단말 보험 가입 가능 여부 체크
     *
     * 계약번호로 보험 가입 정보 조회 후 가입 가능 여부 체크
     *
     * ASIS: /mypage/reqInsr.do
     * TOBE: POST /api/form/servicechange/insur/available
     *
     * @param req ncn(신청자 계약번호)/ctn
     * @return InsuranceProcessResponse
     */
    @PostMapping("/api/form/servicechange/insur/available")
    public CommonResponse<FormResponse<InsuranceProcessResponse>> insurAvailable(@RequestBody @Valid InsuranceProcessRequest req) {
        return ResponseUtils.ok(msfInsuranceSvcService.insurAvailable(req));
    }

    /**
     * 단말 보험 가입 처리
     *
     *
     * ASIS: /mypage/custRequestAjax.do
     * TOBE: POST /api/form/servicechange/insur/process
     *
     * @param req ncn(신청자 계약번호)/ctn
     * @return InsuranceProcessResponse
     */
    @PostMapping("/api/form/servicechange/insur/process")
    public CommonResponse<FormResponse<InsuranceProcessResponse>> insurProcess(@RequestBody @Valid InsuranceProcessRequest req) {
        return ResponseUtils.ok(msfInsuranceSvcService.insurProcess(req));
    }

}
