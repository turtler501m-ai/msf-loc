package com.ktis.msp.batch.job.cls.salemgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class SaleMgmtVO extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6785556478221448883L;

	/**
     * 매출년월
     */
    private String saleYm;
    
    /**
     * 조직ID
     */
    private String orgnId;
    
    /**
     * 전월잔액
     */
    private long prevMonBal;
    
    /**
     * 매출누적금액
     */
    private long saleAcmlAmt;
    
    /**
     * 입금처리금액
     */
    private long dpstProcAmt;
    
    /**
     * 결산처리금액
     */
    private long clsAcntProcAmt;
    
    /**
     * 당월잔액
     */
    private long thsmonBal;
    
    /**
     * 매출일자
     */
    private String saleDt;
    
    /**
     * 매출항목코드
     */
    private String saleItmCd;
    
    /**
     * 매출일련번호
     */
    private long saleSrlNum;
    
    /**
     * 매출건수
     */
    private int saleCnt;
    
    /**
     * 매출금액
     */
    private long saleAmt;
    
    /**
     * 부가세
     */
    private long vat;
    
    /**
     * 입금처리금액
     */
    private long dsptProcAmt;
    
    /**
     * 매출상태코드
     */
    private String saleStatCd;
    
    /**
     * 조정상태코드
     */
    private String adjStatCd;
    
    /**
     * 결산기준코드
     */
    private String clsAcntStdrCd;
    
    /**
     * 선납여부
     */
    private String prepayYn;
    
    /**
     * 매출발생구분코드
     */
    private String saleOccrSctnCd;
    
    /**
     * 매출발생일자
     */
    private String saleOccrDt;
    
    /**
     * 매출수불반영여부
     */
    private String saleRecvnpayIssuYn;
    
    /**
     * 매출수불반영일자
     */
    private String saleRecvnpayIssuDt;
    
    /**
     * 결산처리여부
     */
    private String clsAcntProcYn;
    
    /**
     * 결산처리일자
     */
    private String clsAcntProcDt;
    
    /**
     * 결산년월
     */
    private String clsAcntYm;
    
    /**
     * 여신반영일자
     */
    private String crdtLoanApplDt;
    
    /**
     * 전문관리번호
     */
    private String srlMgmtNum;
    
    /**
     * 전표일자
     */
    private String slpDt;
    
    /**
     * 조정일자
     */
    private String adjDt;
    
    /**
     * 조정조직ID
     */
    private String adjOrgnId;
    
    /**
     * 조정일련번호
     */
    private long adjSrlNum;
    
    /**
     * 조정처리일자
     */
    private String adjProcDt;
    
    /**
     * 조정처리자ID
     */
    private String adjProcrId;
    
    /**
     * 사용자ID
     */
    private String userId;
    
    /**
     * 조정금액
     */
    private long adjAmt;
    
    /**
     * 조정건수
     */
    private int adjCnt;
    
    /**
     * 조정사유
     */
    private String adjRsn;
    
    /**
     * 여신적용여부 
     */
    private String crdtLoanApplYn;
    
    /**
     * 송장ID
     */
    private String invcId;
    
    /**
     * 이동요청번호
     */
    private String mvReqNum;
    
    /**
     * 여신구분코드
     */
    private String crdtLoanCd;
    
    /**
     * 매출승인/확정시 구분코드
     */
    private String saleStatSctnCd;
    
    /**
     * 파일명(청구매출수납)
     */
    private String fileNm;

    private String strtDt;
    private String endDt;
    private String rmk;
    private String prdtId;
    private String orgSaleDt;
    
    private String inoutId;
    
	public String getInvcId() {
		return invcId;
	}

	public void setInvcId(String invcId) {
		this.invcId = invcId;
	}

	public String getMvReqNum() {
		return mvReqNum;
	}

	public void setMvReqNum(String mvReqNum) {
		this.mvReqNum = mvReqNum;
	}

	public String getCrdtLoanApplYn() {
		return crdtLoanApplYn;
	}

	public void setCrdtLoanApplYn(String crdtLoanApplYn) {
		this.crdtLoanApplYn = crdtLoanApplYn;
	}

	public int getAdjCnt() {
		return adjCnt;
	}

	public void setAdjCnt(int adjCnt) {
		this.adjCnt = adjCnt;
	}

	public String getAdjRsn() {
		return adjRsn;
	}

	public void setAdjRsn(String adjRsn) {
		this.adjRsn = adjRsn;
	}

	public long getAdjAmt() {
		return adjAmt;
	}

	public void setAdjAmt(long adjAmt) {
		this.adjAmt = adjAmt;
	}

	public String getAdjDt() {
		return adjDt;
	}

	public void setAdjDt(String adjDt) {
		this.adjDt = adjDt;
	}

	public String getAdjOrgnId() {
		return adjOrgnId;
	}

	public void setAdjOrgnId(String adjOrgnId) {
		this.adjOrgnId = adjOrgnId;
	}

	public long getAdjSrlNum() {
		return adjSrlNum;
	}

	public void setAdjSrlNum(long adjSrlNum) {
		this.adjSrlNum = adjSrlNum;
	}

	public String getAdjProcDt() {
		return adjProcDt;
	}

	public void setAdjProcDt(String adjProcDt) {
		this.adjProcDt = adjProcDt;
	}

	public String getAdjProcrId() {
		return adjProcrId;
	}

	public void setAdjProcrId(String adjProcrId) {
		this.adjProcrId = adjProcrId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSaleYm() {
		return saleYm;
	}

	public void setSaleYm(String saleYm) {
		this.saleYm = saleYm;
	}

	public String getOrgnId() {
		return orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}

	public long getPrevMonBal() {
		return prevMonBal;
	}

	public void setPrevMonBal(long prevMonBal) {
		this.prevMonBal = prevMonBal;
	}

	public long getSaleAcmlAmt() {
		return saleAcmlAmt;
	}

	public void setSaleAcmlAmt(long saleAcmlAmt) {
		this.saleAcmlAmt = saleAcmlAmt;
	}

	public long getDpstProcAmt() {
		return dpstProcAmt;
	}

	public void setDpstProcAmt(long dpstProcAmt) {
		this.dpstProcAmt = dpstProcAmt;
	}

	public long getClsAcntProcAmt() {
		return clsAcntProcAmt;
	}

	public void setClsAcntProcAmt(long clsAcntProcAmt) {
		this.clsAcntProcAmt = clsAcntProcAmt;
	}

	public long getThsmonBal() {
		return thsmonBal;
	}

	public void setThsmonBal(long thsmonBal) {
		this.thsmonBal = thsmonBal;
	}

	public String getSaleDt() {
		return saleDt;
	}

	public void setSaleDt(String saleDt) {
		this.saleDt = saleDt;
	}

	public String getSaleItmCd() {
		return saleItmCd;
	}

	public void setSaleItmCd(String saleItmCd) {
		this.saleItmCd = saleItmCd;
	}

	public long getSaleSrlNum() {
		return saleSrlNum;
	}

	public void setSaleSrlNum(long saleSrlNum) {
		this.saleSrlNum = saleSrlNum;
	}

	public int getSaleCnt() {
		return saleCnt;
	}

	public void setSaleCnt(int saleCnt) {
		this.saleCnt = saleCnt;
	}

	public long getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(long saleAmt) {
		this.saleAmt = saleAmt;
	}

	public long getVat() {
		return vat;
	}

	public void setVat(long vat) {
		this.vat = vat;
	}

	public long getDsptProcAmt() {
		return dsptProcAmt;
	}

	public void setDsptProcAmt(long dsptProcAmt) {
		this.dsptProcAmt = dsptProcAmt;
	}

	public String getSaleStatCd() {
		return saleStatCd;
	}

	public void setSaleStatCd(String saleStatCd) {
		this.saleStatCd = saleStatCd;
	}

	public String getAdjStatCd() {
		return adjStatCd;
	}

	public void setAdjStatCd(String adjStatCd) {
		this.adjStatCd = adjStatCd;
	}

	public String getClsAcntStdrCd() {
		return clsAcntStdrCd;
	}

	public void setClsAcntStdrCd(String clsAcntStdrCd) {
		this.clsAcntStdrCd = clsAcntStdrCd;
	}

	public String getPrepayYn() {
		return prepayYn;
	}

	public void setPrepayYn(String prepayYn) {
		this.prepayYn = prepayYn;
	}

	public String getSaleOccrSctnCd() {
		return saleOccrSctnCd;
	}

	public void setSaleOccrSctnCd(String saleOccrSctnCd) {
		this.saleOccrSctnCd = saleOccrSctnCd;
	}

	public String getSaleOccrDt() {
		return saleOccrDt;
	}

	public void setSaleOccrDt(String saleOccrDt) {
		this.saleOccrDt = saleOccrDt;
	}

	public String getSaleRecvnpayIssuYn() {
		return saleRecvnpayIssuYn;
	}

	public void setSaleRecvnpayIssuYn(String saleRecvnpayIssuYn) {
		this.saleRecvnpayIssuYn = saleRecvnpayIssuYn;
	}

	public String getSaleRecvnpayIssuDt() {
		return saleRecvnpayIssuDt;
	}

	public void setSaleRecvnpayIssuDt(String saleRecvnpayIssuDt) {
		this.saleRecvnpayIssuDt = saleRecvnpayIssuDt;
	}

	public String getClsAcntProcYn() {
		return clsAcntProcYn;
	}

	public void setClsAcntProcYn(String clsAcntProcYn) {
		this.clsAcntProcYn = clsAcntProcYn;
	}

	public String getClsAcntProcDt() {
		return clsAcntProcDt;
	}

	public void setClsAcntProcDt(String clsAcntProcDt) {
		this.clsAcntProcDt = clsAcntProcDt;
	}

	public String getClsAcntYm() {
		return clsAcntYm;
	}

	public void setClsAcntYm(String clsAcntYm) {
		this.clsAcntYm = clsAcntYm;
	}

	public String getCrdtLoanApplDt() {
		return crdtLoanApplDt;
	}

	public void setCrdtLoanApplDt(String crdtLoanApplDt) {
		this.crdtLoanApplDt = crdtLoanApplDt;
	}

	public String getSrlMgmtNum() {
		return srlMgmtNum;
	}

	public void setSrlMgmtNum(String srlMgmtNum) {
		this.srlMgmtNum = srlMgmtNum;
	}

	public String getSlpDt() {
		return slpDt;
	}

	public void setSlpDt(String slpDt) {
		this.slpDt = slpDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCrdtLoanCd() {
		return crdtLoanCd;
	}

	public void setCrdtLoanCd(String crdtLoanCd) {
		this.crdtLoanCd = crdtLoanCd;
	}

	public String getSaleStatSctnCd() {
		return saleStatSctnCd;
	}

	public void setSaleStatSctnCd(String saleStatSctnCd) {
		this.saleStatSctnCd = saleStatSctnCd;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getStrtDt() {
		return strtDt;
	}

	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getPrdtId() {
		return prdtId;
	}

	public void setPrdtId(String prdtId) {
		this.prdtId = prdtId;
	}

	public String getOrgSaleDt() {
		return orgSaleDt;
	}

	public void setOrgSaleDt(String orgSaleDt) {
		this.orgSaleDt = orgSaleDt;
	}

	public String getInoutId() {
		return inoutId;
	}

	public void setInoutId(String inoutId) {
		this.inoutId = inoutId;
	}
	
	
	
}
