package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "commHeader")
public class ChargePlanChangeReservationCancelResponse {

    private String responseType; // 결과값
    private String responseBasic; // 결과 내용
}
