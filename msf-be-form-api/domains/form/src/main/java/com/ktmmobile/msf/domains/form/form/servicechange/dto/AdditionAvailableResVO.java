package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.util.List;

/**
 * 가입가능 부가서비스 목록 응답 VO
 *
 * POST /api/v1/addition/available-list 응답
 *
 * MSF DB에서 관리하는 부가서비스 목록과
 * M플랫폼 X97로 조회한 이용중 SOC를 비교하여 useYn 세팅.
 * 유료(listA) / 무료·번들(listC) / 전체(list) 3가지로 분리 반환.
 *
 * ASIS에서는 HashMap<String,Object>에 "list", "listA", "listC" 키로 반환하던 것을
 * 명시적 VO로 교체.
 *
 * @see McpRegServiceDto 각 부가서비스 항목
 *      (rateCd/SOC코드, rateNm/명칭, baseAmt/기본료, svcRelTp/관계유형, useYn/이용여부)
 */
public class AdditionAvailableResVO {

    /**
     * 전체 부가서비스 목록 (일반+로밍, "PL249Q800" 더미 제외)
     * useYn="Y": 현재 이용중 (X97 기준)
     * useYn="N": 미가입
     */
    private List<McpRegServiceDto> list;

    /**
     * 유료 부가서비스 목록
     * 조건: !(baseAmt="0" AND svcRelTp="C") AND svcRelTp≠"B"
     * 즉, 월정액이 있거나 번들이 아닌 항목
     */
    private List<McpRegServiceDto> listA;

    /**
     * 무료/번들 부가서비스 목록
     * 조건: (baseAmt="0" AND svcRelTp="C") OR svcRelTp="B"
     * - svcRelTp="C": 무료 구성 서비스
     * - svcRelTp="B": 번들 서비스 (요금제에 포함)
     */
    private List<McpRegServiceDto> listC;

    public List<McpRegServiceDto> getList() { return list; }
    public void setList(List<McpRegServiceDto> list) { this.list = list; }

    public List<McpRegServiceDto> getListA() { return listA; }
    public void setListA(List<McpRegServiceDto> listA) { this.listA = listA; }

    public List<McpRegServiceDto> getListC() { return listC; }
    public void setListC(List<McpRegServiceDto> listC) { this.listC = listC; }
}
