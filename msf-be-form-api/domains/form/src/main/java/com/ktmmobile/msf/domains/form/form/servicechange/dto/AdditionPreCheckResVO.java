package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Y24 moscPrdcTrtmPreChk response.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonRootName("outDto")
public class AdditionPreCheckResVO {

    private String rsltCd;
    private String resultCode;
    private String sbscYn;
    private String resltMsg;
    private String svcMsg;
    private String globalNo;
    private String prdcCd;
    private List<String> prdcCdList;
    private List<PreCheckResultInfo> preCheckResultList;
    private List<String> preCheckFailedPrdcCdList;
    private List<String> onlineCancelUnavailablePrdcCdList;
    private List<String> resltMsgList;
    private List<RuleInfo> ruleList;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PreCheckResultInfo {

        private String prdcCd;
        private String successYn;
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RuleInfo {

        private String prdcCd;
        private String prdcNm;
        private String ruleId;
        private String ruleMsgSbst;
        private String ruleRsltCd;
        private String ruleTypeCd;
        private String trgtPrdcCd;
        private String trgtPrdcNm;
    }
}
