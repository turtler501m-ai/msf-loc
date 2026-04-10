package com.ktmmobile.msf.domains.form.form.appform_d.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.NmcpCd;

public interface NmcpCdRepository {

    List<NmcpCd> getCodeList(NmcpCdCondition condition);
}
