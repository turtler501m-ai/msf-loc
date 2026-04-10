package com.ktmmobile.mcp.common.dto.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.common.util.EncryptUtil;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestSelfDlvryDto.java
 * 날짜     : 2020. 1. 07.
 * 작성자   : papier
 * 설명     : 셀프개통 배송 요청 정보(MCP_REQUEST_SELF_DLVRY)
 * </pre>
 */
public class McpRequestSelfDlvryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 일련번호 PK */
    private long selfDlvryIdx;
    /** 유심코드 공통코드[usimProdInfo]  */
    private String usimProdId;
    /** 유심코드상세 공통코드[usimProdDetailInfo]  */
    private String usimProdDtlId;
    /** 고객명 */
    private String cstmrName;
    /** 주민등록번호 암호화 */
    private String cstmrNativeRrn;
    /** 인증방식 */
    private String onlineAuthType;
    /** 인증정보 */
    private String onlineAuthInfo;
    /** 중복가입 확인값 (DI_64 byte) */
    private String onlineAuthDi;
    /** 배송정보_이름 */
    private String dlvryName;
    /** 배송정보_전화번호_앞자리 */
    private String dlvryTelFn;
    /** 배송정보_전화번호_중간자리 */
    private String dlvryTelMn;
    /** 배송정보_전화번호_뒷자리 */
    private String dlvryTelRn;
    /** 배송정보_전화번호 */
    private String dlvryTel;
    /** 배송정보_우편번호 */
    private String dlvryPost;
    /** 배송정보_주소 */
    private String dlvryAddr;
    /** 배송정보_상세주소 */
    private String dlvryAddrDtl;
    /** 고객 요청 사항 */
    private String dlvryMemo;
    /** 유심배송 메모 */
    private String selfMemo;
    /** 유심번호 */
    private String reqUsimSn;
    /** 진행상태 공통코드[dStateCode] 01 접수대기, 02 배송중 , 03 배송완료 , 04 개통완료 */
    private String dlvryStateCode;
    /** 신청상태 공통코드[sStateCode] 01 정상  , 02 관리자삭제 , 03 고객취소  */
    private String selfStateCode;
    /** 송장번호 */
    private String dlvryNo;
    /** 택배사 코드    공통코드[ PERCEL] */
    private String tbCd;
    /** 계약번호 */
    private String contractNum;

    /** 생성자아이디(로그인아이디) */
    private String cretId;
    /** 수정자ID */
    private String rvisnId;
    /** 수정 일시 */
    private Date rvisnDttm;
    /** 등록일시 */
    private Date sysRdate;
    /** 등록일시 시작 */
    private String sysRdateS;

    /** 등록일시 끝*/
    private String sysRdateE;

    /** 배송방법 01 : 배로배송 / 02: 택배 */
    private String dlvryKind;

    private String ktOrdId;

    private String bizOrgCd;	// 배달 업체 코드
    private String deliveryOrderId; // 배달업체 오더 ID

    private String reservedString;
    private String usimAmt;
    private String orderRcvTlphNo;
    private String svcRsltCd; // 배달주문접수 결과코드
    private String svcRsltMsg; // 배달주문접수 결과메세지
    private String payCd; // 결제성공여부 01:성공, 02:실패
    private String acceptTime; // D01 서비스나온값
    private String viewYn; // 완료페이지에서 노출

    // 지번및좌표수정추가
    private String entY;
    private String entX;
    private String dlvryJibunAddr;
    private String dlvryJibunAddrDtl;
    private String homePw;
    private String exitTitle;

    /** 요청유형 [01 일반(유심구매) 02 신청서요청] */
    private String reqType = "01";
    /** 신청서 키값(일련번호) */
    private long requestKey= 0;

    private String prodNm;

    private String reqBuyQnty;
    private String usimUcost;
    private String usimPrice;

    private String percelUrl;

    /** 배송유형(01:택배, 02:바로배송, 03:당일배송) */
    private String dlvryType;

    private String minorAgentName;         // 미성년자_법정대리인_성명
    private String minorOnlineAuthInfo;    // 미성년자_법정대리인_인증정보
    private String minorAgentAgrmYn;       // 미성년자_법정대리인_동의여부

    public String getPercelUrl() {
        return percelUrl;
    }
    public void setPercelUrl(String percelUrl) {
        this.percelUrl = percelUrl;
    }

    public String getEntY() {
        return entY;
    }
    public void setEntY(String entY) {
        this.entY = entY;
    }
    public String getEntX() {
        return entX;
    }
    public void setEntX(String entX) {
        this.entX = entX;
    }
    public String getDlvryJibunAddr() {
        return dlvryJibunAddr;
    }
    public void setDlvryJibunAddr(String dlvryJibunAddr) {
        this.dlvryJibunAddr = dlvryJibunAddr;
    }
    public String getDlvryJibunAddrDtl() {
        return dlvryJibunAddrDtl;
    }
    public void setDlvryJibunAddrDtl(String dlvryJibunAddrDtl) {
        this.dlvryJibunAddrDtl = dlvryJibunAddrDtl;
    }
    public String getHomePw() {
        return homePw;
    }
    public void setHomePw(String homePw) {
        this.homePw = homePw;
    }

    public String getExitTitle() {
        return exitTitle;
    }
    public void setExitTitle(String exitTitle) {
        this.exitTitle = exitTitle;
    }
    public String getViewYn() {
        return viewYn;
    }
    public void setViewYn(String viewYn) {
        this.viewYn = viewYn;
    }
    public String getAcceptTime() {
        return acceptTime;
    }
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }
    public String getSvcRsltCd() {
        return svcRsltCd;
    }
    public void setSvcRsltCd(String svcRsltCd) {
        this.svcRsltCd = svcRsltCd;
    }
    public String getSvcRsltMsg() {
        return svcRsltMsg;
    }
    public void setSvcRsltMsg(String svcRsltMsg) {
        this.svcRsltMsg = svcRsltMsg;
    }
    public String getPayCd() {
        return payCd;
    }
    public void setPayCd(String payCd) {
        this.payCd = payCd;
    }
    public String getOrderRcvTlphNo() {
        return orderRcvTlphNo;
    }
    public void setOrderRcvTlphNo(String orderRcvTlphNo) {
        this.orderRcvTlphNo = orderRcvTlphNo;
    }
    public String getUsimAmt() {
        return usimAmt;
    }
    public void setUsimAmt(String usimAmt) {
        this.usimAmt = usimAmt;
    }
    public String getReservedString() {
        return reservedString;
    }
    public void setReservedString(String reservedString) {
        this.reservedString = reservedString;
    }
    public String getBizOrgCd() {
        return bizOrgCd;
    }
    public void setBizOrgCd(String bizOrgCd) {
        this.bizOrgCd = bizOrgCd;
    }
    public String getDeliveryOrderId() {
        return deliveryOrderId;
    }
    public void setDeliveryOrderId(String deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }
    public String getDlvryKind() {
        return dlvryKind;
    }
    public void setDlvryKind(String dlvryKind) {
        this.dlvryKind = dlvryKind;
    }
    public String getKtOrdId() {
        return ktOrdId;
    }
    public void setKtOrdId(String ktOrdId) {
        this.ktOrdId = ktOrdId;
    }
    public long getSelfDlvryIdx() {
        return selfDlvryIdx;
    }
    public void setSelfDlvryIdx(long selfDlvryIdx) {
        this.selfDlvryIdx = selfDlvryIdx;
    }
    public String getUsimProdId() {
        return usimProdId;
    }
    public void setUsimProdId(String usimProdId) {
        this.usimProdId = usimProdId;
    }
    public String getCstmrName() {
        return cstmrName;
    }
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }

    public String getCstmrNativeRrnDesc() {
        if (null == cstmrNativeRrn || "".equals(cstmrNativeRrn)) {
            return "" ;
        }
        try {
            return EncryptUtil.ace256Dec(cstmrNativeRrn);
        } catch (CryptoException e) {
            return cstmrNativeRrn ;
        }
    }

    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        if (StringUtils.isBlank(cstmrNativeRrn)) {
            this.cstmrNativeRrn = "";
        } else {
            this.cstmrNativeRrn = EncryptUtil.ace256Enc(cstmrNativeRrn);
        }
    }

    public String getOnlineAuthType() {
        return onlineAuthType;
    }
    public void setOnlineAuthType(String onlineAuthType) {
        this.onlineAuthType = onlineAuthType;
    }
    public String getOnlineAuthInfo() {
        return onlineAuthInfo;
    }
    public void setOnlineAuthInfo(String onlineAuthInfo) {
        this.onlineAuthInfo = onlineAuthInfo;
    }
    public String getOnlineAuthDi() {
        return onlineAuthDi;
    }
    public void setOnlineAuthDi(String onlineAuthDi) {
        this.onlineAuthDi = onlineAuthDi;
    }
    public String getDlvryName() {
        return dlvryName;
    }
    public void setDlvryName(String dlvryName) {
        this.dlvryName = dlvryName;
    }
    public String getDlvryTelFn() {
        return dlvryTelFn;
    }
    public void setDlvryTelFn(String dlvryTelFn) {
        this.dlvryTelFn = dlvryTelFn;
    }
    public String getDlvryTelMn() {
        return dlvryTelMn;
    }
    public void setDlvryTelMn(String dlvryTelMn) {
        this.dlvryTelMn = dlvryTelMn;
    }
    public String getDlvryTelRn() {
        return dlvryTelRn;
    }
    public void setDlvryTelRn(String dlvryTelRn) {
        this.dlvryTelRn = dlvryTelRn;
    }
    public String getDlvryTel() {
        return dlvryTel;
    }
    public void setDlvryTel(String dlvryTel) {
        this.dlvryTel = dlvryTel;
    }
    public String getDlvryPost() {
        return dlvryPost;
    }
    public void setDlvryPost(String dlvryPost) {
        this.dlvryPost = dlvryPost;
    }
    public String getDlvryAddr() {
        return dlvryAddr;
    }
    public void setDlvryAddr(String dlvryAddr) {
        this.dlvryAddr = dlvryAddr;
    }
    public String getDlvryAddrDtl() {
        return dlvryAddrDtl;
    }
    public void setDlvryAddrDtl(String dlvryAddrDtl) {
        this.dlvryAddrDtl = dlvryAddrDtl;
    }
    public String getDlvryMemo() {
        return dlvryMemo;
    }
    public void setDlvryMemo(String dlvryMemo) {
        this.dlvryMemo = dlvryMemo;
    }
    public String getSelfMemo() {
        return selfMemo;
    }
    public void setSelfMemo(String selfMemo) {
        this.selfMemo = selfMemo;
    }
    public String getReqUsimSn() {
        return reqUsimSn;
    }
    public void setReqUsimSn(String reqUsimSn) {
        this.reqUsimSn = reqUsimSn;
    }
    public String getDlvryStateCode() {
        return dlvryStateCode;
    }
    public void setDlvryStateCode(String dlvryStateCode) {
        this.dlvryStateCode = dlvryStateCode;
    }
    public String getSelfStateCode() {
        return selfStateCode;
    }
    public void setSelfStateCode(String selfStateCode) {
        this.selfStateCode = selfStateCode;
    }
    public String getDlvryNo() {
        return dlvryNo;
    }
    public void setDlvryNo(String dlvryNo) {
        this.dlvryNo = dlvryNo;
    }
    public String getTbCd() {
        return tbCd;
    }
    public void setTbCd(String tbCd) {
        this.tbCd = tbCd;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getRvisnId() {
        return rvisnId;
    }
    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getUsimProdDtlId() {
        return usimProdDtlId;
    }
    public void setUsimProdDtlId(String usimProdDtlId) {
        this.usimProdDtlId = usimProdDtlId;
    }
    public String getSysRdateS() {
        return sysRdateS;
    }
    public void setSysRdateS(String sysRdateS) {
        this.sysRdateS = sysRdateS;
    }
    public String getSysRdateE() {
        return sysRdateE;
    }
    public void setSysRdateE(String sysRdateE) {
        this.sysRdateE = sysRdateE;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getReqBuyQnty() {
        return reqBuyQnty;
    }
    public void setReqBuyQnty(String reqBuyQnty) {
        this.reqBuyQnty = reqBuyQnty;
    }
    public String getUsimUcost() {
        return usimUcost;
    }
    public void setUsimUcost(String usimUcost) {
        this.usimUcost = usimUcost;
    }
    public String getUsimPrice() {
        return usimPrice;
    }
    public void setUsimPrice(String usimPrice) {
        this.usimPrice = usimPrice;
    }
    public String getReqType() {
        return reqType;
    }
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getDlvryType() {
        return dlvryType;
    }
    public void setDlvryType(String dlvryType) {
        this.dlvryType = dlvryType;
    }

    public String getMinorAgentName() {
        return minorAgentName;
    }

    public void setMinorAgentName(String minorAgentName) {
        this.minorAgentName = minorAgentName;
    }

    public String getMinorOnlineAuthInfo() {
        return minorOnlineAuthInfo;
    }

    public void setMinorOnlineAuthInfo(String minorOnlineAuthInfo) {
        this.minorOnlineAuthInfo = minorOnlineAuthInfo;
    }

    public String getMinorAgentAgrmYn() {
        return minorAgentAgrmYn;
    }

    public void setMinorAgentAgrmYn(String minorAgentAgrmYn) {
        this.minorAgentAgrmYn = minorAgentAgrmYn;
    }
}
