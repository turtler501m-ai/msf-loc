package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoscPymnReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custId;              // 고객번호
    private String ncn;                 // 사용자 서비스계약번호
    private String ctn;                 // 사용자 전화번호
    private String clntIp;              // Client IP
    private String clntUsrId;           // 사용자 User ID
    private String payMentMoney;        // 수납금액 (단위:원)
    private String blMethod;            // 수납방법
    private String blBankCode;          // 은행코드
    private String bankAcctNo;          // 계좌번호 (실시간 계좌이체 시 필수)
    private String agrDivCd;            // 동의유형
    private String myslfAthnTypeItgCd;  // 본인인증유형
    private String cardNo;              // 카드번호
    private String cardExpirDate;       // 카드 유효기간
    private String cardPwd;             // 카드 비밀번호
    private String cardInstMnthCnt;     // 할부기간
    private String rmnyChId;            // 수납채널

}
