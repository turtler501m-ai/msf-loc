package com.ktmmobile.msf.domains.form.login.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PassChangeRequest {
    @NotBlank(groups = {OnModify.class}, message = "비밀번호는 필수 입력 값입니다.")
    public String newPassword;
    public String userId;
}
