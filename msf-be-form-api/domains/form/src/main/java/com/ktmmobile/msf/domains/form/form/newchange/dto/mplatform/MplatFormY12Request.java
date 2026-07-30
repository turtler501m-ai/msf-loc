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
@XmlRootElement(name = "inDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormY12Request {

    private String indCd;            // 구분코드 : 0:모든스펙, 1:선택한 단말의 모든스펙
    private String intmMdlId;        // 단말기모델아이디
    private String intmSpecTypeCd;   // 기기스펙유형코드
}
