package com.ktmmobile.msf.domains.form.form.ownerchange.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.form.ownerchange.repository.msp.MspOwnerChangeMapper;

@Repository
@RequiredArgsConstructor
public class OwnerChangeRepository {

    private final MspOwnerChangeMapper mspOwnerChangeMapper;

    public MspRateMstDto selectRateInfo(String rateCd) { return mspOwnerChangeMapper.selectRateInfo(rateCd); }
}
