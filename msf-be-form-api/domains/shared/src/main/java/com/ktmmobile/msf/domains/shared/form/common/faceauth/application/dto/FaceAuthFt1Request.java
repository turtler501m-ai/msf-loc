package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "inDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceAuthFt1Request {
    private String fathTransacId;
}
