package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MplatFormFS8Response {

    private String urlAdr; //URL주소
    private String fathTransacId; //안면인증 트랜잭션 아이디
    private String resltCd; //결과코드
    private String resltSbst; //결과내용

}
