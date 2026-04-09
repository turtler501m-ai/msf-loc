package com.ktmmobile.msf.formSvcChg.dto;

import java.util.List;

/**
 * 서비스변경 업무 카테고리 DTO. MSF_CD_GROUP_BAS 행 매핑 + 하위 옵션 목록 포함.
 */
public class SvcChgBaseCategoryDto {

    private String groupCd;
    private String groupNm;
    private String svcTgtCd;
    private int sortSeq;
    private List<SvcChgBaseOptionDto> options;

    public String getGroupCd() { return groupCd; }
    public void setGroupCd(String groupCd) { this.groupCd = groupCd; }

    public String getGroupNm() { return groupNm; }
    public void setGroupNm(String groupNm) { this.groupNm = groupNm; }

    public String getSvcTgtCd() { return svcTgtCd; }
    public void setSvcTgtCd(String svcTgtCd) { this.svcTgtCd = svcTgtCd; }

    public int getSortSeq() { return sortSeq; }
    public void setSortSeq(int sortSeq) { this.sortSeq = sortSeq; }

    public List<SvcChgBaseOptionDto> getOptions() { return options; }
    public void setOptions(List<SvcChgBaseOptionDto> options) { this.options = options; }
}
