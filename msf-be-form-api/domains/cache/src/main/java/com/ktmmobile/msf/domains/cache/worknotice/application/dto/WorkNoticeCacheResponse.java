package com.ktmmobile.msf.domains.cache.worknotice.application.dto;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;

public record WorkNoticeCacheResponse(
    Long seq,
    SimpleCommonCode category,
    String title,
    String contents,
    String startDate,
    String endDate
) {

    public static WorkNoticeCacheResponse of(WorkNoticeCache cache, CommonCodeGroups codeGroups) {
        return new WorkNoticeCacheResponse(
            cache.getBoardSeq(),
            codeGroups.getSimple("SBST_CTG_CD1", cache.getSbstSubCtgCd()),
            cache.getBoardTitle(),
            cache.getBoardContentsSbst(),
            cache.getStartDate(),
            cache.getEndDate()
        );
    }
}
