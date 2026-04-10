package com.ktis.msp.rsk.statMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class StatMgmtVO extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 조회조건
	private String billYm;
	private String searchType;
	private String searchVal;
	private String orgnId;
	private String serviceType;
	private String searchDate;
	private String strtDt;
	private String endDt;
	private String stdrDt;
	private String applStrtDt;
	private String applEndDt;
	private String acntTp;
	private String billItemCd;
	private String itemCdNm;
	private String ktInvCd;
	private String ktInvRate;
	private String sttlItmCd;
	private String rmk;
	private String flag;
	private String usgYm;
	private String openYm;
	private String rateCd;
	private String trgtYm;
	private String strtYm;
	private String endYm;
	private String pppo;
	private String contractNum;
	private String unpdYn;

	/**
	 * 2018.3.23 권오승 추가
	 * 청구항목별자료
	 * **/

	private String billCd;		      	 /**청구항목코드*/ 
	private String itemNm;       	/**청구항목코드명*/
	private String iPymnPym;		/**즉납분 수납*/ 	
	private String iPymnBck; 		/**즉납분 수납취소*/
	private String iPymnAdj; 		/**즉납분 조정*/
	private String iPymnAdjr; 		/**즉납분 조정취소*/
	private String iPymnInv; 		/**즉납분 청구*/
	private String iPymnRfn; 		/**즉납분 환불*/
	private String iPymnRfnr; 		/**즉납분 환불취소*/
	private String bPymnPym; 	/**정기청구 수납*/
	private String bPymnBck; 	/**정기청구 수납취소*/
	private String bPymnAdj; 		/**정기청구 조정*/
	private String bPymnAdjr; 	/**정기청구 조정취소*/
	private String bPymnInv;		/**정기청구 청구*/
	
	/**
	 * 20180628 부가세 추가
	 * */
	private String iVat;
	private String bVat;
	
	/**
	 * 20180709 각 청구 항목별 부가세 추가
	 * */
	
	private String billCdVat;		      	 /**청구항목코드 부가세*/ 
	private String itemNmVat;       	/**청구항목코드명 부가세*/
	private String iPymnPymVat;		/**즉납분 수납 부가세*/ 	
	private String iPymnBckVat; 		/**즉납분 수납취소 부가세*/
	private String iPymnAdjVat; 		/**즉납분 조정 부가세*/
	private String iPymnAdjrVat; 		/**즉납분 조정취소 부가세*/
	private String iPymnInvVat; 		/**즉납분 청구 부가세*/
	private String iPymnRfnVat; 		/**즉납분 환불 부가세*/
	private String iPymnRfnrVat; 		/**즉납분 환불취소 부가세*/
	private String bPymnPymVat; 	/**정기청구 수납 부가세*/
	private String bPymnBckVat; 	/**정기청구 수납취소 부가세*/
	private String bPymnAdjVat; 		/**정기청구 조정 부가세*/
	private String bPymnAdjrVat; 	/**정기청구 조정취소 부가세*/
	private String bPymnInvVat;		/**정기청구 청구 부가세*/
	
	
	
	/**
	 * 20181101 각 청구 항목별 차액정산금(요금상향) 추가
	 * */
	private String newFileNm;		      	 /**최근파일명*/
	private String iAdPym;		      	 /**차액정산금 즉납분 수납*/ 
	private String iAdPymVat;       	/**차액정산금 즉납분 수납부가세*/
	private String iAdBck;				/**차액정산금 즉납분 수납취소*/ 	
	private String iAdBckVat; 			/**차액정산금 즉납분 수납취소 부가세*/
	private String iAdRfn; 				/**차액정산금 즉납분 환불*/
	private String iAdRfnVat; 			/**차액정산금 즉납분 환불 부가세*/
	private String iAdRfnr; 				/**차액정산금 즉납분 환불취소*/
	private String iAdRfnrVat; 			/**차액정산금 즉납분 환불취소부가세*/
	private String bAdPym; 			/**차액정산금 정기청구 수납*/
	private String bAdPymVat; 		/**차액정산금 정기청구 수납 부가세*/
	private String bAdBck; 				/**차액정산금 정기청구 수납취소 */
	private String bAdBckVat; 		/**차액정산금 정기청구 수납취소 부가세*/
	
	
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
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
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getApplStrtDt() {
		return applStrtDt;
	}
	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}
	public String getApplEndDt() {
		return applEndDt;
	}
	public void setApplEndDt(String applEndDt) {
		this.applEndDt = applEndDt;
	}
	public String getAcntTp() {
		return acntTp;
	}
	public void setAcntTp(String acntTp) {
		this.acntTp = acntTp;
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
	public String getKtInvCd() {
		return ktInvCd;
	}
	public void setKtInvCd(String ktInvCd) {
		this.ktInvCd = ktInvCd;
	}
	public String getKtInvRate() {
		return ktInvRate;
	}
	public void setKtInvRate(String ktInvRate) {
		this.ktInvRate = ktInvRate;
	}
	public String getSttlItmCd() {
		return sttlItmCd;
	}
	public void setSttlItmCd(String sttlItmCd) {
		this.sttlItmCd = sttlItmCd;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUsgYm() {
		return usgYm;
	}
	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
	}
	public String getOpenYm() {
		return openYm;
	}
	public void setOpenYm(String openYm) {
		this.openYm = openYm;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getTrgtYm() {
		return trgtYm;
	}
	public void setTrgtYm(String trgtYm) {
		this.trgtYm = trgtYm;
	}
	public String getStrtYm() {
		return strtYm;
	}
	public void setStrtYm(String strtYm) {
		this.strtYm = strtYm;
	}
	public String getEndYm() {
		return endYm;
	}
	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}
	public String getPppo() {
		return pppo;
	}
	public void setPppo(String pppo) {
		this.pppo = pppo;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getUnpdYn() {
		return unpdYn;
	}
	public void setUnpdYn(String unpdYn) {
		this.unpdYn = unpdYn;
	}
	public String getBillCd() {
		return billCd;
	}
	public void setBillCd(String billCd) {
		this.billCd = billCd;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public String getiPymnPym() {
		return iPymnPym;
	}
	public void setiPymnPym(String iPymnPym) {
		this.iPymnPym = iPymnPym;
	}
	public String getiPymnBck() {
		return iPymnBck;
	}
	public void setiPymnBck(String iPymnBck) {
		this.iPymnBck = iPymnBck;
	}
	public String getiPymnAdj() {
		return iPymnAdj;
	}
	public void setiPymnAdj(String iPymnAdj) {
		this.iPymnAdj = iPymnAdj;
	}
	public String getiPymnAdjr() {
		return iPymnAdjr;
	}
	public void setiPymnAdjr(String iPymnAdjr) {
		this.iPymnAdjr = iPymnAdjr;
	}
	public String getiPymnInv() {
		return iPymnInv;
	}
	public void setiPymnInv(String iPymnInv) {
		this.iPymnInv = iPymnInv;
	}
	public String getiPymnRfn() {
		return iPymnRfn;
	}
	public void setiPymnRfn(String iPymnRfn) {
		this.iPymnRfn = iPymnRfn;
	}
	public String getiPymnRfnr() {
		return iPymnRfnr;
	}
	public void setiPymnRfnr(String iPymnRfnr) {
		this.iPymnRfnr = iPymnRfnr;
	}
	public String getbPymnPym() {
		return bPymnPym;
	}
	public void setbPymnPym(String bPymnPym) {
		this.bPymnPym = bPymnPym;
	}
	public String getbPymnBck() {
		return bPymnBck;
	}
	public void setbPymnBck(String bPymnBck) {
		this.bPymnBck = bPymnBck;
	}
	public String getbPymnAdj() {
		return bPymnAdj;
	}
	public void setbPymnAdj(String bPymnAdj) {
		this.bPymnAdj = bPymnAdj;
	}
	public String getbPymnAdjr() {
		return bPymnAdjr;
	}
	public void setbPymnAdjr(String bPymnAdjr) {
		this.bPymnAdjr = bPymnAdjr;
	}
	public String getbPymnInv() {
		return bPymnInv;
	}
	public void setbPymnInv(String bPymnInv) {
		this.bPymnInv = bPymnInv;
	}
	public String getiVat() {
		return iVat;
	}
	public void setiVat(String iVat) {
		this.iVat = iVat;
	}
	public String getbVat() {
		return bVat;
	}
	public void setbVat(String bVat) {
		this.bVat = bVat;
	}
	public String getBillCdVat() {
		return billCdVat;
	}
	public void setBillCdVat(String billCdVat) {
		this.billCdVat = billCdVat;
	}
	public String getItemNmVat() {
		return itemNmVat;
	}
	public void setItemNmVat(String itemNmVat) {
		this.itemNmVat = itemNmVat;
	}
	public String getiPymnPymVat() {
		return iPymnPymVat;
	}
	public void setiPymnPymVat(String iPymnPymVat) {
		this.iPymnPymVat = iPymnPymVat;
	}
	public String getiPymnBckVat() {
		return iPymnBckVat;
	}
	public void setiPymnBckVat(String iPymnBckVat) {
		this.iPymnBckVat = iPymnBckVat;
	}
	public String getiPymnAdjVat() {
		return iPymnAdjVat;
	}
	public void setiPymnAdjVat(String iPymnAdjVat) {
		this.iPymnAdjVat = iPymnAdjVat;
	}
	public String getiPymnAdjrVat() {
		return iPymnAdjrVat;
	}
	public void setiPymnAdjrVat(String iPymnAdjrVat) {
		this.iPymnAdjrVat = iPymnAdjrVat;
	}
	public String getiPymnInvVat() {
		return iPymnInvVat;
	}
	public void setiPymnInvVat(String iPymnInvVat) {
		this.iPymnInvVat = iPymnInvVat;
	}
	public String getiPymnRfnVat() {
		return iPymnRfnVat;
	}
	public void setiPymnRfnVat(String iPymnRfnVat) {
		this.iPymnRfnVat = iPymnRfnVat;
	}
	public String getiPymnRfnrVat() {
		return iPymnRfnrVat;
	}
	public void setiPymnRfnrVat(String iPymnRfnrVat) {
		this.iPymnRfnrVat = iPymnRfnrVat;
	}
	public String getbPymnPymVat() {
		return bPymnPymVat;
	}
	public void setbPymnPymVat(String bPymnPymVat) {
		this.bPymnPymVat = bPymnPymVat;
	}
	public String getbPymnBckVat() {
		return bPymnBckVat;
	}
	public void setbPymnBckVat(String bPymnBckVat) {
		this.bPymnBckVat = bPymnBckVat;
	}
	public String getbPymnAdjVat() {
		return bPymnAdjVat;
	}
	public void setbPymnAdjVat(String bPymnAdjVat) {
		this.bPymnAdjVat = bPymnAdjVat;
	}
	public String getbPymnAdjrVat() {
		return bPymnAdjrVat;
	}
	public void setbPymnAdjrVat(String bPymnAdjrVat) {
		this.bPymnAdjrVat = bPymnAdjrVat;
	}
	public String getbPymnInvVat() {
		return bPymnInvVat;
	}
	public void setbPymnInvVat(String bPymnInvVat) {
		this.bPymnInvVat = bPymnInvVat;
	}
	public String getiAdPym() {
		return iAdPym;
	}
	public void setiAdPym(String iAdPym) {
		this.iAdPym = iAdPym;
	}
	public String getiAdPymVat() {
		return iAdPymVat;
	}
	public void setiAdPymVat(String iAdPymVat) {
		this.iAdPymVat = iAdPymVat;
	}
	public String getiAdBck() {
		return iAdBck;
	}
	public void setiAdBck(String iAdBck) {
		this.iAdBck = iAdBck;
	}
	public String getiAdBckVat() {
		return iAdBckVat;
	}
	public void setiAdBckVat(String iAdBckVat) {
		this.iAdBckVat = iAdBckVat;
	}
	public String getiAdRfn() {
		return iAdRfn;
	}
	public void setiAdRfn(String iAdRfn) {
		this.iAdRfn = iAdRfn;
	}
	public String getiAdRfnVat() {
		return iAdRfnVat;
	}
	public void setiAdRfnVat(String iAdRfnVat) {
		this.iAdRfnVat = iAdRfnVat;
	}
	public String getiAdRfnr() {
		return iAdRfnr;
	}
	public void setiAdRfnr(String iAdRfnr) {
		this.iAdRfnr = iAdRfnr;
	}
	public String getiAdRfnrVat() {
		return iAdRfnrVat;
	}
	public void setiAdRfnrVat(String iAdRfnrVat) {
		this.iAdRfnrVat = iAdRfnrVat;
	}
	public String getbAdPym() {
		return bAdPym;
	}
	public void setbAdPym(String bAdPym) {
		this.bAdPym = bAdPym;
	}
	public String getbAdPymVat() {
		return bAdPymVat;
	}
	public void setbAdPymVat(String bAdPymVat) {
		this.bAdPymVat = bAdPymVat;
	}
	public String getbAdBck() {
		return bAdBck;
	}
	public void setbAdBck(String bAdBck) {
		this.bAdBck = bAdBck;
	}
	public String getbAdBckVat() {
		return bAdBckVat;
	}
	public void setbAdBckVat(String bAdBckVat) {
		this.bAdBckVat = bAdBckVat;
	}
	public String getNewFileNm() {
		return newFileNm;
	}
	public void setNewFileNm(String newFileNm) {
		this.newFileNm = newFileNm;
	}
	
}
