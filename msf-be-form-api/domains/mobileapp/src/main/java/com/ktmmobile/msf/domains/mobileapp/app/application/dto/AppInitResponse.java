package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import lombok.Data;

@Data
public class AppInitResponse {
    private String osCd;
    private String version;
    private String autoLoginYn;
    private String bioLoginYn;
    private String apvSttusCd;
}
