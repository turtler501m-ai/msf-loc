package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Data
@NoArgsConstructor
public class MsfRequestNameChgVo {

    private String ncn;
    private String ctn;
    private String custId;
    private String userId;
    private String formTypeCd;
    private String parentScanId;
    private String fmc0Id;
    private String iselfFrmpapYn; // 자체서식지 사용여부
    private String disPrmtId; // 평생할인프로모션ID

    // 양도인 정보
    @NotNull
    private Long requestKey; // 가입신청키
    private Long nflChgTrnsSeq; // 명의변경신청일련번호
    private Long trnsSeq; // 명의변경일련번호
    private String cretIp; // 생성IP
    private String cretDt; // 생성일시
    private String cretId; // 생성자ID
    private String amdIp; // 수정IP
    private String amdDt; // 수정일시
    private String amdId; // 수정자ID

    private String userBirth;
    private String trCstmrJuridicalRrn1; // 양도인 법인번호 앞자리
    private String trCstmrJuridicalRrn2; // 양도인 법인번호 뒷자리
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String trCstmrNativeRrn; // 양도인 주민번호

    private String trIdentityCertTypeCd; // 신분증인증유형코드
    private String trnsCstmrTypeCd; // 양도인고객구분유형코드
    private String trnsTrnsfeMobileNo; // 양수인모바일번호
    private String trnsTrnsfeMobileNo1; // 양수인모바일번호
    private String trnsTrnsfeMobileNo2; // 양수인모바일번호
    private String trnsTrnsfeMobileNo3; // 양수인모바일번호

    private String trFathTrgYn; // 안면인증대상여부
    private String trFathTrgIdentityCertTypeCd; // 안면인증대상신분증유형코드
    private String trFathTransacId; // 안면인증트랜잭션ID
    private String trFathCmpltNtfyDate; // 안면인증완료일자
    private String trFathTelNo; // 안면인증URL전송전화번호
    private String trFathMobileFnNo; // 안면인증정보휴대폰번호앞자리번호
    private String trFathMobileMnNo; // 안면인증정보휴대폰번호중간자리번호
    private String trFathMobileRnNo; // 안면인증정보휴대폰번호뒷자리번호

    private String trAuthInfo; // 인증정보

    private String trIdentityTypeCd; // 신분증유형코드
    private String trIdentityIssuDate; // 신분증발급일자
    private String trIdentityIssuRegion; // 신분증발급지역
    private String trSelfIssuNo; // 발급번호
    private String trDriveLicnsNo; // 운전면허번호

    private String trnsNm; // 양도인명
    private String trnsMobileNo; // 명의변경대상모바일번호
    private String trnsMobileFnNo; // 명의변경대상모바일번호 앞자리
    private String trnsMobileMnNo; // 명의변경대상모바일번호 중간자리
    private String trnsMobileRnNo; // 명의변경대상모바일번호 끝자리
    private String trnsPhoneNo; // 명의자연락처번호
    private String trnsPwd; // 명의변경용비밀번호
    private String trnsMyslfConfMethCd; // 양도인본인확인방법코드
    private String trnsTrnsfeNm; // 양도인이입력한양수인명

    private String trnsBirth; // 양도인생년월일
    private String trnsGenderCd; // 양도인성별
    private String trnsCname; // 양도인상호명
    private String trnsBizNo; // 양도인사업자등록번호
    private String trnsJuridicalRrn; // 양도인법인번호
    private String trnsJuridicalRepNm; // 양도인법인대표자명

    private String trCstmrVisitTypeCd; // 방문고객유형코드

