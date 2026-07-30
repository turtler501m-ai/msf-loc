package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PricePlanX88ResDto extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @JacksonXmlProperty(localName = "rsltYn")
        private String rsltYn;

        @JacksonXmlProperty(localName = "outDto")
        private Message message;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        @JacksonXmlProperty(localName = "rsltCd")
        private String rsltCd;

        @JacksonXmlProperty(localName = "ruleId")
        private String ruleId;

        @JacksonXmlProperty(localName = "ruleMsgSbst")
        private String ruleMsgSbst;

    }

}
