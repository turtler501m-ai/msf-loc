package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 서비스체크 유효성 응답 VO.
 */
public class SvcChgBaseCheckResVO {

    private boolean valid;
    private String message;

    public static SvcChgBaseCheckResVO ok() {
        SvcChgBaseCheckResVO vo = new SvcChgBaseCheckResVO();
        vo.valid = true;
        return vo;
    }

    public static SvcChgBaseCheckResVO fail(String message) {
        SvcChgBaseCheckResVO vo = new SvcChgBaseCheckResVO();
        vo.valid = false;
        vo.message = message;
        return vo;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
