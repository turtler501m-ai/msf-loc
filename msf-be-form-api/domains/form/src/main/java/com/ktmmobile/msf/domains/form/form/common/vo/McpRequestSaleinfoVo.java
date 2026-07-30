package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestSaleinfoVo {

    private Long requestKey;

    private String rvisnId;

    private LocalDateTime rvisnDttm;

    private String modelId;

    private String modelMonthly;

    private Integer modelInstallment;

    private String modelSalePolicyCode;

    private Integer modelPriceVat;

    private Integer modelDiscount1;

    private Integer modelDiscount2;

    private Integer modelPrice;

    private Integer modelDiscount3;

    private Long realMdlInstamt;

    private Integer hndsetSalePrice;

    private String sprtTp;

    private Integer dcAmt;

    private Integer maxDiscount3;

    private Integer addDcAmt;

    private Integer enggMnthCnt;

    private String recycleYn;

    private String usimPriceType;

    private Integer usimPrice;

    private String usimPayMthdCd;

    private String settlWayCd = "";

    private Integer settlAmt;

    private String settlApvNo = "";

    private String settlTraNo = "";

    private String ownPersonalCode = "";

    private Integer rentalBaseAmt;

    private Integer rentalBaseDcAmt;

    private Integer rentalModelCpAmt;

    private String sesplsYn;

    private String sesplsProdId = "";

    private Integer usePoint;

    private String usePointSvcCntrNo = "";

    private String cardDcCd = "";

    private String cardDcDivCd = "";

    private Integer cardDcAmt;

    private String cardTotDcAmt = "";

    private String joinPriceType;

    private String joinPayMthdCd;

    private Integer joinPrice;

    private String socCode;

    private String addtionService = "";

    private String addtionServiceSum = "";

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setAmdId(String amdId) { this.rvisnId = amdId; }
    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }
    public void setModelId(String modelId) { this.modelId = modelId; }
    public void setModelMonthly(String modelMonthly) { this.modelMonthly = modelMonthly; }
    public void setModelInstamt(Integer modelInstamt) { this.modelInstallment = modelInstamt; }
    public void setModelSalePolicyCd(String modelSalePolicyCd) { this.modelSalePolicyCode = modelSalePolicyCd; }
    public void setModelPriceVat(Integer modelPriceVat) { this.modelPriceVat = modelPriceVat; }
    public void setModelDiscount1(Integer modelDiscount1) { this.modelDiscount1 = modelDiscount1; }
    public void setModelSprt(Integer modelSprt) { this.modelDiscount2 = modelSprt; }
    public void setModelPrice(Integer modelPrice) { this.modelPrice = modelPrice; }
    public void setModelDiscount3(Integer modelDiscount3) { this.modelDiscount3 = modelDiscount3; }
    public void setRealMdlInstamt(Long realMdlInstamt) { this.realMdlInstamt = realMdlInstamt; }
    public void setHndsetSalePrice(Integer hndsetSalePrice) { this.hndsetSalePrice = hndsetSalePrice; }
    public void setSprtTypeCd(String sprtTypeCd) { this.sprtTp = sprtTypeCd; }
    public void setDcAmt(Integer dcAmt) { this.dcAmt = dcAmt; }
    public void setMaxApdSprt(Integer maxApdSprt) { this.maxDiscount3 = maxApdSprt; }
    public void setAddDcAmt(Integer addDcAmt) { this.addDcAmt = addDcAmt; }
    public void setEnggMnthCnt(Integer enggMnthCnt) { this.enggMnthCnt = enggMnthCnt; }
    public void setRecycleYn(String recycleYn) { this.recycleYn = recycleYn; }
    public void setUsimPriceTypeCd(String usimPriceTypeCd) { this.usimPriceType = usimPriceTypeCd; }
    public void setUsimPrice(Integer usimPrice) { this.usimPrice = usimPrice; }
    public void setUsimPayMthdCd(String usimPayMthdCd) { this.usimPayMthdCd = usimPayMthdCd; }
    public void setSesplsYn(String sesplsYn) { this.sesplsYn = sesplsYn; }
    public void setJoinPriceTypeCd(String joinPriceTypeCd) { this.joinPriceType = joinPriceTypeCd; }
    public void setJoinPayMthdCd(String joinPayMthdCd) { this.joinPayMthdCd = joinPayMthdCd; }
    public void setJoinPrice(Integer joinPrice) { this.joinPrice = joinPrice; }
    public void setSocCode(String socCode) { this.socCode = socCode; }
}
