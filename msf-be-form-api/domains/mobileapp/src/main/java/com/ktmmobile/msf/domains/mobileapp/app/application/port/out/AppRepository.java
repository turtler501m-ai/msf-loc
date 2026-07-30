package com.ktmmobile.msf.domains.mobileapp.app.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppDownloadRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.vo.UsrAppInfoVo;

public interface AppRepository {

    IntroResponse getIntro(IntroRequest request);

    List<UsrAppInfoVo> getUserApp(String uuid, String apvSttusCd);

    Integer removeUserApp(AppInitRequest request);

    Integer registUserApp(AppRegistRequest request);

    Integer modifyBioSetting(AppRegistRequest request);

    List<IntroResponse> getDownloadList();

    void insertAppTokenTxn(AppRegistRequest request);

    void updateAppTokenTxn(AppRegistRequest request);

    Integer checkUserApp(AppDownloadRequest request);
}
