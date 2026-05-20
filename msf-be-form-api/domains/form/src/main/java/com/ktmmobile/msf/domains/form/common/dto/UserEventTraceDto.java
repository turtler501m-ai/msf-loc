package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEventTraceDto implements Serializable {

    private static final long serialVersionUID = -3675629223200392498L;

    private long uetSeq;            // 수행 일련번호
    private String prcsMdlMain;     // 처리 모듈 대구분
    private String prcsMdlMid;      // 처리 모듈 중구분
    private String prcsMdlSub;      // 처리 모듈 소소구분
    private String trtmRsltSmst;    // 처리 결과 내용
    private String prcsSbst;        // 처리 내용
    private String dtlDesc;         // 설명
    private String sysRdateDd;      // 등록일 YYYYMMDD
    private String rip;             // 등록아이피
    private String regstId;         // 등록자 ID
    private Date regstDttm;         // 등록일시
    private String rvisnId;         // 수정자 ID
    private Date rvisnDttm;         // 수정일시
    private String strRegstDttm;    // 등록일시 String
}
