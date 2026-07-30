package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;

/**
 * 안심보험 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class InsrProdRequest {

    private IntmInsrRelDTO intmInsrRelDTO; // JSON의 키값과 변수명이 같아야 함

    private String prodCtgId;
    private String reqBuyTypeCd;
    private String rprsPrdtId;
    private String serviceChangeYn; // Y: 서비스변경은 ASIS 후보 산출 조건으로 보험 목록 조회

    private List<CategoryInfoDto> listInsrDto; //안심보험 목록
}
