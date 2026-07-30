package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inPrdcDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormHC0InPrdcDtoRequest {

    private String prdcCd; //상품코드
    @XmlElement(name = "prdcTypecd")
    private String prdcTypeCd; //상품타입코드
}
