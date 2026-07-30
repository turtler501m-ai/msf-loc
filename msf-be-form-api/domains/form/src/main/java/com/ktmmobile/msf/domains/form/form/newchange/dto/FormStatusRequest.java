package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FormStatusRequest {

    private Long requestKey;
    private String operTypeCd; //가입유형
    private String resNo; //신청서 예약번호
    private String knoteScanId; //KNote 서식지 아이디

    private String mngmAgncId; //관리대리점코드
    private String cntpntCd; //접점코드
    private String frmpapId; //서식지아이디
    private String frmpapStatCd; //서식지상태변경코드 ( P : 진행 , R : 복구(진행중인 상태를 접수로 변경) , C : 취소 )
}
