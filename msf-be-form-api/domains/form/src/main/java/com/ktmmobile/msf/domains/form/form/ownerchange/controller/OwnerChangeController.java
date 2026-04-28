package com.ktmmobile.msf.domains.form.form.ownerchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.service.OwnerChgRestSvc;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class OwnerChangeController {

    private final OwnerChgRestSvc ownerChgRestSvc;

    // 명의변경 가능 여부 유효성 체크
    @PostMapping("/owner-change/validate")
    public CommonResponse<OwnerChangeValidationResponse> ownerChangeValidation(@RequestBody OwnerChangeValidationRequest request) {
        return ResponseUtils.ok(ownerChgRestSvc.ownerChangeValidation(request));
    }
}
