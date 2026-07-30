package com.ktmmobile.msf.domains.shared.common.sms.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsResponse;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.in.CommonSmsWriter;

@RestController
@RequestMapping({"/api/shared/common", "/api/n", "/api/external-service"})
@RequiredArgsConstructor
public class CommonSmsController {

    private final CommonSmsWriter commonSmsWriter;

    @PostMapping("/sms/send")
    public CommonResponse<Boolean> sendSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendSms(request));
    }

    @PostMapping("/{serviceId}/sms/send")
    public CommonResponse<Boolean> sendSms(@PathVariable("serviceId") String serviceId, @RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendSms(request));
    }

    @PostMapping("/kakao/send")
    public CommonResponse<Boolean> sendKakao(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendKakao(request));
    }

    @PostMapping("/{serviceId}/kakao/send")
    public CommonResponse<Boolean> sendKakao(@PathVariable("serviceId") String serviceId, @RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendKakao(request));
    }

    @PostMapping("/auth/sms/otp/send")
    public CommonResponse<CommonSmsResponse> sendOtpSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.sendOtpSms(request));
    }

    @PostMapping("/auth/sms/otp/verify")
    public CommonResponse<Boolean> verifyOtpSms(@RequestBody @Valid CommonSmsRequest request) {
        return ResponseUtils.ok(commonSmsWriter.verifyOtpSms(request));
    }
}
