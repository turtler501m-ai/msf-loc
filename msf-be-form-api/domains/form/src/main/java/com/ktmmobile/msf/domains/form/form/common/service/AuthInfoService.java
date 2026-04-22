package com.ktmmobile.msf.domains.form.form.common.service;

import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.AuthInfoReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthInfoService {
    private final AuthInfoReadMapper authInfoReadMapper;

    //KTM모바일 고객인증
    public MspJuoSubInfoResponse getJuoSubInfo(MspJuoSubInfoCondition condition) {
        MspJuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        return data;
    }

    //청구계정아이디 조회
    public MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoCondition condition) {
        String customerType = "I"; //내국인성인, 내국인미성년자, 외국인, 외국인미성년자
        if ("JP".equals(condition.getCstmrTypeCd())) { //법인 - constant 처리필요
            customerType = "B";
        } else if ("GO".equals(condition.getCstmrTypeCd())) { //공공기관 - constant 처리필요
            customerType = "G";
        }
        condition.setCustomerType(customerType);
        MspJuoBanInfoResponse data = authInfoReadMapper.verifyBillInfo(condition);
        //if (data == null) {
        //throw new CommonException;
        //}
        return data;
    }

    //신용카드인증
    public Map<String, Object> crdtCardAthnInfo(MspJuoSubInfoCondition condition) {
        return null;
    }

    //계좌번호인증
    public Map<String, Object> accountCheck(MspJuoSubInfoCondition condition) {
        //JuoSubInfoResponse data = authInfoReadMapper.selectKtmCustomer(condition);
        return null;
    }
}
