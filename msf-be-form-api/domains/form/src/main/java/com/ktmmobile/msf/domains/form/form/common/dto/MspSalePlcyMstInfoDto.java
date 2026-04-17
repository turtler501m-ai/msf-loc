package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MspSalePlcyMstInfoDto {

    /** 요금제리스트 */
    //private List<MspRateMstDto> usimMspRateMstList;

    /** 가입유형리스트 */
    //private List<MspPlcyOperTypeDto> usimMspPlcyOperTypeList;

    /**
     * 조직코드
     */
    private String orgnId;

    /**
     * 판매정책코드
     */
    private String salePlcyCd;

    /**
     * 판매정책명
     */
    private String salePlcyNm;

    /**
     * 판매시작일시
     */
    private String saleStrtDttm;

    /**
     * 판매종료일시
     */
    private String saleEndDttm;

    /**
     * 정책유형코드(D:온라인)
     */
    private String plcyTypeCd;

    /**
     * 조직유형
     */
    private String orgnType;

    /**
     * 정책구분코드(01:단말,02:유심)
     */
    private String plcySctnCd;

    /**
     * 제품구분코드(02:3G , 03:LTE)
     */
    private String prdtSctnCd;

    /**
     * 적용구분코드 (O:개통일, R:접수일)
     */
    private String applSctnCd;

    /**
     * 할부이자율
     */
    private BigDecimal instRate;

    /**
     * 신규여부 NAC
     */
    private String newYn;

    /**
     * 번호이동여부  MNP
     */
    private String mnpYn;

    /**
     * 일반 기변여부 HCN
     */
    private String hcnYn;

    /**
     * 우수 기변여부 HDN
     */
    private String hdnYn;

    /**
     * 확정여부
     */
    private String cnfmYn;

    /**
     * 확정자 ID
     */
    private String cnfmId;

    /**
     * 확정일시
     */
    private String cnfmDttm;

    /**
     * 등록자ID
     */
    private String regstId;

    /**
     * 등록일시
     */
    private Date regstDttm;

    /**
     * 수정자ID
     */
    private String rvisnId;

    /**
     * 수정일시
     */
    private Date rvisnDttm;

    /**
     * 약정기간
     */
    private String agrmTrm;

    /**
     * 선후불구분
     */
    private String payClCd;

    /**
     * 지원금유형 (단말할인:KD ,요금할인:PM)
     */
    private String sprtTp;

    private String sprtNm;

    /**
     * NRDS(단품코드)
     */
    private String prdtId;

    private String noArgmYn;

    private String operType;
}
