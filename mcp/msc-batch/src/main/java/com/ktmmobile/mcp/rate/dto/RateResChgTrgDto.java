package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;


/**
 * @Class Name : NmcpRateResChgTrgDto
 * @Description : NMCP_RATE_RES_CHG_TRG_DTO 요금제 예약 변경 전문 대상 테이블에 사용되는 DTO.
 *
 * @author : 개발팀 김동혁
 * @Create Date : 2024. 02. 06.
 */
public class RateResChgTrgDto implements Serializable  {

    private static final long serialVersionUID = 1L;
    
    /* 예약 변경 전문 대상 시퀀스 */
    private String resChgTrgSeq;
        
    /* 예약 변경 신청 대상 시퀀스 */
    private String resChgBasSeq;
    
    /* 예약변경일자 */
    private String resChgApyDate;
    
    /* 처리여부 */
    private String procYn;
    
    /* 이벤트 처리 번호 */
    private String evntTrtmNo;
    
    /* 이벤트 처리 일시 */
    private String evntTrtmDt;
    
    /* 등록자ID */
    private String regstId;
    
    /* 등록일시 */
    private String regstDttm;

    /* 배치모드(0, 1, 2, 3 사용) - 4개의 task로 나누어서 운영 */
    private int batchMod;

	public String getResChgTrgSeq() {
		return resChgTrgSeq;
	}

	public void setResChgTrgSeq(String resChgTrgSeq) {
		this.resChgTrgSeq = resChgTrgSeq;
	}
	
	public String getResChgBasSeq() {
		return resChgBasSeq;
	}

	public void setResChgBasSeq(String resChgBasSeq) {
		this.resChgBasSeq = resChgBasSeq;
	}

	public String getResChgApyDate() {
		return resChgApyDate;
	}

	public void setResChgApyDate(String resChgApyDate) {
		this.resChgApyDate = resChgApyDate;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getEvntTrtmNo() {
		return evntTrtmNo;
	}

	public void setEvntTrtmNo(String evntTrtmNo) {
		this.evntTrtmNo = evntTrtmNo;
	}

	public String getEvntTrtmDt() {
		return evntTrtmDt;
	}

	public void setEvntTrtmDt(String evntTrtmDt) {
		this.evntTrtmDt = evntTrtmDt;
	}

	public String getRegstId() {
		return regstId;
	}

	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	public String getRegstDttm() {
		return regstDttm;
	}

	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	public int getBatchMod() {
		return batchMod;
	}

	public void setBatchMod(int batchMod) {
		this.batchMod = batchMod;
	}
	
}
