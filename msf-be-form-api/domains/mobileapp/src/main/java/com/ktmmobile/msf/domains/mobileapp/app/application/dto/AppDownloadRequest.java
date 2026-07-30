package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AppDownloadRequest {

    @NotBlank(groups = {OnSelect.class}, message = "사용자 아이디를 입력하세요.")
    private String userId;
}
