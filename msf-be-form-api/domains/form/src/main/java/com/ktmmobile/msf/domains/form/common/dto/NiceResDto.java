package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("PMD.FieldNamingConventions")
public class NiceResDto implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private String EncodeData;
    private String param_r1;
    private String param_r2;
    private String param_r3;
    private String enc_data;
    private String reqSeq;           // 요청 일련번호
    private String resSeq;           // 응답 일련번호
    private String authType;         // 요청 Type
    private String name;             // 인증성명
    private String birthDate;        // 인증생년월일
    private String gender;
    private String nationalInfo;
    private String dupInfo;
    private String connInfo;
    private String sMobileNo;
    private String sMobileCo;
    private String ctn;              // 고객인증한 기변변경 휴대폰 번호
    private String sVNumber;         // 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
    private String service;          // 서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
    private String resId;            // 주민번호(사업자 번호,법인번호)
    private String bankCode;         // 은행코드(전문참조)
    private String accountNo;        // 계좌번호
    private String svcGbn;           // 업무구분(전문참조)
    private String svcCls;           // 내-외국인구분
    private String otp;              // 계좌점유인증 otp
    private String requestNo;        // 요청고유번호
    private String resUniqId;        // 응답고유번호

    // s/sV 접두사 필드들 비표준 getter/setter 유지
    public String getsMobileNo() { return sMobileNo; }

    public void setsMobileNo(String sMobileNo) { this.sMobileNo = sMobileNo; }

    public String getsMobileCo() { return sMobileCo; }

    public void setsMobileCo(String sMobileCo) { this.sMobileCo = sMobileCo; }

    public String getsVNumber() { return sVNumber; }

    public void setsVNumber(String sVNumber) { this.sVNumber = sVNumber; }

    // null/blank-check 포함 getter 유지
    public String getParam_r1() { return param_r1 == null ? "" : param_r1; }

    public String getParam_r2() { return param_r2 == null ? "" : param_r2; }

    public String getParam_r3() { return param_r3 == null ? "" : param_r3; }

    public String getAuthType() {
        if (StringUtils.isBlank(authType)) {
            return "";
        }
        return authType;
    }

    public String getName() {
        if (StringUtils.isBlank(name)) {
            return "";
        }
        return name.toUpperCase();
    }

    public String getDupInfo()   { return StringUtils.isBlank(dupInfo)   ? "" : dupInfo; }

    public String getConnInfo()  { return StringUtils.isBlank(connInfo)  ? "" : connInfo; }

    public String getService()   { return StringUtils.isBlank(service)   ? "" : service; }

    public String getResId()     { return StringUtils.isBlank(resId)     ? "" : resId; }

    public String getBankCode()  { return StringUtils.isBlank(bankCode)  ? "" : bankCode; }

    public String getAccountNo() { return StringUtils.isBlank(accountNo) ? "" : accountNo; }

    public String getSvcGbn()    { return StringUtils.isBlank(svcGbn)    ? "" : svcGbn; }

    public String getSvcCls()    { return StringUtils.isBlank(svcCls)    ? "" : svcCls; }

}
