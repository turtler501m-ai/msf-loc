package com.ktis.msp.batch.job.dis.dismgmt.vo;

import com.ktis.msp.base.BaseVo;

public class DisTrgFnlVO extends BaseVo  {

    private String trgFnlSeq;         // 마스터시퀀스
    private String trgFnlInfSeq;      // 상세시퀀스
    private String procYn;            // 처리여부
    private String rsltCd;            // 처리결과코드
    private String prcsSbst;          // 처리 결과 내용
    private String prmtId;            // 프로모션ID
    private String enggMnthCnt;       // 약정기간
    private String openAgntCd;        // 최초대리점
    private String prmtAgntCd;        // 프로모션 처리 대리점 (우수기변: 기변대리점, 이외: 최초대리점)
    private String rateCd;            // 요금제코드
    private String slsTp;             // 가입유형타입(CMN0093)
    private String slsNo;             // 판매번호
    private String modelId;           // 단말모델ID
    private String modelNm;           // 단말모델명
    private String regstId;           // 등록자ID
    private String contractNum;       // 계약번호
    private String evntCd;            // 업무구분
    private String evntTrtmNo;        // 처리번호
    private String evntTrtmDt;        // 전문발생일시
    private String effectiveDate;     // 이벤트적용일시
    private String baseDate;          // 배치 기준날짜
    
    private String batchId;			  // 배치아이디	
    private String execParam; 		  // 배치 실행 파라미터	

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

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getRsltCd() {
        return rsltCd;
    }

    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }

    public String getPrcsSbst() {
        return prcsSbst;
    }

    public void setPrcsSbst(String prcsSbst) {
        this.prcsSbst = prcsSbst;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getEnggMnthCnt() {
        return enggMnthCnt;
    }

    public void setEnggMnthCnt(String enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }

    public String getOpenAgntCd() {
        return openAgntCd;
    }

    public void setOpenAgntCd(String openAgntCd) {
        this.openAgntCd = openAgntCd;
    }

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getSlsTp() {
        return slsTp;
    }

    public void setSlsTp(String slsTp) {
        this.slsTp = slsTp;
    }

    public String getSlsNo() {
        return slsNo;
    }

    public void setSlsNo(String slsNo) {
        this.slsNo = slsNo;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelNm() {
        return modelNm;
    }

    public void setModelNm(String modelNm) {
        this.modelNm = modelNm;
    }

    public String getRegstId() {
        return regstId;
    }

    public void setRegstId(String regstId) {
        this.regstId = regstId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getEvntTrtmNo() {
        return evntTrtmNo;
    }

    public void setEvntTrtmNo(String evntTrtmNo) {
        this.evntTrtmNo = evntTrtmNo;
    }

    public String getEvntTrtmDt() {
        return evntTrtmDt;
    }

    public void setEvntTrtmDt(String evntTrtmDt) {
        this.evntTrtmDt = evntTrtmDt;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getPrmtAgntCd() {
        return prmtAgntCd;
    }

    public void setPrmtAgntCd(String prmtAgntCd) {
        this.prmtAgntCd = prmtAgntCd;
    }

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getExecParam() {
		return execParam;
	}

	public void setExecParam(String execParam) {
		this.execParam = execParam;
	}
}