    private String trMinorAgentNm; // 미성년자법정대리인성명
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String trMinorAgentRrn; // 미성년자법정대리인등록번호
    private String trMinorAgentBirth; // 미성년자법정대리인생년월일
    private String trMinorAgentGenderCd; // 미성년자법정대리인성별
    private String trMinorAgentRelTypeCd; // 미성년자법정대리인관계유형코드
    private String trMinorAgentTelFnNo; // 미성년자법정대리인연락처앞자리번호
    private String trMinorAgentTelMnNo; // 미성년자법정대리인연락처중간자리번호
    private String trMinorAgentTelRnNo; // 미성년자법정대리인끝자리번호
    private String trMinorAgentTelNo; // 미성년자법정대리인번호

    private String trMinorAgentAgrmYn; // 미성년자법정대리인안내사항및동의여부
    private String trMinorAgentSelfInqryAgrmYn; // 미성년자법정대리인본인인증조회동의여부
    private String trMinorAgentSelfCertTypeCd; // 미성년자법정대리인본인인증유형코드
    private String trMinorAgentSelfIssuExprDate; // 미성년자법정대리인발급/만료일자
    private String trMinorAgentSelfIssuNo; // 미성년자법정대리인발급번호

    private String trnsSttusCd; // 처리상태코드
    private String authDelYn; // 개인정보삭제여부
    private String confirmMemo; // 처리메모

    //양수인 정보

    @NotNull
    private String managerCd; // 매니저코드
    private String managerNm; // 매니저명

    @NotNull
    private String agentCd; // 대리점코드
    private String agentNm; // 대리점명

    private String shopCd; // 판매점코드
    private String shopNm; // 판매점명
    private String realShopNm; // 실판매점명
    private String cpntId; // 접점ID
    private String cpntNm; // 접점명
    private String cntpntShopCd; // 채널판매점코드
    private String cntpntShopNm; // 채널판매점명

    private String operTypeCd; // 업무구분유형코드
    private String cstmrTypeCd; // 고객구분유형코드
    private String cstmrType; // 고객구분유형
    private String mcnResNo; // 명의변경예약번호

    private String teCstmrJuridicalRrn1; //양수인 법인번호 앞자리
    private String teCstmrJuridicalRrn2; //양수인 법인번호 뒷자리

    private String teIdentityCertTypeCd; // 신분증인증유형코드
    private String knoteIdentityScanCstmrNm; // KNOTE신분증고객명
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String knoteIdentityEssNo; // KNOTE신분증식별번호
    private String knoteIdentityTypeCd; // KNOTE신분증유형코드

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime knoteIdentityScanDt; // KNOTE신분증스캔일시

    private String knoteScanId; // KNOTE신분증스캔번호

    private String teFathTrgYn; // 안면인증대상여부
    private String teFathTrgIdentityCertTypeCd; // 안면인증대상신분증유형코드
    private String teFathTransacId; // 안면인증트랜잭션ID
    private String teFathCmpltNtfyDate; // 안면인증완료일자
    private String teFathTelNo; // 안면인증URL전송전화번호
    private String teFathMobileFnNo; // 안면인증정보휴대폰번호앞자리번호
    private String teFathMobileMnNo; // 안면인증정보휴대폰번호중간자리번호
    private String teFathMobileRnNo; // 안면인증정보휴대폰번호뒷자리번호

    private String teAuthInfo; // 인증정보

    private String teIdentityTypeCd; // 신분증유형코드
    private String teIdentityIssuDate; // 신분증발급일자
    private String teIdentityIssuRegion; // 신분증발급지역
    private String teSelfIssuNo; // 발급번호
    private String teDriveLicnsNo; // 운전면허번호

    private String reqInfoChgYn; // 가입정보변경여부

    private String soc; // 요금제
    private String socNm; // 요금제명
    private Long socBaseChrgAmt; // 요금제기본료
    private String planSelectType; // 요금제 선택 타입 CURRENT, CHANGE

