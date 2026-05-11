package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName = "commHeader")
@Getter
@Setter
@NoArgsConstructor
public class ChargePlanChangeReservationCancelResponse {

    private String responseType; // 결과값
    private String responseBasic; // 결과 내용
}
