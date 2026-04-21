package com.ktmmobile.msf.domains.shared.form.common.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.application.dto.AuthSmsRequest;
import com.ktmmobile.msf.domains.shared.form.common.application.dto.CommonSmsRequest;

public interface CommonSmsWriter {

    Boolean sendAuthSms(AuthSmsRequest authSmsRequest);

    Boolean verifyAuthSms(AuthSmsRequest authSmsRequest);

    Boolean sendFormSms(CommonSmsRequest request);

    Boolean verifyFormSms(CommonSmsRequest request);
}
