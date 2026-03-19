package com.ktmmobile.msf.formComm.dto;

/**
 * 동시처리 불가 체크 응답 VO.
 */
public class ConcurrentCheckResVO {

    private boolean valid;
    private String message;

    public static ConcurrentCheckResVO ok() {
        ConcurrentCheckResVO vo = new ConcurrentCheckResVO();
        vo.valid = true;
        return vo;
    }

    public static ConcurrentCheckResVO fail(String message) {
        ConcurrentCheckResVO vo = new ConcurrentCheckResVO();
        vo.valid = false;
        vo.message = message;
        return vo;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
