package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsimMspRateDto implements Serializable {

    private static final long serialVersionUID = -4481756991458722818L;

    private String rateCd;          // 요금제 코드
    private String rateNm;          // 요금제명
    private String payClCd;         // 선후불코드
    private String cdDsc;           // 선후불명
    private String rateType;        // 요금타입
    private String rateDesc;        // 요금설명
    private String vat;             // 세금
    private String baseAmt;         // 요금
    private String dataTypeCode;    // 데이터 타입
    private String joinPrice;       // 가입비
    private String usimPrice;       // 유심가격
    private String dcAmt;           // 할인율
    private String freeCallCnt;     // 무료통화
    private String nwInCallCnt;     // 망내통화
    private String nwOutCallCnt;    // 망외통화
    private String freeSmsCnt;      // SMS
    private String freeDataCnt;     // 데이터
    private String agrmTrm;
    private String sprtTp;          // 할인유형
    private String sprtTpNm;        // 할인유형이름
    private String salePlcyCd;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
