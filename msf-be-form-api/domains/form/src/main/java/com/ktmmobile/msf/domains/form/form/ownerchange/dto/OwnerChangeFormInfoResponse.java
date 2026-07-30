package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Data;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Data
public class OwnerChangeFormInfoResponse {

    // msf_request_name_trns (양도인 정보)
    private String trnsCstmrTypeCd;          // 고객구분유형코드
    private String trTrnsNm;                   // 양도인명
    private String trnsTrnsfeMobileNo;       // 양수인 모바일번호
    private String trnsMobileNo;             // 명의변경대상 모바일 번호 양도인
    private String trMinorAgentNm;         // 미성년자법정대리인성명 양도인
    private String trMinorAgentBirth;      // 법정대리인생년월일 양도인
    private String trMinorAgentGenderCd;   // 법정대리인성별 양도인
    private String trMinorAgentRelTypeCd; // 법정대리인관계유형코드 양도인
    private String trMinorAgentTelFnNo;    // 법정대리인 전화번호(국번) 양도인
    private String trMinorAgentTelMnNo;    // 법정대리인 전화번호(중간자리) 양도인
    private String trMinorAgentTelRnNo;    // 법정대리인 전화번호(끝자리) 양도인
    private String trIdentityCertTypeCd;
    private String trDeviceChgTel1; //휴대폰 처음3자리
    private String trDeviceChgTel2; //휴대폰 가운데4자리
    private String trDeviceChgTel3; //휴대폰 마지막4자리

    private String trnsBirth; // 양도인 생년월일
    private String trnsGenderCd; // 양도인 성별
    private String trCstmrJuridicalRrn1; //법인등록번호1
    private String trCstmrJuridicalRrn2; //법인등록번호2
    private String trCstmrJuridicalBizNo1; //사업자등록번호1
    private String trCstmrJuridicalBizNo2; //사업자등록번호2
    private String trCstmrJuridicalBizNo3; //사업자등록번호3
    private String trCstmrJuridicalRepNm; //대표자명
    private String trRepName;

    // private String trRepRegistrationNo1;
    // private String trRepRegistrationNo2;
    // private String trRepForeignerNo1;
    // private String trRepForeignerNo2;
    // private String trMinorUserBirthDate;
    // private String trMinorUserGender;
    // private String trCstmrNm; //이름
    // private String trCstmrNativeRrn1; //내국인 주민번호1
    // private String trCstmrNativeRrn2; //내국인 주민번호2
    // private String trCstmrForeignerRrn1; //외국인 주민번호1
    // private String trCstmrForeignerRrn2; //외국인 주민번호2
    // private String trUserBirthDate; //생년월일
    // private String trUserGender; //성별

    // msf_request_name_chg (명의변경 요청 정보)
    private String cstmrTypeCd;              // 고객구분유형코드
    private String memo;                     // 메모
    private String usimSuccYn;               // 유심 승계 여부
    private String iccId;                   // 유심번호
    private String usimNm;
    private String esimYn;
    private String simTypeCd;
    private String esimPhoneId;
    private String jehuProdTypeCd;
    private String soc;
    private String socNm;
    private String socBaseChrgAmt;
    private String identityCertTypeCd;

    // msf_request_sale
    private String usimPayMthdCd;

