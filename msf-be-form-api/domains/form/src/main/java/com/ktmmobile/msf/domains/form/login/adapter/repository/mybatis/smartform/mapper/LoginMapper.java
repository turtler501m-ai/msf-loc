package com.ktmmobile.msf.domains.form.login.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;

@Mapper
public interface LoginMapper {

    LoginResponse selectUserInfo(LoginRequest request);

    LoginResponse selectUserAppInfo(LoginRequest request);

    Integer updateLoginFail(LoginRequest request);

    Integer updateLoginSucc(LoginRequest request);

    Integer insertUserHistory(LoginRequest request);

    Integer updateBioLoginSucc(LoginRequest request);
}
