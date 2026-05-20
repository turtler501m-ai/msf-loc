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
    String agentCd; //대리점코드 (조회를 위한 parameter)

    //서식지 목록조회
    String mngmAgncId; //개통요청 대리점코드 (Must)
    String cntpntCd; //개통요청 접점코드 (Optional)
    String retvStrtDt; //조회시작일시 (Must)
    String retvEndDt; //조회종료일시 (Must)
    String svcApyTrtStatCd; //처리상태코드 (Must)
    String retvSeq; //조회시작번호 (Optional)
    String retvCascnt; //조회건수 (Optional)

    //서식지 상태조회
    //String mngmAgncId; //개통요청 대리점코드 (Must)
    //String cntpntCd; //개통요청 접점코드 (Must)
    String frmpapId; //서식지 아이디 (Must)
}
