package com.ktmmobile.msf.domains.koiidcard.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdCardQrResponse;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationRequest;
import com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId.MobileIdVerificationSaveResponse;
import com.ktmmobile.msf.domains.koiidcard.application.port.in.MobileIdReader;

@RestController
@RequestMapping("/api/koi-idcard")
@RequiredArgsConstructor
public class MobileQrController {

    private final MobileIdReader mobileIdReader;

    @PostMapping("/qr/request")
    public CommonResponse<MobileIdCardQrResponse> mobileIdCardQr(@RequestBody MobileIdCardQrRequest request) {
        return ResponseUtils.ok(mobileIdReader.mobileIdCardQr(request));
    }

    /**
     * APPFORM -> FORMAPI
     * 신분증 검증 결과 반환
     */
    @PostMapping("/verification/result/get")
    public ResponseEntity<MobileIdVerificationSaveResponse> getVerificationResult(@RequestBody @Valid MobileIdVerificationRequest request) {
        return ResponseEntity.ok(mobileIdReader.getVerificationResult(request));
    }
}
