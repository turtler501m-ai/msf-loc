package com.ktmmobile.msf.domains.form.form.appform_d.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdResponse;

public interface NmcpCdReader {

    List<NmcpCdResponse> getCodeList(NmcpCdCondition condition);
}
