package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceChangeCompleteReqDto {

    private String parentScanId;

    /** 사전 채번된 신청서키 (없으면 서버에서 새로 발급) */
    private Long requestKey;
    private String ncn;
    private String ctn;
    private String custId;
    private String contractNum;
    private String cstmrTypeCd;
    private Boolean additionConfirmCompleted;
    private String cstmrNm;
    private String userBirthDate;
    private String userGender;
    /** 본인인증 결과: 인증 방식 */
    private String onlineAuthType;
    /** 본인인증 결과: 인증 정보 */
    private String onlineAuthInfo;
    /** 본인인증 결과: 고객 CI */
    private String selfCstmrCi;
    /** 신분증인증: 유형 */
    private String selfCertType;
    /** 신분증인증: 발급/만료일자 */
    private String selfIssuExprDt;
    /** 신분증인증: 발급번호/면허번호 */
    private String selfIssuNum;
    /** 안면인증 트랜잭션 ID */
    private String fathTransacId;
    private String cstmrPrivateBizNo;
    private String cstmrForeignerRrn;
    private String cstmrJuridicalRrn1;
    private String cstmrJuridicalRrn2;
    private String cstmrJuridicalBizNo1;
    private String cstmrJuridicalBizNo2;
    private String cstmrJuridicalBizNo3;
    private String cstmrJuridicalRepNm;
    private String cstmrVisitTypeCd;
    private String telNo1;
    private String telNo2;
    private String telNo3;
    private String mobileNo1;
    private String mobileNo2;
    private String mobileNo3;
    private String emailAddr1;
    private String emailAddr2;
    private String zipNo;
    private String address;
    private String detailAddress;
    private String repName;
    private String repBirthDate;
    private String repGender;
    private String repRegistrationNo1;
    private String repRegistrationNo2;
    private String repForeignerNo1;
    private String repForeignerNo2;
    private Boolean repAgree;
    private String minorAgentNm;
    private String agentBirthDate;
    private String agentGender;
    private String minorAgentRelTypeCd;
    private String minorAgentTelFnNo;
    private String minorAgentTelMnNo;
    private String minorAgentTelRnNo;
    private String managerCd;
    private String managerNm;
    private String agentCd;
    private String ktOrgId;
    private String agentNm;
    private String shopCd;
    private String shopNm;
    private String realShopNm;
    private String cpntId;
    private String cpntNm;
    private String cntpntShopCd;
    private String cntpntShopNm;
    /** 선택된 서비스 타입 코드 목록 (R11/R12/P11/O11/O12/R14/O13/R15/R16) */
    private String memo;
    /** 이폼 전달 파라미터 JSON → MSF_REQUEST_SVC_CHG.SIGN_TGT_SBST */
    private String signTgtSbst;
    /** eformsign 신청서 document ID 목록 → MSF_REQUEST_SVC_CHG.SCAN_ID */
    private List<String> documentId = new ArrayList<>();
    private List<ImageSystemUploadFile> reportFiles = new ArrayList<>();
    private List<ImageSystemUploadFile> requiredDocFiles = new ArrayList<>();
    private List<String> serviceSelect = new ArrayList<>();
    private List<AdditionApplyReqDto> additionList = new ArrayList<>();
    private List<AdditionApplyReqDto> additionCancelList = new ArrayList<>();
    private List<Clause> clauses = new ArrayList<>();

    /** P11: 요금제변경 */
    private PlanChange planChange;
    /** O11: 번호변경 */
    private NumberChange numberChange;
    /** O12: 분실복구/일시정지해제 */
    private Unpause unpause;
    /** R14: 단말보험 */
    private Insurance insurance;
    /** O13: SIM정보 */
    private SimInfo simInfo;
    /** R15: 데이터쉐어링 */
    private DataSharing dataSharing;
    /** R16: 결합Solo */
    private CombineSolo combineSolo;

    @Getter @Setter @NoArgsConstructor
    public static class ImageSystemUploadFile {

        private String documentType;
        private String documentId;
        private String pathFileName;
        private String filePathNm;
        private String fileTypeCd;
        private Integer filePageNo;
    }

    @Getter @Setter @NoArgsConstructor
    public static class Clause {

        private String code;
        private String termsGroupCd;
        private String termsItemCd;
        private String cdGroupId;
        private String cdGroupId2;
        private Object checked;
        private String version;
    }

    @Getter @Setter @NoArgsConstructor
    public static class PlanChange {

        private String svcTgtCd;
        private String planCategoryCd; // 요금제 카테고리 코드
        private String planCd;         // 요금제 코드
        private String changeTypeCd;   // 변경일시: changeDate1(예약/익월1일), changeDate2(즉시)
        private String actCode;
        private String planSoc;
        private String beforePlanSoc;
        private String beforePlanAmt;
        private String planFtrNewParam;
        private String openingDate;
    }

    @Getter @Setter @NoArgsConstructor
    public static class NumberChange {

        private String svcTgtCd;
        private String reqWantFnNo; // 번호예약 앞 3자리
        private String reqWantMnNo; // 번호예약 가운데 4자리
        private String reqWantRnNo; // 번호예약 뒤 4자리
        private String wishNo;      // 선택된 희망 신규번호
        private String wishNoc;     // 선택된 희망 신규번호 암호화
        private String wishMarket;  // KT or KTF
    }

    @Getter @Setter @NoArgsConstructor
    public static class Unpause {

        private String svcTgtCd;
        private String unLockPw; // 일시정지 해제 비밀번호
    }

    @Getter @Setter @NoArgsConstructor
    public static class Insurance {

        private String svcTgtCd;
        private String clauseInsuranceYn; // 가입여부 (Y/N)
        private String catCd;             // 보험 카테고리 코드
        private String insrProdCd;        // 보험 상품 코드
    }

    @Getter @Setter @NoArgsConstructor
    public static class SimInfo {

        private String svcTgtCd;
        private boolean hasSim;       // SIM 보유 유형
        private String usimKindsCd;  // USIM 종류 코드
        private String reqUsimNm;    // USIM 모델명
        private String reqUsimSn;    // USIM 일련번호
        private String eid;          // eSIM EID
        private String imei1;        // IMEI1
        private String imei2;        // IMEI2
        private String simPurchaseMethod;  // 납부방법
        private String simTypeCd;  // sim타입 (ESIM, USIM)
    }

    @Getter @Setter @NoArgsConstructor
    public static class DataSharing {

        private String svcTgtCd;
        private String shareUseState; // shareUseState1(가입), shareUseState2(해지)
        private String sharePhoneNum; // 공유 휴대폰 번호
        private String shareUsimNum;  // 공유 USIM 번호
        private String dataSharingTargetNo;
        private Boolean dataSharingAuthCompleted;
        private Boolean dataSharingUsimCheckCompleted;
        private Boolean dataSharingAvailableChecked;
        private Boolean dataSharingAgreementCompleted;
        private Boolean dataSharingConfirmCompleted;
    }

    @Getter @Setter @NoArgsConstructor
    public static class CombineSolo {

        private String svcTgtCd;
        private String soloData; // 아무나SOLO 데이터 용량
    }
}
