package com.ktmmobile.msf.domains.form.login.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String userPw;    // 일반 로그인용
    @NotBlank(groups={OnSelect.class}, message = "단말기 고유 ID는 필수 입력 값입니다.")
    private String uuid;    // 단말 인증용
    @NotBlank(message = "인증방식은 필수 입력 값입니다.")
    private String authType;    // "PASSWORD", "BIOPASS" 등
    private String userSttusCd;
    private Integer failCnt;
}
