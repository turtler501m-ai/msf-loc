package com.ktmmobile.msf.domains.form.form.appform_d.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.MspCd;

public interface MspCdRepository {

    List<MspCd> getCodeList(MspCdCondition condition);
}
