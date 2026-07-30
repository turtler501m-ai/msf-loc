package com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out;

public interface GenerateKeyRepository {

    String getGeneratedResNo();

    Long getGeneratedRequestKey();

    Long getGeneratedCustRequestSeq();

    Long getGeneratedRequestStateSeq();
}
