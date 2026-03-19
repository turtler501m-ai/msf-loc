package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 양도인 신청가능여부 체크 결과 (AS-IS grantorReqChk 응답 대응).
 * RESULT_CODE: SUCCESS, 0002(STOP), 0003(NONPAY), 0004(LESSNINETY), 0005(ERROR).
 */
public class OwnChgGrantorReqChkVO {

    public static final String CODE_SUCCESS = "SUCCESS";
    public static final String CODE_STOP = "0002";
    public static final String CODE_NONPAY = "0003";
    public static final String CODE_LESSNINETY = "0004";
    public static final String CODE_ERROR = "0005";

    private String resultCode;
    private String resultMsg;

    public static OwnChgGrantorReqChkVO success() {
        OwnChgGrantorReqChkVO vo = new OwnChgGrantorReqChkVO();
        vo.setResultCode(CODE_SUCCESS);
        vo.setResultMsg("");
        return vo;
    }

    public static OwnChgGrantorReqChkVO fail(String resultCode, String resultMsg) {
        OwnChgGrantorReqChkVO vo = new OwnChgGrantorReqChkVO();
        vo.setResultCode(resultCode);
        vo.setResultMsg(resultMsg);
        return vo;
    }

    public String getResultCode() { return resultCode; }
    public void setResultCode(String resultCode) { this.resultCode = resultCode; }
    public String getResultMsg() { return resultMsg; }
    public void setResultMsg(String resultMsg) { this.resultMsg = resultMsg; }
}
