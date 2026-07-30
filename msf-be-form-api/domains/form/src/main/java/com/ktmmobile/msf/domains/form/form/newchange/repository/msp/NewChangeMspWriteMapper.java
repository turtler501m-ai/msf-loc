package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;

@Mapper
public interface NewChangeMspWriteMapper {

    // 평생할인 프로모션 기적용 테이블 INSERT
    int insertDisPrmtApd(AppformReqDto appformReqDto);
}
