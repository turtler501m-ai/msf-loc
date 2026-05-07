package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleSubsdMstRequest {
    private String salePlcyCd; //판매정책코드
    private String prdtId; //상품아이디 : K7028268
    private String rateCd; //요금제코드 : Pl198G409
    private String agrmTrm; //요금 약정기간
    private String sprtTp; //할인유형 (단말 KD, 요금 PM)
    private String operType; //가입유형 (MNP3, NAC3, HDN3)
    private String orgnId; //조직코드 : 1100011741
    private String oldYn = "N"; //중고여부
}
