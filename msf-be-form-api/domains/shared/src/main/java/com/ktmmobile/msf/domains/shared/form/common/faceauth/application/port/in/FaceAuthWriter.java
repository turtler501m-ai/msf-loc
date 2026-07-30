package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.port.in;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthConfirmResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthIgnoreResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthQrResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthResultResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSendRequest;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthSmsResponse;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto.FaceAuthUrlResponse;

public interface FaceAuthWriter {

    FaceAuthConfirmResponse requestFaceAuthConfirm(FaceAuthConfirmRequest request);

    FaceAuthUrlResponse requestFaceAuthUrl(FaceAuthSendRequest request);

    FaceAuthSmsResponse requestFaceAuthSms(FaceAuthSendRequest request);

    FaceAuthQrResponse requestFaceAuthQr(FaceAuthSendRequest request);

    FaceAuthIgnoreResponse requestFaceAuthIgnore(FaceAuthSendRequest request);

    FaceAuthResultResponse requestFaceAuthResultPrev(FaceAuthResultRequest request);

    FaceAuthResultResponse requestFaceAuthResult(FaceAuthResultRequest request);
}
