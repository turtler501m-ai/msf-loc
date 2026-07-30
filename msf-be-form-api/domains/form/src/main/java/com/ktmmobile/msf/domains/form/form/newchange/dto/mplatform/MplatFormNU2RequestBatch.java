package com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "inDto") //  XML 최상위 루트 태그명 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class MplatFormNU2RequestBatch {

    private String osstOrdNo; //OSST오더번호
    private String gubun; //업무구분코드
    private String tlphNo; //전화번호
    private String custNo; //고객번호
    private String tlphNoStatChngRsnCd; //전화번호상태변경사유코드
    private String tlphNoStatCd; //전화번호상태코드
    private String custTypeCd; //고객유형코드
    private String nowSvcIndCd; //현서비스구분코드
    private String encdTlphNo; //암호화전화번호 : NU1(번호 조회)를 통해 취득한 encdTlphNo
    private String mpngTlphNoYn; //매핑전화번호여부 - 미사용 컬럼
    private String asgnagncId; //할당대리점아이디
}
