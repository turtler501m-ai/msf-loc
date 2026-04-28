package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * KTM모바일 고객인증 -> 어디로 가야하지....
 **/
@Service
@RequiredArgsConstructor
public class AuthInfoService {
    private final AuthInfoReadMapper authInfoReadMapper;

    //KTM모바일 고객인증
    public MspJuoSubInfoResponse getJuoSubInfo(MspJuoSubInfoCondition condition) {
        MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        return data;
    }

}
