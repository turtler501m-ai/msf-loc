package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NowDlvryReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /////////////// 배달지역 조회 input  ///////////////
    private String zipNo;               // 우편번호
    private String targetAddr1;         // 기본주소
    private String targetAddr2;         // 상세주소
    private String addrTypeCd = "1";    // 주소 유형 코드(1:지번,2:도로명)
    private String bizOrgCd;            // 배달 업체 코드

    //////////////////////// 배달 주문 접수 input ////////////////////////
    private String orderRcvTlphNo;      // 수령고객연락처
    private String custInfoAgreeYn;     // 개인정보제공동의 여부
    private String rsvOrderYn = "N";    // 배달예약여부 (Y or N)
    private String rsvOrderDt;          // 배달희망시간 YYYYMMDDHH24MISS
    private String orderReqMsg;         // 배달요청메세지
    private String acceptTime;          // 접수가능시간
    private String usimAmt;             // 유심금액

    //////////////////// 배달주문 변경취소 //////////////////////////////
    private String jobGubun;            // 작업구분 U:변경, D:취소
    private String ktOrderId;           // KT 오더 ID

    private String entY;    // 위도
    private String entX;    // 경도
    private String jibunAddr; // 지번
    private String nfcYn;   // 일반유심 N , NFC유심 Y
}
