package com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId;

public record MobileIdCardHttpResponse(
    String trxcode,
    String svcCode,
    String ifType,
    String submitMode,
    String nonce,
    String stepCode,
    String result,
    String idType,
    String createDt,
    String updateDt,
    String branchCode,
    String branchNm,
    String empNo,
    String customerNm,
    String customerBirth,
    String customerRrn,
    String createTm,
    String updateTm,
    String osType,
    String middleHost
) {

    private static final String COMPLETE_STEP_CODE = "0004";

    public boolean isProcessing() {
        return !COMPLETE_STEP_CODE.equals(stepCode());
    }

    public boolean isFail() {
        return COMPLETE_STEP_CODE.equals(stepCode())
            && (result() == null || !Boolean.TRUE.toString().equalsIgnoreCase(result()));
    }
}
