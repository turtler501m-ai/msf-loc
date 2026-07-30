package com.ktmmobile.msf.domains.shared.form.common.script.application.dto;

public record TranscriptionScriptRequest(
    String formTypeCd,
    String requestKey,
    String mnp3TgtYn,
    String nac3TgtYn,
    String hdn3TgtYn,
    String icn3TgtYn,
    String reqBuyTypeCd,
    String cstmrSelf,
    String cstmrAgent,
    String enggN,
    String enggY,
    String enggKd,
    String enggPm,
    String rmndY,
    String rateY,
    String insrY,
    String addY,

    // 명의변경용 매핑 데이터
    String cntpntCdNm,
    String userNm,
    String nflCustNm,
    String mobilePriceNm,
    String mobileMntcntAmtFee
) {

    public TranscriptionScriptVariable toOwnerChangeVariable() {
        return TranscriptionScriptVariable.ownerChange(
            cntpntCdNm,
            userNm,
            nflCustNm,
            mobilePriceNm,
            mobileMntcntAmtFee
        );
    }
}