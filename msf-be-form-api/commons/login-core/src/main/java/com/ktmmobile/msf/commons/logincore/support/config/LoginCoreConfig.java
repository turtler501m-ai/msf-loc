package com.ktmmobile.msf.commons.logincore.support.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.application.service.NoopLoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

@Slf4j
@Configuration
public class LoginCoreConfig {

    @Bean
    @ConditionalOnMissingBean(LoginUserFinder.class)
    public LoginUserFinder<LoginAuthenticationCredential> noopLoginUserFinder() {
        log.warn("No LoginUserFinder bean found. Registering NoopLoginUserFinder. Login authentication will always fail until an application-specific LoginUserFinder is provided.");
        return new NoopLoginUserFinder();
    }
}
