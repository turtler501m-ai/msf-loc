package com.ktmmobile.msf.domains.form.form.common.vo;

import java.util.List;

import lombok.Data;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Data
public class McpCustRequestNameChgVo {

    private Long requestKey;                    // 시퀀스
    private String trnsTrnsfeNm;                 // 고객명
    private String knoteScanId;                 // knote 스캔 아이디
    private String scanId;                       // documentId
    private String parentScanId;                 // parentScanId
    private String cstmrType;                  // 고객유형
    private String cstmrTypeCd;                  // 고객유형
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String cstmrNativeRrn;               // 주민번호 (마스킹/암호화 필수)
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String cstmrForeignerRrn;            // 고객정보외국인외국인등록번호
    private String selfCertType;                 // 본인인증_방법 (null 처리분 반영)
    private String teAuthInfo;                   // 본인인증_인증정보
    private String clauseCntrDelYn;              // 약관_고객정보삭제동의_양도인
    private String clausePriCollectYn;           // 약관_개인정보 수집동의
    private String clausePriOfferYn;             // 약관_개인정보 제공동의
    private String clauseEssCollectYn;           // 약관_고유식별 정보수집이용동의
    private String clausePriAdYn;                // 개인정보 처리 위탁 및 광고 수신 동의
    private String clauseJehuYn;                 // 약관_제휴서비스를 위한 동의
    private String reqInfoChgYn;                 // 가입정보변경여부
    private String soc;                          // 요금제
    private String socNm;                        // 요금제명
    private String cstmrZipcd;                   // 우편번호
    private String cstmrAdr;                     // 주소
    private String cstmrAdrDtl;                  // 상세주소
    private String cstmrBillSendTypeCd;          // 명세서종류
    private String cstmrEmailAdr;                // 이메일
    private String reqPayTypeCd;                 // 요금납부방법
    private String reqBankCd;                    // 계좌이체_은행코드
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String reqAccountNo;                 // 계좌이체_계좌번호
    private String reqCardCompanyCd;             // 신용카드_카드사
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String reqCardNo;                    // 신용카드_번호
    private String reqCardYy;                    // 신용카드_유효년
    private String reqCardMm;                    // 신용카드_유효월
    private String teIdentityTypeCd;             // 신분증인증_유형 selfCertType
    private String teIdentityIssuDate;           // 신분증인증_발급/만료일자
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String teSelfIssuNo;                 // 신분증인증_발급번호
    private String trnsTrnsfeMobileNo;          // 연락가능연락처
    private String trnsTrnsfeMobileNo1;          // 연락가능연락처_첫자리
    private String trnsTrnsfeMobileNo2;          // 연락가능연락처_중간자리
    private String trnsTrnsfeMobileNo3;          // 연락가능연락처_끝자리
    private String procResult;                   // 처리결과 (RQ: 신청 고정값)
    private String userId;                       // 등록자 (USERID 매핑)
    private String regDate;               // 등록일 (시스템 현재시간)
    private String jehuProdTypeCd;               // 요금제 제휴처
    private String personalInfoCollectAgreeYn;   // 고객 혜택 제공을 위한 개인정보 수집 동의
    private String othersTrnsAgreeYn;            // 혜택 제공을 위한 제3자 제공 동의(M모바일)
    private String othersTrnsKtAgreeYn;          // 혜택 제공을 위한 제3자 제공 동의(KT)
    private String othersAdReceiveAgreeYn;       // 제3자 제공관련 광고 수신 동의
    private String indvLocaPrvAgreeYn;           // 개인위치정보 제 3자 제공 동의
    private String cstmrForeignerNation;         // 고객정보_외국인_국적
    private String minorAgentSelfInqryAgrmYn;    // 본인인증조회동의
    private String mcnStatRsnCd;                 // 명변 사유코드
    private String usimSuccYn;                   // USIM 승계 여부
    private String iccId;                        // USIM 일련번호
    private String mcnResNo;                     // 명의변경 예약번호
    private String progressStatus;               // 진행상태 (00 고정값)
    private String minorAgentTelMnNo;            // 법정대리인연락처
    private String clauseFathYn;                 // 안면인증 동의여부
    private String selfCstmrCi;                  // 본인인증CI selfCstmrCi
    private String teFathTransacId;              // 안면인증 트랜잭션ID
    private String teFathCmpltNtfyDate;          // FS9 안면인증 완료일
    private String teFathTrgYn;                  // 안면인증 대상여부
    private String teFathTelNo;                  // 안면인증URL전송 전화번호
    private String indvLocaPrvAgree;             // 개인위치정보 제 3자 제공 동의 indvLocaPrvAgree

    public void setup() {
        List<String> certType = List.of("05", "06"); // 05: 여권(외국인), 06: 외국인등록증

        // 외국인인 경우 외국인 주민번호 설정
        if (StringUtils.hasText(this.selfCertType) && certType.contains(this.selfCertType)) {
            this.cstmrNativeRrn = this.cstmrForeignerRrn;
        }
    }

}
