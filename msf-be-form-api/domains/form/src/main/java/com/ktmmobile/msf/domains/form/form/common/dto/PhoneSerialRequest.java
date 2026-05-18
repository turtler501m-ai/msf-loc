package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 휴대폰 일련번호 유효성체크 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class PhoneSerialRequest {

    private String orgnId;
    private String prodId;
    private String prodSn;
    private String phoneModelId;
}
