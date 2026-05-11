package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
public class ResumeRequest {

    @NotNull
    private String ncn; // 계약번호
    @NotNull
    private String ctn; // 회선번호
    @NotNull
    private String custId; // 고객ID
    private String pwdType; // 비밀번호 타입 (PP: 일시정지, CP: 개인정보 암호)
    @NotNull
    private String cpPwdInsert1; // 일시정지패스워드
}
