package com.ktmmobile.msf.domains.form.login.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;

@Mapper
@AutoAuditing
public interface LoginMapper {

    LoginResponse selectUserInfo(LoginRequest request);

    LoginResponse selectUserAppInfo(LoginRequest request);

    Integer updateLoginFail(LoginRequest request);

    Integer updateLoginSucc(LoginRequest request);

    @AutoAuditing(false)
    Integer insertUserHistory(String userId);

    Integer updateBioLoginSucc(LoginRequest request);

    Integer updateUserPass(PassChangeRequest request);
}
