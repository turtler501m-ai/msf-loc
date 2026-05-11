package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName = "outDto")
@Getter
@Setter
@NoArgsConstructor
public class ChargePlanResponse {

    private String efctStDt;
    private String famtTarifAmt;
    private String prodId;
    private String prodNm;
}
