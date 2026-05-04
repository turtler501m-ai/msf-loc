package com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserInfoRow;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserRow;

@AutoAuditing
@Mapper
public interface LoginUserMapper {

    FormLoginUserRow selectFormByUserId(String userId);

    FormLoginUserRow selectFormByDeviceUuid(String deviceUuid);

    FormLoginUserInfoRow selectFormUserInfoByUserId(String userId);

    FormLoginUserInfoRow selectFormUserInfoByUserIdAndDeviceUuid(@Param("userId") String userId, @Param("deviceUuid") String deviceUuid);

    int updateFormLoginSuccess(String userId);

    int updateFormLoginFailure(@Param("userId") String userId, @Param("userStatusCode") String userStatusCode);

    int insertFormUserHistory(String userId);

    int updateFormDeviceLoginSuccess(String deviceUuid);

    int updateFormPassword(@Param("userId") String userId, @Param("encodedPassword") String encodedPassword);
}
