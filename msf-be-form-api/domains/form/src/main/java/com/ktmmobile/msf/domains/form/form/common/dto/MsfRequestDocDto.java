package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 구비서류
 */

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestDocDto {

    String fileTypeCd; //파일유형코드
    String filePathNm; //파일경로명
    String fileNm; //파일명
    Integer filePageNo; //파일페이지번호
}