    private String jehuProdTypeCd; // 요금제제휴처코드
    private String usimSuccYn; // USIM승계여부
    private String simTypeCd; // ESIM / USIM
    private String simPurchaseMethod; // USIM 구매방식
    private String usimPriceTypeCd; // USIM 납부유형코드
    private String usimPayMthdCd; // 유심비 납부방법코드
    private String usimKindCd; // 유심비 선택 종류
    private String plcySctnCd; // 정책코드
    private String prdtSctnCd;
    private String dataType; // 데이터종류(LTE, 5G)
    private Long usimPrice; // USIM 가격
    private String usimNm;
    private String usimSn; // USIM번호
    private String iccId; // ICCID
    private String eid; // EID
    private String imei1; // IMEI1
    private String imei2; // IMEI2
    private String esimPhoneId; // eSIM휴대폰모델ID

    private Long uploadPhoneSrlNo; // 업로드휴대폰일련번호

    private String remainPayDivCd; // 완납/승계구분코드

    private String clauseCntrDelYn; // 양도인고객정보삭제동의여부
    private String clausePriCollectYn; // 약관개인정보수집동의여부
    private String clausePriOfferYn; // 약관개인정보제공동의여부
    private String clauseEssCollectYn; // 약관고유식별정보수집이용제공동의여부
    private String clauseConfidenceYn; // 약관신용정보이용동의여부
    private String clausePriAdYn; // 약관개인정보광고전송동의여부
    private String clausePriTrustYn; // 위탁동의여부
    private String clauseJehuYn; // 제휴서비스동의여부
    private String clauseFinanceYn; // 금융제휴약관동의여부
    private String cnsgInfoAdvrRcvAgreYn; // 금융제휴약관동의여부

    private String personalInfoCollectAgreeYn; // 고객혜택제공을위한개인정보수집및이용관련동의여부
    private String othersTrnsAgreeYn; // 혜택제공을위한제3자제공동의(M모바일)여부
    private String othersTrnsKtAgreeYn; // 혜택제공을위한제3자제공동의(KT)여부
    private String othersTrnsAllAgreeYn; // 혜택제공을위한제3자제공동의(KT)여부
    private String othersAdReceiveAgreeYn; // 제3자제공관련광고수신동의여부
    private String indvLocaPrvAgreeYn; // 개인위치정보 제 3자 제공 동의
    private String clause5gCoverageYn; // 5G커버리지확인및가입동의여부
    private String nwBlckAgrmYn; // 네트워크차단동의여부
    private String appBlckAgrmYn; // 청소년유해매체차단동의여부

    private String clauseFathYn; // 안면인증동의여부
    private String mcnStatRsnCd; // 명변사유코드 CRMCN: 가족간 ,RCMCMCN: 실사용자를 위한 명변
    private String memo; // 메모

    private String regDate; // 등록일자
    private String regstId; // 등록자ID
    private String regDt; // 등록일시
    private String procDt; // 처리일시
    private String procCd; // 처리결과

    private String mcnStateCd; // 진행상태코드
    private String rcvCustNo; // 양수인고객번호
    private String rcvBillAcntNo; // 양수인청구번호

    private String recYn; // 녹취여부
    private String resCd; // 예약등록코드
    private String resMsg; // 예약등록메세지

    private String scanId; // 스캐너ID
    private String appFormYn; // 스캔이미지여부
    private String appFormXmlYn; // 서식지XML여부

    private String fileNm; // 파일명
    private String fileMaskNm; // 마스크파일명

    private String reqType; //신청유형

