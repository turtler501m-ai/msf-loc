package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import org.springframework.util.StringUtils;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("return")
public class MplatformBase {

    @JacksonXmlProperty(localName = "commHeader")
    private CommHeader commHeader;

    @JacksonXmlProperty(localName = "bizHeader")
    private BizHeader bizHeader;

    @JacksonXmlProperty(localName = "rsltCd")
    private String rsltCd;

    @JacksonXmlProperty(localName = "rsltMsg")
    private String rsltMsg;

    @JacksonXmlProperty(localName = "osstOrdNo")
    private String osstOrdNo;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommHeader {

        private String globalNo;
        private String encYn;
        private String responseType;
        private String responseCode;
        private String responseLogcd;
        private String responseTitle;
        private String responseBasic;
        private String langCode;

        public boolean isSuccess() {
            if (!StringUtils.hasText(this.responseType)) {
                return false;
            }
            return this.responseType.equals("N");
        }

    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BizHeader {

        private String appEntrPrsnId;
        private String appAgncCd;
        private String appEventCd;
        private String appSendDateTime;
        private String appRecvDateTime;
        private String appLgDateTime;
        private String appNstepUserId;
    }
}
