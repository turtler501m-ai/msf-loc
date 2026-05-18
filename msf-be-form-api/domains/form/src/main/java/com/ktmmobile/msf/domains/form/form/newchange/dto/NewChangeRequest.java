package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 작성신청서 select request parameter 정의
 */
@Getter
@Setter
@NoArgsConstructor
public class NewChangeRequest {

    //boolean isSaved; //고객스텝 저장 완료 여부
    //boolean isVerified; //인증 완료 여부
    //String msfRequestKey; //쓰는데가 있나......

    Long newRequestKey; //신청서 복사하기에서 사용함.
    String tmpStepCd; //신청서 복사하기에서 임시저장 step 값

    //@NotBlank
    Long requestKey;

    String tempYn; //임시저장 테이블을 검색할지 원천 테이블을 검색할지 구분
    String reqBuyTypeCd; //휴대폰인 경우 MM, USIM 인 경우 UU

    String managerCd; //로그인 사용자
    String agentCd; //대리점코드
    String shopCd; //판매점코드

}
