package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TerminationRemainChargeReqDto {
    private Long requestKey;
    private String ncn;     // 계약번호
    private String ctn;     // 휴대폰번호
    private String custId;  // 고객ID
}
