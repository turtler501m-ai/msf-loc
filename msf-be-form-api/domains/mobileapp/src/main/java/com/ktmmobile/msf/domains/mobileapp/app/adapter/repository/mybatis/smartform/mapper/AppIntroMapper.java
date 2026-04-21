package com.ktmmobile.msf.domains.mobileapp.app.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

@Mapper
public interface AppIntroMapper {

    IntroResponse selectIntro(IntroRequest request);

    UsrAppInfoVo selectUserApp(String uuid);

    Integer removeUserApp(AppInitRequest request);

    Integer registUserApp(AppRegistRequest request);

    Integer updateBioSetting(AppRegistRequest request);
}
