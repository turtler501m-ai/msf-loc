package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRegServiceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceType;  // 서비스유형
    private String rateCd;       // 요금제코드
    private String applEndDt;    // 적용종료일자
    private String applStrtDt;   // 적용시작일자
    private String rateNm;       // 요금제명
    private String rateGrpCd;    // 요금제그룹코드
    private String payClCd;      // 선후불구분
    private String rateType;     // 요금제유형 (ORG0008)
    private String dataType;     // 데이터유형 (ORG0018)
    private String baseAmt;      // 기본료
    private String baseVatAmt;   // 기본료 (VAT포함)
    private String freeCallClCd; // 망내외무료통화구분
    private String freeCallCnt;  // 무료통화건수
    private String nwInCallCnt;  // 망내무료통화건수
    private String nwOutCallCnt; // 망외무료통화건수
    private String freeSmsCnt;   // 무료문자건수
    private String freeDataCnt;  // 무료데이터건수
    private String rmk;          // 비고
    private String regstId;      // 등록자ID
    private String regstDttm;    // 등록일시
    private String rvisnId;      // 수정자ID
    private String rvisnDttm;    // 수정일시
    private String onlineTypeCd; // 온라인유형코드
    private String alFlag;       // 알요금제 구분자
    private String svcRelTp;     // 가입유형 c:가능, b:필수(자동가입), d:가입불가
    private String useYn;        // 사용중 여부 Y:사용중, N:미사용

    // baseVatAmt null이면 baseAmt 기반으로 VAT 10% 계산하여 반환
    public String getBaseVatAmt() {
        if (baseVatAmt == null || baseVatAmt.equals("")) {
            try {
                int amt = Integer.parseInt(baseAmt);
                return String.valueOf((int) Math.floor(amt + (amt * 0.1)));
            } catch (Exception e) {
                return "";
            }
        } else {
            return baseVatAmt;
        }
    }

}
