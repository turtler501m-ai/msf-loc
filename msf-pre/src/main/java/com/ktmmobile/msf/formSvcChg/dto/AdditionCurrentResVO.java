package com.ktmmobile.msf.formSvcChg.dto;

import java.util.List;

/**
 * 현재 가입 부가서비스 조회 응답 VO.
 * POST /api/v1/addition/current 응답. X20 결과 기반.
 */
public class AdditionCurrentResVO {

    /** 현재 가입 부가서비스 목록 (X20) */
    private List<AdditionItemDto> items;
    /**
     * 가입 가능 부가서비스 목록 (유료, 미가입 항목).
     * 프론트 ChangeProd.vue availableAdditionItems 에 바인딩.
     * X97 구현 전까지는 빈 목록 반환.
     */
    private List<AdditionItemDto> availableItems;
    /** 무선데이터차단 사용 여부 (SOC=DATABLOCK 또는 설명에 "무선"+"차단" 포함) */
    private boolean wirelessBlockInUse;
    /** 정보료 상한 금액 (0이면 미가입) */
    private int infoLimitAmount;

    public List<AdditionItemDto> getItems() { return items; }
    public void setItems(List<AdditionItemDto> items) { this.items = items; }

    public List<AdditionItemDto> getAvailableItems() { return availableItems; }
    public void setAvailableItems(List<AdditionItemDto> availableItems) { this.availableItems = availableItems; }

    public boolean isWirelessBlockInUse() { return wirelessBlockInUse; }
    public void setWirelessBlockInUse(boolean wirelessBlockInUse) { this.wirelessBlockInUse = wirelessBlockInUse; }

    public int getInfoLimitAmount() { return infoLimitAmount; }
    public void setInfoLimitAmount(int infoLimitAmount) { this.infoLimitAmount = infoLimitAmount; }
}
