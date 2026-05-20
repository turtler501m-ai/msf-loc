package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Class Name : MspOrgDto
 * @Description : MSP의 제조자공급사 정보
 *
 * @author : ant
 * @Create Date : 2016. 1. 4.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspOrgDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mnfctId; // 제조사ID
    private String mnfctNm; // 제조사명
    private String bizRegNum; // 사업자등록번호
    private String rprsenNm; // 대표자명
    private String zipCd; // 우편번호
    private String addr; // 주소
    private String dtlAddr; // 상세 주소
    private String mnfctYn; // 제조사여부 아닐경우(공급사)
    private String telnum; // 전화번호
    private String fax; // 팩스번호
    private String email; // 이메일
}
