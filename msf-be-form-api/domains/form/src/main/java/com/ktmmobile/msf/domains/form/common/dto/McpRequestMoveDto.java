package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestMoveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;                // 가입신청_키
    private String moveCompany;             // 번호이동정보_변경전통신사
    private String moveMobileFn;            // 번호이동정보_이동할번호_앞자리
    private String moveMobileMn;            // 번호이동정보_이동할번호_중간자리
    private String moveMobileRn;            // 번호이동정보_이동할번호_끝자리
    private String moveAuthType;            // 번호이동정보_인증유형
    private String moveAuthNumber;          // 번호이동정보_인증번호4자리
    private String moveThismonthPayType;    // 번호이동정보_이달사용요금납부방법
    private String moveAllotmentStat;       // 번호이동정보_휴대폰_할부금상태
    private String moveRefundAgreeFlag;     // 번호이동정보_미환급액요금상계동의_여부
    private Date sysRdate;                  // 등록일시
    private String reqGuideFlag;
    private String reqGuideFn;
    private String reqGuideRn;
    private String reqGuideMn;
    private String osstPayType;             // 번호이동 납부방법코드
    private String osstPayDay;              // 번호이동 납부주장일자

}
