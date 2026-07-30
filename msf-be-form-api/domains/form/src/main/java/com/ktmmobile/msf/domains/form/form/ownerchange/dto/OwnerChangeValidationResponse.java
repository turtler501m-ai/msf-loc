package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerChangeValidationResponse {

    private String resultCd;
    private String message;
    private OwnerChangeInfo response;

    @Data
    public static class OwnerChangeInfo {

        private Long requestKey;
        private String addr; // 주소
        private String email; // 이메일 주소
        private String homeTel; // 전화번호
        private String initActivationDate; // 가입일
        private String ctn;
        private String custId;
        private String ncn;
        private String userId;
        private String esimYn;
        private String banAdrZip;
        private String banAdrPrimaryLn;
        private String banAdrSecondaryLn;
        private String blBillingMethod;
        private String prodId;
        private String prodNm;
        private String prodAmt;
        private String billTypeCd; // 명세서 발송유형
        private String payMethod; // 납부방법 (신용카드, 자동이체, 지로, 간편결제)
        /** 녹취스크립트용 판매점 정보 */
        private String cntpntCdNm;
        private String userNm;
        private String jehuProdType;
        private String jehuProdNm;
    }

}
