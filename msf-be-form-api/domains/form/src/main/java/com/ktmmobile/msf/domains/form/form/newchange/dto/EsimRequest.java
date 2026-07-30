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
    private String model; //대표단말기 ID (K코드)
    private String reqModelNm; //단말모델명
    private String reqPhoneSn; //휴대폰일련번호

    private int uploadPhoneSrlNo; //eSIM 정보 저장 후 일련번호

    //private String phoneModelId; //삭제예정
    private String agentCd; //선택한 대리점코드
}
