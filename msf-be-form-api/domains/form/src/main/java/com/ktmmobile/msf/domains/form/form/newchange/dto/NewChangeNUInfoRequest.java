package com.ktmmobile.msf.domains.form.form.newchange.dto;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeNUInfoRequest {

    private String resNo; //예약번호

    //NU1 InDto
    private String osstOrdNo; //OSST오더번호
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


    //NU2 InDto
    //private String osstOrdNo; //OSST오더번호
    private String gubun; //업무구분코드
    @XmlJavaTypeAdapter(EncryptAdapter.class)
    private String tlphNo; //전화번호
    //private String custNo; //고객번호
    private String tlphNoStatChngRsnCd; //전화번호상태변경사유코드
    private String tlphNoStatCd; //전화번호상태코드
    private String custTypeCd; //고객유형코드
    //private String nowSvcIndCd; //현서비스구분코드
    private String encdTlphNo; //암호화전화번호
    private String mpngTlphNoYn; //매핑전화번호여부
    private String asgnagncId; //할당대리점아이디
}
