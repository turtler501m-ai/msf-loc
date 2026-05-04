package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NiceAccountRequest {
    private String EncodeData;
    private String param_r1;
    private String param_r2;
    private String param_r3;
    private String enc_data;

    /**
     * 요청 일련번호
     */
    private String reqSeq;
    /**
     * 응답 일련번호
     */
    private String resSeq;
    /**
     * 요청 Type
     */
    private String authType;
    /**
     * 인증성명
     */
    private String name;
    /**
     * 인증생년월일
     */
    private String birthDate;
    private String gender;
    private String nationalInfo;
    private String dupInfo;
    private String connInfo;
    private String sMobileNo;
    private String sMobileCo;
    /**
     * 고객인증한  기변변경 휴대폰 번호
     */
    private String ctn;
    /**
     * 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
     */
    private String sVNumber;

    //계좌 번호 유효성 check
    private String service; //서비스구분 1=계좌소유주확인 2=계좌성명확인 3=계좌유효성확인
    private String resId; //주민번호(사업자 번호,법인번호)
    private String bankCode; //은행코드(전문참조)
    private String accountNo; //계좌번호
    private String svcGbn; //업무구분(전문참조)
    private String svcCls; //내-외국인구분
    private String otp; //계좌점유인증 otp
    private String requestNo; //요청고유번호
    private String resUniqId;

    //2026.04.29 추가 (필요여부는 추후 고민)
    private String strGbn; //1:개인, 2:사업자
    private String InqRsn; //조회사유

}
