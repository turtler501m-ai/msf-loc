package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 부가서비스 신청 요청 DTO. POST /api/v1/addition/reg.
 * SvcChgInfoDto(name, ncn, ctn, custId) 상속 + soc, ftrNewParam 추가.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionRegReqDto extends SvcChgInfoDto {

    /** 부가서비스 코드 */
    private String soc;
    /** 부가 파라미터 (팝업 입력값: 불법TM차단번호, KISA동의여부, 로밍 시작/종료일 등) */
    private String ftrNewParam;

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getFtrNewParam() { return ftrNewParam; }
    public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }
}
