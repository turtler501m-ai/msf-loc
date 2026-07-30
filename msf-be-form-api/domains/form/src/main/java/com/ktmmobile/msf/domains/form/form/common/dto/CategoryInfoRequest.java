package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryInfoRequest {

    private List<String> productList;
    private List<String> ctgList;
    private String rateAdsvcDivCd;
    //private String product;
}