    //MSF_REQUEST_CSTMR
    private String cstmrNm;                      // 고객명
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String cstmrNativeRrn;               // 고객정보내국인주민등록번호
    private String cstmrNativeBirth;             // 고객정보내국인생년월일
    private String cstmrNativeGenderCd;          // 고객정보내국인성별
    private String cstmrPrivateCname;            // 고객정보개인사업자상호명
    private String cstmrPrivateBizNo;            // 개인사업자사업자등록번호
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    private String cstmrForeignerRrn;            // 고객정보외국인외국인등록번호
    private String cstmrForeignerBirth;          // 고객정보외국인생년월일
    private String cstmrForeignerGenderCd;       // 고객정보외국인성별
    private String cstmrForeignerPn;             // 고객정보외국인여권번호
    private String cstmrForeignerCountryCd;      // 고객정보외국인국가코드
    private String cstmrForeignerNation;         // 고객정보외국인국적
    private String cstmrForeignerVisaNo;         // 고객정보외국인비자번호
    private String cstmrForeignerVdateStartDate; // 고객정보외국인체류기간시작일자
    private String cstmrForeignerVdateEndDate;   // 고객정보외국인체류기간종료일자
    private String cstmrJuridicalCname;          // 고객정보법인사업자법인명
    private String cstmrJuridicalRrn;            // 고객정보법인사업자법인번호
    private String cstmrJuridicalBizNo;          // 고객정보법인사업자사업자등록번호
    private String cstmrJuridicalRepNm;          // 고객정보법인대표자명
    private String cstmrJuridicalBizNoIssuDate;  // 교부일자
    private String cstmrPrivateBizNoIssuDate;    // 사업자 발급일자
    private String upjnCd;                       // 업종코드
    private String bcuSbst;                      // 업태내용
    private String cstmrJuridicalUserNm;         // 법인실사용자명
    private String cstmrJuridicalBirth;          // 법인실사용자생년월일
    private String cstmrVisitTypeCd;             // 방문고객유형코드
    private String cstmrTelFnNo;                 // 고객정보전화번호앞자리번호
    private String cstmrTelMnNo;                 // 고객정보전화번호가운데자리번호
    private String cstmrTelRnNo;                 // 고객정보전화번호끝자리번호
    private String cstmrTelNo;                    // 고객정보전화번호
    private String cstmrMobileFnNo;              // 고객정보휴대폰번호앞자리번호
    private String cstmrMobileMnNo;              // 고객정보휴대폰번호중간자리번호
    private String cstmrMobileRnNo;              // 고객정보휴대폰번호끝자리번호
    private String cstmrMobileNo;              // 고객정보휴대폰번호끝자리번호
    private String cstmrZipcd;                   // 고객정보우편번호
    private String cstmrAdr;                     // 고객정보주소
    private String cstmrAdrDtl;                  // 고객정보상세주소
    private String cstmrAdrBjd;                  // 고객정보법정동주소
    private String cstmrEmailAdr;                // 고객정보이메일
    private String cstmrEmailReceiveYn;          // 고객정보이메일수신여부
    private String cstmrReceiveTelNo;          // 고객정보연락받을번호
    private String cstmrReceiveTelFnNo;          // 고객정보연락받을번호앞자리번호
    private String cstmrReceiveTelNmNo;          // 고객정보연락번호중간자리번호
    private String cstmrReceiveTelRnNo;          // 고객정보연락받을번호끝자리번호

    //MSF_REQUEST_BILL_REQ
    private String reqPayTypeCd;           // 요금납부방법유형코드
    private String reqBankCd;              // 신청정보계좌이체은행코드
    private String reqAccountNm;           // 계좌예금주명
    private String reqAccountRrn;          // 신청정보계좌이체예금주주민번호
    private String reqAccountRelTypeCd;    // 신청정보계좌이체예금주와관계유형코드
    private String reqAccountNo;           // 계좌번호
    private String reqCardNm;              // 신용카드명의자명
    private String reqCardRrn;             // 신청정보신용카드명의자주민번호
    private String reqCardCompanyCd;       // 신청정보신용카드카드사코드
    private String reqCardNo;              // 신용카드번호
    private String reqCardYy;              // 신청정보신용카드유효년
    private String reqCardMm;              // 신청정보신용카드유효월
    private String reqWireTypeCd;          // 무선데이터이용타입유형코드
    private String othersPaymentYn;        // 타인납부여부
    private String othersPaymentAgrYn;     // 타인납부동의여부
    private String othersPaymentTelFnNo;   // 타인납부전화번호앞자리번호
    private String othersPaymentTelMnNo;   // 타인납부전화번호중간자리번호
    private String othersPaymentTelRnNo;   // 타인납부전화번호끝자리번호
    private String othersPaymentNm;        // 타인납부고객명
    private String othersPaymentRrn;       // 타인납부주민번호
    private String othersPaymentRelTypeCd; // 타인납부명의자와의관계유형코드
    private String othersPaymentReqNm;     // 타인납부신청인명
    private String prntsBillNo;            // 모회선청구번호
    private String cstmrBillSendTypeCd;    // 명세서종류유형코드

