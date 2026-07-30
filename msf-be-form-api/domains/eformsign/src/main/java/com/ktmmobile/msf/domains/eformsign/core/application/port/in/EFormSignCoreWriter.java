package com.ktmmobile.msf.domains.eformsign.core.application.port.in;

import java.util.List;

public interface EFormSignCoreWriter {

    void cancelDocument(List<String> documentIds);
}
