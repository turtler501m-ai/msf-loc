package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * X18 실시간 요금조회 응답 VO.
 * ASIS MpFarRealtimePayInfoVO 와 동일 구조.
 * 필드: searchDay, searchTime, sumAmt, list(gubun+payMent)
 */
public class MpFarRealtimePayInfoVO extends CommonXmlVO {

    /** 조회 날짜 (현재 날짜) */
    private String searchDay;

    /** 조회 기간 (현재 월 1일 ~ 현재 날짜) */
    private String searchTime;

    /** 당월요금계 금액 문자열 (예: "19,000 원") */
    private String sumAmt;

    /** 요금 항목 목록 */
    private List<FareItem> items = new ArrayList<>();

    @Override
    protected void parse() throws Exception {
        this.searchDay  = XmlParseUtil.getChildValue(this.body, "searchDay");
        this.searchTime = XmlParseUtil.getChildValue(this.body, "searchTime");

        List<Element> amntList = XmlParseUtil.getChildElementList(this.body, "amntDto");
        for (Element item : amntList) {
            String gubun   = XmlParseUtil.getChildValue(item, "gubun");
            String payment = XmlParseUtil.getChildValue(item, "payMent");
            if ("원단위절사금액".equals(gubun.trim())) continue;
            items.add(new FareItem(gubun, payment));
            if ("당월요금계".equals(gubun.trim())) {
                this.sumAmt = payment;
            }
        }
    }

    public String getSearchDay()  { return searchDay; }
    public String getSearchTime() { return searchTime; }
    public String getSumAmt()     { return sumAmt; }
    public List<FareItem> getItems() { return items; }

    public static class FareItem {
        private final String gubun;
        private final String payment;

        public FareItem(String gubun, String payment) {
            this.gubun   = gubun;
            this.payment = payment;
        }

        public String getGubun()   { return gubun; }
        public String getPayment() { return payment; }
    }
}
