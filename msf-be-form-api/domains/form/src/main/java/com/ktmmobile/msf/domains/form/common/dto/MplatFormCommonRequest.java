package com.ktmmobile.msf.domains.form.common.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormCommonRequest {

    private String custId;
    private String ctn;
    private String ncn;
    private String clntIp;
    private String clntUsrId;
    private String stopRsnCd;

    //Y13
    private String indCd;
    private String intmUniqIdntNo;

    private String mstSvcContId;
}
