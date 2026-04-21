package com.ktmmobile.msf.domains.shared.form.common.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.AuthSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.port.in.CommonSmsWriter;

@RestController
@RequestMapping("/api/shared/common/sms")
@RequiredArgsConstructor
public class CommonSmsController {

    private final CommonSmsWriter commonSmsWriter;

    @PostMapping("/auth/send")
    public CommonResponse<Boolean> sendSms(@RequestBody @Valid AuthSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendAuthSms(request));
    }

    @PostMapping("/auth/verify")
    public CommonResponse<Boolean> verifySms(@RequestBody @Valid AuthSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.verifyAuthSms(request));
    }

    @PostMapping("/form/send")
    public CommonResponse<Boolean> sendSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendFormSms(request));
    }

    @PostMapping("/form/verify")
    public CommonResponse<Boolean> verifySms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.verifyFormSms(request));
    }
}
