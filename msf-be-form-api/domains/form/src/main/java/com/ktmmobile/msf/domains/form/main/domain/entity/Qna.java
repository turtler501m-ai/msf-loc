package com.ktmmobile.msf.domains.form.main.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
public class Qna {
    private Long qnaSeq;
    private String qnaSmsSendYn;
    private String qnaCtgCd;
    private String qnaSubCtgCd;
    private String qnaTitle;
    private String qnaWriterId;
    private String qnaNm;
    private LocalDateTime writeDt;
    private String certSndRsltMot;
    private String qnaContentSbst;
    private String cretViewYn;
    private String ansTitle;
    private String ansContentSbst;
    private String ansSttusCd;
    private String ansId;
    private String ansNm;
    private String ansOrgId;
    private String ansOrgNm;
    private LocalDateTime ansDt;
    private String smsSendYn;
    private Integer boardHitCnt;
}
