package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeStep1Dto {

    //boolean isSaved; //고객스텝 저장 완료 여부
    //boolean isVerified; //인증 완료 여부
    String formTypeCd; //신청서유형 : 1-신규/변경
    Long requestKey; //신청서일련번호
    String reqBuyTypeCd; //REQ_BUY_TYPE_CD - MM
    String serviceTypeCd; //SERVICE_TYPE_CD - PO
    String operTypeCd; //OPER_TYPE_CD - MNP3
    String cstmrType; //NA
    String identityCertTypeCd; //K
    String identityTypeCd;
    String identityIssuRegion; //licenseRegion1
    String cstmrVisitTypeCd; //V1
    String cstmrNm; //고객명
    String cstmrNativeRrn1; //내국인 주민번호 앞자리
    String cstmrNativeRrn2; //내국인 주민번호 뒷자리
    String cstmrForeignerRrn1; //외국인 등록번호 앞자리
    String cstmrForeignerRrn2; //외국인 등록번호 뒷자리
    String cstmrJuridicalRrn1;
    String cstmrJuridicalRrn2;
    String cstmrJuridicalBizNo1;
    String cstmrJuridicalBizNo2;
    String cstmrJuridicalBizNo3;
    String cstmrJuridicalRepNm;
    String upjnCd; //업종?
    String bcuSbst; //업태?
    String deviceChgTel1; //연락처 앞자리
    String deviceChgTel2; //연락처 중간자리
    String deviceChgTel3; //연락처 마지막자리
    String repName;
    String repRegistrationNo1;
    String repRegistrationNo2;
    String repForeignerNo1;
    String repForeignerNo2;
    String repRelation; //
    String repPhone1;
    String repPhone2;
    String repPhone3;
    String repPhoneAuth;
    String repAgree;
    String realUserName;
    String userBirthDate;
    String userGender; //성별
    String minorAgentNm;
    String agentBirthDate;
    String agentGender; //대리인 성별
    String minorAgentRelTypeCd;
    String minorAgentTelFnNo;
    String minorAgentTelMnNo;
    String minorAgentTelRnNo;
    String mobileNo1;
    String mobileNo2;
    String mobileNo3;
    String telNo1;
    String telNo2;
    String telNo3;
    String emailAddr1;
    String emailAddr2;
    String address1;
    String address2;
    String address3;
    String country;
    String stayPeriod;
    String visaType;
    String openTypeCd; //99
    String deviceModel;
    String capacity;
    String color;
    String contractPeriod;
    String installmentMonth;
    String discountType;
    String planName1;
    String planName2;
    String agency;
    String hasSim;
    String usimKindsCd;
    String reqUsimSn;
    String simPurchaseMethod;
    String prodNm;
    String eid;
    String imei1;
    String imei2;
    String imei;
    String moveCompanyCd;
    String moveMobileNo;
    String moveAuthTypeCd;
    String moveAuthNo;
    String transferBankNum;
    String transferCardNum;
    boolean moveThismonthPayTypeCd; //false
    //String moveAllotmentSttusCd[]; //
    String moveRefundAgreeYn; //배열?
    String reqWantFnNo;
    String reqWantMnNo;
    String reqWantRnNo;
    String wishNo;
    //String reqAdditionListNm[];
    //String addtionId[];
    String clauseInsuranceYn;
    String recCat1;
    String recCat2;
    String cstmrBillSendTypeCd;
    String reqPayTypeCd;
    String autoPayerType;
    String reqBankCd;
    String reqAccountNo;
    String reqAccountNm;
    String reqAccountRrn;
    String reqAccountRelTypeCd;
    boolean isAutoAgree;
    String cardPayerType;
    String reqCardCompanyCd;
    String reqCardNo;
    String reqCardMm;
    String reqCardYy;
    String reqCardNm;
    String reqCardRrn;
    String cardRelation;
    String combId;
    boolean combAgree;
    String memo;
    String clausePriCollectYn;
    String clausePriOfferYn;
    String clauseEssCollectYn;
    String clausePriTrustYn;
    String clausePriAdYn;
    String clauseConfidenceConfidenceYn;
    String clauseFathYn;
}
