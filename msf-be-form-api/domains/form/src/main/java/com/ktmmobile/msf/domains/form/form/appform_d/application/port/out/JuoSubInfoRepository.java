package com.ktmmobile.msf.domains.form.form.appform_d.application.port.out;

import java.util.Optional;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.JuoSubInfo;

public interface JuoSubInfoRepository {

    Optional<JuoSubInfo> getJuoSubInfo(JuoSubInfoCondition condition);
}
