package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class IntmInsrVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String seqNum;			/* 시퀀스번호 */
	private String insrMemberNum;	/* 단말보험가입자번호 */
	private String insrCmpnNum;		/* 단말보상정보번호 */
	private String workCd;          /* 작업코드 */
	private String ifCd;			/* 연동코드 */
	private String contractNum;     /* 가입계약번호 */
//	private String svcCntrNo;       /* 서비스계약번호 */
	private String insrProdCd;      /* 보험상품코드(부가서비스) */
	private String insrProdNm;      /* 보험상품명 */
	private String insrTypeCd;		/* 보험상품타입코드 */
	private String strtDttm;        /* 보험시작일시 */
	private String endDttm;         /* 보험종료일시 */
	private String reqInDay;		/* 보험가입요청일시*/
//	private String ifTrgtYn;        /* 연동대상여부(N:비대상, Y:대상, E:제외) */
	private String ifTrgtCd;        /* 연동대상여부(Y:전송대상, N:전송대기, S:전송완료, C:전송전해지, F:등록기간만료) */
//	private String insrStatCd;      /* 보험상태코드, 0:가입대기, 1:가입승인, 2:가입해지 */
	private String insrStatCd;      /* 보험상태코드, 1:가입승인, 2:가입해지, D:상품변경 */
	private String ctn;				/* 고객전화번호 */
	private String custNm;			/* 고객명 */
	private String birthDt;			/* 생년월일 */
	private String banAddr;			/* 청구주소 */
	private String minorAgentName;  /* 법정대리인 */
	private String chnCd;           /* 가입채널, 1:대리점, 2:홈페이지(온라인), 3:고객센터 */
