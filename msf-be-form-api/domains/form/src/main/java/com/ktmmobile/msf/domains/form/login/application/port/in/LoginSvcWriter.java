package com.ktmmobile.msf.domains.form.login.application.port.in;

import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;

public interface LoginSvcWriter {

    Integer modifyPassword(PassChangeRequest request);
}
