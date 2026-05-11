package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeAdditionRequest {

    //Long requestKey; //신청서 일련번호
    Long requestSvcChgDtlSeq; //서비스변경 상세일련번호
    Long additionKey; //부가서비스의 신청서일련번호별 시퀀스
    String additionId; //부가서비스 코드
    String additionNm; //부가서비스 명
    Long rantal; //부가서비스 금액
    String svcChgTypeCd; //가입변경해지 유형코드
    String addtionInfo; //부가정보
    String procYn; //처리여부
}
