package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import lombok.Data;

@Data
public class InqrSvcNoInfoInDTO {

    private String asgnAgncId; //할당 대리점 ID
    private String asgnAgncYn; //할당번호조회여부
    private String cntryCd; //국가코드
    private String custNo; //고객번호
    private String inqrBase; //조회페이지
    private String inqrCascnt; //조회카운트
    private String nowSvcIndCd; //2G,3G구분
    private String searchGubun; //조회구분 코드
    private String arPrGubun; //예약/선호번호 구분코드
    private String tlphNoChrcCd; //전화번호특성코드
    private String tlphNoIndCd; //전화번호구분코드
    private String tlphNoPtrn; //번호조회패턴
    private String tlphNoUseCd; //번호사용용도코드
    private String tlphNoUseMntCd; //번호사용상세사유코드
}
