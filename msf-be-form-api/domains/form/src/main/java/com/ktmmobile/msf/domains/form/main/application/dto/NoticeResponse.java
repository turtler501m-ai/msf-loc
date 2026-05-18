package com.ktmmobile.msf.domains.form.main.application.dto;

import java.time.LocalDateTime;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.main.domain.entity.Board;

public record NoticeResponse(
    Long id,
    String typeCode,
    SimpleCommonCode category,
    SimpleCommonCode subCategory,
    String title,
    String contents,
    String writerId,
    String writerName,
    LocalDateTime writeDate,
    String imageName,
    String attechFileYn,
    Integer hitCount,
    String startDate,
    String endDate
) {
    public static NoticeResponse of(Board board, CommonCodeGroups codeGroups) {
        return new NoticeResponse(
            board.getBoardSeq(),
            board.getBoardCtgCd(),
            codeGroups.getSimple("SBST_CTG_CD1", board.getSbstCtgCd()),
            codeGroups.getSimple("SBST_CTG_CD1", board.getSbstSubCtgCd()),
            board.getBoardTitle(),
            board.getBoardContentsSbst(),
            board.getBoardWriterId(),
            board.getBoardWriterNm(),
            board.getBoardWriteDt(),
            board.getImgNm(),
            board.getBoardAttYn(),
            board.getBoardHitCnt(),
            board.getStartDate(),
            board.getEndDate()
        );
    }
}
