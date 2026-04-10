package com.ktmmobile.mcp.mcash.dto;

import java.io.Serializable;

public class McashDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;          // M포탈 아이디
    private String status;          // 회원상태 A(정상), S(탈퇴대기), C(탈퇴), T(변경대기)
    private String customerId;      // customerId
    private String contractNum;     // 계약번호
    private String svcCntrNo;       // 서비스계약번호
    private String evntType;        // 연동유형 [JOIN(신규), REPAIR(복구), CHANGE(변경), CANCEL(해지), CASH(캐시조회)]
    private String evntTypeDtl;     // 연동유형상세 [JOIN - NEW(최초), RE(재가입), REPAIR - RCL(해지복구), MCC(명변취소), RMC(M캐시탈회취소), CHANGE - CNTR(회선변경), CANCEL - PORTAL(포탈탈퇴), CAN(회선해지), CMC(M캐시탈회), MCN(명의변경)]
    private String evntCd;          // 업무유형 CAN, MCN, RCL, MCC
    private String evntDt;          // 업무일자
    private String mcashSeq;        // 연동 시퀀스

    /*해지(CAN)관련 */
    private String canTrgSeq;       // 연동 대상 시퀀스
    private String evntTrimNo;      // 이벤트처리번호
    private String evntTrimDt;      // 이벤트처리일시
    private String procYn;          // 연동처리여부 Y(처리완료), N(미처리), T(처리중)
    private String procDttm;        // 연동처리일시
    private String rsltCd;          // 연동결과코드
    private String rsltMsg;         // 연동결과메세지

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSvcCntrNo() {
        return svcCntrNo;
    }

    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
    }

    public String getEvntType() {
        return evntType;
    }

    public void setEvntType(String evntType) {
        this.evntType = evntType;
    }

    public String getEvntTypeDtl() {
        return evntTypeDtl;
    }

    public void setEvntTypeDtl(String evntTypeDtl) {
        this.evntTypeDtl = evntTypeDtl;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getEvntDt() {
        return evntDt;
    }

    public void setEvntDt(String evntDt) {
        this.evntDt = evntDt;
    }

    public String getEvntTrimNo() {
        return evntTrimNo;
    }

    public void setEvntTrimNo(String evntTrimNo) {
        this.evntTrimNo = evntTrimNo;
    }

    public String getEvntTrimDt() {
        return evntTrimDt;
    }

    public void setEvntTrimDt(String evntTrimDt) {
        this.evntTrimDt = evntTrimDt;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getProcDttm() {
        return procDttm;
    }

    public void setProcDttm(String procDttm) {
        this.procDttm = procDttm;
    }

    public String getRsltCd() {
        return rsltCd;
    }

    public void setRsltCd(String rsltCd) {
        this.rsltCd = rsltCd;
    }

    public String getRsltMsg() {
        return rsltMsg;
    }

    public void setRsltMsg(String rsltMsg) {
        this.rsltMsg = rsltMsg;
    }
    public String getMcashSeq() {
        return mcashSeq;
    }

    public void setMcashSeq(String mcashSeq) {
        this.mcashSeq = mcashSeq;
    }


    public String getCanTrgSeq() {
        return canTrgSeq;
    }

    public void setCanTrgSeq(String canTrgSeq) {
        this.canTrgSeq = canTrgSeq;
    }

}
