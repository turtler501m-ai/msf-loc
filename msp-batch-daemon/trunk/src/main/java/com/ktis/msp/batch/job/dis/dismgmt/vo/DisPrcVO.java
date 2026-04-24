package com.ktis.msp.batch.job.dis.dismgmt.vo;

import com.ktis.msp.base.BaseVo;

public class DisPrcVO extends BaseVo {

    private String contractNum;			// 계약번호
    private String effectiveDate;       // 부가서비스 가입일자
    private String rsltCd;              //결과 코드
    private String trgFnlSeq;			//최종 가입자 시퀀스
    private String trgFnlInfSeq;		//평생할인 부가서비스 가입 대상 상세 이력 시퀀스
    private String prmtId;				//프로모션 아이디
    private String evntCd;				//업무구분
    private String evntTrtmDt;			//전문 발생 일시
    private String customerId;			//고객 아이디
    private String subscriberNo;		//고객 번호
    private String soc;					//부가서비스
    private String prcMstSeq;			//가입 이력 마스터 시퀀스
    private String prcDtlSeq;			//가입 이력 상세 시퀀스
    private String globalNo;			//mplateForm 연동 번호
    private String prcsSbst;			//결과 내용
    private String succYn;				//성공 결과
    private String ncn;                 //서비스 계약번호

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRsltCd() {
        return rsltCd;
    }

    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }

    public String getTrgFnlSeq() {
        return trgFnlSeq;
    }

    public void setTrgFnlSeq(String trgFnlSeq) {
        this.trgFnlSeq = trgFnlSeq;
    }

    public String getTrgFnlInfSeq() {
        return trgFnlInfSeq;
    }

    public void setTrgFnlInfSeq(String trgFnlInfSeq) {
        this.trgFnlInfSeq = trgFnlInfSeq;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getEvntTrtmDt() {
        return evntTrtmDt;
    }

    public void setEvntTrtmDt(String evntTrtmDt) {
        this.evntTrtmDt = evntTrtmDt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getPrcMstSeq() {
        return prcMstSeq;
    }

    public void setPrcMstSeq(String prcMstSeq) {
        this.prcMstSeq = prcMstSeq;
    }

    public String getPrcDtlSeq() {
        return prcDtlSeq;
    }

    public void setPrcDtlSeq(String prcDtlSeq) {
        this.prcDtlSeq = prcDtlSeq;
    }

    public String getGlobalNo() {
        return globalNo;
    }

    public void setGlobalNo(String globalNo) {
        this.globalNo = globalNo;
    }

    public String getPrcsSbst() {
        return prcsSbst;
    }

    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }

    public String getSuccYn() {
        return succYn;
    }

    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }
}
