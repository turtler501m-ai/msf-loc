package com.ktmmobile.msf.domains.mobileapp.app.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.mobileapp.app.adapter.repository.mybatis.smartform.mapper.AppIntroMapper;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.port.out.AppRepository;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

@RequiredArgsConstructor
@Repository
public class AppRepositoryImpl implements AppRepository {
    private final AppIntroMapper appIntroMapper;

    public IntroResponse getIntro(IntroRequest request) {
        return appIntroMapper.selectIntro(request);
    }

    @Override public UsrAppInfoVo getUserApp(String uuid) {
        return appIntroMapper.selectUserApp(uuid);
    }

    @Override public Integer removeUserApp(AppInitRequest request) {
        return appIntroMapper.removeUserApp(request);
    }

    @Override public Integer registUserApp(AppRegistRequest request) {
        return appIntroMapper.registUserApp(request);
    }

    @Override public Integer modifyBioSetting(AppRegistRequest request) {
        return appIntroMapper.updateBioSetting(request);
    }
}
