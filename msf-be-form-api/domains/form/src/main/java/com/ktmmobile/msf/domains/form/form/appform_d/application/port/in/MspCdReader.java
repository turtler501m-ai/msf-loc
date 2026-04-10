package com.ktmmobile.msf.domains.form.form.appform_d.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdResponse;

public interface MspCdReader {

    List<MspCdResponse> getCodeList(MspCdCondition condition);
}
