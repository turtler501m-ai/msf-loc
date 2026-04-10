package com.ktmmobile.msf.commons.common.pagination;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class PaginationUtils {

    public static int resolvePageNum(int requestedPageNum, int rowSize, int totalCount) {
        if (rowSize <= 0) {
            return 0;
        }
        if (requestedPageNum <= 0) {
            return 1;
        }

        long lastPageNum = Math.max(1L, ((long) totalCount + rowSize - 1) / rowSize);
        return (int) Math.min(requestedPageNum, lastPageNum);
    }
}
