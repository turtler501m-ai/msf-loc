package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrdtCardAuthRequest {
    //신용카드 인증조회 Request DTO
    private String crdtCardNo; //카드번호
    private String crdtCardTermYear; //카드유효기간 년
    private String crdtCardTermMonth; //카드유효기간 월
    private String crdtCardTermDay; //카드유효기간 (년+월)
    private String custNm; //카드소유자명
    private String brthDate; //카드소유주 생년월일
    //private String custBirthDate; //카드소유자 생년월일
    private String ncType; //
}
