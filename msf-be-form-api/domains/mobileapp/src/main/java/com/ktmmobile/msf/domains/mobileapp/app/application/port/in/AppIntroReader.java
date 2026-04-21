package com.ktmmobile.msf.domains.mobileapp.app.application.port.in;

import jakarta.validation.Valid;

import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;

public interface AppIntroReader {

    IntroResponse intro(IntroRequest request);

    AppInitResponse initLogin(AppInitRequest request);

    Integer removeModel(AppInitRequest request);

    Integer registModel(AppRegistRequest request);

    Integer modifyBioSetting(AppRegistRequest request);
}
