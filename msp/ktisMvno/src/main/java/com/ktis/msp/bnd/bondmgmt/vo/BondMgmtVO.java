package com.ktis.msp.bnd.bondmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @author 김연우
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="bondMgmtVO")
public class BondMgmtVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
	// msp_bond_mst
	private String contractNum;               // 계약번호
	private String grntInsrMngmNo;            // 보증보험번호
	private String svcCntrNo;                 // 서비스계약번호
	private String openDt;                    // 계약개통일자
	private String tmntDt;                    // 계약해지일자
	private int    instAmt;                   // 할부원금
	private int    instMnthCnt;               // 할부개월수
	private int    remainBillAmt;             // 잔여청구금액
	private int    remainAmt;                 // 잔여할부금액
	private int    remainCnt;                 // 잔여할부개월수
	private int    unpaidCnt;                 // 미납회차
	private String bondTmntYm;                // 채권만료월
	private String insrBillYm;                // 보험청구월
	private String unpaidYn;                  // 미납여부
	private String instBondStatCd;            // 할부채권상태코드
	private String grntInsrStatChngRsnCd;     // 보험해지사유
	private String instStrtDate;              // 보증보험개시일자
	private String insrTrmnDate;              // 보증보험종료일자
	private String regId;                     // 등록자ID
	private String regDttm;                   // 등록일시
	private String chngId;                    // 변경자ID
	private String chngDttm;                  // 변경일시
	
	// msp_bond_dtl
	private int    unpayArrAmt;               // 연체가산미납금액
	private int    unpayPymArrAmt;            // 연체가산미납수납금액
	private int    adjAmt;                    // 조정금액
	private int    roundAmt;                  // ROUND금액
	private int    ba1Amt;                    // VOC금액
	private int    canadjAmt;                 // 해지소액할인금액
	private String bondSeqNo;                 // 판매회차
	private String billYm;                    // 청구년월
	private int    bpymnCnt;                  // 납부회차
	private int    rmnBillAmt;                // 잔여청구금액
	private int    rmnInstAmt;                // 잔여할부금액
	private int    billAmt;                   // 청구금액
	private int    pymnAmt;                   // 수납금액
	private int    realPymnAmt;               // 실수납금액
	private String unpayYn;                   // 미납여부
	private int    unpayCnt;                  // 미납회차
	private int    unpayAmt;                  // 미납금액
	private int    unpayPymnAmt;              // 미납수납금액
	private int    imdtPrfpayAmt;             // 즉시완납금액
	private int    imdtPayAmt;                // 즉시부분납금액
	private int    midPrfpayAmt;              // 중도완납금액
	private int    midPayAmt;                 // 중도부분납금액
	private int    billArrAmt;                // 연체가산청구금액
	private int    pymnArrAmt;                // 연체가산수납금액
	private String unpayArrYn;                // 연체가산미납여부
	
	// msp_bond_sale_mst
	private String saleYm;                    // 판매년월
	private String saleNo;                    // 판매회차
	private String saleStatCd;                // 판매상태코드(C: 삭제, S:대기, A: 확정)
	private String title;                     // 판매제목
	private String trgtStrtDt;                // 개통대상시작일자
	private String trgtEndDt;                 // 개통대상종료일자
	private int    trgtRmnAmt;                // 할부잔액
	private int    trgtRmnCnt;                // 잔여개월
	private int    trgtPymCnt;                // 수납개월
	private int    trgtUnpaidCnt;             // 미납회차
	private String trgtUnpaidYn;              // 미납여부
	private String saleCfmId;                 // 판매확정자ID
	private String saleCfmDt;                 // 핀메확정일자
	
	// msp_bond_sale_dtl
	private String rtnId;                     // 회수자ID
	private String rtnYm;                     // 회수월
	private String rtnRsn;                    // 회수사유
	
	// msp_bond_sttc
	private int    canAmt;                    // 
	
	// msp_juo_sub_info
	private String subLinkName;               // 사용자이름
	private String userSsn;                   // 주민번호
	private String subStatus;                 // 가입상태
	private String subscriberNo;              // 전화번호
	private String customerType;              // 고객구분
	private String taxId;                     // 사업자등록번호
	private String subAdrPrimaryLn;           // 주소
	private String openAgntCd;                // 개통대리점
	
	private int    payAmt;                    // 납입금액
	private int    totalCnt;                  // 전체건수
	private long   totalRmnAmt;               // 전체잔액
	
	private String payStrtDt;                 // 할부개시일자
	private String payEndDt;                  // 할부종료일자
	
	private String billArrAmtYn;              // 연체가산금여부
	private String ba1AmtYn;                  // 고객불만대응여부
	private String roundAmtYn;                // 라운드여부
	private String midPrfpayAmtYn;            // 조기상환완납여부
	private String midPayAmtYn;               // 조기상환부분납여부
	private String insrBillYn;                // 보험금청구여부
	private String canadjAmtYn;               // 해지소액할인여부
	private String adjsAmtYn;				  // 조정값여부
	
	
	private String pSearchGbn;                // 구분값
	private String pSearchName;               // 검색어
	private String insrPayYn;                 // 보증보험수납여부
	
	private String trgtPeriod;                // 대상기간

	
	private String searchStartDt;			  //검색조건 시작일자
	private String searchEndDt;				  //검색조건 종료일자
	private String searchRemainAmt;			  //검색조건 잔여할부금
	private String searchRemainCnt;			  //검색조건 잔여할부개월수
	private String searchUnpaidCnt;			  //검색조건 미납회차
	

	private long   mtgPayAmt;                 // 이행금액



	
	
	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}
	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	/**
	 * @return the grntInsrMngmNo
	 */
	public String getGrntInsrMngmNo() {
		return grntInsrMngmNo;
	}
	/**
	 * @param grntInsrMngmNo the grntInsrMngmNo to set
	 */
	public void setGrntInsrMngmNo(String grntInsrMngmNo) {
		this.grntInsrMngmNo = grntInsrMngmNo;
	}
	/**
	 * @return the svcCntrNo
	 */
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	/**
	 * @param svcCntrNo the svcCntrNo to set
	 */
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	/**
	 * @return the openDt
	 */
	public String getOpenDt() {
		return openDt;
	}
	/**
	 * @param openDt the openDt to set
	 */
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	/**
	 * @return the tmntDt
	 */
	public String getTmntDt() {
		return tmntDt;
	}
	/**
	 * @param tmntDt the tmntDt to set
	 */
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}
	/**
	 * @return the instAmt
	 */
	public int getInstAmt() {
		return instAmt;
	}
	/**
	 * @param instAmt the instAmt to set
	 */
	public void setInstAmt(int instAmt) {
		this.instAmt = instAmt;
	}
	/**
	 * @return the instMnthCnt
	 */
	public int getInstMnthCnt() {
		return instMnthCnt;
	}
	/**
	 * @param instMnthCnt the instMnthCnt to set
	 */
	public void setInstMnthCnt(int instMnthCnt) {
		this.instMnthCnt = instMnthCnt;
	}
	/**
	 * @return the remainBillAmt
	 */
	public int getRemainBillAmt() {
		return remainBillAmt;
	}
	/**
	 * @param remainBillAmt the remainBillAmt to set
	 */
	public void setRemainBillAmt(int remainBillAmt) {
		this.remainBillAmt = remainBillAmt;
	}
	/**
	 * @return the remainAmt
	 */
	public int getRemainAmt() {
		return remainAmt;
	}
	/**
	 * @param remainAmt the remainAmt to set
	 */
	public void setRemainAmt(int remainAmt) {
		this.remainAmt = remainAmt;
	}
	/**
	 * @return the remainCnt
	 */
	public int getRemainCnt() {
		return remainCnt;
	}
	/**
	 * @param remainCnt the remainCnt to set
	 */
	public void setRemainCnt(int remainCnt) {
		this.remainCnt = remainCnt;
	}
	/**
	 * @return the unpaidCnt
	 */
	public int getUnpaidCnt() {
		return unpaidCnt;
	}
	/**
	 * @param unpaidCnt the unpaidCnt to set
	 */
	public void setUnpaidCnt(int unpaidCnt) {
		this.unpaidCnt = unpaidCnt;
	}
	/**
	 * @return the bondTmntYm
	 */
	public String getBondTmntYm() {
		return bondTmntYm;
	}
	/**
	 * @param bondTmntYm the bondTmntYm to set
	 */
	public void setBondTmntYm(String bondTmntYm) {
		this.bondTmntYm = bondTmntYm;
	}
	/**
	 * @return the insrBillYm
	 */
	public String getInsrBillYm() {
		return insrBillYm;
	}
	/**
	 * @param insrBillYm the insrBillYm to set
	 */
	public void setInsrBillYm(String insrBillYm) {
		this.insrBillYm = insrBillYm;
	}
	/**
	 * @return the unpaidYn
	 */
	public String getUnpaidYn() {
		return unpaidYn;
	}
	/**
	 * @param unpaidYn the unpaidYn to set
	 */
	public void setUnpaidYn(String unpaidYn) {
		this.unpaidYn = unpaidYn;
	}
	/**
	 * @return the instBondStatCd
	 */
	public String getInstBondStatCd() {
		return instBondStatCd;
	}
	/**
	 * @param instBondStatCd the instBondStatCd to set
	 */
	public void setInstBondStatCd(String instBondStatCd) {
		this.instBondStatCd = instBondStatCd;
	}
	/**
	 * @return the grntInsrStatChngRsnCd
	 */
	public String getGrntInsrStatChngRsnCd() {
		return grntInsrStatChngRsnCd;
	}
	/**
	 * @param grntInsrStatChngRsnCd the grntInsrStatChngRsnCd to set
	 */
	public void setGrntInsrStatChngRsnCd(String grntInsrStatChngRsnCd) {
		this.grntInsrStatChngRsnCd = grntInsrStatChngRsnCd;
	}
	/**
	 * @return the instStrtDate
	 */
	public String getInstStrtDate() {
		return instStrtDate;
	}
	/**
	 * @param instStrtDate the instStrtDate to set
	 */
	public void setInstStrtDate(String instStrtDate) {
		this.instStrtDate = instStrtDate;
	}
	/**
	 * @return the insrTrmnDate
	 */
	public String getInsrTrmnDate() {
		return insrTrmnDate;
	}
	/**
	 * @param insrTrmnDate the insrTrmnDate to set
	 */
	public void setInsrTrmnDate(String insrTrmnDate) {
		this.insrTrmnDate = insrTrmnDate;
	}
	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return the regDttm
	 */
	public String getRegDttm() {
		return regDttm;
	}
	/**
	 * @param regDttm the regDttm to set
	 */
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}
	/**
	 * @return the chngId
	 */
	public String getChngId() {
		return chngId;
	}
	/**
	 * @param chngId the chngId to set
	 */
	public void setChngId(String chngId) {
		this.chngId = chngId;
	}
	/**
	 * @return the chngDttm
	 */
	public String getChngDttm() {
		return chngDttm;
	}
	/**
	 * @param chngDttm the chngDttm to set
	 */
	public void setChngDttm(String chngDttm) {
		this.chngDttm = chngDttm;
	}
	/**
	 * @return the unpayArrAmt
	 */
	public int getUnpayArrAmt() {
		return unpayArrAmt;
	}
	/**
	 * @param unpayArrAmt the unpayArrAmt to set
	 */
	public void setUnpayArrAmt(int unpayArrAmt) {
		this.unpayArrAmt = unpayArrAmt;
	}
	/**
	 * @return the unpayPymArrAmt
	 */
	public int getUnpayPymArrAmt() {
		return unpayPymArrAmt;
	}
	/**
	 * @param unpayPymArrAmt the unpayPymArrAmt to set
	 */
	public void setUnpayPymArrAmt(int unpayPymArrAmt) {
		this.unpayPymArrAmt = unpayPymArrAmt;
	}
	/**
	 * @return the adjAmt
	 */
	public int getAdjAmt() {
		return adjAmt;
	}
	/**
	 * @param adjAmt the adjAmt to set
	 */
	public void setAdjAmt(int adjAmt) {
		this.adjAmt = adjAmt;
	}
	/**
	 * @return the roundAmt
	 */
	public int getRoundAmt() {
		return roundAmt;
	}
	/**
	 * @param roundAmt the roundAmt to set
	 */
	public void setRoundAmt(int roundAmt) {
		this.roundAmt = roundAmt;
	}
	/**
	 * @return the ba1Amt
	 */
	public int getBa1Amt() {
		return ba1Amt;
	}
	/**
	 * @param ba1Amt the ba1Amt to set
	 */
	public void setBa1Amt(int ba1Amt) {
		this.ba1Amt = ba1Amt;
	}
	/**
	 * @return the canadjAmt
	 */
	public int getCanadjAmt() {
		return canadjAmt;
	}
	/**
	 * @param canadjAmt the canadjAmt to set
	 */
	public void setCanadjAmt(int canadjAmt) {
		this.canadjAmt = canadjAmt;
	}
	/**
	 * @return the bondSeqNo
	 */
	public String getBondSeqNo() {
		return bondSeqNo;
	}
	/**
	 * @param bondSeqNo the bondSeqNo to set
	 */
	public void setBondSeqNo(String bondSeqNo) {
		this.bondSeqNo = bondSeqNo;
	}
	/**
	 * @return the billYm
	 */
	public String getBillYm() {
		return billYm;
	}
	/**
	 * @param billYm the billYm to set
	 */
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	/**
	 * @return the bpymnCnt
	 */
	public int getBpymnCnt() {
		return bpymnCnt;
	}
	/**
	 * @param bpymnCnt the bpymnCnt to set
	 */
	public void setBpymnCnt(int bpymnCnt) {
		this.bpymnCnt = bpymnCnt;
	}
	/**
	 * @return the rmnBillAmt
	 */
	public int getRmnBillAmt() {
		return rmnBillAmt;
	}
	/**
	 * @param rmnBillAmt the rmnBillAmt to set
	 */
	public void setRmnBillAmt(int rmnBillAmt) {
		this.rmnBillAmt = rmnBillAmt;
	}
	/**
	 * @return the rmnInstAmt
	 */
	public int getRmnInstAmt() {
		return rmnInstAmt;
	}
	/**
	 * @param rmnInstAmt the rmnInstAmt to set
	 */
	public void setRmnInstAmt(int rmnInstAmt) {
		this.rmnInstAmt = rmnInstAmt;
	}
	/**
	 * @return the billAmt
	 */
	public int getBillAmt() {
		return billAmt;
	}
	/**
	 * @param billAmt the billAmt to set
	 */
	public void setBillAmt(int billAmt) {
		this.billAmt = billAmt;
	}
	/**
	 * @return the pymnAmt
	 */
	public int getPymnAmt() {
		return pymnAmt;
	}
	/**
	 * @param pymnAmt the pymnAmt to set
	 */
	public void setPymnAmt(int pymnAmt) {
		this.pymnAmt = pymnAmt;
	}
	/**
	 * @return the realPymnAmt
	 */
	public int getRealPymnAmt() {
		return realPymnAmt;
	}
	/**
	 * @param realPymnAmt the realPymnAmt to set
	 */
	public void setRealPymnAmt(int realPymnAmt) {
		this.realPymnAmt = realPymnAmt;
	}
	/**
	 * @return the unpayYn
	 */
	public String getUnpayYn() {
		return unpayYn;
	}
	/**
	 * @param unpayYn the unpayYn to set
	 */
	public void setUnpayYn(String unpayYn) {
		this.unpayYn = unpayYn;
	}
	/**
	 * @return the unpayCnt
	 */
	public int getUnpayCnt() {
		return unpayCnt;
	}
	/**
	 * @param unpayCnt the unpayCnt to set
	 */
	public void setUnpayCnt(int unpayCnt) {
		this.unpayCnt = unpayCnt;
	}
	/**
	 * @return the unpayAmt
	 */
	public int getUnpayAmt() {
		return unpayAmt;
	}
	/**
	 * @param unpayAmt the unpayAmt to set
	 */
	public void setUnpayAmt(int unpayAmt) {
		this.unpayAmt = unpayAmt;
	}
	/**
	 * @return the unpayPymnAmt
	 */
	public int getUnpayPymnAmt() {
		return unpayPymnAmt;
	}
	/**
	 * @param unpayPymnAmt the unpayPymnAmt to set
	 */
	public void setUnpayPymnAmt(int unpayPymnAmt) {
		this.unpayPymnAmt = unpayPymnAmt;
	}
	/**
	 * @return the imdtPrfpayAmt
	 */
	public int getImdtPrfpayAmt() {
		return imdtPrfpayAmt;
	}
	/**
	 * @param imdtPrfpayAmt the imdtPrfpayAmt to set
	 */
	public void setImdtPrfpayAmt(int imdtPrfpayAmt) {
		this.imdtPrfpayAmt = imdtPrfpayAmt;
	}
	/**
	 * @return the imdtPayAmt
	 */
	public int getImdtPayAmt() {
		return imdtPayAmt;
	}
	/**
	 * @param imdtPayAmt the imdtPayAmt to set
	 */
	public void setImdtPayAmt(int imdtPayAmt) {
		this.imdtPayAmt = imdtPayAmt;
	}
	/**
	 * @return the midPrfpayAmt
	 */
	public int getMidPrfpayAmt() {
		return midPrfpayAmt;
	}
	/**
	 * @param midPrfpayAmt the midPrfpayAmt to set
	 */
	public void setMidPrfpayAmt(int midPrfpayAmt) {
		this.midPrfpayAmt = midPrfpayAmt;
	}
	/**
	 * @return the midPayAmt
	 */
	public int getMidPayAmt() {
		return midPayAmt;
	}
	/**
	 * @param midPayAmt the midPayAmt to set
	 */
	public void setMidPayAmt(int midPayAmt) {
		this.midPayAmt = midPayAmt;
	}
	/**
	 * @return the billArrAmt
	 */
	public int getBillArrAmt() {
		return billArrAmt;
	}
	/**
	 * @param billArrAmt the billArrAmt to set
	 */
	public void setBillArrAmt(int billArrAmt) {
		this.billArrAmt = billArrAmt;
	}
	/**
	 * @return the pymnArrAmt
	 */
	public int getPymnArrAmt() {
		return pymnArrAmt;
	}
	/**
	 * @param pymnArrAmt the pymnArrAmt to set
	 */
	public void setPymnArrAmt(int pymnArrAmt) {
		this.pymnArrAmt = pymnArrAmt;
	}
	/**
	 * @return the unpayArrYn
	 */
	public String getUnpayArrYn() {
		return unpayArrYn;
	}
	/**
	 * @param unpayArrYn the unpayArrYn to set
	 */
	public void setUnpayArrYn(String unpayArrYn) {
		this.unpayArrYn = unpayArrYn;
	}
	/**
	 * @return the saleYm
	 */
	public String getSaleYm() {
		return saleYm;
	}
	/**
	 * @param saleYm the saleYm to set
	 */
	public void setSaleYm(String saleYm) {
		this.saleYm = saleYm;
	}
	/**
	 * @return the saleNo
	 */
	public String getSaleNo() {
		return saleNo;
	}
	/**
	 * @param saleNo the saleNo to set
	 */
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	/**
	 * @return the saleStatCd
	 */
	public String getSaleStatCd() {
		return saleStatCd;
	}
	/**
	 * @param saleStatCd the saleStatCd to set
	 */
	public void setSaleStatCd(String saleStatCd) {
		this.saleStatCd = saleStatCd;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the trgtStrtDt
	 */
	public String getTrgtStrtDt() {
		return trgtStrtDt;
	}
	/**
	 * @param trgtStrtDt the trgtStrtDt to set
	 */
	public void setTrgtStrtDt(String trgtStrtDt) {
		this.trgtStrtDt = trgtStrtDt;
	}
	/**
	 * @return the trgtEndDt
	 */
	public String getTrgtEndDt() {
		return trgtEndDt;
	}
	/**
	 * @param trgtEndDt the trgtEndDt to set
	 */
	public void setTrgtEndDt(String trgtEndDt) {
		this.trgtEndDt = trgtEndDt;
	}
	/**
	 * @return the trgtRmnAmt
	 */
	public int getTrgtRmnAmt() {
		return trgtRmnAmt;
	}
	/**
	 * @param trgtRmnAmt the trgtRmnAmt to set
	 */
	public void setTrgtRmnAmt(int trgtRmnAmt) {
		this.trgtRmnAmt = trgtRmnAmt;
	}
	/**
	 * @return the trgtRmnCnt
	 */
	public int getTrgtRmnCnt() {
		return trgtRmnCnt;
	}
	/**
	 * @param trgtRmnCnt the trgtRmnCnt to set
	 */
	public void setTrgtRmnCnt(int trgtRmnCnt) {
		this.trgtRmnCnt = trgtRmnCnt;
	}
	/**
	 * @return the trgtPymCnt
	 */
	public int getTrgtPymCnt() {
		return trgtPymCnt;
	}
	/**
	 * @param trgtPymCnt the trgtPymCnt to set
	 */
	public void setTrgtPymCnt(int trgtPymCnt) {
		this.trgtPymCnt = trgtPymCnt;
	}
	/**
	 * @return the trgtUnpaidCnt
	 */
	public int getTrgtUnpaidCnt() {
		return trgtUnpaidCnt;
	}
	/**
	 * @param trgtUnpaidCnt the trgtUnpaidCnt to set
	 */
	public void setTrgtUnpaidCnt(int trgtUnpaidCnt) {
		this.trgtUnpaidCnt = trgtUnpaidCnt;
	}
	/**
	 * @return the trgtUnpaidYn
	 */
	public String getTrgtUnpaidYn() {
		return trgtUnpaidYn;
	}
	/**
	 * @param trgtUnpaidYn the trgtUnpaidYn to set
	 */
	public void setTrgtUnpaidYn(String trgtUnpaidYn) {
		this.trgtUnpaidYn = trgtUnpaidYn;
	}
	/**
	 * @return the saleCfmId
	 */
	public String getSaleCfmId() {
		return saleCfmId;
	}
	/**
	 * @param saleCfmId the saleCfmId to set
	 */
	public void setSaleCfmId(String saleCfmId) {
		this.saleCfmId = saleCfmId;
	}
	/**
	 * @return the saleCfmDt
	 */
	public String getSaleCfmDt() {
		return saleCfmDt;
	}
	/**
	 * @param saleCfmDt the saleCfmDt to set
	 */
	public void setSaleCfmDt(String saleCfmDt) {
		this.saleCfmDt = saleCfmDt;
	}
	/**
	 * @return the rtnId
	 */
	public String getRtnId() {
		return rtnId;
	}
	/**
	 * @param rtnId the rtnId to set
	 */
	public void setRtnId(String rtnId) {
		this.rtnId = rtnId;
	}
	/**
	 * @return the rtnYm
	 */
	public String getRtnYm() {
		return rtnYm;
	}
	/**
	 * @param rtnYm the rtnYm to set
	 */
	public void setRtnYm(String rtnYm) {
		this.rtnYm = rtnYm;
	}
	/**
	 * @return the rtnRsn
	 */
	public String getRtnRsn() {
		return rtnRsn;
	}
	/**
	 * @param rtnRsn the rtnRsn to set
	 */
	public void setRtnRsn(String rtnRsn) {
		this.rtnRsn = rtnRsn;
	}
	/**
	 * @return the canAmt
	 */
	public int getCanAmt() {
		return canAmt;
	}
	/**
	 * @param canAmt the canAmt to set
	 */
	public void setCanAmt(int canAmt) {
		this.canAmt = canAmt;
	}
	/**
	 * @return the subLinkName
	 */
	public String getSubLinkName() {
		return subLinkName;
	}
	/**
	 * @param subLinkName the subLinkName to set
	 */
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	/**
	 * @return the userSsn
	 */
	public String getUserSsn() {
		return userSsn;
	}
	/**
	 * @param userSsn the userSsn to set
	 */
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}
	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}
	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	/**
	 * @return the totalRmnAmt
	 */
	public long getTotalRmnAmt() {
		return totalRmnAmt;
	}
	/**
	 * @param totalRmnAmt the totalRmnAmt to set
	 */
	public void setTotalRmnAmt(long totalRmnAmt) {
		this.totalRmnAmt = totalRmnAmt;
	}
	/**
	 * @return the payAmt
	 */
	public int getPayAmt() {
		return payAmt;
	}
	/**
	 * @param payAmt the payAmt to set
	 */
	public void setPayAmt(int payAmt) {
		this.payAmt = payAmt;
	}
	/**
	 * @return the payStrtDt
	 */
	public String getPayStrtDt() {
		return payStrtDt;
	}
	/**
	 * @param payStrtDt the payStrtDt to set
	 */
	public void setPayStrtDt(String payStrtDt) {
		this.payStrtDt = payStrtDt;
	}
	/**
	 * @return the payEndDt
	 */
	public String getPayEndDt() {
		return payEndDt;
	}
	/**
	 * @param payEndDt the payEndDt to set
	 */
	public void setPayEndDt(String payEndDt) {
		this.payEndDt = payEndDt;
	}
	/**
	 * @return the ba1AmtYn
	 */
	public String getBa1AmtYn() {
		return ba1AmtYn;
	}
	/**
	 * @param ba1AmtYn the ba1AmtYn to set
	 */
	public void setBa1AmtYn(String ba1AmtYn) {
		this.ba1AmtYn = ba1AmtYn;
	}
	/**
	 * @return the roundAmtYn
	 */
	public String getRoundAmtYn() {
		return roundAmtYn;
	}
	/**
	 * @param roundAmtYn the roundAmtYn to set
	 */
	public void setRoundAmtYn(String roundAmtYn) {
		this.roundAmtYn = roundAmtYn;
	}
	/**
	 * @return the midPrfpayAmtYn
	 */
	public String getMidPrfpayAmtYn() {
		return midPrfpayAmtYn;
	}
	/**
	 * @param midPrfpayAmtYn the midPrfpayAmtYn to set
	 */
	public void setMidPrfpayAmtYn(String midPrfpayAmtYn) {
		this.midPrfpayAmtYn = midPrfpayAmtYn;
	}
	/**
	 * @return the midPayAmtYn
	 */
	public String getMidPayAmtYn() {
		return midPayAmtYn;
	}
	/**
	 * @param midPayAmtYn the midPayAmtYn to set
	 */
	public void setMidPayAmtYn(String midPayAmtYn) {
		this.midPayAmtYn = midPayAmtYn;
	}
	/**
	 * @return the insrBillYn
	 */
	public String getInsrBillYn() {
		return insrBillYn;
	}
	/**
	 * @param insrBillYn the insrBillYn to set
	 */
	public void setInsrBillYn(String insrBillYn) {
		this.insrBillYn = insrBillYn;
	}
	/**
	 * @return the billArrAmtYn
	 */
	public String getBillArrAmtYn() {
		return billArrAmtYn;
	}
	/**
	 * @param billArrAmtYn the billArrAmtYn to set
	 */
	public void setBillArrAmtYn(String billArrAmtYn) {
		this.billArrAmtYn = billArrAmtYn;
	}
	/**
	 * @return the canadjAmtYn
	 */
	public String getCanadjAmtYn() {
		return canadjAmtYn;
	}
	/**
	 * @param canadjAmtYn the canadjAmtYn to set
	 */
	public void setCanadjAmtYn(String canadjAmtYn) {
		this.canadjAmtYn = canadjAmtYn;
	}
	/**
	 * @return the pSearchGbn
	 */
	public String getpSearchGbn() {
		return pSearchGbn;
	}
	/**
	 * @param pSearchGbn the pSearchGbn to set
	 */
	public void setpSearchGbn(String pSearchGbn) {
		this.pSearchGbn = pSearchGbn;
	}
	/**
	 * @return the insrPayYn
	 */
	public String getInsrPayYn() {
		return insrPayYn;
	}
	/**
	 * @param insrPayYn the insrPayYn to set
	 */
	public void setInsrPayYn(String insrPayYn) {
		this.insrPayYn = insrPayYn;
	}
	/**
	 * @return the pSearchName
	 */
	public String getpSearchName() {
		return pSearchName;
	}
	/**
	 * @param pSearchName the pSearchName to set
	 */
	public void setpSearchName(String pSearchName) {
		this.pSearchName = pSearchName;
	}
	/**
	 * @return the trgtPeriod
	 */
	public String getTrgtPeriod() {
		return trgtPeriod;
	}
	/**
	 * @param trgtPeriod the trgtPeriod to set
	 */
	public void setTrgtPeriod(String trgtPeriod) {
		this.trgtPeriod = trgtPeriod;
	}
	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}
	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return the taxId
	 */
	public String getTaxId() {
		return taxId;
	}
	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	/**
	 * @return the subAdrPrimaryLn
	 */
	public String getSubAdrPrimaryLn() {
		return subAdrPrimaryLn;
	}
	/**
	 * @param subAdrPrimaryLn the subAdrPrimaryLn to set
	 */
	public void setSubAdrPrimaryLn(String subAdrPrimaryLn) {
		this.subAdrPrimaryLn = subAdrPrimaryLn;
	}
	/**
	 * @return the openAgntCd
	 */
	public String getOpenAgntCd() {
		return openAgntCd;
	}
	/**
	 * @param openAgntCd the openAgntCd to set
	 */
	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}
	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}
	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}
	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	/**
	 * @return the searchRemainAmt
	 */
	public String getSearchRemainAmt() {
		return searchRemainAmt;
	}
	/**
	 * @param searchRemainAmt the searchRemainAmt to set
	 */
	public void setSearchRemainAmt(String searchRemainAmt) {
		this.searchRemainAmt = searchRemainAmt;
	}
	/**
	 * @return the searchRemainCnt
	 */
	public String getSearchRemainCnt() {
		return searchRemainCnt;
	}
	/**
	 * @param searchRemainCnt the searchRemainCnt to set
	 */
	public void setSearchRemainCnt(String searchRemainCnt) {
		this.searchRemainCnt = searchRemainCnt;
	}
	/**
	 * @return the searchUnpaidCnt
	 */
	public String getSearchUnpaidCnt() {
		return searchUnpaidCnt;
	}
	/**
	 * @param searchUnpaidCnt the searchUnpaidCnt to set
	 */
	public void setSearchUnpaidCnt(String searchUnpaidCnt) {
		this.searchUnpaidCnt = searchUnpaidCnt;
	}
	/**
	 * @return the adjsAmtYn
	 */
	public String getAdjsAmtYn() {
		return adjsAmtYn;
	}
	/**
	 * @param adjsAmtYn the adjsAmtYn to set
	 */
	public void setAdjsAmtYn(String adjsAmtYn) {
		this.adjsAmtYn = adjsAmtYn;
	}
	/**
	 * @return the mtgPayAmt
	 */
	public long getMtgPayAmt() {
		return mtgPayAmt;
	}
	/**
	 * @param mtgPayAmt the mtgPayAmt to set
	 */
	public void setMtgPayAmt(long mtgPayAmt) {
		this.mtgPayAmt = mtgPayAmt;
	}
}
