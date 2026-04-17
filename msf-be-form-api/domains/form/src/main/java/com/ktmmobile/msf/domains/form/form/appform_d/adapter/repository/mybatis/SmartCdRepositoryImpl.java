package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.smartform.mapper.SmartCdMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.SmartCdRepository;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.SmartCd;

@RequiredArgsConstructor
@Repository
public class SmartCdRepositoryImpl implements SmartCdRepository {

    private final SmartCdMapper smartCdMapper;

    @Override
    public List<SmartCd> getCodeList(SmartCdCondition condition) {
        return smartCdMapper.selectList(condition);
    }
}
