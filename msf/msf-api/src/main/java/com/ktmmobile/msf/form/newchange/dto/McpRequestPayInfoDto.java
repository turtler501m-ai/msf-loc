package com.ktmmobile.msf.form.newchange.dto;

import java.io.Serializable;
import java.util.Date;

public class McpRequestPayInfoDto implements Serializable {

    private static final long serialVersionUID = 4165408994909666445L ;
    /** 가입신청_키 */
    private long requestKey;
    /** 거래승인 번호 */
    private String payTrx;
    /** 결제상태 00 결제진행중(초기값), 01 정상(결제완료) , 02 결제취소(요청), 03 결제취소 완료 , 04 처리중(???)  */
    private String payRstCd;
    /** 취소 결과코드(01:성공,02:오류/공통:PC02) */
    private String cnclRstCd;
    /** 취소 실패코드 */
    private String cnclFailCd;
    /** 취소 실패사유 */
    private String cnclFailRsn;
    /** 취소 처리자ID */
    private String cnclId;
    /** 취소 처리일시 */
    private Date cnclDt;
    /** 등록아이피 */
    private String cretIp;
    /** 등록일자 */
    private Date cretDt;
    private String cretDd;
    /** 등록 사용자 아이디 */
    private String cretId;
    /** 수정자 아이피 */
    private String amdIp;
    /** 수정일자 */
    private Date amdDt;
    /** 수정자 아이디  */
    private String amdId;

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getPayTrx() {
        return payTrx;
    }

    public void setPayTrx(String payTrx) {
        this.payTrx = payTrx;
    }

    public String getPayRstCd() {
        return payRstCd;
    }

    public void setPayRstCd(String payRstCd) {
        this.payRstCd = payRstCd;
    }

    public String getCnclRstCd() {
        return cnclRstCd;
    }

    public void setCnclRstCd(String cnclRstCd) {
        this.cnclRstCd = cnclRstCd;
    }

    public String getCnclFailCd() {
        return cnclFailCd;
    }

    public void setCnclFailCd(String cnclFailCd) {
        this.cnclFailCd = cnclFailCd;
    }

    public String getCnclFailRsn() {
        return cnclFailRsn;
    }

    public void setCnclFailRsn(String cnclFailRsn) {
        this.cnclFailRsn = cnclFailRsn;
    }

    public String getCnclId() {
        return cnclId;
    }

    public void setCnclId(String cnclId) {
        this.cnclId = cnclId;
    }

    public Date getCnclDt() {
        return cnclDt;
    }

    public void setCnclDt(Date cnclDt) {
        this.cnclDt = cnclDt;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretDd() {
        return cretDd;
    }

    public void setCretDd(String cretDd) {
        this.cretDd = cretDd;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdIp() {
        return amdIp;
    }

    public void setAmdIp(String amdIp) {
        this.amdIp = amdIp;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }
}
