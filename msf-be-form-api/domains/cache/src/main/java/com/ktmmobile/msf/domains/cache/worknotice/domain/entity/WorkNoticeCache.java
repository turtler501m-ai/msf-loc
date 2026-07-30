package com.ktmmobile.msf.domains.cache.worknotice.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class WorkNoticeCache {
    private Long boardSeq;
    private String boardCtgCd;
    private String sbstCtgCd;
    private String sbstSubCtgCd;
    private String boardTitle;
    private String viewYn;
    private String notiYn;
    private String boardContentsSbst;
    private String boardWriterId;
    private String boardWriterNm;
    private LocalDateTime boardWriteDt;
    private String imgNm;
    private String boardAttYn;
    private Integer boardHitCnt;
    private String startDate;
    private String endDate;
}
