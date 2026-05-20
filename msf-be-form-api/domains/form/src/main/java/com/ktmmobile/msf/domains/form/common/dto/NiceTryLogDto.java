package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NiceTryLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long niceTryHistSeq = -1; // 순번(PK)
    private String reqSeq;            // 요청 일련번호
    private String resSeq;            // 응답 일련번호
    private String authType;          // 본인인증 유형 N(네이버), A(PASS), T(Toss)
    private String name;              // 인증성명
    private String birthDate;         // 인증생년월일
    private String connInfo;          // CID
    private String nReferer;          // REFERER
    private String rip;               // 등록아이피
    private String sysRdateDt;        // 등록일
    private Date sysRdate;            // 등록일시
    private Date rvisnDttm;           // 수정일시
    private String succYn;            // 본인인증 성공 여부
    private String fnlSuccYn;         // 본인인증 최종 성공 여부 (고객정보 일치여부)
    private long startTime;           // 인증 시간

    // nReferer 필드명이 소문자 n으로 시작하여 비표준 getter/setter 유지
    public String getnReferer() {
        return nReferer;
    }

    public void setnReferer(String nReferer) {
        this.nReferer = nReferer;
    }

    public Date getStartTimeToDate() {
        Date renDate;
        if (startTime > 0) {
            renDate = new Date(startTime);
        } else {
            Calendar cal = Calendar.getInstance();
            renDate = new Date(cal.getTimeInMillis());
        }
        return renDate;
    }

}
