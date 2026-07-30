package com.ktmmobile.msf.domains.login.adapter.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.logincore.application.dto.BiometricChallengeRequest;
import com.ktmmobile.msf.commons.logincore.application.dto.BiometricVerifyRequest;
import com.ktmmobile.msf.commons.logincore.application.dto.LoginSessionIssueRequest;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginAuthenticationFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginBiometricChallengeManager;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginRefreshTokenCookieManager;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.code.LoginAuthType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.domain.dto.BiometricChallengeResponse;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResultResponse;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.login.application.dto.LoginAuthRequest;
import com.ktmmobile.msf.domains.login.application.dto.LoginCredential;
import com.ktmmobile.msf.domains.login.application.dto.LoginUserInfoResponse;
import com.ktmmobile.msf.domains.login.application.port.in.BiometricCredentialValidator;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/n/auth"})
public class LoginAuthController {

    private final LoginAuthenticationFlowProcessor<LoginCredential> loginAuthenticationFlowProcessor;
    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final LoginBiometricChallengeManager loginBiometricChallengeManager;
    private final BiometricCredentialValidator biometricCredentialValidator;
    private final LoginRefreshTokenCookieManager refreshTokenCookieService;

    /**
     * ID/PW 로그인 요청 처리
     *
     * @param request 로그인 요청
     * @return 로그인 진행 결과 응답
     */
    @PostMapping("/login")
    public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> loginWithIdPw(@RequestBody @Valid LoginAuthRequest request) {
        LoginResult result = loginAuthenticationFlowProcessor.loginWithIdPw(request.toCredential(RequestUtils.getClientIp()));
        return resultResponse(result);
    }


    /**
     * 로그인 세션 기반 토큰 발급
     *
     * @param request 로그인 세션 요청
     * @return 토큰 발급 결과 응답
     */
    @PostMapping("/login/issue")
    public ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> issue(@RequestBody @Valid LoginSessionIssueRequest request) {
        LoginTokenPair tokenPair = loginSessionFlowProcessor.issue(request.loginSessionId());
        return tokenResponse(tokenPair);
    }

    /**
     * 로그인 세션 진행 상태 조회
     *
     * @param request 로그인 세션 요청
     * @return 로그인 진행 결과 응답
     */
    @PostMapping("/login/session/get")
    public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> getSessionProgress(@RequestBody @Valid LoginSessionIssueRequest request) {
        LoginResult result = loginSessionFlowProcessor.getSessionProgress(request.loginSessionId());
        return resultResponse(result);
    }

    /**
     * 생체인증 랜덤 키 발급
     *
     * @param request 생체인증 challenge 요청
     * @return 랜덤 키 응답
     */
    @PostMapping("/biometric/challenge")
    public CommonResponse<BiometricChallengeResponse> issueBiometricChallenge(
        @RequestBody @Valid BiometricChallengeRequest request
    ) {
        biometricCredentialValidator.validateDevice(request.deviceUuid());
        LoginBiometricChallenge challenge = loginBiometricChallengeManager.createChallenge(request.deviceUuid());
        return ResponseUtils.ok(BiometricChallengeResponse.from(challenge));
    }

    /**
     * 생체인증 랜덤 키 검증 후 로그인 흐름 시작
     *
     * @param request 생체인증 검증 요청
     * @return 로그인 진행 결과
     */
    @PostMapping("/biometric/verify")
    public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> verifyBiometricChallenge(
        @RequestBody @Valid BiometricVerifyRequest request
    ) {
        String userId = biometricCredentialValidator.validate(request.deviceUuid(), request.bioKey());
        loginBiometricChallengeManager.verifyEncryptedChallenge(request.deviceUuid(), request.bioKey(), request.encryptedNonce());
        LoginResult result = loginAuthenticationFlowProcessor.loginWithBiometric(new LoginCredential(
            userId,
            null,
            request.deviceUuid(),
            LoginAuthType.BIOPASS,
            RequestUtils.getClientIp()
        ));
        return resultResponse(result);
    }

    /**
     * Refresh Token 기반 토큰 재발급
     *
     * @param request HTTP 요청
     * @return 토큰 재발급 결과 응답
     */
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> refresh(HttpServletRequest request) {
        LoginTokenPair tokenPair = loginSessionFlowProcessor.refresh(refreshTokenCookieService.getRefreshToken(request.getCookies()));
        return tokenResponse(tokenPair);
    }

    /**
     * 로그인 결과 공통 응답 변환
     *
     * @param result 로그인 결과
     * @return 공통 응답
     */
    private CommonResponse<LoginResultResponse<LoginUserInfoResponse>> resultResponse(LoginResult result) {
        return ResponseUtils.ok(LoginResultResponse.from(result, LoginUserInfoResponse::from));
    }

    /**
     * 토큰 결과 응답 변환
     *
     * @param tokenPair 토큰 쌍
     * @return Refresh Token 쿠키 포함 응답
     */
    private ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> tokenResponse(LoginTokenPair tokenPair) {
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieService.createRefreshTokenCookie(tokenPair).toString())
            .body(ResponseUtils.ok(LoginResultResponse.from(tokenPair, LoginUserInfoResponse::from)));
    }
}
