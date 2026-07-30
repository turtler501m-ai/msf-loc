package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 변경 사전체크(Y24)
 */
@Getter
@Setter
@NoArgsConstructor
public class PossibleStateCheckRequest {

    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID
    private String contractNum;
    private String planSoc;
    private String beforePlanSoc;
    private String beforePlanAmt;
    private String openingDate;

    private String customerSsn; // 생년월일
    private String parentScanId;

    /*
        PCN: 요금제 변경
        SRG: 부가서비스 변경
        RSV: 예약 처리
    */
    // 작업구분코드
    private String actCode;
    private List<ProductInfo> prdcList;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductInfo {

        // 상품코드
        // prdcList > prdcCd
        private String prdcCd;

        /*
            A : 가입, C : 해지, U: PARAM변경
             - 요금제 변경시 가입하고 하는 요금제만 A로 입력
         */
        // 상품가입처리코드
        // prdcList > prdcSbscTrtmCd
        private String prdcSbscTrtmCd;

        // 상품유형코드 (R : 부가서비스, P : 요금제)
        // prdcList > prdcTypeCd
        private String prdcTypeCd;

        /*
            중복 가입 부가서비스 해지시
            상품 일련번호는 필수
         */
        // 상품일련번호
        // prdcList > prdcSeqNo
        private String prdcSeqNo;

        // 상품 파람 (부가파람이 필요한 상품의 가입 or 변경시 필수)
        // prdcList > ftrNewParam
        private String ftrNewParam;
    }
}