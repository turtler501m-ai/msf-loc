package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper.JuoSubInfoMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.JuoSubInfoRepository;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.JuoSubInfo;

@RequiredArgsConstructor
@Repository
public class JuoSubInfoRepositoryImpl implements JuoSubInfoRepository {

    private final JuoSubInfoMapper juoSubInfoMapper;

    @Override
    public Optional<JuoSubInfo> getJuoSubInfo(JuoSubInfoCondition condition) {
        return juoSubInfoMapper.selectKtmCustomer(condition);
    }

}