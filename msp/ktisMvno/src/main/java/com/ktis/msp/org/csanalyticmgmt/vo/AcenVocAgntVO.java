package com.ktis.msp.org.csanalyticmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

import java.io.Serializable;

public class AcenVocAgntVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntpntShopId;
	private String searchGbn;
	private String searchName;
	private String vocAgntCd;
	private String vocAgntId;
	private String useYn;
	private long seq;
	private String usrId;

	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	public String getSearchGbn() {
		return searchGbn;
	}

	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getVocAgntCd() {
		return vocAgntCd;
	}

	public void setVocAgntCd(String vocAgntCd) {
		this.vocAgntCd = vocAgntCd;
	}

	public String getVocAgntId() {
		return vocAgntId;
	}

	public void setVocAgntId(String vocAgntId) {
		this.vocAgntId = vocAgntId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

}
