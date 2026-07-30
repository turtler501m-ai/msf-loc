package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormY24Y25Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private String rsltCd;
        private String rsltMsg;
        @JacksonXmlProperty(localName = "ruleList")
        private RuleList ruleList;

        @Data
        public static class RuleList {

            private String prdcCd;
            private String prdcNm;
            private String ruleTypeCd;
            private String ruleRsltCd;
            private String ruleId;
            private String ruleMsgSbst;
            private String trgtPrdcCd;
            private String trgtPrdcNm;

        }

    }


}
