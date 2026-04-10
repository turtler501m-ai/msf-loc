package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;
import java.util.List;

public class RateGiftPrmtListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalPrice;                           // 요금제 혜택요약 총 금액
    private int totalCount;                           // 요금제 혜택요약 총 개수
    private List<RateGiftPrmtDTO> rateGiftPrmtList;   // 요금제 혜택요약 리스트
    private List<RateGiftPrmtDTO> wireRateGiftPrmtList;	// 인터넷 혜택
    private int totalWirePrice;						 	// 인터넷 혜택 총 금액
    private List<RateGiftPrmtDTO> freeRateGiftPrmtList; // 무료 혜택
    private int totalMainPrice;                   		// 대표 혜택 총 금액(메인 아코디언, 모바일 - 무료 - 인터넷 순으로 유무)
    private int totalMainCount;                   		// 대표 혜택 개수(메인 아코디언)
    private List<RateGiftPrmtDTO> mainGiftPrmtList;  	// 대표 혜택 리스트(메인 아코디언)

    private String evntCdPrmtYn;                      // 이벤트 코드 대상 요금제 여부 (Y/N)

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<RateGiftPrmtDTO> getRateGiftPrmtList() {
        return rateGiftPrmtList;
    }

    public void setRateGiftPrmtList(List<RateGiftPrmtDTO> rateGiftPrmtList) {
        this.rateGiftPrmtList = rateGiftPrmtList;
    }

    public String getEvntCdPrmtYn() {
        return evntCdPrmtYn;
    }

    public void setEvntCdPrmtYn(String evntCdPrmtYn) {
        this.evntCdPrmtYn = evntCdPrmtYn;
    }

    public List<RateGiftPrmtDTO> getWireRateGiftPrmtList() {
        return wireRateGiftPrmtList;
    }

    public void setWireRateGiftPrmtList(List<RateGiftPrmtDTO> wireRateGiftPrmtList) {
        this.wireRateGiftPrmtList = wireRateGiftPrmtList;
    }

    public int getTotalWirePrice() {
        return totalWirePrice;
    }

    public void setTotalWirePrice(int totalWirePrice) {
        this.totalWirePrice = totalWirePrice;
    }

    public List<RateGiftPrmtDTO> getFreeRateGiftPrmtList() {
        return freeRateGiftPrmtList;
    }

    public void setFreeRateGiftPrmtList(List<RateGiftPrmtDTO> freeRateGiftPrmtList) {
        this.freeRateGiftPrmtList = freeRateGiftPrmtList;
    }

    public int getTotalMainPrice() {
        return totalMainPrice;
    }

    public void setTotalMainPrice(int totalMainPrice) {
        this.totalMainPrice = totalMainPrice;
    }

    public int getTotalMainCount() {
        return totalMainCount;
    }

    public void setTotalMainCount(int totalMainCount) {
        this.totalMainCount = totalMainCount;
    }

    public List<RateGiftPrmtDTO> getMainGiftPrmtList() {
        return mainGiftPrmtList;
    }

    public void setMainGiftPrmtList(List<RateGiftPrmtDTO> mainGiftPrmtList) {
        this.mainGiftPrmtList = mainGiftPrmtList;
    }
}
