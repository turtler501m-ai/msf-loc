package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class ResvTlphNoInDTO {

    private String gubun; //업무구분코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String tlphNo; //전화번호
    private String custNo; //고객번호
    private String tlphNoStatChngRsnCd; //전화번호상태변경사유코드
    private String tlphNoStatCd; //전화번호상태코드
    private String custTypeCd; //고객유형코드
    private String nowSvcIndCd; //현서비스구분코드
    private String encdTlphNo; //암호화전화번호
    private String mpngTlphNoYn; //매핑전화번호여부
    private String asgnAgncId; //할당대리점아이디
}
