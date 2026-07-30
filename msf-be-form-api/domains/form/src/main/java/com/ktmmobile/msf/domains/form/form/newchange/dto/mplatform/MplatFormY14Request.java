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
public class MplatFormY14Request {

    private String wrkjobDivCd;      // 작업구분코드 (A: 등록, U: 변경, C: 듀얼심결합, E: 듀얼심 EID 변경)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String imei;             // IMEI1 (암호화)

    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String imei2;            // IMEI2 (암호화)
}
