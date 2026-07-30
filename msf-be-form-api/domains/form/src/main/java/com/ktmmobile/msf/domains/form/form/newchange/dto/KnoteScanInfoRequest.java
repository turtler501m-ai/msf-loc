package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KNOTE 신분증 스캔 목록 조회 Request
 */
@Getter
@Setter
@NoArgsConstructor
public class KnoteScanInfoRequest {

    //서식지 목록조회
    private String agentCd; //대리점코드 (조회를 위한 parameter)

    //서식지 목록조회
    private String mngmAgncId; //개통요청 대리점코드 (Must)
    private String cntpntCd; //개통요청 접점코드 (Optional)
    private String retvStrtDt; //조회시작일시 (Must)
    private String retvEndDt; //조회종료일시 (Must)
    private String svcApyTrtStatCd; //처리상태코드 (Must)
    private String retvSeq; //조회시작번호 (Optional)
    private String retvCascnt; //조회건수 (Optional)

    //서식지 상태조회
    //String mngmAgncId; //개통요청 대리점코드 (Must)
    //String cntpntCd; //개통요청 접점코드 (Must)
    private String frmpapId; //서식지 아이디 (Must)

    private String operTypeCd; //업무유형 (NAC3, MNP3, HDN3)

    private String parentScanId;


    //대리점코드
    public String getAgentCd() {
        return agentCd;
    }

    public void setAgentCd(String agentCd) {
        //if (agentCd == null || agentCd.equals("")) {
        //    agentCd = AuthenticationUtils.getAgentCode();
        //}
        this.agentCd = agentCd;
    }
}
