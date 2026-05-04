package com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row;

import java.time.LocalDateTime;

public record FormLoginUserRow(
    String userId,
    String pwd,
    String userSttusCd,
    String accessLimitYn,
    Integer loginChkCnt,
    String pwdChgYn,
    LocalDateTime pwdChgDt
) {
}
