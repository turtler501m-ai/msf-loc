package com.ktis.mcpif.mPlatform.event;

public enum MplatformNamespace {
    SEL("sel", "http://selfcare.so.itl.mvno.kt.com/"),
    DEL("del", "http://delivery.so.itl.mvno.kt.com/"),
    JUIC("juic", "http://juice.so.itl.mvno.kt.com/");

    private String prefix;
    private String uri;

    MplatformNamespace(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
