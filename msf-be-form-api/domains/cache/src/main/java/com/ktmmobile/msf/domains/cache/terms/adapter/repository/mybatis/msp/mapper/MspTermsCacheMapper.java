package com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;

@Mapper
public interface MspTermsCacheMapper {

    List<TermsDetail> selectListTermsDetail(List<TermsDetail> termsDetailList);
}
