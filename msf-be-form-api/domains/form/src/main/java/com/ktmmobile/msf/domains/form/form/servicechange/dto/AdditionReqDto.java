package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionReqDto {
    private String ncn; // 서비스 계약번호
    private String ctn; // 전화번호
    private String custId; // 고객번호
}
