package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<MspAdditionDto> dailyAddition = new ArrayList<>(); //유료 부가서비스 정보
    //private String rateCd;
    //private String rateNm;
    //private String baseAmt;

    //categoryInfoDtoListAll.get(0).getProdId()
    //addition.get(0).getRateCd()
    //categoryInfoDtoListAll.get(0).getSortOdrg()

    //부가서비스를 무료와 유료로 분기하여 목록 만들기 (정렬은 포함하지 않음 - 스마트의 카테고리 정보를 조회하지 않기 때문)
    public void setFreeAndPaid(List<MspAdditionDto> addition) {
        addition.forEach(mspAdditionDto -> {
            if (Integer.parseInt(mspAdditionDto.getBaseAmt()) > 0) {
                this.paidAddition.add(mspAdditionDto);
            } else {
                this.freeAddition.add(mspAdditionDto);
            }
        });
    }

    //부가서비스를 무료와 유료로 분기하여 정렬 목록 만들기
    public void setFreeAndPaidSorted(List<MspAdditionDto> addition, List<CategoryInfoDto> categoryInfoDtoList) {
        addition.forEach(itemA -> {
            categoryInfoDtoList.stream()
                .filter(itemB -> itemB.getProdId().equals(itemA.getRateCd()))
                .findFirst()
                .ifPresent(itemB -> itemA.setSortOrdr(itemB.getSortOdrg()));

            if (Integer.parseInt(itemA.getBaseAmt()) > 0) {
                this.paidAddition.add(itemA);
            } else {
                this.freeAddition.add(itemA);
            }
        });
    }

    public void mergeDailyAdditionIntoDisplayList(List<CategoryInfoDto> categoryInfoDtoList) {
        Set<String> displayRateCodes = new HashSet<>();
        this.freeAddition.forEach(item -> displayRateCodes.add(item.getRateCd()));
        this.paidAddition.forEach(item -> displayRateCodes.add(item.getRateCd()));

        this.dailyAddition.forEach(item -> {
            if (item.getRateCd() == null || displayRateCodes.contains(item.getRateCd())) {
                return;
            }

            if (categoryInfoDtoList != null) {
                categoryInfoDtoList.stream()
                    .filter(category -> item.getRateCd().equals(category.getProdId()))
                    .findFirst()
                    .ifPresent(category -> item.setSortOrdr(category.getSortOdrg()));
            }

            if (parseAmount(item.getBaseAmt()) > 0) {
                this.paidAddition.add(item);
            } else {
                this.freeAddition.add(item);
            }
            displayRateCodes.add(item.getRateCd());
        });
    }

    private int parseAmount(String amount) {
        try {
            return Integer.parseInt(amount == null ? "0" : amount.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
