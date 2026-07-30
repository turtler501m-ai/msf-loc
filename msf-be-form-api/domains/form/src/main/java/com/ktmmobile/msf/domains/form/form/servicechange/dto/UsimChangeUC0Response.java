package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsimChangeUC0Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    @Data
    public static class OutDto {

        private String osstOrdNo;
        private String rsltCd; //	결과코드(S:처리, F:실패)
        private String rsltMsg; //	처리결과 메세지
    }
}
