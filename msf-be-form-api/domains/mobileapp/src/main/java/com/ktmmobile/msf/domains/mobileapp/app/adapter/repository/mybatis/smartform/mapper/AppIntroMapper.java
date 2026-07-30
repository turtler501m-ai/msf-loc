package com.ktmmobile.msf.domains.mobileapp.app.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppDownloadRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

@Mapper
@AutoAuditing
public interface AppIntroMapper {

    IntroResponse selectIntro(IntroRequest request);

    List<UsrAppInfoVo> selectUserApp(String uuid, String apvSttusCd);

    Integer removeUserApp(AppInitRequest request);

    Integer registUserApp(AppRegistRequest request);

    Integer updateBioSetting(AppRegistRequest request);

    List<IntroResponse> selectAppDownloadList();

    void insertAppTokenTxn(AppRegistRequest request);

    void updateAppTokenTxn(AppRegistRequest request);

    Integer checkUserApp(AppDownloadRequest request);
}
