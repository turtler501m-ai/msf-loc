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
public class Board {
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
