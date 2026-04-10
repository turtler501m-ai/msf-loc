package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper.MspCdMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.MspCdRepository;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.MspCd;

@RequiredArgsConstructor
@Repository
public class MspCdRepositoryImpl implements MspCdRepository {

    private final MspCdMapper mspCdMapper;

    @Override
    public List<MspCd> getCodeList(MspCdCondition condition) {
        return mspCdMapper.selectList(condition);
    }

}
