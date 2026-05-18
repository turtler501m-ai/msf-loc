package com.ktmmobile.msf.domains.form.form.common.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * eSIM 유효성체크 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class EsimRequest {

    @NotBlank
    private String eid;
    @NotBlank
    private String imei1;
    @NotBlank
    private String imei2;

    private String phoneModelId;
    private int uploadPhoneSrlNo;
}
