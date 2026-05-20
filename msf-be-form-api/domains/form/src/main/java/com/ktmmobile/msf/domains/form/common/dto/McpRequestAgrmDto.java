package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestAgrmDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractNum;     // 계약번호
    private long seq;               // 일련번호
    private String globalNo;        // mplatform 연동 globalNo
    private String dlvryName;       // 배송정보_이름
    private String dlvryMobileFn;   // 배송정보_휴대폰번호_앞자리
    private String dlvryMobileMn;   // 배송정보_휴대폰번호_중간자리
    private String dlvryMobileRn;   // 배송정보_휴대폰번호_뒷자리
    private String dlvryPost;       // 배송정보_우편번호
    private String dlvryAddr;       // 배송정보_주소
    private String dlvryAddrDtl;    // 배송정보_상세주소
    private String presentCode;     // 사은품 상품 코드_공통코드_presentCode
    private Date regstDttm;         // 등록일
    private Date rvisnDttm;         // 수정일
    private String orderType;       // 01:마이페이지에서 신청, 02:sms발송으로 신청

}
