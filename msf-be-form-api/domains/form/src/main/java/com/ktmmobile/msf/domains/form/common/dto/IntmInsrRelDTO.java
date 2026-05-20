package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.REQ_BUY_TYPE_PHONE;

@Getter
@Setter
@NoArgsConstructor
public class IntmInsrRelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String insrProdCd;                          // 보험상품코드
    private String rprsPrdtId;                          // 단말ID
    private String insrProdNm;                          // 보험상품명
    private String reqBuyType = REQ_BUY_TYPE_PHONE;     // 구매유형 (MM:단말, UU:USIM단독)
    private int cmpnLmtAmt;                             // 보상한도금액
    private int insrEnggCnt;                            // 보험약정기간

}
