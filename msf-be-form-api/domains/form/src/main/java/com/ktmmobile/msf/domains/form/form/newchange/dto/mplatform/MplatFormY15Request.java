package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "inDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormY15Request {

    private String wrkjobDivCd;      // 작업구분코드 (A: 등록, U: 변경, C: 듀얼심결합, E: 듀얼심 EID 변경)
    private String intmModelNm;      // 기기모델명
    private String intmModelId;      // 기기모델ID
    private String intmSeq;          // 기기일련번호

    private String wifiMacAdr;       // MAC ID
    private String intmEtcPurpDivCd; // 기기기타용도구분코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String euiccId;          // eIccId (EID) (암호화)

    private String trtDivCd;         // 처리구분코드

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String imei;             // IMEI (암호화)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String imei2;            // IMEI2 (암호화)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String birthday;         // 생년월일 (암호화)

    private String sexDiv;           // 성별 (1: 남, 2: 여)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String ctn;              // 전화번호 (암호화)
}
