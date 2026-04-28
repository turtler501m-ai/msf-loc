package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AppRegistRequest {
    @NotBlank(groups = {OnCreate.class, OnModify.class}, message = "uuid는 필수 입력 값입니다.")
    private String uuid;
    private String madAdr;
    @NotBlank(groups = {OnCreate.class}, message = "userId는 필수 입력 값입니다.")
    private String userId;
    private String apvSttusCd;
    private String osCd;
    private String version;
    private String appOsVer;
    private String appNm;
    private String autoLoginYn;
    private String bioLoginYn;
}
