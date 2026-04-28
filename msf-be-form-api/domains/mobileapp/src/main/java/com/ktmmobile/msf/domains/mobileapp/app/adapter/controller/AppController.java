package com.ktmmobile.msf.domains.mobileapp.app.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppInitResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.AppRegistRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroRequest;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.IntroResponse;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.OnCreate;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.OnDelete;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.OnModify;
import com.ktmmobile.msf.domains.mobileapp.app.application.dto.OnSelect;
import com.ktmmobile.msf.domains.mobileapp.app.application.port.in.AppIntroReader;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final AppIntroReader appIntroReader;

    @PostMapping("/api/app/intro")
    public CommonResponse<IntroResponse> login(@RequestBody @Validated(OnSelect.class) IntroRequest request) {
        return ResponseUtils.ok(appIntroReader.intro(request));
    }

    @PostMapping("/api/app/login/init")
    public CommonResponse<AppInitResponse> init(@RequestBody @Validated(OnSelect.class) AppInitRequest request) {
        return ResponseUtils.ok(appIntroReader.initLogin(request));
    }

    @PostMapping("/api/app/model/register")
    public CommonResponse<Integer> modelRegist(@RequestBody @Validated(OnCreate.class) AppRegistRequest request) {
        return ResponseUtils.ok(appIntroReader.registModel(request));
    }

    @PostMapping("/api/app/model/remove")
    public CommonResponse<Integer> modelRemove(@RequestBody @Validated(OnDelete.class) AppInitRequest request) {
        return ResponseUtils.ok(appIntroReader.removeModel(request));
    }

    @PostMapping("/api/app/settingbio/modify")
    public CommonResponse<Integer> bioModify(@RequestBody @Validated(OnModify.class) AppRegistRequest request) {
        return ResponseUtils.ok(appIntroReader.modifyBioSetting(request));
    }

}
