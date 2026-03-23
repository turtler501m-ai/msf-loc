package com.ktmmobile.msf.formSvcChg.dto;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;

import java.util.List;

/**
 * 부가서비스 변경 사전체크 요청 DTO (Y24).
 * 무선데이터차단, 정보료상한 [확인] 버튼 클릭 시 사용.
 */
public class AdditionPreCheckReqDto extends SvcChgInfoDto {

    /** 사전체크 대상 SOC 코드 목록 */
    private List<String> socList;

    public List<String> getSocList() { return socList; }
    public void setSocList(List<String> socList) { this.socList = socList; }
}
