package com.ktmmobile.msf.domains.cache.worknotice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AccessAllowedIp {
    private String acesAlwdIp;
    private String nm;
    private String mobileNo;
    private String useYn;
}
