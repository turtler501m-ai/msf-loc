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

import com.ktmmobile.msf.commons.logincore.application.dto.LoginSessionIssueRequest;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginAuthenticationFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginRefreshTokenCookieManager;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResultResponse;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.login.application.dto.LoginAuthRequest;
import com.ktmmobile.msf.domains.login.application.dto.LoginCredential;
import com.ktmmobile.msf.domains.login.application.dto.LoginPasswordChangeRequest;
import com.ktmmobile.msf.domains.login.application.dto.LoginUserInfoResponse;
import com.ktmmobile.msf.domains.login.application.port.in.LoginPasswordChanger;
import com.ktmmobile.msf.domains.login.application.port.in.LoginUserInfoReader;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/auth"})
public class LoginAuthController {

    private final LoginAuthenticationFlowProcessor<LoginCredential> loginAuthenticationFlowProcessor;
    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final LoginRefreshTokenCookieManager refreshTokenCookieService;
    private final LoginUserInfoReader loginUserInfoReader;
    private final LoginPasswordChanger loginPasswordChanger;

    @PostMapping("/login")
    public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> loginWithIdPw(@RequestBody @Valid LoginAuthRequest request) {
        LoginResult result = loginAuthenticationFlowProcessor.loginWithIdPw(request.toCredential());
        return resultResponse(result);
    }


    @PostMapping("/login/issue")
    public ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> issue(@RequestBody @Valid LoginSessionIssueRequest request) {
        LoginTokenPair tokenPair = loginSessionFlowProcessor.issue(request.loginSessionId());
        return tokenResponse(tokenPair);
    }

    @PostMapping("/login/session/get")
    public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> getSessionProgress(@RequestBody @Valid LoginSessionIssueRequest request) {
        LoginResult result = loginSessionFlowProcessor.getSessionProgress(request.loginSessionId());
        return resultResponse(result);
    }

    // @PostMapping("/password/modify")
    // public CommonResponse<LoginResultResponse<LoginUserInfoResponse>> modifyPassword(@RequestBody @Valid LoginPasswordChangeRequest request) {
    //     LoginResult result = loginPasswordChanger.changePassword(request);
    //     if (result == null) {
    //         return ResponseUtils.ok((LoginResultResponse<LoginUserInfoResponse>) null);
    //     }
    //     return resultResponse(result);
    // }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> refresh(HttpServletRequest request) {
        LoginTokenPair tokenPair = loginSessionFlowProcessor.refresh(refreshTokenCookieService.getRefreshToken(request.getCookies()));
        return tokenResponse(tokenPair);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpServletRequest request) {
        String refreshToken = refreshTokenCookieService.findRefreshToken(request.getCookies());
        if (refreshToken != null) {
            loginSessionFlowProcessor.logout(refreshToken);
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieService.deleteRefreshTokenCookie().toString())
            .body(ResponseUtils.ok());
    }

    @PostMapping({"/user/get"})
    public CommonResponse<LoginUserInfoResponse> getUserInfo() {
        return ResponseUtils.ok(loginUserInfoReader.getUserInfo(AuthenticationUtils.getUser()));
    }

    private CommonResponse<LoginResultResponse<LoginUserInfoResponse>> resultResponse(LoginResult result) {
        return ResponseUtils.ok(LoginResultResponse.from(result, LoginUserInfoResponse::from));
    }

    private ResponseEntity<CommonResponse<LoginResultResponse<LoginUserInfoResponse>>> tokenResponse(LoginTokenPair tokenPair) {
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieService.createRefreshTokenCookie(tokenPair).toString())
            .body(ResponseUtils.ok(LoginResultResponse.from(tokenPair, LoginUserInfoResponse::from)));
    }
}
