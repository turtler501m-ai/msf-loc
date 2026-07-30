package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPrxBizHeaderResponseKey implements CommonEnum {
    APP_ENTER_PERSON_ID("appEntrPrsnId", "사업자코드"),
    APP_AGENT_CODE("appAgncCd", "대리점 코드"),
    APP_EVENT_CODE("appEventCd", "업무코드"),
    APP_SENDED_DATETIME("appSendDateTime", "MVNO사업자가 송신한 시간"),
    APP_RECEIVED_DATETIME("appRecvDateTime", "M-Platform에서 응답한시간"),
    APP_LOG_DATETIME("appLgDateTime", "M-Platform Log처리 시간"),
    APP_NSTEP_USER_ID("appNstepUserId", "KOS연동용 항목");

    private final String code;
    private final String title;
}