//	private String insrEvntCd;      /* 가입유형(1:신규개통, 2:기기변경, 0:보험해지) */
	private String insrEvntCd;      /* 가입유형(1:신규개통, 2:기기변경) */
	private String insrCmpnCd;		/* 보험사구분 */
	private String reqBuyType;      /* 구매유형(MM:단말구매, UU:유심단독) */
	private String openDt;          /* 개통일 */
	private String dvcChgDt;        /* 기기변경일 */
	private String modelNm;         /* 단말모델명(PRDT_CODE) */
	private String modelId;         /* 단말모델ID */
	private String intmSrlNo;       /* 단말일련번호 */
	private String oldYn;			/* 중고폰여부 */
	private String prdtLnchDt;		/* 단말기출시일 */
	private String outUnitPric;		/* 단말기출고가 */
	private String clauseFlag;      /* 약관동의 */
	private String imgChkYn;        /* 이미지확인대상(Y:확인완료, N:미확인) */
	private String vrfyRsltCd;      /* 검증결과(REG:등록, FNOK:1차부적격, FOK:1차적격, SNOK:2차부적격, SOK:2차적격) */
	private String fstVrfyDttm;		/* 1차검증일시 */
	private String fstVrfyId;		/* 1차검증처리자 */
	private String sndVrfyDttm;		/* 2차검증일시 */
	private String sndVrfyId;		/* 2차검증처리자 */
	private String rmk;				/* 검증결과내용 */
	private String cmpnLmtAmt;      /* 보상한도금액 */
	private String cmpnAcmlAmt;		/* 보상누적금액 */
	private String rmnCmpnAmt;		/* 잔여보상금액 */
	private String rcptAmt;			/* 영수증금액 */
	private String custBrdnAmt;		/* 고객부담금 */
	private String realCmpnAmt;		/* 실보상금액 */
	private String rmnCmpnLmtAmt;	/* 잔여보상한도금액 */
	private String bankNm;			/* 은행명 */
	private String acntNo;			/* 입금계좌번호 */
	private String dpstNm;			/* 예금주 */
	private String insrMngmNo;      /* 보험관리번호 */
	private String acdntNo;			/* 상담번호 */
	private String acdntRegDt;		/* 사고접수일자 */
	private String acdntDt;			/* 사고일자 */
	private String acdntTp;			/* 사고유형 */
	private String cmpnCnfmDt;		/* 승인일자 */
	private String joinDt;			/* 보험가입일자 */
	private String custType;		/* 고객구분 */
	private String overAmt;			/* 초과금 */
	private String usimAmt;			/* USIM비용*/
	private String cmpnModelNm;		/* 보상단말모델명 */
	private String cmpnModelId;		/* 보상단말모델ID */
	private String cmpnModelColor;	/* 보상단말색상코드 */
	private String cmpnCtn;			/* 고객비상연락처 */
	private String payType;			/* 납부방법(A:가상계좌, C:카드결제) */
	private String payAmt;			/* 납부금액 */
	private String vacBankCd;		/* 가상계좌은행코드 */
	private String vacId;			/* 가상계좌번호 */
	private String failDt;			/* 부적격처리일자 */
	private String ltlYn;			/* 잔액한도미만여부 */
	private String templateId;		/* SMS템플릿 ID */
	
	private String ncn;				/* 서비스계약번호 */
	private String custId;			/* customer id */
	private String canRsltCd;		/* 해지사유코드 */
	private String rsltCd;			/* 처리결과코드 */
	private String rsltMsg;			/* 처리결과내용 */
	
	private String procCd;			/* 작업처리코드 */
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	
	public String getInsrMemberNum() {
		return insrMemberNum;
	}

	public void setInsrMemberNum(String insrMemberNum) {
		this.insrMemberNum = insrMemberNum;
	}

	public String getInsrCmpnNum() {
		return insrCmpnNum;
	}

	public void setInsrCmpnNum(String insrCmpnNum) {
		this.insrCmpnNum = insrCmpnNum;
	}

	public String getWorkCd() {
		return workCd;
	}

	public void setWorkCd(String workCd) {
		this.workCd = workCd;
	}

	public String getIfCd() {
		return ifCd;
	}

	public void setIfCd(String ifCd) {
		this.ifCd = ifCd;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getInsrProdCd() {
		return insrProdCd;
	}

	public void setInsrProdCd(String insrProdCd) {
		this.insrProdCd = insrProdCd;
	}

	public String getInsrProdNm() {
		return insrProdNm;
	}

	public void setInsrProdNm(String insrProdNm) {
		this.insrProdNm = insrProdNm;
	}
	
	public String getInsrTypeCd() {
		return insrTypeCd;
	}

	public void setInsrTypeCd(String insrTypeCd) {
		this.insrTypeCd = insrTypeCd;
	}

	public String getStrtDttm() {
		return strtDttm;
	}

	public void setStrtDttm(String strtDttm) {
		this.strtDttm = strtDttm;
	}

	public String getEndDttm() {
		return endDttm;
	}
	

	public String getReqInDay() {
		return reqInDay;
	}

	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public String getIfTrgtCd() {
		return ifTrgtCd;
	}

	public void setIfTrgtCd(String ifTrgtCd) {
		this.ifTrgtCd = ifTrgtCd;
	}

	public String getInsrStatCd() {
		return insrStatCd;
	}

	public void setInsrStatCd(String insrStatCd) {
		this.insrStatCd = insrStatCd;
	}

	public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	public String getBanAddr() {
		return banAddr;
	}

	public void setBanAddr(String banAddr) {
		this.banAddr = banAddr;
	}

	public String getMinorAgentName() {
		return minorAgentName;
	}

	public void setMinorAgentName(String minorAgentName) {
		this.minorAgentName = minorAgentName;
	}

	public String getChnCd() {
		return chnCd;
	}

	public void setChnCd(String chnCd) {
		this.chnCd = chnCd;
	}

	public String getInsrEvntCd() {
		return insrEvntCd;
	}

	public void setInsrEvntCd(String insrEvntCd) {
		this.insrEvntCd = insrEvntCd;
	}

	public String getInsrCmpnCd() {
		return insrCmpnCd;
	}

	public void setInsrCmpnCd(String insrCmpnCd) {
		this.insrCmpnCd = insrCmpnCd;
	}
	
	public String getReqBuyType() {
		return reqBuyType;
	}

	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getDvcChgDt() {
		return dvcChgDt;
	}

	public void setDvcChgDt(String dvcChgDt) {
		this.dvcChgDt = dvcChgDt;
	}

	public String getModelNm() {
		return modelNm;
	}

	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getIntmSrlNo() {
		return intmSrlNo;
	}

	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}

	public String getOldYn() {
		return oldYn;
	}

	public void setOldYn(String oldYn) {
		this.oldYn = oldYn;
	}

	public String getPrdtLnchDt() {
		return prdtLnchDt;
	}

	public void setPrdtLnchDt(String prdtLnchDt) {
		this.prdtLnchDt = prdtLnchDt;
	}

	public String getOutUnitPric() {
		return outUnitPric;
	}

	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}

	public String getClauseFlag() {
		return clauseFlag;
	}

	public void setClauseFlag(String clauseFlag) {
		this.clauseFlag = clauseFlag;
	}

	public String getImgChkYn() {
		return imgChkYn;
	}

	public void setImgChkYn(String imgChkYn) {
		this.imgChkYn = imgChkYn;
	}

	public String getVrfyRsltCd() {
		return vrfyRsltCd;
	}

	public void setVrfyRsltCd(String vrfyRsltCd) {
		this.vrfyRsltCd = vrfyRsltCd;
	}

	public String getFstVrfyDttm() {
		return fstVrfyDttm;
	}

	public void setFstVrfyDttm(String fstVrfyDttm) {
		this.fstVrfyDttm = fstVrfyDttm;
	}

	public String getFstVrfyId() {
		return fstVrfyId;
	}

	public void setFstVrfyId(String fstVrfyId) {
		this.fstVrfyId = fstVrfyId;
	}

	public String getSndVrfyDttm() {
		return sndVrfyDttm;
	}

	public void setSndVrfyDttm(String sndVrfyDttm) {
		this.sndVrfyDttm = sndVrfyDttm;
	}

	public String getSndVrfyId() {
		return sndVrfyId;
	}

	public void setSndVrfyId(String sndVrfyId) {
		this.sndVrfyId = sndVrfyId;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getCmpnLmtAmt() {
		return cmpnLmtAmt;
	}

	public void setCmpnLmtAmt(String cmpnLmtAmt) {
		this.cmpnLmtAmt = cmpnLmtAmt;
	}

	public String getCmpnAcmlAmt() {
		return cmpnAcmlAmt;
	}

	public void setCmpnAcmlAmt(String cmpnAcmlAmt) {
		this.cmpnAcmlAmt = cmpnAcmlAmt;
	}

	public String getRmnCmpnAmt() {
		return rmnCmpnAmt;
	}

	public void setRmnCmpnAmt(String rmnCmpnAmt) {
		this.rmnCmpnAmt = rmnCmpnAmt;
	}

	public String getRcptAmt() {
		return rcptAmt;
	}

	public void setRcptAmt(String rcptAmt) {
		this.rcptAmt = rcptAmt;
	}

	public String getCustBrdnAmt() {
		return custBrdnAmt;
	}

	public void setCustBrdnAmt(String custBrdnAmt) {
		this.custBrdnAmt = custBrdnAmt;
	}

	public String getRealCmpnAmt() {
		return realCmpnAmt;
	}

	public void setRealCmpnAmt(String realCmpnAmt) {
		this.realCmpnAmt = realCmpnAmt;
	}

	public String getRmnCmpnLmtAmt() {
		return rmnCmpnLmtAmt;
	}

	public void setRmnCmpnLmtAmt(String rmnCmpnLmtAmt) {
		this.rmnCmpnLmtAmt = rmnCmpnLmtAmt;
	}

	public String getBankNm() {
		return bankNm;
	}

	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	public String getAcntNo() {
		return acntNo;
	}

	public void setAcntNo(String acntNo) {
		this.acntNo = acntNo;
	}

	public String getDpstNm() {
		return dpstNm;
	}

	public void setDpstNm(String dpstNm) {
		this.dpstNm = dpstNm;
	}

	public String getInsrMngmNo() {
		return insrMngmNo;
	}

	public void setInsrMngmNo(String insrMngmNo) {
		this.insrMngmNo = insrMngmNo;
	}

	public String getAcdntNo() {
		return acdntNo;
	}

	public void setAcdntNo(String acdntNo) {
		this.acdntNo = acdntNo;
	}

	public String getAcdntRegDt() {
		return acdntRegDt;
	}

	public void setAcdntRegDt(String acdntRegDt) {
		this.acdntRegDt = acdntRegDt;
	}

	public String getAcdntDt() {
		return acdntDt;
	}

	public void setAcdntDt(String acdntDt) {
		this.acdntDt = acdntDt;
	}

	public String getAcdntTp() {
		return acdntTp;
	}

	public void setAcdntTp(String acdntTp) {
		this.acdntTp = acdntTp;
	}

	public String getCmpnCnfmDt() {
		return cmpnCnfmDt;
	}

	public void setCmpnCnfmDt(String cmpnCnfmDt) {
		this.cmpnCnfmDt = cmpnCnfmDt;
	}

	public String getJoinDt() {
		return joinDt;
	}

	public void setJoinDt(String joinDt) {
		this.joinDt = joinDt;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getOverAmt() {
		return overAmt;
	}

	public void setOverAmt(String overAmt) {
		this.overAmt = overAmt;
	}

	public String getUsimAmt() {
		return usimAmt;
	}

	public void setUsimAmt(String usimAmt) {
		this.usimAmt = usimAmt;
	}

	public String getCmpnModelNm() {
		return cmpnModelNm;
	}

	public void setCmpnModelNm(String cmpnModelNm) {
		this.cmpnModelNm = cmpnModelNm;
	}

	public String getCmpnModelId() {
		return cmpnModelId;
	}

	public void setCmpnModelId(String cmpnModelId) {
		this.cmpnModelId = cmpnModelId;
	}

	public String getCmpnModelColor() {
		return cmpnModelColor;
	}

	public void setCmpnModelColor(String cmpnModelColor) {
		this.cmpnModelColor = cmpnModelColor;
	}

	public String getCmpnCtn() {
		return cmpnCtn;
	}

	public void setCmpnCtn(String cmpnCtn) {
		this.cmpnCtn = cmpnCtn;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getVacBankCd() {
		return vacBankCd;
	}

	public void setVacBankCd(String vacBankCd) {
		this.vacBankCd = vacBankCd;
	}

	public String getVacId() {
		return vacId;
	}

	public void setVacId(String vacId) {
		this.vacId = vacId;
	}
	
	public String getFailDt() {
		return failDt;
	}

	public void setFailDt(String failDt) {
		this.failDt = failDt;
	}
	
	public String getLtlYn() {
		return ltlYn;
	}

	public void setLtlYn(String ltlYn) {
		this.ltlYn = ltlYn;
	}
	
		
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getNcn() {
		return ncn;
	}

	public void setNcn(String ncn) {
		this.ncn = ncn;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCanRsltCd() {
		return canRsltCd;
	}

	public void setCanRsltCd(String canRsltCd) {
		this.canRsltCd = canRsltCd;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	public String getProcCd() {
		return procCd;
	}

	public void setProcCd(String procCd) {
		this.procCd = procCd;
	}

}
