package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Class Name : CmnIntmMdl
 * @Description : msp 상품상세정보 (msp cmn_intm_mdl 테이블과 대응된다)
 *
 * @author : ant
 * @Create Date : 2016. 2. 11.
 */
@Getter
@Setter
@NoArgsConstructor
public class CmnIntmMdl implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prdtId; // 제품id
    private String rprsPrdtId; // 대표 id
    private String rprsYn; // 대표여부
    private String prdtNm; // 제품명
    private String prdtCode; // 제품코드
    private String prdtTypeCd; // 제품유형코드
    private String prdtIndCd; // 제품구분코드
    private String prdtColrCd; // 제품색상코드
    private String mnfctId; // 제조사id
    private String prdtLnchDt; // 제품출시일자
    private String prdtDt; // 제품단종일자
    private String regId; // 등록자
    private Date regDttm; // 등록일시
    private String rvisnId; // 수정자
    private Date rvisnDttm; // 수정일시
}
