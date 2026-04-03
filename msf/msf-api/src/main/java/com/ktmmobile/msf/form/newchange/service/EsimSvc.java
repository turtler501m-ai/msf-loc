package com.ktmmobile.msf.form.newchange.service;

import com.ktmmobile.msf.form.newchange.dto.EsimDto;
import com.ktmmobile.msf.form.newchange.dto.McpUploadPhoneInfoDto;

import java.util.List;

public interface EsimSvc {

    public int insertMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);

    public EsimDto eSimChk(EsimDto esimDto);

    public EsimDto omdReg(EsimDto esimDto);

    public EsimDto niceAuthChk(EsimDto esimDto);

    public EsimDto eSimWatch(EsimDto esimDto);

    boolean checkAbuseImeiList(List<String> imeis);
}
