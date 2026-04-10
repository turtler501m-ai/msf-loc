package com.ktmmobile.msf.domains.form.system.cert.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.system.cert.dto.CertDto;

// [ASIS] CertServiceImpl — 기존 구현체는 com.ktmmobile.msf.common.* 패키지 참조로 미완성 상태.
// 기동을 위한 스텁 구현. 실제 로직 복원 시 교체 필요.
@Service
public class CertServiceImpl implements CertService {

    @Override
    public int getStepCnt() { return 0; }

    @Override
    public Map<String, Object> getCertInfo(CertDto certDto) { return new HashMap<>(); }

    @Override
    public Map<String, String> isAuthStepApplyUrl(HttpServletRequest request) { return new HashMap<>(); }

    @Override
    public int delStepInfo(int step) { return 0; }

    @Override
    public int updateCrtReferer() { return 0; }

    @Override
    public int getModuTypeStepCnt(String moduType, String ncType) { return 0; }

    @Override
    public Map<String, String> vdlCertInfo(String compType, String[] keyArr, String[] valueArr) { return new HashMap<>(); }

    @Override
    public Map<String, String> smsAuthCertMenuType(String menuType, String smsAuthType) { return new HashMap<>(); }

    @Override
    public String getNcTypeForCrt(String userSsn, String contractNum) { return ""; }

    @Override
    public String getCertReferer(long crtSeq) { return ""; }
}
