package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AppRegistRequest {

    private String loginSessionId;

    @NotBlank(groups = {OnCreate.class, OnModify.class}, message = "uuid는 필수 입력 값입니다.")
    private String deviceUuid;

    private String madAdr;
    private String userId;
    private String apvSttusCd;
    private String osCd;
    private String version;
    private String appOsVer;
    private String appNm;
    private String autoLoginYn;
    private String bioLoginYn;
}
