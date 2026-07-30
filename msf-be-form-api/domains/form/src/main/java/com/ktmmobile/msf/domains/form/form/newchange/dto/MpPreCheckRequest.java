package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MpPreCheckRequest {

    private Long requestKey;
    private String resNo; //MSF_REQUEST.RES_NO 에 저장될 값
    private String knoteScanId; //KNOTE 서식지 아이디
    private String fathTransacId; //안면인증 트랜잭션 아이디
    private String operTypeCd; //업무유형
    private String tmpStepCd; //임시저장 단계 (1: 고객, 2: 상품, 3: 동의, 4: 작성완료) - 작성완료한 경우에는 본 테이블을 바라보도록 하는 것으로 할까보다.
    private String agentCd; //화면에서 선택한 대리점코드
    private String mngmAgncId; //Header 값으로 보낼 관리자할 대리점코드

    private String preCheckFormStep = "N";
    private String prgrStatCd;

    private String requestPreCheck;
}
