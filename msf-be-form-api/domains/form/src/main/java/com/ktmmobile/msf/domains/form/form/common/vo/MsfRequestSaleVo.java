package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestSaleVo {

    Long requestKey;
    String modelId;
    String modelMonthly;
    Long modelInstamt;
    String modelSalePolicyCd;
    Long modelPriceVat;
    Long modelDiscount1;
    Long modelSprt;
    Long modelPrice;
    Long modelDiscount3;
    Long realMdlInstamt;
    Long hndsetSalePrice;
    String sprtTypeCd;
    Long dcAmt;
    Long maxApdSprt;
    Long addDcAmt;
    Long enggMnthCnt;
    String recycleYn;
    String usimPriceTypeCd;
    Long usimPrice;
    String usimPayMthdCd;
    String sesplsYn;
    String joinPriceTypeCd;
    String joinPayMthdCd;
    Long joinPrice;
    String socCode;
    String socNm;
    Long socBaseChrgAmt;

}
