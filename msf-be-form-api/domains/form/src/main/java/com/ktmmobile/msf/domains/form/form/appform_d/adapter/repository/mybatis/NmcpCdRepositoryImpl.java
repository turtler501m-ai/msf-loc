package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper.NmcpCdMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.NmcpCdRepository;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.NmcpCd;

@RequiredArgsConstructor
@Repository
public class NmcpCdRepositoryImpl implements NmcpCdRepository {

    private final NmcpCdMapper nmcpCdMapper;

    @Override
    public List<NmcpCd> getCodeList(NmcpCdCondition condition) {
        return nmcpCdMapper.selectList(condition);
    }

}
