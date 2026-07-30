package com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;

/**
 * MSP 약관 상세 조회 MyBatis Mapper
 */
@Mapper
public interface MspTermsCacheMapper {

    /** 약관 상세 목록 조회 */
    List<TermsDetail> selectListTermsDetail(List<TermsDetail> termsDetailList);
}
