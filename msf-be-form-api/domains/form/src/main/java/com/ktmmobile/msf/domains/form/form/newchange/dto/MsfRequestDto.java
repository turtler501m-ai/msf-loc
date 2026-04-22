package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestDto {
    private String formTypeCd; //신청서유형 : 1-신규/변경
    private String tmpStepCd; //임시저장단계
}
