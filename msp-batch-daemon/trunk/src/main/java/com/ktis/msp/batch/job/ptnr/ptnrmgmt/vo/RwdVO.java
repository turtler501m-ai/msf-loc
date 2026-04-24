package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class RwdVO extends BaseVo implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2108829206251983167L;
	
	private String rwdInfoOutSeq;		// 보상서비스 시퀀스
	private String rwdSeq;				// 보상서비스 시퀀스
	private String resNo;				// 예약번호
	private String requestKey;			// 요청키
	private String contractNum;			// 계약번호
	private String infoOutType;			// 계약번호
	private String rwdProdCd;			// 보상서비스상품코드
	private String rwdProdNm;			// 보상서비스명
	private String rwdPric;				// 보상서비스금액
	private String rwdPrd;				// 보상서비스기간
	private String openDt;				// 서비스가입시 단말기 개통일자  
	private String dvcChgDt;			// 기변일자
	private String reqDttm;				// 보상서비스 신청일자
	private String canDttm;				// 기변일자
	private String strtDttm;			// 기변일자
	private String endDttm;				// 기변일자
	private String rwdStatCd;			// 기변일자
	private String ctn;					// 전화번호
	private String custType;			// 고객구분
	private String custNm;				// 고객명
	private String birthDt;				// 생년월일
	private String reqBuyType;			// 구매유형
	private String modelId;				// 단말기모델ID
	private String modelNm;				// 단말기모델명
	private String intmSrlNo;			// 단말기일련번호
	private String imei;				// 단말고유식별번호
	private String imeiTwo;				// 단말고유식별번호2
	private String banAddr;				// 주소
	private String prdtLnchDt;			// 단말기출시일
	private String outUnitPric;			// 단말기출고가
	private String buyPric;				// 단말기구입가
	private String chnCd;				// 가입채널코드
	private String canRsltCd;			// 가입채널코드
	private String fileId;				// 이미지 파일 아이디
	private String fileDir;				// 이미지 파일 아이디
	private String fileExt;				// 이미지 파일 확장자
	private String regstDttm;			// 보상서비스 신청일자
	private String procYn;				// 연동대상 상태코드
	private String procDt;				// 개통대리점코드
	private String effectiveDate;       // 부가서비스 가입일자
	private String expirationDate;      // 부가서비스 만료일자
	
	/** MP연동 VO */
	private String ncn;					// 계약번호
	private String custId;				// 고객id
	private String rsltCd;
	private String rsltMsg;
	
	// 가입신청 연동 처리
	private String vrfyRsltCd;
	private String vrfyDttm;
	private String vrfyId;
	private String rmk;
	
	private String ifTrgtCd;
	private String openAgentCd;
	private String openAgent;
	
	private String rwdFileOutSeq;
	private String hostUpDir;
	
	
	// 청구,수납
	private String billYm;				// 청구월
	private String blpymDate;			// 실수납일자
	private String billAcntNo;			// 청구계정번호
	private String billItemCd;			// 청구항목코드
	private String itemCdNm;			// 청구항목코드명
	private String pymnCd;				// 수납코드
	private String pymnAmnt;			// 수납금액
	private String vatAmnt;				// 부가세
	private String pymnMthdCd;			// 납부방법코드
	private String pymnMthdNm;       	// 납부방법명
	private String adjsRsnCd;        	// 조정사유코드
	private String adjsRsnNm;      		// 조정사유명

	
	public String getBillYm() {
		return billYm;
	}

	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}

	public String getBlpymDate() {
		return blpymDate;
	}

	public void setBlpymDate(String blpymDate) {
		this.blpymDate = blpymDate;
	}

	public String getBillAcntNo() {
		return billAcntNo;
	}

	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
	}

	public String getBillItemCd() {
		return billItemCd;
	}

	public void setBillItemCd(String billItemCd) {
		this.billItemCd = billItemCd;
	}

	public String getItemCdNm() {
		return itemCdNm;
	}

	public void setItemCdNm(String itemCdNm) {
		this.itemCdNm = itemCdNm;
	}

	public String getPymnCd() {
		return pymnCd;
	}

	public void setPymnCd(String pymnCd) {
		this.pymnCd = pymnCd;
	}

	public String getPymnAmnt() {
		return pymnAmnt;
	}

	public void setPymnAmnt(String pymnAmnt) {
		this.pymnAmnt = pymnAmnt;
	}

	public String getVatAmnt() {
		return vatAmnt;
	}

	public void setVatAmnt(String vatAmnt) {
		this.vatAmnt = vatAmnt;
	}

	public String getPymnMthdCd() {
		return pymnMthdCd;
	}

	public void setPymnMthdCd(String pymnMthdCd) {
		this.pymnMthdCd = pymnMthdCd;
	}

	public String getPymnMthdNm() {
		return pymnMthdNm;
	}

	public void setPymnMthdNm(String pymnMthdNm) {
		this.pymnMthdNm = pymnMthdNm;
	}

	public String getAdjsRsnCd() {
		return adjsRsnCd;
	}

	public void setAdjsRsnCd(String adjsRsnCd) {
		this.adjsRsnCd = adjsRsnCd;
	}

	public String getAdjsRsnNm() {
		return adjsRsnNm;
	}

	public void setAdjsRsnNm(String adjsRsnNm) {
		this.adjsRsnNm = adjsRsnNm;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
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

	public String getVrfyRsltCd() {
		return vrfyRsltCd;
	}

	public void setVrfyRsltCd(String vrfyRsltCd) {
		this.vrfyRsltCd = vrfyRsltCd;
	}

	public String getVrfyDttm() {
		return vrfyDttm;
	}

	public void setVrfyDttm(String vrfyDttm) {
		this.vrfyDttm = vrfyDttm;
	}

	public String getVrfyId() {
		return vrfyId;
	}

	public void setVrfyId(String vrfyId) {
		this.vrfyId = vrfyId;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getHostUpDir() {
		return hostUpDir;
	}

	public void setHostUpDir(String hostUpDir) {
		this.hostUpDir = hostUpDir;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getRwdFileOutSeq() {
		return rwdFileOutSeq;
	}

	public void setRwdFileOutSeq(String rwdFileOutSeq) {
		this.rwdFileOutSeq = rwdFileOutSeq;
	}

	public String getIfTrgtCd() {
		return ifTrgtCd;
	}

	public void setIfTrgtCd(String ifTrgtCd) {
		this.ifTrgtCd = ifTrgtCd;
	}

	public String getOpenAgentCd() {
		return openAgentCd;
	}

	public void setOpenAgentCd(String openAgentCd) {
		this.openAgentCd = openAgentCd;
	}

	public String getOpenAgent() {
		return openAgent;
	}

	public void setOpenAgent(String openAgent) {
		this.openAgent = openAgent;
	}

	public String getRwdSeq() {
		return rwdSeq;
	}

	public void setRwdSeq(String rwdSeq) {
		this.rwdSeq = rwdSeq;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getRwdPric() {
		return rwdPric;
	}

	public void setRwdPric(String rwdPric) {
		this.rwdPric = rwdPric;
	}

	public String getRwdPrd() {
		return rwdPrd;
	}

	public void setRwdPrd(String rwdPrd) {
		this.rwdPrd = rwdPrd;
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

	public String getRegstDttm() {
		return regstDttm;
	}

	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}


	public String getRwdInfoOutSeq() {
		return rwdInfoOutSeq;
	}

	public void setRwdInfoOutSeq(String rwdInfoOutSeq) {
		this.rwdInfoOutSeq = rwdInfoOutSeq;
	}

	public String getInfoOutType() {
		return infoOutType;
	}

	public void setInfoOutType(String infoOutType) {
		this.infoOutType = infoOutType;
	}

	public String getReqDttm() {
		return reqDttm;
	}

	public void setReqDttm(String reqDttm) {
		this.reqDttm = reqDttm;
	}

	public String getCanDttm() {
		return canDttm;
	}

	public void setCanDttm(String canDttm) {
		this.canDttm = canDttm;
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

	public void setEndDttm(String endDttm) {
		this.endDttm = endDttm;
	}

	public String getRwdStatCd() {
		return rwdStatCd;
	}

	public void setRwdStatCd(String rwdStatCd) {
		this.rwdStatCd = rwdStatCd;
	}

	public String getImeiTwo() {
		return imeiTwo;
	}

	public void setImeiTwo(String imeiTwo) {
		this.imeiTwo = imeiTwo;
	}

	public String getCanRsltCd() {
		return canRsltCd;
	}

	public void setCanRsltCd(String canRsltCd) {
		this.canRsltCd = canRsltCd;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getProcDt() {
		return procDt;
	}

	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}

	public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(String birthDt) {
		this.birthDt = birthDt;
	}

	public String getReqBuyType() {
		return reqBuyType;
	}

	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
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

	public String getIntmSrlNo() {
		return intmSrlNo;
	}

	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getBanAddr() {
		return banAddr;
	}

	public void setBanAddr(String banAddr) {
		this.banAddr = banAddr;
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

	public String getBuyPric() {
		return buyPric;
	}

	public void setBuyPric(String buyPric) {
		this.buyPric = buyPric;
	}

	public String getChnCd() {
		return chnCd;
	}

	public void setChnCd(String chnCd) {
		this.chnCd = chnCd;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getRwdProdCd() {
		return rwdProdCd;
	}

	public void setRwdProdCd(String rwdProdCd) {
		this.rwdProdCd = rwdProdCd;
	}

	public String getRwdProdNm() {
		return rwdProdNm;
	}

	public void setRwdProdNm(String rwdProdNm) {
		this.rwdProdNm = rwdProdNm;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	
}