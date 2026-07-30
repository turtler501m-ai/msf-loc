package com.ktmmobile.msf.commons.logincore.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginCoreMapper {

    /**
     * 제한 IP 등록 여부 조회
     *
     * @param clientIp 클라이언트 IP
     * @param userId 사용자 ID
     * @return 등록 여부
     */
    boolean existsLimitedAccessIp(@Param("clientIp") String clientIp, @Param("userId") String userId);
}
