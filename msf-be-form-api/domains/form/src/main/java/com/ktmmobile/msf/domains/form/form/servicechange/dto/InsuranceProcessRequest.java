package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class InsuranceProcessRequest {

    private long custReqSeq;
    private long requestKey;
    @NotNull
    private String ncn;
    @NotNull
    private String ctn;
    private String custId;
    private String contractNum;
    private String insrProdCd;
    private String insrType;
    private String svcTgtCd;
    private String clauseInsuranceYn; // 가입여부 (Y/N)
    private String catCd;             // 보험 카테고리 코드
    private String actCode;
    private String parentScanId;

    private String reqType;           // 신청타입
    private String custNm;            // 고객명
    private String mobileNo;      // 휴대폰번호
    private String cstmrNativeRrn;  // 주민번호 (마스킹/암호화 필수)
    private String cstmrTypeCd;   // 고객타입
    private String etcMobile;        // 기타연락처
    private String cretId;

    private List<PrdcList> prdcList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrdcList {

        private String prdcCd;
        private String prdcSbscTrtmCd;
        private String prdcTypeCd;
    }

}
