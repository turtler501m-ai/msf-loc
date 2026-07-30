package com.ktmmobile.msf.commons.logincore.support.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticationRecorder;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.application.service.NoopLoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

@Slf4j
@Configuration
public class LoginCoreConfig {

    /**
     * 기본 Noop LoginUserFinder Bean 생성
     *
     * @return Noop LoginUserFinder
     */
    @Bean
    @ConditionalOnMissingBean(LoginUserFinder.class)
    public LoginUserFinder<LoginAuthenticationCredential> noopLoginUserFinder() {
        log.warn("No LoginUserFinder bean found. Registering NoopLoginUserFinder. Login authentication will always fail until an application-specific LoginUserFinder is provided.");
        return new NoopLoginUserFinder();
    }

    /**
     * 기본 Noop LoginAuthenticationRecorder Bean 생성
     *
     * @return Noop LoginAuthenticationRecorder
     */
    @Bean
    @ConditionalOnMissingBean(LoginAuthenticationRecorder.class)
    public LoginAuthenticationRecorder<LoginAuthenticationCredential> noopLoginAuthenticationRecorder() {
        log.warn("No LoginAuthenticationRecorder bean found. Login success/failure record will be skipped until an application-specific LoginAuthenticationRecorder is provided.");
        return new LoginAuthenticationRecorder<>() {

            /**
             * 기본 Access Token 발급 성공 기록
             *
             * @param user 로그인 세션 사용자
             */
            @Override
            public void recordAccessTokenIssueSuccess(LoginSessionUser user) {
                //noop
            }

            /**
             * 기본 로그인 실패 기록
             *
             * @param user 로그인 사용자
             * @param credential 로그인 인증 정보
             * @param shouldLock 계정 잠금 여부
             */
            @Override
            public void recordLoginFailure(
                LoginUser user,
                LoginAuthenticationCredential credential,
                boolean shouldLock
            ) {
                //noop
            }
        };
    }
}
