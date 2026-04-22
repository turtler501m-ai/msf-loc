package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AuthInfoReadMapper {

    //KTM모바일 고객인증
    MspJuoSubInfoResponse selectKtmCustomer(MspJuoSubInfoCondition condition);

    //KTM모바일 고객인증
    MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoCondition condition);
}
