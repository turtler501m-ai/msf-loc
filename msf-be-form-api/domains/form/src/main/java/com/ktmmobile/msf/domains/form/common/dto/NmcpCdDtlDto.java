package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class NmcpCdDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdGroupId;       // 코드그룹아이디
    private String cdGroupNm;
    private String cdGroupDesc;
    private String dtlCd;           // 상세코드
    private String dtlCdNm;         // 상세코드명
    private String dtlCdDesc;       // 상세코드설명
    private long indcOdrg;          // 표시순서
    private String useYn;           // 사용여부
    private String expnsnStrVal1;   // 확장문자열값1
    private String expnsnStrVal2;   // 확장문자열값2
    private String expnsnStrVal3;   // 확장문자열값3
    private String cdGroupCd;
    private String imgNm;           // 이미지명
    private String cretId;          // 생성자아이디
    private String amdId;           // 수정자아이디
    private Date cretDt;            // 생성일시
    private Date amdDt;             // 수정일시
    private int eventCdCtn;
    private String pstngStartDate;
    private String pstngEndDate;
    private String gexpnsnStrVal1;  // group 확장문자열 값
    private String gexpnsnStrVal2;
    private String gexpnsnStrVal3;
    private String docContent;
    private String upGrpCd;         // 상위그룹코드

    public String getDtlCdDesc() {
        if (StringUtils.isBlank(dtlCdDesc)) { return ""; }
        return dtlCdDesc;
    }

    public String getExpnsnStrVal1() {
        if (StringUtils.isBlank(expnsnStrVal1)) { return ""; }
        return expnsnStrVal1;
    }

    public String getExpnsnStrVal2() {
        if (StringUtils.isBlank(expnsnStrVal2)) { return ""; }
        return expnsnStrVal2;
    }

    public String getExpnsnStrVal3() {
        if (StringUtils.isBlank(expnsnStrVal3)) { return ""; }
        return expnsnStrVal3;
    }
}
