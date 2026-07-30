package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "inPrdcDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormFHC0InPrdcDtoRequest {

    private String prdcCd; //상품코드
    private String prdcTypeCd; //상품타입코드
}
