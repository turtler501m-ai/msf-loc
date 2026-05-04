package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestNameChgVo {

    private TrCustomerInfo trCustomerInfo;
    private TeCustomerInfo teCustomerInfo;

    @Data
    public static class TrCustomerInfo {

        private Long requestKey;                  // 가입신청키
        private String cretIp;                      // 생성IP
        private String cretDt;                      // 생성일시
        private String cretId;                      // 생성자ID
        private String amdIp;                       // 수정IP
        private String amdDt;                       // 수정일시
        private String amdId;                       // 수정자ID
        private String managerCd;                   // 매니저코드
        private String managerNm;                   // 매니저명
        private String agentCd;                     // 대리점코드
        private String agentNm;                     // 대리점명
        private String shopCd;                      // 판매점코드
        private String shopNm;                      // 판매점명
        private String realShopNm;                  // 실판매점명
        private String cpntId;                      // 접점ID
        private String cpntNm;                      // 접점명
        private String cntpntShopCd;                // 채널판매점코드
        private String cntpntShopNm;                // 채널판매점명
        private String operTypeCd;                  // 업무구분유형코드
        private String cstmrTypeCd;                 // 고객구분유형코드
        private String mcnResNo;                    // 명의변경예약번호
        private String identityCertTypeCd;          // 신분증인증유형코드
        private String knoteIdentityScanCstmrNm;    // KNOTE신분증고객명
        private String knoteIdentityEssNo;          // KNOTE신분증식별번호
        private String knoteIdentityTypeCd;         // KNOTE신분증유형코드
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime knoteIdentityScanDt;         // KNOTE신분증스캔일시
        private String knoteScanId;                 // KNOTE신분증스캔번호
        private String fathTrgYn;                   // 안면인증대상여부
        private String fathTrgIdentityCertTypeCd;   // 안면인증대상신분증유형코드
        private String fathTransacId;               // 안면인증트랜잭션ID
        private String fathCmpltNtfyDate;           // 안면인증완료일자
        private String fathTelNo;                   // 안면인증URL전송전화번호
        private String fathMobileFnNo;              // 안면인증정보휴대폰번호앞자리번호
        private String fathMobileMnNo;              // 안면인증정보휴대폰번호중간자리번호
        private String fathMobileRnNo;              // 안면인증정보휴대폰번호뒷자리번호
        private String authInfo;                    // 인증정보
        private String identityTypeCd;              // 신분증유형코드
        private String identityIssuDate;            // 신분증발급일자
        private String identityIssuRegion;          // 신분증발급지역
        private String selfIssuNo;                  // 발급번호
        private String driveLicnsNo;                // 운전면허번호
        private String reqInfoChgYn;                // 가입정보변경여부
        private String soc;                         // 요금제
        private String socNm;                       // 요금제명
        private Long socBaseChrgAmt;                // 요금제기본료
        private String jehuProdTypeCd;              // 요금제제휴처코드
        private String usimSuccYn;                  // USIM승계여부
        private String usimSn;                      // USIM번호
        private String iccId;                       // ICCID
        private String eid;                         // EID
        private String imei1;                       // IMEI1
        private String imei2;                       // IMEI2
        private String esimPhoneId;                 // eSIM휴대폰모델ID
        private Long uploadPhoneSrlNo;            // 업로드휴대폰일련번호
        private String remainPayDivCd;              // 완납/승계구분코드
        private String clauseCntrDelYn;             // 양도인고객정보삭제동의여부
        private String clausePriCollectYn;          // 약관개인정보수집동의여부
        private String clausePriOfferYn;            // 약관개인정보제공동의여부
        private String clauseEssCollectYn;          // 약관고유식별정보수집이용제공동의여부
        private String clauseConfidenceYn;          // 약관신용정보이용동의여부
        private String clausePriAdYn;               // 약관개인정보광고전송동의여부
        private String clauseJehuYn;                // 제휴서비스동의여부
        private String clauseFinanceYn;             // 금융제휴약관동의여부
        private String personalInfoCollectAgreeYn;  // 고객혜택제공을위한개인정보수집및이용관련동의여부
        private String othersTrnsAgreeYn;           // 혜택제공을위한제3자제공동의(M모바일)여부
        private String othersTrnsKtAgreeYn;         // 혜택제공을위한제3자제공동의(KT)여부
        private String othersAdReceiveAgreeYn;      // 제3자제공관련광고수신동의여부
        private String clauseFathYn;                // 안면인증동의여부
        private String mcnStatRsnCd;                // 명변사유코드
        private String memo;                        // 메모
        private String regDate;                     // 등록일자
        private String regstId;                     // 등록자ID
        private String regDt;                       // 등록일시
        private String procDt;                      // 처리일시
        private String procCd;                      // 처리결과
        private String mcnStateCd;                  // 진행상태코드
        private String rcvCustNo;                   // 양수인고객번호
        private String rcvBillAcntNo;               // 양수인청구번호
        private String recYn;                       // 녹취여부
        private String resCd;                       // 예약등록코드
        private String resMsg;                      // 예약등록메세지
        private String scanId;                      // 스캐너ID
        private String appFormYn;                   // 스캔이미지여부
        private String appFormXmlYn;                // 서식지XML여부
        private String fileNm;                      // 파일명
        private String fileMaskNm;                  // 마스크파일명
    }

    @Data
    public static class TeCustomerInfo {

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
        private String identityCertTypeCd; // 신분증인증유형코드
        private String knoteIdentityScanCstmrNm; // KNOTE신분증고객명
        private String knoteIdentityEssNo; // KNOTE신분증식별번호
        private String knoteIdentityTypeCd; // KNOTE신분증유형코드
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime knoteIdentityScanDt; // KNOTE신분증스캔일시
        private String knoteScanId; // KNOTE신분증스캔번호
        private String fathTrgYn; // 안면인증대상여부
        private String fathTrgIdentityCertTypeCd; // 안면인증대상신분증유형코드
        private String fathTransacId; // 안면인증트랜잭션ID
        private String fathCmpltNtfyDate; // 안면인증완료일자
        private String fathTelNo; // 안면인증URL전송전화번호
        private String fathMobileFnNo; // 안면인증정보휴대폰번호앞자리번호
        private String fathMobileMnNo; // 안면인증정보휴대폰번호중간자리번호
        private String fathMobileRnNo; // 안면인증정보휴대폰번호뒷자리번호
        private String authInfo; // 인증정보
        private String identityTypeCd; // 신분증유형코드
        private String identityIssuDate; // 신분증발급일자
        private String identityIssuRegion; // 신분증발급지역
        private String selfIssuNo; // 발급번호
        private String driveLicnsNo; // 운전면허번호
        private String trnsNm; // 양도인명
        private String trnsMobileNo; // 명의변경대상모바일번호
        private String trnsPhoneNo; // 명의자연락처번호
        private String trnsPwd; // 명의변경용비밀번호
        private String trnsMyslfConfMethCd; // 양도인본인확인방법코드
        private String trnsTrnsfeNm; // 양도인이입력한양수인명
        private String cstmrVisitTypeCd; // 방문고객유형코드
        private String minorAgentNm; // 미성년자법정대리인성명
        private String minorAgentRrn; // 미성년자법정대리인등록번호
        private String minorAgentBirth; // 미성년자법정대리인생년월일
        private String minorAgentGenderCd; // 미성년자법정대리인성별
        private String minorAgentRelTypeCd; // 미성년자법정대리인관계유형코드
        private String minorAgentTelFnNo; // 미성년자법정대리인연락처앞자리번호
        private String minorAgentTelMnNo; // 미성년자법정대리인연락처중간자리번호
        private String minorAgentTelRnNo; // 미성년자법정대리인끝자리번호
        private String minorAgentAgrmYn; // 미성년자법정대리인안내사항및동의여부
        private String minorAgentSelfInqryAgrmYn; // 미성년자법정대리인본인인증조회동의여부
        private String minorAgentSelfCertTypeCd; // 미성년자법정대리인본인인증유형코드
        private String minorAgentSelfIssuExprDate; // 미성년자법정대리인발급/만료일자
        private String minorAgentSelfIssuNo; // 미성년자법정대리인발급번호
        private String trnsSttusCd; // 처리상태코드
        private String authDelYn; // 개인정보삭제여부
        private String confirmMemo; // 처리메모
    }
}
