package com.ktis.msp.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class PtnrCalcVO extends BaseVo implements Serializable {
	
	/**
    * 
    */
   private static final long serialVersionUID = -8342699136878264666L;
   
   /** 검색정보 **/
    private String searchYm; 		/** 조회값 **/
	private String resultCd;		/** 지급결과 **/
	
	/** 제휴정산내역 **/
	private String billYm;			/** 연동년월 **/
	private String linkYm;			/** 연동년월 **/
	private String subscriberNo;	/** 전화번호 **/
	
	/** 롯데멤버스 **/
	private String custNm;			/**  고객명 **/
	
   /** 미래에셋생셩 **/
   private String fromSearchYm;  /**  조회시작년월 **/
   private String toSearchYm;    /**  조회종료년월 **/
   private String insrStatCd;    /**  보험상태 **/


   /** 구글Play **/
	private String searchCd;	/** 검색구분 **/
	private String searchVal;	/** 검색어 **/
	private String svcType;    /** 구매유형 **/
    private String dcCd;       /** 할인율 **/
   
   
   
	public String getResultCd() {
		return resultCd;
	}
	
	public String getSearchYm() {
		return searchYm;
	}

	public void setSearchYm(String searchYm) {
		this.searchYm = searchYm;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public String getBillYm() {
		return billYm;
	}

	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}

	public String getLinkYm() {
		return linkYm;
	}

	public void setLinkYm(String linkYm) {
		this.linkYm = linkYm;
	}

	public String getSubscriberNo() {
		return subscriberNo;
	}

	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

   public String getFromSearchYm() {
      return fromSearchYm;
   }

   public String getToSearchYm() {
      return toSearchYm;
   }

   public String getInsrStatCd() {
      return insrStatCd;
   }

   public void setFromSearchYm(String fromSearchYm) {
      this.fromSearchYm = fromSearchYm;
   }

   public void setToSearchYm(String toSearchYm) {
      this.toSearchYm = toSearchYm;
   }

   public void setInsrStatCd(String insrStatCd) {
      this.insrStatCd = insrStatCd;
   }

	public String getSearchCd() {
		return searchCd;
	}
	
	public void setSearchCd(String searchCd) {
		this.searchCd = searchCd;
	}
	
	public String getSearchVal() {
		return searchVal;
	}
	
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	public String getSvcType() {
		return svcType;
	}
	
	public void setSvcType(String svcType) {
		this.svcType = svcType;
	}
	
	public String getDcCd() {
		return dcCd;
	}
	
	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}
		
	
}
