package com.ktmmobile.msf.formSvcCncl.dto;

/**
 * 서비스해지 잔여요금/위약금 조회 응답 VO.
 * 설계서 S104020101 - X18 연동 예정.
 */
public class SvcCnclRemainChargeResVO {

    private boolean success;
    /** 잔여사용요금 (원) */
    private Long remainCharge;
    /** 위약금 (원) */
    private Long penalty;
    /** 단말기 분납 잔액 (원) */
    private Long installmentRemain;
    private String message;

    public static SvcCnclRemainChargeResVO empty() {
        SvcCnclRemainChargeResVO vo = new SvcCnclRemainChargeResVO();
        vo.success = true;
        vo.remainCharge = null;
        vo.penalty = null;
        vo.installmentRemain = null;
        vo.message = "";
        return vo;
    }

    public static SvcCnclRemainChargeResVO fail(String message) {
        SvcCnclRemainChargeResVO vo = new SvcCnclRemainChargeResVO();
        vo.success = false;
        vo.message = message;
        return vo;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public Long getRemainCharge() { return remainCharge; }
    public void setRemainCharge(Long remainCharge) { this.remainCharge = remainCharge; }
    public Long getPenalty() { return penalty; }
    public void setPenalty(Long penalty) { this.penalty = penalty; }
    public Long getInstallmentRemain() { return installmentRemain; }
    public void setInstallmentRemain(Long installmentRemain) { this.installmentRemain = installmentRemain; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
