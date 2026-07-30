package com.ktmmobile.msf.domains.shared.form.common.generate.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.generate.adapter.repository.mybatis.msp.mapper.McpGenerateKeyMapper;
import com.ktmmobile.msf.domains.shared.form.common.generate.adapter.repository.mybatis.smartform.mapper.GenerateKeyMapper;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

@RequiredArgsConstructor
@Repository
public class GenerateKeyRepositoryImpl implements GenerateKeyRepository {

    // 시퀀스 생성 쿼리를 SMART-FORM 쿼리로 사용하기 위한 조건 - 운영에서는 제거
    private static final boolean RUN_SMART_FORM_MAPPER = true;

    private final GenerateKeyMapper generateKeyMapper;
    private final McpGenerateKeyMapper mcpGenerateKeyMapper;

    @Override
    public String getGeneratedResNo() {
        if (RUN_SMART_FORM_MAPPER) {
            return generateKeyMapper.selectGeneratedResNo();
        }
        return mcpGenerateKeyMapper.selectGeneratedResNo();
    }

    @Override
    public Long getGeneratedRequestKey() {
        if (RUN_SMART_FORM_MAPPER) {
            return generateKeyMapper.selectGeneratedRequestKey();
        }
        return mcpGenerateKeyMapper.selectGeneratedRequestKey();
    }

    @Override
    public Long getGeneratedCustRequestSeq() {
        if (RUN_SMART_FORM_MAPPER) {
            return generateKeyMapper.selectGeneratedCustRequestSeq();
        }
        return mcpGenerateKeyMapper.selectGeneratedCustRequestSeq();
    }

    @Override
    public Long getGeneratedRequestStateSeq() {
        if (RUN_SMART_FORM_MAPPER) {
            return generateKeyMapper.selectGeneratedRequestStateSeq();
        }
        return mcpGenerateKeyMapper.selectGeneratedRequestStateSeq();
    }
}
