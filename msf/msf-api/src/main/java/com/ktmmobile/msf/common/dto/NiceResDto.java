package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class NiceResDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String EncodeData;
    private String param_r1;
    private String param_r2;
    private String param_r3;
    private String enc_data;

    /** 요청 일련번호 */
    private String reqSeq ;
    /** 응답 일련번호 */
    private String resSeq ;
    /** 요청 Type */
    private String authType ;
    /** 인증성명 */
    private String name ;
    /** 인증생년월일 */
    private String birthDate ;
    private String gender 	;
    private String nationalInfo ;
    private String dupInfo 	;
    private String connInfo ;
    private String sMobileNo;
    private String sMobileCo;
	/** 고객인증한  기변변경 휴대폰 번호  */
    private String ctn ;
    /** 가상주민번호 (13자리이며, 숫자 또는 문자 포함) */
    private String sVNumber ;
    //계좌 번호 유효성 check
    private String service   ; //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
    private String resId     ; //주민번호(사업자 번호,법인번호)
    private String bankCode  ; //은행코드(전문참조)
    private String accountNo ; //계좌번호
    private String svcGbn    ; //업무구분(전문참조)
    private String svcCls    ; //내-외국인구분
    private String otp       ; //계좌점유인증 otp
	private String requestNo; //요청고유번호
	private String resUniqId;  //응답고유번호
    public String getsMobileNo() {
		return sMobileNo;
	}
	public void setsMobileNo(String sMobileNo) {
		this.sMobileNo = sMobileNo;
	}
	public String getsMobileCo() {
		return sMobileCo;
	}
	public void setsMobileCo(String sMobileCo) {
		this.sMobileCo = sMobileCo;
	}


	public String getEncodeData() {
        return EncodeData;
    }
    public void setEncodeData(String encodeData) {
        EncodeData = encodeData;
    }
    public String getParam_r1() {
        if (param_r1 == null) {
            return "";
        }
        return param_r1;
    }
    public void setParam_r1(String param_r1) {
        this.param_r1 = param_r1;
    }
    public String getParam_r2() {
        if (param_r2 == null) {
            return "";
        }
        return param_r2;
    }
    public void setParam_r2(String param_r2) {
        this.param_r2 = param_r2;
    }
    public String getParam_r3() {
        if (param_r3 == null) {
            return "";
        }
        return param_r3;
    }
    public void setParam_r3(String param_r3) {
        this.param_r3 = param_r3;
    }
    public String getEnc_data() {
        return enc_data;
    }
    public void setEnc_data(String enc_data) {
        this.enc_data = enc_data;
    }
    public String getReqSeq() {
        return reqSeq;
    }
    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }
    public String getResSeq() {
        return resSeq;
    }
    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }
    public String getAuthType() {
        if (StringUtils.isBlank(authType)) {
            return "";
        }
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getName() {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.toUpperCase();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNationalInfo() {
        return nationalInfo;
    }
    public void setNationalInfo(String nationalInfo) {
        this.nationalInfo = nationalInfo;
    }
    public String getDupInfo() {
        if (StringUtils.isBlank(dupInfo)) {
            return "";
        }
        return dupInfo;
    }
    public void setDupInfo(String dupInfo) {
        this.dupInfo = dupInfo;
    }

    public String getConnInfo() {
        if (StringUtils.isBlank(connInfo)) {
            return "";
        }
        return connInfo;
    }
    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }
    public String getService() {
        if (StringUtils.isBlank(service)) {
            return "";
        }
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public String getResId() {
        if (StringUtils.isBlank(resId)) {
            return "";
        }
        return resId;
    }
    public void setResId(String resId) {
        this.resId = resId;
    }
    public String getBankCode() {
        if (StringUtils.isBlank(bankCode)) {
            return "";
        }
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getAccountNo() {
        if (StringUtils.isBlank(accountNo)) {
            return "";
        }
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getSvcGbn() {
        if (StringUtils.isBlank(svcGbn)) {
            return "";
        }
        return svcGbn;
    }
    public void setSvcGbn(String svcGbn) {
        this.svcGbn = svcGbn;
    }
    public String getSvcCls() {
        if (StringUtils.isBlank(svcCls)) {
            return "";
        }
        return svcCls;
    }
    public void setSvcCls(String svcCls) {
        this.svcCls = svcCls;
    }
    public String getCtn() {
        return ctn;
    }
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    public String getsVNumber() {
        return sVNumber;
    }
    public void setsVNumber(String sVNumber) {
        this.sVNumber = sVNumber;
    }
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getResUniqId() {
		return resUniqId;
	}
	public void setResUniqId(String resUniqId) {
		this.resUniqId = resUniqId;
	}




}
