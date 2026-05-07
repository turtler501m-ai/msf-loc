package com.ktmmobile.msf.domains.mobileapp.app.application.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.fieldmapper.AppUsrInfoFieldMapper;
import com.ktmmobile.msf.domains.mobileapp.app.application.port.in.AppIntroReader;
import com.ktmmobile.msf.domains.mobileapp.app.application.port.out.AppRepository;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppService implements AppIntroReader {

    private final AppRepository repository;
    private final AppUsrInfoFieldMapper appUsrInfoFieldMapper;
    private final LoginSessionFlowProcessor loginSessionFlowProcessor;

    @Transactional(readOnly = true)
    @Override
    public IntroResponse intro(IntroRequest request) {
        // 앱최소버전
        double reqAppOsVer = Double.parseDouble(request.getAppOsVer());
        if (request.getOs().equals("A")) {
            if (!(reqAppOsVer > 12.0)) {
                throw new SimpleDomainException("Android 12 이상에 설치가 가능합니다.");
            }
        } else if (request.getOs().equals("I")) {
            if (!(reqAppOsVer > 12.0)) {
                throw new SimpleDomainException("iOS 15 이상에 설치가 가능합니다.");
            }
        } else {
            throw new SimpleDomainException("설치 가능한 OS가 아닙니다.");
        }

        IntroResponse res = repository.getIntro(request);
        if (res != null) {
            double reqVer = Double.parseDouble(request.getVersion());
            double resVer = Double.parseDouble(res.getVersion());
            if (resVer > reqVer) {
                res.setUpdate("Y");
            } else {
                res.setUpdate("N");
                res.setUpdateUrl("");
                res.setUpdateMsg("");
            }
            return res;
        }
        throw new SimpleDomainException("App 정보 조회에 실패했습니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public AppInitResponse initLogin(AppInitRequest request) {
        AppInitResponse result = new AppInitResponse();
        UsrAppInfoVo vo = repository.getUserApp(request.getDeviceUuid());
        log.debug("initLogin vo:{}", vo);
        if (vo != null && vo.getOsCd() != null) {
            result = appUsrInfoFieldMapper.toAppInitResponse(vo);
        } else {
            result.setApvSttusCd("C");
        }
        return result;
    }

    @Transactional
    @Override
    public Integer registModel(AppRegistRequest request) {
        LoginSessionUser sessionUser = loginSessionFlowProcessor.getSessionUser(request.getLoginSessionId());
        request.setUserId(sessionUser.userId());
        request.setAppNm("스마트서식지");
        request.setApvSttusCd("A");
        request.setAutoLoginYn("Y");
        request.setBioLoginYn("N");

        UsrAppInfoVo vo = repository.getUserApp(request.getDeviceUuid());
        Integer result = vo != null
            ? repository.modifyBioSetting(request)
            : repository.registUserApp(request);
        loginSessionFlowProcessor.completeAction(request.getLoginSessionId(), LoginRequiredAction.DEVICE_AUTH_CODE);
        return result;
    }

    @Override
    public Integer modifyBioSetting(AppRegistRequest request) {
        Integer retInt = 0;
        UsrAppInfoVo vo = repository.getUserApp(request.getDeviceUuid());
        log.debug("removeModel vo:{}", vo);
        if (vo == null) {
            throw new SimpleDomainException("수정에 실패했습니다.");
        }
        return repository.modifyBioSetting(request);
    }

    @Transactional
    @Override
    public Integer removeModel(AppInitRequest request) {
        Integer retInt = 0;
        UsrAppInfoVo vo = repository.getUserApp(request.getDeviceUuid());
        log.debug("removeModel vo:{}", vo);
        if (vo == null) {
            throw new SimpleDomainException("삭제에 실패했습니다.");
        }
        return repository.removeUserApp(request);
    }
}
