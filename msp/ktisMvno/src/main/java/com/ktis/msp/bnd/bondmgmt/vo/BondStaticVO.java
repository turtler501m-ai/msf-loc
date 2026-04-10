package com.ktis.msp.bnd.bondmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="bondStaticVO")
public class BondStaticVO extends BaseVo implements Serializable {
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1L;
	
    private String pSearchOpenYm;
    private String openYm;
    
    private long bondAmt;
    private long rcvAmt;
    private long instOpenCnt;
    private long canCnt;
    private long erlyRfndCnt;
    private long erlyRfndAmt;
    
    private String billYm;
    private long instMnthNum;
    private long rmnCntrCnt;
    private long adjCnt;
    private long adjAmt;
    private long dlyCnt;
    private long dlyAddAmt;
    
    private long totalBondAmt;
    private long totalRcvAmt;
	
	/**
	 * @return the openYm
	 */
	public String getOpenYm() {
		return openYm;
	}
	/**
	 * @param openYm the openYm to set
	 */
	public void setOpenYm(String openYm) {
		this.openYm = openYm;
	}
	/**
	 * @return the bondAmt
	 */
	public long getBondAmt() {
		return bondAmt;
	}
	/**
	 * @param bondAmt the bondAmt to set
	 */
	public void setBondAmt(long bondAmt) {
		this.bondAmt = bondAmt;
	}
	/**
	 * @return the rcvAmt
	 */
	public long getRcvAmt() {
		return rcvAmt;
	}
	/**
	 * @param rcvAmt the rcvAmt to set
	 */
	public void setRcvAmt(long rcvAmt) {
		this.rcvAmt = rcvAmt;
	}
	/**
	 * @return the instOpenCnt
	 */
	public long getInstOpenCnt() {
		return instOpenCnt;
	}
	/**
	 * @param instOpenCnt the instOpenCnt to set
	 */
	public void setInstOpenCnt(long instOpenCnt) {
		this.instOpenCnt = instOpenCnt;
	}
	/**
	 * @return the canCnt
	 */
	public long getCanCnt() {
		return canCnt;
	}
	/**
	 * @param canCnt the canCnt to set
	 */
	public void setCanCnt(long canCnt) {
		this.canCnt = canCnt;
	}
	/**
	 * @return the erlyRfndCnt
	 */
	public long getErlyRfndCnt() {
		return erlyRfndCnt;
	}
	/**
	 * @param erlyRfndCnt the erlyRfndCnt to set
	 */
	public void setErlyRfndCnt(long erlyRfndCnt) {
		this.erlyRfndCnt = erlyRfndCnt;
	}
	/**
	 * @return the erlyRfndAmt
	 */
	public long getErlyRfndAmt() {
		return erlyRfndAmt;
	}
	/**
	 * @param erlyRfndAmt the erlyRfndAmt to set
	 */
	public void setErlyRfndAmt(long erlyRfndAmt) {
		this.erlyRfndAmt = erlyRfndAmt;
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
	 * @return the instMnthNum
	 */
	public long getInstMnthNum() {
		return instMnthNum;
	}
	/**
	 * @param instMnthNum the instMnthNum to set
	 */
	public void setInstMnthNum(long instMnthNum) {
		this.instMnthNum = instMnthNum;
	}
	/**
	 * @return the rmnCntrCnt
	 */
	public long getRmnCntrCnt() {
		return rmnCntrCnt;
	}
	/**
	 * @param rmnCntrCnt the rmnCntrCnt to set
	 */
	public void setRmnCntrCnt(long rmnCntrCnt) {
		this.rmnCntrCnt = rmnCntrCnt;
	}
	/**
	 * @return the adjCnt
	 */
	public long getAdjCnt() {
		return adjCnt;
	}
	/**
	 * @param adjCnt the adjCnt to set
	 */
	public void setAdjCnt(long adjCnt) {
		this.adjCnt = adjCnt;
	}
	/**
	 * @return the adjAmt
	 */
	public long getAdjAmt() {
		return adjAmt;
	}
	/**
	 * @param adjAmt the adjAmt to set
	 */
	public void setAdjAmt(long adjAmt) {
		this.adjAmt = adjAmt;
	}
	/**
	 * @return the dlyCnt
	 */
	public long getDlyCnt() {
		return dlyCnt;
	}
	/**
	 * @param dlyCnt the dlyCnt to set
	 */
	public void setDlyCnt(long dlyCnt) {
		this.dlyCnt = dlyCnt;
	}
	/**
	 * @return the dlyAddAmt
	 */
	public long getDlyAddAmt() {
		return dlyAddAmt;
	}
	/**
	 * @param dlyAddAmt the dlyAddAmt to set
	 */
	public void setDlyAddAmt(long dlyAddAmt) {
		this.dlyAddAmt = dlyAddAmt;
	}
	/**
	 * @return the pSearchOpenYm
	 */
	public String getpSearchOpenYm() {
		return pSearchOpenYm;
	}
	/**
	 * @param pSearchOpenYm the pSearchOpenYm to set
	 */
	public void setpSearchOpenYm(String pSearchOpenYm) {
		this.pSearchOpenYm = pSearchOpenYm;
	}
	/**
	 * @return the totalBondAmt
	 */
	public long getTotalBondAmt() {
		return totalBondAmt;
	}
	/**
	 * @param totalBondAmt the totalBondAmt to set
	 */
	public void setTotalBondAmt(long totalBondAmt) {
		this.totalBondAmt = totalBondAmt;
	}
	/**
	 * @return the totalRcvAmt
	 */
	public long getTotalRcvAmt() {
		return totalRcvAmt;
	}
	/**
	 * @param totalRcvAmt the totalRcvAmt to set
	 */
	public void setTotalRcvAmt(long totalRcvAmt) {
		this.totalRcvAmt = totalRcvAmt;
	}

    
}