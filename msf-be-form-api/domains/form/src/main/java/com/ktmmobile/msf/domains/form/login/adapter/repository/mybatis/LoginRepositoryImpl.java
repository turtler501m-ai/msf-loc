package com.ktmmobile.msf.domains.form.login.adapter.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.login.adapter.repository.mybatis.smartform.mapper.LoginMapper;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;
import com.ktmmobile.msf.domains.form.login.application.port.out.LoginRepository;

@RequiredArgsConstructor
@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private final LoginMapper loginMapper;

    @Override
    public LoginResponse getUserInfo(LoginRequest request) {
        return loginMapper.selectUserInfo(request);
    }

    @Override
    public LoginResponse getUserAppInfo(LoginRequest request) {
        return loginMapper.selectUserAppInfo(request);
    }

    @Override public Integer updateLoginFail(LoginRequest request) {
        return loginMapper.updateLoginFail(request);
    }

    @Override public Integer updateLoginSucc(LoginRequest request) {
        return loginMapper.updateLoginSucc(request);
    }

    @Override public Integer insertUserHistory(String userId) {
        return loginMapper.insertUserHistory(userId);
    }

    @Override public Integer updateBioLoginSucc(LoginRequest request) {
        return loginMapper.updateBioLoginSucc(request);
    }

    @Override public Integer modifyPass(PassChangeRequest request) {
        return loginMapper.updateUserPass(request);
    }

}
