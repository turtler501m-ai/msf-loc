package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 서비스변경 통합 신청 응답 VO.
 * POST /api/v1/service-change/apply 응답.
 */
public class SvcChgApplyResVO {

    /** 처리 성공 여부 */
    private boolean success;

    /** DB 저장된 신청 키 (MSF_REQUEST_SVC_CHG.REQUEST_KEY) */
    private Long requestKey;

    /** 접수번호 (requestKey 문자열, 화면 표시용) */
    private String applicationNo;

    /** 결과 메시지 (실패 시 오류 내용) */
    private String message;

    /** M플랫폼 결과 코드 */
    private String resultCode;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public Long getRequestKey() { return requestKey; }
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public String getApplicationNo() { return applicationNo; }
    public void setApplicationNo(String applicationNo) { this.applicationNo = applicationNo; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getResultCode() { return resultCode; }
    public void setResultCode(String resultCode) { this.resultCode = resultCode; }
}
