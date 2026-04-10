package com.ktmmobile.msf.commons.common.pagination;

import java.util.List;

public record Page<T>(List<T> data, int pageNum, int rowSize, int totalCount) {

    public Page {
        pageNum = PaginationUtils.resolvePageNum(pageNum, rowSize, totalCount);
    }

    public static <T> Page<T> of(List<T> data, PageCondition pageCondition, int totalCount) {
        return new Page<>(data, pageCondition.pageNum(), pageCondition.rowSize(), totalCount);
    }
}
