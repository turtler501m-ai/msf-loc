package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
public class ChargePlanResponse {

    private String efctStDt;
    private String famtTarifAmt;
    private String prodId;
    private String prodNm;
}
