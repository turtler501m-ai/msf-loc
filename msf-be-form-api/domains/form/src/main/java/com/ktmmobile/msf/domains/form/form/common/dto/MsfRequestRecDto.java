package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 녹취파일 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class MsfRequestRecDto {

    private String formTypeCd; //신청서유형코드
    private String recFilePathNm; //녹취파일경로명
    private String recFileNm; //녹취파일명
}
