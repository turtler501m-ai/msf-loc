package com.ktmmobile.msf.form.servicechange.dto;

/**
 * 부가서비스 공통 요청 DTO
 *
 * POST /api/v1/addition/my-list        (이용중 목록 조회)
 * POST /api/v1/addition/available-list (가입가능 목록 조회)
 *
 * ASIS에서는 MyPageSearchDto(세션) 또는 String 낱개 파라미터로 수신하던 것을
 * Stateless REST 전환에 따라 요청 바디로 직접 수신하도록 변경.
 *
 * @see AdditionApplyReqDto 신청/해지 요청 (soc 등 추가 필드 포함)
 */
public class AdditionReqDto {

    /** 서비스 계약번호 9자리 ([-] 제외) — M플랫폼 ncn 파라미터 */
    private String ncn;

    /** 전화번호 11자리 (10자리인 경우 앞에 0 추가) — M플랫폼 ctn 파라미터 */
    private String ctn;

    /** 고객번호 — M플랫폼 custId 파라미터 */
    private String custId;

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
}
