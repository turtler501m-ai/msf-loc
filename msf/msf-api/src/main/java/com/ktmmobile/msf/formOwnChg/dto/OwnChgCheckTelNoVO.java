package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 명의변경 연락처 검증 결과 (AS-IS nameChgChkTelNo 응답 대응).
 * RESULT_CODE: SUCCESS(정상), 0001(명변회선=미납연락처), 0002(명변회선=법정대리인), 0003(연락가능=법정대리인).
 */
public class OwnChgCheckTelNoVO {

    public static final String CODE_SUCCESS = "SUCCESS";
    public static final String CODE_SAME_ETC = "0001";
    public static final String CODE_SAME_MINOR = "0002";
    public static final String CODE_SAME_MINOR2 = "0003";

    private String resultCode;
    private String resultMsg;

    public static OwnChgCheckTelNoVO success() {
        OwnChgCheckTelNoVO vo = new OwnChgCheckTelNoVO();
        vo.setResultCode(CODE_SUCCESS);
        vo.setResultMsg("");
        return vo;
    }

    public static OwnChgCheckTelNoVO fail(String resultCode, String resultMsg) {
        OwnChgCheckTelNoVO vo = new OwnChgCheckTelNoVO();
        vo.setResultCode(resultCode);
        vo.setResultMsg(resultMsg);
        return vo;
    }

    public String getResultCode() { return resultCode; }
    public void setResultCode(String resultCode) { this.resultCode = resultCode; }
    public String getResultMsg() { return resultMsg; }
    public void setResultMsg(String resultMsg) { this.resultMsg = resultMsg; }
}
