package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class FaceAuthFt0Request {

    private String onlineOfflnDivCd;
    private String orgId;
    private String retvCdVal;
    private String cpntId;
    private String fathRglsEnvTestYn;
    private String custIdfyNoTypeCd;
    private String fathSbscDivCd;
}
