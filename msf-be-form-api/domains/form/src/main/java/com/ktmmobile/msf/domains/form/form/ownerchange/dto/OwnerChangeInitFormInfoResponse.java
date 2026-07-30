package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import java.util.List;

import lombok.Data;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Data
public class OwnerChangeInitFormInfoResponse {

    private TrCustomer trCustomer;
    private TeCustomer teCustomer;
    private PlanInfo planInfo;
    private UsimInfo usimInfo;
    private ProductPayment productPayment;
    private Juridical juridical;
    private String memo;
    private String usimSuccYn;               // 유심 승계 여부
    private String iccId;                   // 유심번호
    private String esimPhoneId;
    private String usimPayMthdCd;
    private String usimNm;

    @Data
    public static class TrCustomer {

        private String cstmrTypeCd;
        private String identityCertTypeCd;
        private String identityTypeCd; //신분증 스캔
        private String cstmrJuridicalRrn1; //법인등록번호1
        private String cstmrJuridicalRrn2; //법인등록번호2
        private String cstmrJuridicalBizNo1; //사업자등록번호1
        private String cstmrJuridicalBizNo2; //사업자등록번호2
        private String cstmrJuridicalBizNo3; //사업자등록번호3
        private String cstmrJuridicalRepNm; //대표자명
        private String deviceChgTel1; //휴대폰 처음3자리
        private String deviceChgTel2; //휴대폰 가운데4자리
        private String deviceChgTel3; //휴대폰 마지막4자리
        private String repName;
        private String repGender;
        private String minorAgentNm; //위임받은고객이름
        private String minorAgentRelTypeCd; //신청인과의 관계
        private String minorAgentTelFnNo; //연락처1
        private String minorAgentTelMnNo; //연락처2
        private String minorAgentTelRnNo; //연락처3
        private String repRegistrationNo1;
        private String repRegistrationNo2;
        private String repForeignerNo1;
        private String repForeignerNo2;
        private String minorUserBirthDate;
        private String minorUserGender;
        private String cstmrNm; //이름
        private String cstmrNativeRrn1; //내국인 주민번호1
        private String cstmrNativeRrn2; //내국인 주민번호2
        private String cstmrForeignerRrn1; //외국인 주민번호1
        private String cstmrForeignerRrn2; //외국인 주민번호2
        private String userBirthDate; //생년월일
        private String userGender; //성별
    }

    @Data
    public static class TeCustomer {

        private String cstmrTypeCd;
        private String cstmrNm;
        private String cstmrNativeRrn;
        private String cstmrNativeRrn1;
        private String cstmrNativeRrn2;
        private String cstmrForeignerRrn;          // 양수인 외국인 식별정보 (Omitted)
        private String cstmrForeignerRrn1;
        private String cstmrForeignerRrn2;
        private String upjnCd;
        private String bcuSbst;
        private String deviceChgTel1;
        private String deviceChgTel2;
        private String deviceChgTel3;
        private String identityCertTypeCd;
        private String identityTypeCd;
        private String cstmrJuridicalRrn1;
        private String cstmrJuridicalRrn2;
        private String cstmrJuridicalBizNo1;
        private String cstmrJuridicalBizNo2;
        private String cstmrJuridicalBizNo3;
        private String cstmrJuridicalRepNm;
        private String cstmrVisitTypeCd;
        private String cstmrForeignerVdateStartDate; // 체류기간 시작
        private String cstmrForeignerVdateEndDate; // 체류기간 종료
        private String cstmrPrivateBizNoIssuDate;
        private String cstmrJuridicalBizNoIssuDate;
        private String repName;
        private String repBirthDate;
        private String minorAgentNm;
        private String minorAgentRrn;           // 법정대리인등록번호
        private String minorAgentRelTypeCd;
        private String minorAgentTelFnNo;
        private String minorAgentTelMnNo;
        private String minorAgentTelRnNo;
        private String repRegistrationNo1;
        private String repRegistrationNo2;
        private String repForeignerNo1;
        private String repForeignerNo2;
        private String minorUserGender;
        private String realUserName;
        private String realUserBirthDate;
        private String agentGender;
        private String agentBirthDate;
        private String mobileNo1;
        private String mobileNo2;
        private String mobileNo3;
        private String telNo1;
        private String telNo2;
        private String telNo3;
        private String emailAddr1;
        private String emailAddr2;
        private String zipNo;
        private String address;
        private String detailAddress;
        private String country;
        private String teStayPeriod;
        private String visaType;
    }

    @Data
    public static class PlanInfo {

        private String planName1; // 요금제1
        private String planName2; // 요금제2
        private String planAmt;   // 요금
        private String planNm;
        private String jehuPartnerTypeCd;
        private String jehuPartnerTypeNm;
        private String jehuProdTypeCd;
        private String dataType;
        private String jehuProdName;
        private String planSelectType; // CURRENT, CHANGE
    }

