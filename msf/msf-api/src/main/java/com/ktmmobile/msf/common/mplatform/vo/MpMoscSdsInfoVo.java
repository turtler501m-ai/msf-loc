package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParseUtil;

/**
 * X62 심플할인 정보조회 응답 VO.
 * ASIS: MpMoscSdsInfoVo (mcp-portal-was) 동일 구조.
 * 핵심 필드: ppPenlt (요금할인 반환금 = 심플할인 위약금).
 */
public class MpMoscSdsInfoVo extends CommonXmlVO {

    /** 약정 시작일 */
    private String engtAplyStDate;
    /** 약정 종료일 */
    private String engtExpirPamDate;
    /** 약정 개월 */
    private String engtPerdMonsNum;
    /** 요금할인 적용 여부 */
    private String chageDcAplyYn;
    /** 월 할인 금액 */
    private String dcSuprtAmt;
    /** 요금할인 반환금 (심플할인 위약금) — 핵심 필드 */
    private String ppPenlt;

    @Override
    protected void parse() throws Exception {
        this.engtAplyStDate   = XmlParseUtil.getChildValue(this.body, "engtAplyStDate");
        this.engtExpirPamDate = XmlParseUtil.getChildValue(this.body, "engtExpirPamDate");
        this.engtPerdMonsNum  = XmlParseUtil.getChildValue(this.body, "engtPerdMonsNum");
        this.chageDcAplyYn    = XmlParseUtil.getChildValue(this.body, "chageDcAplyYn");
        this.dcSuprtAmt       = XmlParseUtil.getChildValue(this.body, "dcSuprtAmt");
        this.ppPenlt          = XmlParseUtil.getChildValue(this.body, "ppPenlt");
    }

    public String getEngtAplyStDate()   { return engtAplyStDate; }
    public String getEngtExpirPamDate() { return engtExpirPamDate; }
    public String getEngtPerdMonsNum()  { return engtPerdMonsNum; }
    public String getChageDcAplyYn()    { return chageDcAplyYn; }
    public String getDcSuprtAmt()       { return dcSuprtAmt; }
    /** 요금할인 반환금 (심플할인 위약금) */
    public String getPpPenlt()          { return ppPenlt; }
}
