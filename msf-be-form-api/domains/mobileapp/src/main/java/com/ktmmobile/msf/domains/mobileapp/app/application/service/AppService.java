package com.ktmmobile.msf.domains.mobileapp.app.application.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginSessionFlowProcessor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.support.context.LoginSessionContext;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppDownloadRequest;
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
        int reqAppOsVer = Integer.parseInt(getMajorVersion(request.getAppOsVer()));
        if (request.getOs().equals("A")) {
            if (!(reqAppOsVer > 12)) {
                throw new SimpleDomainException("Android 12 이상에 설치가 가능합니다.");
            }
        } else if (request.getOs().equals("I")) {
            if (!(reqAppOsVer > 15)) {
                throw new SimpleDomainException("iOS 15 이상에 설치가 가능합니다.");
            }
        } else {
            throw new SimpleDomainException("설치 가능한 OS가 아닙니다.");
        }

        IntroResponse res = repository.getIntro(request);
        if (res != null) {
            String reqVer = request.getVersion();
            String resVer = res.getVersion();
            if (resVer.equals(reqVer)) {
                res.setUpdate("N");
                res.setUpdateUrl("");
                res.setUpdateMsg("");
            } else {
                res.setUpdate("Y");
            }
            return res;
        }
        throw new SimpleDomainException("App 정보 조회에 실패했습니다.");
    }

    @Transactional(readOnly = true)
    @Override
    public AppInitResponse initLogin(AppInitRequest request) {
        AppInitResponse result = new AppInitResponse();
        String apvSttusCd = "A";
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid(), apvSttusCd);
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

    @LoginSessionContext
    @Transactional
    @Override
    public Integer registModel(AppRegistRequest request) {
        LoginSessionUser sessionUser = loginSessionFlowProcessor.getSessionUser(request.getLoginSessionId());
        log.debug("registModel sessionUser.userId:{}", sessionUser.userId());
        String sesUserId = sessionUser.userId();
        request.setUserId(sesUserId);
        request.setAppNm("스마트서식지");
        request.setApvSttusCd("A");
        request.setAutoLoginYn("N");
        request.setBioLoginYn("N");

        Integer result = 0;
        String apvSttusCd = "";
        boolean isNew = true;
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid(), apvSttusCd);
        if (voList != null && !voList.isEmpty()) {
            for (UsrAppInfoVo vo: voList) {
                request.setUserId(vo.getUserId());
                if (vo.getUserId().equals(sesUserId)) {
                    request.setApvSttusCd("A");
                    request.setUserId(sesUserId);
                    isNew = false;
                } else {
                    request.setApvSttusCd("C");
                }
                result = repository.modifyBioSetting(request);
            }
        }
        if (isNew) {
            request.setUserId(sesUserId);
            request.setApvSttusCd("A");
            result = repository.registUserApp(request);
        }
        loginSessionFlowProcessor.completeAction(request.getLoginSessionId(), LoginRequiredAction.DEVICE_AUTH_CODE);
        return result;
    }

    @Override
    public Integer modifyBioSetting(AppRegistRequest request) {
        Integer result = 0;
        String apvSttusCd = "A";
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid(), apvSttusCd);

        String authUserId = AuthenticationUtils.getUser().getUserId();
        log.debug("modifyBioSetting authUserId:{}", authUserId);
        if (voList == null) {
            throw new SimpleDomainException("수정에 실패했습니다.");
        } else {
            request.setUserId(authUserId);
            result = repository.modifyBioSetting(request);

            if (request.getBioLoginYn().equals("Y")) {
                repository.insertAppTokenTxn(request);
            } else {
                repository.updateAppTokenTxn(request);
            }
        }
        return result;
    }

    @LoginSessionContext
    @Transactional
    @Override
    public Integer removeModel(AppInitRequest request) {
        String apvSttusCd = "";
        List<UsrAppInfoVo> voList = repository.getUserApp(request.getDeviceUuid(), apvSttusCd);
        if (voList == null) {
            throw new SimpleDomainException("삭제에 실패했습니다.");
        }
        Integer result = repository.removeUserApp(request);
        return result;
    }

    public static String getMajorVersion(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        // 첫 번째 점(.)의 위치를 찾습니다.
        int dotIndex = str.indexOf(".");

        // 만약 점(.)이 없다면 (예: "12"가 그냥 들어온 경우) 원본을 그대로 반환
        if (dotIndex == -1) {
            return str;
        }

        // 처음부터 첫 번째 점 직전까지만 잘라냅니다.
        return str.substring(0, dotIndex);
    }

    @Override
    public List<IntroResponse> appDownload(AppDownloadRequest request) {
        List<IntroResponse> res = new ArrayList<>();
        if (repository.checkUserApp(request) > 0) {
            res = repository.getDownloadList();
        } else {
            throw new SimpleDomainException("입력하신 아이디는 사용 불가합니다. 대리점에 문의해 주세요.");
        }
        return res;
    }
}
