package com.ktmmobile.msf.domains.mobileapp.app.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
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
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid());
        UsrAppInfoVo vo = null;
        if (!voList.isEmpty()) {
            vo = voList.get(0);
        }
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
        String sesUserId = sessionUser.userId();
        request.setUserId(sesUserId);
        request.setAppNm("스마트서식지");

        Integer result = 0;
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid());
        if (voList == null) {
            result = repository.registUserApp(request);
        } else {
            for (UsrAppInfoVo vo: voList) {
                if (vo.getUserId().equals(sesUserId)) {
                    request.setApvSttusCd("A");
                    //request.setAutoLoginYn("Y");
                    request.setBioLoginYn("N");
                } else {
                    request.setApvSttusCd("C");
                    //request.setAutoLoginYn("N");
                    request.setBioLoginYn("N");
                }
                result = repository.modifyBioSetting(request);
            }
        }
        loginSessionFlowProcessor.completeAction(request.getLoginSessionId(), LoginRequiredAction.DEVICE_AUTH_CODE);
        return result;
    }

    @Override
    public Integer modifyBioSetting(AppRegistRequest request) {
        Integer result = 0;
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid());

        String authUserId = AuthenticationUtils.getUser().getUserId();
        if (voList == null) {
            throw new SimpleDomainException("수정에 실패했습니다.");
        } else {
            for (UsrAppInfoVo vo: voList) {
                if (vo.getUserId().equals(authUserId)) {
                    log.debug("modifyBioSetting vo:{}", vo);
                    result = repository.modifyBioSetting(request);
                }
            }
        }
        return result;
    }

    @Transactional
    @Override
    public Integer removeModel(AppInitRequest request) {
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid());
        if (voList == null) {
            throw new SimpleDomainException("삭제에 실패했습니다.");
        }
        Integer result = repository.removeUserApp(request);
        // 삭제 필요..
        //if (result != null && result > 0 && StringUtils.hasText(vo.getUserId())) {
        //    loginSessionFlowProcessor.revokeAuthentication(UserType.FORM_USER, vo.getUserId());
        //}
        return result;
    }
}
