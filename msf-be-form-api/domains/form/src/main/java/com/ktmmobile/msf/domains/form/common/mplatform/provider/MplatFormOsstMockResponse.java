package com.ktmmobile.msf.domains.form.common.mplatform.provider;

public final class MplatFormOsstMockResponse {

    private static final MplatFormOsstMockResponse NOT_LOCAL = new MplatFormOsstMockResponse(false, false, "");
    private static final MplatFormOsstMockResponse PASSTHROUGH_SUCCESS = new MplatFormOsstMockResponse(true, true, "");

    private final boolean localTest;
    private final boolean passthroughSuccess;
    private final String responseXml;

    private MplatFormOsstMockResponse(boolean localTest, boolean passthroughSuccess, String responseXml) {
        this.localTest = localTest;
        this.passthroughSuccess = passthroughSuccess;
        this.responseXml = responseXml == null ? "" : responseXml;
    }

    static MplatFormOsstMockResponse notLocal() {
        return NOT_LOCAL;
    }

    static MplatFormOsstMockResponse response(String responseXml) {
        return new MplatFormOsstMockResponse(true, false, responseXml);
    }

    static MplatFormOsstMockResponse passthroughSuccess() {
        return PASSTHROUGH_SUCCESS;
    }

    public boolean isLocalTest() {
        return localTest;
    }

    public boolean isPassthroughSuccess() {
        return passthroughSuccess;
    }

    public String responseXml() {
        return responseXml;
    }
}
