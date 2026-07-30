package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionVo {

    private Long requestKey; //신청서 일련번호
    private Long requestSvcChgDtlSeq; //서비스변경 상세일련번호
    private Long additionKey; //고객포탈의 부가서비스 관리코드
    private String additionId; //부가서비스 코드
    private String additionNm; //부가서비스 명
    private Long rantal; //부가서비스 금액
    private String svcChgTypeCd; //가입변경해지 유형코드
    private String addtionInfo; //부가정보
    private String procYn; //처리여부

    //MP 연동 시 항목
    private String prdcCd; //상품코드 additionId
    //private String prdcTypeCd; //상품타입코드 'R'

}
