package com.ktmmobile.msf.domains.shared.common.sms.application.port.in;

import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;

public interface CommonSmsWriter {

    Boolean sendSms(CommonSmsRequest request);

    String sendOtpSms(CommonSmsRequest request);

    Boolean verifyOtpSms(CommonSmsRequest request);
}
