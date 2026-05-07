package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MaskingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long maskingReleaseSeq; // 마스킹해제 일련번호
    private String userId; // 유저아이디
    private String authType; // 마스킹해제 인증유형
    private String ci;
    private Date unmaskingStratDt; // 마스킹해제 시작시간
    private String cretDd; // 마스킹해제 신청일
    private String accessIp; // 처리 IP
    private String cretId; // 신청자 ID
    private String amdId; // 수정자 ID
    private Date cretDt; // 신청일시
    private Date amdDt; // 수정일시
    private long seq; // 일련번호
    private String unmaskingInfo; // 마스킹해제정보
    private String accessUrl; // 처리페이지URL
}
