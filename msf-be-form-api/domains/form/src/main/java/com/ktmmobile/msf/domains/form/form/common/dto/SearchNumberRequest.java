package com.ktmmobile.msf.domains.form.form.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchNumberRequest {
    @NotNull
    private Long requestKey; //신청서 일련번호

    private String reqWantNumber; //희망번호 조회 : 희망번호 입력 4자리

    private String tlpNo; //희망번호 예약 시 선택한 전화번호
    private String encdTlphNo; //희망번호 예약 시 선택한 전화번호 암호화한 값
    private String tlphNoOwnCmpnCd; //희망번호 예약 시 선택한 전화번호의 통신사

}
