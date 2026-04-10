package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class NowDlvryReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /////////////// 배달지역 조회 input  ///////////////
    //	우편번호
    private String zipNo;
    // 기본주소
    private String targetAddr1;
    // 상세주소
    private String targetAddr2;
    // 주소 유형 코드(1:지번,2:도로명)
    private String addrTypeCd = "1";
    // 배달 업체 코드 "01: 생각대로, 02: 부르심  - 값이 없으면 사업자별 default 업체로 연동"
    private String bizOrgCd;

    //////////////////////// 배달 주문 접수 input ////////////////////////
    private String orderRcvTlphNo; //	수령고객연락처	11	M	휴대폰 번호
//    private String zipNo; // 우편번호	6	M	우편번호
//    private String addrTypeCd; // 주소 유형	1	M	주소 유형 코드(1:지번,2:도로명)
//    private String targetAddr1; // 기본주소	100	M
//    private String targetAddr2; // 상세주소	100	M
    private String custInfoAgreeYn; // 개인정보제공동의 여부	3	M
    private String rsvOrderYn="N"; // 배달예약여부	1	M	Y or N
    private String rsvOrderDt; // 배달희망시간	14	M	YYYYMMDDHH24MISS
    private String orderReqMsg; // 배달요청메세지	100	M
    private String acceptTime; // 접수가능시간	3	M	배달지역조회 호출시 응답받은 접수가능시간
    private String usimAmt; //	 유심금액	5	M	유심금액
//    private String bizOrgCd; // 배달 업체 코드	2	M	배달지역조회 호출시 응답받은 배달업체코드


    //////////////////// 배달주문 변경취소 //////////////////////////////
    private String jobGubun; // 작업구분	1	M	U : 변경, D : 취소
    private String ktOrderId; // KT 오더 ID	20	M	"KT 오더 ID    사업자코드(3자리) + YYYYMMDD + SEQ(5자리)    예) KIS2021043010001"
//    private String orderRcvTlphNo; // 수령고객연락처	11	C	휴대폰 번호(작업구분이 U인경우 필수)
//    private String zipNo; // 우편번호	6	C	우편번호(작업구분이 U인경우 필수)
//    private String addrTypeCd	; // 주소 유형	1	C	주소 유형 코드(1:지번,2:도로명)(작업구분이 U인경우 필수)
//    private String targetAddr1; // 기본주소	100	C	(작업구분이 U인경우 필수)
//    private String targetAddr2; // 상세주소	100	C	(작업구분이 U인경우 필수)
//    private String custInfoAgreeYn; // 	개인정보제공동의 여부	3	C	(작업구분이 U인경우 필수)
//    private String rsvOrderYn; // 배달예약여부	1	C	(작업구분이 U인경우 필수)
//    private String rsvOrderDt; // 배달희망시간	14	C	YYYYMMDDHH24MISS(작업구분이 U인경우 필수)
//    private String orderReqMsg; // 배달요청메세지	100	C	(작업구분이 U인경우 필수)
//    private String acceptTime; // 접수가능시간	3	C	배달접수시 입력했던 접수가능시간(작업구분이 U인경우 필수)
//    private String usimAmt; //	유심금액	5	C	유심금액

    private String entY; // 위도
    private String entX; // 경도
    private String jibunAddr; // 지번
    private String nfcYn; //일반유심 N , NFC유심 Y

    public String getJibunAddr() {
        return jibunAddr;
    }
    public void setJibunAddr(String jibunAddr) {
        this.jibunAddr = jibunAddr;
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
    public String getZipNo() {
        return zipNo;
    }
    public String getOrderRcvTlphNo() {
        return orderRcvTlphNo;
    }
    public void setOrderRcvTlphNo(String orderRcvTlphNo) {
        this.orderRcvTlphNo = orderRcvTlphNo;
    }
    public String getCustInfoAgreeYn() {
        return custInfoAgreeYn;
    }
    public void setCustInfoAgreeYn(String custInfoAgreeYn) {
        this.custInfoAgreeYn = custInfoAgreeYn;
    }
    public String getRsvOrderYn() {
        return rsvOrderYn;
    }
    public void setRsvOrderYn(String rsvOrderYn) {
        this.rsvOrderYn = rsvOrderYn;
    }
    public String getRsvOrderDt() {
        return rsvOrderDt;
    }
    public void setRsvOrderDt(String rsvOrderDt) {
        this.rsvOrderDt = rsvOrderDt;
    }
    public String getOrderReqMsg() {
        return orderReqMsg;
    }
    public void setOrderReqMsg(String orderReqMsg) {
        this.orderReqMsg = orderReqMsg;
    }
    public String getAcceptTime() {
        return acceptTime;
    }
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }
    public String getUsimAmt() {
        return usimAmt;
    }
    public void setUsimAmt(String usimAmt) {
        this.usimAmt = usimAmt;
    }
    public String getJobGubun() {
        return jobGubun;
    }
    public void setJobGubun(String jobGubun) {
        this.jobGubun = jobGubun;
    }
    public String getKtOrderId() {
        return ktOrderId;
    }
    public void setKtOrderId(String ktOrderId) {
        this.ktOrderId = ktOrderId;
    }
    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }
    public String getTargetAddr1() {
        return targetAddr1;
    }
    public void setTargetAddr1(String targetAddr1) {
        this.targetAddr1 = targetAddr1;
    }
    public String getTargetAddr2() {
        return targetAddr2;
    }
    public void setTargetAddr2(String targetAddr2) {
        this.targetAddr2 = targetAddr2;
    }
    public String getAddrTypeCd() {
        return addrTypeCd;
    }
    public void setAddrTypeCd(String addrTypeCd) {
        this.addrTypeCd = addrTypeCd;
    }
    public String getBizOrgCd() {
        return bizOrgCd;
    }
    public void setBizOrgCd(String bizOrgCd) {
        this.bizOrgCd = bizOrgCd;
    }
    public String getNfcYn() {
        return nfcYn;
    }
    public void setNfcYn(String nfcYn) {
        this.nfcYn = nfcYn;
    }



}
