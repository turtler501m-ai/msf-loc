package com.ktmmobile.msf.formComm.dto;

/**
 * 가입자정보조회 응답 VO. Y04 + X01 결과 조합.
 * 프론트엔드 Pinia store(service_change_form) 에 저장되는 필드 기준.
 */
public class JoinInfoResVO {

    private boolean success;
    private String message;

    /** 서비스계약번호 (9자리) */
    private String ncn;
    /** 전화번호 */
    private String ctn;
    /** 고객ID */
    private String custId;
    /** 고객유형코드 */
    private String custTypeCd;
    /** 고객세부유형코드 */
    private String custDtlTypeCd;
    /** 고객명 */
    private String userName;
    /** 생년월일 */
    private String birthDt;
    /** 이메일 (X01) */
    private String email;
    /** 주소 (X01) */
    private String addr;
    /** 연락처 (X01 homeTel) */
    private String homeTel;
    /** 최초개통일 (X01) */
    private String initActivationDate;

    public static JoinInfoResVO fail(String message) {
        JoinInfoResVO vo = new JoinInfoResVO();
        vo.success = false;
        vo.message = message;
        return vo;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getCustTypeCd() { return custTypeCd; }
    public void setCustTypeCd(String custTypeCd) { this.custTypeCd = custTypeCd; }

    public String getCustDtlTypeCd() { return custDtlTypeCd; }
    public void setCustDtlTypeCd(String custDtlTypeCd) { this.custDtlTypeCd = custDtlTypeCd; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getBirthDt() { return birthDt; }
    public void setBirthDt(String birthDt) { this.birthDt = birthDt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }

    public String getHomeTel() { return homeTel; }
    public void setHomeTel(String homeTel) { this.homeTel = homeTel; }

    public String getInitActivationDate() { return initActivationDate; }
    public void setInitActivationDate(String initActivationDate) { this.initActivationDate = initActivationDate; }
}
