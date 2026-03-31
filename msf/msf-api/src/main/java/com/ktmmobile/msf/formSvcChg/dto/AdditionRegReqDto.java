package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

/**
 * 부가서비스 신청 요청 DTO. POST /api/v1/addition/reg-svc-chg.
 * SvcChgInfoDto(name, ncn, ctn, custId) 상속 + soc, ftrNewParam, flag, couoponPrice 추가.
 * ASIS: RegSvcController.regSvcChgAjax 파라미터 대응.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionRegReqDto extends SvcChgInfoDto {

    /** 부가서비스 코드 */
    private String soc;
    /** 부가 파라미터 (팝업 입력값: 불법TM차단번호, KISA동의여부, 로밍 시작/종료일 등) */
    private String ftrNewParam;
    /**
     * 해지 후 신청 여부 플래그 (로밍 변경 시 "Y").
     * ASIS: RegSvcController.regSvcChgAjax — flag="Y"이면 X38 해지 후 Y25 신청.
     */
    private String flag;
    /**
     * 포인트 할인 사용 금액.
     * ASIS: RegSvcController.regSvcChgAjax — REG_SVC_CD_4(PL2079770) + couoponPrice 포인트 사용.
     * TODO: 별도 포인트 서비스 없음 (미구현).
     */
    private String couoponPrice;

    public String getSoc() { return soc; }
    public void setSoc(String soc) { this.soc = soc; }

    public String getFtrNewParam() { return ftrNewParam; }
    public void setFtrNewParam(String ftrNewParam) { this.ftrNewParam = ftrNewParam; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public String getCouoponPrice() { return couoponPrice; }
    public void setCouoponPrice(String couoponPrice) { this.couoponPrice = couoponPrice; }
}
