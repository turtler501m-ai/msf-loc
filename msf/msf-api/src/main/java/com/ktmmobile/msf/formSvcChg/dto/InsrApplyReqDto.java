package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 단말보험 가입 요청 DTO.
 * ASIS AppformController.selectInsrProdList() / 부가서비스 신청(X21) 요청 파라미터.
 */
public class InsrApplyReqDto extends SvcChgInfoDto {

    /** 보험 상품 코드 */
    private String insrProdCd;

    /** 보험 SOC 코드 */
    private String soc;

    /** 구매 유형 (단말 구분: 자급제 등) */
    private String reqBuyType;

    /** 단말 IMEI */
    private String imei;

    public String getInsrProdCd() { return insrProdCd; }
    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getReqBuyType() { return reqBuyType; }
    public void setReqBuyType(String reqBuyType) { this.reqBuyType = reqBuyType; }

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }
}
