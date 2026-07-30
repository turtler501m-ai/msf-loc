package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreCheckResponse {

    private String osstOrdNo;
    private String rsltCd;
    private String rsltMsg;
    private String nstepGlobalId;
    private String preCheckYn;
}
