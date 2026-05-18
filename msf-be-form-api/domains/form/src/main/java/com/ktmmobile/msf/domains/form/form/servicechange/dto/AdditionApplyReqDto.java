package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionApplyReqDto {
    private String ncn; // 서비스 계약번호
    private String ctn; // 전화번호
    private String custId; // 고객번호
    private String soc; // 부가서비스 상품 코드
    private String ftrNewParam; // 부가정보
    private String prodHstSeq; // 상품이력번호
    private String flag; // "Y": 선해지 후 신청
    private String svcTgtCd; // 서비스변경 타입 코드 (R11/R12 등)
    // 로밍 서브상품 신청 시 대표번호 상품일련번호 조회용
    private String mtCd;    // 대표상품코드 (예: PL2079777)
    private String strtDt;  // 서브 시작일 (yyyyMMdd)
    private String endDt;   // 서브 종료일 (yyyyMMdd)
    private String mtPhone; // 대표 전화번호
}
