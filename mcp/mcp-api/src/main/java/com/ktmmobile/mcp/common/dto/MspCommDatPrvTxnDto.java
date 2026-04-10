package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Class Name : MspRateMstDto
 * @Description : MSP 판매 요금제 정보
 */
public class MspCommDatPrvTxnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 신청일련번호 */
    private String apySeq;

    /** 신청자ID */
    private String apyId;
    /** 신청일시 */
    private String apyDt;
    /** 신청자이름 */
    private String apyNm;
    /** 생년월일 (YYYYMMDD) */
    private String bthday;
    /** 성별 (1:남, 2:여) */
    private String gender;
    /** 가입상품 (01:모바일) */
    private String sbscPrdtCd;
    /** 연락처번호 */
    private String cntcTelNo;
    /** 대상서비스번호 */
    private String tgtSvcNo;
    /** 수령이메일 */
    private String recpEmail;
    /** 요청기간 */
    private String confSbst01Yn;
    /** 제공일자 */
    private String confSbst02Yn;
    /** 요청사유 */
    private String confSbst03Yn;
    /** 제공내역 */
    private String confSbst04Yn;
    /** 본인인증CI */
    private String myslfAthnCi;

    private String rnum;
    private String contractNum;		//계약번호
    private String resultYn;		//처리결과
    private String isInvstProc;		//제공여부
    private String isInvstProcNm;
    
    //통신자료제공현황
    private String procDt;			//제공일자
    private String invstNm;			//요청기관
    private String reqRsn;			//요청사유
    private String cstmrNm;			//고객명
    private String subscriberNo;	//고객휴대폰번호
    private String userSsn;			//주민번호
    private String openDt;			//개통일자
    private String subStatus;		//상태
    private String tmntDt;			//해지일자
    private String cstmrAddr;		//고객주소

    private String reqDttm;			//요청일자

    public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getResultYn() {
		return resultYn;
	}
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	public String getIsInvstProc() {
		return isInvstProc;
	}
	public void setIsInvstProc(String isInvstProc) {
		this.isInvstProc = isInvstProc;
	}
	public String getIsInvstProcNm() {
		return isInvstProcNm;
	}
	public void setIsInvstProcNm(String isInvstProcNm) {
		this.isInvstProcNm = isInvstProcNm;
	}
	public String getCstmrNm() {
		return cstmrNm;
	}
	public void setCstmrNm(String cstmrNm) {
		this.cstmrNm = cstmrNm;
	}
	public String getSubscriberNo() {
		return subscriberNo;
	}
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}
	public String getUserSsn() {
		return userSsn;
	}
	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getTmntDt() {
		return tmntDt;
	}
	public void setTmntDt(String tmntDt) {
		this.tmntDt = tmntDt;
	}
	public String getCstmrAddr() {
		return cstmrAddr;
	}
	public void setCstmrAddr(String cstmrAddr) {
		this.cstmrAddr = cstmrAddr;
	}
	public String getApyId() {
        return apyId;
    }
    public void setApyId(String apyId) {
        this.apyId = apyId;
    }
    public String getApyDt() {
        return apyDt;
    }
    public void setApyDt(String apyDt) {
        this.apyDt = apyDt;
    }
    public String getApySeq() {
        return apySeq;
    }
    public void setApySeq(String apySeq) {
        this.apySeq = apySeq;
    }
    public String getApyNm() {
        return apyNm;
    }
    public void setApyNm(String apyNm) {
        this.apyNm = apyNm;
    }
    public String getBthday() {
        return bthday;
    }
    public void setBthday(String bthday) {
        this.bthday = bthday;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getSbscPrdtCd() {
        return sbscPrdtCd;
    }
    public void setSbscPrdtCd(String sbscPrdtCd) {
        this.sbscPrdtCd = sbscPrdtCd;
    }
    public String getCntcTelNo() {
        return cntcTelNo;
    }
    public void setCntcTelNo(String cntcTelNo) {
        this.cntcTelNo = cntcTelNo;
    }
    public String getTgtSvcNo() {
        return tgtSvcNo;
    }
    public void setTgtSvcNo(String tgtSvcNo) {
        this.tgtSvcNo = tgtSvcNo;
    }
    public String getRecpEmail() {
        return recpEmail;
    }
    public void setRecpEmail(String recpEmail) {
        this.recpEmail = recpEmail;
    }
    public String getConfSbst01Yn() {
        return confSbst01Yn;
    }
    public void setConfSbst01Yn(String confSbst01Yn) {
        this.confSbst01Yn = confSbst01Yn;
    }
    public String getConfSbst02Yn() {
        return confSbst02Yn;
    }
    public void setConfSbst02Yn(String confSbst02Yn) {
        this.confSbst02Yn = confSbst02Yn;
    }
    public String getConfSbst03Yn() {
        return confSbst03Yn;
    }
    public void setConfSbst03Yn(String confSbst03Yn) {
        this.confSbst03Yn = confSbst03Yn;
    }
    public String getConfSbst04Yn() {
        return confSbst04Yn;
    }
    public void setConfSbst04Yn(String confSbst04Yn) {
        this.confSbst04Yn = confSbst04Yn;
    }
    public String getMyslfAthnCi() {
        return myslfAthnCi;
    }
    public void setMyslfAthnCi(String myslfAthnCi) {
        this.myslfAthnCi = myslfAthnCi;
    }
	public String getProcDt() {
		return procDt;
	}
	public void setProcDt(String procDt) {
		this.procDt = procDt;
	}
	public String getInvstNm() {
		return invstNm;
	}
	public void setInvstNm(String invstNm) {
		this.invstNm = invstNm;
	}
	public String getReqRsn() {
		return reqRsn;
	}
	public void setReqRsn(String reqRsn) {
		this.reqRsn = reqRsn;
	}

    public String getReqDttm() {
        return reqDttm;
    }

    public void setReqDttm(String reqDttm) {
        this.reqDttm = reqDttm;
    }

    @Override
	public String toString() {
		return "MspCommDatPrvTxnDto [apySeq=" + apySeq + ", apyId=" + apyId + ", apyDt=" + apyDt + ", apyNm=" + apyNm
				+ ", bthday=" + bthday + ", gender=" + gender + ", sbscPrdtCd=" + sbscPrdtCd + ", cntcTelNo="
				+ cntcTelNo + ", tgtSvcNo=" + tgtSvcNo + ", recpEmail=" + recpEmail + ", confSbst01Yn=" + confSbst01Yn
				+ ", confSbst02Yn=" + confSbst02Yn + ", confSbst03Yn=" + confSbst03Yn + ", confSbst04Yn=" + confSbst04Yn
				+ ", myslfAthnCi=" + myslfAthnCi + ", rnum=" + rnum + ", contractNum=" + contractNum + ", resultYn="
				+ resultYn + ", isInvstProc=" + isInvstProc + ", isInvstProcNm=" + isInvstProcNm + ", procDt=" + procDt
				+ ", invstNm=" + invstNm + ", reqRsn=" + reqRsn + ", cstmrNm=" + cstmrNm + ", subscriberNo="
				+ subscriberNo + ", userSsn=" + userSsn + ", openDt=" + openDt + ", subStatus=" + subStatus
				+ ", tmntDt=" + tmntDt + ", cstmrAddr=" + cstmrAddr + ", reqDttm=" + reqDttm + "]";
	}
    
}
