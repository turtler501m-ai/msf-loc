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
    public Integer insertUserHistory(String userId) {
        return loginMapper.insertUserHistory(userId);
    }

    @Override
    public Integer modifyPass(PassChangeRequest request) {
        return loginMapper.updateUserPass(request);
    }

    @Override
    public LoginResponse getUserHstInfo(LoginRequest loginRequest) {
        return loginMapper.selectUserHstInfo(loginRequest);
    }

    @Override
    public Integer insertAdminUserHistory(String userId) {
        return loginMapper.insertAdminUserHistory(userId);
    }

    @Override
    public Integer modifyAdminPass(PassChangeRequest request) {
        return loginMapper.updateAdminUserPass(request);
    }

}
