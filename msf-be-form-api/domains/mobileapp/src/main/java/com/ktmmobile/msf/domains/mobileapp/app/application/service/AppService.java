package com.ktmmobile.msf.domains.mobileapp.app.application.service;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
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

    @Transactional(readOnly = true)
    public IntroResponse intro(IntroRequest request) {
        IntroResponse res = repository.getIntro(request);
        if(res != null) {
            double reqVer = Double.parseDouble(request.getVersion());
            double resVer = Double.parseDouble(res.getVersion());
            if(resVer > reqVer) {
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
    public AppInitResponse initLogin(AppInitRequest request) {
        AppInitResponse result = new AppInitResponse();
        UsrAppInfoVo vo = repository.getUserApp(request.getUuid());
        log.debug("initLogin vo:{}", vo);
        if(vo != null && vo.getOsCd() != null) {
            result = appUsrInfoFieldMapper.toAppInitResponse(vo);
        } else {
            result.setApvSttusCd("C");
        }
        return result;
    }

    @Transactional
    public Integer registModel(AppRegistRequest request) {
        // 로그인 세션에서 adminId 가져옴
        request.setUserId("V000000003");  // molo - 수정 필요
        request.setAppNm("스마트서식지");
        request.setApvSttusCd("A");
        request.setAutoLoginYn("Y");
        request.setBioLoginYn("N");
        return repository.registUserApp(request);
    }

    @Override public Integer modifyBioSetting(AppRegistRequest request) {
        Integer retInt = 0;
        UsrAppInfoVo vo = repository.getUserApp(request.getUuid());
        log.debug("removeModel vo:{}", vo);
        if(vo == null) {
            throw new SimpleDomainException("수정에 실패했습니다.");
        }
        return repository.modifyBioSetting(request);
    }

    @Transactional
    public Integer removeModel(AppInitRequest request) {
        Integer retInt = 0;
        UsrAppInfoVo vo = repository.getUserApp(request.getUuid());
        log.debug("removeModel vo:{}", vo);
        if(vo == null) {
            throw new SimpleDomainException("삭제에 실패했습니다.");
        }
        return repository.removeUserApp(request);
    }

}
