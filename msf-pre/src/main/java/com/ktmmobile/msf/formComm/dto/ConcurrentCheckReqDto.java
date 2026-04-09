package com.ktmmobile.msf.formComm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 동시처리 불가 체크 요청 DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConcurrentCheckReqDto {

    /** 선택된 서비스변경 업무 코드 목록 */
    private List<String> selectedCodes;

    public List<String> getSelectedCodes() { return selectedCodes; }
    public void setSelectedCodes(List<String> selectedCodes) { this.selectedCodes = selectedCodes; }
}
