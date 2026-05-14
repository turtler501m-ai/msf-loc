package com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

@Mapper
public interface TermsCacheMapper {

    List<TermsType> selectListTermsType(List<String> groupList);

    // TermsGroup selectTermsGroup(TermsCondition condition);
    //
    // List<TermsItem> selectListTerms(TermsCondition condition);
}
