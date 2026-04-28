package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
public class OwnerChangeWireUseTimeResponse {

    private String svcContSbscDt; // 서비스계약가입일시
    private String longUseAdjDayNum; // 장기조정기간일수
    private Integer realUseDayNum; // 실사용기
    private Integer totStopDayNum;
    private Integer totUseDayNum;
}
