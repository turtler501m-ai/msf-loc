package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineSelfResponse {

    private boolean isCombine; //	결합여부
    private String rateCd;
    private String rateNm;
    private String pRateCd;
    private String pRateNm;
    private String rRateCd;
    private String rRateNm; // 결합 부가서비스 이름
    private String ctn;
    private String ncn;
    private String custId;
    private String subLinkName;

    public void expireCombinationBenefit() {
        this.isCombine = true;
        this.rRateNm = "0MB";
    }

}
