package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AppInitRequest {
    @NotBlank(groups = {OnSelect.class, OnDelete.class},message = "uuid는 필수 입력 값입니다.")
    private String uuid;
}
