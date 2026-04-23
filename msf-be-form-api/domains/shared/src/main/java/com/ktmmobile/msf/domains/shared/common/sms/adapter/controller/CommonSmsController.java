package com.ktmmobile.msf.domains.shared.common.sms.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;

@RestController
@RequestMapping("/api/shared/common/sms")
@RequiredArgsConstructor
public class CommonSmsController {

    private final CommonSmsWriter commonSmsWriter;

    @PostMapping("/send")
    public CommonResponse<Boolean> sendSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendSms(request));
    }

    @PostMapping("/otp/send")
    public CommonResponse<String> sendOtpSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendOtpSms(request));
    }

    @PostMapping("/otp/verify")
    public CommonResponse<Boolean> verifyOtpSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.verifyOtpSms(request));
    }
}
