package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 스마트에서 관리하는 부가서비스 조회 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionResponse {

    //private String additionCtgCd; //카테고리
    //private List<MspAdditionDto> listMspAdditionDto; //부가서비스 정보
    private List<MspAdditionDto> freeAddition = new ArrayList<>(); //무료 부가서비스 정보
    private List<MspAdditionDto> paidAddition = new ArrayList<>(); //유료 부가서비스 정보
    //private String rateCd;
    //private String rateNm;
    //private String baseAmt;


    public void setFreeAndPaid(List<MspAdditionDto> addition) {
        addition.forEach(mspAdditionDto -> {
            if (Integer.parseInt(mspAdditionDto.getBaseAmt()) > 0) {
                this.paidAddition.add(mspAdditionDto);
            } else {
                this.freeAddition.add(mspAdditionDto);
            }
        });
    }
}
