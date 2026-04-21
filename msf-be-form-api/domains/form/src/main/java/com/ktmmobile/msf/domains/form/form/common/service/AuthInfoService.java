package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.common.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.JuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthInfoService {
    private final AuthInfoReadMapper authInfoReadMapper;

    //KTM모바일 고객인증
    public JuoSubInfoResponse getJuoSubInfo(JuoSubInfoCondition condition) {
        JuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        return data;
    }
}
