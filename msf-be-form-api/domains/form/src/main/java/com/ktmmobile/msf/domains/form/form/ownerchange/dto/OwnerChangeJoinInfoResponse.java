package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Data;

@Data
public class OwnerChangeJoinInfoResponse {

    private String addr; // 주소
    private String email; // 이메일 주소
    private String homeTel; // 전화번호
    private String initActivationDate; // 가입일
}
