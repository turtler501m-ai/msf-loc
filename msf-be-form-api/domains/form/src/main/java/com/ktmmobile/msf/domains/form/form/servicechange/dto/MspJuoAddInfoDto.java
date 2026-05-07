package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MspJuoAddInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractNum; // 가입계약번호
    private int dvcOfwAmnt; // 단말기출고가
    private int instOrginAmnt; // 할부원금
    private int instMnthCnt; // 할부개월수
    private String appStartDd; // 적용시작일시 - YYYYMMDDHH24MISS
    private String appEndDate; // 적용종료일시 - YYYYMMDDHH24MISS
    private int enggMnthCnt; // 약정개월수
    private String modelName; // 단말기 모델명
    private String modelId; // 단말기 모델ID
    private int monthPay; // 월납부액
    private int remainPay; // 잔여할부금액
    private int remainMonth; // 잔여할부개월
    private String subscriberNo; // 전화번호
    private String enggYn; // 약정여부
    private String agentCd; // 대리점 코드
    private String agentNm; // 대리점 명
    private String channelCd; // 채널 코드
    private String channelNm; // 채널 명

    public int getMonthPay() {
        if (instOrginAmnt != 0) {
            this.monthPay = instOrginAmnt / instOrginAmnt;
        }
        return monthPay;
    }
}
