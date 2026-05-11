package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.mapstruct.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;

@Mapper
public interface AuthInfoReadMapper {

    //KTM모바일 고객인증
    MspJuoSubInfoResponse selectKtmCustomer(MspJuoSubInfoRequest request);

    //청구계정아이디조회
    MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoRequest condition);
}
