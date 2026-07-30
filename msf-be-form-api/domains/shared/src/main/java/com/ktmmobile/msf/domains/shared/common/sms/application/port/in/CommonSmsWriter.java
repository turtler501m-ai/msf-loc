package com.ktmmobile.msf.domains.shared.common.sms.application.port.in;

import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsRequest;
import com.ktmmobile.msf.domains.shared.common.sms.application.dto.CommonSmsResponse;

public interface CommonSmsWriter {

    Boolean sendSms(CommonSmsRequest request);

    Boolean sendKakao(CommonSmsRequest request);

    CommonSmsResponse sendOtpSms(CommonSmsRequest request);

    Boolean verifyOtpSms(CommonSmsRequest request);
}
