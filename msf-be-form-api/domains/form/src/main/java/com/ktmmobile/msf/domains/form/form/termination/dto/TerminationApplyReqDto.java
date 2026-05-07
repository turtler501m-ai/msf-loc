package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class TerminationApplyReqDto {

    private Customer customer;
    private Product product;
    private Agreement agreement;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Customer {
        private String managerCd;
        private String managerNm;
        private String agentCd;
        private String agentNm;
        private String customerType;
        private String identityCertTypeCd;
        private String identityTypeCd;
        private String identityIssuDate;
        private String identityIssuRegion;
        private String driveLicnsNo;
        private String userName;
        private String userBirthDate;
        private String selfIssuNo;
        private String cancelPhone1;
        private String cancelPhone2;
        private String cancelPhone3;
        private String afterTel1;
        private String afterTel2;
        private String afterTel3;
        private String postMethod;
        private String agencyName;
        private String cpntId;
        private String cpntNm;
        private String cntpntShopCd;
        private String cntpntShopNm;
        private String ncn;
        private String custId;
}
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Product {
        private String isActive;
        private String usageFee;
        private String penaltyFee;
        private String finalAmount;
        private String remainPeriod;
        private String remainAmount;
        private String memo;
}
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Agreement {
        private boolean agreeCheck1;
        private boolean agreeCheck2;
        private boolean agreeCheck3;
}
}
