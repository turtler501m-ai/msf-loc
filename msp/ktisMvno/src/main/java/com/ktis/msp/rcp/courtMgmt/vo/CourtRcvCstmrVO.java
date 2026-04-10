package com.ktis.msp.rcp.courtMgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.mvc.BaseVo;

public class CourtRcvCstmrVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = -6510970033252597452L;

    private String strtDt;
    private String endDt;
    private String crTp;
    private String tpJinh;
    private String crTpCd;
    private String searchCd;
    private String searchVal;
    private String searchRrn;
    private String endYn;
    private String cstmrRrn;

    public String getCstmrRrn() {
		return cstmrRrn;
	}

	public void setCstmrRrn(String cstmrRrn) {
		this.cstmrRrn = cstmrRrn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCrTpCd(String crTpCd) {
		this.crTpCd = crTpCd;
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

	public String getCrTpCd() {
		return crTpCd;
	}

	public void setCrTypeCd(String crTpCd) {
		this.crTpCd = crTpCd;
	}

	public String getEndYn() {
		return endYn;
	}

	public void setEndYn(String endYn) {
		this.endYn = endYn;
	}

	public String getTpJinh() {
		return tpJinh;
	}

	public void setTpJinh(String tpJinh) {
		this.tpJinh = tpJinh;
	}

	public String getCrTp() {
		return crTp;
	}

	public void setCrTp(String crTp) {
		this.crTp = crTp;
	}

	public String getSearchRrn() {
		return searchRrn;
	}

	public void setSearchRrn(String searchRrn) {
		this.searchRrn = searchRrn;
	}    
	
}
