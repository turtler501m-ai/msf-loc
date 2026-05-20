package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;            // 가입신청_키
    private String reqBank;             // 신청정보_계좌이체_은행
    private String reqAccountName;      // 신청정보_계좌이체_예금주
    private String reqAccountRrn;       // 신청정보_계좌이체_예금주_주민번호
    private String reqAccountRelation;  // 신청정보_계좌이체_예금주와관계
    private String reqAccountNumber;    // 신청정보_계좌이체_계좌번호
    private String reqCardName;         // 신청정보_신용카드_명의자
    private String reqCardRrn;          // 신청정보_신용카드_명의자_주민번호
    private String reqCardCompany;      // 신청정보_신용카드_카드사
    private String reqCardNo;           // 신청정보_신용카드_번호
    private String reqCardYy;           // 신청정보_신용카드_유효년
    private String reqCardMm;           // 신청정보_신용카드_유효월
    private String reqWireType;         // 신청정보_무선데이터_이용_타입
    private Date sysRdate;              // 등록일시
    private String reqPayOtherFlag;     // 신청정보_타인납부_여부
    private String reqPayOtherTelFn;    // 신청정보_타인납부_전화번호_앞자리
    private String reqPayOtherTelMn;    // 신청정보_타인납부_전화번호_중간자리
    private String reqPayOtherTelRn;    // 신청정보_타인납부_전화번호_끝자리

}
