package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.form.common.util.MaskingUtil;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ChildInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;           // 서비스계약번호
    private String minorAgentSvcCntrNo; // 법정대리인 서비스계약번호
    private String mobileNo;            // 휴대폰번호
    private String cntrMobileNo;
    private String customerId;          // 사용자ID
    private String contractNum;         // 계약번호
    private String name;                // 이름

    private String famSeq;              // 가족관계 시퀀스
    private int seq;                    // 구성원 시퀀스
    private String memType;             // 구성원 유형
    private String childType;           // 피부양자 유형
    private String strtDttm;            // 시작일시
    private String endDttm;             // 종료일시
    private String useYn;               // 사용여부
    private int moCtn;
    private String formatUnSvcNo;

}
