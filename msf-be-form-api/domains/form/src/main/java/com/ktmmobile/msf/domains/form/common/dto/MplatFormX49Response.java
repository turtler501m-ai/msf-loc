package com.ktmmobile.msf.domains.form.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

@EqualsAndHashCode(callSuper = true)
@Data
public class MplatFormX49Response extends MplatformBase {

    @JacksonXmlProperty(localName = "outDto")
    private OutDto outDto;

    public String returnBillTypeCd() {
        if (this.outDto != null) {
            if (this.outDto.getOutEmailDto() != null) {
                return this.outDto.getOutEmailDto().getBillTypeCd();
            }

            if (this.outDto.getOutMmsDto() != null) {
                return this.outDto.getOutMmsDto().getBillTypeCd();
            }

            if (this.outDto.getOutMailDto() != null) {
                return this.outDto.getOutMailDto().getBillTypeCd();
            }
        }
        return "";
    }

    @Data
    public static class OutDto {

        @JacksonXmlProperty(localName = "outEmailDto")
        private OutEmailDto outEmailDto;
        @JacksonXmlProperty(localName = "outMmsDto")
        private OutMmsDto outMmsDto;
        @JacksonXmlProperty(localName = "outMailDto")
        private OutMailDto outMailDto;

        @Data
        public static class OutEmailDto {

            private String billTypeCd;
            private String email;
            private String sendGubun;
            private String securMailYn;
            private String ecRcvAgreYn;

        }

        @Data
        public static class OutMmsDto {

            private String billTypeCd;
            private String ctn;
            private String slsCmpnCd;

        }

        @Data
        public static class OutMailDto {

            private String billTypeCd;
            private String adrCustNm;
            private String adrBasSbst;
            private String adrDtlSbst;
            private String adrZipCd;
            private String rdAdrBasSbst;
            private String rdAdrDtlSbst;
            private String rdAdrZipCd;

        }

    }

}
