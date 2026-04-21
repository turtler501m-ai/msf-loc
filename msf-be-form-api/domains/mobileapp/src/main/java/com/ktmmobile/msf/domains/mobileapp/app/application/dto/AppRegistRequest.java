package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.auditing.data.vo.Auditing;

@Data
@NoArgsConstructor
public class AppRegistRequest {
    @NotBlank(message = "uuid는 필수 입력 값입니다.")
    private String uuid;
    private String madAdr;
    // @NotBlank(message = "userId는 필수 입력 값입니다.")
    private String userId;
    private String osCd;
    private String version;
    private String appOsVer;
    private String appNm;
    private String autoLoginYn;
    private String bioLoginYn;

    private Auditing auditing;
}
