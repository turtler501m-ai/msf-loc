package com.ktis.msp.batch.job.lgs.lgsbatchmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

/**
 * @Class Name  : CmnBtchOprtMgmtVo.java
 * @Description : CmnBtchOprtMgmtVo Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.24     ryu      최초생성
 *
 * @author ryu
 * @since 2014. 08.24
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

public class CmnBtchOprtMgmtVo extends BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2159880908427660238L;
/*
 * 
 CMN0026	02	재작업(전체)	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06						
CMN0026	03	재작업(일부)	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06						
CMN0026	01	정기작업	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06						
CMN0024	02	월	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06	3					
CMN0024	09	수시	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06						
CMN0024	01	일	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06	2					
CMN0023	PPS	선불	Y	AIT0003	2014-10-07 오후 6:03:03	AIT0003	2014-10-07 오후 6:03:03	1					
CMN0023	ORG	기준	Y		2014-08-28 오후 4:57:06		2014-08-28 오후 4:57:06	1					
CMN0023	LGS	물류	Y		2014-08-28 오후 4:56:12		2014-08-28 오후 4:56:12						
CMN0023	CMS	수수료	Y		2014-08-28 오후 4:56:06		2014-08-28 오후 4:56:06						
CMN0023	CMN	공통	Y		2014-08-28 오후 4:55:58		2014-08-28 오후 4:55:58						
CMN0023	CLS	결산	Y		2014-08-28 오후 4:55:04		2014-08-28 오후 4:55:04						         
 */

	
/*  0. 배치작업관리 */	
	    private String btchPrgmId; 		/** 배치프로그램ID */
	    private String oprtDm;     		/** 작업일월 */
	    private String oprtType;   		/** 작업종류 */
	    private String oprtStat;   		/** 작업상태 */
	    private String oprtEndDt;  		/** 작업종료일시 */
	    private String oprtStartDt;		/** 작업시작일시 */
	    private String userId;			/** 사용자ID */

/* 1. 배치로그관리 */	
	    private String oprtSctn;		    /** 작업구분 */
	    private String remrk;				/** 비고 */
	    private String regstId;				/** 사용자ID */
	    private int oprtCnt;				/** 비고 */
	    private String errCd;				/** 사용자ID */
	    private String errDsc;				/** 사용자ID */
		public String getBtchPrgmId() {
			return btchPrgmId;
		}
		public void setBtchPrgmId(String btchPrgmId) {
			this.btchPrgmId = btchPrgmId;
		}
		public String getOprtDm() {
			return oprtDm;
		}
		public void setOprtDm(String oprtDm) {
			this.oprtDm = oprtDm;
		}
		public String getOprtType() {
			return oprtType;
		}
		public void setOprtType(String oprtType) {
			this.oprtType = oprtType;
		}
		public String getOprtStat() {
			return oprtStat;
		}
		public void setOprtStat(String oprtStat) {
			this.oprtStat = oprtStat;
		}
		public String getOprtEndDt() {
			return oprtEndDt;
		}
		public void setOprtEndDt(String oprtEndDt) {
			this.oprtEndDt = oprtEndDt;
		}
		public String getOprtStartDt() {
			return oprtStartDt;
		}
		public void setOprtStartDt(String oprtStartDt) {
			this.oprtStartDt = oprtStartDt;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getOprtSctn() {
			return oprtSctn;
		}
		public void setOprtSctn(String oprtSctn) {
			this.oprtSctn = oprtSctn;
		}
		public String getRemrk() {
			return remrk;
		}
		public void setRemrk(String remrk) {
			this.remrk = remrk;
		}
		public String getRegstId() {
			return regstId;
		}
		public void setRegstId(String regstId) {
			this.regstId = regstId;
		}
		public int getOprtCnt() {
			return oprtCnt;
		}
		public void setOprtCnt(int oprtCnt) {
			this.oprtCnt = oprtCnt;
		}
		public String getErrCd() {
			return errCd;
		}
		public void setErrCd(String errCd) {
			this.errCd = errCd;
		}
		public String getErrDsc() {
			return errDsc;
		}
		public void setErrDsc(String errDsc) {
			this.errDsc = errDsc;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	    
}
