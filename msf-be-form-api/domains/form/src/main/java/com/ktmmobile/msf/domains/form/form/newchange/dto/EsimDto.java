package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EsimDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eid;
    private String imei1;
    private String imei2;
    private int uploadPhoneSrlNo;
    private String resultCode;
    private String intmSrlNo;
    private String resultMsg;
    private String rstCd;

    // Y12 output
    private String moveTlcmIndCd; // 이동텔레콤구분코드
    private String moveCmncGnrtIndCd; // 이동통신세대구분코드
    private String moveCd; // 이동텔레콤구분코드
    // 나이스 인증
    private String cstmrName;
    private String cstmrNativeRrn01;
    private String modelId;
    private String modelNm;
    private String cstmrMobile;

    //또 다른 거시기 Y14 호출 정보
    private String modelIdOther;
    private String modelNmOther;
    private String intmSrlNoOther;


    // Y15 input
    private String wrkjobDivCd; //작업구분코드
    private String wifiMacAdr; //MAC ID
    private String intmEtcPurpDivCd; //기기기타용도구분코드
    private String euiccId; //eIccId
    private String trtDivCd; //처리구분코드
    private String cmpnCdIfVal; //CEIR 연동 사업자식별코드명
    private String birthday; //생년월일
    private String sexDiv; //성별
    private String ctn; //전화번호
}
