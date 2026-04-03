package com.ktmmobile.msf.form.servicechange.dto;

import java.util.List;

public class AdditionAvailableResVO {

    private List<McpRegServiceDto> list;    // 전체 목록
    private List<McpRegServiceDto> listA;   // 유료
    private List<McpRegServiceDto> listC;   // 무료

    public List<McpRegServiceDto> getList() { return list; }
    public void setList(List<McpRegServiceDto> list) { this.list = list; }

    public List<McpRegServiceDto> getListA() { return listA; }
    public void setListA(List<McpRegServiceDto> listA) { this.listA = listA; }

    public List<McpRegServiceDto> getListC() { return listC; }
    public void setListC(List<McpRegServiceDto> listC) { this.listC = listC; }
}
