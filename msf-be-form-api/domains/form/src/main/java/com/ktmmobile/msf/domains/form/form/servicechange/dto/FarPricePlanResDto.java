package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FarPricePlanResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rateAdsvcLteDesc = ""; // 데이터 요금제설명
    private String rateAdsvcCallDesc = ""; // 음성 요금제설명
    private String rateAdsvcSmsDesc = ""; // sms문자 요금제 설명
    private int rateAdsvcGdncSeq = 0; // 요금제부가서비스안내일련번호
}
