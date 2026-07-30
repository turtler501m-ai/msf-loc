package com.ktmmobile.msf.domains.shared.common.sms.application.port.out;

import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.IdVerifValidationDetail;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.MspSmsData;

public interface SmsRepository {

    Integer registerMsfCrtVldDtl(IdVerifValidationDetail idVerifValidationDetail);

    Integer registerSmsInfo(MspSmsData mspSmsData);

    Integer registerKakaoInfo(MspSmsData mspSmsData);
}
