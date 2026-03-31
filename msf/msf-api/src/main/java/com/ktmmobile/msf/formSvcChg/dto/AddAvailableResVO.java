package com.ktmmobile.msf.formSvcChg.dto;

import java.util.List;

/**
 * 가입 가능 부가서비스 목록 조회 응답 VO.
 * POST /api/v1/addition/available-list 응답.
 * ASIS addSvcListAjax 반환 구조 (listA/listC/list) 에 대응.
 */
public class AddAvailableResVO {

    /** 유료 부가서비스 목록 (baseAmt != "0" 또는 svcRelTp != "C/B") */
    private List<McpRegServiceDto> listA;

    /** 무료 부가서비스 목록 (baseAmt == "0" AND svcRelTp == "C" 또는 svcRelTp == "B") */
    private List<McpRegServiceDto> listC;

    /** 전체 부가서비스 목록 (일반/로밍 필터 후) */
    private List<McpRegServiceDto> list;

    public List<McpRegServiceDto> getListA() { return listA; }
    public void setListA(List<McpRegServiceDto> listA) { this.listA = listA; }

    public List<McpRegServiceDto> getListC() { return listC; }
    public void setListC(List<McpRegServiceDto> listC) { this.listC = listC; }

    public List<McpRegServiceDto> getList() { return list; }
    public void setList(List<McpRegServiceDto> list) { this.list = list; }
}
