package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
public class ChargePlanChangeReservationResponse {

    private String prdcCd; // 상품코드
    private String prdcNm; // 상품명
    private String basicAmt; // 요금제 변경 대상 기본료
    private String aplyDate; // 요금제 변경 신청일자
    private String efctStDate; // 요금제 변경 적용일자
}
