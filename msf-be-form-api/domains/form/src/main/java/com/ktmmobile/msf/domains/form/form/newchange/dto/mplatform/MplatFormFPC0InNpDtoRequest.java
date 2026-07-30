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
public class MplatFormFPC0InNpDtoRequest {

    private String athnItemCd; //
    private String athnSbst; //

    private String crprNo; //
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custIdntNo; //
    private String custIdntNoIndCd; //
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String custNm; //
    private String custTypeCd; //

    private String slsCmpnCd; //판매회사코드

    //연동규격서 문서내용
    private String oderTypeCd; //오더유형코드
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String npTlphNo; //번호이동전화번호
    private String indvBizrYn; //개인사업자여부
    private String bchngNpCommCmpnCd; //변경전번호이동사업자코드
    private String npRstrtnContYn; //번호이동제한예외여부
    private String ytrpaySoffAgreYn; //해지미환급금 상계동의여부
    private String atmSeqNo; //atm일련번호
}
