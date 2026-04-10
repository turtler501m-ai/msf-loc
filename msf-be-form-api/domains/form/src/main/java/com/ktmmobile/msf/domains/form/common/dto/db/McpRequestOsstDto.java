package com.ktmmobile.msf.domains.form.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestOsstDto.java
 * 날짜     : 2018. 3. 28.
 * 작성자   : papier
 * 설명     : MCP_REQUEST_OSST DTO
 * </pre>
 */
public class McpRequestOsstDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** MVNO오더번호(RES_NO) */
    private String mvnoOrdNo;
    /** 일련번호 */
    private int seq;
    /** OSST오더번호 */
    private String osstOrdNo;
    /** 진행상태코드 */
    private String prgrStatCd;
    /** 고객ID */
    private String custId;
    /** 서비스계약번호 */
    private String svcCntrNo;
    /** 처리결과코드 */
    private String rsltCd;
    /** 처리결과내용 */
    private String rsltMsg;
    /** 처리일시 */
    private String rsltDt;
    /** NSTEP 에러 GLOBAL ID */
    private String nstepGlobalId;
    /** 상품체크안내메세지 */
    private String prdcChkNotiMsg;
    /** 번호이동이전사업자계약유형코드(0: 선불, 1:후불) */
    private String npBcntrTypeCd;
    /** 번호이동수수료 */
    private long npFee;
    /** 타사미청구금액 */
    private long npNchrgAmt;
    /** 번호이동위약금 */
    private long npPnltAmt;
    /** 번호이동미납금액 */
    private long npUnpayAmt;
    /** 번호이동단말할부금 */
    private long npHndstInstAmt;
    /** 번호이동선납금 */
    private long npPrepayAmt;
    /** 번호이동기본료 */
    private long npBaseChrgAmt;
    /** 번호이동국내통화료 */
    private long npNtnlChrgAmt;
    /** 번호이동국제통화료 */
    private long npIntlChrgAmt;
    /** 번호이동부가사용료 */
    private long npAddChrgAmt;
    /** 번호이동기타사용료 */
    private long npEtcChrgAmt;
    /** 번호이동부가세 */
    private long npVat;
    /** 번호이동수납대상시작일자 */
    private String npRmnStrtDt;
    /** 번호이동수납대상종료일자 */
    private String npRmnEndDt;
    /** 전화번호상태코드(NU1) */
    private String tlphNoStatCd;
    /** 할당대리점ID(NU1) */
    private String asgnAgncId;
    /** 번호소유통신사업자코드(NU1) */
    private String tlphNoOwnCmpnCd;
    /** 개통서비스구분코드(NU1) */
    private String openSvcIndCd;
    /** 암호화전화번호(NU1) */
    private String encdTlphNo;
    /** 전화번호 */
    private String tlphNo;
    /** 연동유형 */
    private String ifType;
    /** 등록일시 */
    private Date regstDttm;
    public String getMvnoOrdNo() {
        return mvnoOrdNo;
    }
    public void setMvnoOrdNo(String mvnoOrdNo) {
        this.mvnoOrdNo = mvnoOrdNo;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public String getOsstOrdNo() {
        return osstOrdNo;
    }
    public void setOsstOrdNo(String osstOrdNo) {
        this.osstOrdNo = osstOrdNo;
    }
    public String getPrgrStatCd() {
        return prgrStatCd;
    }
    public void setPrgrStatCd(String prgrStatCd) {
        this.prgrStatCd = prgrStatCd;
    }
    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getSvcCntrNo() {
        return svcCntrNo;
    }
    public void setSvcCntrNo(String svcCntrNo) {
        this.svcCntrNo = svcCntrNo;
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
    public String getRsltDt() {
        return rsltDt;
    }
    public void setRsltDt(String rsltDt) {
        this.rsltDt = rsltDt;
    }
    public String getNstepGlobalId() {
        return nstepGlobalId;
    }
    public void setNstepGlobalId(String nstepGlobalId) {
        this.nstepGlobalId = nstepGlobalId;
    }
    public String getPrdcChkNotiMsg() {
        return prdcChkNotiMsg;
    }
    public void setPrdcChkNotiMsg(String prdcChkNotiMsg) {
        this.prdcChkNotiMsg = prdcChkNotiMsg;
    }
    public String getNpBcntrTypeCd() {
        return npBcntrTypeCd;
    }
    public void setNpBcntrTypeCd(String npBcntrTypeCd) {
        this.npBcntrTypeCd = npBcntrTypeCd;
    }
    public long getNpFee() {
        return npFee;
    }
    public void setNpFee(long npFee) {
        this.npFee = npFee;
    }
    public long getNpNchrgAmt() {
        return npNchrgAmt;
    }
    public void setNpNchrgAmt(long npNchrgAmt) {
        this.npNchrgAmt = npNchrgAmt;
    }
    public long getNpPnltAmt() {
        return npPnltAmt;
    }
    public void setNpPnltAmt(long npPnltAmt) {
        this.npPnltAmt = npPnltAmt;
    }
    public long getNpUnpayAmt() {
        return npUnpayAmt;
    }
    public void setNpUnpayAmt(long npUnpayAmt) {
        this.npUnpayAmt = npUnpayAmt;
    }
    public long getNpHndstInstAmt() {
        return npHndstInstAmt;
    }
    public void setNpHndstInstAmt(long npHndstInstAmt) {
        this.npHndstInstAmt = npHndstInstAmt;
    }
    public long getNpPrepayAmt() {
        return npPrepayAmt;
    }
    public void setNpPrepayAmt(long npPrepayAmt) {
        this.npPrepayAmt = npPrepayAmt;
    }
    public long getNpBaseChrgAmt() {
        return npBaseChrgAmt;
    }
    public void setNpBaseChrgAmt(long npBaseChrgAmt) {
        this.npBaseChrgAmt = npBaseChrgAmt;
    }
    public long getNpNtnlChrgAmt() {
        return npNtnlChrgAmt;
    }
    public void setNpNtnlChrgAmt(long npNtnlChrgAmt) {
        this.npNtnlChrgAmt = npNtnlChrgAmt;
    }
    public long getNpIntlChrgAmt() {
        return npIntlChrgAmt;
    }
    public void setNpIntlChrgAmt(long npIntlChrgAmt) {
        this.npIntlChrgAmt = npIntlChrgAmt;
    }
    public long getNpAddChrgAmt() {
        return npAddChrgAmt;
    }
    public void setNpAddChrgAmt(long npAddChrgAmt) {
        this.npAddChrgAmt = npAddChrgAmt;
    }
    public long getNpEtcChrgAmt() {
        return npEtcChrgAmt;
    }
    public void setNpEtcChrgAmt(long npEtcChrgAmt) {
        this.npEtcChrgAmt = npEtcChrgAmt;
    }
    public long getNpVat() {
        return npVat;
    }
    public void setNpVat(long npVat) {
        this.npVat = npVat;
    }
    public String getNpRmnStrtDt() {
        return npRmnStrtDt;
    }
    public void setNpRmnStrtDt(String npRmnStrtDt) {
        this.npRmnStrtDt = npRmnStrtDt;
    }
    public String getNpRmnEndDt() {
        return npRmnEndDt;
    }
    public void setNpRmnEndDt(String npRmnEndDt) {
        this.npRmnEndDt = npRmnEndDt;
    }
    public String getTlphNoStatCd() {
        return tlphNoStatCd;
    }
    public void setTlphNoStatCd(String tlphNoStatCd) {
        this.tlphNoStatCd = tlphNoStatCd;
    }
    public String getAsgnAgncId() {
        return asgnAgncId;
    }
    public void setAsgnAgncId(String asgnAgncId) {
        this.asgnAgncId = asgnAgncId;
    }
    public String getTlphNoOwnCmpnCd() {
        return tlphNoOwnCmpnCd;
    }
    public void setTlphNoOwnCmpnCd(String tlphNoOwnCmpnCd) {
        this.tlphNoOwnCmpnCd = tlphNoOwnCmpnCd;
    }
    public String getOpenSvcIndCd() {
        return openSvcIndCd;
    }
    public void setOpenSvcIndCd(String openSvcIndCd) {
        this.openSvcIndCd = openSvcIndCd;
    }
    public String getEncdTlphNo() {
        return encdTlphNo;
    }
    public void setEncdTlphNo(String encdTlphNo) {
        this.encdTlphNo = encdTlphNo;
    }
    public String getTlphNo() {
        return tlphNo;
    }
    public void setTlphNo(String tlphNo) {
        this.tlphNo = tlphNo;
    }
    public String getIfType() {
        return ifType;
    }
    public void setIfType(String ifType) {
        this.ifType = ifType;
    }
    public Date getRegstDttm() {
        return regstDttm;
    }
    public void setRegstDttm(Date regstDttm) {
        this.regstDttm = regstDttm;
    }






}
