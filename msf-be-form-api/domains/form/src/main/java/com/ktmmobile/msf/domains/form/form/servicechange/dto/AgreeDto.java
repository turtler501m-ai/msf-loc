package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgreeDto {
    private String agrYn;
    private String personalInfoCollectAgree;
    private String othersTrnsAgree;
    private String othersTrnsKtAgree;
    private String othersAdReceiveAgree;
    private String indvLocaPrvAgree;

    private String agrYnTime;
    private String personalInfoCollectAgreeTime;
    private String othersTrnsAgreeTime;
    private String othersTrnsKtAgreeTime;
    private String othersAdReceiveAgreeTime;
    private String indvLocaPrvAgreeTime;

}
