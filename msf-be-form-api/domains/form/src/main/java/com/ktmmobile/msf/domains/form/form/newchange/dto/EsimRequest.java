package com.ktmmobile.msf.domains.form.form.newchange.dto;

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

    private String modelId; //K코드 (단말코드)

    private String phoneModelId; //삭제예정
    private int uploadPhoneSrlNo;

    private String agentCd; //선택한 대리점코드
}
