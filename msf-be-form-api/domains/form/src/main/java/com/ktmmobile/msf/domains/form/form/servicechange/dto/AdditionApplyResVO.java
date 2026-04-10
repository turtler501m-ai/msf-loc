package com.ktmmobile.msf.domains.form.form.servicechange.dto;

/**
 * 부가서비스 신청/해지 결과 응답 VO
 *
 * POST /api/v1/addition/cancel (해지 결과)
 * POST /api/v1/addition/reg   (신청 결과)
 *
 * ASIS에서는 Map<String,Object>에 "RESULT_CODE"("S"/"E"), "message" 키로
 * 반환하던 것을 타입이 명확한 VO로 교체.
 *
 * [ASIS Map → TOBE VO 매핑]
 *   rtnMap.put("RESULT_CODE", "S")  →  success=true
 *   rtnMap.put("RESULT_CODE", "E")  →  success=false
 *   rtnMap.put("message", ...)      →  message=...
 */
public class AdditionApplyResVO {

    /** 처리 성공 여부 — true: 성공, false: 실패 */
    private boolean success;

    /**
     * 실패 사유 메시지 (실패 시에만 값 있음)
     * - NO_ONLINE_CAN_CHANGE_ADD: "해지할 수 없는 부가서비스는 고객센터를 통해 해지 가능합니다."
     * - NO_EXSIST_RATE:           "요금제 정보가 존재하지 않습니다."
     * - M플랫폼 오류 메시지
     */
    private String message;

    public AdditionApplyResVO() {}

    /** 성공 응답 생성 */
    public AdditionApplyResVO(boolean success) {
        this.success = success;
    }

    /** 실패 응답 생성 */
    public AdditionApplyResVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
