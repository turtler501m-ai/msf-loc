package com.ktmmobile.msf.domains.form.form.appform_d.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdResponse;

public interface SmartCdReader {

    List<SmartCdResponse> getCodeList(SmartCdCondition condition);
}
