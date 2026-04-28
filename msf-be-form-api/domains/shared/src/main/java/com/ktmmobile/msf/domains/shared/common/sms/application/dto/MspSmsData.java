package com.ktmmobile.msf.domains.shared.common.sms.application.dto;

import lombok.Builder;

import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;

@Builder
public record MspSmsData(
    Long msgId,
    Integer msgType,
    String subject,
    String scheduleTime,
    String submitTime,
    String message,
    String callbackNum,
    String rcptData,
    String kAdflag,
    String reserved01,
    String reserved02,
    String reserved03,
    Boolean develop
) {
    public MspSmsData {
        if (develop == null) {
            develop = !EnvironmentUtils.isProduction();
        }
    }
}
