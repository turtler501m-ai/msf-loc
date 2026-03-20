package com.ktmmobile.msf.formComm.dto;

/**
 * 카드번호 유효성 체크 요청 DTO (IF_0007 / X91).
 * 형식검증(Luhn) + X91 M플랫폼 실인증 파라미터 포함.
 * ASIS AppformController.crdtCardAthnInfo() 파라미터 대응.
 */
public class CardCheckReqDto {

    /** 카드번호 (숫자만 16자리) */
    private String cardNo;

    /** 유효기간 연도 (2자리, 예: "27") */
    private String cardYy;

    /** 유효기간 월 (2자리, 예: "05") */
    private String cardMm;

    /**
     * 카드 명의인 생년월일 (YYYYMMDD).
     * X91 brthDate 파라미터. 미입력 시 형식검증만 수행.
     */
    private String birthDate;

    /**
     * 카드 명의인 이름.
     * X91 custNm 파라미터. 미입력 시 형식검증만 수행.
     */
    private String custNm;

    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

    public String getCardYy() { return cardYy; }
    public void setCardYy(String cardYy) { this.cardYy = cardYy; }

    public String getCardMm() { return cardMm; }
    public void setCardMm(String cardMm) { this.cardMm = cardMm; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
}
