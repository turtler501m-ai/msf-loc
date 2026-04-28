package com.ktmmobile.msf.domains.shared.common.sms.application.port.out;

import com.ktmmobile.msf.domains.shared.common.sms.application.dto.MspSmsData;

public interface SmsRepository {

    String getUserPhone(String userId);

    Integer registerSmsInfo(MspSmsData mspSmsData);
}
