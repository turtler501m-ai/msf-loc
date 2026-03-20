package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X89 현재 요금상품 예약변경 조회 응답 VO.
 * 예약된 요금제 SOC, 적용일 등 반환.
 */
public class MpFarPriceResvInfoVO extends CommonXmlVO {

    /** 예약 요금제 SOC 코드 */
    private String resvSoc;

    /** 예약 요금제 명 */
    private String resvSocNm;

    /** 예약 적용 일자 */
    private String resvApplyDt;

    /** 예약 존재 여부 */
    private boolean hasReservation;

    @Override
    protected void parse() {
        if (body == null) return;
        this.resvSoc = XmlParseUtil.getChildValue(body, "resvSoc");
        this.resvSocNm = XmlParseUtil.getChildValue(body, "resvSocNm");
        this.resvApplyDt = XmlParseUtil.getChildValue(body, "resvApplyDt");
        this.hasReservation = resvSoc != null && !resvSoc.isEmpty();
    }

    public String getResvSoc() { return resvSoc; }
    public String getResvSocNm() { return resvSocNm; }
    public String getResvApplyDt() { return resvApplyDt; }
    public boolean isHasReservation() { return hasReservation; }
}
