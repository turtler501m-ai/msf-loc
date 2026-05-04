package com.ktmmobile.msf.domains.shared.common.sms.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SmsSendedOtpData {

    private String phone;
    private String type;
    private String path;
    private String name;
    private String token;
    private String value;
    private Long key;
}
