package com.ktmmobile.msf.domains.form.form.appform_d.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.SmartCd;

public interface SmartCdRepository {

    List<SmartCd> getCodeList(SmartCdCondition condition);
}
