package com.ktmmobile.msf.domains.mobileapp.app.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.logincore.application.dto.BiometricChallengeRequest;
import com.ktmmobile.msf.commons.logincore.application.dto.BiometricVerifyRequest;
import com.ktmmobile.msf.commons.logincore.application.port.in.BiometricChallengeManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.BiometricChallengeResponse;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.login.application.port.in.BiometricCredentialValidator;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.service.AppService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/biometric")
public class LoginUserBiometricController {

    private final BiometricChallengeManager biometricChallengeManager;
    private final BiometricCredentialValidator biometricCredentialValidator;
    private final AppService appService;

    /**
     * 인증 사용자 생체인증 공용 랜덤 키 발급
     *
     * @param request 생체인증 challenge 요청
     * @return 랜덤 키 응답
     */
    @PostMapping("/challenge")
    public CommonResponse<BiometricChallengeResponse> issueBiometricChallenge(
        @RequestBody @Valid BiometricChallengeRequest request
    ) {
        String userId = AuthenticationUtils.getUser().getUserId();
        biometricCredentialValidator.validateDevice(userId, request.deviceUuid());
        LoginBiometricChallenge challenge = biometricChallengeManager.createChallenge(userId, request.deviceUuid());
        return ResponseUtils.ok(BiometricChallengeResponse.from(challenge));
    }

    /**
     * 생체인증 해제를 위한 인증 사용자 생체인증 랜덤 키 검증
     *
     * @param request 생체인증 검증 요청
     * @return 검증 결과
     */
    @PostMapping("/disable/verify")
    public CommonResponse<Integer> verifyBiometricDisable(
        @RequestBody @Valid BiometricVerifyRequest request
    ) {
        String userId = AuthenticationUtils.getUser().getUserId();
        biometricCredentialValidator.validate(userId, request.deviceUuid(), request.bioKey());
        biometricChallengeManager.verifyEncryptedChallenge(
            userId,
            request.deviceUuid(),
            request.bioKey(),
            request.encryptedNonce()
        );

        AppRegistRequest appRegistRequest = new AppRegistRequest();
        appRegistRequest.setDeviceUuid(request.deviceUuid());
        appRegistRequest.setUserId(userId);
        appRegistRequest.setOsCd(request.osCd());
        appRegistRequest.setBioLoginYn(request.bioLoginYn());
        appRegistRequest.setBioLoginToken(request.bioLoginToken());
        Integer result = appService.modifyBioSetting(appRegistRequest);

        return ResponseUtils.ok(result);
    }
}
