package com.ktmmobile.msf.formOwnChg.dto;

/**
 * 명의변경 신청서 등록 응답.
 * 파일명 규칙: dto.서비스명칭VO.java (10.서식지프로젝트.md)
 */
public class OwnChgApplyVO {

    private boolean success;
    /** 접수번호 (NMCP_NFL_CHG_TRNS.TRNS_APY_NO 등 연동 시 사용) */
    private String applicationNo;
    private String message;

    public static OwnChgApplyVO ok(String applicationNo) {
        OwnChgApplyVO r = new OwnChgApplyVO();
        r.setSuccess(true);
        r.setApplicationNo(applicationNo);
        r.setMessage("신청서 등록이 완료되었습니다.");
        return r;
    }

    public static OwnChgApplyVO fail(String message) {
        OwnChgApplyVO r = new OwnChgApplyVO();
        r.setSuccess(false);
        r.setMessage(message != null ? message : "신청서 등록에 실패했습니다.");
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
