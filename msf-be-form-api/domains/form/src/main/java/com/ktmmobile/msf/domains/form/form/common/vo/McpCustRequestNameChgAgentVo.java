package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Data;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Data
public class McpCustRequestNameChgAgentVo {

    private Long requestKey;                           // 시퀀스
    private String minorAgentNm;                       // 양수인 법정대리인 성명
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String minorAgentRrn;                      // 양수인 법정대리인 식별번호 (마스킹/암호화 필수)
    private String onlineAuthType;                     // 양수인 법정대리인 본인인증 방법 onlineAuthType
    private String teAuthInfo;                         // 양수인 법정대리인 본인인증 인증정보
    private String jrdclAgentRelTypeCd;                // 양수인 법정대리인 유형
    private String minorAgentSelfInqryAgrmYn;          // 양수인 법정대리인 본인인증조회동의
    private String teIdentityTypeCd;                   // 양수인 법정대리인 실명인증증빙서류코드
    private String teIdentityIssuDate;                 // 양수인 법정대리인 식별번호 발급일자
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String teDriveLicnsNo;                     // 양수인 법정대리인 면허번호
    private String cstmrForeignerNation;               // 양수인 법정대리인 국적코드
    private String minorAgentTelFnNo;                  // 양수인 법정대리인 연락처
    private String minorAgentTelRnNo;                  // 양수인 법정대리인 연락처
    private String minorAgentTelMnNo;                  // 양수인 법정대리인 연락처
    private String minorAgentTelNo;                  // 양수인 법정대리인 연락처
    private String selfCstmrCi;                        // 양수인 법정대리인 본인인증CI selfCstmrCi
    private String trMinorAgentNm;                     // 양도인 법정대리인 성명
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String trMinorAgentRrn;                    // 양도인 법정대리인 식별번호 grMinorAgentRrn (마스킹/암호화 필수)
    private String trOnlineAuthType;                   // 양도인 법정대리인 본인인증 방법 grOnlineAuthType
    private String trAuthInfo;                         // 양도인 법정대리인 본인인증 인증정보
    private String trMinorAgentRelTypeCd;              // 양도인 법정대리인 유형
    private String trMinorAgentSelfInqryAgrmYn;        // 양도인 법정대리인 본인인증조회동의
    private String trMinorAgentTelFnNo;                // 양도인 법정대리인 연락처
    private String trMinorAgentTelMnNo;                // 양도인 법정대리인 연락처
    private String trMinorAgentTelRnNo;                // 양도인 법정대리인 연락처
    private String trMinorAgentTelNo;                // 양도인 법정대리인 연락처
    private String userId;                             // 등록자ID (USERID 매핑)
    private String regDate;                      // 등록일시 (시스템 현재시간)
}
