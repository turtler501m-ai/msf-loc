package com.ktmmobile.msf.formComm.dto;

/**
 * 대리점/판매점 조회 응답 VO.
 * MSF_AGENT_SHOP_INFO 테이블 기반.
 * 프론트엔드 대리점 선택 드롭다운 옵션 구조와 일치 (value/label).
 */
public class AgencyShopResVO {

    /** 대리점 코드 (AGENT_SHOP_CD) */
    private String value;
    /** 대리점명 (AGENT_SHOP_NM) */
    private String label;

    public AgencyShopResVO() {}

    public AgencyShopResVO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
