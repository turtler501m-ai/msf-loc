package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoBanInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.MspJuoSubInfoResponse;

@Mapper
public interface AuthInfoReadMapper {

    //KTM모바일 고객인증
    MspJuoSubInfoResponse selectKtmCustomer(MspJuoSubInfoRequest request);

    //서비스변경/해지 휴대폰 인증. 후속 가입정보조회는 SVC_CNTR_NO(NCN)를 사용한다.
    MspJuoSubInfoResponse selectServiceChangeCustomer(MspJuoSubInfoRequest request);

    //서비스변경/해지 휴대폰 인증 전 MSP_JUO_SUB_INFO 단독 고객정보 존재 여부 확인
    String selectServiceChangeCustomerSubStatus(MspJuoSubInfoRequest request);

    //청구계정아이디조회
    MspJuoBanInfoResponse verifyBillInfo(MspJuoBanInfoRequest condition);

    //선택한 대리점코드에 매핑되는 KT조직코드 찾기
    //String ktOrgId(String agentCd);
}
