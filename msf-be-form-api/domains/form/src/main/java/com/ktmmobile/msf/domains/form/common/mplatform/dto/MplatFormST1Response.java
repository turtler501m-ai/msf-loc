package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import lombok.Getter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Getter
public class MplatFormST1Response extends MplatformBase {

    @JacksonXmlProperty(localName = "custId")
    private String custId;    //고객번호
    @JacksonXmlProperty(localName = "mvnoOrdNo")
    private String mvnoOrdNo;
    @JacksonXmlProperty(localName = "nstepGlobalId")
    private String nstepGlobalId;    //KOS 에러 Global ID
    @JacksonXmlProperty(localName = "prgrStatCd")
    private String prgrStatCd;
    @JacksonXmlProperty(localName = "rsltDt")
    private String rsltDt;    //처리일시
    @JacksonXmlProperty(localName = "svcCntrNo")
    private String svcCntrNo;    //서비스계약번호

}
