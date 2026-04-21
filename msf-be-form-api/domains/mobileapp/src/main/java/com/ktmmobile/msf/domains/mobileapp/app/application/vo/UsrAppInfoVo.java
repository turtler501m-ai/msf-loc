package com.ktmmobile.msf.domains.mobileapp.app.application.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsrAppInfoVo {
    private String userId;
    private String osCd;
    private String version;
    private String autoLoginYn;
    private String bioLoginYn;
    private String apvSttusCd;
}
