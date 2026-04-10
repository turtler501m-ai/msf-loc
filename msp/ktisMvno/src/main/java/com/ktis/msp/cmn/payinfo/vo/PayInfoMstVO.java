package com.ktis.msp.cmn.payinfo.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PayInfoMstVO
 * @Description : PAYINFO 관리 마스터
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.07.12  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 7. 12.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="payInfoMstVO")
public class PayInfoMstVO extends BaseVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4695098813219390339L;
	
	private String kftcMstSeqStr; //파일순번
	private String rgstDt; //등록일자
	private String rgstDtStr; //등록일자
	private String stateNm; //상태명
	private String approvalYn; //승인여부
    private String searchStartDt; //조회 시작일
    private String searchEndDt; //조회 종료일
    private String approvalNm; //승인상태명
    private int kftcMstSeq; /** PAYINFO MST SEQ */
    private String startDt; /** 작업 시작날짜 */
    private String endDt; /** 작업 종료날짜 */
    private String kftcFileNm; /** 전문 파일명 */
    private String stateCd; /** 상태코드[CMN0204] */
    private String regstId; /** 등록자ID */
    private String regstDttm; /** 등록일시 */
    private String rvisnId; /** 수정자ID */
    private String rvisnDttm; /** 수정일시 */
    private String approvalId; /** 승인자ID */
    private String approvalIdNm; /** 승인자명 */
    private String approvalDt; /** 승인일자 */
    private String approvalMsg; /** 승인사유 */
    private String stateDesc; /** 상태메세지 */
    private int receiveCnt; /** 수신전문 갯수 */
    private int sendCnt; /** 송신전문 갯수 */
    private String approvalCd; /** [CMN0209]승인코드 */
    
    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * @return the rgstDt
	 */
	public String getRgstDt() {
		return rgstDt;
	}

	/**
	 * @param rgstDt the rgstDt to set
	 */
	public void setRgstDt(String rgstDt) {
		this.rgstDt = rgstDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the kftcFileNm
	 */
	public String getKftcFileNm() {
		return kftcFileNm;
	}

	/**
	 * @param kftcFileNm the kftcFileNm to set
	 */
	public void setKftcFileNm(String kftcFileNm) {
		this.kftcFileNm = kftcFileNm;
	}

	/**
	 * @return the stateCd
	 */
	public String getStateCd() {
		return stateCd;
	}

	/**
	 * @param stateCd the stateCd to set
	 */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}

	/**
	 * @return the stateNm
	 */
	public String getStateNm() {
		return stateNm;
	}

	/**
	 * @param stateNm the stateNm to set
	 */
	public void setStateNm(String stateNm) {
		this.stateNm = stateNm;
	}

	/**
	 * @return the approvalYn
	 */
	public String getApprovalYn() {
		return approvalYn;
	}

	/**
	 * @param approvalYn the approvalYn to set
	 */
	public void setApprovalYn(String approvalYn) {
		this.approvalYn = approvalYn;
	}

	/**
	 * @return the approvalId
	 */
	public String getApprovalId() {
		return approvalId;
	}

	/**
	 * @param approvalId the approvalId to set
	 */
	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	/**
	 * @return the approvalDt
	 */
	public String getApprovalDt() {
		return approvalDt;
	}

	/**
	 * @param approvalDt the approvalDt to set
	 */
	public void setApprovalDt(String approvalDt) {
		this.approvalDt = approvalDt;
	}

	/**
	 * @return the approvalMsg
	 */
	public String getApprovalMsg() {
		return approvalMsg;
	}

	/**
	 * @param approvalMsg the approvalMsg to set
	 */
	public void setApprovalMsg(String approvalMsg) {
		this.approvalMsg = approvalMsg;
	}

	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}

	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}

	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	/**
	 * @return the stateDesc
	 */
	public String getStateDesc() {
		return stateDesc;
	}

	/**
	 * @param stateDesc the stateDesc to set
	 */
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	/**
	 * @return the kftcMstSeqStr
	 */
	public String getKftcMstSeqStr() {
		return kftcMstSeqStr;
	}

	/**
	 * @param kftcMstSeqStr the kftcMstSeqStr to set
	 */
	public void setKftcMstSeqStr(String kftcMstSeqStr) {
		this.kftcMstSeqStr = kftcMstSeqStr;
	}

	/**
	 * @return the rgstDtStr
	 */
	public String getRgstDtStr() {
		return rgstDtStr;
	}

	/**
	 * @param rgstDtStr the rgstDtStr to set
	 */
	public void setRgstDtStr(String rgstDtStr) {
		this.rgstDtStr = rgstDtStr;
	}

	/**
	 * @return the approvalCd
	 */
	public String getApprovalCd() {
		return approvalCd;
	}

	/**
	 * @param approvalCd the approvalCd to set
	 */
	public void setApprovalCd(String approvalCd) {
		this.approvalCd = approvalCd;
	}

	/**
	 * @return the approvalNm
	 */
	public String getApprovalNm() {
		return approvalNm;
	}

	/**
	 * @param approvalNm the approvalNm to set
	 */
	public void setApprovalNm(String approvalNm) {
		this.approvalNm = approvalNm;
	}

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the regstId
	 */
	public String getRegstId() {
		return regstId;
	}

	/**
	 * @param regstId the regstId to set
	 */
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	/**
	 * @return the regstDttm
	 */
	public String getRegstDttm() {
		return regstDttm;
	}

	/**
	 * @param regstDttm the regstDttm to set
	 */
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}

	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}

	/**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}

	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	/**
	 * @param kftcMstSeq the kftcMstSeq to set
	 */
	public void setKftcMstSeq(int kftcMstSeq) {
		this.kftcMstSeq = kftcMstSeq;
	}

	/**
	 * @param receiveCnt the receiveCnt to set
	 */
	public void setReceiveCnt(int receiveCnt) {
		this.receiveCnt = receiveCnt;
	}

	/**
	 * @param sendCnt the sendCnt to set
	 */
	public void setSendCnt(int sendCnt) {
		this.sendCnt = sendCnt;
	}

	/**
	 * @return the kftcMstSeq
	 */
	public int getKftcMstSeq() {
		return kftcMstSeq;
	}

	/**
	 * @return the receiveCnt
	 */
	public int getReceiveCnt() {
		return receiveCnt;
	}

	/**
	 * @return the sendCnt
	 */
	public int getSendCnt() {
		return sendCnt;
	}

	/**
	 * @return the approvalIdNm
	 */
	public String getApprovalIdNm() {
		return approvalIdNm;
	}

	/**
	 * @param approvalIdNm the approvalIdNm to set
	 */
	public void setApprovalIdNm(String approvalIdNm) {
		this.approvalIdNm = approvalIdNm;
	}
    
}
