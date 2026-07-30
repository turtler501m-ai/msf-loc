package com.ktmmobile.msf.domains.form.form.common.repository;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.mybatis.config.SmartFormMyBatisConfig;
import com.ktmmobile.msf.domains.form.form.common.dto.MaskingDto;

@Repository
@RequiredArgsConstructor
public class CommonMaskingDaoImpl {

    @Qualifier(SmartFormMyBatisConfig.SQL_SESSION_TEMPLATE)
    private final SqlSessionTemplate sqlSessionTemplate;

    public int insertMaskingRelease(MaskingDto maskingDto) {
        return sqlSessionTemplate.insert("MaskingMapper.insertMaskingRelease", maskingDto);
    }

    public int insertMaskingReleaseHist(MaskingDto maskingDto) {
        return sqlSessionTemplate.insert("MaskingMapper.insertMaskingReleaseHist", maskingDto);
    }
}
