package com.ktmmobile.msf.domains.form.form.common.dto;

import com.ktmmobile.msf.domains.form.common.dto.IntmInsrRelDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsrProdRequest {
    private IntmInsrRelDTO intmInsrRelDTO; // JSON의 키값과 변수명이 같아야 함
    private String prodCtgId;
}
