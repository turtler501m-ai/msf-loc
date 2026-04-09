package com.ktmmobile.msf.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : MspPpsRcgTesDto.java
 * 날짜     : 2016. 8. 03
 * 작성자   : papier
 * 설명     : 충전 내역조회 테이블(MSP_MNG.PPS_RCG_TES )
 * </pre>
 */
public class MspPpsRcgTesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** PK PPS_RCG_TES_SEQ사용 */
    private long tesSeq;
    /** pps_rcg_type 테이블참조 */
    private String reqType;
    /** 계약번호 */
    private String contractNum;
    /** 충전대리점 */
    private String rechargeAgent;
    /** 충전금액 */
    private long recharge;

    /** 충전결과코드 */
    private String retCode;
    /** 충전결과메세지 */
    private String retMsg;
    /** KT_IN_RES_LOG SEQ */
    private long rcgSeq;
    /** 충전된금액 */
    private int oAmount;

    /** 최대충전가능금액 */
    private String oTesChargeMax;
    /** 기본알 */
    private String oTesBaser;
    /** 충전알 */
    private String oTesChgr;
    /** 데이터알 */
    private String oTesMagicr;
    /** 문자알 */
    private String oTesFsmsr;
    /** 영상알 */
    private String oTesVideor;
    /** 정보료전용알 */
    private String oTesIpvasr;
    /** 알캡상한알 */
    private String oTesIpmaxr;
    /** 문자건수 */
    private String oTesSmsm;
    /** 데이타계정(단위:Byte) */
    private String oTesDataplusv;
    /** 요청IP */
    private String rechargeIp;
    /** 요청일자 */
    private Date reqDate;
    /** 충전요청한 로그인아이디 */
    private String adminId;
    public long getTesSeq() {
        return tesSeq;
    }
    public void setTesSeq(long tesSeq) {
        this.tesSeq = tesSeq;
    }
    public String getReqType() {
        return reqType;
    }
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getRechargeAgent() {
        return rechargeAgent;
    }
    public void setRechargeAgent(String rechargeAgent) {
        this.rechargeAgent = rechargeAgent;
    }
    public long getRecharge() {
        return recharge;
    }

    /*
     * 결과가 정상이면 충전금액 리턴
     * 실패면.. 0 리턴
     */
    public long getRechargeResult() {
        if ("0000".equals(retCode)) {
            return recharge;
        } else {
            return 0;
        }
    }

    public void setRecharge(long recharge) {
        this.recharge = recharge;
    }
    public String getRetCode() {
        return retCode;
    }
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
    public String getRetMsg() {

        if ("0000".equals(retCode) && ( retMsg == null || "".equals(retMsg)) ) {
            return "성공";
        } else {
            return retMsg;
        }
    }
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
    public long getRcgSeq() {
        return rcgSeq;
    }
    public void setRcgSeq(long rcgSeq) {
        this.rcgSeq = rcgSeq;
    }
    public int getoAmount() {
        return oAmount;
    }
    public void setoAmount(int oAmount) {
        this.oAmount = oAmount;
    }
    public String getoTesChargeMax() {
        if (oTesChargeMax == null || oTesChargeMax.equals("")) {
            return "0";
        }
        return oTesChargeMax;
    }
    public void setoTesChargeMax(String oTesChargeMax) {
        this.oTesChargeMax = oTesChargeMax;
    }
    public String getoTesBaser() {
        if (oTesBaser == null || oTesBaser.equals("")) {
            return "0";
        }
        return oTesBaser;

    }
    public void setoTesBaser(String oTesBaser) {
        this.oTesBaser = oTesBaser;
    }
    public String getoTesChgr() {
        if (oTesChgr == null || oTesChgr.equals("")) {
            return "0";
        }
        return oTesChgr;
    }
    public void setoTesChgr(String oTesChgr) {
        this.oTesChgr = oTesChgr;
    }
    public String getoTesMagicr() {
        if (oTesMagicr == null || oTesMagicr.equals("")) {
            return "0";
        }
        return oTesMagicr;
    }
    public void setoTesMagicr(String oTesMagicr) {

        this.oTesMagicr = oTesMagicr;
    }
    public String getoTesFsmsr() {
        if (oTesFsmsr == null || oTesFsmsr.equals("")) {
            return "0";
        }
        return oTesFsmsr;
    }
    public void setoTesFsmsr(String oTesFsmsr) {
        this.oTesFsmsr = oTesFsmsr;
    }
    public String getoTesVideor() {
        return oTesVideor;
    }
    public void setoTesVideor(String oTesVideor) {
        this.oTesVideor = oTesVideor;
    }
    public String getoTesIpvasr() {
        return oTesIpvasr;
    }
    public void setoTesIpvasr(String oTesIpvasr) {
        this.oTesIpvasr = oTesIpvasr;
    }
    public String getoTesIpmaxr() {
        return oTesIpmaxr;
    }
    public void setoTesIpmaxr(String oTesIpmaxr) {
        this.oTesIpmaxr = oTesIpmaxr;
    }
    public String getoTesSmsm() {
        return oTesSmsm;
    }
    public void setoTesSmsm(String oTesSmsm) {
        this.oTesSmsm = oTesSmsm;
    }
    public String getoTesDataplusv() {
        return oTesDataplusv;
    }
    public void setoTesDataplusv(String oTesDataplusv) {
        this.oTesDataplusv = oTesDataplusv;
    }
    public String getRechargeIp() {
        return rechargeIp;
    }
    public void setRechargeIp(String rechargeIp) {
        this.rechargeIp = rechargeIp;
    }
    public Date getReqDate() {
        return reqDate;
    }
    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }





}
