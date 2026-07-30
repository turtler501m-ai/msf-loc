package com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * SmartForm 약관 그룹 조회 MyBatis Mapper
 */
@Mapper
public interface TermsCacheMapper {

    /** 약관 그룹 목록 조회 */
    List<TermsType> selectListTermsType(List<String> groupList);

    // TermsGroup selectTermsGroup(TermsCondition condition);
    //
    // List<TermsItem> selectListTerms(TermsCondition condition);
}
