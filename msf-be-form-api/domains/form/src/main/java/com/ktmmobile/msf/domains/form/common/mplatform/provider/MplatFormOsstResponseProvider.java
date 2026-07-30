package com.ktmmobile.msf.domains.form.common.mplatform.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MplatFormOsstResponseProvider {

    private final MplatFormOsstMockResponseProvider mockResponseProvider;

    @Value("${LOCAL_TEST:false}")
    private boolean localTest;

    public MplatFormOsstMockResponse simpleOpenResponse(String appEventCd) {
        if (!localTest) {
            return MplatFormOsstMockResponse.notLocal();
        }
        return mockResponseProvider.simpleOpenResponse(appEventCd);
    }

    public MplatFormOsstMockResponse simpleOpenDtoResponse(String appEventCd) {
        if (!localTest) {
            return MplatFormOsstMockResponse.notLocal();
        }
        return MplatFormOsstMockResponse.response(mockResponseProvider.simpleOpenDtoResponse(appEventCd));
    }

    public MplatFormOsstMockResponse osstWebResponse(String appEventCd) {
        if (!localTest) {
            return MplatFormOsstMockResponse.notLocal();
        }
        return mockResponseProvider.osstWebResponse(appEventCd);
    }
}
