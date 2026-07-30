package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "inMsg")
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormY13Request {

    private String indCd;            // 조회구분 : 1:단말모델ID,단말일련번호 조회 , 2:IMEI 조회 , 5:단말모델ID, 실물일련번호
    private String intmMdlId;        // 기기모델아이디
    private String intmSrlNo;        // 기기일련번호
    private String intmUniqIdntNo;   // 기기유일식별번호 (IMEI)
}
