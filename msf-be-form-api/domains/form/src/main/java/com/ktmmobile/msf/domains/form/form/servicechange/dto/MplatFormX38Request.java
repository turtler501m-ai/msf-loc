package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "inDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormX38Request {
    private String soc;
    private String prodHstSeq;
    private String mdlInd;
}
