package com.ktmmobile.msf.formSvcChg.dto;

/**
 * 부가서비스 카탈로그 항목 DTO.
 * ASIS McpRegServiceDto 와 동일 구조.
 * selectRegService (MSP_RATE_MST@DL_MSP) 결과 매핑.
 */
public class McpRegServiceDto {

    private String serviceType;   // 서비스유형
    private String rateCd;        // 요금제코드 (SOC 코드)
    private String applEndDt;     // 적용종료일자
    private String applStrtDt;    // 적용시작일자
    private String rateNm;        // 요금제명
    private String rateGrpCd;     // 요금제그룹코드
    private String payClCd;       // 선후불구분
    private String rateType;      // 요금제유형 (ORG0008)
    private String dataType;      // 데이터유형 (ORG0018)
    private String baseAmt;       // 기본료
    private String baseVatAmt;    // 기본료(VAT포함)
    private String freeCallClCd;  // 망내외무료통화구분
    private String freeCallCnt;   // 무료통화건수
    private String nwInCallCnt;   // 망내무료통화건수
    private String nwOutCallCnt;  // 망외무료통화건수
    private String freeSmsCnt;    // 무료문자건수
    private String freeDataCnt;   // 무료데이터건수
    private String rmk;           // 비고
    private String regstId;       // 등록자ID
    private String regstDttm;     // 등록일시
    private String rvisnId;       // 수정자ID
    private String rvisnDttm;     // 수정일시
    private String onlineTypeCd;  // 온라인유형코드
    private String alFlag;        // 알요금제 구분자
    private String svcRelTp;      // 가입유형: C=가능, B=필수(자동가입), D=가입불가
    private String useYn;         // 사용중 여부 Y:사용중 N:미사용 (X97 조회 후 세팅)

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getRateCd() { return rateCd; }
    public void setRateCd(String rateCd) { this.rateCd = rateCd; }

    public String getApplEndDt() { return applEndDt; }
    public void setApplEndDt(String applEndDt) { this.applEndDt = applEndDt; }

    public String getApplStrtDt() { return applStrtDt; }
    public void setApplStrtDt(String applStrtDt) { this.applStrtDt = applStrtDt; }

    public String getRateNm() { return rateNm; }
    public void setRateNm(String rateNm) { this.rateNm = rateNm; }

    public String getRateGrpCd() { return rateGrpCd; }
    public void setRateGrpCd(String rateGrpCd) { this.rateGrpCd = rateGrpCd; }

    public String getPayClCd() { return payClCd; }
    public void setPayClCd(String payClCd) { this.payClCd = payClCd; }

    public String getRateType() { return rateType; }
    public void setRateType(String rateType) { this.rateType = rateType; }

    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    public String getBaseAmt() { return baseAmt; }
    public void setBaseAmt(String baseAmt) { this.baseAmt = baseAmt; }

    public String getBaseVatAmt() {
        if (baseVatAmt == null || baseVatAmt.isEmpty()) {
            try {
                int amt = Integer.parseInt(baseAmt);
                return String.valueOf((int) Math.floor(amt + (amt * 0.1)));
            } catch (Exception e) {
                return "";
            }
        }
        return baseVatAmt;
    }
    public void setBaseVatAmt(String baseVatAmt) { this.baseVatAmt = baseVatAmt; }

    public String getFreeCallClCd() { return freeCallClCd; }
    public void setFreeCallClCd(String freeCallClCd) { this.freeCallClCd = freeCallClCd; }

    public String getFreeCallCnt() { return freeCallCnt; }
    public void setFreeCallCnt(String freeCallCnt) { this.freeCallCnt = freeCallCnt; }

    public String getNwInCallCnt() { return nwInCallCnt; }
    public void setNwInCallCnt(String nwInCallCnt) { this.nwInCallCnt = nwInCallCnt; }

    public String getNwOutCallCnt() { return nwOutCallCnt; }
    public void setNwOutCallCnt(String nwOutCallCnt) { this.nwOutCallCnt = nwOutCallCnt; }

    public String getFreeSmsCnt() { return freeSmsCnt; }
    public void setFreeSmsCnt(String freeSmsCnt) { this.freeSmsCnt = freeSmsCnt; }

    public String getFreeDataCnt() { return freeDataCnt; }
    public void setFreeDataCnt(String freeDataCnt) { this.freeDataCnt = freeDataCnt; }

    public String getRmk() { return rmk; }
    public void setRmk(String rmk) { this.rmk = rmk; }

    public String getRegstId() { return regstId; }
    public void setRegstId(String regstId) { this.regstId = regstId; }

    public String getRegstDttm() { return regstDttm; }
    public void setRegstDttm(String regstDttm) { this.regstDttm = regstDttm; }

    public String getRvisnId() { return rvisnId; }
    public void setRvisnId(String rvisnId) { this.rvisnId = rvisnId; }

    public String getRvisnDttm() { return rvisnDttm; }
    public void setRvisnDttm(String rvisnDttm) { this.rvisnDttm = rvisnDttm; }

    public String getOnlineTypeCd() { return onlineTypeCd; }
    public void setOnlineTypeCd(String onlineTypeCd) { this.onlineTypeCd = onlineTypeCd; }

    public String getAlFlag() { return alFlag; }
    public void setAlFlag(String alFlag) { this.alFlag = alFlag; }

    public String getSvcRelTp() { return svcRelTp; }
    public void setSvcRelTp(String svcRelTp) { this.svcRelTp = svcRelTp; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
