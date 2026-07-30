package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MnpOsstResponse {

    private String globalNo;
    private String osstResultCode;

    private String osstRsltCd; //M-Platform 결과코드
    private String osstRsltMsg; //M-Platform 결과메시지
}
