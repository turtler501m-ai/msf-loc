package com.ktmmobile.msf.domains.shared.common.sms.support.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "common-sms")
public record CommonSmsProperties(
    Error error,
    Otp otp
) {

    public CommonSmsProperties {
        error = error == null ? new Error(false) : error;
        otp = otp == null ? new Otp(false) : otp;
    }

    public record Error(boolean includeDetail) {
    }

    public record Otp(boolean exposeAuthNumber) {
    }
}
