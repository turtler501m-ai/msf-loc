package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionResponse {
    private String additionCtgCd; //카테고리
    //private List<MspAdditionDto> listMspAdditionDto; //부가서비스 정보
    private List<MspAdditionDto> freeAddition = new ArrayList<>(); //무료 부가서비스 정보
    private List<MspAdditionDto> paidAddition = new ArrayList<>(); //유료 부가서비스 정보
    //private String rateCd;
    //private String rateNm;
    //private String baseAmt;


}
