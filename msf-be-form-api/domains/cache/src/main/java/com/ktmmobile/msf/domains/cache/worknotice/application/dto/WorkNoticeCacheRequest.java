package com.ktmmobile.msf.domains.cache.worknotice.application.dto;

import com.ktmmobile.msf.domains.cache.worknotice.domain.code.WorkNoticeFormType;

public record WorkNoticeCacheRequest(
    WorkNoticeFormType formType
) {
}
