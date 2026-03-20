package com.ktmmobile.msf.formComm.dto;

/**
 * 카드번호 유효성 체크 요청 DTO (IF_0007).
 * ASIS myNameChg.js btnCheckCardNo 검증 파라미터 대응.
 */
public class CardCheckReqDto {

    /** 카드번호 (숫자만 16자리) */
    private String cardNo;

    /** 유효기간 연도 (2자리, 예: "27") */
    private String cardYy;

    /** 유효기간 월 (2자리, 예: "05") */
    private String cardMm;

    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

    public String getCardYy() { return cardYy; }
    public void setCardYy(String cardYy) { this.cardYy = cardYy; }

    public String getCardMm() { return cardMm; }
    public void setCardMm(String cardMm) { this.cardMm = cardMm; }
}