    @Data
    public static class UsimInfo {

        private boolean hasSim;
        private String reqUsimSn;                   // 유심번호
        private String reqUsimNm;
        private String simTypeCd;
        private String simPurchaseMethod;
        private String esimYn;
    }

    @Data
    public static class ProductPayment {

        // msf_request_bill_req (청구/납부 정보)
        private String reqPayTypeCd;            // 요금납부방법유형코드
        private String reqBankCd;               // 은행코드
        private String reqAccountNm;            // 예금주명
        private String reqAccountRrn;           // 예금주 식별정보 (Omitted)
        private String reqAccountRelTypeCd;     // 관계유형코드
        @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
        private String reqAccountNo;            // 계좌번호
        private String reqCardNm;               // 신용카드 명의자
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

    @Data
    public static class Juridical {

        private String minorAgentNm;          // 법정대리인명
        private String agentBirthDate;        // 대리인 생년월일
        private String agentGender;           // 대리인 성별
        private String minorAgentRelTypeCd;   // 대리인 관계 유형 코드
        private String minorAgentTelFnNo;     // 대리인 전화번호 앞자리
        private String minorAgentTelMnNo;     // 대리인 전화번호 중간자리
        private String minorAgentTelRnNo;     // 대리인 전화번호 뒷자리
    }

    public void setup() {
        List<String> isForeigner = List.of("FM", "FN");
        List<String> isMinor = List.of("NM", "FM");

        if (isForeigner.contains(this.teCustomer.cstmrTypeCd)) {
            this.teCustomer.cstmrForeignerRrn1 = this.teCustomer.cstmrForeignerRrn.substring(0, 6);
            this.teCustomer.cstmrForeignerRrn2 = this.teCustomer.cstmrForeignerRrn.substring(6);
        }

        if (StringUtils.hasText(this.teCustomer.cstmrNativeRrn)) {
            this.teCustomer.cstmrNativeRrn1 = this.teCustomer.cstmrNativeRrn.substring(0, 6);
            this.teCustomer.cstmrNativeRrn2 = this.teCustomer.cstmrNativeRrn.substring(6);
        }

        if (!StringUtils.hasText(this.teCustomer.cstmrVisitTypeCd)) {
            this.teCustomer.cstmrVisitTypeCd = isMinor.contains(this.teCustomer.cstmrTypeCd) ? "VCD" : "VMY";
        }

        // 기본 값 유심보유 / 유심 / 현재 요금제 사용
        this.usimInfo.hasSim = true;
        this.usimInfo.simTypeCd = "USIM";
        this.planInfo.planSelectType = "CURRENT";

        if ("Y".equals(this.usimInfo.esimYn)) {
            this.usimInfo.simTypeCd = "ESIM";
        }

        // 유심승계가 아닌 경우 데이터 세팅
        if (!"Y".equals(this.usimSuccYn)) {
            this.usimInfo.hasSim = false;
            this.usimInfo.reqUsimSn = this.iccId;
            this.usimInfo.reqUsimNm = this.usimNm;
            this.usimInfo.simPurchaseMethod = "B".equals(this.usimPayMthdCd) ? "B" : "R";
        }

        // 요금제가 있을 경우 요금제 선택 변경
        if (StringUtils.hasText(this.planInfo.planName2)) {
            this.planInfo.planSelectType = "CHANGE";
        }

        // 법정대리인 식별번호 있을 경우 내국인 외국인 앞자리 뒷자리로 분리
        if (StringUtils.hasText(this.teCustomer.minorAgentRrn)) {
            if ("FM".equals(this.teCustomer.cstmrTypeCd)) {
                this.teCustomer.repForeignerNo1 = this.teCustomer.minorAgentRrn.substring(0, 6);
                this.teCustomer.repForeignerNo2 = this.teCustomer.minorAgentRrn.substring(6);
            } else {
                this.teCustomer.repRegistrationNo1 = this.teCustomer.minorAgentRrn.substring(0, 6);
                this.teCustomer.repRegistrationNo2 = this.teCustomer.minorAgentRrn.substring(6);
            }
        }

        // 법인대리인 생년월일 앞자리만 가져오기 (YYYYMMDD + 1||2 0000 로 세팅중)
        if (StringUtils.hasText(this.juridical.getAgentBirthDate()) && this.juridical.getAgentBirthDate().length() > 8) {
            String judBirth = this.juridical.getAgentBirthDate().substring(0, 8);
            String judGender = (this.juridical.getAgentBirthDate().charAt(8) == '1') ? "M" : "F";
            this.juridical.agentBirthDate = judBirth;
            this.juridical.agentGender = judGender;
        }

        // 미성년자 모바일 명세서 선택 불가
        if (isMinor.contains(this.teCustomer.cstmrTypeCd) && "MB".equals(this.productPayment.cstmrBillSendTypeCd)) {
            this.productPayment.cstmrBillSendTypeCd = "CB";
        }

    }
}