    //MSF_REQUEST_AGENT
    private String minorAgentNm;              // 미성년자법정대리인성명
    private String minorAgentRrn;             // 미성년자법정대리인등록번호
    private String minorAgentBirth;           // 미성년자법정대리인생년월일
    private String minorAgentGenderCd;        // 미성년자법정대리인성별
    private String minorAgentRelTypeCd;       // 미성년자법정대리인관계유형코드
    private String minorAgentTelFnNo;         // 미성년자법정대리인연락처앞자리번호
    private String minorAgentTelMnNo;         // 미성년자법정대리인연락처중간자리번호
    private String minorAgentTelRnNo;         // 미성년자법정대리인끝자리번호
    private String minorAgentTelNo;         // 미성년자법정대리인끝자리번호
    private String minorAgentAgrmYn;          // 미성년자법정대리인안내사항및동의여부
    private String minorAgentSelfInqryAgrmYn; // 미성년자법정대리인본인인증조회동의여부
    private String minorAgentSelfCertTypeCd;  // 미성년자법정대리인본인인증유형코드
    private String minorAgentCiInfo;             // 미성년자법정대리인CI정보
    private String jrdclAgentNm;                 // 법인대리인명
    private String jrdclAgentGender;                 // 법인대리인성별
    private String jrdclAgentRrn;                // 법인대리인등록번호
    private String jrdclAgentRelTypeCd;          // 법정대리인관계유형코드
    private String jrdclAgentTelFnNo;            // 법인대리인연락처앞자리번호
    private String jrdclAgentTelMnNo;            // 법인대리인연락처중간자리번호
    private String jrdclAgentTelRnNo;            // 법인대리인연락처끝자리번호
    private String jrdclAgentTelNo;            // 법인대리인연락처번호

    //MSF_REQUEST_DOC
    private List<RequestDocList> msfRequestDocList;

    //MSF_REQUEST_REC
    private String recFilePathNm;
    private String recFileNm;

    @Data
    public static class RequestDocList {

        private String fileNm;
        private String filePageNo;
        private String filePathNm;
        private String fileTypeCd;
        private String maskImageFile;
        private String previewUrl;
    }

