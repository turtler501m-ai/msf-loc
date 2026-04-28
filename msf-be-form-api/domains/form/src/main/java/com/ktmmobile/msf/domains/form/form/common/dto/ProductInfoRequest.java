package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductInfoRequest {

    private String prdtIndCd;
    private String rprsPrdtId;
    private String modelSalePolicyCode;
    private String salePlcyCd;
    private String salePlcyNm;
    private String prdtSctnCd;
    private String plcySctnCd;
    private String sprtTp;
    private String orgnId;
    private String searchCategoryId;
    private String searchValue;
    private String saleYn;
    private String searchSaleYn;
    private String showYn;
    private String searchShowYn;
    private String shandYn;
    private String plcyTypeCd;
    private String makrCd;
    private String prodCtgId;
    private String shandType;
    private String rprsYn;
    private String popupMakeClick;
    private Integer pageNo;
    private String prodType;
    private String ctgCd;
    private String sesplsYn;
    private String prodId;
    private String prdtId;
    private String rateCd;
    private String rateType; //CMN0047 >> 01 : 단말요금제 / 02 : USIM요금제
    private String reqBuyTypeCd; //REQ_BUY_TYPE_CD >> MM : 휴대폰, UU : 유심

    private String payClCd; //PAY_CL_CD : 후불(PO)
    private String serviceType; //SERVICE_TYPE : 구분 (P: 요금제)

    private List<MspSalePlcyMstInfoDto> listMspSaleDto; //정책코드 목록
    private List<CategoryInfoDto> listRateDto; //요금제 목록
    private List<CategoryInfoDto> listPhoneDto; //단말 목록


    private String prodCtgTypeCd; //상품카테고리 구분 코드 ( 요금: P , 부가서비스: R , 안심보험: I)
    //private String prodCtgId; //상품카테고리의 아이디

    private String prodSn; //휴대폰일련번호 유효성체크 :: 일련번호 값


}
