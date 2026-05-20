package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NmcpLinkInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int linkId;       // 고객포탈 링크 일련번호(PK)
    private String linkNm;    // 링크명
    private String linkUrl;
    private String linkDesc;  // 링크 설명
    private String linkType;  // 링크 타겟 (현재창 :01,새창 :02)
    private String cretId;    // 등록자 아이디
    private String amdId;     // 수정자 아이디
    private Date cretDt;      // 등록일
    private Date amtDt;       // 수정일
}
