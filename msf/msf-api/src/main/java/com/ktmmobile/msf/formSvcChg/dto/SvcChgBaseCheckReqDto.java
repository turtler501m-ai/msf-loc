package com.ktmmobile.msf.formSvcChg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 서비스체크 유효성 요청 DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvcChgBaseCheckReqDto {

    private List<String> selectedCodes;
    private boolean serviceCheckCompleted;

    public List<String> getSelectedCodes() { return selectedCodes; }
    public void setSelectedCodes(List<String> selectedCodes) { this.selectedCodes = selectedCodes; }

    public boolean isServiceCheckCompleted() { return serviceCheckCompleted; }
    public void setServiceCheckCompleted(boolean serviceCheckCompleted) { this.serviceCheckCompleted = serviceCheckCompleted; }
}
