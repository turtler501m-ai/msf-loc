package com.ktmmobile.mcp.push.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PushResultDto implements Serializable{
	
	private static final long serialVersionUID = 4212601479589882460L;

	 /** 배치구분*/
    private String batchDiv;

    /** 배치시작일시 */
    private Timestamp batchStDtime;

    /** 배치명 */
    private String batchNm;

    /** 배치종료일시 */
    private Timestamp batchEdDtime;

    /** 배치처리시간-밀리세컨드까지 표시한다. */
    private double batchProcMs;

    /** 처리상태[성공, 실패] */
    private String procSt;

    /** 오류메세지 */
    private String errMsg;

    

	public String getBatchDiv() {
		return batchDiv;
	}

	public void setBatchDiv(String batchDiv) {
		this.batchDiv = batchDiv;
	}

	public Timestamp getBatchStDtime() {
		return batchStDtime;
	}

	public void setBatchStDtime(Timestamp batchStDtime) {
		this.batchStDtime = batchStDtime;
	}

	public String getBatchNm() {
		return batchNm;
	}

	public void setBatchNm(String batchNm) {
		this.batchNm = batchNm;
	}

	public Timestamp getBatchEdDtime() {
		return batchEdDtime;
	}

	public void setBatchEdDtime(Timestamp batchEdDtime) {
		this.batchEdDtime = batchEdDtime;
	}

	public double getBatchProcMs() {
		return batchProcMs;
	}

	public void setBatchProcMs(double batchProcMs) {
		this.batchProcMs = batchProcMs;
	}

	public String getProcSt() {
		return procSt;
	}

	public void setProcSt(String procSt) {
		this.procSt = procSt;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
        this.errMsg = (errMsg.length() > 2000) ? errMsg.substring(0, 2000) : errMsg;
    }
    
}
