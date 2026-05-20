package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneSntyBasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodId;          // 상품id
    private String hndsetModelId;   // 단말기모델id
    private String hndsetModelNm;   // 단말기모델명
    private String atribCd1;        // 속성코드 1 (현재 안쓰고있음)
    private String atribNm1;        // 속성명1 (현재 안쓰고있음)
    private String atribValCd1;     // 속성값 코드1(색상)
    private String atribValNm1;     // 속성값명 1(색상)
    private String atribCd2;        // 속성코드 2 (현재 안쓰고있음)
    private String atribNm2;        // 속성명2 (현재 안쓰고있음)
    private String atribValCd2;     // 속성값 코드2(용량)
    private String atribValNm2;     // 속성값명 2(용량)
    private String atribCd3;        // 속성코드 3 (현재 안쓰고있음)
    private String atribNm3;        // 속성명3 (현재 안쓰고있음)
    private String atribValCd3;     // 속성값 코드3
    private String atribValNm3;     // 속성값명 3
    private String saleYn;          // 판매여부
    private String sdoutYn;         // 품절여부
    private String repProdYn;       // 대표상품여부
    private String cretId;          // 생성자아이디
    private String amdId;           // 수정자아이디
    private Date cretDt;            // 생성일시
    private Date amdDt;             // 수정일시
    private String rprsPrdtYn;      // 대표상품여부 Y/N
}
