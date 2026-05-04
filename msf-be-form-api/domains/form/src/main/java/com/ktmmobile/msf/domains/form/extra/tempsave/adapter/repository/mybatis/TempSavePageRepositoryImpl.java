package com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.pagination.Page;
import com.ktmmobile.msf.domains.form.extra.tempsave.adapter.repository.mybatis.smartform.mapper.TempSavePageMapper;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.dto.TempSavePageCondition;
import com.ktmmobile.msf.domains.form.extra.tempsave.application.port.out.TempSavePageRepository;
import com.ktmmobile.msf.domains.form.extra.tempsave.domain.vo.TempSaveVo;

@Repository
@RequiredArgsConstructor
public class TempSavePageRepositoryImpl implements TempSavePageRepository {

    private final TempSavePageMapper tmpSavePageMapper;

    @Override public Page<TempSaveVo> selectTempSaveList(TempSavePageCondition condition) {
        int totalCount = tmpSavePageMapper.countTempSaveList(condition);
        List<TempSaveVo> data = tmpSavePageMapper.selectTempSaveList(condition);
        return Page.of(data, condition.page(), totalCount);
    }
}
