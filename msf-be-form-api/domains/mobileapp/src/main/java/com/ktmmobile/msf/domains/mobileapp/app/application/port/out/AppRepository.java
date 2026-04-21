package com.ktmmobile.msf.domains.mobileapp.app.application.port.out;

import com.ktmmobile.msf.domains.mobileapp.app.adapter.repository.mybatis.smartform.mapper.AppIntroMapper;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

public interface AppRepository {

    IntroResponse getIntro(IntroRequest request);

    UsrAppInfoVo getUserApp(String uuid);

    Integer removeUserApp(AppInitRequest request);

    Integer registUserApp(AppRegistRequest request);

    Integer modifyBioSetting(AppRegistRequest request);
}
