package com.ktmmobile.msf.commons.common.pagination;

public interface PageableCondition<T extends PageableCondition<T>> {

    PageCondition page();

    T withPage(PageCondition page);

    default T adjustPage(int totalCount) {
        return withPage(page().adjustPageNum(totalCount));
    }
}
