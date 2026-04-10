package com.ktmmobile.msf.domains.form.form.servicechange.dto;
import java.text.ParseException;

import java.io.Serializable;
import java.util.Date;

import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;

public class McpFarPriceDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String prvRateGrpCd;//변경전_요금그룹코드
    private String prvRateGrpNm;//변경전_요금그룹명
    private String prvRateGrpTypeCd;//변경전_요금그룹유형코드
    private String prvRateGrpTypeNm;//변경전_요금그룹유형명
    private String prvRateCd;//변경전_요금제코드
    private String prvRateNm;//변경전_요금제명
    private String prvPtrnRateYn;//변경전_제휴요금여부
    private String prvPayClCd;//변경전_선후불코드
    private String prvDataType;//변경전_데이터유형
    private String prvRateType;//변경전_요금제유형
    private String prvRmk;//변경전_요금설명
    private int prvBaseAmt;//변경전_요금금액
    private String prvApplStrtDt;//변경전_요금제적용시작일자
    private String prvApplEndDt;//변경전_요금적용종료일자
    private String nxtRateGrpCd;//변경후_요금그룹코드
    private String nxtRateGrpNm;//변경후_요금그룹명
    private String nxtRateGrpTypeCd;//변경후_요금그룹유형코드
    private String nxtRateGrpTypeNm;//변경후_요금그룹유형명
    private String nxtRateCd;//변경후_요금제코드
    private String nxtRateNm;//변경후_요금제명
    private String nxtPtrnRateYn;//변경후_제휴요금여부
    private String nxtPayClCd;//변경후_선후불코드
    private String nxtDataType;//변경후_데이터유형
    private String nxtRateType;//변경후_요금제유형
    private String nxtRmk;//변경후_요금설명
    private int nxtBaseAmt;//변경후_요금금액
    private String nxtApplStrtDt;//변경후_요금제적용시작일자
    private String nxtApplEndDt;//변경후_요금적용종료일자
    private int baseVatAmt = 0;//vat포함금액
    private String appStartDd; //적용일시
    private String type;//제주항공요금제 > 일반요금제 경우 J ,  일반요금제 > 제주요금제 I,   그외   O

    private String rsrvPrdcCd;	//예약상품요금제코드
    private String rsrvPrdcNm; //예약상품요금제명
    private String rsrvBasicAmt; //예약요금제 변경 대상 기본료
    private String rsrvAplyDate; //예약요금제 변경 신청일자
    private String rsrvEfctStDate; //예약요금제 변경 적응일자
    private String rsrvBasicVatAmt; //예약요금제 변경 대상 기본료 * 1.1;

    /** 프로모션 할인 금액(공통코드 에서 요금제 코드로 맵핑 하여 프로모션 할인 요금) */
    private int promotionDcAmt = 0;

    private String rateAdsvcLteDesc; //데이터 요금제설명
    private String rateAdsvcCallDesc; //음성 요금제설명
    private String rateAdsvcSmsDesc; //sms문자 요금제 설명

    public String getPrvRateGrpCd() {
        return prvRateGrpCd;
    }
    public void setPrvRateGrpCd(String prvRateGrpCd) {
        this.prvRateGrpCd = prvRateGrpCd;
    }
    public String getPrvRateGrpNm() {
        return prvRateGrpNm;
    }
    public void setPrvRateGrpNm(String prvRateGrpNm) {
        this.prvRateGrpNm = prvRateGrpNm;
    }
    public String getPrvRateGrpTypeCd() {
        return prvRateGrpTypeCd;
    }
    public void setPrvRateGrpTypeCd(String prvRateGrpTypeCd) {
        this.prvRateGrpTypeCd = prvRateGrpTypeCd;
    }
    public String getPrvRateGrpTypeNm() {
        return prvRateGrpTypeNm;
    }
    public void setPrvRateGrpTypeNm(String prvRateGrpTypeNm) {
        this.prvRateGrpTypeNm = prvRateGrpTypeNm;
    }
    public String getPrvRateCd() {
        return prvRateCd;
    }
    public void setPrvRateCd(String prvRateCd) {
        this.prvRateCd = prvRateCd;
    }
    public String getPrvRateNm() {
        return prvRateNm;
    }
    public void setPrvRateNm(String prvRateNm) {
        this.prvRateNm = prvRateNm;
    }
    public String getPrvPtrnRateYn() {
        return prvPtrnRateYn;
    }
    public void setPrvPtrnRateYn(String prvPtrnRateYn) {
        this.prvPtrnRateYn = prvPtrnRateYn;
    }
    public String getPrvPayClCd() {
        return prvPayClCd;
    }
    public void setPrvPayClCd(String prvPayClCd) {
        this.prvPayClCd = prvPayClCd;
    }
    public String getPrvDataType() {
        return prvDataType;
    }
    public void setPrvDataType(String prvDataType) {
        this.prvDataType = prvDataType;
    }
    public String getPrvRateType() {
        return prvRateType;
    }
    public void setPrvRateType(String prvRateType) {
        this.prvRateType = prvRateType;
    }
    public String getPrvRmk() {
        return prvRmk;
    }
    public void setPrvRmk(String prvRmk) {
        this.prvRmk = prvRmk;
    }
    public int getPrvBaseAmt() {
        return prvBaseAmt;
    }
    public void setPrvBaseAmt(int prvBaseAmt) {
        this.prvBaseAmt = prvBaseAmt;
    }
    public String getPrvApplStrtDt() {
        return prvApplStrtDt;
    }
    public void setPrvApplStrtDt(String prvApplStrtDt) {
        this.prvApplStrtDt = prvApplStrtDt;
    }
    public String getPrvApplEndDt() {
        return prvApplEndDt;
    }
    public void setPrvApplEndDt(String prvApplEndDt) {
        this.prvApplEndDt = prvApplEndDt;
    }
    public String getNxtRateGrpCd() {
        return nxtRateGrpCd;
    }
    public void setNxtRateGrpCd(String nxtRateGrpCd) {
        this.nxtRateGrpCd = nxtRateGrpCd;
    }
    public String getNxtRateGrpNm() {
        return nxtRateGrpNm;
    }
    public void setNxtRateGrpNm(String nxtRateGrpNm) {
        this.nxtRateGrpNm = nxtRateGrpNm;
    }
    public String getNxtRateGrpTypeCd() {
        return nxtRateGrpTypeCd;
    }
    public void setNxtRateGrpTypeCd(String nxtRateGrpTypeCd) {
        this.nxtRateGrpTypeCd = nxtRateGrpTypeCd;
    }
    public String getNxtRateGrpTypeNm() {
        return nxtRateGrpTypeNm;
    }
    public void setNxtRateGrpTypeNm(String nxtRateGrpTypeNm) {
        this.nxtRateGrpTypeNm = nxtRateGrpTypeNm;
    }
    public String getNxtRateCd() {
        return nxtRateCd;
    }
    public void setNxtRateCd(String nxtRateCd) {
        this.nxtRateCd = nxtRateCd;
    }
    public String getNxtRateNm() {
        return nxtRateNm;
    }
    public void setNxtRateNm(String nxtRateNm) {
        this.nxtRateNm = nxtRateNm;
    }
    public String getNxtPtrnRateYn() {
        return nxtPtrnRateYn;
    }
    public void setNxtPtrnRateYn(String nxtPtrnRateYn) {
        this.nxtPtrnRateYn = nxtPtrnRateYn;
    }
    public String getNxtPayClCd() {
        return nxtPayClCd;
    }
    public void setNxtPayClCd(String nxtPayClCd) {
        this.nxtPayClCd = nxtPayClCd;
    }
    public String getNxtDataType() {
        return nxtDataType;
    }
    public void setNxtDataType(String nxtDataType) {
        this.nxtDataType = nxtDataType;
    }
    public String getNxtRateType() {
        return nxtRateType;
    }
    public void setNxtRateType(String nxtRateType) {
        this.nxtRateType = nxtRateType;
    }
    public String getNxtRmk() {
        return nxtRmk;
    }
    public void setNxtRmk(String nxtRmk) {
        this.nxtRmk = nxtRmk;
    }
    public int getNxtBaseAmt() {
        return nxtBaseAmt;
    }
    public void setNxtBaseAmt(int nxtBaseAmt) {
        this.nxtBaseAmt = nxtBaseAmt;
    }
    public String getNxtApplStrtDt() {
        return nxtApplStrtDt;
    }
    public void setNxtApplStrtDt(String nxtApplStrtDt) {
        this.nxtApplStrtDt = nxtApplStrtDt;
    }
    public String getNxtApplEndDt() {
        return nxtApplEndDt;
    }
    public void setNxtApplEndDt(String nxtApplEndDt) {
        this.nxtApplEndDt = nxtApplEndDt;
    }
    public int getBaseVatAmt() {
        return baseVatAmt;
    }
    public void setBaseVatAmt(int baseVatAmt) {
        this.baseVatAmt = baseVatAmt;
    }
    public String getAppStartDd() {
        return appStartDd;
    }

    public Date getAppStartDdDate() {
        try {
            //2021 03 10 134038
            return DateTimeUtil.check(appStartDd,"yyyyMMddHHmmss");
        } catch (ParseException e) {
            return null;
        }
    }

    public void setAppStartDd(String appStartDd) {
        this.appStartDd = appStartDd;
    }
    public String getType() {
//		parse();
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPromotionDcAmt() {
        return promotionDcAmt;
    }
    public void setPromotionDcAmt(int promotionDcAmt) {

        this.promotionDcAmt = promotionDcAmt;
    }




    public String getRateAdsvcLteDesc() {
        return rateAdsvcLteDesc;
    }
    public void setRateAdsvcLteDesc(String rateAdsvcLteDesc) {
        this.rateAdsvcLteDesc = rateAdsvcLteDesc;
    }
    public String getRateAdsvcCallDesc() {
        return rateAdsvcCallDesc;
    }
    public void setRateAdsvcCallDesc(String rateAdsvcCallDesc) {
        this.rateAdsvcCallDesc = rateAdsvcCallDesc;
    }
    public String getRateAdsvcSmsDesc() {
        return rateAdsvcSmsDesc;
    }
    public void setRateAdsvcSmsDesc(String rateAdsvcSmsDesc) {
        this.rateAdsvcSmsDesc = rateAdsvcSmsDesc;
    }



    public String getRsrvPrdcCd() {
        return rsrvPrdcCd;
    }
    public void setRsrvPrdcCd(String rsrvPrdcCd) {
        this.rsrvPrdcCd = rsrvPrdcCd;
    }
    public String getRsrvPrdcNm() {
        return rsrvPrdcNm;
    }
    public void setRsrvPrdcNm(String rsrvPrdcNm) {
        this.rsrvPrdcNm = rsrvPrdcNm;
    }


    public String getRsrvBasicAmt() {
        return rsrvBasicAmt;
    }
    public void setRsrvBasicAmt(String rsrvBasicAmt) {
        this.rsrvBasicAmt = rsrvBasicAmt;
    }
    public String getRsrvAplyDate() {
        return rsrvAplyDate;
    }
    public void setRsrvAplyDate(String rsrvAplyDate) {
        this.rsrvAplyDate = rsrvAplyDate;
    }
    public String getRsrvEfctStDate() {
        return rsrvEfctStDate;
    }
    public void setRsrvEfctStDate(String rsrvEfctStDate) {
        this.rsrvEfctStDate = rsrvEfctStDate;
    }


    public String getRsrvBasicVatAmt() {
        return rsrvBasicVatAmt;
    }
    public void setRsrvBasicVatAmt(String rsrvBasicVatAmt) {
        this.rsrvBasicVatAmt = rsrvBasicVatAmt;
    }
    /** @Description :
     * 월요금
     */
     public int getInstMnthAmt() {
         //요금제 vat포함금액  -   프로모션 할인 금액
         int rtnInt = getBaseVatAmt()  - getPromotionDcAmt();
         if (rtnInt > 0) {
             return rtnInt;
         } else {
             return 0 ;
         }
     }




//    public String parse() {
//    	if (!prvRateGrpCd.equals("30") && (nxtRateType.equals("KISSLCT24") || nxtRateType.equals("KISJJAR18") || nxtRateType.equals("KISJJAR10"))){
//    		this.type= "I";
//    	}else if (!nxtRateGrpCd.equals("30") && (prvRateType.equals("KISSLCT24") || prvRateType.equals("KISJJAR18") || prvRateType.equals("KISJJAR10"))){
//    		this.type= "J";
//    	}else if (!prvRateGrpCd.equals("30") && (nxtRateType.equals("KISLTUS21") || nxtRateType.equals("KISMLTE21"))){
//    		this.type= "G";
//    	}else{
//    		this.type= "O";
//    	}
//    	return type;
//	}


}