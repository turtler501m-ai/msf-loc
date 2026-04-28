package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@JacksonXmlRootElement(localName = "outDto")
@Data
public class RealtimePayInfoResponse {

    @JacksonXmlProperty(localName = "amntDto")
    private List<AmntDto> amntDto;
    private String searchDay;
    private String searchTime;

    @Data
    public static class AmntDto {

        private String gubun;
        private String payMent;
    }
}
