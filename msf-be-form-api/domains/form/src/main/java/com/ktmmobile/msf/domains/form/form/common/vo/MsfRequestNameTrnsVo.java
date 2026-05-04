package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestNameTrnsVo {

    private Long requestKey; // 가입신청키
    private Long nflChgTrnsSeq; // 명의변경신청일련번호
    private Long trnsSeq; // 명의변경일련번호
    private String cretIp; // 생성IP
    private LocalDateTime cretDt; // 생성일시
    private String cretId; // 생성자ID
    private String amdIp; // 수정IP
    private LocalDateTime amdDt; // 수정일시
    private String amdId; // 수정자ID
    private String trnsCstmrTypeCd; // 양도인고객구분유형코드
    private String trnsTrnsfeMobileNo; // 양수인모바일번호
    private String identityCertTypeCd; // 신분증인증유형코드
    private String knoteIdentityScanCstmrNm; // KNOTE신분증고객명
    private String knoteIdentityEssNo; // KNOTE신분증식별번호
    private String knoteIdentityTypeCd; // KNOTE신분증유형코드
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
