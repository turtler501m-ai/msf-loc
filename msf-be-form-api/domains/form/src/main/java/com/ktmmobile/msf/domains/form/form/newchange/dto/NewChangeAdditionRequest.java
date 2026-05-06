package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeAdditionRequest {
    private String rateCd;
    private String rateNm;
    private long baseAmt;
}
