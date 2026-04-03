package com.ktmmobile.msf.usim.dto;

import java.io.Serializable;

import com.ktmmobile.msf.form.common.util.NmcpServiceUtils;

public class UsimMspRateDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -4481756991458722818L;
    private String rateCd;   						//요금제 코드
    private String rateNm; 						//요금제명
    private String payClCd; 						//선후불코드
    private String cdDsc; 							//선후불명
    private String rateType; 						//요금타입
    private String rateDesc; 						//요금설명
    private String vat; 							// 세금
    private String baseAmt; 						//요금
    private String dataTypeCode;					//데이터 타입
    private String joinPrice;						//가입비
    private String usimPrice;						//유심가격
    private String dcAmt;							//할인율
    private String freeCallCnt;					// 무료통화
    private String nwInCallCnt;					// 망내통화
    private String nwOutCallCnt;				// 망외통화
    private String freeSmsCnt;					// SMS
    private String freeDataCnt;					// 데이터

    private String agrmTrm;



    private String sprtTp;						//할인유형
    private String sprtTpNm;					//할인유형이름

    private String salePlcyCd;


    public String getSalePlcyCd() {
		return salePlcyCd;
	}
	public void setSalePlcyCd(String salePlcyCd) {
		this.salePlcyCd = salePlcyCd;
	}
	public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    public String getPayClCd() {
        return payClCd;
    }
    public void setPayClCd(String payClCd) {
        this.payClCd = payClCd;
    }
    public String getCdDsc() {
        return cdDsc;
    }
    public void setCdDsc(String cdDsc) {
        this.cdDsc = cdDsc;
    }
    public String getRateType() {
        return rateType;
    }
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
    public String getRateDesc() {
        return rateDesc;
    }
    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
    }
    public String getVat() {
        return vat;
    }
    public void setVat(String vat) {
        this.vat = vat;
    }
    public String getBaseAmt() {
        return baseAmt;
    }
    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }
    public String getDataTypeCode() {
        return dataTypeCode;
    }
    public void setDataTypeCode(String dataTypeCode) {
        this.dataTypeCode = dataTypeCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getJoinPrice() {
        return joinPrice;
    }
    public void setJoinPrice(String joinPrice) {
        this.joinPrice = joinPrice;
    }
    public String getUsimPrice() {
        return usimPrice;
    }
    public void setUsimPrice(String usimPrice) {
        this.usimPrice = usimPrice;
    }
    public String getDcAmt() {
        return dcAmt;
    }
    public void setDcAmt(String dcAmt) {
        this.dcAmt = dcAmt;
    }
    public String getAgrmTrm() {
        return agrmTrm;
    }
    public void setAgrmTrm(String agrmTrm) {
        this.agrmTrm = agrmTrm;
    }
    public String getFreeCallCnt() {
        return freeCallCnt;
    }
    public void setFreeCallCnt(String freeCallCnt) {
        this.freeCallCnt = freeCallCnt;
    }
    public String getNwInCallCnt() {
        return nwInCallCnt;
    }
    public void setNwInCallCnt(String nwInCallCnt) {
        this.nwInCallCnt = nwInCallCnt;
    }
    public String getNwOutCallCnt() {
        return nwOutCallCnt;
    }
    public void setNwOutCallCnt(String nwOutCallCnt) {
        this.nwOutCallCnt = nwOutCallCnt;
    }
    public String getFreeSmsCnt() {
        return freeSmsCnt;
    }
    public void setFreeSmsCnt(String freeSmsCnt) {
        this.freeSmsCnt = freeSmsCnt;
    }
    public String getFreeDataCnt() {
        return freeDataCnt;
    }
    public void setFreeDataCnt(String freeDataCnt) {
        this.freeDataCnt = freeDataCnt;
    }


    public String getSprtTp() {
        return sprtTp;
    }
    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }
    public String getSprtTpNm() {
        return sprtTpNm;
    }
    public void setSprtTpNm(String sprtTpNm) {
        this.sprtTpNm = sprtTpNm;
    }

}
