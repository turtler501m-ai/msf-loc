package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpFarPriceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prvRateGrpCd;     // 변경전_요금그룹코드
    private String prvRateGrpNm;     // 변경전_요금그룹명
    private String prvRateGrpTypeCd; // 변경전_요금그룹유형코드
    private String prvRateGrpTypeNm; // 변경전_요금그룹유형명
    private String prvRateCd;        // 변경전_요금제코드
    private String prvRateNm;        // 변경전_요금제명
    private String prvPtrnRateYn;    // 변경전_제휴요금여부
    private String prvPayClCd;       // 변경전_선후불코드
    private String prvDataType;      // 변경전_데이터유형
    private String prvRateType;      // 변경전_요금제유형
    private String prvRmk;           // 변경전_요금설명
    private int prvBaseAmt;          // 변경전_요금금액
    private String prvApplStrtDt;    // 변경전_요금제적용시작일자
    private String prvApplEndDt;     // 변경전_요금적용종료일자
    private String nxtRateGrpCd;     // 변경후_요금그룹코드
    private String nxtRateGrpNm;     // 변경후_요금그룹명
    private String nxtRateGrpTypeCd; // 변경후_요금그룹유형코드
    private String nxtRateGrpTypeNm; // 변경후_요금그룹유형명
    private String nxtRateCd;        // 변경후_요금제코드
    private String nxtRateNm;        // 변경후_요금제명
    private String nxtPtrnRateYn;    // 변경후_제휴요금여부
    private String nxtPayClCd;       // 변경후_선후불코드
    private String nxtDataType;      // 변경후_데이터유형
    private String nxtRateType;      // 변경후_요금제유형
    private String nxtRmk;           // 변경후_요금설명
    private int nxtBaseAmt;          // 변경후_요금금액
    private String nxtApplStrtDt;    // 변경후_요금제적용시작일자
    private String nxtApplEndDt;     // 변경후_요금적용종료일자
    private int baseVatAmt;          // vat포함금액
    private String appStartDd;       // 적용일시
    private String type;             // 제주항공요금제>일반요금제:J, 일반>제주:I, 그외:O
    private int promotionDcAmt;      // 프로모션 할인 금액
    private String rsrvPrdcCd;       // 예약 상품요금제코드
    private String rsrvPrdcNm;       // 예약 상품요금제명
    private String rsrvBasicAmt;     // 예약 요금제변경 대상기본료
    private String rsrvAplyDate;     // 예약요금제 변경 신청일자
    private String rsrvEfctStDate;   // 예약요금제 변경 적용일자

    public String getType() {
        // parse();
        return type;
    }

    /*
    public String parse() {
        if (!prvRateGrpCd.equals("30") && (nxtRateType.equals("KISSLCT24") || nxtRateType.equals("KISJJAR18") || nxtRateType.equals("KISJJAR10"))){
            this.type= "I";
        }else if (!nxtRateGrpCd.equals("30") && (prvRateType.equals("KISSLCT24") || prvRateType.equals("KISJJAR18") || prvRateType.equals("KISJJAR10"))){
            this.type= "J";
        }else if (!prvRateGrpCd.equals("30") && (nxtRateType.equals("KISLTUS21") || nxtRateType.equals("KISMLTE21"))){
            this.type= "G";
        }else{
            this.type= "O";
        }
        return type;
    }
    */

}