    public void setupNameChgData() {
        List<String> minors = List.of("NM", "FM");
        //String reqPayTypeCd = this.reqPayTypeCd;
        this.regstId = AuthenticationUtils.getUser().getUserId();

        this.formTypeCd = "3"; // 1: 신규/변경, 2: 서비스변경, 3: 명의변경, 4: 서비스해지
        this.procCd = "RQ"; // RQ: 신청, CP: 처리, BK: 반려, CL01: 공통코드
        // 실사용자 명의 변경: RCMCMCN, 가족 간 승계: CRMCN
        // 실사용자를 위한 명의변경만 유심 승계 가능
        this.mcnStatRsnCd = "Y".equals(this.usimSuccYn) ? "RCMCMCN" : "CRMCN";

        // 고객 유형 타입 변경
        this.cstmrType = switch (this.cstmrTypeCd) {
            case "FM", "GO" -> "NE";
            case String s when !"JP".equals(s) && StringUtils.hasText(this.cstmrPrivateBizNo) -> "PP";
            default -> this.cstmrTypeCd;
        };
        // 방문타입 처리
        this.trCstmrVisitTypeCd = switch (this.trnsCstmrTypeCd) {
            case "FM", "NM" -> "VCD";
            default -> "VMY";
        };

        this.cstmrVisitTypeCd = switch (this.cstmrTypeCd) {
            case "FM", "NM" -> "VCD";
            default -> this.cstmrVisitTypeCd;
        };


        if ("CURRENT".equals(this.planSelectType)) {
            this.soc = null;
            this.socNm = null;
            this.socBaseChrgAmt = null;
        }

        setupFrmpapData();
        setupPaymentData(); // 납부정보
        setupAgreeInfo(); // 동의정보
        setupUsimInfo(); // 유심정보
        setupIdentityInfo(); // 인증 신분증에 따라 처리
        setupGoverInfo(); // 법인, 공공
        setupForeigner(); // 외국인인 경우

        if (!minors.contains(this.trnsCstmrTypeCd)) {
            clearTrMinorAgentFields();
        }
        if (!minors.contains(this.cstmrTypeCd)) {
            clearTeMinorAgentFields();
        }

    }

    public void setupFrmpapData() {
        if (StringUtils.hasText(this.knoteScanId)) {
            this.iselfFrmpapYn = null;
            this.fmc0Id = this.knoteScanId;
            return;
        }
        this.iselfFrmpapYn = "Y";
        this.fmc0Id = this.teFathTransacId;
    }

    public void setupGoverInfo() {
        List<String> government = List.of("GO", "JP");

        if (!government.contains(this.trnsCstmrTypeCd)) {
            this.trnsCname = null;
            this.cstmrJuridicalBizNo = null;
        }

        // 법인, 공공 기관인 경우 개인 사업자 번호 삭제
        if (government.contains(this.cstmrTypeCd)) {
            this.cstmrJuridicalBizNo = this.cstmrPrivateBizNo;
            this.cstmrPrivateBizNo = null;
        }
    }

    public void setupForeigner() {
        List<String> foreigner = List.of("FN", "FM");

        if (foreigner.contains(this.cstmrTypeCd)) {
            this.cstmrNativeRrn = this.cstmrForeignerRrn;
        }
    }

    public void setupIdentityInfo() {
        if ("04".equals(this.teIdentityTypeCd)) { // 국가유공자
            this.teDriveLicnsNo = null;
        }
    }

    public void setupUsimInfo() {

        if ("Y".equals(this.usimSuccYn)) {
            this.usimPriceTypeCd = "N";
            this.usimPayMthdCd = "1"; // 유심비 납부방법코드 면제

            if ("ESIM".equals(this.simTypeCd)) {
                this.eid = "ESIM";
            }

            return;
        }

        this.usimPriceTypeCd = this.simPurchaseMethod; // USIM 납부유형코드
        this.usimPayMthdCd = "B".equals(this.simPurchaseMethod) ? "3" : "2"; // 유심비 납부방법코드

        if ("01".equals(this.usimKindCd)) { //일반유심 - 스마트 화면에서 값
            if ("LTE".equals(this.prdtSctnCd)) {
                this.usimKindCd = "02"; // RCP2035 기준코드
            } else {
                this.usimKindCd = "07"; // RCP2035 기준코드
            }
        } else if ("02".equals(this.usimKindCd)) { //NFC유심 - 스마트 화면에서 값
            this.usimKindCd = "08"; // RCP2035 기준코드
        }

        this.dataType = StringUtil.NVL(this.dataType, "LTE"); //요금제 조회에서 리턴된 데이터유형 LTE / 5G 등
        // this.operTypeCd = StringUtil.NVL(this.operTypeCd, "NAC3");

    }

