package com.ktmmobile.msf.domains.cache.terms.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.msp.mapper.MspTermsCacheMapper;
import com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.smartform.mapper.TermsCacheMapper;
import com.ktmmobile.msf.domains.cache.terms.application.port.out.TermsCacheRepository;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * SmartForm과 MSP 데이터소스 약관 캐시 원천 데이터 조회
 */
@RequiredArgsConstructor
@Repository
public class TermsCacheRepositoryImpl implements TermsCacheRepository {

    private final TermsCacheMapper termsMapper;
    private final MspTermsCacheMapper mspTermsMapper;

    /** 약관 그룹 목록 조회 */
    @Override
    public List<TermsType> getListTermsType(List<String> groupList) {
        return termsMapper.selectListTermsType(groupList);
    }

    /** 약관 상세 목록 조회 */
    @Override
    public List<TermsDetail> getListTermsDetail(List<TermsDetail> detailList) {
        return mspTermsMapper.selectListTermsDetail(detailList);
    }
}
