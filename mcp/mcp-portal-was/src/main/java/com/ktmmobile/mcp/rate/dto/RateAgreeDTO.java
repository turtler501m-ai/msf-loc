package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class RateAgreeDTO implements Serializable{

    private static final long serialVersionUID = 1L;

	/** 코드그룹아이디1 */
	private String cdGroupId1;
	/** 코드그룹아이디2 */
	private String cdGroupId2;
	/** 약관버젼 */
	private int docVer;
	/** 약관내용 */
	private String docContent;
	/** 사용여부 */
	private String useYn;
	/** 생성자아이디 */
	private String cretId;
	/** 생성일자 */
	private String cretDt;
	/** 수정자아이디 */
	private String amdId;
	/** 수정일자 */
	private String amdDt;
	
	public String getCdGroupId1() {
		return cdGroupId1;
	}
	public void setCdGroupId1(String cdGroupId1) {
		this.cdGroupId1 = cdGroupId1;
	}
	
	public String getCdGroupId2() {
		return cdGroupId2;
	}
	public void setCdGroupId2(String cdGroupId2) {
		this.cdGroupId2 = cdGroupId2;
	}
	
	public int getDocVer() {
		return docVer;
	}
	public void setDocVer(int docVer) {
		this.docVer = docVer;
	}
	
	public String getDocContent() {
		return docContent;
	}
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	
}
