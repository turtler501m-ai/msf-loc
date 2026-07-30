package com.ktmmobile.msf.domains.shared.common.sms.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MspSmsData {
    private Long msgId;
    private Integer msgType;
    private Integer msgTypeSecond;
    private String subject;
    private String scheduleTime;
    private String submitTime;
    private String message;
    private String callbackNum;
    private String rcptData;
    private String kAdflag;
    private String kTmplcode;
    private String kMessage;
    private String kSenderkey;
    private String reserved01;
    private String reserved02;
    private String reserved03;
    private String failSend;
}
