package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NmcpAppFormMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pageNm;   // 페이지명
    private String pageCode; // 페이지코드
    private String fileNm;   // 파일명
    private String fileExt;
    private String useYn;    // 사용유무 프로그램에서 따로 영향이 없음 (사용하지 않음)
    private String cretId;   // 생성자ID
    private String amdId;    // 수정자ID
    private Date cretDt;     // 생성년월
    private Date amtDt;      // 수정년월
}
