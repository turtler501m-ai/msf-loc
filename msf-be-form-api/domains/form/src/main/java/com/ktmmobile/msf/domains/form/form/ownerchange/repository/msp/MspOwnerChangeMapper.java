package com.ktmmobile.msf.domains.form.form.ownerchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;

@Mapper
public interface MspOwnerChangeMapper {

    MspRateMstDto selectRateInfo(String rateCd);
}
