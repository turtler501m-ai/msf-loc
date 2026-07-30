package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inNpDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormPC0InNpDtoRequest {

    private String athnItemCd; //2
    private String athnSbst; //
    private String bchngNpCommCmpnCd; //
    private String crprNo; //
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custIdntNo; //
    private String custIdntNoIndCd; //
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custNm; //
    private String custTypeCd; //
    private String indvBizrYn; //
    private String npRstrtnContYn; //
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String npTlphNo; //
    private String oderTypeCd; //
    private String slsCmpnCd; //
    private String ytrpaySoffAgreYn; //
}
