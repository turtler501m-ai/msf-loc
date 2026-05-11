package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestNameChgVo {

    // 양도인 정보
    private Long requestKey; // 가입신청키
    private Long nflChgTrnsSeq; // 명의변경신청일련번호
    private Long trnsSeq; // 명의변경일련번호
    private String cretIp; // 생성IP
    private String cretDt; // 생성일시
    private String cretId; // 생성자ID
    private String amdIp; // 수정IP
    private String amdDt; // 수정일시
    private String amdId; // 수정자ID

    private String trnsCstmrTypeCd; // 양도인고객구분유형코드
    private String trnsTrnsfeMobileNo; // 양수인모바일번호

    private String trIdentityCertTypeCd; // 신분증인증유형코드
    private String trKnoteIdentityScanCstmrNm; // KNOTE신분증고객명
    private String trKnoteIdentityEssNo; // KNOTE신분증식별번호
    private String trKnoteIdentityTypeCd; // KNOTE신분증유형코드

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime trKnoteIdentityScanDt; // KNOTE신분증스캔일시

    private String trKnoteScanId; // KNOTE신분증스캔번호

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
    private String trnsPhoneNo; // 명의자연락처번호
    private String trnsPwd; // 명의변경용비밀번호
    private String trnsMyslfConfMethCd; // 양도인본인확인방법코드
    private String trnsTrnsfeNm; // 양도인이입력한양수인명

    private String trCstmrVisitTypeCd; // 방문고객유형코드

    private String trMinorAgentNm; // 미성년자법정대리인성명
    private String trMinorAgentRrn; // 미성년자법정대리인등록번호
    private String trMinorAgentBirth; // 미성년자법정대리인생년월일
    private String trMinorAgentGenderCd; // 미성년자법정대리인성별
    private String trMinorAgentRelTypeCd; // 미성년자법정대리인관계유형코드
    private String trMinorAgentTelFnNo; // 미성년자법정대리인연락처앞자리번호
    private String trMinorAgentTelMnNo; // 미성년자법정대리인연락처중간자리번호
    private String trMinorAgentTelRnNo; // 미성년자법정대리인끝자리번호

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
    private String mcnResNo; // 명의변경예약번호

    private String teIdentityCertTypeCd; // 신분증인증유형코드
    private String teKnoteIdentityScanCstmrNm; // KNOTE신분증고객명
    private String teKnoteIdentityEssNo; // KNOTE신분증식별번호
    private String teKnoteIdentityTypeCd; // KNOTE신분증유형코드

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime teKnoteIdentityScanDt; // KNOTE신분증스캔일시

    private String teKnoteScanId; // KNOTE신분증스캔번호

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

    private String jehuProdTypeCd; // 요금제제휴처코드
    private String usimSuccYn; // USIM승계여부
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
    private String clauseJehuYn; // 제휴서비스동의여부
    private String clauseFinanceYn; // 금융제휴약관동의여부

    private String personalInfoCollectAgreeYn; // 고객혜택제공을위한개인정보수집및이용관련동의여부
    private String othersTrnsAgreeYn; // 혜택제공을위한제3자제공동의(M모바일)여부
    private String othersTrnsKtAgreeYn; // 혜택제공을위한제3자제공동의(KT)여부
    private String othersAdReceiveAgreeYn; // 제3자제공관련광고수신동의여부

    private String clauseFathYn; // 안면인증동의여부
    private String mcnStatRsnCd; // 명변사유코드
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

    //MSF_REQUEST_CSTMR
    String cstmrNm;                      // 고객명
    String cstmrNativeRrn;               // 고객정보내국인주민등록번호
    String cstmrNativeBirth;             // 고객정보내국인생년월일
    String cstmrNativeGenderCd;          // 고객정보내국인성별
    String cstmrPrivateCname;            // 고객정보개인사업자상호명
    String cstmrPrivateBizNo;            // 개인사업자사업자등록번호
    String cstmrForeignerRrn;            // 고객정보외국인외국인등록번호
    String cstmrForeignerBirth;          // 고객정보외국인생년월일
    String cstmrForeignerGenderCd;       // 고객정보외국인성별
    String cstmrForeignerPn;             // 고객정보외국인여권번호
    String cstmrForeignerCountryCd;      // 고객정보외국인국가코드
    String cstmrForeignerNation;         // 고객정보외국인국적
    String cstmrForeignerVisaNo;         // 고객정보외국인비자번호
    String cstmrForeignerVdateStartDate; // 고객정보외국인체류기간시작일자
    String cstmrForeignerVdateEndDate;   // 고객정보외국인체류기간종료일자
    String cstmrJuridicalCname;          // 고객정보법인사업자법인명
    String cstmrJuridicalRrn;            // 고객정보법인사업자법인번호
    String cstmrJuridicalBizNo;          // 고객정보법인사업자사업자등록번호
    String cstmrJuridicalRepNm;          // 고객정보법인대표자명
    String upjnCd;                       // 업종코드
    String bcuSbst;                      // 업태내용
    String cstmrJuridicalUserNm;         // 법인실사용자명
    String cstmrJuridicalBirth;          // 법인실사용자생년월일
    String cstmrVisitTypeCd;             // 방문고객유형코드
    String cstmrTelFnNo;                 // 고객정보전화번호앞자리번호
    String cstmrTelMnNo;                 // 고객정보전화번호가운데자리번호
    String cstmrTelRnNo;                 // 고객정보전화번호끝자리번호
    String cstmrMobileFnNo;              // 고객정보휴대폰번호앞자리번호
    String cstmrMobileMnNo;              // 고객정보휴대폰번호중간자리번호
    String cstmrMobileRnNo;              // 고객정보휴대폰번호끝자리번호
    String cstmrZipcd;                   // 고객정보우편번호
    String cstmrAdr;                     // 고객정보주소
    String cstmrAdrDtl;                  // 고객정보상세주소
    String cstmrAdrBjd;                  // 고객정보법정동주소
    String cstmrEmailAdr;                // 고객정보이메일
    String cstmrEmailReceiveYn;          // 고객정보이메일수신여부
    String cstmrReceiveTelFnNo;          // 고객정보연락받을번호앞자리번호
    String cstmrReceiveTelNmNo;          // 고객정보연락번호중간자리번호
    String cstmrReceiveTelRnNo;          // 고객정보연락받을번호끝자리번호

    //MSF_REQUEST_BILL_REQ
    String reqPayTypeCd;           // 요금납부방법유형코드
    String reqBankCd;              // 신청정보계좌이체은행코드
    String reqAccountNm;           // 계좌예금주명
    String reqAccountRrn;          // 신청정보계좌이체예금주주민번호
    String reqAccountRelTypeCd;    // 신청정보계좌이체예금주와관계유형코드
    String reqAccountNo;           // 계좌번호
    String reqCardNm;              // 신용카드명의자명
    String reqCardRrn;             // 신청정보신용카드명의자주민번호
    String reqCardCompanyCd;       // 신청정보신용카드카드사코드
    String reqCardNo;              // 신용카드번호
    String reqCardYy;              // 신청정보신용카드유효년
    String reqCardMm;              // 신청정보신용카드유효월
    String reqWireTypeCd;          // 무선데이터이용타입유형코드
    String othersPaymentYn;        // 타인납부여부
    String othersPaymentTelFnNo;   // 타인납부전화번호앞자리번호
    String othersPaymentTelMnNo;   // 타인납부전화번호중간자리번호
    String othersPaymentTelRnNo;   // 타인납부전화번호끝자리번호
    String othersPaymentNm;        // 타인납부고객명
    String othersPaymentRrn;       // 타인납부주민번호
    String othersPaymentRelTypeCd; // 타인납부명의자와의관계유형코드
    String othersPaymentReqNm;     // 타인납부신청인명
    String prntsBillNo;            // 모회선청구번호
    String cstmrBillSendTypeCd;    // 명세서종류유형코드

    //MSF_REQUEST_AGENT
    String minorAgentNm;              // 미성년자법정대리인성명
    String minorAgentRrn;             // 미성년자법정대리인등록번호
    String minorAgentBirth;           // 미성년자법정대리인생년월일
    String minorAgentGenderCd;        // 미성년자법정대리인성별
    String minorAgentRelTypeCd;       // 미성년자법정대리인관계유형코드
    String minorAgentTelFnNo;         // 미성년자법정대리인연락처앞자리번호
    String minorAgentTelMnNo;         // 미성년자법정대리인연락처중간자리번호
    String minorAgentTelRnNo;         // 미성년자법정대리인끝자리번호
    String minorAgentAgrmYn;          // 미성년자법정대리인안내사항및동의여부
    String minorAgentSelfInqryAgrmYn; // 미성년자법정대리인본인인증조회동의여부
    String minorAgentSelfCertTypeCd;  // 미성년자법정대리인본인인증유형코드
    String minorAgentCiInfo;             // 미성년자법정대리인CI정보
    String jrdclAgentNm;                 // 법인대리인명
    String jrdclAgentRrn;                // 법인대리인등록번호
    String jrdclAgentRelTypeCd;          // 법정대리인관계유형코드
    String jrdclAgentTelFnNo;            // 법인대리인연락처앞자리번호
    String jrdclAgentTelMnNo;            // 법인대리인연락처중간자리번호
    String jrdclAgentTelRnNo;            // 법인대리인연락처끝자리번호

    //MSF_REQUEST_DOC
    String fileTypeCd;
    String filePathNm;

}
