package com.ktmmobile.msf.formComm.dto;

/**
 * 계좌번호 유효성 체크 요청 DTO (IF_0006, NICE 계좌인증).
 * ASIS NiceResDto 의 계좌 관련 필드 대응.
 */
public class AccountCheckReqDto {

    /** 은행코드 (NICE 전문 참조, 예: "031"=기업은행) */
    private String bankCode;

    /** 계좌번호 */
    private String accountNo;

    /** 예금주명 (service=2 계좌성명확인 시 필수) */
    private String name;

    /**
     * 서비스 구분.
     * 1=계좌소유주확인, 2=계좌성명확인(기본), 3=계좌유효성확인
     */
    private String service = "2";

    /**
     * 업무 구분.
     * 5=소유주확인, 2=예금주명확인(기본), 4=계좌유효성확인
     */
    private String svcGbn = "2";

    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getSvcGbn() { return svcGbn; }
    public void setSvcGbn(String svcGbn) { this.svcGbn = svcGbn; }
}
