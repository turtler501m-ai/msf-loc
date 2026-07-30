package com.ktmmobile.msf.domains.eformsign.feature.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.MspSmsData;

@Mapper
public interface MspEformMapper {

    List<MspSmsData> selectListMspSmsData(MspSmsData mspSmsData);
}
