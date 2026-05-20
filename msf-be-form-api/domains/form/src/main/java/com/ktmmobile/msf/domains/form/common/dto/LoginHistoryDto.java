package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginHistoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;  // 아이디
    private String intype;  // 인입경로 P:PC, M:Mobile, A:Android, I:IOS
    private String name;    // 이름
    private String phone;   // 전화번호
    private String regdate; // 생성일

}
