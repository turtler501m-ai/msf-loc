package com.ktmmobile.msf.commons.websecurity.web.dto.response;

import com.ktmmobile.msf.commons.common.pagination.Page;

public record PageResponse(int pageNum, int rowSize, int totalCount) {

    public static PageResponse of(Page<?> page) {
        return new PageResponse(page.pageNum(), page.rowSize(), page.totalCount());
    }
}
