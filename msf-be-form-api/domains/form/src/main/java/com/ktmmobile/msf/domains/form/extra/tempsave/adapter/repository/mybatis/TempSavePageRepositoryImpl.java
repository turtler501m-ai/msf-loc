package com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis.smartform.mapper.TempSavePageMapper;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out.TempSavePageRepository;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.entity.TempSavePage;

@RequiredArgsConstructor
@Repository
public class TempSavePageRepositoryImpl implements TempSavePageRepository {

    private final TempSavePageMapper formRequestMapper;

    @Override
    public Page<TempSavePage> selectList(TempSavePageCondition condition) {
        int totalCount = formRequestMapper.count(condition);

        List<TempSavePage> data = formRequestMapper.selectList(condition);

        return Page.of(data, condition.page(), totalCount);
    }
}
