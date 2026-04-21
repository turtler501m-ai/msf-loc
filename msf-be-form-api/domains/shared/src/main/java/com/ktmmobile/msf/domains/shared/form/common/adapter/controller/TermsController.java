package com.ktmmobile.msf.domains.shared.form.common.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.TermsGroupResponse;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.TermsReader;

@RestController
@RequestMapping("/api/shared/form/common/terms")
@RequiredArgsConstructor
public class TermsController {

    private final TermsReader termsAgreementReader;

    @PostMapping("/list")
    public CommonResponse<TermsGroupResponse> getListTerms(@RequestBody @Valid TermsCondition condition) {
        return ResponseUtils.ok(termsAgreementReader.getListTerms(condition));
    }
}
