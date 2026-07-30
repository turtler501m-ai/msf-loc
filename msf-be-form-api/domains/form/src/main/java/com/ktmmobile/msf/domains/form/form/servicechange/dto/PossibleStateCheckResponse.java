package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;

/**
 * 상품 변경 사전체크(Y24)
 */
@Getter
@Setter
@NoArgsConstructor
@JsonRootName("outDto")
public class PossibleStateCheckResponse extends MplatformBase {

    private String resultCode;
    private String resltMsg;
    private String svcMsg;
    private String globalNo;
    private String sbscYn;
    private String ruleMsgSbst;

    // 결과코드
    @JacksonXmlProperty(localName = "rsltCd")
    private String rsltCd;

    // 결과 메시지
    @JacksonXmlProperty(localName = "rsltMsg")
    private String rsltMsg;

    @JacksonXmlProperty(localName = "ruleList")
    private List<RuleInfo> ruleList;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RuleInfo {

        // 상품코드
        @JacksonXmlProperty(localName = "prdcCd")
        private String prdcCd;

        // 상품명
        @JacksonXmlProperty(localName = "prdcNm")
        private String prdcNm;

        /*
            RULE : 상품 RULE 체크 제약 및 안내
            BETA : 상품 BETA RULE 제약
         */
        // 룰 유형 코드
        @JacksonXmlProperty(localName = "ruleTypeCd")
        private String ruleTypeCd;

        /*
            L : 변경 불가 메시지
            M : 안내메세지
         */
        // 룰 결과 코드
        @JacksonXmlProperty(localName = "ruleRsltCd")
        private String ruleRsltCd;

        // 룰 ID
        @JacksonXmlProperty(localName = "ruleId")
        private String ruleId;

        // RULE 메시지
        @JacksonXmlProperty(localName = "ruleMsgSbst")
        private String ruleMsgSbst;

        // 타겟 상품코드
        @JacksonXmlProperty(localName = "trgtPrdcCd")
        private String trgtPrdcCd;

        // 타켓 상품명
        @JacksonXmlProperty(localName = "trgtPrdcNm")
        private String trgtPrdcNm;
    }

}