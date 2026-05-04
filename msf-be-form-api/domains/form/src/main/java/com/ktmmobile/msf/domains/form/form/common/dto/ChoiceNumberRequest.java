package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChoiceNumberRequest {
    private Long requestKey;
    private String reqWantFnNo;
    private String resNo;
}
