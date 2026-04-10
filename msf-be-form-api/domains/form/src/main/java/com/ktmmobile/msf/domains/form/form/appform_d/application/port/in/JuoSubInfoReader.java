package com.ktmmobile.msf.domains.form.form.appform_d.application.port.in;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoResponse;

public interface JuoSubInfoReader {

    JuoSubInfoResponse getJuoSubInfo(JuoSubInfoCondition condition);
}
