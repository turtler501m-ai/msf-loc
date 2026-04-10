package com.ktmmobile.msf.commons.common.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageCondition {

    private static final int MAX_ROW_SIZE = 500;

    private final int pageNum;
    private final int rowSize;

    @JsonIgnore
    private final int offset;
    @JsonIgnore
    private final boolean pageEnabled;

    public PageCondition(Integer pageNum, Integer rowSize) {
        this.rowSize = resolveRowSize(rowSize);
        this.pageEnabled = this.rowSize > 0;
        this.pageNum = resolvePageNum(pageNum, this.pageEnabled);
        if (!this.pageEnabled) {
            this.offset = 0;
        } else {
            this.offset = (this.pageNum - 1) * this.rowSize;
        }
    }

    private static int resolveRowSize(Integer rowSize) {
        if (rowSize == null || rowSize <= 0) {
            return 0;
        }
        if (rowSize > MAX_ROW_SIZE) {
            return MAX_ROW_SIZE;
        }
        return rowSize;
    }

    private static int resolvePageNum(Integer pageNum, boolean pageEnabled) {
        if (!pageEnabled) {
            return 0;
        }
        return (pageNum == null || pageNum <= 0) ? 1 : pageNum;
    }

    public static PageCondition getOrDisabled(PageCondition page) {
        return page == null ? disabled() : page;
    }

    public static PageCondition disabled() {
        return new PageCondition(0, 0);
    }

    public PageCondition adjustPageNum(int totalCount) {
        if (!pageEnabled) {
            return this;
        }

        int resolvedPageNum = PaginationUtils.resolvePageNum(pageNum, rowSize, totalCount);
        if (resolvedPageNum == pageNum) {
            return this;
        }

        return new PageCondition(resolvedPageNum, rowSize);
    }

    public int pageNum() {
        return pageNum;
    }

    public int rowSize() {
        return rowSize;
    }

    public int offset() {
        return offset;
    }

    public boolean pageEnabled() {
        return pageEnabled;
    }
}
