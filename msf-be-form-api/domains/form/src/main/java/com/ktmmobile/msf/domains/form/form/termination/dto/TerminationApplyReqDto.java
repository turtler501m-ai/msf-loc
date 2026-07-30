package com.ktmmobile.msf.domains.form.form.termination.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.domains.form.form.common.dto.MsfRequestDocDto;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class TerminationApplyReqDto {

    private String parentScanId;
    private Long requestKey;
    private String documentId;
    private String fileNm;
    private String fileMaskNm;
    private Customer customer;
    private Product product;
    private Agreement agreement;
    private List<MsfRequestDocDto> msfRequestDocList;

    private String cstmrTypeCd;
    private String receiveWayCd;
    private String cancelMobileNo;
    private String receiveMobileNo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Customer {
        private String managerCd;
        private String managerNm;
        private String agentCd;
        private String ktOrgId;
        private String agentNm;
        private String shopCd;
        private String shopNm;
        private String realShopNm;
        private String customerType;
        private String identityCertTypeCd;
        private String identityTypeCd;
        private String identityIssuDate;
        private String identityIssuRegion;
        private String driveLicnsNo;
        private String userName;
        private String userBirthDate;
        private String userGender;
        private String cstmrNativeRrn;
        private String cstmrNativeRrn1;
        private String cstmrNativeRrn2;
        private String cstmrPrivateCname;
        private String cstmrPrivateBizNo;
        private String cstmrPrivateBizNo1;
        private String cstmrPrivateBizNo2;
        private String cstmrPrivateBizNo3;
        private String cstmrForeignerRrn;
        private String cstmrForeignerRrn1;
        private String cstmrForeignerRrn2;
        private String cstmrForeignerPn;
        private String cstmrForeignerCountryCd;
        private String cstmrForeignerNation;
        private String cstmrForeignerVisaNo;
        private String cstmrForeignerVdateStartDate;
        private String cstmrForeignerVdateEndDate;
        private String cstmrJuridicalRrn;
        private String cstmrJuridicalRrn1;
        private String cstmrJuridicalRrn2;
        private String cstmrJuridicalBizNo;
        private String cstmrJuridicalBizNo1;
        private String cstmrJuridicalBizNo2;
        private String cstmrJuridicalBizNo3;
        private String cstmrJuridicalRepNm;
        private String upjnCd;
        private String bcuSbst;
        private String cstmrJuridicalUserNm;
        private String cstmrJuridicalBirth;
        private String repName;
        private String repBirthDate;
        private String repRegistrationNo1;
        private String repRegistrationNo2;
        private boolean repAgree;
        private String minorAgentNm;
        private String agentBirthDate;
        private String agentGender;
        private String minorAgentRelTypeCd;
        private String minorAgentTelFnNo;
        private String minorAgentTelMnNo;
        private String minorAgentTelRnNo;
        private String cstmrVisitTypeCd;
        private String cstmrTelFnNo;
        private String cstmrTelMnNo;
        private String cstmrTelRnNo;
        private String cstmrZipcd;
        private String cstmrAdr;
        private String cstmrAdrDtl;
        private String cstmrAdrBjd;
        private String cstmrEmailAdr;
        private String cstmrEmailReceiveYn;
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
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Product {
        private String cancelUseCompanyCd;
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
        private List<Clause> clauses;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Clause {
        private String code;
        private String termsGroupCd;
        private String termsItemCd;
        private String cdGroupId;
        private String cdGroupId2;
        private Object checked;
        private String version;
    }
}
