package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.io.Serializable;

public class OsstUc0ReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** MVNO 오더 번호*/
    private String mvnoOrdNo;

    /** 고객번호 */
    private String custNo;

    /** 전화번호 */
    private String tlphNo;

    /** 서비스계약번호 */
    private String svcContId;

    /** 오더유형코드 */
    private String oderTypeCd;

    /** USIM 수납방법 코드 R:즉납, B:후청구, N:비구매 */
    private String usimPymnMthdCd;

    /** USIM 일련번호 */
    private String iccId;

    /** 접점코드 */
    private String cntpntCd;

    /** 판매자아이디 */
    private String slsPrsnId;

    /** 유심변경 사유코드 37:일반 41:고장-고객귀책 42:고장-사업자귀책 33:분실 */
    private String usimChgRsnCd;
    private String divCd;
    private String osstOrdNo;

    public String getMvnoOrdNo() {
        return mvnoOrdNo;
    }

    public void setMvnoOrdNo(String mvnoOrdNo) {
        this.mvnoOrdNo = mvnoOrdNo;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getTlphNo() {
        return tlphNo;
    }

    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }

    public String getSvcContId() {
        return svcContId;
    }

    public void setSvcContId(String svcContId) {
        this.svcContId = svcContId;
    }

    public String getOderTypeCd() {
        return oderTypeCd;
    }

    public void setOderTypeCd(String oderTypeCd) {
        this.oderTypeCd = oderTypeCd;
    }

    public String getUsimPymnMthdCd() {
        return usimPymnMthdCd;
    }

    public void setUsimPymnMthdCd(String usimPymnMthdCd) {
        this.usimPymnMthdCd = usimPymnMthdCd;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getCntpntCd() {
        return cntpntCd;
    }

    public void setCntpntCd(String cntpntCd) {
        this.cntpntCd = cntpntCd;
    }

    public String getSlsPrsnId() {
        return slsPrsnId;
    }

    public void setSlsPrsnId(String slsPrsnId) {
        this.slsPrsnId = slsPrsnId;
    }

    public String getUsimChgRsnCd() {
        return usimChgRsnCd;
    }

    public void setUsimChgRsnCd(String usimChgRsnCd) {
        this.usimChgRsnCd = usimChgRsnCd;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getDivCd() {
        return divCd;
    }

    public void setDivCd(String divCd) {
        this.divCd = divCd;
    }

    public String getOsstOrdNo() {
        return osstOrdNo;
    }

    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
    }

    @Override
    public String toString() {
        return "OsstUc0ReqDto [mvnoOrdNo=" + mvnoOrdNo + ", custNo=" + custNo + ", tlphNo=" + tlphNo + ", svcContId="
            + svcContId + ", oderTypeCd=" + oderTypeCd + ", usimPymnMthdCd=" + usimPymnMthdCd + ", iccId=" + iccId
            + ", cntpntCd=" + cntpntCd + ", slsPrsnId=" + slsPrsnId + ", usimChgRsnCd=" + usimChgRsnCd + "]";
    }


}
