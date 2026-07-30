package com.ktmmobile.msf.domains.shared.form.common.complete.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormCondition;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.dto.CompletedFormResponse;
import com.ktmmobile.msf.domains.shared.form.common.complete.application.port.in.FormCommonCompleteReader;

@RestController
@RequestMapping({"/api/form/common/complete"})
@RequiredArgsConstructor
public class FormCommonCompleteController {

    private final FormCommonCompleteReader formCommonCompleteReader;

    @PostMapping("/form")
    public CommonResponse<CompletedFormResponse> noticeList(@RequestBody @Valid CompletedFormCondition condition) {
        return ResponseUtils.ok(formCommonCompleteReader.getCompletedFormResponse(condition));
    }
}
