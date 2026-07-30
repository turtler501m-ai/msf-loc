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
public class PricePlanY02ResDto extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OutDto {

        @JacksonXmlProperty(localName = "efctStDt")
        private String efctStDt;
        @JacksonXmlProperty(localName = "famtTarifAmt")
        private String famtTarifAmt;
        @JacksonXmlProperty(localName = "prodId")
        private String prodId;
        @JacksonXmlProperty(localName = "prodNm")
        private String prodNm;
    }
}
