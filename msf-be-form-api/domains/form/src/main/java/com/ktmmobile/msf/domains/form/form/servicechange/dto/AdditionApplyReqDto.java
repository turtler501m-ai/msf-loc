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
}
