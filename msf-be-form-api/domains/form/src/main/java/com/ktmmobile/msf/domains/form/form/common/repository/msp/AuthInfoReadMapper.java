package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.JuoSubInfoResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AuthInfoReadMapper {

    //판매정책조회
    JuoSubInfoResponse selectKtmCustomer(JuoSubInfoCondition condition);
}
