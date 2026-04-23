package com.ktmmobile.msf.domains.shared.form.common.terms.adapter.repository;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.terms.adapter.repository.mybatis.msp.mapper.McpTermsMapper;
import com.ktmmobile.msf.domains.shared.form.common.terms.adapter.repository.mybatis.smartform.mapper.TermsMapper;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.dto.TermsCondition;
import com.ktmmobile.msf.domains.shared.form.common.terms.application.port.out.TermsRepository;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsContent;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsGroup;
import com.ktmmobile.msf.domains.shared.form.common.terms.domain.entity.TermsItem;

@RequiredArgsConstructor
@Repository
public class TermsRepositoryImpl implements TermsRepository {

    private final TermsMapper termsMapper;
    private final McpTermsMapper mcpTermsMapper;

    @Override public TermsGroup getTermsGroup(TermsCondition condition) {
        return termsMapper.selectTermsGroup(condition);
    }

    @Override public List<TermsItem> getListTerms(TermsCondition condition) {
        return termsMapper.selectListTerms(condition);
    }

    @Override public List<TermsContent> getListTermsContent(List<TermsItem> itemList) {
        // ORACLE에서 CLOB이 포함된 데이터 조회 시,
        // UNION ALL이 없는 한 ROW 데이터를 조회할 경우,
        // 오류가 발생하는 것을 방지하기 위해 조회가 되지 않는 빈 Item 추가한 다음 쿼리 실행
        if (itemList.size() == 1) {
            List<TermsItem> list = new ArrayList<>(itemList);
            list.add(TermsItem.toEmpty());
            return mcpTermsMapper.selectListTermsContent(list);
        }
        return mcpTermsMapper.selectListTermsContent(itemList);
    }
}
