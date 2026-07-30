package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormNP1Request {

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String npTlphNo; //번호이동 전화번호
    private String bchngNpCommCmpnCd; //변경전번호이동사업자코드
    private String slsCmpnCd; //판매회사코드
    private String custTypeCd; //고객유형코드
    private String indvBizrYn; //개인사업자 여부
    private String custIdntNoIndCd; //고객식별번호구분코드
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custIdntNo; //고객식별번호
    private String crprNo; //법인번호
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custNm; //고객명
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String fornBrthDate; //외국인생년월일
}
