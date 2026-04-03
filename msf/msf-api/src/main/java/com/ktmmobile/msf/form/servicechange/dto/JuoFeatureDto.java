package com.ktmmobile.msf.form.servicechange.dto;

import java.io.Serializable;

public class JuoFeatureDto implements Serializable {

    //상품 현행화 정보

    private static final long serialVersionUID = 1L;


    /** 가입계약번호 */
    private String contractNum;
    /** 상품코드 */
    private String soc;
    /** 만기일시 */
    private String expirationDate;
    /**
     * 3개월 이내 결합이력이 존재하여 결합이 불가
     *  만기일시 3개월 연장 */
    private int intAddMonths = 0;

    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getSoc() {
        return soc;
    }
    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public int getIntAddMonths() {
        return intAddMonths;
    }
    public void setIntAddMonths(int intAddMonths) {
        this.intAddMonths = intAddMonths;
    }




}