    public void setupAgreeInfo() {
        this.clauseCntrDelYn = "Y"; // 양도인고객정보삭제동의여부 (필수)
        this.clausePriCollectYn = "Y"; // 약관개인정보수집동의여부 (필수)
        this.clausePriOfferYn = "Y"; // 약관개인정보제공동의여부 (필수)
        this.clauseEssCollectYn = nvl(this.clauseEssCollectYn); // 약관고유식별정보수집이용제공동의여부
        this.clauseConfidenceYn = nvl(this.clauseConfidenceYn); // 약관신용정보이용동의여부
        this.clauseJehuYn = nvl(this.clauseJehuYn); // 제휴서비스동의여부
        this.clauseFinanceYn = nvl(this.clauseFinanceYn); // 금융제휴약관동의여부
        /* 고객 혜택 제공 */
        this.cnsgInfoAdvrRcvAgreYn = nvl(this.personalInfoCollectAgreeYn); // 위탁정보
        this.personalInfoCollectAgreeYn = nvl(this.personalInfoCollectAgreeYn); // 고객혜택제공을위한개인정보수집및이용관련동의여부
        this.clausePriAdYn = nvl(this.clausePriAdYn); // 약관개인정보광고전송동의여부
        this.clausePriTrustYn = nvl(this.clausePriTrustYn); // 위탁동의여부
        /* 고객 혜택 제공 */
        /* 혜택 제공 */
        this.othersTrnsAgreeYn = nvl(this.othersTrnsAllAgreeYn); // 혜택제공을위한제3자제공동의(M모바일)여부
        this.othersTrnsKtAgreeYn = nvl(this.othersTrnsAllAgreeYn); // 혜택제공을위한제3자제공동의(KT)여부
        this.othersAdReceiveAgreeYn = nvl(this.othersAdReceiveAgreeYn); // 제3자제공관련광고수신동의여부
        /* 혜택 제공 */
        this.indvLocaPrvAgreeYn = nvl(this.indvLocaPrvAgreeYn); // 개인위치정보 제 3자 제공 동의
    }

    public String nvl(String s) {
        return StringUtils.hasText(s) ? s : "N";
    }

    public void setupPaymentData() {
        String reqPayTypeCd = this.reqPayTypeCd;
        if (reqPayTypeCd == null) {
            return;
        }

        boolean isForeigner = List.of("FN", "FM").contains(this.cstmrTypeCd);
        boolean isGovernment = List.of("JP", "GO").contains(this.cstmrTypeCd);

        // 타인납부인 경우
        if ("Y".equals(this.othersPaymentYn)) {
            this.reqCardNm = this.othersPaymentNm;
            this.reqAccountNm = this.othersPaymentNm;
            this.reqCardRrn = this.othersPaymentRrn;
            this.reqAccountRrn = this.othersPaymentRrn;
            this.othersPaymentAgrYn = "Y";
        } else { // 본인 납부인 경우 (법인은 실사용자 정보)
            this.reqCardNm = isGovernment ? this.cstmrJuridicalUserNm : this.cstmrNm;
            this.reqCardRrn = isForeigner ? cstmrForeignerBirth : isGovernment ? this.cstmrJuridicalBirth : this.cstmrNativeBirth;
            this.reqAccountNm = isGovernment ? this.cstmrJuridicalUserNm : this.cstmrNm;
            this.reqAccountRrn = isForeigner ? cstmrForeignerBirth : isGovernment ? this.cstmrJuridicalBirth : this.cstmrNativeBirth;
            this.othersPaymentReqNm = null;
            this.othersPaymentNm = null;
            this.othersPaymentRrn = null;
            this.othersPaymentRelTypeCd = null;
            this.othersPaymentYn = "N";
            this.othersPaymentAgrYn = "N";
        }

        switch (reqPayTypeCd) {
            case "D" -> // 자동이체
                clearCardPaymentFields(); // 자동이체면 카드 관련 필드 초기화
            case "C" -> {// 신용카드
                clearBankTransferFields(); // 자동이체면 계좌 관련 필드 초기화
            }
            case "0" -> {// 통합청구 (명의변경 사용안함)
                clearBankTransferFields();
                clearCardPaymentFields();
            }
            default -> {
                // 정의되지 않은 코드가 들어오면 안전하게 다 지우거나 예외 처리
                clearCardPaymentFields();
                clearBankTransferFields();
            }
        }
    }

