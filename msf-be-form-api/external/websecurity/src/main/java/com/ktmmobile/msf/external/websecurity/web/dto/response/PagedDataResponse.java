package com.ktmmobile.msf.external.websecurity.web.dto.response;

import java.util.List;
import java.util.function.Function;

import com.ktmmobile.msf.commons.common.pagination.Page;

public record PagedDataResponse<T>(List<T> data, PageResponse page) {

    public static <T, E> PagedDataResponse<T> of(Page<E> page, Function<E, T> mapper) {
        List<T> data = page.data().stream().map(mapper).toList();
        return new PagedDataResponse<>(data, PageResponse.of(page));
    }
}
