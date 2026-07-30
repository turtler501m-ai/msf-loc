package com.ktmmobile.msf.domains.form.form.common.vo;

import java.util.List;

import lombok.Data;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;

@Data
public class McpCustRequestMstVo {

    private Long requestKey;          // 신청시퀀스번호
    private String reqType;           // 신청타입
    private String userId;            // 아이디
    private String custNm;            // 고객명(양도인)
    private String mobileNo;      // 휴대폰번호(양도인)
    @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
    private String cstmrNativeRrn;  // 주민번호 (마스킹/암호화 필수)
    private String cstmrNativeBirth;
    private String cstmrNativeGenderCd;
    private String ncn;               // 계약번호
    private String cstmrTypeCd;   // 고객타입
    private String cstmrType;   // 고객타입
    private String customerType;   // 고객타입
    private String onlineAuthType;    // 온라인인증타입 (null 처리분 반영)
    private String authInfo;        // 온라인인증정보
    private String etcMobile;        // 기타연락처
    private String cretId;        // 생성자아이디

    public void setupData() {
        List<String> government = List.of("GO"); // G 공공기관
        List<String> compony = List.of("JP"); // B 법인
        String customerType = switch (this.cstmrTypeCd) {
            case String s when government.contains(s) -> "G";
            case String s when compony.contains(s) -> "B";
            default -> "I";
        };

        this.cretId = AuthenticationUtils.getUser().getUserId();
        this.customerType = customerType;
    }
}
