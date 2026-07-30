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
public class PricePlanX89ResDto extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @JacksonXmlProperty(localName = "prdcCd")
        private String prdcCd;

        @JacksonXmlProperty(localName = "prdcNm")
        private String prdcNm;

        @JacksonXmlProperty(localName = "basicAmt")
        private String basicAmt;

        @JacksonXmlProperty(localName = "aplyDate")
        private String aplyDate;

        @JacksonXmlProperty(localName = "efctStDate")
        private String efctStDate;
    }
}