    // msf_request_cstmr (양수인 정보)
    private String cstmrNm;                 // 양수인 고객명
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String cstmrNativeRrn;          // 양수인 식별정보 (Omitted)
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String cstmrForeignerRrn;          // 양수인 외국인 식별정보 (Omitted)
    private String cstmrNativeGenderCd;     // 성별
    private String cstmrForeignerGenderCd;  // 외국인 성별
    private String cstmrForeignerPn;        // 여권번호
    private String cstmrForeignerCountryCd; // 국가코드
    private String cstmrForeignerNation;    // 국적
    private String cstmrForeignerVisaNo;    // 비자
    private String upjnCd;                  // 업종
    private String bcuSbst;                 // 업태
    private String cstmrVisitTypeCd;        // 방문고객 유형코드
    private String cstmrReceiveTelFnNo;     // 일반전화(국번)
    private String cstmrReceiveTelNmNo;     // 일반전화(중간자리)
    private String cstmrReceiveTelRnNo;      // 일반전화(끝자리)
    private String cstmrTelFnNo;            // 고객정보 전화번호(국번)
    private String cstmrTelMnNo;            // 고객정보 전화번호(중간자리)
    private String cstmrTelRnNo;            // 고객정보 전화번호(끝자리)
    private String cstmrMobileFnNo;         // 휴대폰(국번)
    private String cstmrMobileMnNo;         // 휴대폰(중간자리)
    private String cstmrMobileRnNo;         // 휴대폰(끝자리)
    private String cstmrZipcd;              // 우편번호
    private String cstmrAdr;                // 정보주소
    private String cstmrAdrDtl;             // 상세주소
    private String cstmrEmailAdr;           // 이메일
    private String emailAddr1;           // 이메일
    private String emailAddr2;           // 이메일
    private String cstmrJuridicalRrn1; //법인등록번호1
    private String cstmrJuridicalRrn2; //법인등록번호2
    private String cstmrJuridicalBizNo1; //사업자등록번호1
    private String cstmrJuridicalBizNo2; //사업자등록번호2
    private String cstmrJuridicalBizNo3; //사업자등록번호3
    private String cstmrJuridicalRepNm; //대표자명
    private String repName;
    private String cstmrPrivateBizNoIssuDate;
    private String cstmrJuridicalBizNoIssuDate;
    private String cstmrForeignerVdateStartDate;
    private String cstmrForeignerVdateEndDate;

    // msf_request_agent (대리인 정보)
    private String minorAgentNm;            // 법정대리인성명
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String minorAgentRrn;           // 법정대리인등록번호
    private String minorAgentGenderCd;       // 성별
    private String minorAgentRelTypeCd;     // 관계유형 코드
    private String minorAgentTelFnNo;       // 법정대리인 전화(국번)
    private String minorAgentTelMnNo;       // 법정대리인 전화(중간자리)
    private String minorAgentTelRnNo;       // 법정대리인 전화(끝자리)
    private String jrdclAgentNm;            // 법인대리인명
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String jrdclAgentRrn;           // 법인대리인 등록식별정보 (Omitted)
    private String jrdclAgentRelTypeCd;     // 법인대리인 관계유형코드
    private String jrdclAgentTelFnNo;       // 법인대리인 전화(국번)
    private String jrdclAgentTelMnNo;       // 법인대리인 전화(중간자리)
    private String jrdclAgentTelRnNo;       // 법인대리인 전화(끝자리)
    private String cstmrJuridicalUserNm;       // 실사용자이름
    private String cstmrJuridicalBirth;       // 실사용자생년월일

    // msf_request_bill_req (청구/납부 정보)
    private String reqPayTypeCd;            // 요금납부방법유형코드
    private String reqBankCd;               // 은행코드
    private String reqAccountNm;            // 예금주명
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String reqAccountRrn;           // 예금주 식별정보 (Omitted)
    private String reqAccountRelTypeCd;     // 관계유형코드
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String reqAccountNo;            // 계좌번호
    private String reqCardNm;               // 신용카드 명의자
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String reqCardRrn;              // 카드명의자 식별정보 (Omitted)
    private String reqCardCompanyCd;        // 카드사코드
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String reqCardNo;               // 신용카드번호
    private String reqCardYy;               // 유효년
    private String reqCardMm;               // 유효월
    private String othersPaymentYn;         // 타인납부여부
    private String othersPaymentTelFnNo;    // 타인납부 전화번호(국번)
    private String othersPaymentTelMnNo;    // 타인납부 전화번호(중간자리)
    private String othersPaymentTelRnNo;    // 타인납부 전화번호(끝자리)
    private String othersPaymentNm;         // 타인납부고객명
    @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String othersPaymentRrn;        // 타인납부자 식별정보 (Omitted)
    private String othersPaymentRelTypeCd;  // 관계유형코드
    private String othersPaymentReqNm;      // 신청인명
    private String othersPaymentAgrYn;      // 타인납부 동의여부
    private String prntsBillNo;             // 통합청구번호
    private String cstmrBillSendTypeCd;     // 명세서 종류유형코드
    private String billEmailAdr;            // 명세서이메일주소 (cstmr_email_adr 변수명 충돌 방지 조정)
}
