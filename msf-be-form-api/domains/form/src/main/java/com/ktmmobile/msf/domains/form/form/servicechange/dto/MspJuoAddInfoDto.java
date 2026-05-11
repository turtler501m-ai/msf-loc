package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspJuoAddInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 결합 신청 기본 정보
    private String contractNum;
    // 단말 지원금
    private int dvcOfwAmnt;
    // 약정 원금
    private int instOrginAmnt;
    // 약정 개월 수
    private int instMnthCnt;
    // 적용 시작 일시
    private String appStartDd;
    // 적용 종료 일시
    private String appEndDate;
    // 정정 개월 수
    private int enggMnthCnt;
    // 단말 모델명
    private String modelName;
    // 단말 모델 ID
    private String modelId;
    // 월 납부 금액
    private int monthPay;
    // 남은 납부 금액
    private int remainPay;
    // 남은 납부 개월 수
    private int remainMonth;
    // 회선번호
    private String subscriberNo;
    // 정정 여부
    private String enggYn;
    // 대리점 코드
    private String agentCd;
    // 대리점명
    private String agentNm;
    // 채널 코드
    private String channelCd;
    // 채널명
    private String channelNm;
}
