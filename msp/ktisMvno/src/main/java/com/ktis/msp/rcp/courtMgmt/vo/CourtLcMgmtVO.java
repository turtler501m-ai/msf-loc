package com.ktis.msp.rcp.courtMgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import oracle.net.aso.i;

import java.io.Serializable;

public class CourtLcMgmtVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = -6510970033252597452L;

    private String strtDt;
    private String endDt;
    private String searchCd;
    private String searchVal;
    private String searchRrn;
    private String lcNum;
    private String telFn;
    private String telMn;
    private String telRn;
    private String contactNum;
    private String cstmrName;
    private String cstmrRrn;
    private String cstmrAddr;
    private String openDt;
    private String lcMstSeqNvl;
    private String lcMstSeq;
    private String unpdPrc;
    private String instPrc;
    private String totalPrc;
    private String newLcYn;
    private String lcDtlSeq;
    private String isLcMst;
    private int maxLcNumNvl;

	public static long getSerialversionuid() {
		return serialVersionUID;
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

    public String getSearchRrn() {
		return searchRrn;
	}

	public void setSearchRrn(String searchRrn) {
		this.searchRrn = searchRrn;
	}

	public String getLcMstSeqNvl() {
        return lcMstSeqNvl;
    }

    public void setLcMstSeqNvl(String lcMstSeqNvl) {
        this.lcMstSeqNvl = lcMstSeqNvl;
    }

    public String getTelRn() {
        return telRn;
    }

    public void setTelRn(String telRn) {
        this.telRn = telRn;
    }

    public String getTelMn() {
        return telMn;
    }

    public void setTelMn(String telMn) {
        this.telMn = telMn;
    }

    public String getTelFn() {
        return telFn;
    }

    public void setTelFn(String telFn) {
        this.telFn = telFn;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getNewLcYn() {
        return newLcYn;
    }

    public void setNewLcYn(String newLcYn) {
        this.newLcYn = newLcYn;
    }

    public String getCstmrName() {
        return cstmrName;
    }

    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }

    public String getCstmrRrn() {
        return cstmrRrn;
    }

    public void setCstmrRrn(String cstmrRrn) {
        this.cstmrRrn = cstmrRrn;
    }

    public String getCstmrAddr() {
        return cstmrAddr;
    }

    public void setCstmrAddr(String cstmrAddr) {
        this.cstmrAddr = cstmrAddr;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getUnpdPrc() {
        return unpdPrc;
    }

    public void setUnpdPrc(String unpdPrc) {
        this.unpdPrc = unpdPrc;
    }
    public String getTotalPrc() {
        return totalPrc;
    }

    public void setTotalPrc(String totalPrc) {
        this.totalPrc = totalPrc;
    }

    public String getInstPrc() {
        return instPrc;
    }

    public void setInstPrc(String instPrc) {
        this.instPrc = instPrc;
    }

    public String getLcMstSeq() {
        return lcMstSeq;
    }

    public void setLcMstSeq(String lcMstSeq) {
        this.lcMstSeq = lcMstSeq;
    }

    public String getLcDtlSeq() {
        return lcDtlSeq;
    }

    public void setLcDtlSeq(String lcDtlSeq) {
        this.lcDtlSeq = lcDtlSeq;
    }

	public String getLcNum() {
		return lcNum;
	}

	public void setLcNum(String lcNum) {
		this.lcNum = lcNum;
	}

	public String getIsLcMst() {
		return isLcMst;
	}

	public void setIsLcMst(String isLcMst) {
		this.isLcMst = isLcMst;
	}

	public int getMaxLcNumNvl() {
		return maxLcNumNvl;
	}

	public void setMaxLcNumNvl(int maxLcNumNvl) {
		this.maxLcNumNvl = maxLcNumNvl;
	}
}
