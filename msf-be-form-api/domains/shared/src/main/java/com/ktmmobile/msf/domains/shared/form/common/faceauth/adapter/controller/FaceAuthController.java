package com.ktmmobile.msf.domains.shared.form.common.faceauth.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthIgnoreResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthQrResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSendRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSmsResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthUrlResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.in.FaceAuthWriter;

@RestController
@RequestMapping("/api/shared/form/common/faceauth")
@RequiredArgsConstructor
public class FaceAuthController {

    private final FaceAuthWriter faceAuthWriter;

    @PostMapping("/confirm")
    public CommonResponse<FaceAuthConfirmResponse> faceAuthConfirm(@RequestBody @Valid FaceAuthConfirmRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthConfirm(request));
    }

    @PostMapping("/url")
    public CommonResponse<FaceAuthUrlResponse> faceAuthUrl(@RequestBody @Valid FaceAuthSendRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthUrl(request));
    }

    @PostMapping("/sms")
    public CommonResponse<FaceAuthSmsResponse> faceAuthSms(@RequestBody @Valid FaceAuthSendRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthSms(request));
    }

    @PostMapping("/qr")
    public CommonResponse<FaceAuthQrResponse> faceAuthQr(@RequestBody @Valid FaceAuthSendRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthQr(request));
    }

    @PostMapping("/ignore")
    public CommonResponse<FaceAuthIgnoreResponse> faceAuthIgnore(@RequestBody @Valid FaceAuthSendRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthIgnore(request));
    }

    @PostMapping("/result/prev")
    public CommonResponse<FaceAuthResultResponse> faceAuthResultPrev(@RequestBody @Valid FaceAuthResultRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthResultPrev(request));
    }

    @PostMapping("/result")
    public CommonResponse<FaceAuthResultResponse> faceAuthResult(@RequestBody @Valid FaceAuthResultRequest request) {
        return ResponseUtils.ok(faceAuthWriter.requestFaceAuthResult(request));
    }
}
