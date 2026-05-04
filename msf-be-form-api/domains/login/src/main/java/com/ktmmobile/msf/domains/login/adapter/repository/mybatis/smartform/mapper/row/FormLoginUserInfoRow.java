package com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row;

public record FormLoginUserInfoRow(
    String userId,
    String userNm,
    String mobileNo,
    String agentCd,
    String shopCd,
    String apvSttusCd
) {
}
