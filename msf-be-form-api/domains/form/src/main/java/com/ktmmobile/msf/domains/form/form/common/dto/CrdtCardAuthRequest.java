package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrdtCardAuthRequest {

    //신용카드 인증조회 Request DTO
    private String operTypeCd; //가입유형 구분 (NAC3, MNP3, HDN3)
    private String crdtCardNo; //카드번호
    private String crdtCardTermYear; //카드유효기간 년
    private String crdtCardTermMonth; //카드유효기간 월
    private String crdtCardTermDay; //카드유효기간 (년+월)
    private String custNm; //카드소유자명
    private String brthDate; //카드소유주 생년월일
    //private String custBirthDate; //카드소유자 생년월일
    private String ncType; //

    private String othersPaymentYn; //타인납부여부 Y(타인납부)  N(본인납부)
    private String customerSsn; //개통이력확인을 위한 고객의 식별번호 (납부자정보와 다를 수 있음)

    private String cstmrTypeCd;


    //to-be Request
    //private String operTypeCd; //가입유형 구분 (NAC3, MNP3, HCN3)
    //private String cstmrTypeCd; //고객구분
    //private String cstmrNm; //가입하려고 하는 고객명
    //private String customerSsn; //가입하려고 하는 고객식별번호 - 개통이력확인을 위한 고객 식별번호 //CSTMR_NATIVE_RRN, CSTMR_FOREIGNER_RRN
    //private String othersPaymentYn; //타인납부여부 Y(타인납부)  N(본인납부)
    //private String reqCardNo; //신용카드번호 16자리
    //private String reqCardYy; //신용카드 유효기간 년 ( YYYYMM )
    //private String reqCardMm; //신용카드 유효기간 월 ( YYYYMM )
    //private String reqCardNm; //카드소유자의 이름
    //private String reqCardRrn; //카드소유자의 생년월일 YYYYMMDD

}
