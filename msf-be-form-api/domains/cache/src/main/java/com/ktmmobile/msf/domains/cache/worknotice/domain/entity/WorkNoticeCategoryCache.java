package com.ktmmobile.msf.domains.cache.worknotice.domain.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class WorkNoticeCategoryCache {
    String category;
    List<WorkNoticeCache> list;
}
