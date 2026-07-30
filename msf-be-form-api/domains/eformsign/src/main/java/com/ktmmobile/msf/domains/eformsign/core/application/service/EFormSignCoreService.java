package com.ktmmobile.msf.domains.eformsign.core.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.eformsign.core.application.dto.EFormSignCoreApiTokenResponse;
import com.ktmmobile.msf.domains.eformsign.core.application.port.in.EFormSignCoreReader;
import com.ktmmobile.msf.domains.eformsign.core.application.port.in.EFormSignCoreWriter;
import com.ktmmobile.msf.domains.eformsign.core.application.port.out.EFormSignCoreClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EFormSignCoreService implements EFormSignCoreReader, EFormSignCoreWriter {

    private final EFormSignCoreClient eFormSignCoreClient;

    @Override
    public EFormSignCoreApiTokenResponse getEformSignApiToken() {
        return eFormSignCoreClient.issueApiToken();
    }

    @Override
    public void cancelDocument(List<String> documentIds) {
        eFormSignCoreClient.cancelDocument(documentIds);
    }
}