    public void clearBankTransferFields() {
        this.reqBankCd = null;              // 신청정보계좌이체은행코드
        this.reqAccountNm = null;           // 계좌예금주명
        this.reqAccountRrn = null;          // 신청정보계좌이체예금주주민번호
        this.reqAccountRelTypeCd = null;    // 신청정보계좌이체예금주와관계유형코드
        this.reqAccountNo = null;           // 계좌번호
    }

    public void clearCardPaymentFields() {
        this.reqCardNm = null;              // 신용카드명의자명
        this.reqCardRrn = null;             // 신청정보신용카드명의자주민번호
        this.reqCardCompanyCd = null;       // 신청정보신용카드카드사코드
        this.reqCardNo = null;              // 신용카드번호
        this.reqCardYy = null;              // 신청정보신용카드유효년
        this.reqCardMm = null;              // 신청정보신용카드유효월
    }

    public void clearTrMinorAgentFields() {
        this.trMinorAgentNm = null; // 미성년자법정대리인성명
        this.trMinorAgentRrn = null; // 미성년자법정대리인등록번호
        this.trMinorAgentBirth = null; // 미성년자법정대리인생년월일
        this.trMinorAgentGenderCd = null; // 미성년자법정대리인성별
        this.trMinorAgentRelTypeCd = null; // 미성년자법정대리인관계유형코드
        this.trMinorAgentTelFnNo = null; // 미성년자법정대리인연락처앞자리번호
        this.trMinorAgentTelMnNo = null; // 미성년자법정대리인연락처중간자리번호
        this.trMinorAgentTelRnNo = null; // 미성년자법정대리인끝자리번호
        this.trMinorAgentTelNo = null; // 미성년자법정대리인번호
        this.trMinorAgentAgrmYn = "N";          // 미성년자법정대리인안내사항및동의여부
        this.trMinorAgentSelfInqryAgrmYn = "N"; // 미성년자법정대리인본인인증조회동의여부
        this.trMinorAgentSelfCertTypeCd = null; // 미성년자법정대리인본인인증유형코드
        this.trMinorAgentSelfIssuExprDate = null; // 미성년자법정대리인발급/만료일자
        this.trMinorAgentSelfIssuNo = null; // 미성년자법정대리인발급번호
    }

    public void clearTeMinorAgentFields() {
        this.minorAgentNm = null;              // 미성년자법정대리인성명
        this.minorAgentRrn = null;             // 미성년자법정대리인등록번호
        this.minorAgentBirth = null;           // 미성년자법정대리인생년월일
        this.minorAgentGenderCd = null;        // 미성년자법정대리인성별
        this.minorAgentRelTypeCd = null;       // 미성년자법정대리인관계유형코드
        this.minorAgentTelFnNo = null;         // 미성년자법정대리인연락처앞자리번호
        this.minorAgentTelMnNo = null;         // 미성년자법정대리인연락처중간자리번호
        this.minorAgentTelRnNo = null;         // 미성년자법정대리인끝자리번호
        this.minorAgentTelNo = null;         // 미성년자법정대리인번호
        this.minorAgentAgrmYn = "N";          // 미성년자법정대리인안내사항및동의여부
        this.minorAgentSelfInqryAgrmYn = "N"; // 미성년자법정대리인본인인증조회동의여부
        this.minorAgentSelfCertTypeCd = null;  // 미성년자법정대리인본인인증유형코드
        this.minorAgentCiInfo = null;             // 미성년자법정대리인CI정보
    }
}
