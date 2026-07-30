package com.ktmmobile.msf.domains.form.form.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSalePlcyMstInfoDto {

    //private List<MspRateMstDto> usimMspRateMstList; //요금제리스트
    //private List<MspPlcyOperTypeDto> usimMspPlcyOperTypeList; //가입유형리스트

    private String orgnId; //조직코드
    private String orgnType; //조직유형
    private String operType;
    private String salePlcyCd; //판매정책코드
    private String salePlcyNm; //판매정책명
    private String saleStrtDttm; //판매시작일시
    private String saleEndDttm; //판매종료일시
    private String plcyTypeCd; //정책유형코드
    private String plcySctnCd; //정책구분코드(01:단말,02:유심)
    private String prdtSctnCd; //제품구분코드(02:3G , 03:LTE) >> LTE5G / 5G / 3G / LTE / -1
    private String applSctnCd; //적용구분코드 (O:개통일, R:접수일)
    private BigDecimal instRate; //할부이자율
    private String agrmTrm; //약정기간
    private String payClCd; //선후불구분
    private String sprtTp; //지원금유형 (단말할인:KD ,요금할인:PM)
    private String sprtNm; //지원금유형 명
    private String prdtId; //NRDS(단품코드)
    private String noArgmYn;
    private String newYn; //신규여부 NAC
    private String mnpYn; //번호이동여부  MNP
    private String hcnYn; //일반 기변여부 HCN
    private String hdnYn; //우수 기변여부 HDN

    //private String cnfmYn; //확정여부
    //private String cnfmId; //확정자 ID
    //private String cnfmDttm; //확정일시
    //private String regstId; //등록자ID
    //private Date regstDttm; //등록일시
    //private String rvisnId; //수정자ID
    //private Date rvisnDttm; //수정일시
}
