package com.ktmmobile.msf.commons.logincore.support.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.application.service.NoopLoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

@Configuration
public class LoginCoreFallbackConfig {

    @Bean
    @ConditionalOnMissingBean(LoginUserFinder.class)
    public LoginUserFinder<LoginAuthenticationCredential> noopLoginUserFinder() {
        return new NoopLoginUserFinder();
    }
}
