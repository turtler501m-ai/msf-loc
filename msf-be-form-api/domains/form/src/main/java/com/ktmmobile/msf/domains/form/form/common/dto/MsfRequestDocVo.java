package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestDocVo {

    String requestDocSeq; //가입신청서 문서 일련번호
    String fileTypeCd; //파일유형코드
    String filePathNm; //파일경로명
    String fileNm; //파일명
}
