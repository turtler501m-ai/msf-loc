package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 아무나 SOLO 결합 요청 DTO.
 * check: X87 기존결합유무 + DB 할인부가명 체크.
 * reg: Y44 결합 처리.
 */
public class CombineCheckReqDto extends SvcChgInfoDto {

    /** 요금제 코드 (아무나SOLO 할인부가 매핑 조회용) */
    private String rateCd;

    /** 마스터 서비스 계약 ID (Y44 결합 처리 시 필수) */
    private String mstSvcContId;

    public String getRateCd() { return rateCd; }
    public void setRateCd(String rateCd) { this.rateCd = rateCd; }

    public String getMstSvcContId() { return mstSvcContId; }
    public void setMstSvcContId(String mstSvcContId) { this.mstSvcContId = mstSvcContId; }
}
