package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspPlcyOperTypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String salePlcyCd;  // 판매정책코드
    private String operType;    // 가입유형 코드
    private String operName;    // 가입유형 이름
    private String orgnId;

}
