package com.ktmmobile.msf.domains.eformsign.feature.application.port.in;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkResponse;

public interface EformWriter {

    EformSendLinkResponse eformsignSendLink(EformSendLinkRequest request);
}
