package com.ktmmobile.msf.domains.eformsign.feature.application.port.in;

import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformFileDownloadResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformsignFileDownloadRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwResponse;
import com.ktmmobile.msf.domains.form.form.newchange.dto.NewChangeRequest;

public interface EformReader {

    EformFileDownloadResponse eformsignFileDownload(EformsignFileDownloadRequest request);

    EformValidateResponse validateEformSignature(EformValidateRequest request);

    VerifyFormPwResponse verifyFormPw(VerifyFormPwRequest request);

    EformResponse getFormInfo(NewChangeRequest request);
}
