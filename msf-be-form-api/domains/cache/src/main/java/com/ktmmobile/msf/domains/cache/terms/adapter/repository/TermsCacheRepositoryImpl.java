package com.ktmmobile.msf.domains.cache.terms.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.msp.mapper.MspTermsCacheMapper;
import com.ktmmobile.msf.domains.cache.terms.adapter.repository.mybatis.smartform.mapper.TermsCacheMapper;
import com.ktmmobile.msf.domains.cache.terms.application.port.out.TermsCacheRepository;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

@RequiredArgsConstructor
@Repository
public class TermsCacheRepositoryImpl implements TermsCacheRepository {

    private final TermsCacheMapper termsMapper;
    private final MspTermsCacheMapper mspTermsMapper;

    @Override
    public List<TermsType> getListTermsType(List<String> groupList) {
        return termsMapper.selectListTermsType(groupList);
    }

    @Override
    public List<TermsDetail> getListTermsDetail(List<TermsDetail> detailList) {
        return mspTermsMapper.selectListTermsDetail(detailList);
    }

    // @Override public TermsGroup getTermsGroup(TermsCondition condition) {
    //     return termsMapper.selectTermsGroup(condition);
    // }
    //
    // @Override public List<TermsItem> getListTerms(TermsCondition condition) {
    //     return termsMapper.selectListTerms(condition);
    // }
    //
    // @Override public List<TermsContent> getListTermsContent(List<TermsContentRequest> termsContentRequests) {
    //     // ORACLE에서 CLOB이 포함된 데이터 조회 시,
    //     // UNION ALL이 없는 한 ROW 데이터를 조회할 경우,
    //     // 오류가 발생하는 것을 방지하기 위해 조회가 되지 않는 빈 Item 추가한 다음 쿼리 실행
    //     if (termsContentRequests.size() == 1) {
    //         List<TermsContentRequest> list = new ArrayList<>(termsContentRequests);
    //         list.add(TermsContentRequest.toEmpty());
    //         return mcpTermsMapper.selectListTermsContent(list);
    //     }
    //     return mcpTermsMapper.selectListTermsContent(termsContentRequests);
    // }
    //
    // @Override public TermsContent getTermsContent(TermsContentRequest request) {
    //     // ORACLE에서 CLOB이 포함된 데이터 조회 시,
    //     // UNION ALL이 없는 한 ROW 데이터를 조회할 경우,
    //     // 오류가 발생하는 것을 방지하기 위해 조회가 되지 않는 빈 Item 추가한 다음 쿼리 실행
    //     List<TermsContentRequest> list = new ArrayList<>();
    //     list.add(request);
    //     list.add(TermsContentRequest.toEmpty());
    //     List<TermsContent> result = mcpTermsMapper.selectListTermsContent(list);
    //     if (result == null || result.isEmpty()) {
    //         return null;
    //     }
    //     return result.getFirst();
    // }
}
