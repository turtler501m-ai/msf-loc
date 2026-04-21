package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import lombok.Data;

@Data
public class IntroResponse {
    private String update;
    private String version;
    private String updateUrl;
    private String updateMsg;
}
