package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AdditionPreCheckReqDto {

    private String ncn;
    private String ctn;
    private String custId;
    private String actCode;
    private List<ProductInfo> prdcList;
    private String prmtId;
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductInfo {
        private String prdcCd;
        private String prdcSbscTrtmCd;
        private String prdcTypeCd;
        private String prdcSeqNo;
        private String ftrNewParam;
    }
}
