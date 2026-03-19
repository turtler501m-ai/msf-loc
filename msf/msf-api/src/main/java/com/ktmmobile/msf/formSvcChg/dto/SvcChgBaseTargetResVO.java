package com.ktmmobile.msf.formSvcChg.dto;

import java.util.List;
import java.util.Map;

/**
 * 서비스변경 업무 목록 조회 응답 VO.
 * GET /api/v1/service-change/base/targets 응답.
 *
 * categoryList: 프론트엔드 ChangeTypeCust.vue 가 사용하는 형식
 *   [ { category: {id, label}, options: [{id, label, concurrentBlockYn, imagingYn}] } ]
 * concurrentGroups: 동시선택 불가 코드 묶음 (flat하면 coreChangeCodes)
 *   [ ["RATE_CHANGE", "NUM_CHANGE", "LOST_RESTORE"] ]
 */
public class SvcChgBaseTargetResVO {

    /** DB 원본 구조 (내부 처리용) */
    private List<SvcChgBaseCategoryDto> categories;

    /** 프론트엔드 ChangeTypeCust.vue 용 categoryList */
    private List<Map<String, Object>> categoryList;

    /** 동시선택 불가 코드 묶음. concurrentGroups.flat() = coreChangeCodes */
    private List<List<String>> concurrentGroups;

    public List<SvcChgBaseCategoryDto> getCategories() { return categories; }
    public void setCategories(List<SvcChgBaseCategoryDto> categories) { this.categories = categories; }

    public List<Map<String, Object>> getCategoryList() { return categoryList; }
    public void setCategoryList(List<Map<String, Object>> categoryList) { this.categoryList = categoryList; }

    public List<List<String>> getConcurrentGroups() { return concurrentGroups; }
    public void setConcurrentGroups(List<List<String>> concurrentGroups) { this.concurrentGroups = concurrentGroups; }
}
