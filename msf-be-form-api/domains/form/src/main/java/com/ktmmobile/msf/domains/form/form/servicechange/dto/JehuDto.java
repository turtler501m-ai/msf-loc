package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class JehuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String partnerId;
    private String billYm;
    private String rateCd;
    private String contractNum;
    private String fullPayYn;
    private String sendFlag;
    private String payResult;
    private String resultDtlCd;
    private String calPoint;
    private String payPoint;

}
