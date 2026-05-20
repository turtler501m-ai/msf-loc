package com.ktmmobile.msf.domains.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @Class Name : MspSalePlcyMstDto
 * @Description :
 * MSP 의 MSP_SALE_PLCY_MST 테이블과 대응되는 DTO 이다.
 * 판매정책 마스터 정보로
 * 	판매정책 적용기간, 가입유형,할부이자율,지원금유형등의 정보들을 포함하고있다.
 * @author : ant
 * @Create Date : 2016. 1. 12.
 */
@Getter
@Setter
@NoArgsConstructor
public class MspSalePlcyMstDto implements Serializable {

    private List<MspRateMstDto> usimMspRateMstList; // 요금제리스트
    private List<MspPlcyOperTypeDto> usimMspPlcyOperTypeList; // 가입유형리스트
    private String orgnId; // 조직코드
    private String salePlcyCd; // 판매정책코드
    private String salePlcyNm; // 판매정책명
    private String saleStrtDttm; // 판매시작일시
    private String saleEndDttm; // 판매종료일시
    private String plcyTypeCd; // 정책유형코드(D:온라인)
    private String orgnType; // 조직유형
    private String plcySctnCd; // 정책구분코드(01:단말,02:유심)
    private String prdtSctnCd; // 제품구분코드(02:3G , 03:LTE)
    private String applSctnCd; // 적용구분코드 (O:개통일, R:접수일)
    private BigDecimal instRate; // 할부이자율
    private String newYn; // 신규여부 NAC
    private String mnpYn; // 번호이동여부 MNP
    private String hcnYn; // 일반 기변여부 HCN
    private String hdnYn; // 우수 기변여부 HDN
    private String cnfmYn; // 확정여부
    private String cnfmId; // 확정자 ID
    private String cnfmDttm; // 확정일시
    private String regstId; // 등록자ID
    private Date regstDttm; // 등록일시
    private String rvisnId; // 수정자ID
    private Date rvisnDttm; // 수정일시
    private String agrmTrm; // 약정기간
    private String payClCd; // 선후불구분
    private String sprtTp; // 지원금유형 (단말할인:KD ,요금할인:PM)
    private String prdtId; // NRDS(단품코드)
    private String noArgmYn;
    private String operType;
    private String prdtNm; // NRDS(단품코드명)
    private String prdtIndCd; // 유심 구분 (03:3G, 05:일반, 06:마이크로)
    private String rateCd; // 요금제 코드
    private String selfOpenYn; // 셀프개통 여부
}
