package com.ktmmobile.msf.domains.eformsign.feature.application.port.out;

import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformValidateHttpResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformSendLinkResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignFileDownloadRequest;

public interface EformClient {

    EformValidateHttpResponse validateEformSignature(EformValidateRequest request);

    EformFileDownloadResponse eformsignFileDownload(EformsignFileDownloadRequest request);

    EformSendLinkResponse eformsignSendLink(EformSendLinkRequest request);
}
