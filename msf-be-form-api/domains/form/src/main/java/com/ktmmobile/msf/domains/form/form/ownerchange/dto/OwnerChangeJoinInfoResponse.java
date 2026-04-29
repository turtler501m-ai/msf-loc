package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
public class OwnerChangeJoinInfoResponse {

    private String addr; // 주소
    private String email; // 이메일 주소
    private String homeTel; // 전화번호
    private String initActivationDate; // 가입일
    private String ctn;
    private String custId;
    private String ncn;
    private String userId;
}
