package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@NoArgsConstructor
public class PhoneMspDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orgnId;          // 조직코드
    private String salePlcyCd;      // 판매정책코드
    private String prdtId;          // 제품ID
    private String oldYn;           // 중고여부
    private int newCmsnAmt;         // 신규수수료
    private int mnpCmsnAmt;         // MNP수수료
    private int hcnCmsnAmt;         // 기변수수료
    private String rprsPrdtId;      // 대표제품ID
    private String rprsYn;          // 대표제품 여부
    private String prdtNm;          // 제품명 (ex. LG 클래스)
    private String prdtCode;        // 제품코드 (ex. LG-F40K)
    private String prdtTypeCd;      // 제품유형코드
    private String prdtIndCd;       // 제품구분코드 (LTE:04, 3G:03)
    private String prdtColrCd;      // 제품색상코드
    private String mnfctId;         // 제조사 ID
    private String prdtLnchDt;      // 제품 출시일자
    private String prdtDt;          // 제품단종일자
    private String prdtIndCdLabel;  // 제품구분코드 label 값
    private String mnfctNm;         // 제조사 명

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getPrdtIndCdLabel() {
        if (prdtIndCd == null) {
            prdtIndCdLabel = "";
        } else if (prdtIndCd.equals("02")) {
            prdtIndCdLabel = "ALL";
        } else if (prdtIndCd.equals("03")) {
            prdtIndCdLabel = "3G";
        } else if (prdtIndCd.equals("04")) {
            prdtIndCdLabel = "LTE";
        } else {
            prdtIndCdLabel = "";
        }
        return prdtIndCdLabel;
    }

}
