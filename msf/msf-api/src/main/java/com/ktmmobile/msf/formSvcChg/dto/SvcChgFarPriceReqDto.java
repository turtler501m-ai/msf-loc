package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 요금제 변경 요청 DTO (X19/X84/X88/X89/X90 공용).
 * schedule: "immediate" (즉시변경 X19), "reserve" (예약변경 X88), "immediate-from-reserve" (예약일 즉시 X84).
 */
public class SvcChgFarPriceReqDto extends SvcChgInfoDto {

    /** 변경할 요금제 SOC 코드 */
    private String soc;

    /** 요금제 명 (화면 표시용) */
    private String socNm;

    /** 변경 스케줄: immediate(즉시), reserve(익월1일 예약) */
    private String schedule;

    /** 부가파라미터 (X19/X88 ftrNewParam) */
    private String ftrNewParam;

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getSocNm() { return socNm; }
    public void setSocNm(String socNm) { this.socNm = socNm; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public String getFtrNewParam() { return ftrNewParam; }
    public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }
}
