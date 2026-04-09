package com.ktmmobile.msf.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestAdditionDto.java
 * 날짜     : 2016. 1. 18. 오후 2:16:28
 * 작성자   : papier
 * 설명     : 가입신청_부가서비스 테이블(MCP_REQUEST_ADDITION)
 * </pre>
 */
public class McpRequestAdditionDto implements Serializable {

    private static final long serialVersionUID = 1L;

   /** 가입신청_키 */
    private long requestKey;
    /** 부가서비스_키 */
    private long additionKey;
    /** 부가서비스명 */
    private String additionName;
    /** 사용료 */
    private long rantal;
    /** 부가 서비스 구분 */
    private String chargeFlag ;
    /** 개통간소화 시 부가서비스 가능 여부 */
    private String osstYn;

    /** 개통간소화 시 부가서비스 가능 여부 */
    private String rateAdsvcProdRelSeq;


    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public long getAdditionKey() {
        return additionKey;
    }
    public void setAdditionKey(long additionKey) {
        this.additionKey = additionKey;
    }
    public String getAdditionName() {
        return additionName;
    }
    public void setAdditionName(String additionName) {
        this.additionName = additionName;
    }
    public long getRantal() {
        return rantal;
    }
    public long getVatRantal() {
        return (long) (rantal*1.1);
    }
    public void setRantal(long rantal) {
        this.rantal = rantal;
    }
    public String getChargeFlag() {
        return chargeFlag;
    }
    public void setChargeFlag(String chargeFlag) {
        this.chargeFlag = chargeFlag;
    }
    public String getOsstYn() {
        return osstYn;
    }
    public void setOsstYn(String osstYn) {
        this.osstYn = osstYn;
    }
	public String getRateAdsvcProdRelSeq() {
		return rateAdsvcProdRelSeq;
	}
	public void setRateAdsvcProdRelSeq(String rateAdsvcProdRelSeq) {
		this.rateAdsvcProdRelSeq = rateAdsvcProdRelSeq;
	}



}
