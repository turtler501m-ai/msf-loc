package com.ktmmobile.msf.domains.login.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginRefreshTokenCookieManager;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/auth"})
public class LoginLogoutController {

    private final LoginSessionFlowProcessor loginSessionFlowProcessor;
    private final LoginRefreshTokenCookieManager refreshTokenCookieManager;

    /**
     * 현재 인증 사용자 로그아웃 처리
     *
     * @return Refresh Token 쿠키 삭제 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout() {
        MsfUser user = AuthenticationUtils.getUser();
        loginSessionFlowProcessor.logout(user.getUserType(), user.getUserId());
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookieManager.deleteRefreshTokenCookie().toString())
            .body(ResponseUtils.ok());
    }
}
