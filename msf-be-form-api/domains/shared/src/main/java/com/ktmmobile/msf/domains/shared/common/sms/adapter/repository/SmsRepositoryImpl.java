package com.ktmmobile.msf.domains.shared.common.sms.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.msp.mapper.MspSmsMapper;
import com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.smartform.mapper.SmsMapper;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.IdVerifValidationDetail;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.MspSmsData;
import com.ktmmobile.msf.domains.shared.common.sms.application.port.out.SmsRepository;

@RequiredArgsConstructor
@Repository
public class SmsRepositoryImpl implements SmsRepository {

    private final SmsMapper smsMapper;
    private final MspSmsMapper mspSmsMapper;

    @Override public Integer registerMsfCrtVldDtl(IdVerifValidationDetail idVerifValidationDetail) {
        return smsMapper.insertMsfCrtVldDtl(idVerifValidationDetail);
    }

    @Override public Integer registerSmsInfo(MspSmsData mspSmsData) {
        return mspSmsMapper.insertSmsData(mspSmsData);
    }

    @Override public Integer registerKakaoInfo(MspSmsData mspSmsData) {
        return mspSmsMapper.insertKakaoData(mspSmsData);
    }
}
