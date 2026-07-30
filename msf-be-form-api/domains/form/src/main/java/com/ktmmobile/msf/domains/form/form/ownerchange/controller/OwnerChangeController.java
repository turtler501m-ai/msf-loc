package com.ktmmobile.msf.domains.form.form.ownerchange.controller;

import java.io.IOException;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormDetailRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeInitFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeSaveResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.service.OwnerChgMcpSvc;
import com.ktmmobile.msf.domains.form.form.ownerchange.service.OwnerChgRestSvc;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class OwnerChangeController {

    private final OwnerChgRestSvc ownerChgRestSvc;
    private final OwnerChgMcpSvc ownerChgMcpSvc;

    // 명의변경 가능 여부 유효성 체크
    @PostMapping("/owner-change/validate")
    public CommonResponse<OwnerChangeValidationResponse> ownerChangeValidation(@RequestBody OwnerChangeValidationRequest request) throws IOException {
        return ResponseUtils.ok(ownerChgRestSvc.ownerChangeValidation(request));
    }

    // 명의변경 작성완료 데이터 저장 (사전체크 FMC0)
    @PostMapping("/owner-change/form/save")
    public CommonResponse<OwnerChangeSaveResponse> ownerChangeFormSave(@RequestBody @Valid MsfRequestNameChgVo request) throws IOException {
        return ResponseUtils.ok(ownerChgRestSvc.ownerChangeFormSave(request));
    }

    // 명의변경 신청서 데이터 조회
    @PostMapping("/owner-change/form/get")
    public CommonResponse<OwnerChangeInitFormInfoResponse> ownerChangeFormGet(@RequestBody @Valid OwnerChangeFormDetailRequest request) {
        return ResponseUtils.ok(ownerChgRestSvc.ownerChangeFormGet(request));
    }

    // 명의변경 신청서 데이터 조회
    @PostMapping("/owner-change/form/prmtAmt/get")
    public CommonResponse<Long> ownerChangeFormPrmtAmtGet(@RequestBody @Valid MspSaleSubsdMstRequest request) {
        return ResponseUtils.ok(ownerChgMcpSvc.ownerChangeFormPrmtAmtGet(request));
    }
}
