package com.ktmmobile.msf.domains.form.form.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;

/**
 * KTM모바일 고객인증 -> 어디로 가야하지....
 **/
@Service
@RequiredArgsConstructor
public class AuthInfoService {

    private final AuthInfoReadMapper authInfoReadMapper;

    //KTM모바일 고객인증
    public FormResponse<MspJuoSubInfoResponse> getJuoSubInfo(MspJuoSubInfoRequest request) {
        //MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        //return data;

        MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(request);
        if (data == null) {
            return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_FAIL);
        }
        return FormResponse.of(ResponseMessage.VALID_KTM_MOBILE_MEMBER_SUCCESS, data);
    }

}
