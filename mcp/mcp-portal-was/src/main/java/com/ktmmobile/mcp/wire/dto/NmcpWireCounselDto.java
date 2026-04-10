package com.ktmmobile.mcp.wire.dto;

import java.io.Serializable;

public class  NmcpWireCounselDto  implements Serializable{

    private static final long serialVersionUID = 1L;  
    
    private String counselSeq;       
    private String wireProdDtlSeq; 
    private String counselNm;        
    private String counselCtn;       
    private String counselMemo;      
    private String counselRegDt;
	
    public String getCounselSeq() {
		return counselSeq;
	}
	public void setCounselSeq(String counselSeq) {
		this.counselSeq = counselSeq;
	}
	public String getWireProdDtlSeq() {
		return wireProdDtlSeq;
	}
	public void setWireProdDtlSeq(String wireProdDtlSeq) {
		this.wireProdDtlSeq = wireProdDtlSeq;
	}
	public String getCounselNm() {
		return counselNm;
	}
	public void setCounselNm(String counselNm) {
		this.counselNm = counselNm;
	}
	public String getCounselCtn() {
		return counselCtn;
	}
	public void setCounselCtn(String counselCtn) {
		this.counselCtn = counselCtn;
	}
	public String getCounselMemo() {
		return counselMemo;
	}
	public void setCounselMemo(String counselMemo) {
		this.counselMemo = counselMemo;
	}
	public String getCounselRegDt() {
		return counselRegDt;
	}
	public void setCounselRegDt(String counselRegDt) {
		this.counselRegDt = counselRegDt;
	}
     
}
