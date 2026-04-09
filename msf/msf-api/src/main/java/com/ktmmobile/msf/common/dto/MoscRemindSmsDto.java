package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

/**
 * 제휴상품 리마인드 SMS 수신 상태 조회 및 변경
 * Y42
 *
 */
public class MoscRemindSmsDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String custId; //고객번호
    private String ncn; //사용자 서비스계약번호
    private String ctn; //사용자 전화번호
    private String prodGubun; //조회할 상품 구분값 MI: 밀리의서재 , CU: 씨유
    private String wrkjobCd; //R: 조회 , U: 변경
    private String smsRcvBlckYn; //Y:차단 , N:수신

    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getNcn() {
        return ncn;
    }
    public void setNcn(String ncn) {
        this.ncn = ncn;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getProdGubun() {
        return prodGubun;
    }
    public void setProdGubun(String prodGubun) {
        this.prodGubun = prodGubun;
    }
    public String getWrkjobCd() {
        return wrkjobCd;
    }
    public void setWrkjobCd(String wrkjobCd) {
        this.wrkjobCd = wrkjobCd;
    }
    public String getSmsRcvBlckYn() {
        return smsRcvBlckYn;
    }
    public void setSmsRcvBlckYn(String smsRcvBlckYn) {
        this.smsRcvBlckYn = smsRcvBlckYn;
    }
}
